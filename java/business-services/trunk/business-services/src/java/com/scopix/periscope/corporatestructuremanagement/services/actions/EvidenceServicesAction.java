/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.List;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/servers/evidence/essManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/servers/evidence/essList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/servers/evidence/essEdit.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class EvidenceServicesAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private EvidenceServicesServer evidenceServicesServer;
    private Map session;
    private List<EvidenceServicesServer> evidenceServicesServers;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (evidenceServicesServer == null || evidenceServicesServer.getId() != null) {
            evidenceServicesServer = (EvidenceServicesServer) getSession().get("essFilter");
        }
        if (evidenceServicesServer != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceServicesServers = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getEvidenceServicesServers(evidenceServicesServer, sessionId);
            session.put("essFilter", evidenceServicesServer);
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newESS() {
        this.setEditable(false);
        evidenceServicesServer = new EvidenceServicesServer();
        return EDIT;
    }

    public String editESS() throws ScopixException {
        if (evidenceServicesServer != null && evidenceServicesServer.getId() != null && evidenceServicesServer.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceServicesServer = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getEvidenceServicesServer(evidenceServicesServer.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.evidenceServicesServer")}));
        }
        return EDIT;
    }

    public String deleteESS() throws ScopixException {
        if (evidenceServicesServer != null && evidenceServicesServer.getId() != null && evidenceServicesServer.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    removeEvidenceServicesServer(evidenceServicesServer.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.evidenceServicesServer")}));
        }
        this.list();
        return LIST;
    }

    public String saveESS() throws ScopixException {
        if (evidenceServicesServer.getUrl() == null || evidenceServicesServer.getUrl().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.url")}));
        }
        if (evidenceServicesServer.getEvidencePath() == null || evidenceServicesServer.getEvidencePath().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.evidencePath")}));
        }
        if (evidenceServicesServer.getProofPath() == null || evidenceServicesServer.getProofPath().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.proofPath")}));
        }
        if (evidenceServicesServer.getAlternativeMode()) {
            if (evidenceServicesServer.getAlternativeEvidencePath() == null
                    || evidenceServicesServer.getAlternativeEvidencePath().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.alternativeEvidencePath")}));
            }
            if (evidenceServicesServer.getAlternativeSFTPip() == null
                    || evidenceServicesServer.getAlternativeSFTPip().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.alternativeSFTPip")}));
            }
            if (evidenceServicesServer.getAlternativeSFTPuser() == null
                    || evidenceServicesServer.getAlternativeSFTPuser().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.alternativeSFTPuser")}));
            }
            if (evidenceServicesServer.getAlternativeSFTPpassword() == null
                    || evidenceServicesServer.getAlternativeSFTPpassword().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.alternativeSFTPpassword")}));
            }
            if (evidenceServicesServer.getAlternativeRemoteSFTPPath() == null
                    || evidenceServicesServer.getAlternativeRemoteSFTPPath().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.alternativeRemoteSFTPPath")}));
            }
            if (evidenceServicesServer.getLocalFilePath() == null
                    || evidenceServicesServer.getLocalFilePath().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.localFilePath")}));
            }
        }
        
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).updateEvidenceServicesServer(
                    evidenceServicesServer, sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).addEvidenceServicesServer(
                    evidenceServicesServer, sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public EvidenceServicesServer getEvidenceServicesServer() {
        return evidenceServicesServer;
    }

    public void setEvidenceServicesServer(EvidenceServicesServer evidenceServicesServer) {
        this.evidenceServicesServer = evidenceServicesServer;
    }

    public List<EvidenceServicesServer> getEvidenceServicesServers() {
        return evidenceServicesServers;
    }

    public void setEvidenceServicesServers(List<EvidenceServicesServer> evidenceServicesServers) {
        this.evidenceServicesServers = evidenceServicesServers;
    }

    private void loadFilters() {
        evidenceServicesServer = (EvidenceServicesServer) getSession().get("essFilter");
    }

    public List<String> getStateOptions() {
        List<String> opciones = new ArrayList<String>();
        opciones.add(this.getText("label.alternativeModeOn"));
        opciones.add(this.getText("label.alternativeModeOff"));

        return opciones;
    }
}
