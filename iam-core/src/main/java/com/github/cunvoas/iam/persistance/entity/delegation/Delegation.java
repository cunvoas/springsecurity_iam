package com.github.cunvoas.iam.persistance.entity.delegation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;
import com.github.cunvoas.iam.persistance.entity.delegation.state.DemandState;


/**
 * The persistent class for the iam_application database table.
 * 
 */
@Entity
@Table(name="iam_delegation")
@NamedQueries({
    @NamedQuery(name="Delegation.findCurrent", query="SELECT d FROM Delegation d where d.begin<=current_date() and current_date()<=d.end and d.status.id=? and d.delege=?")
})
public class Delegation implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String delegateur;
    private String delege;
    
    private Application application;
    private List<DelegationRole> delegationRoles;
    
    private Date begin;
    private Date end;
    
    private AbstractState status = new DemandState();
    private Date statusDate;
    
    
    private List<DelegationHistory> historique;

    public Delegation() {
    }

    @Id
    @SequenceGenerator(name="IAM_DELEGATION_ID_GENERATOR", sequenceName="seq_delegation")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IAM_DELEGATION_ID_GENERATOR")
    @Column(name="dlg_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="dlg_delegator_id", nullable=false, length=50)
    public String getDelegateur() {
        return this.delegateur;
    }

    public void setDelegateur(String delegateur) {
        this.delegateur = delegateur;
    }
    
    @Column(name="dlg_delegate_id", nullable=false, length=50)
    public String getDelege() {
        return this.delege;
    }

    public void setDelege(String delege) {
        this.delege = delege;
    }


    //bi-directional many-to-one association to Application
    @ManyToOne(fetch=FetchType.LAZY, optional=false, cascade={CascadeType.ALL})
    @JoinColumn(name="dlg_app_id", foreignKey=@ForeignKey(name="fk_dlg_app"))
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
	/**
	 * Getter for begin.
	 * @return the begin
	 */
    @Column(name="dlg_begin", nullable=false)
    @Temporal(TemporalType.DATE)
	public Date getBegin() {
		return begin;
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
    @Column(name="dlg_end", nullable=false)
    @Temporal(TemporalType.DATE)
	public Date getEnd() {
		return end;
	}


	/**
	 * Setter for end.
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	

	/**
	 * Getter for status.
	 * @return the status
	 */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=AbstractState.class
    		, cascade={CascadeType.PERSIST,CascadeType.ALL})
    @JoinColumn(name="dlg_status", foreignKey=@ForeignKey(name="fk_dlg_status"))
	public AbstractState getStatus() {
		return status;
	}

	/**
	 * Setter for status.
	 * @param status the status to set
	 */
	public void setStatus(AbstractState status) {
		this.status = status;
	}

	/**
	 * Getter for statusDate.
	 * @return the statusDate
	 */
	@Column(name="dlg_status_dat", nullable=false)
    @Temporal(TemporalType.DATE)
	public Date getStatusDate() {
		return statusDate;
	}

	/**
	 * Setter for statusDate.
	 * @param statusDate the statusDate to set
	 */
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	

	/**
	 * Getter for DelegationRole.
	 * @return the DelegationRole
	 */
	@OneToMany(mappedBy="delegation")
	public List<DelegationRole> getDelegationRoles() {
		return delegationRoles;
	}

	/**
	 * Setter for DelegationRole.
	 * @param DelegationRole the DelegationRole to set
	 */
	public void setDelegationRoles(List<DelegationRole> delegationRoles) {
		this.delegationRoles = delegationRoles;
	}
	
    //bi-directional many-to-one association to Ressource
    @OneToMany(mappedBy="delegation", fetch=FetchType.LAZY)
    public List<DelegationHistory> getHistorique() {
        return this.historique;
    }

    public void setHistorique(List<DelegationHistory> historique) {
        this.historique = historique;
    }


    
   

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
        if (!(obj instanceof Delegation)) {
            return false;
        }
        Delegation other = (Delegation) obj;
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
     * @param newState
     */
    private void changeState(DelegationState newState) {
		this.setStatusDate(new Date());
		this.setStatus((AbstractState)newState);
		if (this.getHistorique()==null) {
			this.setHistorique(new ArrayList<DelegationHistory>());
		}
		DelegationHistory dHistory = new DelegationHistory();
		dHistory.setDateStatus(this.getStatusDate());
		dHistory.setDelegation(this);
		dHistory.setStatus(newState);
		this.getHistorique().add(dHistory);
    }

    /**
     * change to the READ state.
     * @param state
     */
	public void read() {
		DelegationState newState = this.getStatus().read();
		changeState(newState);
	}

    
    /**
     * change to the ACCEPTED state.
     * @param state
     */
	public void accept() {
		DelegationState newState = this.getStatus().accept();
		changeState(newState);
	}


    /**
     * change to the REFUSED state.
     * @param state
     */
	public void  refuse() {
		DelegationState newState = this.getStatus().refuse();
		changeState(newState);
	}

    
    /**
     * change to the EXPIRED state.
     * @param state
     */
	public void  expire() {
		DelegationState newState = this.getStatus().expire();
		changeState(newState);
	}

    
    /**
     * change to the CANCELED state.
     * @param state
     */
	public void  cancel() {
		DelegationState newState = this.getStatus().cancel();
		changeState(newState);
	}

    /**
     * change to the TERMINATED state.
     * @param state
     */
	public void  terminate() {
		DelegationState newState = this.getStatus().terminate();
		changeState(newState);
	}
	
	
	/**
	 * Change to another state.
	 * @param newState
	 */
	public void  changeTo(DelegationState newState) {
		
//		if (newState.getId() == this.getStatus().getId()) {
//			// if object instance is same => break
//			return ;
//		}
		
		// first demand
		if (DelegationState.STATE_DEMAND.equals(newState.getId())) {
			changeState(newState);
			
		} else if (DelegationState.STATE_READ.equals(newState.getId())) {
			this.read();
			
		} else if (DelegationState.STATE_CANCELED.equals(newState.getId())) {
			this.cancel();
			
		} else if (DelegationState.STATE_ACCEPTED.equals(newState.getId())) {
			this.accept();
			
		} else if (DelegationState.STATE_REFUSED.equals(newState.getId())) {
			this.refuse();
			
		} else if (DelegationState.STATE_EXPIRED.equals(newState.getId())) {
			this.expire();
			
		} else if (DelegationState.STATE_TERMINATED.equals(newState.getId())) {
			this.terminate();
			
		}
		
	}


    
    

}