/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/area/areaManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/area/areaList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/area/areaEdit.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class AreasAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private Map session;
    private Area area;
    private List<Area> areas;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (getArea() == null || getArea().getId() != null) {
            setArea((Area) getSession().get("aFilter"));
        }
        if (getArea() != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            areas =
                    (List<Area>) SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getAreaList(area, sessionId);
            getSession().put("aFilter", getArea());
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newArea() {
        this.setEditable(false);
        setArea(new Area());
        return EDIT;
    }

    public String editArea() throws ScopixException {
        if (getArea() != null && getArea().getId() != null && getArea().getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setArea((Area) SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getArea(getArea().
                    getId(), sessionId));
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.area")}));
        }
        return EDIT;
    }

    public String deleteArea() throws ScopixException {
        if (area != null && area.getId() != null && area.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).removeArea(area.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.area")}));
        }
        this.list();
        return LIST;
    }

    public String saveArea() throws ScopixException {
        if (area.getName() == null || area.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (area.getDescription() == null || area.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (area.getStore() == null || area.getStore().getId() == null || area.getStore().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.store")}));
        }
        if (area.getAreaType() == null || area.getAreaType().getId() == null || area.getAreaType().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.areaType")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).updateArea(area, sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).addArea(area, sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public List<Store> getStores() throws ScopixException {
        List<Store> stores = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        stores = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null, sessionId);
        return stores;
    }

    public List<AreaType> getAreaTypes() throws ScopixException {
        List<AreaType> areaTypes = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        areaTypes = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaTypeList(null,
                sessionId);
        return areaTypes;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreas() {
        if (areas == null) {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    private void loadFilters() {
        area = (Area) getSession().get("aFilter");
    }
}
