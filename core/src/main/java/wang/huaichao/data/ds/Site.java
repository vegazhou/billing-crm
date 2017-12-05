package wang.huaichao.data.ds;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 8/18/2016.
 */
public class Site {
    private String siteName;
    private int totalMinutes = 0;
    private int coverdMinutes = 0;
    private int unCoverdMinutes = 0;
    private BigDecimal cost = BigDecimal.ZERO;

    private List<PstnPool> pstnPools = new ArrayList<>();

    private Map<String, ChargeScheme> chargeSchemeMap = new HashMap<>();

    public int deductFromPstnPackage(Date callStartDate, Date callEndDate, int minutes) {
        int meetingMinutes = minutes;

        for (PstnPool pstnPool : pstnPools) {
            minutes = pstnPool.deduct(callStartDate, callEndDate, minutes);
            if (minutes == 0) break;
        }

        // site PSTN package usage statistics
        accumulate(meetingMinutes, minutes);

        return minutes;
    }

    private void accumulate(int total, int left) {
        totalMinutes += total;
        unCoverdMinutes += left;
        coverdMinutes += total - left;
    }

    public void addCost(BigDecimal cost) {
        this.cost = this.cost.add(cost);
    }

    public ChargeScheme getChargeSchemeByTime(Date date) {

        for (ChargeScheme chargeScheme : chargeSchemeMap.values()) {
            if (date.getTime() >= chargeScheme.getStartDate().getTime() &&
                    date.getTime() < chargeScheme.getEndDate().getTime()) {
                return chargeScheme;
            }
        }

        throw new RuntimeException("no charge scheme found for site " + siteName + " at " + date);
    }

    public void addPstnPool(PstnPool pstnPool) {
        pstnPools.add(pstnPool);
    }

    //===================================================================
    // getters & setters
    //===================================================================

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Map<String, ChargeScheme> getChargeSchemeMap() {
        return chargeSchemeMap;
    }

    public void setChargeSchemeMap(Map<String, ChargeScheme> chargeSchemeMap) {
        this.chargeSchemeMap = chargeSchemeMap;
    }

    public List<PstnPool> getPstnPools() {
        return pstnPools;
    }

    public void setPstnPools(List<PstnPool> pstnPools) {
        this.pstnPools = pstnPools;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getCoverdMinutes() {
        return coverdMinutes;
    }

    public void setCoverdMinutes(int coverdMinutes) {
        this.coverdMinutes = coverdMinutes;
    }

    public int getUnCoverdMinutes() {
        return unCoverdMinutes;
    }

    public void setUnCoverdMinutes(int unCoverdMinutes) {
        this.unCoverdMinutes = unCoverdMinutes;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
