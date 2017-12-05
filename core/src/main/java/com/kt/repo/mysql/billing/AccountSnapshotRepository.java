package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.AccountSnapshot;
import com.kt.entity.mysql.billing.AccountSnapshotPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public interface AccountSnapshotRepository extends JpaRepository<AccountSnapshot, AccountSnapshotPrimaryKey> {
}
