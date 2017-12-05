package com.kt.common.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * HttpServletRequestReplacedFilter
 */
public class HttpServletRequestReplacedFilter implements Filter {
    private static final Set<String> APP_NAME_WHITE_LIST = new HashSet<String>(Arrays.asList("101",
            "102", "103", "104", "201", "202", "203", "204", "301", "302", "303"));

    private static final String HEADER_NAME_SOURCE = "Source";

    /**
     * destroy
     */
    @Override
    public void destroy() {
        // Do nothing
    }

    /**
     * doFilter
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param chain    FilterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //if (isValidSource(req)) {
            String saccount = (String)request.getParameter("s_saccount");
            ServletRequest requestWrapper = new APIHttpServletRequestWrapper(req);
            String saccount2 = (String)request.getParameter("s_saccount");
            resp.setHeader("Access-Control-Allow-Origin", getOrigin(req));
            resp.setHeader("Access-Control-Allow-Headers", "Accept,Content-Type,Source,Uid,Token,Sign");
            resp.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            chain.doFilter(requestWrapper, resp);
        /*} else {
            resp.setStatus(HttpStatus.SC_FORBIDDEN);
            OutputStream outputStream = resp.getOutputStream();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", "global.source.invalid");
            jsonObject.put("message", "source is invalid");
            JSONObject error = new JSONObject();
            error.put("error", jsonObject);
            outputStream.write(error.toString().getBytes());*/
        //}
    }

    private String getOrigin(HttpServletRequest req) {
        String origin = req.getHeader("Origin");
        if (origin == null) {
            String referer = ("" + req.getHeader("Referer")).toLowerCase();
            if (referer.startsWith("http")) {
                String[] arrStr = referer.split("/");
                if (arrStr.length > 2) {
                    origin = arrStr[0] + "//" + arrStr[2];
                }
            }
        }
        return origin;
    }

    private boolean isValidSource(HttpServletRequest req) {
        String source = req.getHeader(HEADER_NAME_SOURCE);
        return !StringUtils.isEmpty(source) && APP_NAME_WHITE_LIST.contains(source);
    }

    /**
     * init
     *
     * @param arg0 FilterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing
    }
}