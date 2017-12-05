package com.kt.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2015/11/4.
 */
public class WebExUtil {

    public static String extractSiteNameFromUrl(String siteUrl) {
        return StringUtils.remove(siteUrl.toLowerCase(), ".webex.com.cn");
    }
}
