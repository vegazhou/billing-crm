package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.batch.WbxProvTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WbxProvTaskRepository extends JpaRepository<WbxProvTask, String> {

    List<WbxProvTask> findByOrderId(String orderId);

    List<WbxProvTask> findByContractId(String contractId);

    List<WbxProvTask> findBySiteName(String siteName);

    List<WbxProvTask> findByTypeAndStateOrderByCreateTimeAsc(String type, String state);

    List<WbxProvTask> findByTypeAndSiteNameAndHostEmailIgnoreCaseOrderByCreateTimeDesc(String type, String siteName, String hostEmail);

}
