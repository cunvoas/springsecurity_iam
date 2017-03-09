package com.github.cunvoas.iam.web.front.resource.tree;

public class JsTreeState {
    private Boolean opened=Boolean.TRUE;
    private Boolean selected=Boolean.FALSE;
    private Boolean disabled  =Boolean.FALSE;
    
    /**
     * 
     * Getter for opened.
     * @return the opened
     */
    public Boolean getOpened() {
        return opened;
    }
    /**
     * Setter for opened.
     * @param opened the opened to set
     */
    public void setOpened(Boolean opened) {
        this.opened = opened;
    }
    /**
     * Getter for selected.
     * @return the selected
     */
    public Boolean getSelected() {
        return selected;
    }
    /**
     * Setter for selected.
     * @param selected the selected to set
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    /**
     * Getter for disabled.
     * @return the disabled
     */
    public Boolean getDisabled() {
        return disabled;
    }
    /**
     * Setter for disabled.
     * @param disabled the disabled to set
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
    
}
