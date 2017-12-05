package wang.huaichao.data.ds;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 9/7/2016.
 */
public class HostOneSiteUsage {
    private String hostName;
    private String siteName;
    private int minutes = 0;
    private BigDecimal fee = BigDecimal.ZERO;

    private Set<Long> confs = new HashSet<>();

    public void add(long confId, int minutes, BigDecimal cost) {
        confs.add(confId);
        this.minutes += minutes;
        fee = fee.add(cost);
    }

    public int getCount() {
        return confs.size();
    }

    // ==========================================================

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
