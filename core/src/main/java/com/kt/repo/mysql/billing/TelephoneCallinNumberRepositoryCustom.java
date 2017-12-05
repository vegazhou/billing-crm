package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TelephoneCallinNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface TelephoneCallinNumberRepositoryCustom{
    List<TelephoneCallinNumberEntity> getTelephoneCallinNumber(Date startDate, Date endDate);
}
