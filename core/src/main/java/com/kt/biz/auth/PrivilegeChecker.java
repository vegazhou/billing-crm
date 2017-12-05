package com.kt.biz.auth;

import com.kt.entity.mysql.user.OrgUser;
import com.kt.exception.InsufficientPrivilegeException;
import com.kt.biz.types.RoleType;
import com.kt.session.Principal;
import com.kt.session.PrincipalContext;

import java.util.List;

/**
 * Created by Vega Zhou on 2015/11/1.
 */
public class PrivilegeChecker {


    /**
     * The principal must be a super admin , otherwise throw IPE
     *
     * @throws InsufficientPrivilegeException
     */
    public static void requireSuperAdmin() throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }
        List<RoleType> roles = principal.getRoles();
        assertContainRole(roles, RoleType.SUPER_ADMIN);
    }

    public static void isContractAuditor() throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }

        List<RoleType> roles = principal.getRoles();
        assertContainRole(roles, RoleType.CONTRACT_AUDITOR);
    }

    public static void isFinAuditor() throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }

        List<RoleType> roles = principal.getRoles();
        assertContainRole(roles, RoleType.FIN_AUDITOR);
    }


    public static void isProductAuditor() throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }

        List<RoleType> roles = principal.getRoles();
        assertContainRole(roles, RoleType.PRODUCT_AUDITOR);
    }


    public static void isOperator() throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }

        List<RoleType> roles = principal.getRoles();
        assertContainRole(roles, RoleType.OPERATOR);
    }


    public static void isAnyOf(RoleType... roles) throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }
        List<RoleType> rolesOwned = principal.getRoles();
        assertContainAnyOf(rolesOwned, roles);
    }


    private static void assertContainRole(List<RoleType> roles, RoleType targetRole) throws InsufficientPrivilegeException {
        if (roles != null) {
            for (RoleType role : roles) {
                if (role == RoleType.SUPER_ADMIN) {
                    return;
                } else if (role == targetRole) {
                    return;
                }
            }
        }
        throw new InsufficientPrivilegeException();
    }

    private static void assertContainAnyOf(List<RoleType> roles, RoleType... targetRoles) throws InsufficientPrivilegeException {
        if (roles != null) {
            for (RoleType role : roles) {
                if (role == RoleType.SUPER_ADMIN) {
                    return;
                }
                if (targetRoles != null) {
                    for (RoleType targetRole : targetRoles) {
                        if (role == targetRole) {
                            return;
                        }
                    }
                }
            }
        }
        throw new InsufficientPrivilegeException();
    }


    public static void is(RoleType role) throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();
        if (principal == null) {
            throw new InsufficientPrivilegeException();
        }

        List<RoleType> roles = PrincipalContext.getPrincipal().getRoles();
        assertContainRole(roles, role);
    }


    private static boolean isPremierTo(RoleType roleType) {
        List<RoleType> roles = PrincipalContext.getPrincipal().getRoles();
        for (RoleType role : roles) {
            if (role.isPremierTo(roleType)) {
                return true;
            }
        }
        return false;
    }


    private static boolean isPremierOrEqual(RoleType roleType) {
        List<RoleType> roles = PrincipalContext.getPrincipal().getRoles();
        for (RoleType role : roles) {
            if (role.isPremierOrEqual(roleType)) {
                return true;
            }
        }
        return false;
    }


    public static void isSelf(OrgUser user) throws InsufficientPrivilegeException {
        Principal principal = PrincipalContext.getPrincipal();

        for (RoleType role : principal.getRoles()) {
            if (role == RoleType.SUPER_ADMIN || role == RoleType.USER_ADMIN) {
                return;
            }
        }


        if (principal.getUserName().equals(user.getUserName())) {
            // self access is allowed
            return;
        }


        throw new InsufficientPrivilegeException();
    }
}
