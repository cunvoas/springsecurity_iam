package com.github.cunvoas.iam.web.front.resource;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Form for application editor.
 * @author cunvoas
 */
public class SearchResValForm {

    private String appId;
    @NotBlank(message="valid.reslval.application.code.blank")
    private String appCode;
    private String appDesc;
    
    private String roleId;
    @NotBlank(message="valid.reslval..application.code.blank")
    private String roleCode;
    private String roleDesc;
    
    // use only for the form
    private Integer valId;
    
    /**
     * Getter for appId.
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }
    /**
     * Setter for appId.
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }
    /**
     * Getter for appCode.
     * @return the appCode
     */
    public String getAppCode() {
        return appCode;
    }
    /**
     * Setter for appCode.
     * @param appCode the appCode to set
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
    /**
     * Getter for roleId.
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }
    /**
     * Setter for roleId.
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    /**
     * Getter for roleCode.
     * @return the roleCode
     */
    public String getRoleCode() {
        return roleCode;
    }
    /**
     * Setter for roleCode.
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    /**
     * Getter for appDesc.
     * @return the appDesc
     */
    public String getAppDesc() {
        return appDesc;
    }
    /**
     * Setter for appDesc.
     * @param appDesc the appDesc to set
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }
    /**
     * Getter for roleDesc.
     * @return the roleDesc
     */
    public String getRoleDesc() {
        return roleDesc;
    }
    /**
     * Setter for roleDesc.
     * @param roleDesc the roleDesc to set
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
    
    /**
     * Getter for valId.
     * @return the valId
     */
    public Integer getValId() {
        return valId;
    }
    /**
     * Setter for valId.
     * @param valId the valId to set
     */
    public void setValId(Integer valId) {
        this.valId = valId;
    }
  

}
