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
public class QualityControlManagerPermissions {

    public static final String GET_STORES_FOR_QC_MANAGER_PERMISSION = "GET_STORES_FOR_QC_MANAGER_PERMISSION";
    public static final String GET_EVIDENCE_FINISHED_PERMISSION = "GET_EVIDENCE_FINISHED_PERMISSION";
    public static final String GET_EVIDENCE_FINISHED_LIST_PERMISSION = "GET_EVIDENCE_FINISHED_LIST_PERMISSION";
    public static final String REJECT_EVALUATION_PERMISSION = "REJECT_EVALUATION_PERMISSION";
    public static final String ACCESS_REPORT_PERMISSION = "ACCESS_REPORT_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_STORES_FOR_QC_MANAGER_PERMISSION);
        permissions.add(GET_EVIDENCE_FINISHED_PERMISSION);
        permissions.add(GET_EVIDENCE_FINISHED_LIST_PERMISSION);
        permissions.add(REJECT_EVALUATION_PERMISSION);
        permissions.add(ACCESS_REPORT_PERMISSION);
        return permissions;
    }
}
