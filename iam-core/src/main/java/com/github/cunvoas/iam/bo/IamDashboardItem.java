package com.github.cunvoas.iam.bo;

public class IamDashboardItem {
    
    public IamDashboardItem(int segment) {
        this.segment=segment;
    }
    
    // monitor segment (hour)
    private int segment;
    
    private long nbVector;
    private long nbVectorRedused;
    private long nbNewUser;
    private long nbNewDelegationDemand;
    private long nbAdminActivity;
    
    public void clear() {
        nbVector=0;
        nbNewUser=0;
        nbNewDelegationDemand=0;
        nbAdminActivity=0;
    }
    
    public void resetUniqueUser() {
        nbNewUser=0;
    }
    
    
    /**
     * Getter for segment.
     * @return the segment
     */
    public int getSegment() {
        return segment;
    }
    /**
     * Setter for segment.
     * @param segment the segment to set
     */
    public void setSegment(int segment) {
        this.segment = segment;
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
    public void addNbVector() {
        this.nbVector++;
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
    public void addNbNewUser() {
        this.nbNewUser++;
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
    public void addNbNewDelegationDemand() {
        this.nbNewDelegationDemand++;
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
    public void addNbAdminActivity() {
        this.nbAdminActivity++;
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
    public void addNbVectorRedused() {
        this.nbVectorRedused++;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("hour=").append(segment);
        sBuilder.append(", access=").append(nbVector);
        sBuilder.append(", refused=").append(nbVectorRedused);
        return sBuilder.toString();
    }
    
    
    
}
