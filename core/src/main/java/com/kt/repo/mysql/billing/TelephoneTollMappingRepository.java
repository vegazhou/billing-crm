package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TelephoneTollMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface TelephoneTollMappingRepository extends JpaRepository<TelephoneTollMappingEntity, String>, TelephoneTollMappingRepositoryCustom {
}
