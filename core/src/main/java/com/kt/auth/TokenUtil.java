/*
 *
 */
package com.kt.auth;

import org.apache.log4j.Logger;

public final class TokenUtil {

    private static final Logger logger = Logger.getLogger(TokenUtil.class);

    /**
     * Generate token.
     *
     * @param userId
     *            the user id
     * @param appName
     *            the app name
     * @param timeToLive
     *            the time to live
     * @return the string
     */
    public static String generateToken(String userId, String appName,
            long timeToLive) {

        String token = null;
        long createTime = System.currentTimeMillis();	
        StringBuffer ticketStr = new StringBuffer();
        ticketStr.append(userId);
        ticketStr.append("%");
        ticketStr.append(appName);
        ticketStr.append("%");
        ticketStr.append(createTime);
        ticketStr.append("%");
        ticketStr.append(timeToLive);
        SessionTicket sessionTicket = new SessionTicket(createTime, timeToLive);
        sessionTicket.setRawTicket(ticketStr.toString().getBytes());
        try {
            token = TicketUtil.generateTicket(sessionTicket);
        } catch (Exception e) {
            logger.error("An exception is thrown in TokenUtil.generateToken"
                    + e.getMessage());
            return null;
        }
        return token;
    }

    /**
     * Validate token.
     *
     * @param tokenStr
     *            the token str
     * @param userId
     *            the user id
     * @param appName
     *            the app name
     * @return true, if validate token
     */
    public static boolean validateToken(String tokenStr, String userId,
            String appName) {
        boolean isValid = false;
        String ticketStr = parseToken(tokenStr);
        if (ticketStr != null) {
            String[] ticket = ticketStr.split("%");
            isValid = isValidUser(ticket, userId)
                    && isValidApp(ticket, appName) && isAlive(ticket);
        }
        return isValid;
    }

    /**
     * Checks if is valid user.
     *
     * @param ticket
     *            the ticket
     * @param userId
     *            the user id
     * @return true, if checks if is valid user
     */
    private static boolean isValidUser(String[] ticket, String userId) {
        return userId.equals(ticket[0]);
    }

    /**
     * Checks if is valid app.
     *
     * @param ticket
     *            the ticket
     * @param appName
     *            the app name
     * @return true, if checks if is valid app
     */
    private static boolean isValidApp(String[] ticket, String appName) {
        return appName.equals(ticket[1]);
    }

    /**
     * Checks if is alive.
     *
     * @param ticket
     *            the ticket
     * @return true, if checks if is alive
     */
    private static boolean isAlive(String[] ticket) {
        boolean isValid = false;
        if (Long.parseLong(ticket[3]) == 0) {
            isValid = true;
        } else if ((System.currentTimeMillis() - Long.parseLong(ticket[2])) < Long
                .parseLong(ticket[3])*60*1000) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Parses the token.
     *
     * @param tokenStr
     *            the token str
     * @return the string
     */
    private static String parseToken(String tokenStr) {
        String ticketStr = null;
        SessionTicket sessionTikcet = null;
        try {
            sessionTikcet = TicketUtil.getTicket(tokenStr, false);
        } catch (Exception e) {
            logger.error("An exception is thrown in TokenUtil.parseToken"
                    + e.getMessage());
        }
        if (sessionTikcet != null) {
            ticketStr = new String(sessionTikcet.getRawTicket());
        }
        return ticketStr;
    }
}
