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
 * MetricTemplateAction.java
 *
 * Created on 24-06-2008, 02:59:48 PM
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.templatemanagement.services.actions;

import com.scopix.periscope.evaluationmanagement.EvaluatorsList;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.EvidenceType;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.MetricType;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import com.scopix.periscope.templatemanagement.YesNoType;
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
 * @author C�sar Abarza Suazo.
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/templatemanager/metrictemplate/metricTemplateManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/templatemanager/metrictemplate/metricTemplateList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/templatemanager/metrictemplate/metricTemplateEdit.jsp")
})
@ParentPackage(value = "default")
@Namespace("/templatemanagement")
public class MetricTemplateAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private List<MetricTemplate> metricTemplates;
    private MetricTemplate metricTemplate;
    private String evidenceTypeElement;
    private String metricTypeElement;
    private String yesNoType;
    private boolean disabled;
    private Map session;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (metricTemplate == null || metricTemplate.getId() != null) {
            metricTemplate = (MetricTemplate) session.get("mtFilter");
        }
        if (metricTypeElement != null && !metricTypeElement.equals("-1")) {
            if (metricTemplate == null) {
                metricTemplate = new MetricTemplate();
            }
            metricTemplate.setMetricTypeElement(MetricType.valueOf(metricTypeElement));
        }
        if (evidenceTypeElement != null && !evidenceTypeElement.equals("-1")) {
            if (metricTemplate == null) {
                metricTemplate = new MetricTemplate();
            }
            metricTemplate.setEvidenceTypeElement(EvidenceType.valueOf(evidenceTypeElement));
        }
        if (metricTemplate != null) {
            metricTemplates = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getMetricTemplateList(
                    metricTemplate, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
            session.put("mtFilter", metricTemplate);
            session.put("mtFilterMType", metricTypeElement);
            session.put("mtFilterEType", evidenceTypeElement);
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newMetricTemplate() {
        this.setEditable(false);
        disabled = true;
        metricTemplate = new MetricTemplate();
        return EDIT;
    }

    public String editMetricTemplate() throws ScopixException {
        if (metricTemplate != null && metricTemplate.getId() != null && metricTemplate.getId() > 0) {
            this.setEditable(true);
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            metricTemplate = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getMetricTemplate(
                    metricTemplate.getId(), sessionId);
            metricTypeElement = metricTemplate.getMetricTypeElement().name();
            evidenceTypeElement = metricTemplate.getEvidenceTypeElement().name();
            if (metricTemplate.getMetricTypeElement().equals(MetricType.YES_NO)) {
                yesNoType = metricTemplate.getYesNoType().name();
                disabled = false;
            } else {
                disabled = true;
            }
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.metricTemplate")}));
        }
        return EDIT;
    }

    public String deleteMetricTemplate() throws ScopixException {
        if (metricTemplate != null && metricTemplate.getId() != null && metricTemplate.getId() > 0) {
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).removeMetricTemplate(metricTemplate.getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.metricTemplate")}));
        }
        this.list();
        return LIST;
    }

    public String saveMetricTemplate() throws ScopixException {
        if (metricTemplate.getName() == null || metricTemplate.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (metricTemplate.getDescription() == null || metricTemplate.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (metricTypeElement == null || metricTypeElement.equals("-1")) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.metricType")}));
        }
        if (metricTypeElement != null && metricTypeElement.equals(MetricType.YES_NO.getName()) && (yesNoType == null || yesNoType.
                equals("-1"))) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.yesNoType")}));
        }
        if (evidenceTypeElement == null || evidenceTypeElement.equals("-1")) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.evidenceType")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }
        metricTemplate.setMetricTypeElement(MetricType.valueOf(metricTypeElement));
        metricTemplate.setEvidenceTypeElement(EvidenceType.valueOf(evidenceTypeElement));
        if (metricTemplate.getMetricTypeElement().equals(MetricType.YES_NO)) {
            metricTemplate.setYesNoType(YesNoType.valueOf(yesNoType));
        }
        if (metricTemplate.getEvidenceSpringBeanEvaluatorName() != null && metricTemplate.getEvidenceSpringBeanEvaluatorName().
                equals("-1")) {
            metricTemplate.setEvidenceSpringBeanEvaluatorName(null);
        }
        if (metricTemplate.getMetricSpringBeanEvaluatorName() != null && metricTemplate.getMetricSpringBeanEvaluatorName().equals(
                "-1")) {
            metricTemplate.setMetricSpringBeanEvaluatorName(null);
        }
        long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).
                    updateMetricTemplate(metricTemplate, sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).addMetricTemplate(metricTemplate, sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public MetricType[] getMetricTypes() {
        return MetricType.values();
    }

    public EvidenceType[] getEvidenceTypes() {
        return EvidenceType.values();
    }

    public YesNoType[] getYesNoTypes() {
        return YesNoType.values();
    }

    public MetricTemplate getMetricTemplate() {
        return metricTemplate;
    }

    public void setMetricTemplate(MetricTemplate metricTemplate) {
        this.metricTemplate = metricTemplate;
    }

    public String getEvidenceTypeElement() {
        return evidenceTypeElement;
    }

    public void setEvidenceTypeElement(String evidenceTypeElement) {
        this.evidenceTypeElement = evidenceTypeElement;
    }

    public String getMetricTypeElement() {
        return metricTypeElement;
    }

    public void setMetricTypeElement(String metricTypeElement) {
        this.metricTypeElement = metricTypeElement;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public List<MetricTemplate> getMetricTemplates() {
        if (metricTemplates == null) {
            metricTemplates = new ArrayList<MetricTemplate>();
        }
        return metricTemplates;
    }

    public void setMetricTemplates(List<MetricTemplate> metricTemplates) {
        this.metricTemplates = metricTemplates;
    }

    public String getYesNoType() {
        return yesNoType;
    }

    public void setYesNoType(String yesNoType) {
        this.yesNoType = yesNoType;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Map<String, String> getMetricEvaluators() {
        EvaluatorsList evaluatorsList = SpringSupport.getInstance().findBeanByClassName(EvaluatorsList.class);
        Map<String, String> list = evaluatorsList.getSpringBeanEvaluatorsForMetric();
        return list;
    }

    public Map<String, String> getEvidenceEvaluators() {
        EvaluatorsList evaluatorsList = SpringSupport.getInstance().findBeanByClassName(EvaluatorsList.class);
        Map<String, String> list = evaluatorsList.getSpringBeanEvaluatorsForEvidenceMT();
        return list;
    }

    private void loadFilters() {
        metricTemplate = (MetricTemplate) getSession().get("mtFilter");
        metricTypeElement = (String) session.get("mtFilterMType");
        evidenceTypeElement = (String) session.get("mtFilterEType");
    }
}
