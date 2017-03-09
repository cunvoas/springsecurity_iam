package com.github.cunvoas.iam.web.front.resource.tree;

import java.util.Map;

import com.github.cunvoas.iam.bo.IamRessource;


public class JsTreeResource {
    
    /**
     * Default Constructor.
     */
    public JsTreeResource() {
        super();
    }
    
    /**
     * Constructor.
     * @param ress
     * @param mapValeur
     */
    public JsTreeResource(IamRessource ress, Map<Integer, String> mapValeur) {
        super();
        this.id=String.valueOf(ress.getId());
        this.parent = ress.getParent()!=null?String.valueOf(ress.getParent().getId()):"#";
        this.text = ress.getCode();
        this.icon = ress.getParent()==null?"fa fa-desktop":ress.isNode()?"fa fa-folder":"fa fa-bookmark-o";
        
        if (mapValeur!=null && ress.getValeur()!=null && !ress.isNode()) {
            this.attr.setResourceValue(String.valueOf(ress.getValeur()));
            this.attr.setResourceText(mapValeur.get(ress.getValeur()));
        }
    }
    
    private String id;        //id
    private String parent;    // parendId
    private String text;    // code
    private String icon ; //fa-folder / fa-bookmark-o
    private JsTreeState state=new JsTreeState();
    private JsTreeData attr=new JsTreeData();
    
    private String parentOrg;    // parendId original
    private String position;    // new position
    
    /**
     * Getter for id.
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * Setter for id.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Getter for parent.
     * @return the parent
     */
    public String getParent() {
        return parent;
    }
    /**
     * Setter for parent.
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }
    /**
     * Getter for text.
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * Setter for text.
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * Getter for icon.
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }
    /**
     * Setter for icon.
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * Getter for state.
     * @return the state
     */
    public JsTreeState getState() {
        return state;
    }
    /**
     * Setter for state.
     * @param state the state to set
     */
    public void setState(JsTreeState state) {
        this.state = state;
    }
    /**
     * Getter for  attr.
     * @return the attr
     */
    public JsTreeData getAttr() {
        return attr;
    }
    /**
     * Setter for  attr.
     * @param attr the attr to set
     */
    public void setAttr(JsTreeData attr) {
        this.attr = attr;
    }

    /**
     * Getter for  parentOrg.
     * @return the parentOrg
     */
    public String getParentOrg() {
        return parentOrg;
    }

    /**
     * Setter for  parentOrg.
     * @param parentOrg the parentOrg to set
     */
    public void setParentOrg(String parentOrg) {
        this.parentOrg = parentOrg;
    }

    /**
     * Getter for  position.
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Setter for  position.
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    
}
