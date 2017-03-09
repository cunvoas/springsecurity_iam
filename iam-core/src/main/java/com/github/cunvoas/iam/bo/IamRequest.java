package com.github.cunvoas.iam.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Request from IAM Habilitation Vector.
 * @author CUNVOAS
 */
public class IamRequest {
    
    @NotEmpty
    private String application;
    
    @NotEmpty
    private String userId;

    /**
     * Getter for application.
     * @return the application
     */
    public String getApplication() {
        return application;
    }

    /**
     * Setter for application.
     * @param application the application to set
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Getter for userId.
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter for userId.
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    

}
