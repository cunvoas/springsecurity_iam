package com.github.cunvoas.iam.bo;

import java.io.Serializable;
import java.util.Date;

public class IamRawDelegate implements Serializable {
    /** serialVersionUID. */
    private static final long serialVersionUID = 6101337259362510745L;
    private Integer delegateId;
    private String delegateUid;
    private String delegateDescription;
    private Date endDate;
    
    /**
     * Getter for delegateId.
     * @return the delegateId
     */
    public Integer getDelegateId() {
        return delegateId;
    }
    /**
     * Setter for delegateId.
     * @param delegateId the delegateId to set
     */
    public void setDelegateId(Integer delegateId) {
        this.delegateId = delegateId;
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
     * Getter for delegateDescription.
     * @return the delegateDescription
     */
    public String getDelegateDescription() {
        return delegateDescription;
    }
    /**
     * Setter for delegateDescription.
     * @param delegateDescription the delegateDescription to set
     */
    public void setDelegateDescription(String delegateDescription) {
        this.delegateDescription = delegateDescription;
    }
	/**
	 * Getter for  endDate.
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Setter for  endDate.
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
}
