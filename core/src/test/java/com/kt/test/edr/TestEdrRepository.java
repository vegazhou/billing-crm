package com.kt.test.edr;

import com.kt.exception.InvalidDateFormatException;
import com.kt.exception.WafException;
import com.kt.repo.edr.EdrRepository;
import com.kt.repo.edr.bean.MeetingUserSummary;
import com.kt.repo.edr.bean.MeetingUserTime;
import com.kt.util.DateUtil;
import com.kt.util.DateUtils;
import com.kt.util.SortUtil;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.*;

/**
 * Created by jianf on 2016/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestEdrRepository {
    private static final Logger LOGGER = Logger.getLogger(TestEdrRepository.class);
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }
    @Autowired
    EdrRepository reportRepository;

    @Test
    public void getMeetingUserList() throws WafException {
        String startTime = "2016-06-01 00:00:00";
        String endTime = "2016-07-01 00:00:00";;
        Date now = new Date();
        Date startDate = DateUtils.parseDate(startTime);
        Date endDate = DateUtils.parseDate(endTime);
//        Set<Integer> set = new HashSet<Integer>();
//        set.add(22122);
//        set.add(23122);
        int customerSiteId = 22122;
        List<MeetingUserSummary> meetingUserList = reportRepository.getMeetingUserList(startDate, endDate, customerSiteId, null);
        List<MeetingUserTime> meetingUserTime = new ArrayList<MeetingUserTime>();

        for(MeetingUserSummary s: meetingUserList){
            MeetingUserTime userStartTime = new MeetingUserTime();
            MeetingUserTime userEndTime = new MeetingUserTime();
            userEndTime.setDate(s.getEndTime());
            userEndTime.setFlag(-1);
            userEndTime.setSiteId(s.getSiteId());

            userStartTime.setDate(s.getStartTime());
            userStartTime.setFlag(1);
            userStartTime.setSiteId(s.getSiteId());

            meetingUserTime.add(userStartTime);
            meetingUserTime.add(userEndTime);

        }
        LOGGER.debug(meetingUserTime.size());
        SortUtil.anyProperSort(meetingUserTime, "date", true);

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Map<Integer, Integer> maxMap = new HashMap<Integer, Integer>();
        for(MeetingUserTime t: meetingUserTime){
//            LOGGER.debug(ToStringBuilder.reflectionToString(t));
            int siteId = t.getSiteId();
            int flag = t.getFlag();
            Integer ports = map.get(siteId);
            int currentPost = 0;
            if(ports == null){
                currentPost = (flag>0 ? flag:0);
                map.put(siteId, currentPost);
            }else{
                currentPost = flag + ports;
                map.put(siteId, currentPost);
            }

            Integer maxPorts = maxMap.get(siteId);
            if(maxPorts == null){
                maxMap.put(siteId, currentPost);
            }else{
                maxMap.put(siteId, currentPost > maxPorts ? currentPost:maxPorts);
            }
        }

        LOGGER.debug("Meeting Site count:" + maxMap.size());
        LOGGER.debug("Meeting Site count:" + maxMap.size());
        for(Integer s: maxMap.keySet()){
            LOGGER.debug("Site ID:" + s + "; ports:" + maxMap.get(s));
        }
        long seconds = (new Date().getTime() - now.getTime() ) /1000;
        LOGGER.debug("Cost time:" + seconds + (" second(s)!"));
    }

    @Test
    public void getMeetingUserCount(){
        Set companyId = new HashSet();
        companyId.add(1);
        int count = reportRepository.getMeetingUserCount(DateUtils.parseDate("2016-04-01 00:00:00"),DateUtils.parseDate("2016-04-31 00:00:00"),21327, companyId);
        LOGGER.debug("count:" + count);
    }
}

