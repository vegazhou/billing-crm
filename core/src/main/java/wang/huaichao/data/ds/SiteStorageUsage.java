package wang.huaichao.data.ds;

public class SiteStorageUsage {
    private double fee;
    private String siteName;
    private long orderSize;
    private long usedSize;
    private long overflowSize;
    private double price;

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public long getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(long orderSize) {
        this.orderSize = orderSize;
    }

    public long getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(long usedSize) {
        this.usedSize = usedSize;
    }

    public long getOverflowSize() {
        return overflowSize;
    }

    public void setOverflowSize(long overflowSize) {
        this.overflowSize = overflowSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
