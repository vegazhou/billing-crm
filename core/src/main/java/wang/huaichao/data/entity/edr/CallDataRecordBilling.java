package wang.huaichao.data.entity.edr;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 8/24/2016.
 */
public abstract class CallDataRecordBilling {
    private int duration = -1;
    private int coveredMinutes = -1;
    private int uncoverdMinutes = -1;
    private BigDecimal rate = BigDecimal.ZERO;
    private BigDecimal cost = BigDecimal.ZERO;

    public abstract Date getEndTime();

    public abstract Date getStartTime();

    public abstract boolean IsInternationalCall();

    public abstract String GetUserNumber();

    public abstract String GetPlatformNumber();

    public int getDuration() {
        if (duration == -1) {
            duration = (int) (getEndTime().getTime() - getStartTime().getTime() + 59999) / 60000;
        }
        return duration;
    }

    public int getCoveredMinutes() {
        if (coveredMinutes == -1) throw new RuntimeException("coveredMinutes not initialized");
        return coveredMinutes;
    }

    public void setCoveredMinutes(int coveredMinutes) {
        this.coveredMinutes = coveredMinutes;
    }

    public int getUncoverdMinutes() {
        if (uncoverdMinutes == -1) throw new RuntimeException("uncoverdMinutes not initialized");
        return uncoverdMinutes;
    }

    public void setUncoverdMinutes(int uncoverdMinutes) {
        this.uncoverdMinutes = uncoverdMinutes;
        this.coveredMinutes = getDuration() - uncoverdMinutes; // ===
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
        this.cost = rate.multiply(new BigDecimal(getUncoverdMinutes()));
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
