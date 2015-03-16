/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

/**
 *
 * @author Gustavo Alvarez
 */
@Results({
@Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/corporateManagement.jsp")
})
@Namespace("/")
@ParentPackage(value = "default")
public class CorporateManagementAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
