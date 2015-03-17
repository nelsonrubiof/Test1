/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
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
 * Created on 24-06-2008, 10:13:55 AM
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.customizing;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/home.jsp"),
    @Result(name = "index", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "banner", value = "/WEB-INF/jsp/banner.jsp"),
    @Result(name = "logout", value = "/WEB-INF/jsp/logout.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class CustomizingAction extends com.scopix.periscope.frameworksfoundation.struts2.BaseAction implements SessionAware {

    private static final String LOGOUT = "logout";
    private static final String INDEX = "index";
    private static final String BANNER = "banner";
    private String user;
    private String password;
    private Map session;

    @Override
    public String execute() throws Exception {
        return INDEX;
    }

    public String showLogin() {
        if (session.get("sessionId") != null) {
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }

    public String login() throws ScopixException {
        long sessionId = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class).login(user, password);
        if (sessionId > 0) {
            session.put("sessionId", sessionId);
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }

    public String logout() throws ScopixException {
        if (session.get("sessionId") != null) {
            SpringSupport.getInstance().findBeanByClassName(
                    com.scopix.periscope.securitymanagement.SecurityManager.class).logout(
                    session.get("sessionId") != null ? (Long) ServletActionContext.getRequest().getSession().getAttribute(
                    "sessionId") : 0L);
            session.remove("sessionId");
            return LOGOUT;
        } else {
            return INDEX;
        }
    }

    public String showBanner() {
        return BANNER;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }
}
