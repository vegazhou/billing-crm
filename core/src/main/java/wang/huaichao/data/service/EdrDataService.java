package wang.huaichao.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import wang.huaichao.data.entity.edr.CallDataRecord;
import wang.huaichao.data.mapper.PstnRecordMapper;
import wang.huaichao.utils.FileUtils;
import wang.huaichao.utils.StringUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 8/16/2016.
 */
@Service
public class EdrDataService {
    @Autowired
    @Qualifier("edrJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;


    public List<CallDataRecord> findPstnRecords(Date start, Date end, List<String> siteNames) throws IOException, ParseException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/edr/find-pstn-usage.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startTime", start);
        params.addValue("endTime", end);
        params.addValue("siteNames", siteNames);

        List<CallDataRecord> records = jdbcTemplate.query(sql, params, new RowMapper<CallDataRecord>() {
            @Override
            public CallDataRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                CallDataRecord callDataRecord = new CallDataRecord();

                callDataRecord.setSiteName(resultSet.getString("siteName"));
                callDataRecord.setUserName(_convertCharset(resultSet.getString("userName")));
                callDataRecord.setHostName(resultSet.getString("hostName"));
                callDataRecord.setConfId(resultSet.getLong("confId"));
                callDataRecord.setConfName(_convertCharset(resultSet.getString("confName")));
                callDataRecord.setStartTime(resultSet.getTimestamp("startTime"));
                callDataRecord.setEndTime(resultSet.getTimestamp("endTime"));
                callDataRecord.setOrignCountryCode(resultSet.getString("orignCountryCode"));
                callDataRecord.setOriginAreaCode(resultSet.getString("originAreaCode"));
                callDataRecord.setOrigNumber(resultSet.getString("origNumber"));
                callDataRecord.setDestCountryCode(resultSet.getString("destCountryCode"));
                callDataRecord.setDestAreaCode(resultSet.getString("destAreaCode"));
                callDataRecord.setDestNumber(resultSet.getString("destNumber"));
                callDataRecord.setAccessNumber(resultSet.getString("accessNumber"));
                callDataRecord.setCallIn(resultSet.getBoolean("callIn"));
                callDataRecord.setCallType(resultSet.getString("callType"));

                return callDataRecord;
            }
        });

        return records;
    }

    private static String _convertCharset(String edrString) {
        return StringUtils.fromCharset(edrString, "iso8859-1");
    }

    public List<CallDataRecord> findVoipAndDataRecords(Date start, Date end, List<String> siteNames)
            throws IOException {
        return _findVoipOrDataMeetingRecords(start, end, siteNames,
                "/wang/huaichao/edr/find-voip-and-data-usage.sql");
    }

    private List<CallDataRecord> _findVoipOrDataMeetingRecords(Date start,
                                                               Date end,
                                                               List<String> siteNames,
                                                               String sqlFile) throws IOException {

        if (siteNames == null || siteNames.size() == 0) return new ArrayList<>(0);

        String sql = FileUtils.readFromClassPath(sqlFile);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startTime", start);
        params.addValue("endTime", end);
        params.addValue("siteNames", siteNames);

        return jdbcTemplate.query(sql, params, voipAndDataMapper);
    }

    public List<CallDataRecord> findVoipRecords(Date start, Date end, List<String> siteNames)
            throws IOException {
        return _findVoipOrDataMeetingRecords(start, end, siteNames,
                "/wang/huaichao/edr/find-voip-usage.sql");
    }

    public List<CallDataRecord> findDataRecords(Date start, Date end, List<String> siteNames)
            throws IOException {
        return _findVoipOrDataMeetingRecords(start, end, siteNames,
                "/wang/huaichao/edr/find-data-usage.sql");
    }


    public Map<String, Integer> findSiteIdSiteNameMapBySiteNamesIn(List<String> siteNames) {
        String sql = "select siteid,sitename from wbxsite where sitename in (:siteNames)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("siteNames", siteNames);

        final Map<String, Integer> siteNameSiteIdMap = new HashMap<>();
        jdbcTemplate.query(sql, params, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                siteNameSiteIdMap.put(rs.getString("sitename"), rs.getInt("siteid"));
                return null;
            }
        });
        return siteNameSiteIdMap;
    }

    public Map<String, Integer> findSiteIdSiteNameMap() {
        String sql = "select siteid,sitename from wbxsite";

        final Map<String, Integer> siteNameSiteIdMap = new HashMap<>();
        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                siteNameSiteIdMap.put(rs.getString("sitename"), rs.getInt("siteid"));
                return null;
            }
        });

        return siteNameSiteIdMap;
    }


    private static final RowMapper<CallDataRecord> voipAndDataMapper = new RowMapper<CallDataRecord>() {
        @Override
        public CallDataRecord mapRow(ResultSet resultSet, int i) throws SQLException {

            CallDataRecord record = new CallDataRecord();

            record.setUserName(_convertCharset(resultSet.getString("userName")));
            record.setHostName(resultSet.getString("hostName"));
            record.setConfId(resultSet.getLong("confId"));
            String confName = _convertCharset(resultSet.getString("confName"));
            //#x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
            record.setConfName(confName.replaceAll(
                    "[^\u0009\r\n\u0020-\ud7ff\ue0000-\ufffd\u10000-\u10ffff]", ""
            ));
            record.setStartTime(resultSet.getTimestamp("startTime"));
            record.setEndTime(resultSet.getTimestamp("endTime"));
            record.setCallType(resultSet.getString("callType"));
            record.setSiteName(resultSet.getString("siteName"));

            record.calculateDuration();

            return record;
        }
    };
}
