package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface CustomerRepository extends JpaRepository<Customer, String>, CustomerRepositoryCustom {
    @Query( value = " select c.* from b_customer c inner join b_webex_sites s on s.CUSTOMER_ID = c.pid and s.SITENAME = ?1 ", nativeQuery = true )
    Customer findFirstCustomerBySiteName(String siteName);
    Customer findFirstCustomerByCode(String code);

    List<Customer> findByIsVat(int isVat);

    List<Customer> findByIsVatAndVatNo(int isVat, String vatNo);

    List<Customer> findByIsRel(int isRel);

    Customer findByDisplayNameLike(String displayName);
}
