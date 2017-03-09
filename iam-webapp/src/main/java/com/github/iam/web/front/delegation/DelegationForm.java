package com.github.cunvoas.iam.web.front.delegation;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;

/**
 * Form for application editor.
 * 
 * @author cunvoas
 */
public class DelegationForm {
	private Integer id;

	private Integer idApplication;
	@NotBlank(message = "valid.delegation.application.blank")
	private String applicationLabel;

	// @NotBlank(message="valid.delegation.debut.blank")
	private Date debut;
	// @NotBlank(message="valid.delegation.fin.blank")
	private Date fin;

	@NotBlank(message = "valid.delegation.delegator.blank")
	private String delegator;
	private String delegatorLabel;

	@NotBlank(message = "valid.delegation.delegate.blank")
	private String delegate;
	private String delegateLabel;

	private Integer statusId = DelegationState.ID_DEMAND;
	@NotBlank(message = "valid.delegation.delegate.blank")
	private String statusLabel;

	private List<DelegationRoleForm> roles;

	/**
	 * Getter for idApplication.
	 * 
	 * @return the idApplication
	 */
	public Integer getIdApplication() {
		return idApplication;
	}

	/**
	 * Setter for idApplication.
	 * 
	 * @param idApplication
	 *            the idApplication to set
	 */
	public void setIdApplication(Integer idApplication) {
		this.idApplication = idApplication;
	}

	/**
	 * Getter for debut.
	 * 
	 * @return the debut
	 */
	public Date getDebut() {
		return debut;
	}

	/**
	 * Setter for debut.
	 * 
	 * @param debut
	 *            the debut to set
	 */
	public void setDebut(Date debut) {
		this.debut = debut;
	}

	/**
	 * Getter for fin.
	 * 
	 * @return the fin
	 */
	public Date getFin() {
		return fin;
	}

	/**
	 * Setter for fin.
	 * 
	 * @param fin
	 *            the fin to set
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}

	/**
	 * Getter for delegator.
	 * 
	 * @return the delegator
	 */
	public String getDelegator() {
		return delegator;
	}

	/**
	 * Setter for delegator.
	 * 
	 * @param delegator
	 *            the delegator to set
	 */
	public void setDelegator(String delegator) {
		this.delegator = delegator;
	}

	/**
	 * Getter for delegate.
	 * 
	 * @return the delegate
	 */
	public String getDelegate() {
		return delegate;
	}

	/**
	 * Setter for delegate.
	 * 
	 * @param delegate
	 *            the delegate to set
	 */
	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Getter for roles.
	 * 
	 * @return the roles
	 */
	public List<DelegationRoleForm> getRoles() {
		return roles;
	}

	/**
	 * Setter for roles.
	 * 
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<DelegationRoleForm> roles) {
		this.roles = roles;
	}

	/**
	 * Getter for statusId.
	 * 
	 * @return the statusId
	 */
	public Integer getStatusId() {
		return statusId;
	}

	/**
	 * Setter for statusId.
	 * 
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	/**
	 * Getter for statusLabel.
	 * 
	 * @return the statusLabel
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * Setter for statusLabel.
	 * 
	 * @param statusLabel
	 *            the statusLabel to set
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Getter for delegatorLabel.
	 * 
	 * @return the delegatorLabel
	 */
	public String getDelegatorLabel() {
		return delegatorLabel;
	}

	/**
	 * Setter for delegatorLabel.
	 * 
	 * @param delegatorLabel
	 *            the delegatorLabel to set
	 */
	public void setDelegatorLabel(String delegatorLabel) {
		this.delegatorLabel = delegatorLabel;
	}

	/**
	 * Getter for delegateLabel.
	 * 
	 * @return the delegateLabel
	 */
	public String getDelegateLabel() {
		return delegateLabel;
	}

	/**
	 * Setter for delegateLabel.
	 * 
	 * @param delegateLabel
	 *            the delegateLabel to set
	 */
	public void setDelegateLabel(String delegateLabel) {
		this.delegateLabel = delegateLabel;
	}

	/**
	 * Getter for applicationLabel.
	 * 
	 * @return the applicationLabel
	 */
	public String getApplicationLabel() {
		return applicationLabel;
	}

	/**
	 * Setter for applicationLabel.
	 * 
	 * @param applicationLabel
	 *            the applicationLabel to set
	 */
	public void setApplicationLabel(String applicationLabel) {
		this.applicationLabel = applicationLabel;
	}

}
