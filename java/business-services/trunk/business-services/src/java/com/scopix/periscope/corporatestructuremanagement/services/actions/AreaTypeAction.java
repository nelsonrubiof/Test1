/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/areatype/areaTypeManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/areatype/areaTypeList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/areatype/areaTypeEdit.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class AreaTypeAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private Map session;
    private AreaType areaType;
    private List<AreaType> areaTypes;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (areaType == null || areaType.getId() != null) {
            areaType = (AreaType) getSession().get("atFilter");
        }
        if (areaType != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            areaTypes = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaTypeList(areaType,
                    sessionId);
            getSession().put("atFilter", areaType);
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newAreaType() {
        this.setEditable(false);
        areaType = new AreaType();
        return EDIT;
    }

    public String editAreaType() throws ScopixException {
        if (areaType != null && areaType.getId() != null && areaType.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            areaType = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaType(
                    areaType.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.areaType")}));
        }
        return EDIT;
    }

    public String deleteAreaType() throws ScopixException {
        if (areaType != null && areaType.getId() != null && areaType.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).removeAreaType(areaType.getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.areaType")}));
        }
        this.list();
        return LIST;
    }

    public String saveAreaType() throws ScopixException {
        if (areaType.getName() == null || areaType.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (areaType.getDescription() == null || areaType.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }

        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).updateAreaType(areaType, sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).addAreaType(areaType, sessionId);
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

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public List<AreaType> getAreaTypes() {
        return areaTypes;
    }

    public void setAreaTypes(List<AreaType> areaTypes) {
        this.areaTypes = areaTypes;
    }

    private void loadFilters() {
        areaType = (AreaType) getSession().get("atFilter");
    }
}
