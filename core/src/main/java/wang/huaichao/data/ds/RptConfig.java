package wang.huaichao.data.ds;

import wang.huaichao.data.ds.details.UsageDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/6/2016.
 */
public class RptConfig {
    private String customerId;
    private String customerCode;


    private String payeeName;           // 收款方 名
    private String payeeBankName;       // 收款方 开户银行名称
    private String payeeBankNo;         // 收款方 银行账号
    private String payeeAddress;        // 收款方 公司地址

    private String billNo;              // 账单 号码
    private String startTime;           // 账单 开始时间
    private String endTime;             // 账单 结束时间
    private String billDate;            // 账单 日期

    private String payerName;           // 付款方 名（客户名）
    private String payerContactName;    // 付款方 联系人
    private String payerNo;             // 付款方 编号（客户编号）

    // fees
    private float productFee;
    private float unpaidProductFee;

    private float pstnFee;
    private float unpaidPstnFee;

    private float prepaid; // yu fu
    private float deposit; // yu cun

    // host usages
    private HostUsage pstnHostUsage;
    private HostUsage dataHostUsage;
    private ProductUsage productUsage;
    private UsageDetails usageDetails;


    private Map<String, PortsUsageInfo> siteFeeMap = new HashMap<>();
    Map<String, SitePortsOverflowStatistics> sitePortsOverflowStatisticsMap = new HashMap<>();

    // ports overflow fee
    private float overflowFee = 0;

    private double storageMonthlyFee = 0;
    private Map<String, SiteStorageUsage> siteStorageUsageFee = new HashMap<>();
    // storage overflow fee
    private double storageOverflowFee = 0;


    // =======================================================
    public float getTotalProductFee() {
        return productFee + overflowFee;
    }

    public float getTotalProductPrepayFee() {
        return productFee - unpaidProductFee;
    }

    public float getTotalUnpaidFee() {
        float pacc = prepaid;
        float dacc = deposit;
        float overFlowAndPstnFeeToPay;
        float productFeeToPay;

        float overFlowAndPstnFee = overflowFee + pstnFee +
            (float) storageOverflowFee;

        if (overFlowAndPstnFee > dacc) {
            overFlowAndPstnFeeToPay = overFlowAndPstnFee - dacc;
            dacc = 0;
        } else {
            overFlowAndPstnFeeToPay = 0;
            dacc = dacc - overFlowAndPstnFee;
        }

        if (productFee + storageMonthlyFee > pacc + dacc) {
            productFeeToPay = productFee - pacc - dacc +
                (float) storageMonthlyFee;
        } else {
            productFeeToPay = 0;
        }

        return overFlowAndPstnFeeToPay + productFeeToPay;
    }
    // =======================================================

    public float getOverflowFee() {
        return overflowFee;
    }

    public float getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(float prepaid) {
        this.prepaid = prepaid;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public Map<String, PortsUsageInfo> getSiteFeeMap() {
        return siteFeeMap;
    }

    public void setSiteFeeMap(Map<String, PortsUsageInfo> siteFeeMap) {
        this.siteFeeMap = siteFeeMap;

        // calculate overflow fee
        float val = 0;
        for (PortsUsageInfo value : siteFeeMap.values()) {
            val += value.getOverflowFee();
        }
        overflowFee = val;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeBankName() {
        return payeeBankName;
    }

    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }

    public String getPayeeBankNo() {
        return payeeBankNo;
    }

    public void setPayeeBankNo(String payeeBankNo) {
        this.payeeBankNo = payeeBankNo;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerNo() {
        return payerNo;
    }

    public void setPayerNo(String payerNo) {
        this.payerNo = payerNo;
    }

    public float getProductFee() {
        return productFee;
    }

    public void setProductFee(float productFee) {
        this.productFee = productFee;
    }

    public float getUnpaidProductFee() {
        return unpaidProductFee;
    }

    public void setUnpaidProductFee(float unpaidProductFee) {
        this.unpaidProductFee = unpaidProductFee;
    }

    public float getPstnFee() {
        return pstnFee;
    }

    public void setPstnFee(float pstnFee) {
        this.pstnFee = pstnFee;
    }

    public float getUnpaidPstnFee() {
        return unpaidPstnFee;
    }

    public void setUnpaidPstnFee(float unpaidPstnFee) {
        this.unpaidPstnFee = unpaidPstnFee;
    }

    public String getPayerContactName() {
        return payerContactName;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public void setPayerContactName(String payerContactName) {
        this.payerContactName = payerContactName;
    }

    public HostUsage getPstnHostUsage() {
        return pstnHostUsage;
    }

    public void setPstnHostUsage(HostUsage pstnHostUsage) {
        this.pstnHostUsage = pstnHostUsage;
    }

    public HostUsage getDataHostUsage() {
        return dataHostUsage;
    }

    public ProductUsage getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(ProductUsage productUsage) {
        this.productUsage = productUsage;
    }

    public void setDataHostUsage(HostUsage dataHostUsage) {
        this.dataHostUsage = dataHostUsage;
    }

    public UsageDetails getUsageDetails() {
        return usageDetails;
    }

    public void setUsageDetails(UsageDetails usageDetails) {
        this.usageDetails = usageDetails;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Map<String, SitePortsOverflowStatistics> getSitePortsOverflowStatisticsMap() {
        return sitePortsOverflowStatisticsMap;
    }

    public void setSitePortsOverflowStatisticsMap(Map<String, SitePortsOverflowStatistics> sitePortsOverflowStatisticsMap) {
        this.sitePortsOverflowStatisticsMap = sitePortsOverflowStatisticsMap;
    }

    // storage fee

    public Map<String, SiteStorageUsage> getSiteStorageUsageFee() {
        return siteStorageUsageFee;
    }

    public void setSiteStorageUsageFee(Map<String, SiteStorageUsage> siteStorageUsageFee) {
        this.siteStorageUsageFee = siteStorageUsageFee;
        double fee = 0;
        for (SiteStorageUsage siteStorageUsage : siteStorageUsageFee.values()) {
            fee += siteStorageUsage.getFee();
        }
        storageOverflowFee = fee;
    }

    public double getStorageOverflowFee() {
        return storageOverflowFee;
    }

    public double getStorageMonthlyFee() {
        return storageMonthlyFee;
    }

    public void setStorageMonthlyFee(double storageMonthlyFee) {
        this.storageMonthlyFee = storageMonthlyFee;
    }

    public double getTotalStorageFee() {
        return storageMonthlyFee + storageOverflowFee;
    }
}
