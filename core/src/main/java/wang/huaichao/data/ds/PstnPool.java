package wang.huaichao.data.ds;

import wang.huaichao.data.entity.crm.PstnPackage;

import java.util.Date;

/**
 * Created by Administrator on 8/19/2016.
 */
public class PstnPool {
    private String packageId;
    private String orderId;
    private Date startDate;
    private Date endDate;
    private int availableMinutes;
    private int totalMinutes;

    private PstnPackage pstnPackage;

    // return minutes left
    public int deduct(Date callStartDate, Date callEndDate, int minutes) {
        if (availableMinutes == 0) return minutes;

        // have intersection
        if (callStartDate.getTime() < endDate.getTime() && callEndDate.getTime() >= startDate.getTime()) {
            if (minutes <= availableMinutes) {
                availableMinutes -= minutes;
                return 0;
            } else {
                int left = minutes - availableMinutes;
                availableMinutes = 0;
                return left;
            }
        }
        return minutes;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getAvailableMinutes() {
        return availableMinutes;
    }

    public void setAvailableMinutes(int availableMinutes) {
        this.availableMinutes = availableMinutes;
        this.totalMinutes = availableMinutes;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public PstnPackage getPstnPackage() {
        return pstnPackage;
    }

    public void setPstnPackage(PstnPackage pstnPackage) {
        this.pstnPackage = pstnPackage;
    }
}
