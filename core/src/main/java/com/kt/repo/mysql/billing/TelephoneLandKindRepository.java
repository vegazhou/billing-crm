package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TelephoneCallinNumberEntity;
import com.kt.entity.mysql.billing.TelephoneLandKindEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface TelephoneLandKindRepository extends JpaRepository<TelephoneLandKindEntity, String>, TelephoneLandKindRepositoryCustom {
}
