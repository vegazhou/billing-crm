package com.kt.biz.model.util;

import com.google.gson.*;
import com.kt.biz.model.annotation.AttributeType;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class TransformUtil {

    public static void attachElement2JsonObject(JsonObject json, String propertyName, Object propertyValue, AttributeType type) {
        switch (type) {
            case STRING:
                if (propertyValue == null) {
                    json.add(propertyName, null);
                } else {
                    json.addProperty(propertyName, String.valueOf(propertyValue));
                }
                break;
            case INT:
            case FLOAT:
            case DOUBLE:
                json.addProperty(propertyName, (Number) propertyValue);
                break;
            case STRING_LIST:
                if (propertyValue instanceof List) {
                    List list = (List) propertyValue;
                    JsonArray array = new JsonArray();
                    for (Object element : list) {
                        array.add(new JsonPrimitive(String.valueOf(element)));
                    }
                    json.add(propertyName, array);
                }

                break;
            case INT_LIST:
            case FLOAT_LIST:
                if (propertyValue instanceof List) {
                    List list = (List) propertyValue;
                    JsonArray array = new JsonArray();
                    for (Object element : list) {
                        array.add(new JsonPrimitive((Number) element));
                    }
                    json.add(propertyName, array);
                }
                break;
            case DATE:
                if (propertyValue instanceof Date) {
                    Date date = (Date) propertyValue;
                    json.addProperty(propertyName, DateUtil.formatDate(date));
                }


        }
    }

    public static Object getAttributeValue(JsonObject json, String attributeName, AttributeType type) {
        switch (type) {
            case STRING:
                return getMemberAsString(json, attributeName);
            case INT:
                return getMemberAsInt(json, attributeName);
            case FLOAT:
                return getMemberAsFloat(json, attributeName);
            case DOUBLE:
                return getMemberAsDouble(json, attributeName);
            case STRING_LIST:
                return getMemberAsStringList(json, attributeName);
            case INT_LIST:
                break;
            case FLOAT_LIST:
                break;
            case DATE:
                return getMemberAsDate(json, attributeName);
        }
        return null;
    }


    public static Object convertString2Field(String value, AttributeType type) {
        switch (type) {
            case STRING:
                return value;
            case INT:
                return Integer.valueOf(value);
            case FLOAT:
                return Float.valueOf(value);
            case DOUBLE:
                return Double.valueOf(value);
            case STRING_LIST:
                String[] array = StringUtils.split(value, ";");
                if (array == null) {
                    return new ArrayList<>();
                }
                return Arrays.asList(array);
            case INT_LIST:
                break;
            case FLOAT_LIST:
                break;
            case DATE:
                try {
                    return DateUtil.toDate(value);
                } catch (ParseException ignore) {
                }
        }
        return null;
    }


    private static Integer getMemberAsInt(JsonObject object, String member) {
        JsonElement node = object.get(member);
        if (node != null && !node.isJsonNull()) {
            return node.getAsInt();
        } else {
            return null;
        }
    }

    private static Float getMemberAsFloat(JsonObject object, String member) {
        JsonElement node = object.get(member);
        if (node != null && !node.isJsonNull()) {
            return node.getAsFloat();
        } else {
            return null;
        }
    }

    private static Double getMemberAsDouble(JsonObject object, String member) {
        JsonElement node = object.get(member);
        if (node != null && !node.isJsonNull()) {
            return node.getAsDouble();
        } else {
            return null;
        }
    }

    public static String getMemberAsString(JsonObject object, String member) {
        JsonElement node = object.get(member);
        if (node != null && !node.isJsonNull()) {
            return node.getAsString();
        } else {
            return null;
        }
    }

    public static List<String> getMemberAsStringList(JsonObject object, String member) {
        if (object == null) {
            return null;
        }
        JsonElement memberElement = object.get(member);
        if (memberElement != null && memberElement instanceof JsonArray) {
            JsonArray siteArray = (JsonArray) memberElement;

            if (!siteArray.isJsonNull()) {
                List<String> result = new ArrayList<>();
                for (JsonElement site : siteArray) {
                    result.add(site.getAsString());
                }
                return result;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }


    private static Date getMemberAsDate(JsonObject object, String member) {
        String dateInString = getMemberAsString(object, member);
        if (StringUtils.isBlank(dateInString)) {
            return null;
        }
        try {
            return DateUtil.toDate(dateInString);
        } catch (ParseException ignore) {
        }

        try {
            return DateUtil.toShortDate(dateInString);
        } catch (ParseException ignore) {
        }

        return null;
    }
}
