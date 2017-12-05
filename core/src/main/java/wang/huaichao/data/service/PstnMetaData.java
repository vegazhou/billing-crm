package wang.huaichao.data.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/9/2016.
 */
@Component
public class PstnMetaData implements BeanPostProcessor {
    public static final Map<String, Integer> CountryAreaCode2ZoneMap = new HashMap<>();
    public static final Map<String, String> CallInAccessNumber2FeeNameMap = new HashMap<>();
    public static final Map<String, String> CallInAccessNumber2400NumberMap = new HashMap<>();
    public static final Map<String, String> CallIn400Map = new HashMap<>();
    public static final Map<Integer, String> Zone2ZoneNameMap = new HashMap<>();

    private boolean initialized = false;

    @Autowired
    @Qualifier("crmJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private CrmDataService crmDataService;


    public void initialize(boolean force) {
        if (force || !initialized) {
            _doInit();
            initialized = true;
        }
    }

    private void _doInit() {
        buildCallIn400Map();
        buildCountryAreaCode2ZoneMap();
        buildZone2ZoneNameMap();
        buildCallInAccessNumber2FeeNameMap();
    }

    private void buildCallIn400Map() {
        CallIn400Map.put("+861064689121", "4006140081");
        CallIn400Map.put("+861084518118", "4006140081");
        CallIn400Map.put("+861058062000", "4006140081");
        CallIn400Map.put("+861058062088", "4008191212");
        CallIn400Map.put("+861058062036", "4008191212");
        CallIn400Map.put("+861064386066", "4008191212");
    }

    private void buildCountryAreaCode2ZoneMap() {
        String sql = "select country_code,area_code,land_kind,note from TELEPHONE_LAND_KIND_T";
        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                String country_code = resultSet.getString("country_code");
                String area_code = resultSet.getString("area_code");
                int land_kind = resultSet.getInt("land_kind");
                String note = resultSet.getString("note");

                if (area_code == null) {
                    CountryAreaCode2ZoneMap.put(country_code, land_kind);
                } else {
                    String[] split = area_code.split(",");
                    for (String s : split) {
                        CountryAreaCode2ZoneMap.put(country_code + "_" + s.trim(), land_kind);
                    }
                }
                return null;
            }
        });
    }

    private void buildZone2ZoneNameMap() {
        String sql = "select distinct land_kind,note from TELEPHONE_LAND_KIND_T";
        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                int land_kind = resultSet.getInt("land_kind");
                String note = resultSet.getString("note");
                Zone2ZoneNameMap.put(land_kind, note);
                return null;
            }
        });
    }

    private void buildCallInAccessNumber2FeeNameMap() {
        String sql = "select " +
                "'+' || t4e.REAL_NUMBER access_number, " +
                "'+' || t4e.access_number num400, " +
                "tcn.note " +
                "from " +
                "  TELEPHONE_400_EXCHANGE_T t4e, " +
                "  TELEPHONE_CALLIN_NUMBER_T tcn " +
                "where " +
                "  t4e.ACCESS_NUMBER = tcn.ACCESS_NUMBER";

        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                String access_number = resultSet.getString("access_number");
                CallInAccessNumber2FeeNameMap.put(
                        access_number,
                        resultSet.getString("note")
                                .replace("LOCAL TOLL", "LT")
                                .replace("TOLL-FREE", "TF")
                );
                CallInAccessNumber2400NumberMap.put(
                        access_number, resultSet.getString("num400")
                );
                return null;
            }
        });
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        initialize(false);
        return bean;
    }
}
