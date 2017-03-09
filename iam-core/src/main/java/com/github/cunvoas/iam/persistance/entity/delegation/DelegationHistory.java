package com.github.cunvoas.iam.persistance.entity.delegation;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;

@Entity
@Table(name = "iam_delegation_histo")
public class DelegationHistory implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -7529372445847200327L;

	private Integer id;
	private Date dateStatus;
	private Delegation delegation;
	private DelegationState status;

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "IAM_DELEGATION_HISTO_ID_GENERATOR", sequenceName = "seq_delegation_histo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IAM_DELEGATION_HISTO_ID_GENERATOR")
	@Column(name = "dlg_his_id", unique = true, nullable = false)
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
	 * Getter for dateStatus.
	 * 
	 * @return the dateStatus
	 */
	@Column(name = "dlg_his_dat", nullable = false)
    @Temporal(TemporalType.DATE)
	public Date getDateStatus() {
		return dateStatus;
	}

	/**
	 * Setter for dateStatus.
	 * 
	 * @param dateStatus
	 *            the dateStatus to set
	 */
	public void setDateStatus(Date dateStatus) {
		this.dateStatus = dateStatus;
	}

	/**
	 * Getter for delegation.
	 * 
	 * @return the delegation
	 */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dlg_his_dlg", foreignKey=@ForeignKey(name="fk_dlg_his_dlg"))
	public Delegation getDelegation() {
		return delegation;
	}

	/**
	 * Setter for delegation.
	 * 
	 * @param delegation
	 *            the delegation to set
	 */
	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	/**
	 * Getter for status.
	 * 
	 * @return the status
	 */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=AbstractState.class
    		, cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="dlg_his_status", foreignKey=@ForeignKey(name="fk_dlg_his_status"))
	public DelegationState getStatus() {
		return status;
	}

	/**
	 * Setter for status.
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(DelegationState status) {
		this.status = status;
	}

	/**
	 * Getter for serialversionuid.
	 * 
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
