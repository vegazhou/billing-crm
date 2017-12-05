package wang.huaichao.data.ds;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/22/2016.
 */
public class SitePortsOverflowStatistics {
    private String siteName;
    private Date peakTime;
    private int peakNum;
    private Map<Long, ConfrencePortsOverflowStatistics> confrencePortsOverflowStatisticsMap = new HashMap<>();


    public void add(long confId, String confName, Date peakTime,
                    int peakNum, String userName, String userEmail,
                    int meetingPeakNum) {
        ConfrencePortsOverflowStatistics statistics = confrencePortsOverflowStatisticsMap.get(confId);

        if (statistics != null) return;

        if (this.peakTime == null) {
            this.peakTime = new Date(peakTime.getTime() - 28800000);
            this.peakNum = peakNum;
        } else {
            this.peakNum += peakNum;
        }

        statistics = new ConfrencePortsOverflowStatistics();

        statistics.setPeakTime(peakTime);
        statistics.setUserEmail(userEmail);
        statistics.setUserName(userName);
        statistics.setAttendees(meetingPeakNum);
        statistics.setConfName(confName);

        confrencePortsOverflowStatisticsMap.put(confId, statistics);
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Map<Long, ConfrencePortsOverflowStatistics> getConfrencePortsOverflowStatisticsMap() {
        return confrencePortsOverflowStatisticsMap;
    }

    public void setConfrencePortsOverflowStatisticsMap(Map<Long, ConfrencePortsOverflowStatistics> confrencePortsOverflowStatisticsMap) {
        this.confrencePortsOverflowStatisticsMap = confrencePortsOverflowStatisticsMap;
    }

    public Date getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(Date peakTime) {
        this.peakTime = peakTime;
    }

    public int getPeakNum() {
        return peakNum;
    }

    public void setPeakNum(int peakNum) {
        this.peakNum = peakNum;
    }
}
