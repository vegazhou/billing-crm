package wang.huaichao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.utils.DateBuilder;
import wang.huaichao.utils.FileUtils;
import wang.huaichao.utils.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 6/26/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EdrTest {
    @Autowired
    @Qualifier("edrJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void downloadEdrPstnRecords() throws IOException {
        MapSqlParameterSource params = new MapSqlParameterSource();

        List<String> siteNames = Arrays.asList(
            "edaixi"
        );

        DateRange dr = DateRange.fromBillingPeriod(201605);
        params.addValue("siteNames", siteNames);
        params.addValue("startTime", dr.start);
        params.addValue("endTime", dr.end);

        String sql = FileUtils.readFromClassPath("/wang/huaichao/edr/find-pstn-usage.sql");


        FileOutputStream fos = new FileOutputStream("e:\\records.obj");
        final ObjectOutputStream oos = new ObjectOutputStream(fos);

        jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 0; i < columnCount; i++) {
                    System.out.println(metaData.getColumnType(i+1));
                    System.out.println(metaData.getColumnName(i+1));
                    Object object = rs.getObject(i + 1);
                    System.out.println(StringUtils.fromCharset(object.toString(),"iso8859-1"));

                }


                return null;
            }
        });

        oos.close();
    }

    private static class DateRange {
        private Date start;
        private Date end;

        private DateRange() {

        }

        public static DateRange fromBillingPeriod(int billingPeriod) {
            DateRange dr = new DateRange();
            DateBuilder db;
            try {
                db = new DateBuilder(Global.yyyyMM_FMT.parse(billingPeriod + ""));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            dr.start = db.beginOfMonth().build();
            dr.end = db.nextMonth().build();
            return dr;
        }
    }

    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }
}
