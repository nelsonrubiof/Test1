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
 *  evidenceRegionTransferMonitorAction.java
 * 
 *  Created on Jul 28, 2014, 3:48:56 PM
 * 
 */

package com.scopix.periscope.corporatestructuremanagement.services.actions;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

/**
 *
 * @author Sebastian
 */
@Results({
@Result(name = "success", value = "/WEB-INF/jsp/evidenceregiontransfer/evidenceregiontransfer.jsp")
})
@Namespace("/")
public class RegionTransferMonitorAction  extends BaseAction {

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
