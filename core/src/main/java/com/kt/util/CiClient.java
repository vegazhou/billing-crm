package com.kt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.biz.bean.CiUserBean;
import com.kt.ciclient.CiClientBuilder;
import com.kt.ciclient.command.response.LoginResponse;
import com.kt.ciclient.exception.CiApiException;
import com.kt.sys.GlobalSettings;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Vega Zhou on 2016/3/31.
 */
public class CiClient {

    private static final Logger LOGGER = Logger.getLogger(CiClient.class);


    public static CiUserBean getUserInfoByEmail(String email) {
        com.kt.ciclient.CiClient client = CiClientBuilder.build(GlobalSettings.getProperty("openam_api_endpoint"));
        try {
            LoginResponse loginResponse = client.login(GlobalSettings.getProperty("bss_system_user"), GlobalSettings.getProperty("bss_system_password"));
            HttpGet get = new HttpGet(GlobalSettings.getProperty("ci_url") + "/orgs/orgusers/getUserAccount/" + email);
            get.setHeader("Token", loginResponse.getTokenId());
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpResponse httpResponse = httpClient.execute(get);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                LOGGER.error("getUserInfoByEmail from CI failed: status code = " + httpResponse.getStatusLine().getStatusCode());
                LOGGER.error(EntityUtils.toString(httpResponse.getEntity()));
                return null;
            } else {
                String json = EntityUtils.toString(httpResponse.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, CiUserBean.class);
            }
        } catch (ClientProtocolException e) {
            LOGGER.error("getUserInfoByEmail from CI failed", e);
            return null;
        } catch (IOException e) {
            LOGGER.error("getUserInfoByEmail from CI failed", e);
            return null;
        } catch (CiApiException e) {
            LOGGER.error("login openAM failed", e);
            return null;
        } finally {
            silentlyLogout(client);
        }
    }

    private static void silentlyLogout(com.kt.ciclient.CiClient client) {
        try {
            if (client != null) {
                client.logout();
            }
        } catch (CiApiException e) {
            LOGGER.error("logout openAM failed", e);
        }
    }
}
