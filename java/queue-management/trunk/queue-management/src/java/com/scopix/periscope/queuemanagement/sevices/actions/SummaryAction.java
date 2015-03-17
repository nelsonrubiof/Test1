/*
 * 
 * Copyright © 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QueueManagementIndexAction.java
 *
 * Created on 20-05-2008, 11:16:03 AM
 *
 */
package com.scopix.periscope.queuemanagement.sevices.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dto.SummaryDTO;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;
import com.scopix.periscope.queuemanagement.services.webservices.client.QueueManagementWebServiceClient;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author César Abarza Suazo.
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/queuemanagement/summary/summary.jsp")
})
@Namespace("/queuemanagement")
@ParentPackage(value = "default")
public class SummaryAction extends BaseAction implements SessionAware {

    private List<SummaryDTO> summary;
    private Map session;

    @Override
    public String execute() throws Exception {
        long sessionId = getSession().get("sessionId") != null ? (Long) getSession().get("sessionId") : 0L;
        QueueManagementWebServices webService = SpringSupport.getInstance().findBeanByClassName(
                QueueManagementWebServiceClient.class).getWebService();
        summary = webService.getSummaryList(sessionId);
        getSession().put("summary", summary);
        //summary.get(0).getStore();
        return SUCCESS;
    }

    public String readyList() {
        summary = (List<SummaryDTO>) getSession().get("summary");
        return SUCCESS;
    }

    public List<SummaryDTO> getSummary() {
        return summary;
    }

    public void setSummary(List<SummaryDTO> summary) {
        this.summary = summary;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }
}
