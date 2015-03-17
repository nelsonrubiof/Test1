/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * LogoutCommand.java
 * 
 * Created on 19-11-2013, 12:28:20 PM
 */
package com.scopix.periscope.command;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;

/**
 * Clase intermediaria entre la capa web y la de servicios para la invocación de servicios relacionados con el cierre de sesión de
 * la aplicación
 * 
 * @author carlos polo
 * @version 2.0.0
 * @since 6.0
 */
public class LogoutCommand implements Serializable {

    private static final long serialVersionUID = -145200945592657047L;
    private static Logger log = Logger.getLogger(LogoutCommand.class);

    /**
     * Invocación del servicio para cerrar la sesión del usuario
     * 
     * @author carlos polo
     * @version 2.0.0
     * @param sessionId token del usuario autenticado
     * @param security número del security a invocar (parametrizado en el applicationContext-cxf.xml)
     * @since 6.0
     * @date 19/11/2013
     */
    public void execute(Long sessionId, String security) {
        log.info("start, sessionId: [" + sessionId + "], security: [" + security + "]");
        try {
            SecurityWebServices evaluationRoutingService = (SecurityWebServices) SpringSupport.getInstance().findBean(
                    "SecurityWebServicesClient-" + security);

            evaluationRoutingService.logout(sessionId);
        } catch (ScopixWebServiceException e) {
            log.error(e.getMessage(), e); // deja fluir el proceso
        }
        log.info("end");
    }
}