package com.kt.api.controller;

import com.kt.api.bean.orguser.*;
import com.kt.api.common.APIConstants;
import com.kt.biz.user.RoleTranslator;
import com.kt.common.exception.ApiException;

import com.kt.entity.mysql.user.OrgUser;
import com.kt.exception.*;
import com.kt.service.*;
import com.kt.session.PrincipalContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * OrgUserController.
 */
@Controller
@RequestMapping("/orgs/orgusers")
public class OrgUserController extends BaseController {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(OrgUserController.class);

    /**
     * The org user service.
     */
    @Autowired
    private OrgUserService orgUserService;

    private static final String ORDER_DESC = "desc";

    private static final String FAIL = "FAIL";

    @RequestMapping(value = "/getuserinfor", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrgUser4LoginResponse getUserInfor(HttpServletRequest request) {
        try {
            String name = PrincipalContext.getCurrentUserName();
            OrgUser entity = orgUserService.findByUserName(name);

            if (entity == null) {
                throw new ApiException("apis.orguser.error.loginFailed");
            }

            // orgUserService.save(entity);
            OrgUser4LoginResponse outputBean = new OrgUser4LoginResponse();

            outputBean.setUserId(entity.getPid());
            outputBean.addRoles(RoleTranslator.translate(entity.getRoleId()));
            outputBean.setAccessToken(name);
            outputBean.setUserName(entity.getUserName());
            return outputBean;
        } catch (UserNotFoundException e) {
            throw new ApiException(ExceptionKeys.USER_NOT_FOUND);
        } catch (InsufficientPrivilegeException e) {
            throw new ApiException(ExceptionKeys.INSUFFICIENT_PRIVILEGES);
        }
    }


}
