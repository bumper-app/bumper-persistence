package com.bumper.persistence.dao;



/**
 *
 * @author math
 */
public class DAOException extends Exception{
    
    private final StackTraceElement[] stackTrace;
    private final String innerMessage;
    private final String operation;
    private final String objectAffected;

    DAOException(String operation, String object, String innerMessage, StackTraceElement[] stackTrace) {
        this.objectAffected = object;
        this.operation = operation;
        this.stackTrace = stackTrace;
        this.innerMessage = innerMessage;
    }
    
    /**
     *
     * @return
     */
    public StackTraceElement[] getStackTraceElement(){
        return this.stackTrace;
    }

    /**
     *
     * @return
     */
    public String getInnerMessage() {
        return innerMessage;
    }

    /**
     *
     * @return
     */
    public String getOperation() {
        return operation;
    }
    
    /**
     *
     * @return
     */
    public String getObjectAffected() {
        return objectAffected;
    }
    
}
