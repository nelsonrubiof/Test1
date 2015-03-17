/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.Location;
import com.scopix.periscope.corporatestructuremanagement.RelationEvidenceProviderLocation;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/location/locationManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/location/locationList.jsp"),
    @Result(name = "areaFilter", value = "/WEB-INF/jsp/corporatemanagement/location/areaFilter.jsp")
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class LocationEPAction extends BaseAction implements SessionAware {
    private static Logger log = Logger.getLogger(LocationEPAction.class);
    private static final String LIST = "list";
    private static final String SHOW_AREA_FILTER = "areaFilter";
    private Integer areaId;
    private Integer storeId;
    private Map session;
    private List<EvidenceProvider> evidenceProviders;
    private List<EvidenceProviderWithRelations> evidenceProviderWithRelationses;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (storeId != null && storeId > 0 && areaId != null && areaId > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            EvidenceProvider evidenceProvider = new EvidenceProvider();
            //evidenceProvider.setArea(new Area());
            evidenceProvider.setStore(new Store());
            evidenceProvider.getStore().setId(storeId);
            Area a = new Area();
            a.setId(areaId);
            evidenceProvider.getAreas().add(a);
            evidenceProviders = manager.getEvidenceProviderListByArea(evidenceProvider, sessionId);
            //evidenceProviders = manager.getEvidenceProviderList(evidenceProvider, sessionId);
            evidenceProviderWithRelationses = new ArrayList<EvidenceProviderWithRelations>();
            for (EvidenceProvider ep : evidenceProviders) {
                EvidenceProviderWithRelations epwr = new EvidenceProviderWithRelations();
                epwr.setEvidenceProvider(ep);
                for (RelationEvidenceProviderLocation rel : ep.getRelationEvidenceProviderLocationsFrom()) {
                    if (rel.getLocation().equals(Location.LEFT)) {
                        epwr.setLeftPosition(rel);
                    } else if (rel.getLocation().equals(Location.RIGHT)) {
                        epwr.setRightPosition(rel);
                    } else if (rel.getLocation().equals(Location.TOP)) {
                        epwr.setTopPosition(rel);
                    } else if (rel.getLocation().equals(Location.BOTTOM)) {
                        epwr.setBottomPosition(rel);
                    }
                    epwr.setViewOrder(rel.getViewOrder());
                    epwr.setDefaultEvidenceProvider(
                            rel.getDefaultEvidenceProvider() != null ? rel.getDefaultEvidenceProvider() : false);
                }
                evidenceProviderWithRelationses.add(epwr);
            }
            session.put("EVIDENCE_PROVIDERS_LOCATIONS", evidenceProviders);
        } else {
            this.addActionError(this.getText("requiredFields.missing"));
        }
        return LIST;
    }

    public String save() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        evidenceProviders = (List<EvidenceProvider>) session.get("EVIDENCE_PROVIDERS_LOCATIONS");
        HttpServletRequest request = ServletActionContext.getRequest();
        Area a = new Area();
        a.setId(areaId);
        for (EvidenceProvider ep : evidenceProviders) {
            List<RelationEvidenceProviderLocation> evidenceProviderLocations = new ArrayList<RelationEvidenceProviderLocation>();
            String left = request.getParameter("left_" + ep.getId());
            String right = request.getParameter("right_" + ep.getId());
            String top = request.getParameter("top_" + ep.getId());
            String bottom = request.getParameter("bottom_" + ep.getId());
            String defaultEvidenceProvider = request.getParameter("defaultEvidenceProvider");
            String viewOrder = request.getParameter("viewOrder_" + ep.getId());
            if (left != null && !left.equals("-1")) {
                log.debug("agregando left " + ep.getId());
                RelationEvidenceProviderLocation repl = new RelationEvidenceProviderLocation();
                repl.setArea(a);
                repl.setEvidenceProviderFrom(ep);
                EvidenceProvider epAux = new EvidenceProvider();
                epAux.setId(Integer.parseInt(left));
                repl.setEvidenceProviderTo(epAux);
                repl.setLocation(Location.LEFT);
                evidenceProviderLocations.add(repl);
            }
            if (right != null && !right.equals("-1")) {
                log.debug("agregando right " + ep.getId());
                RelationEvidenceProviderLocation repl = new RelationEvidenceProviderLocation();
                repl.setArea(a);
                repl.setEvidenceProviderFrom(ep);
                EvidenceProvider epAux = new EvidenceProvider();
                epAux.setId(Integer.parseInt(right));
                repl.setEvidenceProviderTo(epAux);
                repl.setLocation(Location.RIGHT);
                evidenceProviderLocations.add(repl);
            }
            if (top != null && !top.equals("-1")) {
                log.debug("agregando top " + ep.getId());
                RelationEvidenceProviderLocation repl = new RelationEvidenceProviderLocation();
                repl.setArea(a);
                repl.setEvidenceProviderFrom(ep);
                EvidenceProvider epAux = new EvidenceProvider();
                epAux.setId(Integer.parseInt(top));
                repl.setEvidenceProviderTo(epAux);
                repl.setLocation(Location.TOP);
                evidenceProviderLocations.add(repl);
            }
            if (bottom != null && !bottom.equals("-1")) {
                log.debug("agregando bottom " + ep.getId());
                RelationEvidenceProviderLocation repl = new RelationEvidenceProviderLocation();
                repl.setArea(a);
                repl.setEvidenceProviderFrom(ep);
                EvidenceProvider epAux = new EvidenceProvider();
                epAux.setId(Integer.parseInt(bottom));
                repl.setEvidenceProviderTo(epAux);
                repl.setLocation(Location.BOTTOM);
                evidenceProviderLocations.add(repl);
            }
            //Setear los valores seleccionados
            if (defaultEvidenceProvider != null) {
                Integer dep = new Integer(defaultEvidenceProvider);
                if (dep.equals(ep.getId())) {
                    for (RelationEvidenceProviderLocation repl : evidenceProviderLocations) {
                        log.debug("setDefault relation " + repl.getId());
                        repl.setDefaultEvidenceProvider(Boolean.TRUE);
                    }
                }
            }
            if (viewOrder != null && !viewOrder.equals("-1")) {
                Integer vo = new Integer(viewOrder);
                for (RelationEvidenceProviderLocation repl : evidenceProviderLocations) {
                    log.debug("setViewOrder relation " + repl.getId());
                    repl.setViewOrder(vo);
                }
            }
            log.debug("actualizando Relacion " +ep.getId() + " locations " + evidenceProviderLocations.size());
            manager.updateRelationEvidenceProviderLocation(ep.getId(), evidenceProviderLocations, sessionId);
        }
        return SUCCESS;
    }

    public List<EvidenceProvider> evidenceProviders(Integer id) {
        List<EvidenceProvider> eps = new ArrayList<EvidenceProvider>();
        if (id != null && session.get("EVIDENCE_PROVIDERS_LOCATIONS") != null) {
            eps.addAll((List<EvidenceProvider>) session.get("EVIDENCE_PROVIDERS_LOCATIONS"));
            Collections.sort(eps);
            EvidenceProvider ep = new EvidenceProvider();
            ep.setId(id);
            int index = Collections.binarySearch(eps, ep);
            if (index >= 0) {
                eps.remove(index);
            }
        }
        return eps;
    }

    public List<Integer> getPositions() {
        List<Integer> positions = new ArrayList<Integer>();
        if (session.get("EVIDENCE_PROVIDERS_LOCATIONS") != null) {
            List<EvidenceProvider> eps = new ArrayList<EvidenceProvider>();
            eps.addAll((List<EvidenceProvider>) session.get("EVIDENCE_PROVIDERS_LOCATIONS"));
            for (int i = 0; i < eps.size(); i++) {
                positions.add(i + 1);
            }
        }
        return positions;
    }

    public List<Store> getStores() throws ScopixException {
        List<Store> stores = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        stores = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null, sessionId);
        return stores;
    }

    public String showAreaFilter() {
        session.put("idStoreLocationEPFilter", storeId);
        return SHOW_AREA_FILTER;
    }

    public List<Area> getAreasFilter() throws ScopixException {
        List<Area> areas = null;
        storeId = (Integer) session.get("idStoreLocationEPFilter");
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

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * @return the areaId
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the evidenceProviders
     */
    public List<EvidenceProvider> getEvidenceProviders() {
        return evidenceProviders;
    }

    /**
     * @param evidenceProviders the evidenceProviders to set
     */
    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
    }

    /**
     * @return the evidenceProviderWithRelations
     */
    public List<EvidenceProviderWithRelations> getEvidenceProviderWithRelationses() {
        if (evidenceProviderWithRelationses == null) {
            evidenceProviderWithRelationses = new ArrayList<EvidenceProviderWithRelations>();
        }
        return evidenceProviderWithRelationses;
    }

    /**
     * @param evidenceProviderWithRelations the evidenceProviderWithRelations to set
     */
    public void setEvidenceProviderWithRelationses(List<EvidenceProviderWithRelations> evidenceProviderWithRelations) {
        this.evidenceProviderWithRelationses = evidenceProviderWithRelations;
    }
}

class EvidenceProviderWithRelations {

    private EvidenceProvider evidenceProvider;
    private RelationEvidenceProviderLocation leftPosition;
    private RelationEvidenceProviderLocation rightPosition;
    private RelationEvidenceProviderLocation topPosition;
    private RelationEvidenceProviderLocation bottomPosition;
    private boolean defaultEvidenceProvider;
    private Integer viewOrder;

    /**
     * @return the evidenceProvider
     */
    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    /**
     * @param evidenceProvider the evidenceProvider to set
     */
    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    /**
     * @return the left
     */
    public RelationEvidenceProviderLocation getLeftPosition() {
        if (leftPosition == null) {
            leftPosition = new RelationEvidenceProviderLocation();
            leftPosition.setEvidenceProviderTo(new EvidenceProvider());
            leftPosition.getEvidenceProviderTo().setId(-1);
            leftPosition.setLocation(Location.LEFT);
        }
        return leftPosition;
    }

    /**
     * @param left the left to set
     */
    public void setLeftPosition(RelationEvidenceProviderLocation left) {
        this.leftPosition = left;
    }

    /**
     * @return the right
     */
    public RelationEvidenceProviderLocation getRightPosition() {
        if (rightPosition == null) {
            rightPosition = new RelationEvidenceProviderLocation();
            rightPosition.setEvidenceProviderTo(new EvidenceProvider());
            rightPosition.getEvidenceProviderTo().setId(-1);
            rightPosition.setLocation(Location.RIGHT);
        }
        return rightPosition;
    }

    /**
     * @param right the right to set
     */
    public void setRightPosition(RelationEvidenceProviderLocation right) {
        this.rightPosition = right;
    }

    /**
     * @return the top
     */
    public RelationEvidenceProviderLocation getTopPosition() {
        if (topPosition == null) {
            topPosition = new RelationEvidenceProviderLocation();
            topPosition.setEvidenceProviderTo(new EvidenceProvider());
            topPosition.getEvidenceProviderTo().setId(-1);
            topPosition.setLocation(Location.TOP);
        }
        return topPosition;
    }

    /**
     * @param top the top to set
     */
    public void setTopPosition(RelationEvidenceProviderLocation top) {
        this.topPosition = top;
    }

    /**
     * @return the bottom
     */
    public RelationEvidenceProviderLocation getBottomPosition() {
        if (bottomPosition == null) {
            bottomPosition = new RelationEvidenceProviderLocation();
            bottomPosition.setEvidenceProviderTo(new EvidenceProvider());
            bottomPosition.getEvidenceProviderTo().setId(-1);
            bottomPosition.setLocation(Location.BOTTOM);
        }
        return bottomPosition;
    }

    /**
     * @param bottom the bottom to set
     */
    public void setBottomPosition(RelationEvidenceProviderLocation bottom) {
        this.bottomPosition = bottom;
    }

    /**
     * @return the defaultEvidenceProvider
     */
    public boolean isDefaultEvidenceProvider() {
        return defaultEvidenceProvider;
    }

    /**
     * @param defaultEvidenceProvider the defaultEvidenceProvider to set
     */
    public void setDefaultEvidenceProvider(boolean defaultEvidenceProvider) {
        this.defaultEvidenceProvider = defaultEvidenceProvider;
    }

    /**
     * @return the viewOrder
     */
    public Integer getViewOrder() {
        return viewOrder;
    }

    /**
     * @param viewOrder the viewOrder to set
     */
    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }
}
