/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  ActivityLogDAOImpl.java
 * 

 * 
 */
package com.scopix.periscope.activitylog.dao;

import com.scopix.periscope.activitylog.ActivityLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
@Repository
@SpringBean(rootClass = ActivityLogDAO.class)
public class ActivityLogDAOImpl implements ActivityLogDAO {

    private static final String GET = "select * from activity_log where id=?";
    private static final String EXIST = "select count(*) from activity_log where id=?";
    private static final String GET_ALL = "select * from activity_log";
    private static final String DELETE = "delete from activity_log where id=?";
    private static final String SAVE = "insert into activity_log (user_name, status, pending_evaluation_id, corporate_name, queue_name, request_date, send_date) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "update activity_log set  user_name=?, status=?, pending_evaluation_id=? , corporate_name=?, queue_name=?, request_date=?, send_date=? where id=?";
    private static final String GET_FOR_USER_AND_RANGE = "select * from activity_log where user_name = ? and request_date >= ? and request_date <= ? order by request_date desc limit 1000";
    private static final String GET_FOR_RANGE = "select * from activity_log where request_date >= ? and request_date <= ? order by request_date desc limit 1000";
    private static final String GET_NAMES_FOR_RANGE = "select DISTINCT user_name from activity_log where request_date >= ? and request_date <= ?";
    private static final String GET_USER_NAMES_IN_LOG = "select DISTINCT user_name from activity_log";
    private static final String UPDATE_ENTRY = "update activity_log set status=?, send_date=? where user_name=? and pending_evaluation_id=?";
    //private static final String USERS_PER_QUEUE = "select  corporate_name, queue_name, count(distinct user_name) from activity_log where  request_date >= ? and request_date =< ? group by corporate_name, queue_name";
    private static final String USERS_PER_QUEUE = "  select  distinct (user_name) as user_name, corporate_name, queue_name from activity_log where  request_date >= ?"
            + " and request_date < ? order by corporate_name asc, queue_name asc, user_name asc";
    private static final String USERS_LATEST_REQUEST_PER_QUEUE = "select MAX (request_date), user_name, corporate_name, queue_name "
            + " from activity_log where request_date >= ? and request_date <= ? group by user_name, corporate_name, queue_name";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean exists(Integer activityLogId) {
        int total = jdbcTemplate.queryForInt(EXIST, new Object[]{activityLogId});
        return total > 0;
    }

    @Override
    public ActivityLog get(Integer activityLogId) {
        ActivityLog activityLog = jdbcTemplate.queryForObject(GET, new Object[]{activityLogId}, new ActivityLogRowMapper());
        return activityLog;
    }

    @Override
    public List<ActivityLog> getAll() {
        return jdbcTemplate.query(GET_ALL, new ActivityLogRowMapper());
    }

    @Override
    public void remove(ActivityLog activityLog) {
        jdbcTemplate.update(GET_FOR_USER_AND_RANGE, new Object[]{activityLog.getId()});
    }

    @Override
    public void remove(Integer activityLogId) {
        jdbcTemplate.update(DELETE, new Object[]{activityLogId});
    }

    @Override
    public List<ActivityLog> getForUserAndDateRange(String userName, Date from, Date to) {
        java.sql.Timestamp jInitialDate = new java.sql.Timestamp(from.getTime());
        java.sql.Timestamp jFinalDate = new java.sql.Timestamp(to.getTime());
        return jdbcTemplate.query(GET_FOR_USER_AND_RANGE, new Object[]{userName, jInitialDate, jFinalDate}, new ActivityLogRowMapper());
    }

    @Override
    public Integer save(ActivityLog activityLog) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String userName = activityLog.getUserName();
        final String status = activityLog.getStatus();
        final Integer pendingEvaluationId = activityLog.getPendingEvaluationId();
        final String corporateName = activityLog.getCorporateName();
        final String queueName = activityLog.getQueueName();
        final Timestamp requestDate = new Timestamp(activityLog.getRequestDate().getTime());
        Timestamp sendDatenf = null;
        if (activityLog.getSendDate() != null) {
            sendDatenf = new Timestamp(activityLog.getSendDate().getTime());
        }
        final Timestamp sendDate = sendDatenf;
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
                PreparedStatement ps = cnctn.prepareStatement(SAVE, new String[]{"id"});
                ps.setString(1, userName);
                ps.setString(2, status);
                ps.setInt(3, pendingEvaluationId);
                ps.setString(4, corporateName);
                ps.setString(5, queueName);
                ps.setTimestamp(6, requestDate);
                ps.setTimestamp(7, sendDate);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Integer> save(List<ActivityLog> list) {

        List<Integer> ids = new ArrayList<Integer>();
        for (ActivityLog activityLog : list) {
            ids.add(save(activityLog));
        }
        return ids;
    }

    @Override
    public void update(ActivityLog activityLog) {
        this.jdbcTemplate.update(UPDATE,
                new Object[]{activityLog.getUserName(), activityLog.getStatus(), activityLog.getCorporateName(), activityLog.getQueueName(), new Timestamp(activityLog.getRequestDate().getTime()), new Timestamp(activityLog.getSendDate().getTime()), activityLog.getId()});
    }

    @Override
    public void update(List<ActivityLog> list) {
        for (ActivityLog activityLog : list) {
            update(activityLog);
        }
    }

    @Override
    public List<String> getUserNamesInLog() {
        return jdbcTemplate.queryForList(GET_USER_NAMES_IN_LOG, String.class);
    }

    @Override
    public List<ActivityLog> getForDateRange(Date from, Date to) {
        java.sql.Timestamp jInitialDate = new java.sql.Timestamp(from.getTime());
        java.sql.Timestamp jFinalDate = new java.sql.Timestamp(to.getTime());
        return jdbcTemplate.query(GET_FOR_RANGE, new Object[]{jInitialDate, jFinalDate}, new ActivityLogRowMapper());
    }

    @Override
    public List<String> getUsersNameForDateRange(Date from, Date to) {
        java.sql.Timestamp jInitialDate = new java.sql.Timestamp(from.getTime());
        java.sql.Timestamp jFinalDate = new java.sql.Timestamp(to.getTime());
        return jdbcTemplate.queryForList(GET_NAMES_FOR_RANGE, new Object[]{jInitialDate, jFinalDate}, String.class);
    }

    @Override
    public void UpdateActivityLogEntry(String userName, Integer pendingEvaluationId, String status, Date sendDate) {
        java.sql.Timestamp jInitialDate = new java.sql.Timestamp(sendDate.getTime());
        this.jdbcTemplate.update(UPDATE_ENTRY,
                new Object[]{status, jInitialDate, userName, pendingEvaluationId});
    }

    @Override
    public List<Map<String, Object>> getUsersPerQueue(Date from, Date to) {
        java.sql.Timestamp jInitialDate = new java.sql.Timestamp(from.getTime());
        java.sql.Timestamp jFinalDate = new java.sql.Timestamp(to.getTime());
        return this.jdbcTemplate.queryForList(USERS_PER_QUEUE, new Object[]{jInitialDate, jFinalDate});
    }

    @Override
    public List<Map<String, Object>> getUsersLatestRequestQueue(Date from, Date to) {
        java.sql.Timestamp jInitialDate = new java.sql.Timestamp(from.getTime());
        java.sql.Timestamp jFinalDate = new java.sql.Timestamp(to.getTime());
        return this.jdbcTemplate.queryForList(USERS_LATEST_REQUEST_PER_QUEUE, new Object[]{jInitialDate, jFinalDate});
    }

    private class ActivityLogRowMapper implements RowMapper<ActivityLog> {

        @Override
        public ActivityLog mapRow(ResultSet rs, int i) throws SQLException {
            ActivityLog activityLog = new ActivityLog();
            activityLog.setId(rs.getInt("ID"));
            activityLog.setStatus(rs.getString("STATUS"));
            activityLog.setPendingEvaluationId(rs.getInt("PENDING_EVALUATION_ID"));
            activityLog.setUserName(rs.getString("USER_NAME"));
            activityLog.setCorporateName(rs.getString("CORPORATE_NAME"));
            activityLog.setQueueName(rs.getString("QUEUE_NAME"));
            Date requestDate = rs.getTimestamp("REQUEST_DATE");
            Date sendDate = rs.getTimestamp("SEND_DATE");
            activityLog.setRequestDate(requestDate);
            activityLog.setSendDate(sendDate);
            return activityLog;

        }
    }
}