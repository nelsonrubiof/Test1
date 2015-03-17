/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Country;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/country/countryManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/country/countryList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/country/countryEdit.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class CountryAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private Country country;
    private List<Country> countries;
    private Map session;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (getCountry() == null || getCountry().getId() != null) {
            setCountry((Country) getSession().get("countryFilter"));
        }
        if (getCountry() != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setCountries(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getCountryList(
                    getCountry(), sessionId));
            getSession().put("countryFilter", getCountry());
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newCountry() {
        this.setEditable(false);
        setCountry(new Country());
        //session.remove("countryId");
        return EDIT;
    }

    public String editCountry() throws ScopixException {
        if (getCountry() != null && getCountry().getId() != null && getCountry().getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            setCountry(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getCountry(getCountry().
                    getId(), sessionId));
        //session.put("storeId_eess", store.getId());
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.country")}));
        }
        return EDIT;
    }

    public String deleteCountry() throws ScopixException {
        if (getCountry() != null && getCountry().getId() != null && getCountry().getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).removeCountry(getCountry().getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.country")}));
        }
        this.list();
        return LIST;
    }

    public String saveCountry() throws ScopixException {
        if (country.getName() == null || country.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (country.getDescription() == null || country.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (country.getCode() == null || country.getCode().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.code")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    updateCountry(getCountry(), sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    addCountry(getCountry(), sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String refreshCountry() {
        //session.put("eessId", store.getEvidenceServicesServer().getId());
        //session.put("storeId_eess", store.getId());
        return EDIT;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    private void loadFilters() {
        country = (Country) getSession().get("countryFilter");
    }
}
