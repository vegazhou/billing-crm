package wang.huaichao.data.ds;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/8/2016.
 */
public class ProductUsage {
    private BigDecimal totalFee = BigDecimal.ZERO;

    private Map<String, ProductMonthlyFee> productMonthlyFeeMap = new HashMap<>();

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        //this.totalFee = totalFee;
    }

    public Map<String, ProductMonthlyFee> getProductMonthlyFeeMap() {
        return productMonthlyFeeMap;
    }

    public void setProductMonthlyFeeMap(Map<String, ProductMonthlyFee> productMonthlyFeeMap) {
        this.productMonthlyFeeMap = productMonthlyFeeMap;

        BigDecimal t = BigDecimal.ZERO;

        for (ProductMonthlyFee fee : productMonthlyFeeMap.values()) {
            t = t.add(fee.getCost());
        }

        totalFee = t;
    }
}
