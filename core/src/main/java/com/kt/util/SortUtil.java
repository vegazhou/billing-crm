package com.kt.util;

/**
 * Created by Administrator on 2016/3/3.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SortUtil {
    private static final Logger logger = LoggerFactory.getLogger(SortUtil.class);

    public SortUtil() {
    }

    public static <T> List<T> anyProperSort(List<T> list, String properName, boolean is_Asc) {
        AnyProperComparator comparator = new AnyProperComparator(properName, is_Asc);
        Collections.sort(list, comparator);
        return list;
    }

    static class AnyProperComparator implements Comparator<Object> {
        private String properName;
        private boolean flag;

        public AnyProperComparator(String properName, boolean flag) {
            this.properName = properName;
            this.flag = flag;
        }

        public void setProperName(String properName) {
            this.properName = properName;
        }

        public String getProperName() {
            return this.properName;
        }

        public boolean isFlag() {
            return this.flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int compare(Object r1, Object r2) {
            if(r1 == null && r2 == null){
                return 0;
            }else if(r1 == null){
                return -1;
            }else if(r2 == null){
                return 1;
            }
            Class c = r1.getClass();
            double result = 0.0D;

            try {
                Field e = c.getDeclaredField(this.properName);
                String classType = e.getType().getSimpleName();
                Method method = null;
//                logger.debug("classType:" + classType);
                if("String".equals(classType)) {
                    method = c.getMethod("get" + this.properName.substring(0, 1).toUpperCase() + this.properName.substring(1), new Class[0]);
                    if (this.flag) {
                        result = (double) ((String) method.invoke(r1, new Object[0])).compareTo((String) method.invoke(r2, new Object[0]));
                    } else {
                        result = (double) ((String) method.invoke(r2, new Object[0])).compareTo((String) method.invoke(r1, new Object[0]));
                    }
                }else if("Date".equals(classType)){
                    method = c.getMethod("get" + this.properName.substring(0, 1).toUpperCase() + this.properName.substring(1), new Class[0]);
                    long date1 = ((Date) (method.invoke(r1, new Object[0]))).getTime();
                    long date2 = ((Date) (method.invoke(r2, new Object[0]))).getTime();
                    if (date1 == date2) {
                        return 0;
                    } else if (date1 < date2) {
                        return this.flag ? -1 : 1;
                    } else {
                        return this.flag ? 1 : -1;
                    }
//                    if (this.flag) {
//                        boolean isAfter = ((Date) (method.invoke(r1, new Object[0]))).after((Date) method.invoke(r2, new Object[0]));
//                        result = isAfter?1:-1;
//                    } else {
//                        boolean isAfter = ((Date) (method.invoke(r1, new Object[0]))).before((Date) method.invoke(r2, new Object[0]));
//                        result = isAfter?1:-1;
//                    }
                } else if(!"Integer".equals(classType) && !"int".equals(classType)) {
                    if(!"Double".equals(classType) && !"double".equals(classType)) {
                        if(!"Float".equals(classType) && !"float".equals(classType)) {
                            logger.error("属性排序只支持数据类型和String类型，其它类型暂不支持。");
                            result = -100.0D;
                        } else {
                            method = c.getMethod("get" + this.properName.substring(0, 1).toUpperCase() + this.properName.substring(1), new Class[0]);
                            if(this.flag) {
                                result = (double)(((Float)method.invoke(r1, new Object[0])).floatValue() - ((Float)method.invoke(r2, new Object[0])).floatValue());
                            } else {
                                result = (double)(((Float)method.invoke(r2, new Object[0])).floatValue() - ((Float)method.invoke(r1, new Object[0])).floatValue());
                            }
                        }
                    } else {
                        method = c.getMethod("get" + this.properName.substring(0, 1).toUpperCase() + this.properName.substring(1), new Class[0]);
                        if(this.flag) {
                            result = ((Double)method.invoke(r1, new Object[0])).doubleValue() - ((Double)method.invoke(r2, new Object[0])).doubleValue();
                        } else {
                            result = ((Double)method.invoke(r2, new Object[0])).doubleValue() - ((Double)method.invoke(r1, new Object[0])).doubleValue();
                        }
                    }
                } else {
                    method = c.getMethod("get" + this.properName.substring(0, 1).toUpperCase() + this.properName.substring(1), new Class[0]);
                    if(this.flag) {
                        result = (double)(((Integer)method.invoke(r1, new Object[0])).intValue() - ((Integer)method.invoke(r2, new Object[0])).intValue());
                    } else {
                        result = (double)(((Integer)method.invoke(r2, new Object[0])).intValue() - ((Integer)method.invoke(r1, new Object[0])).intValue());
                    }
                }
            } catch (SecurityException var9) {
                var9.printStackTrace();
            } catch (NoSuchFieldException var10) {
                var10.printStackTrace();
            } catch (NoSuchMethodException var11) {
                var11.printStackTrace();
            } catch (IllegalArgumentException var12) {
                var12.printStackTrace();
            } catch (IllegalAccessException var13) {
                var13.printStackTrace();
            } catch (InvocationTargetException var14) {
                var14.printStackTrace();
            }

            return result > 0.0D?1:(result < 0.0D?-1:0);
        }
    }
}