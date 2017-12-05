package com.kt.repo.mongo.repository;

import com.kt.repo.mongo.entity.ContractPdfBean;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Garfield Chen on 2016/9/28.
 */
public interface ContractPdfEntityRepository extends MongoRepository<ContractPdfBean, String> {
	
	ContractPdfBean findByContractIdAndId(String contractId, String Id);
	ContractPdfBean findById(String Id);
}
