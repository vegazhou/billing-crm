package com.kt.biz.model.util;

import com.kt.entity.mysql.crm.Rate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class Copier {

    public static Rate copy(Rate src) {
        Rate copy = new Rate();
        copy.setCode(src.getCode());
        copy.setDisplayName(src.getDisplayName());
        copy.setRate(src.getRate());
        copy.setServiceRate(src.getServiceRate());
        copy.setCountry(src.getCountry());
        return copy;
    }


    public static List<Rate> copy(List<Rate> src) {
        if (src == null) {
            return null;
        } else if (src.size() == 0) {
            return new ArrayList<>();
        } else {
            List<Rate> copy = new ArrayList<>(src.size());
            for (Rate item : src) {
                copy.add(copy(item));
            }
            return copy;
        }
    }

}
