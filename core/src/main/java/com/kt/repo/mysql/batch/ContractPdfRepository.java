package com.kt.repo.mysql.batch;

import java.util.List;

import com.kt.entity.mysql.billing.ContractPdf;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Garfield Chen on 2016/9/28.
 */
public interface ContractPdfRepository extends JpaRepository<ContractPdf, String> {
	List<ContractPdf> findByContractId(String contractId);
}
