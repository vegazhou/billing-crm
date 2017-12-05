package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TelephoneTollMappingEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface TelephoneTollMappingRepositoryCustom {
    List<TelephoneTollMappingEntity> getTelephoneTollMapping(Date startDate, Date endDate);
}
