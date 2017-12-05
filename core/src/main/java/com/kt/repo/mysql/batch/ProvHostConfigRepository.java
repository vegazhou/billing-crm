package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.batch.ProvHostConfig;
import com.kt.entity.mysql.batch.WbxProvTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvHostConfigRepository extends JpaRepository<ProvHostConfig, String> {

    List<ProvHostConfig> findByCustomerIdAndServiceNameAndAttendeeCapacityAndTelTypeAndProductType(String customerId,
                                                                                     String serviceName,
                                                                                     String attendeeCapacity,
                                                                                     String telType,
                                                                                     String productType);

    List<ProvHostConfig> findByCustomerIdAndServiceNameOrderByAttendeeCapacityAsc(String customerId,
                                                                                  String serviceType);

    List<ProvHostConfig> findByCustomerIdAndServiceNameAndAttendeeCapacity(String customerId,
                                                                           String serviceType,
                                                                           String attendeeCapacity);

    List<ProvHostConfig> findBySiteName(String siteName);

    List<ProvHostConfig> findByProductType(String productType);

}
