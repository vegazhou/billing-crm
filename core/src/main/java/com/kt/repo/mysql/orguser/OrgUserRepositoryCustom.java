package com.kt.repo.mysql.orguser;

import com.kt.biz.types.RoleType;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.service.SearchCriteria;

import java.sql.SQLException;
import java.util.List;

/**
 * The Interface OrgUserRepositoryCustom.
 */
public interface OrgUserRepositoryCustom {


	List<OrgUser> findByRole(RoleType role);

	Page<OrgUser> findAllByPage(int curPage, SearchCriteria searchorguser) throws SQLException;
}
