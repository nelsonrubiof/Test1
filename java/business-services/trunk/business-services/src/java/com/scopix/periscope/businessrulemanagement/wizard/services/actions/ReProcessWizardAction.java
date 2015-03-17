/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessrulemanagement.wizard.services.actions;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Cesar Abarza
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/businessrules/wizards/reprocess/reprocess.jsp")
})
@Namespace("/wizards")
@ParentPackage(value = "default")
public class ReProcessWizardAction extends BaseAction implements SessionAware {

    private Map session;
    private List<Integer> storeIds;
    private List<Integer> situationTemplateIds;
    private Date startDate;
    private Date endDate;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String reprocess() throws ScopixException {
        if (startDate == null || startDate.after(new Date())) {
            this.addActionError(this.getText("error.general.maxDate",
                    new String[]{this.getText("label.startDate")}));
        }
        if (endDate == null || endDate.after(new Date())) {
            this.addActionError(this.getText("error.general.maxDate",
                    new String[]{this.getText("label.endDate")}));
        }
        if (startDate.after(endDate)) {
            this.addActionError(this.getText("error.general.invalidRange",
                    new String[]{this.getText("label.indicatorName")}));
        }
        if (storeIds == null || storeIds.isEmpty()) {
            this.addActionError(this.getText("error.general.requiredField",
                    new String[]{this.getText("label.store")}));
        }
        if (situationTemplateIds == null || situationTemplateIds.isEmpty()) {
            this.addActionError(this.getText("error.general.requiredField",
                    new String[]{this.getText("label.situationTemplate")}));
        }
        if (!this.hasActionErrors() && !this.hasActionMessages()) {
            try {
                long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
                manager.reprocess(startDate, endDate, situationTemplateIds, storeIds, sessionId);
                this.addActionMessage(this.getText("reprocess.ok"));
            } catch (ScopixException e) {
                this.addActionError(this.getText("reprocess.nook"));
            }
        }
        return SUCCESS;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return this.session;
    }

    public List<SituationTemplate> getSituationTemplates() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        TemplatesManager manager = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class);
        SituationTemplate situationTemplate = new SituationTemplate();
        situationTemplate.setActive(true);
        List<SituationTemplate> situationTemplates = manager.getSituationTemplateList(situationTemplate, sessionId);
        return situationTemplates;
    }

    /**
     * @return the stores
     */
    public List<Store> getStores() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        List<Store> stores = manager.getStoreList(null, sessionId);
        return stores;
    }

    /**
     * @return the storeIds
     */
    public List<Integer> getStoreIds() {
        if (storeIds == null) {
            storeIds = new ArrayList<Integer>();
        }

        return storeIds;
    }

    /**
     * @param storeIds the storeIds to set
     */
    public void setStoreIds(List<Integer> storeIds) {
        this.storeIds = storeIds;
    }

    /**
     * @return the situationTemplateIds
     */
    public List<Integer> getSituationTemplateIds() {
        if (situationTemplateIds == null) {
            situationTemplateIds = new ArrayList<Integer>();
        }
        return situationTemplateIds;
    }

    /**
     * @param situationTemplateIds the situationTemplateIds to set
     */
    public void setSituationTemplateIds(List<Integer> situationTemplateIds) {
        this.situationTemplateIds = situationTemplateIds;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateValue() {
        if (startDate == null) {
            startDate = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(startDate);
    }

    public String getEndDateValue() {
        if (endDate == null) {
            endDate = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(endDate);
    }
}
