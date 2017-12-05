package com.kt.repo.edr;

import com.kt.biz.bean.KeyValueBean;
import com.kt.biz.bean.PortsUsageBean;
import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import com.kt.repo.edr.bean.*;
import com.kt.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by jianf on 2016/6/30.
 */
@Repository
public class EdrRepository {
    private static final Log logger = LogFactory.getLog(EdrRepository.class);


    @Autowired
    @Qualifier("edrDbHelper")
    private DbHelper webexDbHelper;

    @Autowired
    private DbHelper dbHelper;

    public WbxSite getWbxSiteBySiteName(String siteName){
        String querySQL = "select siteId, siteName from wbxsite where siteName = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(siteName);
        try {
            WbxSite site = webexDbHelper.getBean(querySQL, WbxSite.class, params.toArray());
            return site;
        } catch (SQLException e) {
            logger.error("dbHelper.execute() error:", e);
        }
        return null;
    }

    public WbxSite getWbxSiteBySiteId(int siteId){
        String querySQL = "select siteId, siteName from wbxsite where siteId = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(siteId);
        try {
            WbxSite site = webexDbHelper.getBean(querySQL, WbxSite.class, params.toArray());
            return site;
        } catch (SQLException e) {
            logger.error("dbHelper.execute() error:", e);
        }
        return null;
    }
    public List<WbxSite> getWbxSites(Set<String> siteNames){
        String querySQL = "select sitename, siteid from wbxsite where ";
        List<Object> params = new ArrayList<Object>();
        if(siteNames != null && siteNames.size() > 0){
            StringBuffer sbSiteParam = new StringBuffer();
            for(String s: siteNames){
                sbSiteParam.append("?,");
                params.add(s);
            }

            querySQL = querySQL + " sitename in ( " + sbSiteParam.substring(0, sbSiteParam.length()-1) + " )";
        }else{
            return null;
        }

        List<WbxSite> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, WbxSite.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<WbxSite>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

    public List<PortsOverflowDetail> getMeetingUserDetailsForPortsOverflow(Date startTime, Date endTime, Set<Integer> siteIds, Set<Integer> companyIds){
        String querySQL = " select r.starttime, r.endtime, r.duration, r.useremail, r.username, r.meetingType, r.confType, r.confName, r.confKey, r.objid, r.confId, r.siteId, r.siteName, m.hostName, m.hostEmail from xxrpt_hgsmeetinguserreport r , xxrpt_hgsmeetingreport m  " + // r.confid,
                " where m.siteid=r.siteid and m.confid=r.confid and m.webexid != 'wbxadmin' and r.endtime >= to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and r.starttime <= to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 ";

        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(strEndTime);
        params.add(strStartTime);

        if(siteIds != null && siteIds.size() > 0){
            StringBuffer sbSiteParam = new StringBuffer();
            for(Integer i: siteIds){
                sbSiteParam.append("?,");
                params.add(i);
            }

            querySQL = querySQL + " and r.siteid in ( " + sbSiteParam.substring(0, sbSiteParam.length()-1) + " )";
        }

        if(companyIds != null && companyIds.size() > 0){
            StringBuffer sbCompanyIdParam = new StringBuffer();
            for(Integer i: companyIds){
                sbCompanyIdParam.append("?,");
                params.add(i);
            }

            querySQL = querySQL + " and m.companyId in ( " + sbCompanyIdParam.substring(0, sbCompanyIdParam.length()-1) + " )";
        }

        List<PortsOverflowDetail> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, PortsOverflowDetail.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<PortsOverflowDetail>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

    public List<MeetingUserSummary> getMeetingUserList(Date startTime, Date endTime, int siteId, Set<Integer> companyIds){
        String querySQL = " select r.siteid, r.starttime, r.endtime, r.confid from xxrpt_hgsmeetinguserreport r  " + // r.confid,
                " inner join xxrpt_hgsmeetingreport m on m.siteid=r.siteid and m.confid=r.confid and m.webexid != 'wbxadmin' " +
                " where r.siteId = ? and r.endtime <= to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and r.starttime >= to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 ";

        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(siteId);
        params.add(strEndTime);
        params.add(strStartTime);

//        if(siteIds != null && siteIds.size() > 0){
//            StringBuffer sbSiteParam = new StringBuffer();
//            for(Integer i: siteIds){
//                sbSiteParam.append("?,");
//                params.add(i);
//            }
//
//            querySQL = querySQL + " and r.siteid in ( " + sbSiteParam.substring(0, sbSiteParam.length()-1) + " )";
//        }

        if(companyIds != null && companyIds.size() > 0){
            StringBuffer sbCompanyIdParam = new StringBuffer();
            for(Integer i: companyIds){
                sbCompanyIdParam.append("?,");
                params.add(i);
            }

            querySQL = querySQL + " and m.companyId in ( " + sbCompanyIdParam.substring(0, sbCompanyIdParam.length()-1) + " )";
        }

        List<MeetingUserSummary> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, MeetingUserSummary.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<MeetingUserSummary>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

    public int getMeetingUserCount(Date startTime, Date endTime, int siteId, Set<Integer> companyIds){
        String querySQL = " select count(1) as value from xxrpt_hgsmeetinguserreport r  " + // r.confid,
                " inner join xxrpt_hgsmeetingreport m on m.siteid=r.siteid and m.confid=r.confid and m.webexid != 'wbxadmin' " +
                " where r.siteId = ? and m.starttime between to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 ";

        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(siteId);
        params.add(strStartTime);
        params.add(strEndTime);

        if(companyIds != null && companyIds.size() > 0){
            StringBuffer sbCompanyIdParam = new StringBuffer();
            for(Integer i: companyIds){
                sbCompanyIdParam.append("?,");
                params.add(i);
            }

            querySQL = querySQL + " and m.companyId in ( " + sbCompanyIdParam.substring(0, sbCompanyIdParam.length()-1) + " )";
        }

        int count = -1;
        try {
            KeyValueBean bean = webexDbHelper.getBean(querySQL, KeyValueBean.class, params.toArray());
            if(bean != null && StringUtils.isNumeric(bean.getValue())){
                count = Integer.parseInt(bean.getValue());
            }
        } catch (SQLException e) {
            logger.error("dbHelper.execute() error:", e);
        }

        return count;
    }

    public List<BillDetail> getBillDetail(Set<Integer> siteIds, Date startTime, Date endTime){
        if(siteIds == null || siteIds.size() == 0){
            logger.error("No site set!");
            return null;
        }
        String sites = "";
        if(siteIds != null && siteIds.size() > 0){
            StringBuffer sbSiteParam = new StringBuffer();
            for(Integer i: siteIds){
                sbSiteParam.append( i + ",");
            }

            sites = sbSiteParam.substring(0, sbSiteParam.length()-1);
        }

        String querySQL = " select * from \n" +
                "(\n" +
                "select m.hostemail as hostname, m.confid, m.confname as confname,m.confkey as meetingNumber, e.starttime,e.endtime,\n" +
                "'0' as callingnum, '0' as callednum, 'VOIP' as calL_type, '' as Domestic_international_type,\n" +
                "ceil(((to_number( e.endtime - e.starttime )*24*60*60*1000 - 999)/60000)) as duration \n" +
                "from XXRPT_HGSMEETINGREPORT m \n" +
                "INNER join WBXEVENTLOGUSER e on e.siteid = m.siteid and e.confid=m.confid \n" +
                "where m.siteid in ( " + sites + " ) and m.webexid != 'wbxadmin' and e.sessiontype in (22, 12) and e.endtime between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24  \n" +
                "\n" +
                "union ALL\n" +
                "\n" +
                "select m.hostemail as hostname, m.confid, m.confname as confname, m.confkey as meetingNumber,a.starttime, a.endtime,\n" +
                "'0' as callingnum, '0' as callednum, 'DATA' as call_type, '' as Domestic_international_type, ceil(((to_number( a.endtime - a.starttime )*24*60*60*1000 - 999)/60000)) as duration\n" +
                "from xxrpt_hgsmeetingreport m \n" +
                "INNER join XXRPT_HGSMEETINGUSERREPORT a on a.siteid=m.siteid and a.confid=m.confid\n" +
                "where m.siteid in (" + sites + " )  and m.webexid != 'wbxadmin' and a.endtime between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 \n" +
                "\n" +
                "union ALL\n" +
                "\n" +
                "select  m.hostemail as hostname, case when c.has_data_meeting = 'Y' then c.meeting_id else c.pure_audio_conf_id end as confid, m.confname as confname, m.confkey as meetingNumber, \n" +
                "p.start_time as startime, p.end_time as endtime, \n" +
                "p.Orig_Country_Code||p.Orign_Area_Code||p.orign_number as callingnum,\n" +
                "case when p.access_number in  ('861058062088', '861058062036', '861064386066', '861064689121', '861084518118', '861058062000'  ) then '4008191212' else p.dest_country_code || p.dest_area_code ||p. phone_number end as callednum, \n" +
                "\n" +
                "case when p.is_dialout = 'N' then 'CALLIN' else 'CALLOUT' end as call_type, \n" +
                "case when p.calltype is not null then p.calltype when ( p.is_dialout='N' and p.Orig_Country_Code = '86') or ( p.is_dialout='Y' and p.dest_Country_Code = '86' ) then 'Domestic' else 'international' end as Domestic_international_type, \n" +
                "ceil(((to_number( p.end_time - p.start_time )*24*60*60*1000 - 999)/60000)) as duration\n" +
                "from cdr c \n" +
                "INNER join cdr_participants p on c.cdr_id=p.cdr_id and p.end_time between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 AND p.ORIGN_NUMBER <> 'mmp_video' AND p.PHONE_NUMBER IS NOT NULL \n" +
                "INNER join xxrpt_hgsmeetingreport m on c.site_id = m.siteid and m.webexid != 'wbxadmin' and  ( (c.has_data_meeting = 'Y' and c.meeting_id = m.confid ) or (c.has_data_meeting = 'N' and c.pure_audio_conf_id = m.confid) )\n" +
                "where c.site_id in (" + sites + " )\n" +
                "\n" +
                ") order by starttime ";

        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(strStartTime);
        params.add(strEndTime);
        params.add(strStartTime);
        params.add(strEndTime);
        params.add(strStartTime);
        params.add(strEndTime);

        List<BillDetail> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, BillDetail.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<BillDetail>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

    public List<TelephoneBillDetail> getTelephoneBillDetail(Set<Integer> siteIds, Date startTime, Date endTime) throws Exception{
        if(siteIds == null || siteIds.size() == 0){
            logger.error("No site set!");
            return null;
        }
        String sites = "";
        if(siteIds != null && siteIds.size() > 0){
            StringBuffer sbSiteParam = new StringBuffer();
            for(Integer i: siteIds){
                sbSiteParam.append( i + ",");
            }

            sites = sbSiteParam.substring(0, sbSiteParam.length()-1);
        }

        String querySQL = " select * from \n" +
                "(\n" +
                "select m.starttime as meeting_starttime, m.endtime as meeting_endtime, m.siteName, m.hostName, m.hostemail as hostEmail, m.confid, m.confkey as meetingNumber,m.confname as confname,e.starttime,e.endtime, null as privateTalkStarttime, null as privateTalkEndtime, \n" +
                "'0' as callingnum, '' as Orig_Country_Code, '' as Orign_Area_Code, '0' as callednum, '' as dest_country_code, '' as dest_area_code, '' as access_number, 'VOIP' as session_type, '' as calltype,\n" +
                "ceil(((to_number( e.endtime - e.starttime )*24*60*60*1000 - 999)/60000)) as duration, 0 as privateDuration, ceil(((to_number( e.endtime - e.starttime )*24*60*60*1000 - 999)/60000)) as totalDuration \n" +
                "from XXRPT_HGSMEETINGREPORT m \n" +
                "INNER join WBXEVENTLOGUSER e on e.siteid = m.siteid and e.confid=m.confid \n" +
                "where m.siteid in ( " + sites + " ) and m.webexid != 'wbxadmin' and e.sessiontype in (22, 12) and m.endtime between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24  \n" +
                "\n" +
                "union ALL\n" +
                "\n" +
                "select m.starttime as meeting_starttime, m.endtime as meeting_endtime, m.siteName, m.hostName, m.hostemail as hostEmail, m.confid, m.confkey as meetingNumber,m.confname as confname, a.starttime, a.endtime, null as privateTalkStarttime, null as privateTalkEndtime, \n" +
                "'0' as callingnum, '' as Orig_Country_Code, '' as Orign_Area_Code, '0' as callednum, '' as dest_country_code, '' as dest_area_code, '' as access_number, 'DATA' as session_type, '' as calltype, ceil(((to_number( a.endtime - a.starttime )*24*60*60*1000 - 999)/60000)) as duration, 0 as privateDuration, ceil(((to_number( a.endtime - a.starttime )*24*60*60*1000 - 999)/60000)) as totalDuration \n" +
                "from xxrpt_hgsmeetingreport m \n" +
                "INNER join XXRPT_HGSMEETINGUSERREPORT a on a.siteid=m.siteid and a.confid=m.confid \n" +
                "where m.siteid in (" + sites + " )  and m.webexid != 'wbxadmin' and m.endtime between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 \n" +
                "\n" +
                "union ALL\n" +
                "\n" +
                "select  m.starttime as meeting_starttime, m.endtime as meeting_endtime, m.siteName, m.hostName, m.hostemail as hostEmail, case when c.has_data_meeting = 'Y' then c.meeting_id else c.pure_audio_conf_id end as confid, m.confkey as meetingNumber, m.confname as confname, \n" +
                "p.start_time as startime, p.end_time as endtime, p.private_talk_starttime as privateTalkStarttime, p.private_talk_endtime as privateTalkEndtime, \n" +
                "p.Orig_Country_Code||p.Orign_Area_Code||p.orign_number as callingnum, p.Orig_Country_Code, p.Orign_Area_Code, \n" +
                "p.dest_country_code || p.dest_area_code || p. phone_number as callednum, p.dest_country_code, p.dest_area_code, p.access_number, \n" +
                "\n" +
                "case when p.is_dialout = 'N' then 'CALLIN' else 'CALLOUT' end as session_type, \n" +
                "case when p.is_dialout is not null and p.is_dialout <> 'N' and p.callType is null then 'Domestic' else p.callType end as callType, \n" +
//                "p.callType, \n" +
                "case when p.end_time is not null then ceil(((to_number( p.end_time - p.start_time )*24*60*60*1000 - 999)/60000)) else 0 end as duration, \n" +
                "case when p.private_talk_endtime is not null then ceil(((to_number( p.private_talk_endtime - p.private_talk_starttime )*24*60*60*1000 - 999)/60000)) else 0 end as privateDuration, \n" +
                "case when p.end_time is not null and p.private_talk_endtime is not null then ceil(((to_number( ( p.end_time - p.start_time + ( p.private_talk_endtime - p.private_talk_starttime ) ) )*24*60*60*1000 - 999)/60000)) \n" +
                "when p.end_time is not null then ceil(((to_number( p.end_time - p.start_time )*24*60*60*1000 - 999)/60000)) \n" +
                "else ceil(((to_number( p.private_talk_endtime - p.private_talk_starttime )*24*60*60*1000 - 999)/60000)) \n" +
                "end as totalDuration \n" +
                "from cdr c \n" +
                "INNER join cdr_participants p on c.cdr_id=p.cdr_id and ( (c.end_time is null and p.end_time between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24) OR (c.end_time is not null and c.end_time between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24) )  AND p.ORIGN_NUMBER <> 'mmp_video' AND p.PHONE_NUMBER IS NOT NULL \n" +
//                "INNER join cdr_participants p on c.cdr_id=p.cdr_id and (c.end_time between to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24 and to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 8/24) AND p.ORIGN_NUMBER <> 'mmp_video' AND p.PHONE_NUMBER IS NOT NULL \n" +
                "INNER join xxrpt_hgsmeetingreport m on c.site_id = m.siteid and m.webexid != 'wbxadmin' and ( (c.has_data_meeting = 'Y' and c.meeting_id = m.confid ) or (c.has_data_meeting = 'N' and c.pure_audio_conf_id = m.confid) )\n" +
                "where c.site_id in (" + sites + " )\n" +
                "\n" +
                ") order by meeting_starttime desc ";

        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(strStartTime);
        params.add(strEndTime);
        params.add(strStartTime);
        params.add(strEndTime);
        params.add(strStartTime);
        params.add(strEndTime);
        params.add(strStartTime);
        params.add(strEndTime);

        List<TelephoneBillDetail> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, TelephoneBillDetail.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<TelephoneBillDetail>();
            logger.error("dbHelper.execute() error:", e);
            throw new Exception("fail to get edr telephone data:", e);
        }

        return queryResult;
    }

    public List<EcMeetingDetail> getEcMeetingList(Set<Integer> siteIds, Date startTime, Date endTime){
        // on ec meeting, limited in 8 hours
       String querySQL =  "SELECT M.SITEID,M.SITENAME, M.CONFID, M.CONFNAME, M.STARTTIME, M.ENDTIME, M.HOSTID, M.HOSTNAME, M.HOSTEMAIL, M.DURATION, M.PEAKATTENDEE FROM XXRPT_HGSMEETINGREPORT M WHERE M.MEETINGTYPE='ONS' AND M.ENDTIME BETWEEN TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') - 8/24 AND TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') - 8/24 ";
        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(strStartTime);
        params.add(strEndTime);

        if(siteIds != null && siteIds.size() > 0){
            StringBuffer sbSiteParam = new StringBuffer();
            for(Integer i: siteIds){
                sbSiteParam.append("?,");
                params.add(i);
            }

            querySQL = querySQL + " AND M.SITEID IN ( " + sbSiteParam.substring(0, sbSiteParam.length()-1) + " )";
        }

        List<EcMeetingDetail> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, EcMeetingDetail.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<EcMeetingDetail>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

    public PortsUsageBean getPortsUsageSetting(String customerId, String siteName, Date startTime, Date endTime) throws Exception{
        String sql = "select o.pid as orderId, ws.siteName, a.attribute_value as orderPortsAmount, b.type as serviceType, o.customer_id as customerId \n" +
                "                from B_CHARGE_SCHEME_ATTRIBUTES a \n" +
                "                inner join B_CHARGE_SCHEME c on a.SCHEME_ID = c.ID and c.state='IN_EFFECT' and c.type='MONTHLY_PAY_BY_PORTS' \n" +
                "                inner join b_order o on o.charge_id = c.id and o.customer_id = ? and o.state='IN_EFFECT' \n" +
                "                and to_date(o.effectiveenddate, 'yyyy-mm-dd hh24:mi:ss') >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n" +
                "                and to_date(o.effectivestartdate, 'yyyy-mm-dd hh24:mi:ss') <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n" +
                "                inner join B_SITE_ORDER_RELATIONS sor on sor.order_id = o.pid -- and sor.is_draft=1 \n" +
                "                inner join B_WEBEX_SITES ws on ws.pid = sor.SITE_ID and ws.state='IN_EFFECT' and ws.sitename = ? \n" +
                "                inner join b_product p on p.pid = o.product_id inner join b_biz b on p.biz_id = b.id and b.STATE='IN_EFFECT' \n" +
                "                where a.attribute_name = 'PORTS_AMOUNT' and a.attribute_value > 0 ";

        List<Object> params = new ArrayList<Object>();
        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        params.add(customerId);
        params.add(strStartTime);
        params.add(strEndTime);
        params.add(siteName);

        try {
            PortsUsageBean portsUsageBean = dbHelper.getBean(sql, PortsUsageBean.class, params.toArray());
            if(portsUsageBean != null) {
                WbxSite site = this.getWbxSiteBySiteName(portsUsageBean.getSiteName());
                portsUsageBean.setSiteId(site.getSiteId());
            }
            return portsUsageBean;
        } catch (SQLException e) {
            logger.error("dbHelper.execute() error:", e);
            throw new Exception("Fail to get ports info!", e);
        }

    }

    public List<CmrPortsOverflowDetail> getMeetingUserDetailsForCmrPortsOverflow(Date startTime, Date endTime, int siteId) {
//        String querySQL = " select c.site_id as siteId, p.start_time as startTime, p.end_time as endTime, c.meeting_id as confid from cdr c \n" +
//                "inner join CDR_PARTICIPANTS p on p.cdr_id = c.cdr_id and p.call_mode = 5 and p.end_time >= to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and p.start_time < to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 \n" +
//                "where c.site_id = ? ";

        String querySQL = " select r.starttime, r.endtime, r.duration, r.useremail, r.objid, r.username, r.meetingType, r.confType, r.confName, r.confKey, r.confId, r.siteId, r.siteName, m.hostName, m.hostEmail from xxrpt_hgsmeetinguserreport r  \n" + // r.confid,
                " inner join xxrpt_hgsmeetingreport m on m.siteid=r.siteid and m.confid=r.confid and m.webexid != 'wbxadmin' \n" +
                " inner join wbxeventloguser u on u.teleinfo='TelePresence systems' and u.siteid=r.siteid and r.objid=u.objid \n" +
                " where r.siteid= ? and r.endtime >= to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and r.starttime <= to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 ";


        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(siteId);
        params.add(strStartTime);
        params.add(strEndTime);

        List<CmrPortsOverflowDetail> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, CmrPortsOverflowDetail.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<CmrPortsOverflowDetail>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

    public List<MeetingUserSummary> getMeetingCmrUserList(Date startTime, Date endTime, int siteId) {
//        String querySQL = " select c.site_id as siteId, p.start_time as startTime, p.end_time as endTime, c.meeting_id as confid from cdr c \n" +
//                "inner join CDR_PARTICIPANTS p on p.cdr_id = c.cdr_id and p.call_mode = 5 and p.end_time >= to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and p.start_time < to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 \n" +
//                "where c.site_id = ? ";

        String querySQL = "select r.siteid, r.starttime, r.endtime, r.confid from XXRPT_HGSMEETINGUSERREPORT r \n" +
                " inner join xxrpt_hgsmeetingreport m on m.siteid=r.siteid and m.confid=r.confid and m.webexid != 'wbxadmin' \n" +
                " inner join wbxeventloguser u on u.teleinfo='TelePresence systems' and u.siteid=r.siteid and r.objid=u.objid \n" +
                " where r.siteId = ? and r.endtime >= to_date(?, 'yyyy-MM-dd HH24:mi:ss')-8/24 and r.starttime < to_date(?, 'yyyy-MM-dd HH24:mi:ss') - 8/24 ";


        String strStartTime = DateUtils.format2DefaultFullTime(startTime);
        String strEndTime = DateUtils.format2DefaultFullTime(endTime);
        List<Object> params = new ArrayList<Object>();
        params.add(siteId);
        params.add(strStartTime);
        params.add(strEndTime);


        List<MeetingUserSummary> queryResult = null;
        try {
            queryResult = webexDbHelper.getBeanList(querySQL, MeetingUserSummary.class, params.toArray());

        } catch (SQLException e) {
            queryResult = new ArrayList<MeetingUserSummary>();
            logger.error("dbHelper.execute() error:", e);
        }

        return queryResult;
    }

}
