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
 * SituationTemplateAction.java
 *
 * Created on 27-03-2008, 01:37:54 PM
 *
 */
package com.scopix.periscope.templatemanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.evaluationmanagement.EvaluatorsList;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 * 
 * @author C�sar Abarza Suazo
 * @version 1.0.0
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/templatemanager/situationtemplate/situationTemplateManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/templatemanager/situationtemplate/situationTemplateList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/templatemanager/situationtemplate/situationTemplateEdit.jsp"),
    @Result(name = "editEvaluator", value = "/WEB-INF/jsp/templatemanager/situationtemplate/situationTemplateEditEvaluator.jsp"),
    @Result(name = "metrics", value = "/WEB-INF/jsp/templatemanager/situationtemplate/situationTemplateMetricTemplate.jsp")
})
@ParentPackage(value = "default")
@Namespace("/templatemanagement")
public class SituationTemplateAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String EDIT_EVALUATOR = "editEvaluator";
    private static final String METRICS = "metrics";
    private SituationTemplate situationTemplate;
    private List<SituationTemplate> situationTemplates;
    private Map session;
    private Integer[] metricTemplateAssigned;
    private Integer[] metricTemplateNotAssigned;
    private boolean noSave;

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String list() throws ScopixException {
        if (situationTemplate == null || situationTemplate.getId() != null) {
            situationTemplate = (SituationTemplate) getSession().get("stFilter");
        }
        if (situationTemplate != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            situationTemplates = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getSituationTemplateList(
                    situationTemplate, sessionId);
            getSession().put("stFilter", situationTemplate);
        }
        return LIST;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    /**
     *
     * @return
     */
    public String newSituationTemplate() {
        this.setEditable(false);
        situationTemplate = new SituationTemplate();
        session.put("situationTemplate", situationTemplate);
        return EDIT;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String editSituationTemplate() throws ScopixException {
        if (situationTemplate != null && situationTemplate.getId() != null && situationTemplate.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            situationTemplate = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getSituationTemplate(
                    situationTemplate.getId(), sessionId);
            if (!situationTemplate.getSituations().isEmpty()) {
                //this.addActionError(this.getText("error.general.cant.edit",
                //new String[]{this.getText("label.situationTemplate"), this.getText("label.situation")}));
                noSave = true;
            }
            situationTemplate.getMetricTemplate().isEmpty();
            session.put("situationTemplate", situationTemplate);

        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.situationTemplate")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return SUCCESS;
        }
        return EDIT;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String changeEvaluatorName() throws ScopixException {
        if (situationTemplate != null && situationTemplate.getId() != null && situationTemplate.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            situationTemplate = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getSituationTemplate(
                    situationTemplate.getId(), sessionId);
            if (!situationTemplate.getSituations().isEmpty()) {
                noSave = true;
            }
            situationTemplate.getMetricTemplate().isEmpty();
            session.put("situationTemplate", situationTemplate);

        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.situationTemplate")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return SUCCESS;
        }
        return EDIT_EVALUATOR;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String saveSituationTemplateEvaluator() throws ScopixException {
        if (situationTemplate != null && situationTemplate.getId() != null && situationTemplate.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SituationTemplate st = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getSituationTemplate(
                    situationTemplate.getId(), sessionId);
            if (situationTemplate.getEvidenceSpringBeanEvaluatorName().equals("-1")) {
                st.setEvidenceSpringBeanEvaluatorName(null);
            } else {
                st.setEvidenceSpringBeanEvaluatorName(situationTemplate.getEvidenceSpringBeanEvaluatorName());
            }
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).updateSituationTemplate(st, sessionId);
        }
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String deleteSituationTemplate() throws ScopixException {
        if (situationTemplate != null && situationTemplate.getId() != null && situationTemplate.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).
                    removeSituationTemplate(situationTemplate.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.situationTemplate")}));
        }
        this.list();
        return LIST;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String saveSituationTemplate() throws ScopixException {
        if (situationTemplate.getName() == null || situationTemplate.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        situationTemplate.setMetricTemplate(((SituationTemplate) session.get("situationTemplate")).getMetricTemplate());
        if (situationTemplate.getProduct() != null
                && situationTemplate.getProduct().getId() != null
                && situationTemplate.getProduct().getId() == -1) {
            situationTemplate.setProduct(null);
        }
        if (situationTemplate.getAreaType() != null
                && situationTemplate.getAreaType().getId() != null
                && situationTemplate.getAreaType().getId() == -1) {
            situationTemplate.setAreaType(null);
        }
        if (situationTemplate.getActive() == null) {
            situationTemplate.setActive(true);
        }
        if (situationTemplate.getEvidenceSpringBeanEvaluatorName().equals("-1")) {
            situationTemplate.setEvidenceSpringBeanEvaluatorName(null);
        }
        

        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).updateSituationTemplate(situationTemplate,
                    sessionId);
        } else {
            situationTemplate.setActive(true);
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).addSituationTemplate(situationTemplate,
                    sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    /**
     *
     * @return
     */
    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    /**
     *
     * @return
     */
    public String showMetrics() {
        return METRICS;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String activate() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        situationTemplate = (SituationTemplate) session.get("situationTemplate");
        situationTemplate.setActive(true);
        SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).updateActiveState(situationTemplate, sessionId);
        noSave = true;
        return EDIT;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String deactivate() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        situationTemplate = (SituationTemplate) session.get("situationTemplate");
        situationTemplate.setActive(false);
        SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).updateActiveState(situationTemplate, sessionId);
        noSave = true;
        return EDIT;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String activateList() throws ScopixException {
        if (situationTemplate != null && situationTemplate.getId() != null && situationTemplate.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            situationTemplate = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getSituationTemplate(
                    situationTemplate.getId(), sessionId);
            situationTemplate.setActive(true);
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).
                    updateActiveState(situationTemplate, sessionId);
            this.list();
        }
        return LIST;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public String deactivateList() throws ScopixException {
        if (situationTemplate != null && situationTemplate.getId() != null && situationTemplate.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            situationTemplate = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getSituationTemplate(
                    situationTemplate.getId(), sessionId);
            situationTemplate.setActive(false);
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).
                    updateActiveState(situationTemplate, sessionId);
            this.list();
        }
        return LIST;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public List<MetricTemplate> getMetricTemplates() throws ScopixException {
        long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
        List<MetricTemplate> metricTemplates = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).
                getMetricTemplateList(new MetricTemplate(), sessionId);
        if (metricTemplates != null) {
            situationTemplate = (SituationTemplate) session.get("situationTemplate");
            Collections.sort(metricTemplates);
            for (MetricTemplate metricTemplate : situationTemplate.getMetricTemplate()) {
                int index = Collections.binarySearch(metricTemplates, metricTemplate);
                if (index >= 0) {
                    metricTemplates.remove(index);
                }
            }
            Collections.sort(metricTemplates, MetricTemplate.getComparatorByDescription());
            session.put("metricTemplates", metricTemplates);
        }
        return metricTemplates;
    }

    /**
     *
     * @return
     */
    public List<MetricTemplate> getAssignedMetricTemplate() {
        situationTemplate = (SituationTemplate) session.get("situationTemplate");
        List<MetricTemplate> metricTemplates = situationTemplate.getMetricTemplate();
        Collections.sort(metricTemplates, MetricTemplate.getComparatorByDescription());
        return metricTemplates;
    }

    /**
     *
     * @return
     */
    public String addMetricTemplate() {
        List<MetricTemplate> metricTemplates = (List<MetricTemplate>) session.get("metricTemplates");
        situationTemplate = (SituationTemplate) session.get("situationTemplate");
        if (metricTemplateNotAssigned != null && metricTemplateNotAssigned.length > 0 && metricTemplates != null) {
            Collections.sort(metricTemplates);
            for (Integer id : metricTemplateNotAssigned) {
                MetricTemplate metricTemplate = new MetricTemplate();
                metricTemplate.setId(id);
                int index = Collections.binarySearch(metricTemplates, metricTemplate);
                if (index >= 0) {
                    metricTemplate = metricTemplates.remove(index);
                    situationTemplate.getMetricTemplate().add(metricTemplate);
                }
            }
        }
        return METRICS;
    }

    /**
     *
     * @return
     */
    public String removeMetricTemplate() {
        List<MetricTemplate> metricTemplates = (List<MetricTemplate>) session.get("metricTemplates");
        situationTemplate = (SituationTemplate) session.get("situationTemplate");
        if (metricTemplateAssigned != null && metricTemplateAssigned.length > 0
                && situationTemplate.getMetricTemplate() != null) {
            Collections.sort(situationTemplate.getMetricTemplate());
            for (Integer id : metricTemplateAssigned) {
                MetricTemplate metricTemplate = new MetricTemplate();
                metricTemplate.setId(id);
                int index = Collections.binarySearch(situationTemplate.getMetricTemplate(), metricTemplate);
                if (index >= 0) {
                    metricTemplate = situationTemplate.getMetricTemplate().remove(index);
                    if (metricTemplates == null) {
                        metricTemplates = new ArrayList<MetricTemplate>();
                    }
                    metricTemplates.add(metricTemplate);
                }
            }
            session.put("metricTemplates", metricTemplates);
        }
        return METRICS;
    }

    /**
     *
     * @return
     */
    public Map getSession() {
        return session;
    }

    /**
     *
     * @param session
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     *
     * @return
     */
    public SituationTemplate getSituationTemplate() {
        return situationTemplate;
    }

    /**
     *
     * @param situationTemplate
     */
    public void setSituationTemplate(SituationTemplate situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    /**
     *
     * @return
     */
    public List<SituationTemplate> getSituationTemplates() {
        return situationTemplates;
    }

    /**
     *
     * @param situationTemplates
     */
    public void setSituationTemplates(List<SituationTemplate> situationTemplates) {
        this.situationTemplates = situationTemplates;
    }

    /**
     *
     * @return
     */
    public Integer[] getMetricTemplateAssigned() {
        return metricTemplateAssigned;
    }

    /**
     *
     * @param metricTemplateAssigned
     */
    public void setMetricTemplateAssigned(Integer[] metricTemplateAssigned) {
        this.metricTemplateAssigned = metricTemplateAssigned;
    }

    /**
     *
     * @return
     */
    public Integer[] getMetricTemplateNotAssigned() {
        return metricTemplateNotAssigned;
    }

    /**
     *
     * @param metricTemplateNotAssigned
     */
    public void setMetricTemplateNotAssigned(Integer[] metricTemplateNotAssigned) {
        this.metricTemplateNotAssigned = metricTemplateNotAssigned;
    }

    /**
     *
     * @return
     */
    public List<String> getSituationEvaluators() {
        EvaluatorsList evaluatorsList = SpringSupport.getInstance().findBeanByClassName(EvaluatorsList.class);
        List<String> list = evaluatorsList.getRuleEvaluators();
        return list;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public List<Product> getProducts() throws ScopixException {
        List products = null;
        long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
        TemplatesManager templatesManager = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class);
        products = templatesManager.getProductList(null, sessionId);
        return products;
    }

    /**
     *
     * @return
     * @throws PeriscopeException
     * @throws PeriscopeSecurityException
     */
    public List<AreaType> getAreaTypes() throws ScopixException {
        List areaTypes = null;
        long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
        CorporateStructureManager corporateStructureManager = SpringSupport.getInstance().findBeanByClassName(
                CorporateStructureManager.class);
        areaTypes = corporateStructureManager.getAreaTypeList(null, sessionId);
        return areaTypes;
    }

    /**
     *
     * @return
     */
    public Map<String, String> getEvidenceEvaluators() {
        EvaluatorsList evaluatorsList = SpringSupport.getInstance().findBeanByClassName(EvaluatorsList.class);
        Map<String, String> list = evaluatorsList.getSpringBeanEvaluatorsForEvidenceST();
        return list;
    }

    private void loadFilters() {
        situationTemplate = (SituationTemplate) getSession().get("stFilter");
    }

    /**
     *
     * @return
     */
    public boolean isNoSave() {
        return noSave;
    }

    /**
     *
     * @param noSave
     */
    public void setNoSave(boolean noSave) {
        this.noSave = noSave;
    }
}
