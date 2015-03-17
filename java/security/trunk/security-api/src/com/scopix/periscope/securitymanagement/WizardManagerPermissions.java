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
public class WizardManagerPermissions {

    public static final String GET_WIZARD_CUSTOMIZING_LIST_PERMISSION = "GET_WIZARD_CUSTOMIZING_LIST_PERMISSION";
    public static final String ADD_WIZARD_CUSTOMIZING_PERMISSION = "ADD_WIZARD_CUSTOMIZING_PERMISSION";
    public static final String INACTIVATE_WIZARD_CUSTOMIZING_PERMISSION = "INACTIVATE_WIZARD_CUSTOMIZING_PERMISSION";
    public static final String UPDATE_WIZARD_CUSTOMIZING_PERMISSION = "UPDATE_WIZARD_CUSTOMIZING_PERMISSION";
    public static final String UPDATE_ORDER_PERMISSION = "UPDATE_ORDER_PERMISSION";
    public static final String MOVE_UP_PERMISSION = "MOVE_UP_PERMISSION";
    public static final String MOVE_DOWN_PERMISSION = "MOVE_DOWN_PERMISSION";
    public static final String GENERATE_RECORDS_PERMISSION = "GENERATE_RECORDS_PERMISSION";
    public static final String GET_FORMULA_LIST_PERMISSION = "GET_FORMULA_LIST_PERMISSION";
    public static final String GET_FORMULA_PERMISSION = "GET_FORMULA_PERMISSION";
    public static final String ADD_FORMULA_PERMISSION = "ADD_FORMULA_PERMISSION";
    public static final String EDIT_FORMULA_PERMISSION = "EDIT_FORMULA_PERMISSION";
    public static final String TEST_FORMULA_PERMISSION = "TEST_FORMULA_PERMISSION";
    public static final String DELETE_FORMULA_PERMISSION = "DELETE_FORMULA_PERMISSION";
    public static final String REPROCESS_DATA_PERMISSION = "REPROCESS_DATA_PERMISSION";


    /**
     * se agregan para el manejo de EPC Flexible NR 20100924
     */
    public static final String UPDATE_EXTRACTION_PLAN_METRIC_PERMISSION = "UPDATE_EXTRACTION_PLAN_METRIC_PERMISSION";
    public static final String CREATE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION = "CREATE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION";
    public static final String GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION = "GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION";
    public static final String GET_EXTRACTION_PLAN_RANGES_LIST_PERMISSION = "GET_EXTRACTION_PLAN_RANGES_LIST_PERMISSION";
    public static final String GET_EXTRACTION_PLAN_RANGES_DETAIL_LIST_PERMISSION =
            "GET_EXTRACTION_PLAN_RANGES_DETAIL_LIST_PERMISSION";
    public static final String UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION = "UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION";
    public static final String COPY_EXTRACTION_PLAN_RANGE_PERMISSION = "COPY_EXTRACTION_PLAN_RANGE_PERMISSION";
    public static final String DELETE_EXTRACTION_PLAN_RANGE_PERMISSION = "DELETE_EXTRACTION_PLAN_RANGE_PERMISSION";
    public static final String GET_EXTRACTION_PLAN_RANGE_PERMISSION = "GET_EXTRACTION_PLAN_RANGE_PERMISSION";
    public static final String COPY_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION = "COPY_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION";
    public static final String DISABLE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION = "DISABLE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION";




    public static List<String> getPermissionsList() {

        List<String> permissions = new ArrayList<String>();
        permissions.add(GET_WIZARD_CUSTOMIZING_LIST_PERMISSION);
        permissions.add(ADD_WIZARD_CUSTOMIZING_PERMISSION);
        permissions.add(INACTIVATE_WIZARD_CUSTOMIZING_PERMISSION);
        permissions.add(UPDATE_WIZARD_CUSTOMIZING_PERMISSION);
        permissions.add(UPDATE_ORDER_PERMISSION);
        permissions.add(MOVE_UP_PERMISSION);
        permissions.add(MOVE_DOWN_PERMISSION);
        permissions.add(GENERATE_RECORDS_PERMISSION);
        permissions.add(GET_FORMULA_LIST_PERMISSION);
        permissions.add(GET_FORMULA_PERMISSION);
        permissions.add(ADD_FORMULA_PERMISSION);
        permissions.add(EDIT_FORMULA_PERMISSION);
        permissions.add(TEST_FORMULA_PERMISSION);
        permissions.add(DELETE_FORMULA_PERMISSION);
        permissions.add(REPROCESS_DATA_PERMISSION);
        permissions.add(UPDATE_EXTRACTION_PLAN_METRIC_PERMISSION);
        permissions.add(CREATE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        permissions.add(GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        permissions.add(GET_EXTRACTION_PLAN_RANGES_LIST_PERMISSION);
        permissions.add(GET_EXTRACTION_PLAN_RANGES_DETAIL_LIST_PERMISSION);
        permissions.add(UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION);
        permissions.add(COPY_EXTRACTION_PLAN_RANGE_PERMISSION);
        permissions.add(DELETE_EXTRACTION_PLAN_RANGE_PERMISSION);
        permissions.add(GET_EXTRACTION_PLAN_RANGE_PERMISSION);
        permissions.add(COPY_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);



        return permissions;
    }
}
