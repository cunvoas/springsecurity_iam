package com.github.cunvoas.iam.web.front.delegation;

import java.util.Date;


public class DelegationDto {
	private Integer id;
	private String appCode;
	private String appName;

	private String roleCode;
	private String roleName;
	
	private String delegator;
	private String delegate;
	
	private Date startDate;
	private Date endDate;
	
	private String status;

	/**
	 * Getter for id.
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Getter for appCode.
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * Setter for appCode.
	 * @param appCode the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * Getter for appName.
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Setter for appName.
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Getter for roleCode.
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * Setter for roleCode.
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * Getter for roleName.
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Setter for roleName.
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Getter for delegator.
	 * @return the delegator
	 */
	public String getDelegator() {
		return delegator;
	}

	/**
	 * Setter for delegator.
	 * @param delegator the delegator to set
	 */
	public void setDelegator(String delegator) {
		this.delegator = delegator;
	}

	/**
	 * Getter for delegate.
	 * @return the delegate
	 */
	public String getDelegate() {
		return delegate;
	}

	/**
	 * Setter for delegate.
	 * @param delegate the delegate to set
	 */
	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	/**
	 * Getter for startDate.
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setter for startDate.
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter for endDate.
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Setter for endDate.
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter for status.
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter for status.
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
