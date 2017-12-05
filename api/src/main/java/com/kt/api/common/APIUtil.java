package com.kt.api.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.kt.auth.Hash;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The Class APIUtil.
 */
public final class APIUtil {
    private static final Logger LOGGER = Logger.getLogger(APIUtil.class);

    /**
     * Hide Utility Class Constructor
     */
    private APIUtil() {
    }

    /**
     * generateBaseUrl
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String generateBaseUrl(HttpServletRequest request) {
        int port = request.getServerPort();
        String reqUri = request.getRequestURI();
        return "http://" + request.getServerName() + (port == 80 ? "" : ":" + port)
                + (reqUri.endsWith("/") ? reqUri : reqUri + "/");
    }

    /**
     * getUUID
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * str2Int
     *
     * @param str     String
     * @param nullVal int
     * @return int
     */
    public static int str2Int(String str, int nullVal) {
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            result = nullVal;
            LOGGER.info("Integer.parseInt() error:", e);
        }
        return result;
    }

    /**
     * now
     *
     * @return String
     */
    public static String now() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(new Date());
    }

    /**
     * Encrypt password.
     *
     * @param password the password
     * @return the string
     */
    public static String encryptPassword(String password) {
        return Hash.sha2InHex(password);
    }

    /**
     * createdResonse
     *
     * @param request HttpServletRequest
     * @param id      String
     * @return ResponseEntity
     */
    public static ResponseEntity<String> createdResonse(HttpServletRequest request, String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(APIConstants.HEADER_NAME_LOCATION, generateBaseUrl(request) + id);
        return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
    }
    
    /**
     * createdResonse
     *
     * @param request HttpServletRequest
     * @param id      String
     * @return ResponseEntity
     */
    public static ResponseEntity<String> newResponse(HttpServletRequest request, String id) {
        return createdResonse(request, id);
    }
}
