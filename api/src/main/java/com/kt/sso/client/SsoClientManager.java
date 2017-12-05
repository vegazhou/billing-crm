package com.kt.sso.client;

import com.kt.sso.client.exception.SsoClientException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vega Zhou on 2015/10/13.
 */
public class SsoClientManager {

    private static Properties props;

    static {
        props =  new Properties();
        try {
            props.load(SsoClientManager.class.getResourceAsStream("/openam.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger LOGGER = Logger.getLogger(SsoClientManager.class);

    private static SsoClient client;

    public static SsoClient getInstance() {
        if (client == null) {
            try {
                client = new SsoClient(props.getProperty("openam_url"), props.getProperty("openam_username"), props.getProperty("openam_password"));
            } catch (SsoClientException e) {
                LOGGER.error("initialize sso client failed:", e);
            }
        }
        return client;
    }
}
