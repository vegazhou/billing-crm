package wang.huaichao.data.ds;

/**
 * Created by Administrator on 9/18/2016.
 */
public class PortsUsageInfo {
    private String siteName;
    private int totalPorts;
    private int usedPorts;
    private float overflowFee;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getTotalPorts() {
        return totalPorts;
    }

    public void setTotalPorts(int totalPorts) {
        this.totalPorts = totalPorts;
    }

    public int getUsedPorts() {
        return usedPorts;
    }

    public void setUsedPorts(int usedPorts) {
        this.usedPorts = usedPorts;
    }

    public float getOverflowFee() {
        return overflowFee;
    }

    public void setOverflowFee(float overflowFee) {
        this.overflowFee = overflowFee;
    }
}
