/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  UsersOnQueuesMonitorAction.java
 * 
 *  Created on Aug 14, 2014, 12:44:55 PM
 * 
 */
package com.scopix.periscope.activitylog.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Sebastian
 */
@Results({
    @Result(name = "home", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/activitylog/userslastactivity.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class UsersLastActivityAction extends ActionSupport implements SessionAware {

    private Map session;
    private static final String EDIT = "edit";
    private static final String HOME = "home";

    /**
     * Action class
     *
     * @return jsp or view to display
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        if (!loggedIn()) {
            return HOME;
        }
        return EDIT;
    }

    private boolean loggedIn() {
        if (getSession().get("sessionId") != null && (Long) getSession().get("sessionId") > 0L) {
            return true;
        }
        return false;
    }

    /**
     * returns the session
     *
     * @return session
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