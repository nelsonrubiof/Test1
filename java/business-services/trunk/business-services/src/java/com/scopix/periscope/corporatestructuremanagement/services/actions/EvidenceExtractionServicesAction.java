/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/servers/evidenceextraction/eessManagement.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/servers/evidenceextraction/eessEdit.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/servers/evidenceextraction/eessList.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class EvidenceExtractionServicesAction extends BaseAction implements SessionAware {

    private static Logger log = Logger.getLogger(EvidenceExtractionServicesAction.class);

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private EvidenceExtractionServicesServer evidenceExtractionServicesServer;
    private Map session;
    private List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (evidenceExtractionServicesServer == null || evidenceExtractionServicesServer.getId() != null) {
            evidenceExtractionServicesServer = (EvidenceExtractionServicesServer) getSession().get("eessFilter");
        }
        if (evidenceExtractionServicesServer != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceExtractionServicesServers = (List<EvidenceExtractionServicesServer>) SpringSupport.getInstance().
                    findBeanByClassName(CorporateStructureManager.class).getEvidenceExtractionServicesServers(
                    evidenceExtractionServicesServer, sessionId);
            session.put("eessFilter", evidenceExtractionServicesServer);
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newEESS() {
        this.setEditable(false);
        evidenceExtractionServicesServer = new EvidenceExtractionServicesServer();
        return EDIT;
    }

    public String editEESS() throws ScopixException {
        if (evidenceExtractionServicesServer != null && evidenceExtractionServicesServer.getId() != null &&
                evidenceExtractionServicesServer.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceExtractionServicesServer = (EvidenceExtractionServicesServer) SpringSupport.getInstance().findBeanByClassName(
                    CorporateStructureManager.class).getEvidenceExtractionServicesServer(evidenceExtractionServicesServer.getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.evidenceServicesServer")}));
        }
        return EDIT;
    }

    public String deleteEESS() throws ScopixException {
        try {
            if (evidenceExtractionServicesServer != null && evidenceExtractionServicesServer.getId() != null &&
                    evidenceExtractionServicesServer.getId() > 0) {
                long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                        removeEvidenceExtractionServicesServer(evidenceExtractionServicesServer.getId(), sessionId);
            } else {
                this.addActionError(this.getText("error.general.delete",
                        new String[]{this.getText("label.evidenceServicesServer")}));
            }
            this.list();
        } catch (Exception e) {
            log.debug("error: " + e.getMessage());
        }
        return LIST;
    }

    public String saveEESS() throws ScopixException {
        if(evidenceExtractionServicesServer.getName() == null || evidenceExtractionServicesServer.getName().length()==0){
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (evidenceExtractionServicesServer.getUrl() == null || evidenceExtractionServicesServer.getUrl().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.url")}));
        }
        if (evidenceExtractionServicesServer.getEvidenceServicesServer() != null &&
                evidenceExtractionServicesServer.getEvidenceServicesServer().getId() != null &&
                evidenceExtractionServicesServer.getEvidenceServicesServer().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                        "label.evidenceServicesServer")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    updateEvidenceExtractionServicesServer(evidenceExtractionServicesServer, sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).addEvidenceExtractionServicesServer(
                    evidenceExtractionServicesServer, sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public List<EvidenceServicesServer> getEvidenceExtractionServicesList() throws ScopixException {
        List<EvidenceServicesServer> evidenceExtractionServices = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        evidenceExtractionServices = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                getEvidenceServicesServers(null, sessionId);
        return evidenceExtractionServices;
    }

    public EvidenceExtractionServicesServer getEvidenceExtractionServicesServer() {
        return evidenceExtractionServicesServer;
    }

    public void setEvidenceExtractionServicesServer(EvidenceExtractionServicesServer evidenceExtractionServicesServer) {
        this.evidenceExtractionServicesServer = evidenceExtractionServicesServer;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServers() {
        return evidenceExtractionServicesServers;
    }

    public void setEvidenceExtractionServicesServers(List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers) {
        this.evidenceExtractionServicesServers = evidenceExtractionServicesServers;
    }

    private void loadFilters() {
        evidenceExtractionServicesServer = (EvidenceExtractionServicesServer) getSession().get("eessFilter");
    }
}
