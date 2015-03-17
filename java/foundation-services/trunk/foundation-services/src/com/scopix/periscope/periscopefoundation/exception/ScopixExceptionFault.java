/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.periscopefoundation.exception;

/**
 *
 * @author nelson
 */
public class ScopixExceptionFault {

    /**
     * Nombre de la clase que corresponde a la excepción raíz
     */
    private String exceptionType;
    /*
     * Mensaje de la excepción raíz
     */
    private String exceptionMessage;
    /*
     * Stack trace de la excepción
     */
    private String exceptionTrace;

    /**
     * Constructor sin parámetros necesario para el unmarshalling
     */
    public ScopixExceptionFault() {
        super();
    }

    public ScopixExceptionFault(Exception rootException) {
        super();
        this.exceptionType = rootException.getClass().getName();
        this.exceptionMessage = rootException.getMessage();
        StringBuilder stackTraceBuffer = new StringBuilder();
        for (StackTraceElement element : rootException.getStackTrace()) {
            stackTraceBuffer.append(element.toString());
            stackTraceBuffer.append('\n');
        }
        this.exceptionTrace = stackTraceBuffer.toString();
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    /**
     * Setter necesario para el unmarshalling
     *
     * @param exceptionType
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    /**
     * Setter necesario para el unmarshalling
     *
     * @param exceptionMessage
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Setter necesario para el unmarshalling
     *
     * @param exceptionTrace
     */
    public void setExceptionTrace(String exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }
}
