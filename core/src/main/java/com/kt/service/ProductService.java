package com.kt.service;

import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.util.BizChargeCompatibility;
import com.kt.biz.types.AgentType;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;
import com.kt.biz.types.CommonState;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.*;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.BizRepository;
import com.kt.repo.mysql.batch.ChargeSchemeRepository;
import com.kt.repo.mysql.batch.ProductRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BizSchemeService bizSchemeService;
    @Autowired
    private ChargeSchemeService chargeSchemeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BizRepository bizRepository;
    @Autowired
    private ChargeSchemeRepository chargeSchemeRepository;
    @Autowired
    private ContractService contractService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product createProduct(String displayName, String bizTemplateId, String chargeSchemeTemplateId, boolean isTrial, boolean isAgent) throws WafException {
        PrivilegeChecker.isOperator();
        BizType bType = validateBizId(bizTemplateId);

        ChargeType cType = validateChargeSchemeId(chargeSchemeTemplateId);

        if (!BizChargeCompatibility.isCompatible(bType, cType)) {
            throw new BizChargeNotCompatibleException();
        }

        Product product = new Product();
        product.setState(CommonState.DRAFT.toString());
        product.setDisplayName(displayName);

        product.setBizId(bizTemplateId);
        product.setChargeSchemeId(chargeSchemeTemplateId);
        product.setIsTrial(isTrial ? 1 : 0);
        product.setIsAgent(isAgent ? 1 : 0);
        return productRepository.save(product);
    }


    public Product findById(String id) throws ProductNotFoundException {
        Product product = _findById(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        return product;
    }

    public Product _findById(String id) {
        return productRepository.findOne(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateProduct(String id, String displayName, String bizTemplateId, String chargeSchemeTemplateId,boolean isTrial,boolean isAgent) throws WafException {
        PrivilegeChecker.isOperator();
        Product entity = findById(id);
        if (CommonState.valueOf(entity.getState()) != CommonState.DRAFT) {
            throw new EditingNonDraftProductException();
        }

        validateBizId(bizTemplateId);
        validateChargeSchemeId(chargeSchemeTemplateId);
        entity.setIsTrial(isTrial ? 1 : 0);
        entity.setIsAgent(isAgent ? 1 : 0);
        entity.setDisplayName(displayName);
        entity.setBizId(bizTemplateId);
        entity.setChargeSchemeId(chargeSchemeTemplateId);
        productRepository.save(entity);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteProduct(String id) throws WafException {
        PrivilegeChecker.isOperator();
        Product product = productRepository.findOne(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        CommonState state = CommonState.valueOf(product.getState());
        if (state != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }

        productRepository.delete(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendProduct4Approval(String productId) throws WafException {
        PrivilegeChecker.isOperator();
        Product product = findById(productId);
        if (CommonState.valueOf(product.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingProductAllowedException();
        }

        product.setState(CommonState.WAITING_APPROVAL.toString());
        productRepository.save(product);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void withdrawProduct(String productId) throws WafException {
        PrivilegeChecker.isOperator();
        Product product = findById(productId);
        if (CommonState.valueOf(product.getState()) != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalProductAllowedException();
        }

        product.setState(CommonState.DRAFT.toString());
        productRepository.save(product);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approve(String productId) throws WafException {
        PrivilegeChecker.isProductAuditor();
        Product product = findById(productId);
        if (CommonState.valueOf(product.getState()) != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalProductAllowedException();
        }

        product.setState(CommonState.IN_EFFECT.toString());
        productRepository.save(product);

        //TODO: 需要测试以下代码是否为必需
//        AbstractBizScheme bizScheme = bizSchemeService.findBizSchemeById(product.getBizId());
//        bizScheme.setState(CommonState.IN_EFFECT);
//        bizScheme.save();
//
//        AbstractChargeScheme chargeScheme = chargeSchemeService.findChargeSchemeById(product.getChargeSchemeId());
//        chargeScheme.setState(CommonState.IN_EFFECT);
//        chargeScheme.save();

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void decline(String productId) throws WafException {
        PrivilegeChecker.isProductAuditor();
        Product product = findById(productId);
        if (CommonState.valueOf(product.getState()) != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalProductAllowedException();
        }

        product.setState(CommonState.DRAFT.toString());
        productRepository.save(product);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void draftAgentProducts(String agentId, List<String> directProductIds) throws WafException {
        for (String directProductId : directProductIds) {
            draftAgentProduct(agentId, directProductId);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product draftAgentProduct(String agentId, String agentProductId) throws WafException {
        assertAgentIsValid(agentId);
        Product product = assertAgentProductValid(agentProductId);

        AbstractBizScheme bizTemplate = bizSchemeService.findBizSchemeById(product.getBizId());
        AbstractBizScheme bizScheme = bizSchemeService.instantiateFromTemplate(bizTemplate);
        bizSchemeService.saveBizScheme(bizScheme);

        AbstractChargeScheme chargeTemplate = chargeSchemeService.findChargeSchemeById(product.getChargeSchemeId());
        AbstractChargeScheme chargeScheme = chargeSchemeService.instantiateFromTemplate(chargeTemplate);
        chargeSchemeService.saveChargeScheme(chargeScheme);

        Product agentProduct = new Product();
        agentProduct.setAgentId(agentId);
        agentProduct.setIsTrial(product.getIsTrial());
        agentProduct.setIsAgent(1);
        agentProduct.setDisplayName(product.getDisplayName());
        agentProduct.setBizId(bizScheme.getId());
        agentProduct.setChargeSchemeId(chargeScheme.getId());
        agentProduct.setChargeType(product.getChargeType());
        agentProduct.setDirectProduct(product.getPid());
        agentProduct.setState(CommonState.DRAFT.toString());
        productRepository.save(agentProduct);
        return product;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void onShelfAgentProduct(String agentProductId) throws WafException {
        Product product = findById(agentProductId);
        if (StringUtils.isBlank(product.getDirectProduct())) {
            throw new NotAnAgentException();
        }
        if (CommonState.valueOf(product.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingProductAllowedException();
        }

        assertNoOtherInEffectAgentProduct(product.getAgentId(), product.getDirectProduct());

        product.setState(CommonState.IN_EFFECT.toString());
        productRepository.save(product);

        Biz biz = bizRepository.findOne(product.getBizId());
        biz.setState(CommonState.IN_EFFECT.toString());
        bizRepository.save(biz);

        ChargeScheme chargeScheme = chargeSchemeRepository.findOne(product.getChargeSchemeId());
        chargeScheme.setState(CommonState.IN_EFFECT.toString());
        chargeSchemeRepository.save(chargeScheme);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void offShelfAgentProduct(String agentProductId) throws WafException {
        Product product = findById(agentProductId);
        if (StringUtils.isBlank(product.getDirectProduct())) {
            throw new NotAnAgentException();
        }
        if (CommonState.valueOf(product.getState()) != CommonState.IN_EFFECT) {
            throw new OnlyInEffectiveProductAllowedException();
        }

        product.setState(CommonState.END_OF_LIFE.toString());
        productRepository.save(product);

        Biz biz = bizRepository.findOne(product.getBizId());
        biz.setState(CommonState.END_OF_LIFE.toString());
        bizRepository.save(biz);

        ChargeScheme chargeScheme = chargeSchemeRepository.findOne(product.getChargeSchemeId());
        chargeScheme.setState(CommonState.END_OF_LIFE.toString());
        chargeSchemeRepository.save(chargeScheme);
    }



    private void assertAgentIsValid(String agentId) throws NotAnAgentException {
        Customer mayBeAgent;
        try {
            mayBeAgent = customerService.findByCustomerId(agentId);
        } catch (WafException e) {
            throw new NotAnAgentException();
        }

        if (AgentType.valueOf(mayBeAgent.getAgentType()) != AgentType.AGENT) {
            throw new NotAnAgentException();
        }
    }


    private Product assertAgentProductValid(String agentProductId) throws WafException {
        Product product = findById(agentProductId);
        if (product.getIsAgent() == 0) {
            throw new NotAnAgentProductException();
        }
        if (StringUtils.isNotBlank(product.getAgentId())) {
            throw new NotPublicAgentProductException();
        }
        if (CommonState.valueOf(product.getState()) != CommonState.IN_EFFECT) {
            throw new ProductNotInEffectException();
        }
        return product;
    }

    private void assertNoOtherInEffectAgentProduct(String agentId, String publicAgentProductId) throws MoreThanOneAgentProductInEffect {
        List<Product> inEffectAgentProducts = productRepository.findInEffectAgentProduct(agentId, publicAgentProductId);
        if (inEffectAgentProducts.size() > 0) {
            throw new MoreThanOneAgentProductInEffect();
        }
    }


    private BizType validateBizId(String bizId) throws WafException {
        AbstractBizScheme biz = bizSchemeService.findBizSchemeById(bizId);
        if (biz == null) {
            throw new BizSchemeNotFoundException();
        } else if (!biz.isTemplate()) {
            throw new NotABizSchemeTemplateException();
        } else if (CommonState.IN_EFFECT != biz.getState()) {
            throw new BizSchemeIsNotInEffectException();
        }
        return biz.getType();
    }


    private ChargeType validateChargeSchemeId(String chargeSchemeId) throws WafException {
        AbstractChargeScheme chargeScheme = chargeSchemeService.findChargeSchemeById(chargeSchemeId);
        if (chargeScheme == null) {
            throw new ChargeSchemeNotFoundException();
        } else if (!chargeScheme.isTemplate()) {
            throw new NotAChargeSchemeTemplateException();
        } else if (CommonState.IN_EFFECT != chargeScheme.getState()) {
            throw new ChargeSchemeIsNotInEffectException();
        }
        return chargeScheme.getType();
    }
    
    public Page<Product> paginateAllProducts(int curPage, SearchCriteria searchorg)
            throws SQLException, InsufficientPrivilegeException {
        
        return productRepository.findAllProductsByPage(curPage, searchorg);
    }
    
    
    public Page<Product> findProductsForAgent(int curPage, SearchFilter searchorg)
            throws SQLException, InsufficientPrivilegeException {
        
        return productRepository.findProductsForAgent(curPage, searchorg);
    }


    public List<Product> findAllEffectiveProductsOfContract(String contractId) throws WafException {
        Contract contract = contractService.findByContractId(contractId);
        String agentId = contract.getAgentId();
        if (StringUtils.isBlank(agentId)) {
            return findInEffectDirectProducts();
        } else {
            Customer agent = customerService.findByCustomerId(agentId);
            AgentType agentType = AgentType.valueOf(agent.getAgentType());
            if (agentType == AgentType.AGENT) {
                return findAvailableAgentProducts(contract.getAgentId());
            } else {
                return findInEffectDirectProducts();
            }
        }
    }

    public List<Product> findInEffectDirectProducts() {
        return productRepository.findInEffectDirectProducts();
    }

    public List<Product> findInEffectPublicAgentProducts() {
        return productRepository.findInEffectPublicAgentProducts();
    }

    public List<Product> findAvailableAgentProducts(String agentId) {
        //TODO: 列出所有的公共代理产品和所属代理商的专用代理产品
        return productRepository.findAvailableAgentProducts(agentId);
    }

    public List<Product> findAgentProducts(String agentId) {
        return productRepository.findAgentProducts(agentId);
    }


    public List<Product> findInEffectProducts() {
        return productRepository.findByStateOrderByDisplayNameAsc(CommonState.IN_EFFECT.toString());
    }

    List<Product> findByChargeSchemeId(String chargeSchemeId){
        return productRepository.findByChargeSchemeId(chargeSchemeId);
    }
}
