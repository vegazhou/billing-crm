package com.kt.api.controller;

import com.kt.api.bean.timezone.TimeZone4Get;
import com.kt.biz.types.TimeZone;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
@Controller
@RequestMapping("/timezone")
public class TimeZoneController extends BaseController {

    @RequestMapping(value = "/query")
    @ResponseBody
    public List<TimeZone4Get> list(HttpServletRequest request) {
        TimeZone[] timeZones = TimeZone.values();
        List<TimeZone4Get> result = new ArrayList<TimeZone4Get>(timeZones.length);
        for (TimeZone timeZone : timeZones) {
            result.add(covert2bean(timeZone));
        }
        return result;
    }

    private TimeZone4Get covert2bean(TimeZone timeZone) {
        TimeZone4Get bean = new TimeZone4Get();
        bean.setDisplayName(timeZone.getDisplayName());
        bean.setValue(timeZone.getValue());
        return bean;
    }


}
