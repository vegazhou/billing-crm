package com.kt.biz.user;

import com.kt.biz.types.RoleType;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2015/11/8.
 */
public class RoleTranslator {

    public static List<RoleType> translate(String roleIdJointBySemicolon) {
        List<RoleType> results = new LinkedList<>();
        if (StringUtils.isNotBlank(roleIdJointBySemicolon)) {
            String[] roleIds = StringUtils.split(roleIdJointBySemicolon, ";");
            for (String roleId : roleIds) {
                RoleType role = RoleType.valueOf(roleId.trim());
                if (!results.contains(role)) {
                    results.add(role);
                }
            }
        }
        return results;
    }


    public static String translate(List<RoleType> roles) {
        List<String> roleIds = new LinkedList<>();
        for (RoleType role : roles) {
            String roleId = role.toString();
            if (!roleIds.contains(roleId)) {
                roleIds.add(role.toString());
            }
        }
        return StringUtils.join(roleIds, ";");
    }
}
