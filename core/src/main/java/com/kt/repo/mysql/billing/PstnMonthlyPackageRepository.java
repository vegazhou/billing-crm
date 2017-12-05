package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.PstnMonthlyPackage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/8.
 */
public interface PstnMonthlyPackageRepository extends JpaRepository<PstnMonthlyPackage, Long>, PstnMonthlyPackageRepositoryCustom {
}
