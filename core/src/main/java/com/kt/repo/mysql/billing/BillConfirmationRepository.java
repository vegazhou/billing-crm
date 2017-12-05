package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BillConfirmation;
import com.kt.entity.mysql.billing.BillConfirmationPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public interface BillConfirmationRepository extends JpaRepository<BillConfirmation, BillConfirmationPrimaryKey> {
}
