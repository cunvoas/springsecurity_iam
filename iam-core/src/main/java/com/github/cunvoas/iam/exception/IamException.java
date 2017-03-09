package com.github.cunvoas.iam.exception;

import java.util.Arrays;

/**
 * @author CUNVOAS
 */
public class IamException extends RuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = -7047210165242205961L;
    
    public static final String BUSINESS_ERROR="BUSINESS_ERROR {}";
    private static final String BUSINESS_ERROR_CODE="RULE_";
   
    private String code;
    private String[] stack;
    private String[] details;
    private boolean businessRuleViolated = false;
    
    public IamException() {
        super();
    }
    
    public IamException(String message) {
        super(message);
        this.code=message;
        businessRuleViolated = message!=null && message.trim().startsWith(BUSINESS_ERROR_CODE);
    }
    
    public IamException(String message, String[] details) {
        super(message);
        this.code=message;
        businessRuleViolated = message!=null && message.trim().startsWith(BUSINESS_ERROR_CODE);
        this.details=Arrays.copyOf(details, details.length);
    }
    
    public IamException(String message, Throwable th) {
        super(message);
        this.code=message;
        businessRuleViolated = message!=null && message.trim().startsWith(BUSINESS_ERROR_CODE);
        initStack(th);
    }
        
    
    public IamException(Throwable th) {
        super(th.getMessage());
        initStack(th);
    }
    
    private void initStack(Throwable th) {
        StackTraceElement[] elts = th.getStackTrace();
        stack = new String[elts.length];
        for (int i = 0; i < elts.length; i++) {
            StackTraceElement stackTraceElement = elts[i];
            stack[i] = stackTraceElement.toString();
        }
    }

    /**
     * Getter for stack.
     * @return the stack
     */
    public String[] getStack() {
        return Arrays.copyOf(stack, stack.length);
    }
    
    public String getStackString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(getMessage());
        sBuilder.append("\n");
        
        for (String st : stack) {
            sBuilder.append("\t");
            sBuilder.append(st);
            sBuilder.append("\n");
        }
        return sBuilder.toString();
    }

    /**
     * Getter for details.
     * @return the details
     */
    public String[] getDetails() {
        return Arrays.copyOf(details, details.length);
    }

    /**
     * Getter for businessRuleViolated.
     * @return the businessRuleViolated
     */
    public boolean isBusinessRuleViolated() {
        return businessRuleViolated;
    }

    /**
     * Getter for code.
     * @return the code
     */
    public String getCode() {
        return code;
    }


}
