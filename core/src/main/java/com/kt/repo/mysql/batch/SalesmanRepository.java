package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Salesman;
import com.kt.entity.mysql.crm.WebExSite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/1.
 */
public interface SalesmanRepository extends JpaRepository<Salesman, String>, SalesmanRepositoryCustom  {

    List<Salesman> findByEnabledOrderByNameAsc(int enabled);

    List<Salesman> findByEmail(String email);
}
