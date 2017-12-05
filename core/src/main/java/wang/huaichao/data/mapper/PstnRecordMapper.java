package wang.huaichao.data.mapper;

import org.springframework.jdbc.core.RowMapper;
import wang.huaichao.data.entity.edr.CallDataRecord;
import wang.huaichao.data.entity.edr.CallDataRecordBilling;
import wang.huaichao.utils.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 8/12/2016.
 */
public class PstnRecordMapper implements RowMapper<CallDataRecord> {
    private static Map<String, PropertyDescriptor> propDescMap = ReflectionUtils.getAccessors(CallDataRecord.class, CallDataRecordBilling.class);

    private static Map<Class, Method> invokeMap = new HashMap<>();

    static {
        try {
            invokeMap.put(String.class, ResultSet.class.getMethod("getString", String.class));
            invokeMap.put(Date.class, ResultSet.class.getMethod("getTimestamp", String.class));
            invokeMap.put(boolean.class, ResultSet.class.getMethod("getBoolean", String.class));
            invokeMap.put(Boolean.class, ResultSet.class.getMethod("getBoolean", String.class));
            invokeMap.put(int.class, ResultSet.class.getMethod("getInt", String.class));
            invokeMap.put(Integer.class, ResultSet.class.getMethod("getInt", String.class));
            invokeMap.put(long.class, ResultSet.class.getMethod("getLong", String.class));
            invokeMap.put(Long.class, ResultSet.class.getMethod("getLong", String.class));
            invokeMap.put(float.class, ResultSet.class.getMethod("getFloat", String.class));
            invokeMap.put(Float.class, ResultSet.class.getMethod("getFloat", String.class));
            invokeMap.put(BigDecimal.class, ResultSet.class.getMethod("getBigDecimal", String.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CallDataRecord mapRow(ResultSet resultSet, int i) throws SQLException {
        CallDataRecord callDataRecord = new CallDataRecord();


        for (String propName : propDescMap.keySet()) {
            PropertyDescriptor propDesc = propDescMap.get(propName);
            Object value = resultSet.getObject(propName);
            if (value == null) continue;

            Class<?> propertyType = propDesc.getPropertyType();

            try {
                value = invokeMap.get(propertyType).invoke(resultSet, propName);
            } catch (Exception e) {
                throw new RuntimeException("invoke result set method " + invokeMap.get(propertyType)
                        + " with column name " + propName + " failed", e);
            }

            try {
                propDesc.getWriteMethod().invoke(callDataRecord, value);
            } catch (Exception e) {
                throw new RuntimeException("call setter failed: " + propName + ":" + value.getClass(), e);
            }
        }

        return callDataRecord;
    }
}
