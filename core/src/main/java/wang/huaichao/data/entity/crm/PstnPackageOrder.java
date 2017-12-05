package wang.huaichao.data.entity.crm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/18/2016.
 */
public class PstnPackageOrder {
    private String customerId;
    private String orderId;
    private String chargeSchemaId;
    private Date startDate;
    private Date endDate;
    private PackageType packageType;

    private int months;
    private int minitues;
    private List<String> sites = new ArrayList<>();

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChargeSchemaId() {
        return chargeSchemaId;
    }

    public void setChargeSchemaId(String chargeSchemaId) {
        this.chargeSchemaId = chargeSchemaId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getMinitues() {
        return minitues;
    }

    public void setMinitues(int minitues) {
        this.minitues = minitues;
    }

    public List<String> getSites() {
        return sites;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }

    public static enum PackageType {
        PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES,
        PSTN_MONTHLY_PACKET,
        PSTN_SINGLE_PACKET_FOR_ALL_SITES
    }
}
