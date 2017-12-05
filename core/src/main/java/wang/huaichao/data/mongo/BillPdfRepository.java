package wang.huaichao.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 9/20/2016.
 */
public interface BillPdfRepository extends MongoRepository<BillPdf, String> {
    BillPdf findByCustomerIdAndBillingPeriod(String customerId, int billingPeriod);

    List<BillPdf> findByBillingPeriod(int billingPeriod);

    List<BillPdf> findByBillingPeriodAndCustomerIdIn(int billingPeriod, List<String> ids);


}
