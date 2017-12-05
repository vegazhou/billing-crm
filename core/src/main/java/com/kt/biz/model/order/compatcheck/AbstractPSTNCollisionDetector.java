package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.model.order.util.TimeSpanMerger;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public abstract class AbstractPSTNCollisionDetector implements CollisionDetector {
    private OrderBean me;

    private Map<String, TimeSpanMerger> conferenceMergers = new HashMap<>();

    private Map<String, TimeSpanMerger> pstnMergers = new HashMap<>();


    abstract List<String> getMySiteNames();

    protected boolean isMyTimeSpanAlignWithConferenceTimeSpan() {
        List<String> siteNames = getMySiteNames();
        for (String siteName : siteNames) {
            TimeSpanMerger merger = getConferenceMergerOfSite(siteName);
            if (merger == null) {
                return false;
            } else {
                if (!merger.covers(me.getStartDate(), me.getEndDate())) {
                    return false;
                }
            }
        }
        return true;
    }


    protected boolean isMyTimeSpanAlignWithPSTNTimeSpan() {
        List<String> siteNames = getMySiteNames();
        for (String siteName : siteNames) {
            TimeSpanMerger merger = getPSTNMergerOfSite(siteName);
            if (merger == null) {
                return false;
            } else {
                if (!merger.covers(me.getStartDate(), me.getEndDate())) {
                    return false;
                }
            }
        }
        return true;
    }


    protected void putIntoConferenceMergers(String siteName, OrderBean orderBean) {
        TimeSpanMerger merger = getConferenceMergerOfSite(siteName);
        if (merger != null) {
            merger.addTimeSpan(orderBean.getStartDate(), orderBean.getEndDate());
        }
    }


    private TimeSpanMerger getConferenceMergerOfSite(String siteName) {
        if (StringUtils.isBlank(siteName)) {
            return null;
        }
        String key = siteName.trim().toLowerCase();
        if (this.conferenceMergers.containsKey(key)) {
            return conferenceMergers.get(key);
        } else {
            TimeSpanMerger merger = new TimeSpanMerger();
            conferenceMergers.put(key, merger);
            return merger;
        }
    }

    protected void putIntoPSTNMergers(String siteName, OrderBean orderBean) {
        TimeSpanMerger merger = getPSTNMergerOfSite(siteName);
        if (merger != null) {
            merger.addTimeSpan(orderBean.getStartDate(), orderBean.getEndDate());
        }
    }

    protected void putIntoPSTNMergers(List<String> siteNames, OrderBean orderBean) {
        if (siteNames != null) {
            for (String siteName : siteNames) {
                putIntoPSTNMergers(siteName, orderBean);
            }
        }
    }


    private TimeSpanMerger getPSTNMergerOfSite(String siteName) {
        if (StringUtils.isBlank(siteName)) {
            return null;
        }
        String key = siteName.trim().toLowerCase();
        if (this.pstnMergers.containsKey(key)) {
            return pstnMergers.get(key);
        } else {
            TimeSpanMerger merger = new TimeSpanMerger();
            pstnMergers.put(key, merger);
            return merger;
        }
    }

    public OrderBean getMe() {
        return me;
    }

    public void setMe(OrderBean me) {
        this.me = me;
    }

    protected String findSameSite(List<String> siteNames, List<String> testedTargets) {
        for (String testedTarget : testedTargets) {
            String sameSite = findSameSite(siteNames, testedTarget);
            if (sameSite != null) {
                return sameSite;
            }
        }
        return null;
    }


    protected String findSameSite(List<String> siteNames, String testedTarget) {
        for (String siteName : siteNames) {
            if (OrderUtils.isSameSite(siteName, testedTarget)) {
                return siteName;
            }
        }
        return null;
    }
}
