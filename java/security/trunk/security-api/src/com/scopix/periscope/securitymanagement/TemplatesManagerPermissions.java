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
public class TemplatesManagerPermissions {

    public static final String ADD_SITUATION_TEMPLATE_PERMISSION = "ADD_SITUATION_TEMPLATE_PERMISSION";
    public static final String UPDATE_SITUATION_TEMPLATE_PERMISSION = "UPDATE_SITUATION_TEMPLATE_PERMISSION";
    public static final String REMOVE_SITUATION_TEMPLATE_PERMISSION = "REMOVE_SITUATION_TEMPLATE_PERMISSION";
    public static final String LIST_SITUATION_TEMPLATE_PERMISSION = "LIST_SITUATION_TEMPLATE_PERMISSION";
    public static final String GET_SITUATION_TEMPLATE_PERMISSION = "GET_SITUATION_TEMPLATE_PERMISSION";
    public static final String ADD_METRIC_TEMPLATE_PERMISSION = "ADD_METRIC_TEMPLATE_PERMISSION";
    public static final String UPDATE_METRIC_TEMPLATE_PERMISSION = "UPDATE_METRIC_TEMPLATE_PERMISSION";
    public static final String REMOVE_METRIC_TEMPLATE_PERMISSION = "REMOVE_METRIC_TEMPLATE_PERMISSION";
    public static final String LIST_METRIC_TEMPLATE_PERMISSION = "LIST_METRIC_TEMPLATE_PERMISSION";
    public static final String GET_METRIC_TEMPLATE_PERMISSION = "GET_METRIC_TEMPLATE_PERMISSION";
    public static final String ADD_PRODUCT_PERMISSION = "ADD_PRODUCT_PERMISSION";
    public static final String UPDATE_PRODUCT_PERMISSION = "UPDATE_PRODUCT_PERMISSION";
    public static final String GET_PRODUCT_PERMISSION = "GET_PRODUCT_PERMISSION";
    public static final String LIST_PRODUCT_PERMISSION = "LIST_PRODUCT_PERMISSION";
    public static final String REMOVE_PRODUCT_PERMISSION = "REMOVE_PRODUCT_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_METRIC_TEMPLATE_PERMISSION);
        permissions.add(GET_SITUATION_TEMPLATE_PERMISSION);
        permissions.add(ADD_METRIC_TEMPLATE_PERMISSION);
        permissions.add(ADD_SITUATION_TEMPLATE_PERMISSION);
        permissions.add(UPDATE_METRIC_TEMPLATE_PERMISSION);
        permissions.add(UPDATE_SITUATION_TEMPLATE_PERMISSION);
        permissions.add(REMOVE_METRIC_TEMPLATE_PERMISSION);
        permissions.add(REMOVE_SITUATION_TEMPLATE_PERMISSION);
        permissions.add(LIST_METRIC_TEMPLATE_PERMISSION);
        permissions.add(LIST_SITUATION_TEMPLATE_PERMISSION);
        permissions.add(ADD_PRODUCT_PERMISSION);
        permissions.add(UPDATE_PRODUCT_PERMISSION);
        permissions.add(GET_PRODUCT_PERMISSION);
        permissions.add(LIST_PRODUCT_PERMISSION);
        permissions.add(REMOVE_PRODUCT_PERMISSION);
        return permissions;
    }
}
