package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface OrderRepository extends JpaRepository<Order, String>, OrderRepositoryCustom {

    List<Order> findByContractId(String id);

    Order findOneByChargeId(String chargeId);

    void deleteByContractId(String id);

    @Query(value = "select o from Order o where o.state = ?1 and " +
            "((o.effectiveStartDate like ?2% and o.provisionState IS NULL) or " +
            "(o.effectiveEndDate like ?2% and o.terminateState IS NULL)) order by contractId")
    List<Order> findOrderByStateAndDateOrderByContractId(String orderState, String date);

    @Query(value = "select o from Order o where o.state = ?1 and " +
            "((o.effectiveStartDate like ?2% and o.provisionState IS NULL) or " +
            "(o.effectiveEndDate like ?2% and o.terminateState IS NULL)) order by placedDate asc")
    List<Order> findOrderByStateAndDateOrderByPlacedDate(String orderState, String date);

    @Query(value = "select o from Order o where o.state = ?1 and " +
            "((o.effectiveStartDate like ?2% and o.provisionState IS NULL) or " +
            "(o.effectiveEndDate like ?2% and o.terminateState IS NULL)) and o.productId = ?3 order by customerId asc, placedDate asc")
    List<Order> findOrderByStateAndDateAndProductIdOrderByCustomerId(String orderState, String date, String productId);

    @Query(value = "select o from Order o where o.state = ?1 and o.customerId= ?2 and " +
            "o.productId = ?3 order by placedDate desc")
    List<Order> findOrderByStateAndCustomerIdAndProductIdOrderByPlacedDate(String orderState, String customerId, String productId);

    List<Order> findByStateAndCustomerIdAndProductIdInOrderByPlacedDateDesc(String orderState, String customerId, Collection<String> productId);

    List<Order> findByStateAndEffectiveEndDateOrderByCustomerIdDesc(String orderState, String date);

}
