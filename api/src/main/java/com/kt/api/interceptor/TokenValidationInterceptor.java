package com.kt.api.interceptor;

import com.kt.api.common.APIConstants;
import com.kt.ciclient.entity.Principal;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.service.OrgUserService;
import com.kt.session.PrincipalContext;
import com.kt.sso.client.SsoClient;
import com.kt.sso.client.SsoClientManager;
import com.kt.sso.client.exception.SsoClientException;
import com.kt.sys.WafContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Vega Zhou on 2015/10/10.
 */
public class TokenValidationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(TokenValidationInterceptor.class);

    private static final String TOKEN_COOKIE_NAME = "iPlanetDirectoryPro";

    @Autowired
    private OrgUserService orgUserService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (o instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) o;
            if (shouldIgnoreToken(method) || isTokenValid(httpServletRequest, method)) {
                return true;
            }
        }

        writeUnauthorizedError(httpServletResponse);
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        PrincipalContext.clearPrincipal();
        WafContext.clear();
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        PrincipalContext.clearPrincipal();
        WafContext.clear();
    }

    private void writeUnauthorizedError(HttpServletResponse response) {
        response.setStatus(401);
    }

    private boolean isTokenValid(HttpServletRequest request, HandlerMethod method) {
        Cookie[] cookies = request.getCookies();
        //String token = findTokenInCookies(cookies);
        String token = request.getHeader(APIConstants.HEADER_NAME_TOKEN);
        if (token == null) {
            LOGGER.info("token not found when calling " + method.getMethod().toGenericString());
            token = findTokenInCookies(cookies);
        }
        return !StringUtils.isBlank(token) && checkTokenInSessionStore(token, request);
    }


    private boolean checkTokenInSessionStore(String token, HttpServletRequest request) {
        SsoClient client = SsoClientManager.getInstance();

        try {
            if (PrincipalContext.isPrincipalInSession()) {
                return true;
            }

            Principal principal = client.getPrincipal(token);
            String userId = principal.getUid();
            if (userId == null) {
                return false;
            }

            OrgUser user = orgUserService._doFindByUserName(userId);
            if (user == null) {
                return false;
            }

            PrincipalContext.storePrincipal(user);
            return true;
        } catch (SsoClientException e) {
            LOGGER.error("checkTokenInSessionStore failed", e);
        }

        return false;
    }


    private String findTokenInCookies(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @return return true if the controller call should be process without validating session info
     */
    private boolean shouldIgnoreToken(HandlerMethod method) {
        return false;
    }
}
