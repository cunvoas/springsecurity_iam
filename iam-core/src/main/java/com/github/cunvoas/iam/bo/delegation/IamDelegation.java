package com.github.cunvoas.iam.bo.delegation;

import java.util.Date;
import java.util.List;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRole;

/**
 * Delegation for IAM.
 * @author CUNVOAS
 */
public class IamDelegation {
    
	private Integer id;
    private IamApplication application;    // application that concerns delegation
    private List<IamRole> roles;    // roles that concerns delegation
    
    private String uidDelegator;    // uid of person which give the delegation
    private String uidDelegate;    // uid of person which gain the delegation
    
    private Date begin;
    private Date end;
    
    private IamDelegationState state;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IamDelegation)) {
            return false;
        }
        IamDelegation other = (IamDelegation) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Getter for uidDelegator.
     * @return the uidDelegator
     */
    public String getUidDelegator() {
        return uidDelegator;
    }

    /**
     * Setter for uidDelegator.
     * @param uid the uidDelegator to set
     */
    public void setUidDelegator(String uidDelegator) {
        this.uidDelegator = uidDelegator;
    }

    /**
     * Getter for application.
     * @return the application
     */
    public IamApplication getApplication() {
        return application;
    }

    /**
     * Setter for application.
     * @param application the application to set
     */
    public void setApplication(IamApplication application) {
        this.application = application;
    }

    /**
     * Getter for roles.
     * @return the roles
     */
    public List<IamRole> getRoles() {
        return roles;
    }

    /**
     * Setter for roles.
     * @param roles the roles to set
     */
    public void setRoles(List<IamRole> roles) {
        this.roles = roles;
    }

    /**
     * Getter for uidDelegate.
     * @return the uidDelegate
     */
    public String getUidDelegate() {
        return uidDelegate;
    }

    /**
     * Setter for uidDelegate.
     * @param uidDelegate the uidDelegate to set
     */
    public void setUidDelegate(String uidDelegate) {
        this.uidDelegate = uidDelegate;
    }

    /**
     * Getter for begin.
     * @return the begin
     */
    public Date getBegin() {
        return (Date)begin.clone();
    }

    /**
     * Setter for begin.
     * @param begin the begin to set
     */
    public void setBegin(Date begin) {
        this.begin = begin;
    }

    /**
     * Getter for end.
     * @return the end
     */
    public Date getEnd() {
        return  (Date)end.clone();
    }

    /**
     * Setter for end.
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Getter for state.
     * @return the state
     */
    public IamDelegationState getState() {
        return state;
    }

    /**
     * Setter for state.
     * @param state the state to set
     */
    public void setState(IamDelegationState state) {
        this.state = state;
    }

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

    
    
}
