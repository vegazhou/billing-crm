package com.kt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.bean.GenericChargeSchemeBean;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.ChargeSchemeManager;
import com.kt.biz.model.IScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.model.util.TransformUtil;
import com.kt.biz.types.*;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.*;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.*;
import com.kt.service.mail.MailService;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * Created by Vega Zhou on 2016/3/8.
 */
@Service
public class ChargeSchemeService {

    private static final Logger LOGGER = Logger.getLogger(ChargeSchemeService.class);

    @Autowired
    private ChargeSchemeRepository chargeSchemeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebExSiteService webExSiteService;
    @Autowired
    private WebExSiteRepository webExSiteRepository;
    @Autowired
    private WebExSiteDraftRepository webExSiteDraftRepository;
    @Autowired
    private WebExSiteDraftOrderRelationRepository webExSiteDraftOrderRelationRepository;
    @Autowired
    MailService mailService;


    /**
     *
     * @param displayName
     * @param chargeType the
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IScheme createChargeScheme(String displayName, ChargeType chargeType) throws InsufficientPrivilegeException {
        PrivilegeChecker.isOperator();
        AbstractChargeScheme scheme = ChargeSchemeManager.newInstance(chargeType);
        if (scheme != null) {
            scheme.setDisplayName(displayName);
            scheme.setIsTemplate(true);
            scheme.setCreatedBy(PrincipalContext.getCurrentUserName());
            scheme.setCreatedTime(DateUtil.now());
            scheme.save();
        }
        return scheme;
    }

    public AbstractChargeScheme findChargeSchemeById(String id) throws WafException {
        ChargeScheme schemeEntity = chargeSchemeRepository.findOne(id);
        if (schemeEntity == null) {
            throw new ChargeSchemeNotFoundException();
        }

        AbstractChargeScheme scheme = ChargeSchemeManager.newInstance(ChargeType.valueOf(schemeEntity.getType()));
        if (scheme == null) {
            throw new ChargeSchemeNotFoundException();
        }
        scheme.load(id);
        return scheme;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateChargeScheme(String id, GenericChargeSchemeBean chargeSchemeBean) throws WafException {
        try {
            PrivilegeChecker.isOperator();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(chargeSchemeBean);
            updateChargeScheme(id, parse(jsonString));
        } catch (JsonProcessingException e) {
            LOGGER.error("charge scheme json handling error", e);
            throw new InvalidJsonObjectException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateTelecomChargeScheme(String id, GenericChargeSchemeBean chargeSchemeBean) throws WafException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(chargeSchemeBean);
            updateChargeScheme(id, parse(jsonString));
        } catch (JsonProcessingException e) {
            LOGGER.error("charge scheme json handling error", e);
            throw new InvalidJsonObjectException();
        }
    }

    private void updateChargeScheme(String id, JsonObject json) throws WafException {

        AbstractChargeScheme scheme = findChargeSchemeById(id);
        if (scheme.getState() != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }

        if (!scheme.isTemplate()) {
            List<String> siteNameUsed = getSiteNamesFromJson(json);
            Order order = orderRepository.findOneByChargeId(scheme.getId());
            assertChargeSchemeCanBeUpdated(order);
            if (order != null) {
                assertSitesInChargeSchemeCorrect(siteNameUsed, order.getPid());
                updateSiteDraftOrderRelationship(siteNameUsed, order.getContractId(), order.getPid());
                OrderBeanCache.expire(order.getPid());
            }
        }

        scheme.loadFromJson(json);
        saveChargeScheme(scheme);

        // if the charge scheme is a working copy,
        // we should recalculate the end date of the corresponding order
        if (!scheme.isTemplate()) {
            Order order = orderRepository.findOneByChargeId(id);
            if (order != null) {
                try {
                    Date start = DateUtil.toDate(order.getEffectiveStartDate());
                    Date newEffectiveEndDate = scheme.calculateEndDate(start);
                    order.setEffectiveEndDate(DateUtil.formatDate(newEffectiveEndDate));
                    double firstInstallment = scheme.calculateFirstInstallment(start, new PayInterval(order.getPayInterval()));
                    order.setFirstInstallment(MathUtil.scale(firstInstallment));
                    order.setSysFirstInstallment(MathUtil.scale(firstInstallment));
                    order.setTotalAmount(scheme.calculateTotalAmount(start, newEffectiveEndDate));
                    orderRepository.save(order);
                } catch (ParseException e) {
                    LOGGER.error("Invalid order effective start date found!", e);
                }
            }
        }
    }


    protected void saveChargeScheme(IScheme scheme) {
        scheme.save();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteChargeScheme(String id) throws WafException {
        PrivilegeChecker.isOperator();
        IScheme scheme = findChargeSchemeById(id);
        if (isInUsage(id)) {
            throw new ChargeSchemeIsInUsageException();
        }
        scheme.purge();
    }


    /**
     * 警告: 会强制删除计费方案，仅供方便测试时使用
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByForce(String id) throws WafException {
        IScheme scheme = findChargeSchemeById(id);
        scheme.purge();
    }


    /**
     * 警告: 会强制删除所有计费方案，仅供方便测试时使用
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAllChargeSchemes() throws WafException {
        PrivilegeChecker.requireSuperAdmin();
        List<ChargeScheme> schemes = chargeSchemeRepository.findAll();
        for (ChargeScheme scheme : schemes) {
            deleteChargeScheme(scheme.getId());
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendChargeScheme4Approval(String id) throws WafException {
        PrivilegeChecker.isOperator();
        AbstractChargeScheme scheme = findChargeSchemeById(id);
        CommonState currentState = scheme.getState();
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }

        ChargeScheme entity = chargeSchemeRepository.findOne(id);
        entity.setState(CommonState.WAITING_APPROVAL.toString());
        chargeSchemeRepository.save(entity);

        if (entity.getIsTemplate() == 1) {
            mailService.notifyChargeWaitingApproval(entity);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendTelecomChargeScheme4Approval(String id) throws WafException {
        AbstractChargeScheme scheme = findChargeSchemeById(id);
        CommonState currentState = scheme.getState();
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }

        ChargeScheme entity = chargeSchemeRepository.findOne(id);
        entity.setState(CommonState.WAITING_APPROVAL.toString());
        chargeSchemeRepository.save(entity);

        if (entity.getIsTemplate() == 1) {
            //TODO: should notify approval listener here
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void withdraw(String id) throws WafException {
        PrivilegeChecker.isOperator();
        AbstractChargeScheme scheme = findChargeSchemeById(id);
        CommonState currentState = scheme.getState();
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalSchemeAllowedException();
        }

        ChargeScheme entity = chargeSchemeRepository.findOne(id);
        entity.setState(CommonState.DRAFT.toString());
        chargeSchemeRepository.save(entity);

        if (entity.getIsTemplate() == 1) {
            mailService.notifyChargeWithdrawn(entity);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void decline(String id) throws WafException {
        AbstractChargeScheme scheme = findChargeSchemeById(id);
        if (scheme.getState() == CommonState.IN_EFFECT) {
            return;
        }
        if (scheme.isTemplate()) {
            PrivilegeChecker.isProductAuditor();
        } else {
            PrivilegeChecker.isAnyOf(RoleType.CONTRACT_AUDITOR, RoleType.FIN_AUDITOR);
        }


        ChargeScheme entity = chargeSchemeRepository.findOne(id);
        entity.setState(CommonState.DRAFT.toString());
        chargeSchemeRepository.save(entity);

        if (entity.getIsTemplate() == 1) {
            mailService.notifyChargeDeclined(entity);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approve(String id) throws WafException {
        AbstractChargeScheme scheme = findChargeSchemeById(id);
        CommonState currentState = scheme.getState();
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalSchemeAllowedException();
        }

        if (scheme.isTemplate()) {
            PrivilegeChecker.is(RoleType.PRODUCT_AUDITOR);
        } else {
            PrivilegeChecker.isAnyOf(RoleType.CONTRACT_AUDITOR, RoleType.FIN_AUDITOR);
        }

        ChargeScheme entity = chargeSchemeRepository.findOne(id);
        entity.setState(CommonState.IN_EFFECT.toString());
        chargeSchemeRepository.save(entity);

        if (entity.getIsTemplate() == 1) {
            mailService.notifyChargeApproved(entity);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approveTelecom(String id) throws WafException {
        AbstractChargeScheme scheme = findChargeSchemeById(id);
        CommonState currentState = scheme.getState();
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalSchemeAllowedException();
        }

        ChargeScheme entity = chargeSchemeRepository.findOne(id);
        entity.setState(CommonState.IN_EFFECT.toString());
        chargeSchemeRepository.save(entity);

        if (entity.getIsTemplate() == 1) {
            //TODO: should notify related people here
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected AbstractChargeScheme instantiateFromTemplate(AbstractChargeScheme template) throws WafException {
        if (template.getState() != CommonState.IN_EFFECT) {
            throw new ChargeSchemeIsNotInEffectException();
        }

        AbstractChargeScheme newInstance = template.instantiate();
        saveChargeScheme(newInstance);
        return newInstance;
    }

    public List<AbstractChargeScheme> findChargeScheme(String state, String type,
                                                       boolean isTemplate) throws WafException {
        int intIsTemplate=0;
        if(isTemplate){
            intIsTemplate=1;
        }
        List<AbstractChargeScheme> acsList = new ArrayList<AbstractChargeScheme>();
        Collection<String> types= new ArrayList<String>();
        types.add(type);
        List<ChargeScheme> chargeSchemes = chargeSchemeRepository.findByStateAndIsTemplateAndTypeInOrderByDisplayNameAsc(state,intIsTemplate,types);
        if (chargeSchemes == null || chargeSchemes.size()==0) {
            throw new ChargeSchemeNotFoundException();
        }
        for(ChargeScheme schemeEntity: chargeSchemes) {
            AbstractChargeScheme scheme = ChargeSchemeManager.newInstance(ChargeType.valueOf(schemeEntity.getType()));
            if (scheme != null) {
                scheme.load(schemeEntity.getId());
                acsList.add(scheme);
            }
        }
        return acsList;
    }

    public Page<ChargeScheme> listAllTemplates(int curPage, SearchFilter search) {
        return chargeSchemeRepository.listAllTemplates(curPage, search);
    }


    public List<ChargeScheme> listAvailableTemplates4Biz(Biz biz, BizType bizType) {
        return chargeSchemeRepository.listCandidateTemplates4Biz(biz);
//        return chargeSchemeRepository.findByStateAndIsTemplateAndTypeInOrderByDisplayNameAsc(CommonState.IN_EFFECT.toString(), 1,
//                BizChargeCompatibility.getAvailableChargeTypesAsStringList(bizType));
    }





    private boolean isInUsage(String chargeSchemeId) {
        return productRepository.findByChargeSchemeId(chargeSchemeId).size() > 0;
    }


    private void assertSitesInChargeSchemeCorrect(List<String> siteNames, String orderId) throws WafException {
        if (siteNames == null || siteNames.size() == 0) {
            return;
        }
        for (String siteName : siteNames) {
            webExSiteService.assertSiteValid4Order(siteName, orderId);
        }
    }

    public void updateSiteDraftOrderRelationship(List<String> siteNames, String contractId, String orderId) throws WafException {
        webExSiteDraftOrderRelationRepository.deleteByOrderId(orderId);
        if (siteNames == null || siteNames.size() == 0) {
            return;
        }
        for (String siteName : siteNames) {
            if (StringUtils.isNotBlank(siteName)) {
                siteName = siteName.trim().toLowerCase();

                WebExSite workingSite = webExSiteRepository.findOneBySiteName(siteName);
                if (workingSite == null) {
                    WebExSiteDraft draft = webExSiteDraftRepository.findBySiteNameAndContractId(siteName, contractId);
                    WebExSiteOrderRelation relation = new WebExSiteOrderRelation();
                    relation.setSiteId(draft.getId());
                    relation.setOrderId(orderId);
                    relation.setIsDraft(1);
                    webExSiteDraftOrderRelationRepository.save(relation);
                } else {
                    WebExSiteOrderRelation relation = new WebExSiteOrderRelation();
                    relation.setSiteId(workingSite.getPid());
                    relation.setOrderId(orderId);
                    relation.setIsDraft(1);
                    webExSiteDraftOrderRelationRepository.save(relation);
                }
            }
        }
    }

    private List<String> getSiteNamesFromJson(JsonObject json) {
        List<String> results = new ArrayList<>();
        String site = TransformUtil.getMemberAsString(json, SchemeKeys.COMMON_SITE);
        if (StringUtils.isNotBlank(site)) {
            results.add(site.trim().toLowerCase());
        }

        List<String> sites = TransformUtil.getMemberAsStringList(json, SchemeKeys.COMMON_SITES);
        if (sites != null) {
            results.addAll(sites);
        }
        return results;
    }


    private JsonObject parse(String input) throws InvalidJsonObjectException {
        try {
            JsonParser parser = new JsonParser();
            return (JsonObject) parser.parse(input);
        } catch (Exception e) {
            throw new InvalidJsonObjectException();
        }
    }


    private void assertChargeSchemeCanBeUpdated(Order order) throws WafException {
        if (order != null && StringUtils.isNotBlank(order.getOriginalOrderId())) {
            // 不允许修改合同变更中的订购的计费方案
            throw new UpdatingOriginalOrderNotAllowedException();
        }
    }
}
