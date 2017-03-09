package com.github.cunvoas.iam.web.front.json;

public class JsonErrorMessage {
    private String rule;
    private String message;
    
    /**
     * Getter for rule.
     * @return the rule
     */
    public String getRule() {
        return rule;
    }
    /**
     * Setter for rule.
     * @param rule the rule to set
     */
    public void setRule(String rule) {
        this.rule = rule;
    }
    /**
     * Getter for message.
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * Setter for message.
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}
