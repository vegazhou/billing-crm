package com.kt.service;

import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.site.LanguageMatrix;
import com.kt.biz.site.WebExSitePrimaryFields;
import com.kt.biz.types.*;
import com.kt.biz.validators.WebExSiteNameValidator;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.*;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.MigrateWebExSiteRepository;
import com.kt.repo.mysql.batch.WebExSiteDraftOrderRelationRepository;
import com.kt.repo.mysql.batch.WebExSiteDraftRepository;
import com.kt.repo.mysql.batch.WebExSiteRepository;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Service
public class WebExSiteService {

    @Autowired
    private WebExSiteRepository webExSiteRepository;
    @Autowired
    private WebExSiteDraftRepository webExSiteDraftRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WebExSiteDraftOrderRelationRepository webExSiteDraftOrderRelationRepository;
    @Autowired
    private WbxProvTaskService wbxProvTaskService;
    @Autowired
    private MigrateWebExSiteRepository migrateWebExSiteRepository;


    public List<String> listSiteNamesAvailable4Contract(String contractId) throws WafException {
        return webExSiteRepository.findAvailableSiteNames4Contract(contractId);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WebExSiteDraft draftSite4Contract(String contractId, WebExSitePrimaryFields sitePrimaryFields) throws WafException {
        PrivilegeChecker.isOperator();
        String siteName = sitePrimaryFields.getSiteName();
        if (!WebExSiteNameValidator.isValidSiteName(siteName)) {
            throw new InvalidWebExSiteNameException();
        }
        siteName = siteName.trim().toLowerCase();

        Contract contract = contractService.findByContractId(contractId);
        if (CommonState.DRAFT != CommonState.valueOf(contract.getState())) {
            throw new OnlyDraftingContractAllowedException();
        }

        WebExSiteDraft existingDraft = webExSiteDraftRepository.findBySiteNameAndContractId(siteName, contractId);
        if (existingDraft != null) {
            throw new DuplicatedWebExSiteDraftException();
        }

        WebExSite existingSite = webExSiteRepository.findOneBySiteName(siteName);
        if (existingSite != null) {
            throw new SiteAlreadyExistedException();
        }

        WebExSiteDraft draft = new WebExSiteDraft();
        draft.setContractId(contractId);
        draft.setSiteName(siteName);

        Language primaryLanguage = Language.SIMPLIFIED_CHINESE;
        List<Language> additionalLanguages = new ArrayList<>();
        TimeZone timeZone = TimeZone.BEIJING;

        if (sitePrimaryFields.getLocation() == null) {
            throw new InvalidLocationException();
        }

        LanguageMatrix lm = sitePrimaryFields.getLanguages();
        if (lm != null) {
            if (lm.getPrimaryLanguage() != null) {
                primaryLanguage = lm.getPrimaryLanguage();
            }
            additionalLanguages = lm.getEnabledAdditionalLanguages();
        }
        if (sitePrimaryFields.getTimeZone() != null) {
            timeZone = sitePrimaryFields.getTimeZone();
        }

        draft.setCountryCode("CN");
        draft.setPrimaryLanguage(primaryLanguage.toString());
        draft.setTimeZone(timeZone.toString());
        draft.setAdditionalLanguage(StringUtils.join(additionalLanguages, ";"));
        draft.setLocation(sitePrimaryFields.getLocation().toString());
        return webExSiteDraftRepository.save(draft);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WebExSiteDraft draftSite4TelcomContract(String contractId, WebExSitePrimaryFields sitePrimaryFields) throws WafException {
        String siteName = sitePrimaryFields.getSiteName();
        if (!WebExSiteNameValidator.isValidSiteName(siteName)) {
            throw new InvalidWebExSiteNameException();
        }
        siteName = siteName.trim().toLowerCase();

        Contract contract = contractService.findByContractId(contractId);
        if (CommonState.DRAFT != CommonState.valueOf(contract.getState())) {
            throw new OnlyDraftingContractAllowedException();
        }

        WebExSiteDraft existingDraft = webExSiteDraftRepository.findBySiteNameAndContractId(siteName, contractId);
        if (existingDraft != null) {
            throw new DuplicatedWebExSiteDraftException();
        }

        WebExSite existingSite = webExSiteRepository.findOneBySiteName(siteName);
        if (existingSite != null) {
            throw new SiteAlreadyExistedException();
        }

        WebExSiteDraft draft = new WebExSiteDraft();
        draft.setContractId(contractId);
        draft.setSiteName(siteName);

        Language primaryLanguage = Language.SIMPLIFIED_CHINESE;
        List<Language> additionalLanguages = new ArrayList<>();
        TimeZone timeZone = TimeZone.BEIJING;

        if (sitePrimaryFields.getLocation() == null) {
            throw new InvalidLocationException();
        }

        LanguageMatrix lm = sitePrimaryFields.getLanguages();
        if (lm != null) {
            if (lm.getPrimaryLanguage() != null) {
                primaryLanguage = lm.getPrimaryLanguage();
            }
            additionalLanguages = lm.getEnabledAdditionalLanguages();
        }
        if (sitePrimaryFields.getTimeZone() != null) {
            timeZone = sitePrimaryFields.getTimeZone();
        }

        draft.setCountryCode("CN");
        draft.setPrimaryLanguage(primaryLanguage.toString());
        draft.setTimeZone(timeZone.toString());
        draft.setAdditionalLanguage(StringUtils.join(additionalLanguages, ";"));
        draft.setLocation(sitePrimaryFields.getLocation().toString());
        return webExSiteDraftRepository.save(draft);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDraft(String draftId, WebExSitePrimaryFields sitePrimaryFields) throws WafException {
        PrivilegeChecker.isOperator();
        WebExSiteDraft draft = webExSiteDraftRepository.findOne(draftId);
        if (draft == null) {
            throw new WebExSiteDraftNotFoundException();
        }

        assertContractIsInDraft(draft.getContractId());

        //changing site name is not allowed, because charge scheme only save site name, not draft id

        if (sitePrimaryFields.getLocation() != null) {
            draft.setLocation(sitePrimaryFields.getLocation().toString());
        }
        if (sitePrimaryFields.getTimeZone() != null) {
            draft.setTimeZone(sitePrimaryFields.getTimeZone().toString());
        }
        LanguageMatrix languageMatrix = sitePrimaryFields.getLanguages();
        if (languageMatrix != null) {
            if (languageMatrix.getPrimaryLanguage() != null) {
                draft.setPrimaryLanguage(languageMatrix.getPrimaryLanguage().toString());
            }
            draft.setAdditionalLanguage(StringUtils.join(languageMatrix.getEnabledAdditionalLanguages(), ";"));
        }

        webExSiteDraftRepository.save(draft);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSite(String draftId, WebExSitePrimaryFields sitePrimaryFields) throws WafException {
        PrivilegeChecker.isOperator();
        WebExSite site = webExSiteRepository.findOne(draftId);
        if (site == null) {
            throw new WebExSiteNotFoundException();
        }

        //assertContractIsInDraft(site.getContractId());

        //changing site name is not allowed, because charge scheme only save site name, not draft id

        if (sitePrimaryFields.getLocation() != null) {
        	site.setLocation(sitePrimaryFields.getLocation().toString());
        }
        if (sitePrimaryFields.getTimeZone() != null) {
        	site.setTimeZone(sitePrimaryFields.getTimeZone().toString());
        }
        LanguageMatrix languageMatrix = sitePrimaryFields.getLanguages();
        if (languageMatrix != null) {
            if (languageMatrix.getPrimaryLanguage() != null) {
            	site.setPrimaryLanguage(languageMatrix.getPrimaryLanguage().toString());
            }
            site.setAdditionalLanguage(StringUtils.join(languageMatrix.getEnabledAdditionalLanguages(), ";"));
        }
        site.setLastModifiedDate(DateUtil.now());
        site.setLastModifiedBy(PrincipalContext.getCurrentUserName());

        webExSiteRepository.save(site);

        //Insert site provisoning task
        wbxProvTaskService.generateSiteChangeTask(site.getSiteName());
    }

    private void assertContractIsInDraft(String contractId) throws WafException {
        Contract contract = contractService.findByContractId(contractId);
        CommonState state = CommonState.valueOf(contract.getState());
        if (state != CommonState.DRAFT) {
            throw new OnlyDraftingContractAllowedException();
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUnusedSiteDraft(String draftId) throws WafException {
        WebExSiteDraft draft = webExSiteDraftRepository.findOne(draftId);
        if (draft == null) {
            throw new WebExSiteDraftNotFoundException();
        }
        WebExSiteOrderRelation relation = webExSiteDraftOrderRelationRepository.findFirstBySiteId(draftId);
        if (relation != null) {
            throw new WebExSiteDraftIsInUsageException();
        }
        webExSiteDraftRepository.delete(draft);
    }

    /**
     * this function only delete the draft entities under a contract,
     * but will NOT delete the draft/order relations
     * @param contractId a contract id, all the drafts under this contract will be be deleted
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAllSiteDraftByContractId(String contractId) {
        webExSiteDraftRepository.deleteByContractId(contractId);
    }


    public WebExSite findBySiteName(String siteName) {
        if (StringUtils.isBlank(siteName)) {
            return null;
        }

        siteName = siteName.trim().toLowerCase();
        return webExSiteRepository.findOneBySiteName(siteName);
    }


    public WebExSiteDraft findByDraftId(String draftId) throws WebExSiteDraftNotFoundException {
        WebExSiteDraft draft = webExSiteDraftRepository.findOne(draftId);
        if (draft == null) {
            throw new WebExSiteDraftNotFoundException();
        }
        return draft;
    }


    public List<WebExSiteDraft> findSiteDraftsOfContract(String contractId) {
        return webExSiteDraftRepository.findByContractId(contractId);
    }


    public void assertSiteNotOccupied(String contractId) throws SiteAlreadyExistedException {
        List<WebExSiteDraft> siteDrafts = findSiteDraftsOfContract(contractId);
        for (WebExSiteDraft siteDraft : siteDrafts) {
            WebExSite existingSite = findBySiteName(siteDraft.getSiteName());
            if (existingSite != null) {
                throw new SiteAlreadyExistedException();
            }

            if (WebExSiteNameValidator.isSiteExistedInWorldWideWeb(siteDraft.getSiteName())) {
                throw new SiteAlreadyExistedException();
            }
        }
    }


    /**
     * The method is used to check if a site name is valid to create a draft of a contract
     *
     * @param siteName      the site name to be checked
     * @param contractId    the contract id in which the site draft will be created
     * @return true if the site name is available for the contract, otherwise false
     */
    public boolean isSiteNameAvailable4Contract(String siteName, String contractId) {
        if (StringUtils.isBlank(siteName)) {
            return false;
        }
        siteName = siteName.trim().toLowerCase();
        if (!WebExSiteNameValidator.isValidSiteName(siteName)) {
            return false;
        }

        WebExSiteDraft draft = webExSiteDraftRepository.findBySiteNameAndContractId(siteName, contractId);
        if (draft != null) {
            // there is already a draft with the same site name in the same contract
            return false;
        }

        WebExSite site = webExSiteRepository.findOneBySiteName(siteName);
        if (site != null) {
            // there is already an occupied site in our system
            return false;
        }

        if (WebExSiteNameValidator.isSiteExistedInWorldWideWeb(siteName)) {
            // the site is a real web site, but not in our system
            return false;
        }
        return true;
    }


    /**
     * This method is called when a site or site draft is assigned to an order, the following validation will be done:
     *  1. the site is an existing in effect site, and belongs to the customer
     *  2. the site is a draft, and belongs to the contract
     *
     * @param siteName siteName be to checked
     * @param orderId the order which the site is assigned to
     * @throws WafException
     */
    public void assertSiteValid4Order(String siteName, String orderId) throws WafException {
        if (StringUtils.isBlank(siteName)) {
            return;
        } else {
            siteName = siteName.trim().toLowerCase();
        }
        Order order = orderService.findOrderById(orderId);
        String contractId = order.getContractId();
        Contract contract = contractService.findByContractId(contractId);
        String customerId = contract.getCustomerId();
        WebExSite workingSite = webExSiteRepository.findOneBySiteNameAndCustomerId(siteName, customerId);
        if (workingSite != null) {
            return;
        }

        WebExSiteDraft draft = webExSiteDraftRepository.findBySiteNameAndContractId(siteName, contractId);
        if (draft == null) {
            throw new InvalidSiteException();
        }
    }


    public List<WebExSite> listSitesOfCustomer(String customerId) {
        return webExSiteRepository.findByCustomerId(customerId);
    }

    /**
     * This method is usually used when a contract is about into effect.
     * the webex site draft will become ordered and occupied, other customers will not be able to use the same site name.
     *
     * @param draftId the id of draft which is to be put in effect
     * @throws WafException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void putDraftInEffect(String draftId) throws WafException {
        WebExSiteDraft draft = webExSiteDraftRepository.findOne(draftId);
        if (draft == null) {
            throw new WebExSiteDraftNotFoundException();
        }

        WebExSiteOrderRelation relationFound = webExSiteDraftOrderRelationRepository.findFirstBySiteId(draftId);
        boolean isDraftReallyUsed = relationFound != null;
        if (!isDraftReallyUsed) {
            // remove from contract cache
            webExSiteDraftOrderRelationRepository.deleteBySiteId(draftId);
            webExSiteDraftRepository.delete(draftId);
        } else {
            provisionWebExSiteByDraft(draftId);
        }
    }


    private WebExSite provisionWebExSiteByDraft(String draftId) throws WafException {
        WebExSiteDraft draft = webExSiteDraftRepository.findOne(draftId);
        if (draft == null) {
            throw new WebExSiteDraftNotFoundException();
        }

        Contract contract = contractService.findByContractId(draft.getContractId());
        Customer customer = customerService.findByCustomerId(contract.getCustomerId());

        WebExSite newSite = new WebExSite();
        newSite.setState(WebExSiteState.PROVISIONING.toString());
        newSite.setCustomerId(customer.getPid());

        copyValuesFromDraft2FormalSite(draft, newSite);
        resetSiteDefaultValues(newSite);
        newSite = webExSiteRepository.save(newSite);

        cloneDraftOrderRelations(newSite.getPid(), draftId);
        webExSiteDraftOrderRelationRepository.deleteBySiteId(draftId);
        webExSiteDraftRepository.delete(draft);

        return newSite;
    }


    private void resetSiteDefaultValues(WebExSite site) {
        site.setVoip(1);
    }

    private void copyValuesFromDraft2FormalSite(WebExSiteDraft draft, WebExSite site) {
        site.setSiteName(draft.getSiteName());
        site.setPrimaryLanguage(draft.getPrimaryLanguage());
        site.setAdditionalLanguage(draft.getAdditionalLanguage());
        site.setTimeZone(draft.getTimeZone());
        site.setLocation(draft.getLocation());
        site.setCountryCode(draft.getCountryCode());
    }


    private void cloneDraftOrderRelations(String siteId, String draftId) {
        List<WebExSiteOrderRelation> relations = webExSiteDraftOrderRelationRepository.findBySiteId(draftId);
        if (relations != null && relations.size() > 0) {
            // copy draft/order relation as site/order relation
            for (WebExSiteOrderRelation relation : relations) {
                WebExSiteOrderRelation r = new WebExSiteOrderRelation();
                r.setIsDraft(0);
                r.setSiteId(siteId);
                r.setOrderId(relation.getOrderId());
                webExSiteDraftOrderRelationRepository.save(r);
            }
        }
    }

    public List<WebExSiteDraft> listAllByPage(int curPage, SearchFilter search) {
        return webExSiteRepository.listAllByPage(curPage, search);
	}
    
    public Page<WebExSiteDraftReport> listAllSiteByPageForReport(int curPage, SearchFilter search) {
        return webExSiteRepository.listAllSiteByPageForReport(curPage, search);
	}
    
    public Page<WebExSiteDraftReport> listAllByPageForReport(int curPage, SearchFilter search) {
        return webExSiteRepository.listAllByPageForReport(curPage, search);
	}

    public WebExSiteOrderRelation findOrderRelationByOrderId(String orderId){
        return webExSiteDraftOrderRelationRepository.findFirstByOrderId(orderId);
    }

    public WebExSite findWebExSiteBySiteId(String siteId) throws WebExSiteDraftNotFoundException{
    	WebExSite draft= webExSiteRepository.findOne(siteId);
        if (draft == null) {
            throw new WebExSiteDraftNotFoundException();
        }
        return draft;
    }

    public WebExSite saveWebExSite(WebExSite site){
        return webExSiteRepository.save(site);
    }

    public MigrateWebExSite saveMigrateWebExSite(MigrateWebExSite site){
        return migrateWebExSiteRepository.save(site);
    }

    public MigrateWebExSite findMigrateSiteBySiteName(String siteName) {
        if (StringUtils.isBlank(siteName)) {
            return null;
        }

        siteName = siteName.trim().toLowerCase();
        return migrateWebExSiteRepository.findOneBySiteName(siteName);
    }

}
