package com.kt.service;

import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.bean.CiUserBean;
import com.kt.biz.types.RoleType;
import com.kt.biz.user.RoleTranslator;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.exception.InsufficientPrivilegeException;
import com.kt.exception.UserAlreadyExistedException;
import com.kt.exception.UserNotFoundException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.orguser.OrgUserRepository;
import com.kt.util.CiClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Class OrgUserService.
 */
@Service
public class OrgUserService {

    private static final Logger LOGGER = Logger.getLogger(OrgUserService.class);

    @Autowired
    private OrgUserRepository orgUserRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrgUser createUser(String userName, List<RoleType> roles) throws WafException {
        PrivilegeChecker.is(RoleType.USER_ADMIN);

        if (StringUtils.isBlank(userName)) {
            throw new UserNotFoundException();
        }
        userName = userName.trim().toLowerCase();

        OrgUser existingUser = orgUserRepository.findOneByUserName(userName);
        if (existingUser != null) {
            throw new UserAlreadyExistedException();
        }

        CiUserBean ciUser = CiClient.getUserInfoByEmail(userName);
        if (ciUser == null) {
            throw new UserNotFoundException();
        }

        OrgUser user = new OrgUser();
        user.setFullName(ciUser.getFullName());
        user.setUserName(ciUser.getUserName());
        user.setRoleId(RoleTranslator.translate(roles));
        return orgUserRepository.save(user);
    }


    public OrgUser findByUserName(String username) throws InsufficientPrivilegeException, UserNotFoundException {
        OrgUser user = _doFindByUserName(username);
        if (user == null) {
            throw new UserNotFoundException();
        }

        PrivilegeChecker.isSelf(user);
        return user;
    }
    
    
    /**
     * this function is only for internal calls, because it didn't check the caller's privilege
     *
     * @param username the account name to be fetched
     * @return the user found
     */
    public OrgUser _doFindByUserName(String username) {
        return orgUserRepository.findOneByUserName(username.toLowerCase());
    }



    public List<OrgUser> findByRole(RoleType role) {
        return orgUserRepository.findByRole(role);
    }
    
    













   
    
}
