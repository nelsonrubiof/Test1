/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * UserSecurityManagementAction.java
 *
 * Created on 16-06-2008, 11:42:13 AM
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.securitymanagement.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.StringUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.AreaType;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.securitymanagement.Store;
import com.scopix.periscope.securitymanagement.SynchronizationManager;
import com.scopix.periscope.securitymanagement.UserState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author César Abarza Suazo.
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/securitymanagement/users/userManagement.jsp"),
    @Result(name = "filter", value = "/WEB-INF/jsp/securitymanagement/users/filter.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/securitymanagement/users/list.jsp"),
    @Result(name = "group", value = "/WEB-INF/jsp/securitymanagement/users/groups.jsp"),
    @Result(name = "areaTypes", value = "/WEB-INF/jsp/securitymanagement/users/areaTypes.jsp"),
    @Result(name = "stores", value = "/WEB-INF/jsp/securitymanagement/users/stores.jsp"),
    @Result(name = "corporates", value = "/WEB-INF/jsp/securitymanagement/users/corporates.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/securitymanagement/users/edit.jsp")
})
@Namespace("/securitymanagement")
@ParentPackage("default")
public class UserSecurityManagementAction extends BaseAction implements SessionAware {

    private static final String FILTER = "filter";
    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String GROUP = "group";
    private static final String GROUP_AREA_TYPES = "areaTypes";
    private static final String GROUP_STORES = "stores";
    private static final String GROUP_CORPORATES = "corporates";
    private List<PeriscopeUser> users;
    private PeriscopeUser periscopeUser;
    private Map session;
    private String password2;
    private Integer[] userGroups;
    private Integer[] noUserGroups;
    private Integer[] userAreaTypes;
    private Integer[] noUserAreaTypes;
    private Integer[] userStores;
    private Integer[] noUserStores;
    private Integer[] userCorporates;
    private Integer[] noUserCorporates;
    private String userState;
    private SynchronizationManager synchronizationManager;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (periscopeUser == null || periscopeUser.getName() == null) {
            periscopeUser = (PeriscopeUser) session.get("userFilter");
        }
        if (periscopeUser != null) {
            users = SpringSupport.getInstance().
                    findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                    getUserList(periscopeUser, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
            session.put("userFilter", periscopeUser);
        }
        return LIST;
    }

    public String filter() {
        return FILTER;
    }

    public String newUser() {
        this.setEditable(false);
        periscopeUser = new PeriscopeUser();
        session.put("user", periscopeUser);
        session.remove("rolesGroups");
        session.remove("areaTypes");
        session.remove("stores");
        session.remove("corporates");
        return EDIT;
    }

    public String editUser() throws ScopixException {
        this.setEditable(true);
        periscopeUser = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class).getUser(periscopeUser.getId(), session.containsKey(
                "sessionId") ? (Long) session.get("sessionId") : 0);
        password2 = periscopeUser.getPassword();
        userState = periscopeUser.getUserState().getName();
        session.put("startDate", periscopeUser.getStartDate());
        periscopeUser.getRolesGroups().isEmpty();
        periscopeUser.getAreaTypes().isEmpty();
        periscopeUser.getStores().isEmpty();
        periscopeUser.getCorporates().isEmpty();
        session.put("user", periscopeUser);
        session.remove("rolesGroups");
        session.remove("areaTypes");
        session.remove("stores");
        session.remove("corporates");
        return EDIT;
    }

    public String save() throws ScopixException {
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);

        //Validar que vengan todos los datos
        //Validar claves = y argo 6
        //Validar nombre de usuario no exista
        if (periscopeUser.getName() == null || periscopeUser.getName().length() == 0) {
            this.addActionError(this.getText("error.securitymanagement.userName.invalid"));
        }
        if (userState == null || userState.equals("-1")) {
            this.addActionError(this.getText("error.securitymanagement.userState.invalid"));
        }
        if (periscopeUser.getEmail() == null || !StringUtil.validateString(periscopeUser.getEmail(), StringUtil.EMAIL)) {
            this.addActionError(this.getText("error.securitymanagement.email.invalid"));
        }
        if (periscopeUser.getPassword() == null || periscopeUser.getPassword().length() < 6) {
            this.addActionError(this.getText("error.securitymanagement.password.invalid"));
        }
        if (password2 == null || password2.length() < 6) {
            this.addActionError(this.getText("error.securitymanagement.password2.invalid"));
        }
        if (!password2.equals(periscopeUser.getPassword())) {
            this.addActionError(this.getText("error.securitymanagement.passwords.notEqual"));
        }
        if (periscopeUser.getMainCorporate().getId() != null && periscopeUser.getMainCorporate().getId().equals(-1)) {
            this.addActionError(this.getText("error.securitymanagement.mainCorporateRequired"));
        }
        Long sessionId = (session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        if (!this.isEditable()
                && periscopeUser.getName() != null
                && secMan.findSpecificUserByName(periscopeUser.getName(), sessionId) != null) {
            this.addActionError(this.getText("error.securitymanagement.user.alreadyExist"));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }
        PeriscopeUser user = (PeriscopeUser) session.get("user");
        if (user != null) {
            periscopeUser.setRolesGroups(user.getRolesGroups());
            periscopeUser.setAreaTypes(user.getAreaTypes());
            periscopeUser.setStores(user.getStores());
            periscopeUser.setCorporates(user.getCorporates());
            session.remove("user");
        }
        periscopeUser.setUserState(UserState.valueOf(userState));
        if (isEditable()) {
            periscopeUser.setStartDate((Date) session.get("startDate"));
            secMan.updateUser(periscopeUser, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        } else {
            secMan.addUser(periscopeUser, session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        }
        getSynchronizationManager().synchronizeUser(periscopeUser.getId());
        this.loadFilters();
        return SUCCESS;
    }

    public String deleteUser() throws ScopixException {
        SpringSupport.getInstance().findBeanByClassName(com.scopix.periscope.securitymanagement.SecurityManager.class).
                deleteUser(periscopeUser.getId(), session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);

        getSynchronizationManager().synchronizeUser(periscopeUser.getId());
        this.list();
        return LIST;
    }

    public List<PeriscopeUser> getUsers() {
        if (users == null) {
            users = new ArrayList<PeriscopeUser>();
        }
        return users;
    }

    public void setUsers(List<PeriscopeUser> users) {
        this.users = users;
    }

    public PeriscopeUser getPeriscopeUser() {
        return periscopeUser;
    }

    public void setPeriscopeUser(PeriscopeUser user) {
        this.periscopeUser = user;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return this.session;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public UserState[] getUserStates() {
        UserState[] userStates = UserState.values();
        return userStates;
    }

    public Integer[] getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Integer[] userGroups) {
        this.userGroups = userGroups;
    }

    public Integer[] getNoUserGroups() {
        return noUserGroups;
    }

    public void setNoUserGroups(Integer[] noUserGroups) {
        this.noUserGroups = noUserGroups;
    }

    public String showGroups() {
        periscopeUser = (PeriscopeUser) session.get("user");
        return GROUP;
    }

    public String showAreaTypes() {
        periscopeUser = (PeriscopeUser) session.get("user");
        return GROUP_AREA_TYPES;
    }

    public String showStores() {
        periscopeUser = (PeriscopeUser) session.get("user");
        return GROUP_STORES;
    }

    public String showCorporates() {
        periscopeUser = (PeriscopeUser) session.get("user");
        return GROUP_CORPORATES;
    }

    public List<RolesGroup> getRolesGroup() throws ScopixException {
        List<RolesGroup> rolesGroup = (List<RolesGroup>) session.get("rolesGroups");
        if (rolesGroup == null) {
            rolesGroup = SpringSupport.getInstance().findBeanByClassName(
                    com.scopix.periscope.securitymanagement.SecurityManager.class).
                    getRolesGroupList(new RolesGroup(), session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        }
        if (rolesGroup != null) {
            Collections.sort(rolesGroup);
            for (RolesGroup roleGroup : periscopeUser.getRolesGroups()) {
                int index = Collections.binarySearch(rolesGroup, roleGroup);
                if (index >= 0) {
                    rolesGroup.remove(index);
                }
            }
            session.put("rolesGroups", rolesGroup);
        }
        return rolesGroup;
    }

    public List<AreaType> getAreaTypes() throws ScopixException {
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        List<AreaType> areaTypes = (List<AreaType>) session.get("areaTypes");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (areaTypes == null) {
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            areaTypes = secMan.getAreaTypeList(new AreaType(), sessionId);
        }
        if (areaTypes != null) {
            Collections.sort(areaTypes);
            for (AreaType areaType : periscopeUser.getAreaTypes()) {
                int index = Collections.binarySearch(areaTypes, areaType);
                if (index >= 0) {
                    areaTypes.remove(index);
                }
            }
            session.put("areaTypes", areaTypes);
        }
        Collections.sort(areaTypes, AreaType.compratorByDescriptionWithCorporate);
        return areaTypes;
    }

    public List<Store> getStores() throws ScopixException {
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        List<Store> stores = (List<Store>) session.get("stores");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (stores == null) {
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            stores = secMan.getStoreList(new Store(), sessionId);
        }
        if (stores != null) {
            Collections.sort(stores);
            for (Store store : periscopeUser.getStores()) {
                int index = Collections.binarySearch(stores, store);
                if (index >= 0) {
                    stores.remove(index);
                }
            }
            session.put("stores", stores);
        }
        Collections.sort(stores, Store.compratorByDescriptionWithCorporate);
        return stores;
    }

    /**
     * Retorna la lista de corporates que NO han sido asignados al usuario
     *
     * Obtiene la lista de corporates del sistema y le quita los que ya han sido asignados
     * al usuario
     *
     * @return Lista de Corporate NO asignados al usuario
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public List<Corporate> getCorporates() throws ScopixException {
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        List<Corporate> corporates = (List<Corporate>) session.get("corporates");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (corporates == null) {
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            corporates = secMan.getCorporateList(new Corporate(), sessionId);
        }
        if (corporates != null) {
            Collections.sort(corporates);
            for (Corporate corporate : periscopeUser.getCorporates()) {
                int index = Collections.binarySearch(corporates, corporate);
                if (index >= 0) {
                    corporates.remove(index);
                }
            }
            session.put("corporates", corporates);
        }
        Collections.sort(corporates, Corporate.compratorByDescription);
        return corporates;
    }

    /**
     * Retorna la lista de corporates definidos en el sistema
     *
     * Obtiene y retorna la lista de corporates del SecurityManager
     *
     * @return Lista de Corporate NO asignados al usuario
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public List<Corporate> getAllCorporates() throws ScopixException {
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        List<Corporate> corporates = null;
        long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
        corporates = secMan.getCorporateList(new Corporate(), sessionId);
        Collections.sort(corporates, Corporate.compratorByDescription);
        return corporates;
    }

    public List<RolesGroup> getAssignRolesGroup() {
        List<RolesGroup> rolesGroup = ((PeriscopeUser) session.get("user")).getRolesGroups();
        return rolesGroup;
    }

    public List<AreaType> getAssignAreaTypes() {
        List<AreaType> areaTypes = ((PeriscopeUser) session.get("user")).getAreaTypes();
        Collections.sort(areaTypes, AreaType.compratorByDescriptionWithCorporate);
        return areaTypes;
    }

    public List<Store> getAssignStores() {
        List<Store> stores = ((PeriscopeUser) session.get("user")).getStores();
        Collections.sort(stores, Store.compratorByDescriptionWithCorporate);
        return stores;
    }

    /**
     * Retorna la lista de corporates  asignados al usuario ordenados por descripcion
     *
     * @return Lista de Corporate asignados al usuario
     */
    public List<Corporate> getAssignCorporates() {
        List<Corporate> corporates = ((PeriscopeUser) session.get("user")).getCorporates();
        Collections.sort(corporates, Corporate.compratorByDescription);
        return corporates;
    }

    public String addGroupToUser() {
        List<RolesGroup> rolesGroups = (List<RolesGroup>) session.get("rolesGroups");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (noUserGroups != null && noUserGroups.length > 0 && rolesGroups != null) {
            Collections.sort(rolesGroups);
            for (Integer id : noUserGroups) {
                RolesGroup roleGroup = new RolesGroup();
                roleGroup.setId(id);
                int index = Collections.binarySearch(rolesGroups, roleGroup);
                if (index >= 0) {
                    roleGroup = rolesGroups.remove(index);
                    periscopeUser.getRolesGroups().add(roleGroup);
                }
            }
            session.put("rolesGroups", rolesGroups);
        }
        return GROUP;
    }

    public String addAreaTypeToUser() {
        List<AreaType> areaTypes = (List<AreaType>) session.get("areaTypes");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (noUserAreaTypes != null && noUserAreaTypes.length > 0 && areaTypes != null) {
            Collections.sort(areaTypes);
            for (Integer id : noUserAreaTypes) {
                AreaType at = new AreaType();
                at.setId(id);
                int index = Collections.binarySearch(areaTypes, at);
                if (index >= 0) {
                    at = areaTypes.remove(index);
                    periscopeUser.getAreaTypes().add(at);
                }
            }
            session.put("areaTypes", areaTypes);
        }
        return GROUP_AREA_TYPES;
    }

    public String addStoreToUser() {
        List<Store> stores = (List<Store>) session.get("stores");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (noUserStores != null && noUserStores.length > 0 && stores != null) {
            Collections.sort(stores);
            for (Integer id : noUserStores) {
                Store s = new Store();
                s.setId(id);
                int index = Collections.binarySearch(stores, s);
                if (index >= 0) {
                    s = stores.remove(index);
                    periscopeUser.getStores().add(s);
                }
            }
            session.put("stores", stores);
        }
        return GROUP_STORES;
    }

    public String addCorporateToUser() {
        List<Corporate> corporates = (List<Corporate>) session.get("corporates");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (getNoUserCorporates() != null && getNoUserCorporates().length > 0 && corporates != null) {
            Collections.sort(corporates);
            for (Integer id : getNoUserCorporates()) {
                Corporate c = new Corporate();
                c.setId(id);
                int index = Collections.binarySearch(corporates, c);
                if (index >= 0) {
                    c = corporates.remove(index);
                    periscopeUser.getCorporates().add(c);
                }
            }
            session.put("corporates", corporates);
        }
        return GROUP_CORPORATES;
    }

    public String removeGroupFromUser() {
        List<RolesGroup> rolesGroups = (List<RolesGroup>) session.get("rolesGroups");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (userGroups != null && userGroups.length > 0 && periscopeUser.getRolesGroups() != null) {
            Collections.sort(periscopeUser.getRolesGroups());
            for (Integer id : userGroups) {
                RolesGroup roleGroup = new RolesGroup();
                roleGroup.setId(id);
                int index = Collections.binarySearch(periscopeUser.getRolesGroups(), roleGroup);
                if (index >= 0) {
                    roleGroup = periscopeUser.getRolesGroups().remove(index);
                    if (rolesGroups == null) {
                        rolesGroups = new ArrayList<RolesGroup>();
                    }
                    rolesGroups.add(roleGroup);
                }
            }
            session.put("rolesGroups", rolesGroups);
        }
        return GROUP;
    }

    public String removeAreaTypeFromUser() {
        List<AreaType> areaTypes = (List<AreaType>) session.get("areaTypes");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (userAreaTypes != null && userAreaTypes.length > 0 && periscopeUser.getAreaTypes() != null) {
            Collections.sort(periscopeUser.getAreaTypes());
            for (Integer id : userAreaTypes) {
                AreaType at = new AreaType();
                at.setId(id);
                int index = Collections.binarySearch(periscopeUser.getAreaTypes(), at);
                if (index >= 0) {
                    at = periscopeUser.getAreaTypes().remove(index);
                    if (areaTypes == null) {
                        areaTypes = new ArrayList<AreaType>();
                    }
                    areaTypes.add(at);
                }
            }
            session.put("areaTypes", areaTypes);
        }
        return GROUP_AREA_TYPES;
    }

    public String removeStoreFromUser() {
        List<Store> stores = (List<Store>) session.get("stores");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (userStores != null && userStores.length > 0 && periscopeUser.getStores() != null) {
            Collections.sort(periscopeUser.getStores());
            for (Integer id : userStores) {
                Store s = new Store();
                s.setId(id);
                int index = Collections.binarySearch(periscopeUser.getStores(), s);
                if (index >= 0) {
                    s = periscopeUser.getStores().remove(index);
                    if (stores == null) {
                        stores = new ArrayList<Store>();
                    }
                    stores.add(s);
                }
            }
            session.put("stores", stores);
        }
        return GROUP_STORES;
    }

    public String removeCorporateFromUser() {
        List<Corporate> corporates = (List<Corporate>) session.get("corporates");
        periscopeUser = (PeriscopeUser) session.get("user");
        if (getUserCorporates() != null && getUserCorporates().length > 0 && periscopeUser.getCorporates() != null) {
            Collections.sort(periscopeUser.getCorporates());
            for (Integer id : getUserCorporates()) {
                Corporate c = new Corporate();
                c.setId(id);
                int index = Collections.binarySearch(periscopeUser.getCorporates(), c);
                if (index >= 0) {
                    c = periscopeUser.getCorporates().remove(index);
                    if (corporates == null) {
                        corporates = new ArrayList<Corporate>();
                    }
                    corporates.add(c);
                }
            }
            session.put("corporates", corporates);
        }
        return GROUP_CORPORATES;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Integer[] getUserAreaTypes() {
        return userAreaTypes;
    }

    public void setUserAreaTypes(Integer[] userAreaTypes) {
        this.userAreaTypes = userAreaTypes;
    }

    public Integer[] getNoUserAreaTypes() {
        return noUserAreaTypes;
    }

    public void setNoUserAreaTypes(Integer[] noUserAreaTypes) {
        this.noUserAreaTypes = noUserAreaTypes;
    }

    public Integer[] getUserStores() {
        return userStores;
    }

    public void setUserStores(Integer[] userStores) {
        this.userStores = userStores;
    }

    public Integer[] getNoUserStores() {
        return noUserStores;
    }

    public void setNoUserStores(Integer[] noUserStores) {
        this.noUserStores = noUserStores;
    }

    private void loadFilters() {
        periscopeUser = (PeriscopeUser) getSession().get("userFilter");
    }

    /**
     * @return the userCorporates
     */
    public Integer[] getUserCorporates() {
        return userCorporates;
    }

    /**
     * @param userCorporates the userCorporates to set
     */
    public void setUserCorporates(Integer[] userCorporates) {
        this.userCorporates = userCorporates;
    }

    /**
     * @return the noUserCorporates
     */
    public Integer[] getNoUserCorporates() {
        return noUserCorporates;
    }

    /**
     * @param noUserCorporates the noUserCorporates to set
     */
    public void setNoUserCorporates(Integer[] noUserCorporates) {
        this.noUserCorporates = noUserCorporates;
    }

    public SynchronizationManager getSynchronizationManager() {
        if (synchronizationManager == null) {
            synchronizationManager = SpringSupport.getInstance().findBeanByClassName(SynchronizationManager.class);
        }
        return synchronizationManager;
    }

    public void setSynchronizationManager(SynchronizationManager synchronizationManager) {
        this.synchronizationManager = synchronizationManager;
    }
}
