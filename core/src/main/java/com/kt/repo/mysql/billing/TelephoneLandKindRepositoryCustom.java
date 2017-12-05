package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TelephoneLandKindEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface TelephoneLandKindRepositoryCustom{
    List<TelephoneLandKindEntity> getTelephoneLandKind(Date startDate, Date endDate);
}
