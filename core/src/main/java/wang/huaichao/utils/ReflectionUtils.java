package wang.huaichao.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 8/12/2016.
 */
public class ReflectionUtils {
    public static Map<String, PropertyDescriptor> getAccessors(Class<?> klass, Class<?> endKlass) {

        Map<String, PropertyDescriptor> map = new HashMap<>();
        BeanInfo beanInfo;

        try {
            beanInfo = Introspector.getBeanInfo(klass, endKlass);
        } catch (IntrospectionException e) {
            throw new RuntimeException("failed to get ds info: " + klass);
        }

        PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor pd : propDescs) {
            map.put(pd.getName(), pd);
        }

        return map;
    }
}
