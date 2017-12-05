package com.kt.biz.model;

import com.kt.entity.mysql.crm.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public interface ISchemeEntityAttributeRepository<T extends Attribute> extends JpaRepository<T, String> {

    List<T> findByEntityId(String id);

    void deleteByEntityId(String id);

    List<T> findByNameAndValue(String id, String value);

    List<T> findByNameAndValueIgnoreCase(String id, String value);

}
