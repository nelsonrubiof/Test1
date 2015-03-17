/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * UploadEvidenceToExternalDeviceAction.java
 *
 * Created on 29-12-2009, 02:40:13 PM
 *
 */
package com.scopix.periscope.extractionmanagement.services.actions;

import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Gustavo Alvarez
 */
@Results({
    //@Result(name = "success", value = "/WEB-INF/jsp/home.jsp"),
    @Result(name = "index", value = "/WEB-INF/jsp/index.jsp"),
    @Result(name = "menu_principal", value = "/WEB-INF/jsp/menuPrincipal.jsp")
//@Result(name = "logout", value = "/WEB-INF/jsp/logout.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class UploadEvidenceToExternalDeviceAction extends com.scopix.periscope.frameworksfoundation.struts2.BaseAction implements
        SessionAware {

    private static final String INDEX = "index";
    private static final String MENU_PRINCIPAL = "menu_principal";
    private Map session;

    @Override
    public String execute() throws Exception {
        return INDEX;
    }

    public String showMenuPrincipal() throws Exception {
        return MENU_PRINCIPAL;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }
}
