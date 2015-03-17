/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SecurityManagementAction.java
 *
 * Created on 16-06-2008, 11:28:56 AM
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.securitymanagement.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@Results({
    @Result(name="success", value="/WEB-INF/jsp/securitymanagement/securityManagement.jsp")
})
@Namespace("/")
@ParentPackage("default")
public class SecurityManagementAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }    
}