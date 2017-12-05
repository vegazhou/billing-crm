package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/8.
 */
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long>, AccountOperationRepositoryCustom {
}
