package com.github.cunvoas.iam.bo;

/**
 * @author CUNVOAS
 */
public class IamDashboard {
    // nbr of refereced applications
    private long nbApplication;
    
    // unique user 0:00 > 23:59
    private long nbNewUser;
    
    // nbr of IAM vercor served
    private long nbVector;
    private long nbVectorRedused;
    
    // delegation activity
    private long nbNewDelegationDemand;

    // admin activity
    private long nbAdminActivity;
    

    /**
     * Getter for nbApplication.
     * @return the nbApplication
     */
    public long getNbApplication() {
        return nbApplication;
    }

    /**
     * Setter for nbApplication.
     * @param nbApplication the nbApplication to set
     */
    public void setNbApplication(long nbApplication) {
        this.nbApplication = nbApplication;
    }

    /**
     * Getter for nbVector.
     * @return the nbVector
     */
    public long getNbVector() {
        return nbVector;
    }

    /**
     * Setter for nbVector.
     * @param nbVector the nbVector to set
     */
    public void setNbVector(long nbVector) {
        this.nbVector = nbVector;
    }

    /**
     * Getter for nbNewUser.
     * @return the nbNewUser
     */
    public long getNbNewUser() {
        return nbNewUser;
    }

    /**
     * Setter for nbNewUser.
     * @param nbNewUser the nbNewUser to set
     */
    public void setNbNewUser(long nbNewUser) {
        this.nbNewUser = nbNewUser;
    }

    /**
     * Getter for nbNewDelegationDemand.
     * @return the nbNewDelegationDemand
     */
    public long getNbNewDelegationDemand() {
        return nbNewDelegationDemand;
    }

    /**
     * Setter for nbNewDelegationDemand.
     * @param nbNewDelegationDemand the nbNewDelegationDemand to set
     */
    public void setNbNewDelegationDemand(long nbNewDelegationDemand) {
        this.nbNewDelegationDemand = nbNewDelegationDemand;
    }

    /**
     * Getter for nbAdminActivity.
     * @return the nbAdminActivity
     */
    public long getNbAdminActivity() {
        return nbAdminActivity;
    }

    /**
     * Setter for nbAdminActivity.
     * @param nbAdminActivity the nbAdminActivity to set
     */
    public void setNbAdminActivity(long nbAdminActivity) {
        this.nbAdminActivity = nbAdminActivity;
    }
    
    public void add(IamDashboardItem item) {
        this.nbAdminActivity += item.getNbAdminActivity();
        this.nbVector += item.getNbVector();
        this.nbVectorRedused += item.getNbVectorRedused();
        this.nbNewDelegationDemand += item.getNbNewDelegationDemand();
        this.nbNewUser += item.getNbNewUser();
    }

    /**
     * Getter for nbVectorRedused.
     * @return the nbVectorRedused
     */
    public long getNbVectorRedused() {
        return nbVectorRedused;
    }

    /**
     * Setter for nbVectorRedused.
     * @param nbVectorRedused the nbVectorRedused to set
     */
    public void setNbVectorRedused(long nbVectorRedused) {
        this.nbVectorRedused = nbVectorRedused;
    }
    
    

}
