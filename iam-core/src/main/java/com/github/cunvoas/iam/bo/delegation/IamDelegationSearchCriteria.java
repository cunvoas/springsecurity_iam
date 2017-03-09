package com.github.cunvoas.iam.bo.delegation;

import java.io.Serializable;
import java.util.Date;


/**
 * @author CUNVOAS
 */
public class IamDelegationSearchCriteria implements Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID = 2625469288943407514L;

	private Integer applicationId;
	private String applicationCode;
	
	private String delegatorName;
	private String delegatorUid;
	
	private String delegateName;
	private String delegateUid;

	private Date beginDate;
	private Date endDate;
	private Date searchDate;
	
	private Integer status;
	
	private Boolean alive;
	
	/**
	 * Getter for delegatorName.
	 * @return the delegatorName
	 */
	public String getDelegatorName() {
		return delegatorName;
	}
	/**
	 * Setter for delegatorName.
	 * @param delegatorName the delegatorName to set
	 */
	public void setDelegatorName(String delegatorName) {
		this.delegatorName = delegatorName;
	}
	/**
	 * Getter for delegatorUid.
	 * @return the delegatorUid
	 */
	public String getDelegatorUid() {
		return delegatorUid;
	}
	/**
	 * Setter for delegatorUid.
	 * @param delegatorUid the delegatorUid to set
	 */
	public void setDelegatorUid(String delegatorUid) {
		this.delegatorUid = delegatorUid;
	}
	/**
	 * Getter for delegateName.
	 * @return the delegateName
	 */
	public String getDelegateName() {
		return delegateName;
	}
	/**
	 * Setter for delegateName.
	 * @param delegateName the delegateName to set
	 */
	public void setDelegateName(String delegateName) {
		this.delegateName = delegateName;
	}
	/**
	 * Getter for delegateUid.
	 * @return the delegateUid
	 */
	public String getDelegateUid() {
		return delegateUid;
	}
	/**
	 * Setter for delegateUid.
	 * @param delegateUid the delegateUid to set
	 */
	public void setDelegateUid(String delegateUid) {
		this.delegateUid = delegateUid;
	}
	/**
	 * Getter for applicationId.
	 * @return the applicationId
	 */
	public Integer getApplicationId() {
		return applicationId;
	}
	/**
	 * Setter for applicationId.
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * Getter for applicationCode.
	 * @return the applicationCode
	 */
	public String getApplicationCode() {
		return applicationCode;
	}
	/**
	 * Setter for applicationCode.
	 * @param applicationCode the applicationCode to set
	 */
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	/**
	 * Getter for beginDate.
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * Setter for beginDate.
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
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
	public Integer getStatus() {
		return status;
	}
	/**
	 * Setter for status.
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * Getter for searchDate.
	 * @return the searchDate
	 */
	public Date getSearchDate() {
		return searchDate;
	}
	/**
	 * Setter for searchDate.
	 * @param searchDate the searchDate to set
	 */
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	/**
	 * Getter for alive.
	 * @return the alive
	 */
	public Boolean getAlive() {
		return alive;
	}
	/**
	 * Setter for alive.
	 * @param alive the alive to set
	 */
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	
}
