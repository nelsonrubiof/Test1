/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.businessrulemanagement.wizard.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

/**
 *
 * @author Gustavo Alvarez
 * @version 2.0.0
 */
@Results({
@Result(name = "success", value = "/WEB-INF/jsp/businessrules/businessRulesManagement.jsp")
})
@Namespace("/")
@ParentPackage(value = "default")
public class BusinessRuleManagementAction extends BaseAction {

    /**
     *
     * @return String generico de Action  
     * @throws Exception Excepcion en caso de Error
     */
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

}