package com.kt.sys;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vega Zhou on 2015/11/20.
 */
public class GlobalSettings {

    private static final Properties props = new Properties();

    private static Logger LOGGER = Logger.getLogger(GlobalSettings.class);

    static {
        try {
            props.load(GlobalSettings.class.getResourceAsStream("/global.properties"));
        } catch (IOException e) {
            LOGGER.error("ERROR OCCURED READING global.properties", e);
        }
    }


    public static String getProperty(String propertyName) {
        return props.getProperty(propertyName);
    }

    public static String getBssRpcEndpoint() {
        return getProperty("bss_rpc_endpoint");
    }

    public static String getSAPUser() {
        return getProperty("sap_user");
    }

    public static String getSAPPassword() {
        return getProperty("sap_password");
    }

    public static String getSAPCustomerSyncEndpoint() {
        return getProperty("sap_customer_sync_endpoint");
    }

    public static String getSAPPaymentSyncEndpoint() {
        return getProperty("sap_payment_sync_endpoint");
    }

    public static String getSAPBillSyncEndpoint() {
        return getProperty("sap_bill_sync_endpoint");
    }



    public static String getOpenAMEndpoint() {
        return getProperty("openam_api_endpoint");
    }

    public static String getOpenAMAdminAccount() {
        return getProperty("openam_admin_account");
    }

    public static String getOpenAMAdminPassword() {
        return getProperty("openam_admin_password");
    }

    public static String getMailHost() {
        return getProperty("mail_host");
    }

    public static String getMailAuthUser() {
        return getProperty("mail_user_name");
    }

    public static String getMailAuthPass() {
        return getProperty("mail_password");
    }
}
