package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TelephoneCallinNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface TelephoneCallinNumberRepository extends JpaRepository<TelephoneCallinNumberEntity, String>, TelephoneCallinNumberRepositoryCustom{
}
