package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Product;
import com.kt.service.SearchCriteria;
import com.kt.service.SearchFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Garfield Chen on 2016/3/16.
 */
@Repository
public  class ProductRepositoryImpl implements ProductRepositoryCustom {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ProductRepositoryImpl.class);

	/** The db helper. */
	@Autowired
	private DbHelper dbHelper;


	public Page<Product> findAllProductsByPage(int curPage, SearchCriteria searchorg) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * from b_product where agent_id is null ");
		StringBuilder whereSql = new StringBuilder("");
		if (searchorg.getOrgName() != null) {
			whereSql.append(" and lower(DISPLAY_NAME) LIKE '%").append(searchorg.getOrgName()).append("%'");
		}
		if (searchorg.getStatus() != null) {
			whereSql.append(" and state ='").append(searchorg.getStatus()).append("'");
		}

		sql.append(whereSql.toString());

		if (searchorg.getOrderByField() != null && searchorg.getOrderByField().length() > 0
				&& searchorg.getOrderType() != null && searchorg.getOrderType().length() > 0) {
			sql.append(" ORDER BY ").append(searchorg.getOrderByField())
					.append(" ").append(searchorg.getOrderType());
		}
		Page<Product> page = null;
		try {
			page = dbHelper.getPage(sql.toString(), Product.class,
					curPage, searchorg.getPageSize(), new Object[]{});
		} catch (SQLException e) {
			LOGGER.error("SQLException", e);
		}
		return page;

	}
	
	
	
	public Page<Product> findProductsForAgent(int curPage, SearchFilter searchorg) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * from b_product where pid !=' ' ");
		StringBuilder whereSql = new StringBuilder("");
		if (searchorg.getDisplayName() != null) {
			whereSql.append(" and lower(DISPLAY_NAME) LIKE '%").append(searchorg.getDisplayName()).append("%'");
		}
		if (searchorg.getCustomerId() != null) {
			whereSql.append(" and AGENT_ID ='").append(searchorg.getCustomerId()).append("'");
		}

		sql.append(whereSql.toString());

		if (searchorg.getOrderByField() != null && searchorg.getOrderByField().length() > 0
				&& searchorg.getOrderType() != null && searchorg.getOrderType().length() > 0) {
			sql.append(" ORDER BY ").append(searchorg.getOrderByField())
					.append(" ").append(searchorg.getOrderType());
		}
		Page<Product> page = null;
		try {
			page = dbHelper.getPage(sql.toString(), Product.class,
					curPage, searchorg.getPageSize(), new Object[]{});
		} catch (SQLException e) {
			LOGGER.error("SQLException", e);
		}
		return page;

	}



	private static final String SQL_1 = "select p.*, c.type as CHARGE_TYPE from b_product p inner join b_charge_scheme c " +
			"on p.charge_scheme_id = c.id where p.state = 'IN_EFFECT' and (agent_id is null or agent_id = '') and p.is_agent = 0 order by p.display_name asc";

	@Override
	public List<Product> findInEffectDirectProducts() {
		try {
			return dbHelper.getBeanList(SQL_1, Product.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	private static final String SQL_3 = "select t1.*, c.type as CHARGE_TYPE  from " +
			"(" +
			"select p.* from b_product p " +
			"where p.state = 'IN_EFFECT' and p.is_agent = 1 and (agent_id is null or agent_id = '') and " +
			"p.pid not in (select direct_product from b_product p where p.state = 'IN_EFFECT' and agent_id = ?) " +
			"union all " +
			"select p.* from b_product p " +
			"where p.state = 'IN_EFFECT' and p.is_agent = 1 and agent_id = ? " +
			") t1 inner join b_charge_scheme c " +
			"on t1.charge_scheme_id = c.id " +
			"order by t1.display_name asc";
	@Override
	public List<Product> findAvailableAgentProducts(String agentId) {
		try {
			return dbHelper.getBeanList(SQL_3, Product.class, agentId, agentId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final String SQL_4 = "select p.*, c.type as CHARGE_TYPE from b_product p inner join b_charge_scheme c " +
			"on p.charge_scheme_id = c.id where agent_id = ? order by p.display_name asc";
	@Override
	public List<Product> findAgentProducts(String agentId) {
		try {
			return dbHelper.getBeanList(SQL_4, Product.class, agentId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final String SQL_2 = "select * from b_product where AGENT_ID = ? and DIRECT_PRODUCT = ? AND STATE = 'IN_EFFECT'";
	@Override
	public List<Product> findInEffectAgentProduct(String agentId, String publicAgentProductId) {
		try {
			return dbHelper.getBeanList(SQL_2, Product.class, agentId, publicAgentProductId);
		} catch (SQLException e) {
			e.printStackTrace();
			return new LinkedList<>();
		}
	}


	private static final String SQL_5 = "select p.*, c.type as CHARGE_TYPE from b_product p inner join b_charge_scheme c " +
			"on p.charge_scheme_id = c.id " +
			"where p.state = 'IN_EFFECT' and (p.agent_id is null or p.agent_id = '') and p.is_agent = 1 " +
			"order by p.display_name asc";
	@Override
	public List<Product> findInEffectPublicAgentProducts() {
		try {
			return dbHelper.getBeanList(SQL_5, Product.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return new LinkedList<>();
		}
	}
}
