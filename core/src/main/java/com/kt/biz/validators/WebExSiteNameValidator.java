package com.kt.biz.validators;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public class WebExSiteNameValidator {

    private static String ATOM = "^[a-z0-9][a-z0-9-_]*[a-z0-9]";

    private final static Pattern localPattern = java.util.regex.Pattern.compile(ATOM, CASE_INSENSITIVE);


    public static boolean isValidSiteName(String siteName) {
        if (StringUtils.isBlank(siteName)) {
            return false;
        }

        Matcher matcher = localPattern.matcher(siteName.trim());
        return matcher.matches();
    }

    public static void main(String args[]) {
        System.out.println(isSiteExistedInWorldWideWeb("ktyxxx"));
    }

    public static boolean isSiteExistedInWorldWideWeb(String siteName) {
        try {
            String url = "http://" + siteName + ".webex.com.cn";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            connection.disconnect();
        } catch (MalformedURLException e) {
            return false;
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return true;
        }
        return true;
    }
}
