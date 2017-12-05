package wang.huaichao.data.ds;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 9/8/2016.
 */
public class HostUsage {
    private Map<String, HostOneSiteUsage> hostOneSiteUsageMap = new LinkedHashMap<>();
    private Set<String> siteNameSet = new HashSet<>();

    private int totalMinutes = 0;
    private int meetingCount = -1;
    private BigDecimal totalCost = BigDecimal.ZERO;

    public void addUsage(long confId, String hostName, String siteName, int minutes, BigDecimal cost) {
        totalCost = totalCost.add(cost);
        totalMinutes += minutes;
        siteNameSet.add(siteName);


        String key = hostName + siteName;

        HostOneSiteUsage hostOneSiteUsage = hostOneSiteUsageMap.get(key);

        if (hostOneSiteUsage == null) {
            hostOneSiteUsage = new HostOneSiteUsage();

            hostOneSiteUsage.setHostName(hostName);
            hostOneSiteUsage.setSiteName(siteName);

            hostOneSiteUsageMap.put(key, hostOneSiteUsage);
        }

        hostOneSiteUsage.add(confId, minutes, cost);
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getMeetingCount() {
        if (meetingCount == -1) {
            int t = 0;
            for (HostOneSiteUsage hostOneSiteUsage : hostOneSiteUsageMap.values()) {
                t += hostOneSiteUsage.getCount();
            }
            meetingCount = t;
        }
        return meetingCount;
    }

    public void setMeetingCount(int meetingCount) {
        //this.meetingCount = meetingCount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Map<String, HostOneSiteUsage> getHostOneSiteUsageMap() {
        return hostOneSiteUsageMap;
    }

    public void setHostOneSiteUsageMap(Map<String, HostOneSiteUsage> hostOneSiteUsageMap) {
        this.hostOneSiteUsageMap = hostOneSiteUsageMap;
    }

    public Set<String> getSiteNameSet() {
        return siteNameSet;
    }

    public void setSiteNameSet(Set<String> siteNameSet) {
        this.siteNameSet = siteNameSet;
    }
}
