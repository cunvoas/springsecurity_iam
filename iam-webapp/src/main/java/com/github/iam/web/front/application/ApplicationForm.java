package com.github.cunvoas.iam.web.front.application;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Form for application editor.
 * @author cunvoas
 */
public class ApplicationForm {

    private Integer id;
    
    @NotBlank(message="valid.application.code.blank")
    @Length(min=3, max=50, message="valid.application.code.len")
    @Pattern(regexp="(^[A-Z]{1}[A-Z0-9_]*$)", message="valid.application.code.pattern")
    private String code;
    
    @Length(max=200, message="valid.application.description.len")
    private String description;
    
    @URL(message="valid.application.url")
    @Length(max=300, message="valid.application.url.len")
    private String url;

    /**
     * Getter for  id.
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for  id.
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for  code.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for  code.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for  description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for  description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for  url.
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for  url.
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    

}
