/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/sensor/sensorManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/sensor/sensorList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/sensor/sensorEdit.jsp"),
    @Result(name = "areaFilter", value = "/WEB-INF/jsp/corporatemanagement/sensor/areaFilter.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class SensorAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String SHOW_AREA_FILTER = "areaFilter";
    private Sensor sensor;
    private Map session;
    private List<Sensor> sensors;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (getSensor() == null || getSensor().getId() != null) {
            setSensor((Sensor) getSession().get("sensorFilter"));
        }
        if (getSensor() != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            sensors = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getSensorList(getSensor(),
                    sessionId);
            session.put("sensorFilter", getSensor());
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newSensor() {
        this.setEditable(false);
        setSensor(new Sensor());
        session.remove("idStoreSensor");
        return EDIT;
    }

    public String editSensor() throws ScopixException {
        if (getSensor() != null && getSensor().getId() != null && getSensor().getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setSensor(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getSensor(
                    getSensor().getId(), sessionId));
            session.put("idStoreSensor",
                    (getSensor() != null && getSensor().getStore() != null ? getSensor().getStore().getId() : null));

        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.sensor")}));
        }
        return EDIT;
    }

    public String deleteSensor() throws ScopixException {
        if (getSensor() != null && getSensor().getId() != null && getSensor().getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).removeSensor(getSensor().getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.sensor")}));
        }
        this.list();
        return LIST;
    }

    public String saveSensor() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        if (getSensor().getName() == null || getSensor().getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (getSensor().getName() != null && manager.getSensor(getSensor().getName(), sessionId) != null) {
            this.addActionError(this.getText("error.general.repeatValue", new String[]{this.getText("label.name")}));
        }
        if (getSensor().getDescription() == null || getSensor().getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (getSensor().getStore() == null || getSensor().getStore().getId() == null || getSensor().getStore().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.store")}));
        }
        if (getSensor().getArea() == null || getSensor().getArea().getId() == null || getSensor().getArea().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.area")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        if (this.isEditable()) {
            manager.updateSensor(getSensor(), sessionId);
        } else {
            manager.addSensor(getSensor(), sessionId);
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

    public List<Store> getStores() throws ScopixException {
        List<Store> stores = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        stores = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null, sessionId);
        return stores;
    }

    public String showAreaFilter() {
        session.put("idStoreSensorFilter", (getSensor() != null && getSensor().getStore() != null ? getSensor().getStore().getId()
                : null));
        return SHOW_AREA_FILTER;
    }

    public String showArea() {
        session.put("idStoreSensor",
                (getSensor() != null && getSensor().getStore() != null ? getSensor().getStore().getId() : null));
        return EDIT;
    }

    public List<Area> getAreasFilter() throws ScopixException {
        List<Area> areas = null;
        Integer storeId = (Integer) session.get("idStoreSensorFilter");
        if (storeId != null && storeId > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            Area area = new Area();
            area.setStore(new Store());
            area.getStore().setId(storeId);
            areas = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaList(area, sessionId);
        } else {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public List<Area> getAreas() throws ScopixException {
        List<Area> areas = null;
        Integer storeId = (Integer) session.get("idStoreSensor");
        if (storeId != null && storeId > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            Area area = new Area();
            area.setStore(new Store());
            area.getStore().setId(storeId);
            areas = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaList(area, sessionId);
        } else {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    private void loadFilters() {
        setSensor((Sensor) getSession().get("sensorFilter"));
    }

    /**
     * @return the sensors
     */
    public List<Sensor> getSensors() {
        if (sensors == null) {
            sensors = new ArrayList<Sensor>();
        }
        return sensors;
    }

    /**
     * @param sensors the sensors to set
     */
    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    /**
     * @return the sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
