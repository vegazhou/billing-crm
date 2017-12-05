package wang.huaichao.utils;

import wang.huaichao.data.entity.crm.MeetingRecord;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 9/6/2016.
 */
public class CollectionUtils {
    public static <T, E> List<T> collect(Collection<E> objs, String propName, Class<T> propClass) {
        List<T> lst = new ArrayList<>();

        if (objs == null || objs.size() == 0) return lst;

        Field f = null;

        for (E obj : objs) {
            if (f == null) {
                try {
                    f = obj.getClass().getDeclaredField(propName);
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                lst.add((T) f.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return lst;
    }

    public static <E> List<String> collectString(Collection<E> objs, String propName) {
        return collect(objs, propName, String.class);
    }


    public static void main(String[] args) {
        List<MeetingRecord> lst = new ArrayList<>();

        MeetingRecord meetingRecord = new MeetingRecord();
        meetingRecord.setCustomerId("cusid 1");
        lst.add(meetingRecord);

        meetingRecord = new MeetingRecord();
        meetingRecord.setCustomerId("cusid 2");
        lst.add(meetingRecord);

        List<String> ids = CollectionUtils.collect(lst, "customerId", String.class);

        for (String s : ids) {
            System.out.println(s);
        }
    }
}
