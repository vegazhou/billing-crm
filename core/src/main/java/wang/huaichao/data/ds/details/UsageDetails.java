package wang.huaichao.data.ds.details;

import wang.huaichao.data.entity.crm.MeetingRecord;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/8/2016.
 */
public class UsageDetails {
    private BigDecimal cost;
    // key: hostName + siteName
    private Map<String, HostDetails> hostDetailsMap = new LinkedHashMap<>();

    public void add(MeetingRecord record) {
        String key = record.getHostName() + record.getSiteName();
        HostDetails hostDetails = hostDetailsMap.get(key);
        if (hostDetails == null) {
            hostDetails = new HostDetails();
            hostDetails.setHostName(record.getHostName());
            hostDetails.setUserName(record.getUserName());
            hostDetails.setSiteName(record.getSiteName());
            hostDetailsMap.put(key, hostDetails);
        }
        hostDetails.add(record);
    }

    public void calculate() {
        BigDecimal t = BigDecimal.ZERO;
        for (HostDetails hostDetails : hostDetailsMap.values()) {
            hostDetails.calculate();
            t = t.add(hostDetails.getCost());
        }
        cost = t;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Map<String, HostDetails> getHostDetailsMap() {
        return hostDetailsMap;
    }

    public void setHostDetailsMap(Map<String, HostDetails> hostDetailsMap) {
        this.hostDetailsMap = hostDetailsMap;
    }
}
