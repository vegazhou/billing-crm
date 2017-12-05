package com.kt.repo.mysql.orguser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kt.biz.types.RoleType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.service.SearchCriteria;

/**
 * The Class OrgUserRepositoryImpl.
 */
@Repository
public class OrgUserRepositoryImpl implements OrgUserRepositoryCustom {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(OrgUserRepositoryImpl.class);

	/** The db helper. */
	@Autowired
	private DbHelper dbHelper;

	private static final String SQL_FIND_BY_ROLE = "select * from kt_b_user where role_id like ?";

	@Override
	public List<OrgUser> findByRole(RoleType role) {
		try {
			return dbHelper.getBeanList(SQL_FIND_BY_ROLE, OrgUser.class, "%" + role.toString() + "%");
		} catch (SQLException e) {
			LOGGER.error("SQLException occurred while findByRole " + role.toString(), e);
			return null;
		}
	}

	public Page<OrgUser> findAllByPage(int curPage, SearchCriteria searchorguser)
			throws SQLException {

		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * from kt_core_orguser where pid !=' ' ");
		StringBuilder whereSql = new StringBuilder("");
		if(searchorguser.getUserAccount() != null){
			whereSql.append(" and userName LIKE '%").append(searchorguser.getUserAccount()).append("%'");
		}
		if(searchorguser.getUserName() != null){
			whereSql.append(" and fullName LIKE '%").append(searchorguser.getUserName()).append("%'");
		}
		if(searchorguser.getUserPhone() != null){
			whereSql.append(" and mobile LIKE '%").append(searchorguser.getUserPhone()).append("%'");
		}
		
		
		if(searchorguser.getOrgId() != null){
			whereSql.append(" and org_id = '").append(searchorguser.getOrgId()).append("'");
		}
		
		sql.append(whereSql.toString());

		if (searchorguser.getOrderByField() != null && searchorguser.getOrderByField().length() > 0
				&& searchorguser.getOrderType() != null && searchorguser.getOrderType().length() > 0) {
			sql.append(" ORDER BY ").append(searchorguser.getOrderByField())
					.append(" ").append(searchorguser.getOrderType());
		}
		Page<OrgUser> page = dbHelper.getPage(sql.toString(), OrgUser.class,
				curPage, searchorguser.getPageSize(), new Object[] {});
		return page;

	}

}
