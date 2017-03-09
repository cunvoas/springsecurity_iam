package com.github.cunvoas.iam.web.front.application;

import org.springframework.web.multipart.MultipartFile;

/**
 * Form for application editor.
 * @author cunvoas
 */
public class ApplicationUploadForm {

    private Integer id;
    
    private MultipartFile file;

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

    /**
     * Getter for file.
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * Setter for file.
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
    
    
    

}
