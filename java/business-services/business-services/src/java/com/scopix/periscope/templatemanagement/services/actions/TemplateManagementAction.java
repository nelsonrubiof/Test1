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
 * TemplateManagementAction.java
 *
 * Created on 27-03-2008, 01:37:54 PM
 *
 */
package com.scopix.periscope.templatemanagement.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

/**
 *
 * @author C�sar Abarza Suazo
 * @version 1.0.0
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/templatemanager/templateManagement.jsp")
})
@ParentPackage(value = "default")
@Namespace("/")
public class TemplateManagementAction extends BaseAction {

    /**
     *
     * @return String con valor SUCCESS
     */
    @Override
    public String execute() {

        return SUCCESS;
    }
}