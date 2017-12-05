package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface ProductRepository extends JpaRepository<Product, String>, ProductRepositoryCustom {

    List<Product> findByBizId(String bizId);

    List<Product> findByChargeSchemeId(String chargeSchemeId);

    List<Product> findByStateOrderByDisplayNameAsc(String state);
}
