package com.github.cunvoas.iam.web.front.delegation;

/**
 * @author cunvoas
 */
public class DelegationStateDto {
	
	private Integer stateId;
	private String stateLabel;
	
	// change when page is displayed
	//private boolean changeToRead;
	private boolean changeToCancel;
	private boolean changeToAccepted;
	private boolean changeToRefused;
	
	/**
	 * Getter for  stateId.
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}
	/**
	 * Setter for  stateId.
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	/**
	 * Getter for  stateLabel.
	 * @return the stateLabel
	 */
	public String getStateLabel() {
		return stateLabel;
	}
	/**
	 * Setter for  stateLabel.
	 * @param stateLabel the stateLabel to set
	 */
	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
	/**
	 * Getter for  changeToCancel.
	 * @return the changeToCancel
	 */
	public boolean isChangeToCancel() {
		return changeToCancel;
	}
	/**
	 * Setter for  changeToCancel.
	 * @param changeToCancel the changeToCancel to set
	 */
	public void setChangeToCancel(boolean changeToCancel) {
		this.changeToCancel = changeToCancel;
	}
	/**
	 * Getter for  changeToAccepted.
	 * @return the changeToAccepted
	 */
	public boolean isChangeToAccepted() {
		return changeToAccepted;
	}
	/**
	 * Setter for  changeToAccepted.
	 * @param changeToAccepted the changeToAccepted to set
	 */
	public void setChangeToAccepted(boolean changeToAccepted) {
		this.changeToAccepted = changeToAccepted;
	}
	/**
	 * Getter for  changeToRefused.
	 * @return the changeToRefused
	 */
	public boolean isChangeToRefused() {
		return changeToRefused;
	}
	/**
	 * Setter for  changeToRefused.
	 * @param changeToRefused the changeToRefused to set
	 */
	public void setChangeToRefused(boolean changeToRefused) {
		this.changeToRefused = changeToRefused;
	}
	
	

}
