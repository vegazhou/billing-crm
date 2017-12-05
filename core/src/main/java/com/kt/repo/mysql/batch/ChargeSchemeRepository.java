package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.ChargeScheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface ChargeSchemeRepository extends JpaRepository<ChargeScheme, String>, ChargeSchemeRepositoryCustom {

    List<ChargeScheme> findByStateAndIsTemplateAndTypeInOrderByDisplayNameAsc(String state, int isTemplate, Collection<String> types);

}
