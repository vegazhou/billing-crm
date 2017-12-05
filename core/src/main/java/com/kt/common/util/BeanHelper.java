package com.kt.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.common.exception.ApiException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * BeanHelper
 */
public final class BeanHelper {
    private static final Logger LOGGER = Logger.getLogger(BeanHelper.class);

    /**
     * Hide Utility Class Constructor
     */
    private BeanHelper() {
    }

    /**
     * copyPropertiesWithTrim
     *
     * @param source     Object
     * @param target     Object
     * @param properties String[]
     */
    public static void copyPropertiesWithTrim(Object source, Object target, String[] properties) {
        BeanWrapper src = new BeanWrapperImpl(source);
        BeanWrapper trg = new BeanWrapperImpl(target);
        Object value;
        for (String propertyName : properties) {
            try {
                value = src.getPropertyValue(propertyName);
                if (value != null && (value instanceof String)) {
                    value = ((String) value).trim();
                } else if (value == null && src.getPropertyType(propertyName) == String.class) {
                    value = "";
                }
                trg.setPropertyValue(propertyName, value);
            } catch (BeansException e) {
                LOGGER.debug("bean copy fail", e);
            }

        }
    }

    /**
     * copyProperties
     *
     * @param source     Object
     * @param target     Object
     * @param properties String[]
     */
    public static void copyProperties(Object source, Object target, String[] properties) {
        BeanWrapper src = new BeanWrapperImpl(source);
        BeanWrapper trg = new BeanWrapperImpl(target);
        Object value;
        for (String propertyName : properties) {
            try {
                value = src.getPropertyValue(propertyName);
                if (value != null && (value instanceof String)) {
                    value = ((String) value).trim();
                }
                trg.setPropertyValue(propertyName, value);
            } catch (BeansException e) {
                LOGGER.debug("bean copy fail", e);
            }

        }
    }

    /**
     * copyProperties
     *
     * @param source Object
     * @param target Object
     */
    public static void copyProperties(Object source, Object target) {
        String[] properties = getCopyFields(source.getClass(), target.getClass());
        copyProperties(source, target, properties);
    }

    /**
     * getCopyFields
     *
     * @param source Class
     * @param target Class
     * @return String[]
     */
    public static String[] getCopyFields(Class source, Class target) {
        String[] properties;
        if (source.getDeclaredFields().length >
                target.getDeclaredFields().length) {
            properties = getInverseFields(target, null);
        } else {
            properties = getInverseFields(source, null);
        }
        return properties;
    }

    /**
     * copyPropertiesWithTrim
     *
     * @param source Object
     * @param target Object
     */
    public static void copyPropertiesWithTrim(Object source, Object target) {
        String[] properties = getCopyFields(source.getClass(), target.getClass());
        copyPropertiesWithTrim(source, target, properties);
    }

    /**
     * copyInversePropertiesWithTrim
     *
     * @param source           Object
     * @param target           Object
     * @param ignoreProperties String[]
     */
    public static void copyInversePropertiesWithTrim(Object source, Object target, String[] ignoreProperties) {
        String[] properties = getInverseFields(target, ignoreProperties);
        copyPropertiesWithTrim(source, target, properties);
    }

    /**
     * getInverseFields
     *
     * @param clz            Class
     * @param excludedFields String[]
     * @return String[]
     */
    public static String[] getInverseFields(Class clz, String[] excludedFields) {
        Field[] declaredFields = clz.getDeclaredFields();
        List<String> fieldList = new ArrayList<String>();
        if (excludedFields == null || excludedFields.length == 0) {
            for (Field f : declaredFields) {
                fieldList.add(f.getName());
            }
        } else {
            List excludedlist = Arrays.asList(excludedFields);
            String fieldName;
            for (Field f : declaredFields) {
                fieldName = f.getName();
                if (!excludedlist.contains(fieldName)) {
                    fieldList.add(fieldName);
                }
            }
        }
        return fieldList.toArray(new String[fieldList.size()]);
    }

    /**
     * getInverseFields
     *
     * @param beanObj        Object
     * @param excludedFields String[]
     * @return String[]
     */
    private static String[] getInverseFields(Object beanObj, String[] excludedFields) {
        return getInverseFields(beanObj.getClass(), excludedFields);
    }

    /**
     * getIgnoreProperties
     *
     * @param request HttpServletRequest
     * @param clz     Class
     * @return String[]
     */
    public static String[] getProperties(HttpServletRequest request, Class clz) {
        Map<String, Object> datas = getEntity(request);
        return getProperties(datas, clz);
    }

    /**
     * getProperties
     *
     * @param datas Map
     * @param clz   Class
     * @return String[]
     */
    private static String[] getProperties(Map<String, Object> datas, Class clz) {
        Field[] fields = clz.getDeclaredFields();
        List<String> propList = new ArrayList<String>();
        if (datas != null) {
            JsonProperty jsonProperty;
            JsonIgnore jsonIgnore;
            String name;
            for (Field field : fields) {
                jsonIgnore = field.getAnnotation(JsonIgnore.class);
                if (jsonIgnore == null) {
                    jsonProperty = field.getAnnotation(JsonProperty.class);
                    if (jsonProperty == null) {
                        name = field.getName();
                    } else {
                        name = jsonProperty.value();
                    }
                    if (datas.get(name) != null) {
                        propList.add(field.getName());
                    }
                }
            }
        }
        return propList.toArray(new String[propList.size()]);
    }

    /**
     * getEntity
     *
     * @param request HttpServletRequest
     * @return Map
     */
    private static Map<String, Object> getEntity(HttpServletRequest request) {
        Map<String, Object> datas;
        try {
            datas = new ObjectMapper().readValue(getRequestBody(request),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            datas = null;
            LOGGER.error("new ObjectMapper().readValue() error:", e);
        }
        return datas;
    }

    /**
     * getRequestBody
     *
     * @param request HttpServletRequest
     * @return String
     * @throws IOException
     */
    private static String getRequestBody(HttpServletRequest request) throws IOException {
        String src;
        try {
            src = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        } catch (IllegalStateException ex) {
            src = IOUtils.toString(request.getReader());
            LOGGER.error("BeanHelper.getRequestBody() error:", ex);
        }
        return src;
    }

    /**
     * bean2JsonString
     *
     * @param bean Object
     * @return String
     * @throws IOException
     */
    public static String bean2JsonString(Object bean) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream,
                JsonEncoding.UTF8);
        objectMapper.writeValue(jsonGenerator, bean);
        return outputStream.toString("UTF-8");
    }

    /**
     * convertList
     *
     * @param sourceList List
     * @param sourceClass Class
     * @param targetClass Class
     * @param <TS> source class
     * @param <TT> target class
     * @return List
     */
    public static <TS, TT> List<TT> convertList(List<TS> sourceList, Class<TS> sourceClass, Class<TT> targetClass) {
        List<TT> datas = new ArrayList<TT>();
        if (sourceList != null && !sourceList.isEmpty()) {
            TT tmpBean;
            String[] properties = getCopyFields(sourceClass, targetClass);
            for (TS entity : sourceList) {
                tmpBean = newInstance(targetClass);
                copyPropertiesWithTrim(entity, tmpBean, properties);
                datas.add(tmpBean);
            }
        }
        return datas;
    }

    private static <T> T newInstance(Class<T> targetClass) {
        T tmpBean;
        try {
            tmpBean = targetClass.newInstance();
        } catch (Exception ex) {
            LOGGER.error("targetClass.newInstance() error:", ex);
            throw new ApiException("global.ServiceInternalError");
        }
        return tmpBean;
    }

    /**
     * getGetMethod
     *
     * @param clz            Class
     * @param propertyName String
     * @return String
     */
    public static Method getGetMethod(String propertyName, Class clz) {
        if(propertyName == null){
            return null;
        }
//      Field[] declaredFields = clz.getDeclaredFields();
//		Method method = null;
//		for (Field f : declaredFields) {
//			String fieldName = f.getName();
//			if (fieldName.equalsIgnoreCase(propertyName)) {
//				try {
//					method = clz.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[] {});
//				} catch (NoSuchMethodException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SecurityException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return method;
//			}
//		}
        Method[] declaredMethods = clz.getMethods();
        for( Method d: declaredMethods){
            if(d.getName().equalsIgnoreCase("get" + propertyName ) || d.getName().equalsIgnoreCase("is" + propertyName )){
                return d;
            }
        }
        return null;
    }
}
