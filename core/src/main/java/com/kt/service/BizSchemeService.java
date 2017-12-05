package com.kt.service;

import com.google.gson.JsonObject;
import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.model.IScheme;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.BizSchemeManager;
import com.kt.biz.types.BizType;
import com.kt.biz.types.CommonState;
import com.kt.biz.types.RoleType;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Biz;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.BizRepository;
import com.kt.repo.mysql.batch.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
@Service
public class BizSchemeService {
    @Autowired
    private BizRepository bizRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IScheme createBizScheme(String displayName, BizType bizType) throws InsufficientPrivilegeException {
        PrivilegeChecker.isOperator();
        AbstractBizScheme scheme = BizSchemeManager.newInstance(bizType);
        if (scheme != null) {
            scheme.setDisplayName(displayName);
            scheme.setIsTemplate(true);
            scheme.setState(CommonState.DRAFT);
            scheme.save();
        }
        return scheme;
    }


    public AbstractBizScheme findBizSchemeById(String id) throws WafException {
        Biz schemeEntity = bizRepository.findOne(id);
        if (schemeEntity == null) {
            throw new BizSchemeNotFoundException();
        }

        AbstractBizScheme scheme = BizSchemeManager.newInstance(BizType.valueOf(schemeEntity.getType()));
        if (scheme == null) {
            throw new BizSchemeNotFoundException();
        }
        scheme.load(id);
        return scheme;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteBizScheme(String id) throws WafException {
        PrivilegeChecker.isOperator();
        IScheme scheme = findBizSchemeById(id);
        if (isInUsage(id)) {
            throw new BizSchemeIsInUsageException();
        }
        scheme.purge();
    }


    /**
     * 警告: 会强制删除业务方案，仅供方便测试时使用
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByForce(String id) throws WafException {
        IScheme scheme = findBizSchemeById(id);
        scheme.purge();
    }


    /**
     * WARNING: for test usage only
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAllBizSchemes() throws WafException {
        PrivilegeChecker.requireSuperAdmin();
        List<Biz> all = bizRepository.findAll();
        for (Biz biz : all) {
            deleteBizScheme(biz.getId());
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateBizScheme(String id, JsonObject json) throws WafException {
        PrivilegeChecker.isOperator();
        AbstractBizScheme scheme = findBizSchemeById(id);
        if (scheme.getState() != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }
        scheme.loadFromJson(json);
        saveBizScheme(scheme);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void saveBizScheme(IScheme scheme) throws SchemeValidationException, InsufficientPrivilegeException {
        scheme.validate();
        scheme.save();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendBizScheme4Approval(String bizId) throws WafException {
        PrivilegeChecker.isOperator();
        Biz biz = bizRepository.findOne(bizId);
        CommonState currentState = CommonState.valueOf(biz.getState());

        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }
        biz.setState(CommonState.WAITING_APPROVAL.toString());
        bizRepository.save(biz);

        if (biz.getIsTemplate() == 1) {
            //TODO: notify listeners biz sent for approval
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendTelecomBizScheme4Approval(String bizId) throws WafException {
        Biz biz = bizRepository.findOne(bizId);
        CommonState currentState = CommonState.valueOf(biz.getState());

        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }
        biz.setState(CommonState.WAITING_APPROVAL.toString());
        bizRepository.save(biz);

        if (biz.getIsTemplate() == 1) {
            //TODO: notify listeners biz sent for approval
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void withdraw(String bizId) throws WafException {
        PrivilegeChecker.isOperator();
        Biz biz = bizRepository.findOne(bizId);
        CommonState currentState = CommonState.valueOf(biz.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }
        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalSchemeAllowedException();
        }
        biz.setState(CommonState.DRAFT.toString());
        bizRepository.save(biz);

        if (biz.getIsTemplate() == 1) {
            //TODO: notify listeners biz withdrawn
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void decline(String bizId) throws WafException {
        PrivilegeChecker.isProductAuditor();
        Biz biz = bizRepository.findOne(bizId);
        CommonState currentState = CommonState.valueOf(biz.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        biz.setState(CommonState.DRAFT.toString());
        bizRepository.save(biz);

        if (biz.getIsTemplate() == 1) {
            //TODO: notify listeners biz declined
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approve(String bizId) throws WafException {
        Biz biz = bizRepository.findOne(bizId);
        CommonState currentState = CommonState.valueOf(biz.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (biz.getIsTemplate() == 1) {
            PrivilegeChecker.isProductAuditor();
        } else {
            PrivilegeChecker.isAnyOf(RoleType.CONTRACT_AUDITOR, RoleType.FIN_AUDITOR);
        }

        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalSchemeAllowedException();
        }
        biz.setState(CommonState.IN_EFFECT.toString());
        bizRepository.save(biz);

        if (biz.getIsTemplate() == 1) {
            //TODO: notify listeners biz approved
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approveTelecom(String bizId) throws WafException {
        Biz biz = bizRepository.findOne(bizId);
        CommonState currentState = CommonState.valueOf(biz.getState());
        if (currentState == CommonState.IN_EFFECT) {
            return;
        }

        if (currentState != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalSchemeAllowedException();
        }
        biz.setState(CommonState.IN_EFFECT.toString());
        bizRepository.save(biz);

        if (biz.getIsTemplate() == 1) {
            //TODO: notify listeners biz approved
        }
    }


    protected AbstractBizScheme instantiateFromTemplate(AbstractBizScheme template) throws WafException {
        if (template.getState() != CommonState.IN_EFFECT) {
            throw new BizSchemeIsNotInEffectException();
        }

        AbstractBizScheme newInstance = template.instantiate();
        saveBizScheme(newInstance);
        return newInstance;
    }


    public Page<Biz> listAllTemplates(int curPage, SearchFilter search) {
        return bizRepository.listAllTemplates(curPage, search);
    }


    private boolean isInUsage(String bizId) {
        return productRepository.findByBizId(bizId).size() > 0;
    }
    
    
    public List<Biz> findAllInEffectTemplates() {
        return bizRepository.findByStateAndIsTemplateOrderByDisplayNameAsc(CommonState.IN_EFFECT.toString(), 1);
    }

    public List<Biz> findAllUnusedInEffectTemplates() {
        return bizRepository.listAllUnusedInEffectTemplates();
    }
}
