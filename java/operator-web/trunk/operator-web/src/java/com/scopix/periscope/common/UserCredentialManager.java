package com.scopix.periscope.common;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.scopix.periscope.manager.SecurityManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase para procesamiento y validación de la autenticación del usuario
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class UserCredentialManager implements Serializable {

    private static Session mySession;
    private SecurityManager secManager;
    private boolean authenticated = false;
    private static final long serialVersionUID = 3885418527436015957L;
    private static Logger log = Logger.getLogger(UserCredentialManager.class);
    private static final String KEY_USER_MODEL = UserCredentialManager.class.getName() + "_MODEL";

    /**
     * Patrón singleton para retornar una única instancia de la clase
     * 
     * @author carlos polo
     * @version 1.0.0
     * @return UserCredentialManager instancia única de la clase
     * @since 6.0
     * @date 04/02/2013
     */
    public static UserCredentialManager getIntance() {
        return getIntance(getMySession());
    }

    /**
     * Obtiene instancia de la clase
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param zkSession sessión zk inicializada
     * @return UserCredentialManager instancia única de la clase
     * @since 6.0
     * @date 04/02/2013
     */
    public static UserCredentialManager getIntance(Session zkSession) {
        synchronized (zkSession) {
            UserCredentialManager userModel = (UserCredentialManager) zkSession.getAttribute(KEY_USER_MODEL);
            if (userModel == null) {
                userModel = new UserCredentialManager();
                zkSession.setAttribute(KEY_USER_MODEL, userModel);
            }
            return userModel;
        }
    }

    /**
     * Invoca servicio de autenticación del usuario
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param user login del usuario
     * @param password password del usuario
     * @param security número del security a invocar (parametrizado en el applicationContext-cxf.xml)
     * @return Long sessionId
     * @since 6.0
     * @date 04/02/2013
     */
    public Long authenticate(String user, String password, String security) {
        log.info("start, user: [" + user + "], security: [" + security + "] - " + user);
        Long sessionId = null;

        try {
            sessionId = getSecManager().authenticateUser(user, password, security);
            setAuthenticated(true);
        } catch (ScopixException ex) {
            log.error(ex, ex);
        }
        log.info("end, sessionId: " + sessionId + " - " + user);
        return sessionId;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @param authenticated the authenticated to set
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * @return the secManager
     */
    public SecurityManager getSecManager() {
        if (secManager == null) {
            secManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return secManager;
    }

    /**
     * @param secManager the secManager to set
     */
    public void setSecManager(SecurityManager secManager) {
        this.secManager = secManager;
    }

    /**
     * @return the mySession
     */
    public static Session getMySession() {
        if (mySession == null) {
            mySession = Sessions.getCurrent();
        }
        return mySession;
    }

    /**
     * @param mySession the mySession to set
     */
    @SuppressWarnings("static-access")
    public void setMySession(Session mySession) {
        this.mySession = mySession;
    }
}