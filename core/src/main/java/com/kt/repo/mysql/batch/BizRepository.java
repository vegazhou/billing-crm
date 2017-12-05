package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Biz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface BizRepository extends JpaRepository<Biz, String>, BizRepositoryCustom  {
    List<Biz> findByStateAndIsTemplateOrderByDisplayNameAsc(String state, int isTemplate);
}
