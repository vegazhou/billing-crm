package wang.huaichao;

import com.kt.entity.mysql.crm.Customer;
import com.kt.repo.mysql.batch.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.entity.crm.CustomerToIgnore;
import wang.huaichao.data.repo.CustomerToIgnoreRepository;
import wang.huaichao.data.service.EmailService;
import wang.huaichao.exception.BillingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EmailServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }


    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerToIgnoreRepository customerToIgnoreRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test_sendPdfBill() throws Exception {
        // 上海理想信息产业（集团）有限公司
        String customerId = "f5ac0bc7-d990-407c-8629-b304578f59b0";

        customerId = "5921";

        emailService.sendPdfBill(customerId, 201612, "huaichao.wang@ketianyun.com");
    }

    @Test
    public void test_batchSendPdfBill() throws Exception {
        Future future = emailService.batchSendPdfBill(201608, "huaichao.wang@ketianyun.com");
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_batchSendPdfBillWithGivenCustomerIds() throws BillingException {
        List<String> lst = new ArrayList<>();
        lst.add("5fe9a738-b14c-462a-a3af-114d6733ef5a");
        Future future = emailService.batchSendPdfBill(lst, 201610, null, false);
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCustomerToIgnore() {
        CustomerToIgnore customerToIgnore = new CustomerToIgnore();
        customerToIgnore.setCustomerId("f5ac0bc7-d990-407c-8629-b304578f59b0");
        customerToIgnore.setType(CustomerToIgnore.CustomerToIgnoreType.EMAIL_PDF);
        customerToIgnoreRepository.save(customerToIgnore);
    }

    @Test
    public void getEmailCustomers() {
        int billingPeriod = 201706;
        List<String> mailingCustomers = emailService.getMailingCustomers(billingPeriod);
        List<Customer> all = customerRepository.findAll();
        Map<String, Customer> map = new HashMap<>();
        for (Customer customer : all) {
            map.put(customer.getPid(), customer);
        }
        for (String mailingCustomer : mailingCustomers) {
            Customer customer = map.get(mailingCustomer);
            if (customer != null) {
                System.out.println(customer.getDisplayName() + "," + customer.getPid());
            } else {
                System.out.println("err,err");
            }
        }
        System.out.println("total customers: " + mailingCustomers.size());
    }
}
