/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Country;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.corporatestructuremanagement.PeriodInterval;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.StoreVO;
import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.StringUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/store/storeManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/store/storeList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/store/storeEdit.jsp"),
    @Result(name = "periodInterval", value = "/WEB-INF/jsp/corporatemanagement/store/periodInterval.jsp"),
    @Result(name = "extractionPlanToPast", value = "/WEB-INF/jsp/corporatemanagement/store/extractionPlanToPast.jsp"),
    @Result(name = "extractionPlanToPastResult", value = "/WEB-INF/jsp/corporatemanagement/store/extractionPlanToPastResult.jsp"),
    @Result(name = "closeSituation", value = "/WEB-INF/jsp/corporatemanagement/store/closeSituation.jsp"),
    @Result(name = "closeSituationResult", value = "/WEB-INF/jsp/corporatemanagement/store/closeSituationResult.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class StoresAction extends BaseAction implements SessionAware {

    private Logger log = Logger.getLogger(StoresAction.class);
    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String PERIOD_INTERVAL = "periodInterval";
    private static final String EXTRACTION_PLAN_TO_PAST = "extractionPlanToPast";
    private static final String EXTRACTION_PLAN_TO_PAST_RESULT = "extractionPlanToPastResult";
    private static final String CLOSE_SITUATION = "closeSituation";
    private static final String CLOSE_SITUATION_RESULT = "closeSituationResult";
    private Store store;
    private Map<String, Object> session;
    private List<Store> stores;
    private String latitudeCoordenate;
    private String longitudeCoordenate;
    private List<Integer> days;
    private List<PeriodInterval> periodIntervals;
    private PeriodInterval periodInterval;
    private Date extractionPlanToPastDate;
    private List<EvidenceRequest> evidenceRequests;
    private Integer[] storesAssignedIds;
    private Integer[] storesNotAssignedIds;
    private Date closeSituationDate;
    private String pagePendingsCloseSituation;
    private String pageResumenCloseSituation;
    private Integer[] sitautionsAssignedIds;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (getStore() == null || getStore().getId() != null) {
            setStore((Store) getSession().get("sFilter"));
        }
        if (getStore() != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setStores(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(getStore(),
                    sessionId));
            getSession().put("sFilter", getStore());
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newStore() {
        this.setEditable(false);
        setStore(new Store());
        session.remove("eessId");
        session.remove("storeId_eess");
        session.remove("countryId_region");
        session.remove("periodIntervals");
        return EDIT;
    }

    public String editStore() throws ScopixException {
        if (getStore() != null && getStore().getId() != null && getStore().getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setStore(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStore(getStore().getId(),
                    sessionId));
            if (store.getLatitudeCoordenate() != null) {
                setLatitudeCoordenate(store.getLatitudeCoordenate().toString());
            }
            if (store.getLongitudeCoordenate() != null) {
                setLongitudeCoordenate(store.getLongitudeCoordenate().toString());
            }
            if (store.getEvidenceServicesServer() != null && store.getEvidenceServicesServer().getId() != null) {
                session.put("eessId", store.getEvidenceServicesServer().getId());
            }
            if (store.getCountry() != null && store.getCountry().getId() != null) {
                session.put("countryId_region", store.getCountry().getId());
            }
            store.getPeriodIntervals().isEmpty();
            session.put("periodIntervals", store.getPeriodIntervals());
            session.put("storeId_eess", store.getId());
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.store")}));
        }
        return EDIT;
    }

    public String deleteStore() throws ScopixException {
        if (getStore() != null && getStore().getId() != null && getStore().getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).removeStore(getStore().getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.store")}));
        }
        this.list();
        return LIST;
    }

    public String saveStore() throws ScopixException {
        if (store.getName() == null || store.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (store.getDescription() == null || store.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (store.getAddress() == null || store.getAddress().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.address")}));
        }
        if (store.getCorporate() == null || store.getCorporate().getId() == null) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.corporate")}));
        }
        if (store.getEvidenceServicesServer() == null
                || store.getEvidenceServicesServer().getId() == null
                || store.getEvidenceServicesServer().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                "label.evidenceServicesServer")}));
        }
        if (store.getEvidenceExtractionServicesServer() == null || store.getEvidenceExtractionServicesServer().getId() == null
                || store.getEvidenceExtractionServicesServer().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                "label.evidenceExtractionServicesServer")}));
        }
        if (store.getCountry() == null || store.getCountry().getId() == null || store.getCountry().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.country")}));
        }
        if (store.getRegion() == null || store.getRegion().getId() == null || store.getRegion().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.region")}));
        }
        if (store.getTimeZoneId() == null || store.getTimeZoneId().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.timezone")}));
        }

        if (latitudeCoordenate != null && latitudeCoordenate.trim().length() > 0) {
            if (StringUtil.validateString(latitudeCoordenate, StringUtil.FLOAT_NUMBER)) {
                store.setLatitudeCoordenate(new Double(latitudeCoordenate));
            } else {
                this.addActionError(this.getText("error.general.invalidField", new String[]{this.getText("label.latitude")}));
            }
        }
        if (longitudeCoordenate != null && longitudeCoordenate.trim().length() > 0) {
            if (StringUtil.validateString(longitudeCoordenate, StringUtil.FLOAT_NUMBER)) {
                store.setLongitudeCoordenate(new Double(longitudeCoordenate));
            } else {
                this.addActionError(this.getText("error.general.invalidField", new String[]{this.getText("label.longitude")}));
            }
        }
        if (session.get("periodIntervals") == null || ((List) session.get("periodIntervals")).isEmpty()) {
            this.addActionError(this.getText("error.periodInterval.required"));
        } else {
            store.setPeriodIntervals((List<PeriodInterval>) getSession().get("periodIntervals"));
            session.remove("periodIntervals");
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        if (this.isEditable()) {
            manager.updateStore(getStore(), sessionId);
        } else {
            manager.addStore(getStore(), sessionId);
        }
        //Para marcar los epc y obligar a su regeneración
        if (session.get("changePeriodIntervals") != null) {
            session.remove("changePeriodIntervals");
            this.addActionMessage(this.getText("message.rememberSendExtractionPlan"));
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String showPeriodIntervals() {

        return PERIOD_INTERVAL;
    }

    public String addPeriodInterval() {
        if (periodInterval == null) {
            this.addActionError(this.getText("error.general.requiredField"));
        }
        if (days == null || days.isEmpty()) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.days")}));
        }
        //Validar que el formato de los valores esten ok
        if (!this.isAValidTime(periodInterval.getInitTime())) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.initTime")}));
        }
        if (!this.isAValidTime(periodInterval.getEndTime())) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.endTime")}));
        }
        //Validar que initTime sea menor que endTime
        if (!this.isAValidRange(periodInterval.getInitTime(), periodInterval.getEndTime())) {
            this.addActionError(this.getText("period.interval.invalid"));
        }
        //Validar que para el dia seleccionado no exite otro periodInterval
        if (!this.isValidSelectedDays()) {
            this.addActionError(this.getText("error.days.invalidSelection"));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return PERIOD_INTERVAL;
        }
        for (Integer day : days) {
            //CHECKSTYLE:OFF
            switch (day) {
                case 1:
                    periodInterval.setSunday(true);
                    break;
                case 2:
                    periodInterval.setMonday(true);
                    break;
                case 3:
                    periodInterval.setTuesday(true);
                    break;
                case 4:
                    periodInterval.setWednesday(true);
                    break;
                case 5:
                    periodInterval.setThursday(true);
                    break;
                case 6:
                    periodInterval.setFriday(true);
                    break;
                case 7:
                    periodInterval.setSaturday(true);
                    break;
            }
            //CHECKSTYLE:ON
        }
        periodIntervals = this.getPeriodIntervals();
        if (periodIntervals.size() > 0) {
            int id = periodIntervals.get(periodIntervals.size() - 1).getId() + 1;
            periodInterval.setId(id);
        } else {
            periodInterval.setId(1);
        }
        this.getPeriodIntervals().add(periodInterval);

        session.put("periodIntervals", periodIntervals);
        session.put("changePeriodIntervals", "TRUE");
        periodInterval = null;


        return PERIOD_INTERVAL;
    }

    public String deletePeriodInterval() {
        if (periodInterval != null && periodInterval.getId() != null && periodInterval.getId() > 0) {
            periodIntervals = this.getPeriodIntervals();
            Collections.sort(periodIntervals);
            int index = Collections.binarySearch(periodIntervals, periodInterval);
            if (index >= 0) {
                periodIntervals.remove(index);
                session.put("periodIntervals", periodIntervals);
                session.put("changePeriodIntervals", "TRUE");
            }
        }
        return PERIOD_INTERVAL;
    }

    public LinkedHashMap<Integer, String> getDaysList() {
        LinkedHashMap<Integer, String> daysList = new LinkedHashMap<Integer, String>();
        daysList.put(2, this.getText("day.monday"));
        daysList.put(3, this.getText("day.tuesday"));
        daysList.put(4, this.getText("day.wednesday"));
        daysList.put(5, this.getText("day.thursday"));
        daysList.put(6, this.getText("day.friday"));
        daysList.put(7, this.getText("day.saturday"));
        daysList.put(1, this.getText("day.sunday"));
        return daysList;
    }

    public String readyListPeriodInterval() {
        return PERIOD_INTERVAL;
    }

    public Corporate getCorporate() throws ScopixException {
        Corporate corporate = (Corporate) session.get("corporate");
        if (corporate == null) {
            corporate = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getCorporate(session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
            session.put("corporate", corporate);
        }
        return corporate;
    }

    public List<EvidenceServicesServer> getEvidenceServicesServers() throws ScopixException {
        List<EvidenceServicesServer> list = null;
        list = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getEvidenceServicesServers(null,
                session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        return list;
    }

    public List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServers() throws ScopixException {
        Integer id = (Integer) session.get("eessId");
        List<EvidenceExtractionServicesServer> list = new ArrayList<EvidenceExtractionServicesServer>();
        if (id != null && id > 0) {
            EvidenceExtractionServicesServer eess = new EvidenceExtractionServicesServer();
            eess.setEvidenceServicesServer(new EvidenceServicesServer());
            eess.getEvidenceServicesServer().setId(id);
//            eess.setStore(new Store());
//            eess.getStore().setId((Integer) session.get("storeId_eess"));
            list = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getFreeEvidenceExtractionServicesServers(eess, session.containsKey("sessionId") ? (Long) session.get(
                    "sessionId") : 0);
            if (list == null) {
                list = new ArrayList<EvidenceExtractionServicesServer>();
            }
        } else {
            list = new ArrayList<EvidenceExtractionServicesServer>();
        }
        return list;
    }

    public String refreshEESS() {
        session.put("eessId", store.getEvidenceServicesServer().getId());
        session.put("storeId_eess", store.getId());
        return EDIT;
    }

    public List<Country> getCountries() throws ScopixException {
        List<Country> list = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        list = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getCountryList(null, sessionId);
        return list;
    }

    public List<Region> getRegions() throws ScopixException {
        Integer id = (Integer) session.get("countryId_region");
        log.debug("ID: " + id);
        List<Region> list = new ArrayList<Region>();
        if (id != null && id > 0) {
            Region region = new Region();
            region.setCountry(new Country());
            region.getCountry().setId(id);
            list = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getRegionList(region, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
            if (list == null) {
                list = new ArrayList<Region>();
            }
        } else {
            list = new ArrayList<Region>();
        }
        return list;
    }

    public String goToExtractionPlanToPast() throws ScopixException {
        if (store.getId() == null) {
            this.addActionError(this.getText("error.extractionPlanToPast.selectStore"));
            return LIST;
        }
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        store = manager.getStore(store.getId(), sessionId);
        session.put("STORE_EXTRACTION_PLAN", store);
        List<Store> sts = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null,
                sessionId);

        /**
         * agregamos el store que se selecciono
         */
        Store stAssigned = new Store();
        stAssigned.setId(getStore().getId());
        stAssigned.setDescription(getStore().getDescription());
        List<Store> listAssigned = new ArrayList<Store>();
        listAssigned.add(stAssigned);
        session.put("storesAssigned", listAssigned);

        List<Store> nsts = new ArrayList<Store>();
        for (Store st : sts) {
            if (!stAssigned.getId().equals(st.getId())) {
                Store nst = new Store();
                nst.setId(st.getId());
                nst.setDescription(st.getDescription());
                nsts.add(nst);
            }
        }
        session.put("storesNoAssigned", nsts);
        return EXTRACTION_PLAN_TO_PAST;
    }

    public List<Store> getAssignedStore() {
        List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
        if (storesAssigned == null) {
            storesAssigned = new ArrayList<Store>();
            session.put("storesAssigned", storesAssigned);
        }
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("description", Boolean.FALSE);
        SortUtil.sortByColumn(cols, storesAssigned);
        return storesAssigned;
    }

    public List<Store> getNoAssignedStore() {
        List<Store> storesNoAssigned = (List<Store>) session.get("storesNoAssigned");
        if (storesNoAssigned == null) {
            storesNoAssigned = new ArrayList<Store>();
            session.put("storesNoAssigned", storesNoAssigned);
        }
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("description", Boolean.FALSE);
        SortUtil.sortByColumn(cols, storesNoAssigned);
        return storesNoAssigned;
    }

    public String addStore() {
        List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
        List<Store> storesNoAssigned = (List<Store>) session.get("storesNoAssigned");
        Collections.sort(storesNoAssigned);
        if (storesNotAssignedIds != null && storesNotAssignedIds.length > 0 && storesAssigned != null) {
            for (Integer id : storesNotAssignedIds) {
                Store storeNotAssigned = new Store();
                storeNotAssigned.setId(id);
                int index = Collections.binarySearch(storesNoAssigned, storeNotAssigned);
                if (index >= 0) {
                    storeNotAssigned = storesNoAssigned.remove(index);
                    if (storesAssigned == null) {
                        storesAssigned = new ArrayList<Store>();
                    }
                    storesAssigned.add(storeNotAssigned);
                }
            }
        }
        session.put("storesAssigned", storesAssigned);
        session.put("storesNoAssigned", storesNoAssigned);
        return EXTRACTION_PLAN_TO_PAST;
    }

    public String removeStore() {
        List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
        List<Store> storesNoAssigned = (List<Store>) session.get("storesNoAssigned");
        if (storesAssignedIds != null && storesAssignedIds.length > 0 && storesAssigned != null) {
            Collections.sort(storesAssigned);
            for (Integer id : storesAssignedIds) {
                Store storeAssigned = new Store();
                storeAssigned.setId(id);
                int index = Collections.binarySearch(storesAssigned, storeAssigned);
                if (index >= 0) {
                    storeAssigned = storesAssigned.remove(index);
                    if (storesAssigned == null) {
                        storesAssigned = new ArrayList<Store>();
                    }
                    storesNoAssigned.add(storeAssigned);
                }
            }
        }
        session.put("storesAssigned", storesAssigned);
        session.put("storesNoAssigned", storesNoAssigned);

        return EXTRACTION_PLAN_TO_PAST;
    }

    public String sendExtractionPlanToPast() throws ScopixException {
        List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
        if (extractionPlanToPastDate == null || extractionPlanToPastDate.after(this.getYesterdayAsDate())) {
            //extractionPlanToPastDate.after(this.getYesterdayAsDate())
            this.addActionError(this.getText("error.extractionPlanToPast.invalidDate"));
        } else if (storesAssigned == null || storesAssigned.isEmpty()) {
            this.addActionError("NO_storesAssignedIds");
        } else {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            store = (Store) session.get("STORE_EXTRACTION_PLAN");
//                evidenceRequests = manager.sendExtractionPlanToPast(store.getId(), extractionPlanToPastDate, sessionId);
            evidenceRequests = new ArrayList<EvidenceRequest>();
            for (Store s : storesAssigned) {
                //for (Integer storeID : storesAssignedIds) {
                List<EvidenceRequest> requestStore = manager.sendExtractionPlanToPast(s.getId(),
                        extractionPlanToPastDate,
                        sessionId);
                if (requestStore != null) {
                    evidenceRequests.addAll(requestStore);
                }
            }
            session.put("evidenceRequests", evidenceRequests);
        }
        return EXTRACTION_PLAN_TO_PAST_RESULT;
    }

    public String getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return sdf.format(cal.getTime());
    }

    public Date getYesterdayAsDate() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public String gotoExtractionPlanResult() {
        evidenceRequests = (List<EvidenceRequest>) session.get("evidenceRequests");
        return EXTRACTION_PLAN_TO_PAST_RESULT;
    }

    public String refreshRegions() {
        session.put("countryId_region", store.getCountry().getId());
        return EDIT;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map value) {
        this.session = value;
    }

    private void loadFilters() {
        store = (Store) getSession().get("sFilter");
    }

    public String getLatitudeCoordenate() {
        return latitudeCoordenate;
    }

    public void setLatitudeCoordenate(String latitudeCoordenate) {
        this.latitudeCoordenate = latitudeCoordenate;
    }

    public String getLongitudeCoordenate() {
        return longitudeCoordenate;
    }

    public void setLongitudeCoordenate(String longitudeCoordenate) {
        this.longitudeCoordenate = longitudeCoordenate;
    }

    /**
     * @return the days
     */
    public List<Integer> getDays() {
        if (days == null) {
            days = new ArrayList<Integer>();
        }
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(List<Integer> days) {
        this.days = days;
    }

    /**
     * @return the periodIntervals
     */
    public List<PeriodInterval> getPeriodIntervals() {
        periodIntervals = (List<PeriodInterval>) session.get("periodIntervals");
        if (periodIntervals == null) {
            periodIntervals = new ArrayList<PeriodInterval>();
        }
        return periodIntervals;
    }

    /**
     * @param periodIntervals the periodIntervals to set
     */
    public void setPeriodIntervals(List<PeriodInterval> periodIntervals) {
        this.periodIntervals = periodIntervals;
    }

    /**
     * @return the periodInterval
     */
    public PeriodInterval getPeriodInterval() {
        return periodInterval;
    }

    /**
     * @param periodInterval the periodInterval to set
     */
    public void setPeriodInterval(PeriodInterval periodInterval) {
        this.periodInterval = periodInterval;
    }

    private boolean isAValidTime(String time) {
        boolean resp = false;
        if (time != null && time.length() == 5) {
            String[] split = time.split(":");
            try {
                int hour = Integer.parseInt(split[0]);
                int min = Integer.parseInt(split[1]);
                if (hour >= 0 && hour < 24 && min >= 0 && min < 60) {
                    resp = true;
                }
            } catch (Exception e) {
                log.debug("parsing error");
            }
        }
        return resp;
    }

    private boolean isAValidRange(String initTime, String endTime) {
        boolean resp = false;
        try {
            Calendar calInit = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            if (initTime != null && initTime.length() == 5) {
                String[] split = initTime.split(":");

                int hour = Integer.parseInt(split[0]);
                int min = Integer.parseInt(split[1]);
                calInit.set(Calendar.HOUR, hour);
                calInit.set(Calendar.MINUTE, min);
            }
            if (endTime != null && endTime.length() == 5) {
                String[] split = endTime.split(":");
                int hour = Integer.parseInt(split[0]);
                int min = Integer.parseInt(split[1]);
                calEnd.set(Calendar.HOUR, hour);
                calEnd.set(Calendar.MINUTE, min);
            }
            if (calInit.before(calEnd)) {
                resp = true;
            }
        } catch (Exception e) {
            log.debug("parsing error");
        }
        return resp;
    }

    private boolean isValidSelectedDays() {
        boolean resp = true;
        List<PeriodInterval> pis = this.getPeriodIntervals();
        for (PeriodInterval pi : pis) {
            for (Integer day : days) {
                //CHECKSTYLE:OFF
                switch (day) {
                    case 2:
                        if (pi.isMonday()) {
                            resp = false;
                        }
                        break;
                    case 3:
                        if (pi.isTuesday()) {
                            resp = false;
                        }
                        break;
                    case 4:
                        if (pi.isWednesday()) {
                            resp = false;
                        }
                        break;
                    case 5:
                        if (pi.isThursday()) {
                            resp = false;
                        }
                        break;
                    case 6:
                        if (pi.isFriday()) {
                            resp = false;
                        }
                        break;
                    case 7:
                        if (pi.isSaturday()) {
                            resp = false;
                        }
                        break;
                    case 1:
                        if (pi.isSunday()) {
                            resp = false;
                        }
                        break;
                }
                //CHECKSTYLE:ON
                if (!resp) {
                    break;
                }
            }
            if (!resp) {
                break;
            }
        }
        return resp;
    }

    /**
     * @return the extractionPlanToPastDate
     */
    public Date getExtractionPlanToPastDate() {
        return extractionPlanToPastDate;
    }

    /**
     * @param extractionPlanToPastDate the extractionPlanToPastDate to set
     */
    public void setExtractionPlanToPastDate(Date extractionPlanToPastDate) {
        this.extractionPlanToPastDate = extractionPlanToPastDate;
    }

    /**
     * @return the evidenceRequests
     */
    public List<EvidenceRequest> getEvidenceRequests() {
        if (evidenceRequests == null) {
            evidenceRequests = new ArrayList<EvidenceRequest>();
        }
        return evidenceRequests;
    }

    /**
     * @param evidenceRequests the evidenceRequests to set
     */
    public void setEvidenceRequests(List<EvidenceRequest> evidenceRequests) {
        this.evidenceRequests = evidenceRequests;
    }

    public Integer[] getStoresAssignedIds() {
        return storesAssignedIds;
    }

    public void setStoresAssignedIds(Integer[] storesAssignedIds) {
        this.storesAssignedIds = storesAssignedIds;
    }

    public Integer[] getStoresNotAssignedIds() {
        return storesNotAssignedIds;
    }

    public void setStoresNotAssignedIds(Integer[] storesNotAssignedIds) {
        this.storesNotAssignedIds = storesNotAssignedIds;
    }

//    public String addStoreCloseSituation() {
//        List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
//        List<Store> storesNoAssigned = (List<Store>) session.get("storesNoAssigned");
//        Collections.sort(storesNoAssigned);
//        if (storesNotAssignedIds != null && storesNotAssignedIds.length > 0 && storesAssigned != null) {
//            for (Integer id : storesNotAssignedIds) {
//                Store storeNotAssigned = new Store();
//                storeNotAssigned.setId(id);
//                int index = Collections.binarySearch(storesNoAssigned, storeNotAssigned);
//                if (index >= 0) {
//                    storeNotAssigned = storesNoAssigned.remove(index);
//                    if (storesAssigned == null) {
//                        storesAssigned = new ArrayList<Store>();
//                    }
//                    storesAssigned.add(storeNotAssigned);
//                }
//            }
//        }
//        session.put("storesAssigned", storesAssigned);
//        session.put("storesNoAssigned", storesNoAssigned);
//        return CLOSE_SITUATION;
//    }
//
//    public String removeStoreCloseSituation() {
//        List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
//        List<Store> storesNoAssigned = (List<Store>) session.get("storesNoAssigned");
//        if (storesAssignedIds != null && storesAssignedIds.length > 0 && storesAssigned != null) {
//            Collections.sort(storesAssigned);
//            for (Integer id : storesAssignedIds) {
//                Store storeAssigned = new Store();
//                storeAssigned.setId(id);
//                int index = Collections.binarySearch(storesAssigned, storeAssigned);
//                if (index >= 0) {
//                    storeAssigned = storesAssigned.remove(index);
//                    if (storesAssigned == null) {
//                        storesAssigned = new ArrayList<Store>();
//                    }
//                    storesNoAssigned.add(storeAssigned);
//                }
//            }
//        }
//        session.put("storesAssigned", storesAssigned);
//        session.put("storesNoAssigned", storesNoAssigned);
//
//        return CLOSE_SITUATION;
//    }
    public String goToCloseSituation() throws ScopixException {
        if (store.getId() == null) {
            this.addActionError(this.getText("error.extractionPlanToPast.selectStore"));
            return LIST;
        }
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        store = manager.getStore(store.getId(), sessionId);
        List<Store> sts = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null,
                sessionId);

        List<Store> nsts = new ArrayList<Store>();
        for (Store st : sts) {
            Store nst = new Store();
            nst.setId(st.getId());
            nst.setDescription(st.getDescription());
            nsts.add(nst);
        }
        session.put("storesAssigned", nsts);

        List<SituationTemplate> situationTemplates = manager.getSituationTemplateList();
        session.put("situationTemplatesAssigned", situationTemplates);

        return CLOSE_SITUATION;
    }

    public String goToCloseSituationResult() {
        //evidenceRequests = (List<EvidenceRequest>) session.get("evidenceRequests");
        return CLOSE_SITUATION_RESULT;
    }

    public String sendCloseSituation() throws ScopixException {
        //List<Store> storesAssigned = (List<Store>) session.get("storesAssigned");
        if (closeSituationDate == null || closeSituationDate.after(this.getYesterdayAsDate())) {
            //extractionPlanToPastDate.after(this.getYesterdayAsDate())
            this.addActionError(this.getText("error.extractionPlanToPast.invalidDate"));
        } else if (storesAssignedIds == null || storesAssignedIds.length == 0) {
            this.addActionError("NO_storesAssignedIds");
        } else if (sitautionsAssignedIds == null || sitautionsAssignedIds.length == 0) {
            this.addActionError("NO_sitautionsAssignedIds");
        } else {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            EvaluationManager evaluationManager = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
            List<ObservedSituation> observedSituations = new ArrayList<ObservedSituation>();
            List<StoreVO> resumenStore = new ArrayList<StoreVO>();
            for (Integer storeId : storesAssignedIds) {
                List<ObservedSituation> observedSituationsStore = evaluationManager.closeSituations(storeId,
                        closeSituationDate,
                        sitautionsAssignedIds,
                        sessionId);
                if (observedSituationsStore != null && !observedSituationsStore.isEmpty()) {
                    Store s = observedSituationsStore.get(0).getObservedMetrics().get(0).getMetric().getStore();
                    StoreVO sVO = new StoreVO();
                    sVO.setId(s.getId());
                    sVO.setDescription(s.getDescription());
                    sVO.setName(s.getName());
                    sVO.setNumbersResult(observedSituationsStore.size());
                    resumenStore.add(sVO);
                    observedSituations.addAll(observedSituationsStore);
                }
            }
            if (!observedSituations.isEmpty()) {
                setPagePendingsCloseSituation("20");
                setPageResumenCloseSituation("20");
            }

            session.put("observedSituations", observedSituations);
            session.put("resumenStore", resumenStore);
        }
        return CLOSE_SITUATION_RESULT;
    }

    public List<SituationTemplate> getAssignedSituationTemplate() {
        List<SituationTemplate> situationTemplatesAssigned = (List<SituationTemplate>) session.get("situationTemplatesAssigned");
        if (situationTemplatesAssigned == null) {
            situationTemplatesAssigned = new ArrayList<SituationTemplate>();
            session.put("storesAssigned", situationTemplatesAssigned);
        }
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("name", Boolean.FALSE);
        SortUtil.sortByColumn(cols, situationTemplatesAssigned);
        return situationTemplatesAssigned;
    }

    public String showAllCloseSituation() {
        setPagePendingsCloseSituation((pagePendingsCloseSituation == null
                || !pagePendingsCloseSituation.equals("20")) ? "20" : "" + getObservedSituations().size());
        return CLOSE_SITUATION_RESULT;
    }

    public String showAllResumenCloseSituation() {
        setPageResumenCloseSituation((pageResumenCloseSituation == null
                || !pageResumenCloseSituation.equals("20")) ? "20" : "" + getResumenStore().size());
        return CLOSE_SITUATION_RESULT;
    }

    public Date getCloseSituationDate() {
        return closeSituationDate;
    }

    public void setCloseSituationDate(Date closeSituationDate) {
        this.closeSituationDate = closeSituationDate;
    }

    public List<ObservedSituation> getObservedSituations() {
        List<ObservedSituation> l = (List<ObservedSituation>) session.get("observedSituations");
        return l;
    }

    public String getPagePendingsCloseSituation() {
        return pagePendingsCloseSituation;
    }

    public void setPagePendingsCloseSituation(String pagePendingsCloseSituation) {
        this.pagePendingsCloseSituation = pagePendingsCloseSituation;
    }

    public Integer[] getSitautionsAssignedIds() {
        return sitautionsAssignedIds;
    }

    public void setSitautionsAssignedIds(Integer[] sitautionsAssignedIds) {
        this.sitautionsAssignedIds = sitautionsAssignedIds;
    }

    public List<StoreVO> getResumenStore() {
        List<StoreVO> l = (List<StoreVO>) session.get("resumenStore");
        return l;
    }

    public String getPageResumenCloseSituation() {
        return pageResumenCloseSituation;
    }

    public void setPageResumenCloseSituation(String pageResumenCloseSituation) {
        this.pageResumenCloseSituation = pageResumenCloseSituation;
    }
}
