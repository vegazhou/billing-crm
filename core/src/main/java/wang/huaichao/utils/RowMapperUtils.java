package wang.huaichao.utils;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 9/6/2016.
 */
public class RowMapperUtils {
    public static RowMapper<String> getStringRowMapper(final String columnName) {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(columnName);
            }
        };
    }

    public static RowMapper<Float> getFloatRowMapper(final String columnName) {
        return new RowMapper<Float>() {
            @Override
            public Float mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getFloat(columnName);
            }
        };
    }

}
