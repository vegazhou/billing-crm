package com.kt.session;

import com.kt.entity.mysql.user.OrgUser;

/**
 * Created by Vega Zhou on 2015/10/31.
 */
public class PrincipalContext {

    private static ThreadLocal<Principal> principal = new ThreadLocal<Principal>();

    public static void storePrincipal(OrgUser user) {
        Principal p = new Principal();

        p.setRoles(user.getRoleId());
        p.setUserName(user.getUserName());
        PrincipalContext.principal.set(p);
    }

    public static boolean isPrincipalInSession() {
        return principal.get() != null;
    }



    public static String getCurrentUserName() {
        return principal.get().getUserName();
    }

    public static Principal getPrincipal() {
        return principal.get();
    }

    public static void clearPrincipal() {
        principal.remove();
    }
}
