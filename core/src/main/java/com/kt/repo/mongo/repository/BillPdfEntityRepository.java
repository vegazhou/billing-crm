package com.kt.repo.mongo.repository;

import com.kt.repo.mongo.entity.BillPdfBean;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jianf on 2016/7/18.
 */
public interface BillPdfEntityRepository extends MongoRepository<BillPdfBean, String> {
}
