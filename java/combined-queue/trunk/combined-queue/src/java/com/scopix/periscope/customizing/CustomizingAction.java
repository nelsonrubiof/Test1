/*
 * 
 * Copyright � 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CustomizingAction.java
 *
 * Created on 30/10/2013
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.customizing;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.securitymanagement.exception.ScopixSecurityException;
/**
 *
 * @author Sebastian Torres Brown.
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/home.jsp"),
    @Result(name = "index", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "banner", value = "/WEB-INF/jsp/banner.jsp"),
    @Result(name = "logout", value = "/WEB-INF/jsp/logout.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class CustomizingAction extends ActionSupport implements SessionAware {

    private static final String LOGOUT = "logout";
    private static final String INDEX = "index";
    private static final String BANNER = "banner";
    private String user;
    private String password;
    private Map session;

    /**
     * redirect to index page
     *
     * @return String with index redirection
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
       return showLogin();
    }

    /**
     * redirects to main page or login page if there is no session associated
     *
     * @return
     */
    public String showLogin() {
        if (session.get("sessionId") != null) {
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }

    /**
     * Authenticates user and redirects to main page
     *
     * @return string with redirection
     * @throws ScopixSecurityException  
     */
    public String login() throws ScopixSecurityException{
        try {
            com.scopix.periscope.securitymanagement.SecurityManager securityManager = SpringSupport
                    .getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class);
            long sessionId = securityManager .login(user, password);       
            if (sessionId > 0) {
                //securityManager.checkSecurity(sessionId, "COMBINED_QUEUE_ADMINISTRATOR");
                session.put("sessionId", sessionId);
                return INDEX;
            } else {
                return LOGIN;
            }
        } catch (ScopixWebServiceException ex) {
            throw new ScopixSecurityException(ex.getMessage());
        } catch (ScopixException ex) {
            throw new ScopixSecurityException(ex.getMessage());
        }
    }

    /**
     * Log out from the application
     *
     * @return string with redirection
     * @throws ScopixException  
     */
    public String logout() throws ScopixException {
        if (session.get("sessionId") != null) {
            SpringSupport.getInstance().findBeanByClassName(
                    com.scopix.periscope.securitymanagement.SecurityManager.class).logout(
                    session.get("sessionId") != null ? (Long) ServletActionContext.getRequest().getSession().getAttribute(
                    "sessionId") : 0L);
            session.remove("sessionId");
            return LOGOUT;
        } else {
            return LOGIN;
        }
    }

    /**
     * shows main page banner
     *
     * @return Banner display redirection
     */
    public String showBanner() {
        return BANNER;
    }

    /**
     * gets the user
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * gets the user password
     *
     * @return string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * returns the session
     *
     * @return
     */
    public Map getSession() {
        return session;
    }

    /**
     * sets the session
     *
     * @param session
     */
    @Override
    public void setSession(Map session) {
        this.session = session;
    }
}
