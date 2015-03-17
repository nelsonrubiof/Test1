/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  ReportingDataMapper.java
 * 
 *  Created on 30-08-2011, 10:40:57 AM
 * 
 */
package com.scopix.periscope.reporting.dao;

import com.scopix.periscope.reporting.ReportingData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.log4j.Logger;


import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author nelson
 */
public class ReportingDataMapper implements RowMapper {

    private static Logger log = Logger.getLogger(ReportingDataMapper.class);

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ReportingData rd = new ReportingData();
        rd.setId((Integer) rs.getObject("id"));
        rd.setAreaId((Integer) rs.getObject("area_id"));
        rd.setCantDo(rs.getBoolean("cant_do"));
        rd.setCantDoReason(rs.getString("cant_do_reason"));
        rd.setDepartment(rs.getString("department"));
        rd.setEvaluationUser(rs.getString("evaluation_user"));
        rd.setMetricTemplateId((Integer) rs.getObject("metric_template_id"));
        rd.setMetricVal(rs.getDouble("metric_val"));
        rd.setMetricCount((Integer) rs.getObject("metric_count"));
        rd.setObservedMetricId((Integer) rs.getObject("observed_metric_id"));
        rd.setObservedSituationId((Integer) rs.getObject("observed_situation_id"));
        rd.setProduct(rs.getString("product"));
        rd.setReject(rs.getBoolean("reject"));
        rd.setRejectedUser(rs.getString("rejected_user"));
        rd.setSentToMIS(rs.getBoolean("sent_tomis"));
        rd.setState(rs.getString("state"));
        rd.setStoreId((Integer) rs.getObject("store_id"));
        rd.setTarget(rs.getDouble("target"));
        rd.setUploadProcessId((Integer) rs.getObject("upload_process_id"));
        rd.setSituationTemplateId((Integer) rs.getObject("situation_template_id"));

        /** manejo fechas*/
        Date d1 = null;
        try {
            if (rs.getTimestamp("date_record") != null) {
                d1 = new Date(rs.getTimestamp("date_record").getTime());
                rd.setDateRecord(d1);
            }
        } catch (SQLException e) {
            log.warn("no fue posible procesar date_record:" + rs.getDate("date_record") + "[e:" + e + "]");
        }

        Date d2 = null;
        try {
            if (rs.getTimestamp("evaluation_start_date") != null) {
                d2 = new Date(rs.getTimestamp("evaluation_start_date").getTime());
                rd.setEvaluationStartDate(d2);
            }
        } catch (SQLException e) {
            log.warn("no fue posible procesar evaluation_start_date:" + rs.getDate("evaluation_start_date")
                    + "[e:" + e + "]");
        }

        Date d3 = null;
        try {
            if (rs.getTimestamp("evaluation_end_date") != null) {
                d3 = new Date(rs.getTimestamp("evaluation_end_date").getTime());
                rd.setEvaluationEndDate(d3);
            }
        } catch (SQLException e) {
            log.warn("no fue posible procesar evaluation_end_date:" + rs.getDate("evaluation_end_date")
                    + "[e:" + e + "]");
        }
        Date d4 = null;
        try {
            if (rs.getTimestamp("evidence_date") != null) {
                d4 = new Date(rs.getTimestamp("evidence_date").getTime());
                rd.setEvidenceDate(d4);
            }
        } catch (SQLException e) {
            log.warn("no fue posible procesar evidence_date:" + rs.getDate("evidence_date")
                    + "[e:" + e + "]");
        }
        Date d5 = null;
        try {
            if (rs.getTimestamp("rejected_date") != null) {
                d5 = new Date(rs.getTimestamp("rejected_date").getTime());
                rd.setRejectedDate(d5);
            }
        } catch (SQLException e) {
            log.warn("no fue posible procesar rejected_date:" + rs.getDate("rejected_date")
                    + "[e:" + e + "]");
        }
        Date d6 = null;
        try {
            if (rs.getTimestamp("sent_tomisdate") != null) {
                d6 = new Date(rs.getTimestamp("sent_tomisdate").getTime());
                rd.setSentToMISDate(d6);
            }
        } catch (SQLException e) {
            log.warn("no fue posible procesar sent_tomisdate:" + rs.getDate("sent_tomisdate")
                    + "[e:" + e + "]");
        }
        return rd;
    }
}
