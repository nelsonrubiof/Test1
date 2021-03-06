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
public class EvaluationManagerPermissions {

    public static final String ACCEPT_NEW_EVIDENCE_PERMISSION = "ACCEPT_NEW_EVIDENCE_PERMISSION";
    public static final String ACCEPT_EVIDENCE_EVALUATION_PERMISSION = "ACCEPT_EVIDENCE_EVALUATION_PERMISSION";
    public static final String GET_OBSERVED_METRIC_FOR_A_EVIDENCE_REQUEST_PERMISSION =
            "GET_OBSERVED_METRIC_FOR_A_EVIDENCE_REQUEST_PERMISSION";
    public static final String GET_OBSERVED_SITUATION_FOR_A_METRIC_PERMISSION = "GET_OBSERVED_SITUATION_FOR_A_METRIC_PERMISSION";
    public static final String ENQUEUE_FOR_EVALUATION_PERMISSION = "ENQUEUE_FOR_EVALUATION_PERMISSION";
    public static final String ENQUEUE_FOR_EVALUATION_LIST_PERMISSION = "ENQUEUE_FOR_EVALUATION_LIST_PERMISSION";
    public static final String GET_NEXT_PENDING_EVALUATION_PERMISSION = "GET_NEXT_PENDING_EVALUATION_PERMISSION";
    public static final String PROCESS_EVALUATION_PERMISSION = "PROCESS_EVALUATION_PERMISSION";
    public static final String RELOAD_QUEUE_PERMISSION = "RELOAD_QUEUE_PERMISSION";
    public static final String ADD_BUSINESS_RULE_CHECK_PERMISSION = "ADD_BUSINESS_RULE_CHECK_PERMISSION";
    public static final String ADD_COMPLIANCE_CONDITION_CHECK_PERMISSION = "ADD_COMPLIANCE_CONDITION_CHECK_PERMISSION";
    public static final String ADD_COMPLIANCE_CONDITION_EVALUATION_PERMISSION = "ADD_COMPLIANCE_CONDITION_EVALUATION_PERMISSION";
    public static final String ADD_OBSERVED_SITUATION_PERMISSION = "ADD_OBSERVED_SITUATION_PERMISSION";
    public static final String ADD_OBSERVED_SITUATION_EVALUATION_PERMISSION = "ADD_OBSERVED_SITUATION_EVALUATION_PERMISSION";
    public static final String ADD_PENDING_EVALUATION_PERMISSION = "ADD_PENDING_EVALUATION_PERMISSION";
    public static final String ADD_OBSERVED_METRIC_PERMISSION = "ADD_OBSERVED_METRIC_PERMISSION";
    public static final String ADD_METRIC_EVALUATION_PERMISSION = "ADD_METRIC_EVALUATION_PERMISSION";
    public static final String ADD_EVIDENCE_PERMISSION = "ADD_EVIDENCE_PERMISSION";
    public static final String ADD_EVIDENCE_EVALUATION_PERMISSION = "ADD_EVIDENCE_EVALUATION_PERMISSION";
    public static final String GET_BUSINESS_RULE_CHECK_PERMISSION = "GET_BUSINESS_RULE_CHECK_PERMISSION";
    public static final String GET_COMPLIANCE_CONDITION_CHECK_PERMISSION = "GET_COMPLIANCE_CONDITION_CHECK_PERMISSION";
    public static final String GET_COMPLIANCE_CONDITION_EVALUATION_PERMISSION = "GET_COMPLIANCE_CONDITION_EVALUATION_PERMISSION";
    public static final String GET_OBSERVED_SITUATION_PERMISSION = "GET_OBSERVED_SITUATION_PERMISSION";
    public static final String GET_OBSERVED_SITUATION_EVALUATION_PERMISSION = "GET_OBSERVED_SITUATION_EVALUATION_PERMISSION";
    public static final String GET_OBSERVED_METRIC_PERMISSION = "GET_OBSERVED_METRIC_PERMISSION";
    public static final String GET_METRIC_EVALUATION_PERMISSION = "GET_METRIC_EVALUATION_PERMISSION";
    public static final String GET_EVIDENCE_PERMISSION = "GET_EVIDENCE_PERMISSION";
    public static final String GET_EVIDENCE_EVALUATION_PERMISSION = "GET_EVIDENCE_EVALUATION_PERMISSION";
    public static final String REMOVE_BUSINESS_RULE_CHECK_PERMISSION = "REMOVE_BUSINESS_RULE_CHECK_PERMISSION";
    public static final String REMOVE_COMPLIANCE_CONDITION_CHECK_PERMISSION = "REMOVE_COMPLIANCE_CONDITION_CHECK_PERMISSION";
    public static final String REMOVE_COMPLIANCE_CONDITION_EVALUATION_PERMISSION =
            "REMOVE_COMPLIANCE_CONDITION_EVALUATION_PERMISSION";
    public static final String REMOVE_OBSERVED_SITUATION_PERMISSION = "REMOVE_OBSERVED_SITUATION_PERMISSION";
    public static final String REMOVE_OBSERVED_SITUATION_EVALUATION_PERMISSION =
            "REMOVE_OBSERVED_SITUATION_EVALUATION_PERMISSION";
    public static final String REMOVE_OBSERVED_METRIC_PERMISSION = "REMOVE_OBSERVED_METRIC_PERMISSION";
    public static final String REMOVE_METRIC_EVALUATION_PERMISSION = "REMOVE_METRIC_EVALUATION_PERMISSION";
    public static final String REMOVE_EVIDENCE_PERMISSION = "REMOVE_EVIDENCE_PERMISSION";
    public static final String REMOVE_EVIDENCE_EVALUATION_PERMISSION = "REMOVE_EVIDENCE_EVALUATION_PERMISSION";
    public static final String GET_PENDING_EVALUATION_LIST_FOR_EVALUATION_MANAGER_PERMISSION =
            "GET_PENDING_EVALUATION_LIST_FOR_EVALUATION_MANAGER_PERMISSION";
    public static final String GET_PENDING_EVALUATION_PERMISSION = "GET_PENDING_EVALUATION_PERMISSION";
    public static final String GET_NEXT_EVIDENCE_FOR_OPERATOR_PERMISSION = "GET_NEXT_EVIDENCE_FOR_OPERATOR_PERMISSION";
    public static final String UPDATE_PENDING_EVALUATION_PERMISSION = "UPDATE_PENDING_EVALUATION_PERMISSION";
    public static final String UPDATE_OBSERVED_METRIC_PERMISSION = "UPDATE_OBSERVED_METRIC_PERMISSION";
    public static final String ADD_PROOF_PERMISSION = "ADD_PROOF_PERMISSION";
    public static final String ADD_MARQUIS_PERMISSION = "ADD_MARQUIS_PERMISSION";
    public static final String GET_OBSERVED_METRIC_LIST_PERMISSION = "GET_OBSERVED_METRIC_LIST_PERMISSION";
    public static final String GET_EVIDENCE_AND_PROOF_PERMISSION = "GET_EVIDENCE_AND_PROOF_PERMISSION";

    public static List<String> getPermissionsList() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(ACCEPT_NEW_EVIDENCE_PERMISSION);
        permissions.add(ACCEPT_EVIDENCE_EVALUATION_PERMISSION);
        permissions.add(GET_OBSERVED_METRIC_FOR_A_EVIDENCE_REQUEST_PERMISSION);
        permissions.add(GET_OBSERVED_SITUATION_FOR_A_METRIC_PERMISSION);
        permissions.add(ENQUEUE_FOR_EVALUATION_PERMISSION);
        permissions.add(ENQUEUE_FOR_EVALUATION_LIST_PERMISSION);
        permissions.add(GET_NEXT_PENDING_EVALUATION_PERMISSION);
        permissions.add(PROCESS_EVALUATION_PERMISSION);
        permissions.add(RELOAD_QUEUE_PERMISSION);
        permissions.add(ADD_BUSINESS_RULE_CHECK_PERMISSION);
        permissions.add(ADD_COMPLIANCE_CONDITION_CHECK_PERMISSION);
        permissions.add(ADD_COMPLIANCE_CONDITION_EVALUATION_PERMISSION);
        permissions.add(ADD_OBSERVED_SITUATION_PERMISSION);
        permissions.add(ADD_OBSERVED_SITUATION_EVALUATION_PERMISSION);
        permissions.add(ADD_PENDING_EVALUATION_PERMISSION);
        permissions.add(ADD_OBSERVED_METRIC_PERMISSION);
        permissions.add(ADD_METRIC_EVALUATION_PERMISSION);
        permissions.add(ADD_EVIDENCE_PERMISSION);
        permissions.add(ADD_EVIDENCE_EVALUATION_PERMISSION);
        permissions.add(GET_BUSINESS_RULE_CHECK_PERMISSION);
        permissions.add(GET_COMPLIANCE_CONDITION_CHECK_PERMISSION);
        permissions.add(GET_COMPLIANCE_CONDITION_EVALUATION_PERMISSION);
        permissions.add(GET_OBSERVED_SITUATION_PERMISSION);
        permissions.add(GET_OBSERVED_SITUATION_EVALUATION_PERMISSION);
        permissions.add(GET_OBSERVED_METRIC_PERMISSION);
        permissions.add(GET_METRIC_EVALUATION_PERMISSION);
        permissions.add(GET_EVIDENCE_PERMISSION);
        permissions.add(GET_EVIDENCE_EVALUATION_PERMISSION);
        permissions.add(REMOVE_BUSINESS_RULE_CHECK_PERMISSION);
        permissions.add(REMOVE_COMPLIANCE_CONDITION_CHECK_PERMISSION);
        permissions.add(REMOVE_COMPLIANCE_CONDITION_EVALUATION_PERMISSION);
        permissions.add(REMOVE_OBSERVED_SITUATION_PERMISSION);
        permissions.add(REMOVE_OBSERVED_SITUATION_EVALUATION_PERMISSION);
        permissions.add(REMOVE_OBSERVED_METRIC_PERMISSION);
        permissions.add(REMOVE_METRIC_EVALUATION_PERMISSION);
        permissions.add(REMOVE_EVIDENCE_PERMISSION);
        permissions.add(REMOVE_EVIDENCE_EVALUATION_PERMISSION);
        permissions.add(GET_PENDING_EVALUATION_LIST_FOR_EVALUATION_MANAGER_PERMISSION);
        permissions.add(GET_PENDING_EVALUATION_PERMISSION);
        permissions.add(GET_NEXT_EVIDENCE_FOR_OPERATOR_PERMISSION);
        permissions.add(UPDATE_PENDING_EVALUATION_PERMISSION);
        permissions.add(UPDATE_OBSERVED_METRIC_PERMISSION);
        permissions.add(ADD_PROOF_PERMISSION);
        permissions.add(ADD_MARQUIS_PERMISSION);
        permissions.add(GET_OBSERVED_METRIC_LIST_PERMISSION);
        permissions.add(GET_EVIDENCE_AND_PROOF_PERMISSION);
        return permissions;
    }
}
