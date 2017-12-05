package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Product;
import com.kt.service.SearchCriteria;
import com.kt.service.SearchFilter;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> findAllProductsByPage(int curPage, SearchCriteria searchorguser);
    
    Page<Product> findProductsForAgent(int curPage, SearchFilter searchorguser);

    List<Product> findInEffectDirectProducts();

    List<Product> findAvailableAgentProducts(String agentId);

    List<Product> findInEffectPublicAgentProducts();

    List<Product> findAgentProducts(String agentId);

    List<Product> findInEffectAgentProduct(String agentId, String publicAgentProductId);
}
