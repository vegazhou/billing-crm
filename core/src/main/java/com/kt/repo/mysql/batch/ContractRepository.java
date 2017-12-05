package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Contract;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface ContractRepository extends JpaRepository<Contract, String>, ContractRepositoryCustom  {

    Contract findFirstByCustomerId(String customerId);

    List<Contract> findByCustomerId(String customerId);

    Contract findFirstBySalesman(String salesmanId);

    List<Contract> findByCustomerIdAndState(String customerId, String state);

    List<Contract> findByAgentId(String agentId);

    List<Contract> findByDisplayNameLike(String pattern);
}
