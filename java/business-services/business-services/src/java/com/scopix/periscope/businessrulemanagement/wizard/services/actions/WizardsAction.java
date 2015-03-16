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
 * @author Cesar Abarza
 */
@Results({
@Result(name = "success", value = "/WEB-INF/jsp/businessrules/wizards/wizards.jsp")
})
@Namespace("/")
@ParentPackage(value = "default")
public class WizardsAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

}