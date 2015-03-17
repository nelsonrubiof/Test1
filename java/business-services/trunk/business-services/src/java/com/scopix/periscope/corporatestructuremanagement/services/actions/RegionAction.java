/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Country;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/region/regionManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/region/regionList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/region/regionEdit.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class RegionAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private Region region;
    private List<Region> regions;
    private Map session;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (getRegion() == null || getRegion().getId() != null) {
            setRegion((Region) getSession().get("regionFilter"));
        }
        if (getRegion() != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setRegions(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getRegionList(getRegion(),
                    sessionId));
            getSession().put("regionFilter", getRegion());
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newRegion() {
        this.setEditable(false);
        setRegion(new Region());
        //session.remove("countryId");
        return EDIT;
    }

    public String editRegion() throws ScopixException {
        if (getRegion() != null && getRegion().getId() != null && getRegion().getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setRegion(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getRegion(
                    getRegion().getId(), sessionId));
        //session.put("storeId_eess", store.getId());
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.region")}));
        }
        return EDIT;
    }

    public String deleteRegion() throws ScopixException {
        if (getRegion() != null && getRegion().getId() != null && getRegion().getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).removeRegion(getRegion().getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.region")}));
        }
        this.list();
        return LIST;
    }

    public String saveRegion() throws ScopixException {
        if (region.getName() == null || region.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (region.getDescription() == null || region.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).updateRegion(getRegion(), sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).addRegion(getRegion(), sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String refreshRegion() {
        //session.put("eessId", store.getEvidenceServicesServer().getId());
        //session.put("storeId_eess", store.getId());
        return EDIT;
    }

    public List<Country> getCountries() throws ScopixException {
        List<Country> list = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        list = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getCountryList(null, sessionId);
        return list;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    private void loadFilters() {
        region = (Region) getSession().get("regionFilter");
    }
}
