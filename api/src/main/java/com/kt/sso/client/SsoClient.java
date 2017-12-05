package com.kt.sso.client;

import com.kt.ciclient.CiClient;
import com.kt.ciclient.CiClientBuilder;
import com.kt.ciclient.command.response.LoginResponse;
import com.kt.ciclient.entity.Principal;
import com.kt.ciclient.exception.CiApiException;
import com.kt.sso.client.exception.SsoClientException;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega Zhou on 2015/10/13.
 */
public class SsoClient {

    private static final Logger LOGGER = Logger.getLogger(SsoClient.class);

    private String serverUrl;
    private String userName;
    private String password;

    private CiClient client;

    private transient Date expiredDate = new Date();

    private String token;

    public SsoClient(String serverUrl, String userName, String password) throws SsoClientException {
        this.serverUrl = serverUrl;
        this.userName = userName;
        this.password = password;
        init();
    }


    public Principal getPrincipal(String token) throws SsoClientException {
        assureTokenNotExpired();
        try {
            return client.validateToken(token);
        } catch (CiApiException e) {
            throw new SsoClientException("validateToken call failed", e);
        }
    }



    private void init() throws SsoClientException {
        client = CiClientBuilder.build(serverUrl);
        renewToken();
    }

    private void renewToken() throws SsoClientException {
        LoginResponse loginResponse;
        try {
            loginResponse = client.login(userName, password);
        } catch (CiApiException e) {
            throw new SsoClientException("SsoClient login failed", e);
        }

        if (!loginResponse.isSuccess()) {
            throw new SsoClientException("SSoClient login failed");
        } else {
            resetTokenInfo(loginResponse);
        }
    }

    private void resetTokenInfo(LoginResponse loginResponse) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, (int) loginResponse.getExpiredIn() - 60);
        this.expiredDate = calendar.getTime();
        this.token = loginResponse.getTokenId();
    }

    private boolean isTokenExpired() {
        return expiredDate.before(new Date());
    }



    private void assureTokenNotExpired() throws SsoClientException {
        if (isTokenExpired()) {
            LOGGER.debug("renew sso client token positively");
            renewToken();
        } else {
            try {
                client.validateToken(token);
            } catch (CiApiException e) {
                LOGGER.debug("renew sso client token passively");
                renewToken();
            }
        }
    }


}
