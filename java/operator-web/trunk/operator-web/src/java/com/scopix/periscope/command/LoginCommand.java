package com.scopix.periscope.command;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;

/**
 * Clase intermediaria entre la capa web y la de servicios para la invocación de servicios relacionados con la autenticación en la
 * aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class LoginCommand implements Serializable {

    private static final long serialVersionUID = -145200945592657047L;
    private static Logger log = Logger.getLogger(LoginCommand.class);

    /**
     * Invocación servicio para autenticar a un usuario
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param login login del usuario
     * @param password password del usuario
     * @param security número del security a invocar (parametrizado en el applicationContext-cxf.xml)
     * @return Long SESSION_ID
     * @since 6.0
     * @date 18/02/2013
     * @throws ScopixException excepción en la autenticación del usuario
     */
    public Long execute(String login, String password, String security) throws ScopixException {
        log.info("start - " + login);
        Long ret;
        try {
            SecurityWebServices securityService = (SecurityWebServices) SpringSupport.getInstance().findBean(
                    "SecurityWebServicesClient-" + security);

            ret = securityService.login(login, password, 0);
        } catch (ScopixWebServiceException e) {
            log.error(e.getMessage(), e);
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end - " + login);
        return ret;
    }
}