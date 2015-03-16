/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessrulemanagement.wizard.services.actions;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.businesswarehouse.transfer.Transfer;
import com.scopix.periscope.businesswarehouse.transfer.TransferProofs;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Cesar Abarza
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/businessrules/wizards/transfer/transfer.jsp"),
    @Result(name = "evals", value = "/WEB-INF/jsp/businessrules/wizards/transfer/evaluations.jsp"),
    @Result(name = "fake", value = "/WEB-INF/jsp/businessrules/wizards/transfer/fake.jsp")
})
@Namespace("/wizards")
@ParentPackage(value = "default")
public class TransferWizardAction extends BaseAction implements SessionAware, ServletResponseAware {

    private static Logger log = Logger.getLogger(TransferWizardAction.class);
    private static final String EVALS = "evals";
//    private static final String PROFILE_PROPERTIES_PATH = "/config/profiles/profile.server.properties";
    //CHECKSTYLE:OFF
    private final String FAKE = "fake";
    //CHECKSTYLE:ON
    private Map session;
    private Date date;
    private Date startDate;
    private Date endDate;
    private List<Integer> storeIds;
    private List<Integer> situationTemplateIds;
    private HttpServletResponse response;
    private boolean readyForDownload;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    public String transferProof() {
        if (date == null || date.after(new Date())) {
            this.addActionError(this.getText("error.general.maxDate", new String[]{this.getText("label.date")}));
        }
        if (!this.hasActionErrors()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler sched = sf.getScheduler();
                if (!sched.isStarted()) {
                    sched.start();
                }
                JobDetail job = new JobDetail("TransferProofSimpleJob", "TransferProofGroup", TransferProofs.class);
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("END_DATE", sdf.format(date));
                job.setJobDataMap(jobDataMap);
                SimpleTrigger trigger = new SimpleTrigger("TransferProofSimpleTrigger", "TransferProofGroup");
                sched.scheduleJob(job, trigger);
                this.addActionMessage(this.getText("transfer.proof.ok"));
            } catch (Exception e) {
                log.error(e, e);
                this.addActionError(this.getText("transfer.proof.error"));
            }
        }
        return SUCCESS;
    }

    public String transferData() {
        if (date == null || date.after(new Date())) {
            this.addActionError(this.getText("error.general.maxDate", new String[]{this.getText("label.date")}));
        }
        if (!this.hasActionErrors()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler sched = sf.getScheduler();
                if (!sched.isStarted()) {
                    sched.start();
                }
                JobDetail job = new JobDetail("TransferSimpleJob", "TransferGroup", Transfer.class);
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("END_DATE", sdf.format(date));
                job.setJobDataMap(jobDataMap);
                SimpleTrigger trigger = new SimpleTrigger("TransferSimpleTrigger", "TransferGroup");
                sched.scheduleJob(job, trigger);
                this.addActionMessage(this.getText("transfer.data.ok"));
            } catch (Exception e) {
                log.error(e, e);
                this.addActionError(this.getText("transfer.data.error"));
            }
        }
        return SUCCESS;
    }

    public String showEvaluations() {
        return EVALS;
    }

    private void validateFilters() {
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
                    new String[]{this.getText("label.date")}));
        }
        if (storeIds == null || storeIds.isEmpty()) {
            this.addActionError(this.getText("error.general.requiredField",
                    new String[]{this.getText("label.store")}));
        }
        if (situationTemplateIds == null || situationTemplateIds.isEmpty()) {
            this.addActionError(this.getText("error.general.requiredField",
                    new String[]{this.getText("label.situationTemplate")}));
        }
    }

    public String getOSEData() throws Exception {
        validateFilters();
        if (!this.hasActionErrors()) {
            ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
            byte[] report = manager.generateOSEReport(startDate, endDate, situationTemplateIds, storeIds);
            session.put("REPORT", report);
            setReadyForDownload(true);
        } else {
            setReadyForDownload(false);
        }
        return EVALS;
    }

    public String getIndicatorData() throws Exception {
        validateFilters();
        if (!this.hasActionErrors()) {
            ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
            byte[] report = manager.generateIndicatorReport(startDate, endDate, situationTemplateIds, storeIds);
            session.put("REPORT", report);
            setReadyForDownload(true);
        } else {
            setReadyForDownload(false);
        }

        return EVALS;
    }

    public String fake() {
        setReadyForDownload(false);
        return FAKE;
    }

    public String downloadFile() throws IOException {
        byte[] report = (byte[]) session.get("REPORT");
        if (report != null) {
            getServletResponse().setHeader("Expires", "0");
            getServletResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            getServletResponse().setHeader("Pragma", "public");
            getServletResponse().setContentType("application/vnd.ms-excel");
            getServletResponse().addHeader("Content-Disposition", "attachment;filename=report.xls");
            getServletResponse().setDateHeader("Expires", System.currentTimeMillis() - 30000);
            getServletResponse().setContentLength(report.length);
            getServletResponse().getOutputStream().write(report);
            getServletResponse().getOutputStream().flush();
            getServletResponse().getOutputStream().close();
            session.remove("REPORT");
        }
        return null;
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

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return this.session;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getServletResponse() {
        return response;
    }

    /**
     * @return the startDate
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setDate(Date startDate) {
        this.date = startDate;
    }

    public String getDateValue() {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
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

    public String getStartDateValue() {
        if (startDate == null) {
            startDate = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(startDate);
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

    public String getEndDateValue() {
        if (endDate == null) {
            endDate = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(endDate);
    }

    /**
     * @return the readyForDownload
     */
    public boolean isReadyForDownload() {
        return readyForDownload;
    }

    /**
     * @param readyForDownload the readyForDownload to set
     */
    public void setReadyForDownload(boolean readyForDownload) {
        this.readyForDownload = readyForDownload;
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
}
