/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.securitymanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marko.perich
 */
public class SecurityManagerPermissions {

    public static final String APP_MANAGER_GUI_AUDIT_BUTTON = "APP_MANAGER_GUI_AUDIT_BUTTON";
    public static final String GET_USER_LIST_PERMISSION = "GET_USER_LIST_PERMISSION";
    public static final String GET_USER_PERMISSION = "GET_USER_PERMISSION";
    public static final String ADD_USER_PERMISSION = "ADD_USER_PERMISSION";
    public static final String UPDATE_USER_PERMISSION = "UPDATE_USER_PERMISSION";
    public static final String FIND_SPECIFIC_USER_BY_NAME_PERMISSION = "FIND_SPECIFIC_USER_BY_NAME_PERMISSION";
    public static final String DELETE_USER_PERMISSION = "DELETE_USER_PERMISSION";
    public static final String GET_ROLES_GROUP_LIST_PERMISSION = "GET_ROLES_GROUP_LIST_PERMISSION";
    public static final String GET_ROLES_GROUP_PERMISSION = "GET_ROLES_GROUP_PERMISSION";
    public static final String FIND_SPECIFIC_ROLES_GROUP_BY_NAME_PERMISSION = "FIND_SPECIFIC_ROLES_GROUP_BY_NAME_PERMISSION";
    public static final String ADD_ROLES_GROUP_PERMISSION = "ADD_ROLES_GROUP_PERMISSION";
    public static final String UPDATE_ROLES_GROUP_PERMISSION = "UPDATE_ROLES_GROUP_PERMISSION";
    public static final String DELETE_ROLES_GROUP_PERMISSION = "DELETE_ROLES_GROUP_PERMISSION";
    public static final String GET_ROLES_LIST_PERMISSION = "GET_ROLES_LIST_PERMISSION";
    public static final String GET_ROLE_PERMISSION = "GET_ROLE_PERMISSION";
    public static final String CHANGE_PASSWORD_PERMISSION = "CHANGE_PASSWORD_PERMISSION";
	public static final String GET_STORES_FOR_USER_PERMISSION = "GET_STORES_FOR_USER_PERMISSION"; // GET_STORE_IDS_FOR_USER_PERMISSION
    public static final String GET_AREA_TYPES_FOR_USER_PERMISSION = "GET_AREA_TYPES_FOR_USER_PERMISSION";
    public static final String GET_LIST_AREATYPE_PERMISSION = "GET_LIST_AREATYPE_PERMISSION";
    public static final String GET_LIST_CORPORATE_PERMISSION = "GET_LIST_CORPORATE_PERMISSION";
    public static final String GET_LIST_STORE_PERMISSION = "GET_LIST_STORE_PERMISSION";
    public static final String GET_STORES_FOR_CORPORATE_PERMISSION = "GET_STORES_FOR_CORPORATE_PERMISSION";
    public static final String GET_AREA_TYPES_FOR_CORPORATE_PERMISSION = "GET_AREA_TYPES_FOR_CORPORATE_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_USER_LIST_PERMISSION);
        permissions.add(GET_USER_PERMISSION);
        permissions.add(ADD_USER_PERMISSION);
        permissions.add(UPDATE_USER_PERMISSION);
        permissions.add(FIND_SPECIFIC_USER_BY_NAME_PERMISSION);
        permissions.add(DELETE_USER_PERMISSION);
        permissions.add(GET_ROLES_GROUP_LIST_PERMISSION);
        permissions.add(GET_ROLES_GROUP_PERMISSION);
        permissions.add(FIND_SPECIFIC_ROLES_GROUP_BY_NAME_PERMISSION);
        permissions.add(ADD_ROLES_GROUP_PERMISSION);
        permissions.add(UPDATE_ROLES_GROUP_PERMISSION);
        permissions.add(DELETE_ROLES_GROUP_PERMISSION);
        permissions.add(GET_ROLES_LIST_PERMISSION);
        permissions.add(GET_ROLE_PERMISSION);
        permissions.add(APP_MANAGER_GUI_AUDIT_BUTTON);
        permissions.add(CHANGE_PASSWORD_PERMISSION);
        permissions.add(GET_STORES_FOR_USER_PERMISSION);
        permissions.add(GET_AREA_TYPES_FOR_USER_PERMISSION);
        permissions.add(GET_LIST_AREATYPE_PERMISSION);
        permissions.add(GET_LIST_CORPORATE_PERMISSION);
        permissions.add(GET_LIST_STORE_PERMISSION);
        permissions.add(GET_STORES_FOR_CORPORATE_PERMISSION);
        permissions.add(GET_AREA_TYPES_FOR_CORPORATE_PERMISSION);

        return permissions;
    }
}
