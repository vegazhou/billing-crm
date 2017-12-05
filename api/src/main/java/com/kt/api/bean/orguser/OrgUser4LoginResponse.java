package com.kt.api.bean.orguser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.biz.types.RoleType;

import java.util.LinkedList;
import java.util.List;

/**
 * The Class OrgUser4Create.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUser4LoginResponse {

	/** The access token. */
	@JsonProperty("token")
	private String accessToken;
	
	/** The user id. */
	@JsonProperty("userId")
	private String userId;
	
	
	@JsonProperty("roles")
	private List<String> roles = new LinkedList<String>();

	@JsonProperty("orgId")
	private String orgId;
	
	
	@JsonProperty("userName")
	private String userName;

	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the access token.
	 *
	 * @param accessToken
	 *            the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void addRole(RoleType role) {
		this.roles.add(role.toString());
	}

	public void addRoles(List<RoleType> roles) {
		for (RoleType role : roles) {
			this.roles.add(role.toString());
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
