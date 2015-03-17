/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.periscopefoundation.exception;

import javax.xml.ws.WebFault;
 
/**
 *
 * @author nelson
 */
@WebFault(faultBean = "com.scopix.periscope.periscopefoundation.exception.ScopixExceptionFault", name = "scopixExceptionFault")
public class ScopixWebServiceException extends ScopixException {

    ScopixExceptionFault faultInfo;

    /**
     *
     * @param message mensaje de Excepcion
     */
    public ScopixWebServiceException(String message) {
        super(message);
    }

    /**
     *
     * @param cause Causa de la Excepcion
     */
    public ScopixWebServiceException(Throwable cause) {
        super(cause);
        faultInfo = new ScopixExceptionFault((Exception) cause);
    }

    /**
     *
     * @param message mensaje de Excepcion
     * @param cause Causa de la Excepcion
     */
    public ScopixWebServiceException(String message, Throwable cause) {
        super(message, cause);
        faultInfo = new ScopixExceptionFault((Exception) cause);
    }

    /**
     * Constructor necesario para el unmarshalling
     *
     * @param message
     * @param fault 
     */
    public ScopixWebServiceException(String message, ScopixExceptionFault fault) {
        super(message);
        this.faultInfo = fault;
    }

    /**
     *
     * @return ScopixExceptionFault para las excepciones capturas en un servicio
     */
    public ScopixExceptionFault getFaultInfo() {
        return this.faultInfo;
    }
}
