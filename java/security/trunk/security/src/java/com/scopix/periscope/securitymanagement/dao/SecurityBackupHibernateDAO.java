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
 *  SecurityBackupHibernateDAO.java
 * 
 *  Created on 24-06-2011, 03:48:56 PM
 * 
 */
package com.scopix.periscope.securitymanagement.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author nelson
 */
public class SecurityBackupHibernateDAO {

    private static Logger log = Logger.getLogger(SecurityBackupHibernateDAO.class);
    private JdbcTemplate jdbcTemplateRemote;
    private JdbcTemplate jdbcTemplateLocal;

    public void setDataSource(DataSource dataSource) {
        this.setJdbcTemplateRemote(new JdbcTemplate(dataSource));
    }

    public void setDataSourceLocal(DataSource dataSource) {
        this.setJdbcTemplateLocal(new JdbcTemplate(dataSource));
    }

    public List<PeriscopeUserForQualityControlDTO> getPeriscopeUsersForAccessReports(Date start, Date end, int corporateId)
            throws ScopixException {
        log.debug("start");
        List<PeriscopeUserForQualityControlDTO> list = new ArrayList<PeriscopeUserForQualityControlDTO>();

        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        String formatDate = "yyyy-MM-dd";
        //SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select ul.login_date, pu.name, pu.user_state, pu.full_name, pu.job_position, pu.email, ");
            sb.append(" ul.application_name ");
            sb.append(" from user_login ul, periscope_user pu");
            sb.append(" where");
            sb.append(" ul.login_date between '").append(DateFormatUtils.format(start, formatDate)).append(" 00:00:00' and ");
            sb.append("'").append(DateFormatUtils.format(end, formatDate)).append(" 23:59:59'");
            sb.append(" and ul.periscope_user_id = pu.id");
            sb.append(" and ul.description = 'LOGIN'");
            sb.append(" and pu.main_corporate_id = ").append(corporateId);
            sb.append(" and pu.deleted = false");

            con = getJdbcTemplateRemote().getDataSource().getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sb.toString());
            if (rs != null) {
                PeriscopeUserForQualityControlDTO dto = new PeriscopeUserForQualityControlDTO();
                while (rs.next()) {
                    dto = new PeriscopeUserForQualityControlDTO();
                    dto.setUserName(rs.getString("name"));
                    dto.setFullName(rs.getString("full_name"));
                    dto.setJobPosition(rs.getString("job_position"));
                    dto.setEmail(rs.getString("email"));
                    dto.setUserState(rs.getString("user_state"));
                    dto.setLoginDate(rs.getTimestamp("login_date"));
                    dto.setApplicationName(rs.getString("application_name"));

                    list.add(dto);
                }
            }
            log.debug("user list length: " + list.size());
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
            throw new ScopixException("tab.qualitycontrol.summary"); //"periscopeexception.list.error", new String[]{
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.debug("end");
        return list;
    }

    public void synchronizeUser(Integer userId) {
        log.info("start");
        try {
            String sql = "";
            sql += updateCorporate(userId);
            sql += updateUser(userId);
            sql += updateStore(userId);
            sql += updateAreaType(userId);
            sql += updateUserAreaType(userId);
            sql += upadteUserRoleGroup(userId);
            sql += updateUserStore(userId);
            sql += updateUserCorporate(userId);
            getJdbcTemplateRemote().execute(sql);
        } catch (DataAccessException e) {
            log.error("No es posible actualizar user id:" + userId + " e:" + e, e);
        }
        log.info("end");

    }

    private String updateCorporate(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlCorporate = new StringBuilder("select id, name, description ");
        sqlCorporate.append(" from corporate c, rel_periscope_user_corporate puc ");
        sqlCorporate.append(" where c.id = puc.corporate_id ");
        sqlCorporate.append(" and puc.periscope_user_id  = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlCorporate.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("id");
            String name = (String) row.get("name");
            String description = (String) row.get("description");
            sql.append("select synchronize.merge_corporate(").append(id).append(",");
            sql.append(" '").append(StringEscapeUtils.escapeSql(name)).append("',");
            sql.append(" '").append(StringEscapeUtils.escapeSql(description)).append("');");
            /*getJdbcTemplateRemote().execute("select synchronize.merge_corporate(" + id + ", "
            + "'" + StringEscapeUtils.escapeSql(name) + "', '" + StringEscapeUtils.escapeSql(description) + "')");*/
        }
        return sql.toString();
    }

    private String updateUser(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlUser = new StringBuilder("select id, name, user_state, start_date, modification_date, password,");
        sqlUser.append(" deleted, email, main_corporate_id, full_name, job_position");
        sqlUser.append(" from periscope_user");
        sqlUser.append(" where id = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlUser.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("id");
            String name = (String) row.get("name");
            String userState = (String) row.get("user_state");
            Date startDate = (Date) row.get("start_date");
            Date modificationDate = (Date) row.get("modification_date");
            String password = (String) row.get("password");
            Boolean deleted = (Boolean) row.get("deleted");
            String email = (String) row.get("email");
            Integer mainCorporateId = (Integer) row.get("main_corporate_id");
            String fullName = (String) row.get("full_name");
            String jobPosition = (String) row.get("job_position");
            sql.append("select synchronize.merge_user(").append(id).append(",");
            sql.append(" '").append(StringEscapeUtils.escapeSql(name)).append("',");
            sql.append(" '").append(StringEscapeUtils.escapeSql(userState)).append("',");
            sql.append(" '").append(startDate).append("',");
            sql.append(" '").append(modificationDate).append("',");
            sql.append(" '").append(password).append("', ");
            sql.append(deleted).append(", ").append("'").append(StringEscapeUtils.escapeSql(email)).append("', ");
            sql.append(mainCorporateId).append(", '").append(StringEscapeUtils.escapeSql(fullName)).append("',");
            sql.append("'").append(StringEscapeUtils.escapeSql(jobPosition)).append("');");
//            getJdbcTemplateRemote().execute("select synchronize.merge_user(" + id + ", "
//                    + "'" + StringEscapeUtils.escapeSql(name) + "', '" + StringEscapeUtils.escapeSql(userState) + "', "
//                    + "'" + startDate + "', '" + modificationDate + "', '" + password + "', " + deleted + ", "
//                    + "'" + StringEscapeUtils.escapeSql(email) + "', " + mainCorporateId + ", "
//                    + "'" + StringEscapeUtils.escapeSql(fullName) + "', '" + StringEscapeUtils.escapeSql(jobPosition) + "')");
        }
        return sql.toString();
    }

    private String updateStore(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlStore = new StringBuilder("select id, name, description, corporate_id, corporate_store_id ");
        sqlStore.append(" from store s, rel_periscope_user_store rpus ");
        sqlStore.append(" where s.id = rpus.store_id");
        sqlStore.append(" and rpus.periscope_user_id = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlStore.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("id");
            String name = (String) row.get("name");
            String description = (String) row.get("description");
            Integer corporateId = (Integer) row.get("corporate_id");
            Integer corporateStoreId = (Integer) row.get("corporate_store_id");
            sql.append("select synchronize.merge_store(").append(id).append(", ");
            sql.append(" '").append(StringEscapeUtils.escapeSql(name)).append("',");
            sql.append(" '").append(StringEscapeUtils.escapeSql(description)).append("', ");
            sql.append(" '").append(corporateId).append("', '").append(corporateStoreId).append("');");
//            getJdbcTemplateRemote().execute("select synchronize.merge_store(" + id + ", "
//                    + "'" + StringEscapeUtils.escapeSql(name) + "', '" + StringEscapeUtils.escapeSql(description) + "', "
//                    + "'" + corporateId + "', '" + corporateStoreId + "')");
        }
        return sql.toString();
    }

    private String updateAreaType(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlAreaType = new StringBuilder("select id, name, description, corporate_id, corporate_area_type_id ");
        sqlAreaType.append(" from area_type at, rel_periscope_user_area_type rpuat");
        sqlAreaType.append(" where at.id = rpuat.area_type_id ");
        sqlAreaType.append(" and rpuat.periscope_user_id  = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlAreaType.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("id");
            String name = (String) row.get("name");
            String description = (String) row.get("description");
            Integer corporateId = (Integer) row.get("corporate_id");
            Integer corporateAreaTypeId = (Integer) row.get("corporate_area_type_id");
            sql.append("select synchronize.merge_area_type(").append(id).append(", ");
            sql.append(" '").append(StringEscapeUtils.escapeSql(name)).append("',");
            sql.append(" '").append(StringEscapeUtils.escapeSql(description)).append("', ");
            sql.append(" ").append(corporateId).append(", ").append(corporateAreaTypeId).append(");");
//            getJdbcTemplateRemote().execute("select synchronize.merge_area_type(" + id + ", "
//                    + "'" + StringEscapeUtils.escapeSql(name) + "', '" + StringEscapeUtils.escapeSql(description) + "', "
//                    + "" + corporateId + ", " + corporateAreaTypeId + ")");
        }
        return sql.toString();
    }

    private String updateUserAreaType(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlUserAreaType = new StringBuilder("select periscope_user_id, area_type_id ");
        sqlUserAreaType.append(" from rel_periscope_user_area_type ");
        sqlUserAreaType.append(" where periscope_user_id  =  ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlUserAreaType.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("periscope_user_id");
            Integer areaTypeId = (Integer) row.get("area_type_id");
            sql.append("select synchronize.merge_rel_user_area_type(").append(id).append(", ").append(areaTypeId).append(");");
//            getJdbcTemplateRemote().execute("select synchronize.merge_rel_user_area_type(" + id + ", "
//                    + areaTypeId + ")");
        }
        return sql.toString();
    }

    private String upadteUserRoleGroup(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlUserRoleGroup = new StringBuilder("select periscope_user_id, roles_group_id ");
        sqlUserRoleGroup.append(" from rel_periscope_user_roles_group ");
        sqlUserRoleGroup.append(" where periscope_user_id  = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlUserRoleGroup.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("periscope_user_id");
            Integer rolesGroupId = (Integer) row.get("roles_group_id");
            sql.append("select synchronize.merge_rel_user_role_group(").append(id).append(", ").append(rolesGroupId).append(");");
//            getJdbcTemplateRemote().execute(" select synchronize.merge_rel_user_role_group(" + id + ", "
//                    + rolesGroupId + ")");
        }
        return sql.toString();
    }

    private String updateUserStore(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlUserStore = new StringBuilder("select periscope_user_id, store_id ");
        sqlUserStore.append(" from rel_periscope_user_store ");
        sqlUserStore.append(" where periscope_user_id = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlUserStore.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("periscope_user_id");
            Integer storeId = (Integer) row.get("store_id");
            sql.append("select synchronize.merge_rel_user_store(").append(id).append(", ").append(storeId).append(");");
//            getJdbcTemplateRemote().execute("select synchronize.merge_rel_user_store(" + id + ", "
//                    + storeId + ")");
        }
        return sql.toString();
    }

    private String updateUserCorporate(Integer userId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlUserCorporate = new StringBuilder("select periscope_user_id, corporate_id ");
        sqlUserCorporate.append(" from rel_periscope_user_corporate ");
        sqlUserCorporate.append(" where periscope_user_id = ").append(userId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlUserCorporate.toString());
        for (Map<String, Object> row : result) {
            Integer periscopeUserId = (Integer) row.get("periscope_user_id");
            Integer corporateId = (Integer) row.get("corporate_id");
            sql.append("select synchronize.merge_rel_user_corporate(").append(periscopeUserId).append(", ");
            sql.append(corporateId).append(");");
//            getJdbcTemplateRemote().execute("select synchronize.merge_rel_user_corporate(" + periscopeUserId + ", "
//                    + corporateId + ")");
        }
        return sql.toString();
    }

    public JdbcTemplate getJdbcTemplateRemote() {
        return jdbcTemplateRemote;
    }

    public void setJdbcTemplateRemote(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateRemote = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplateLocal() {
        return jdbcTemplateLocal;
    }

    public void setJdbcTemplateLocal(JdbcTemplate jdbcTemplateLocal) {
        this.jdbcTemplateLocal = jdbcTemplateLocal;
    }

    public void synchronizeRoleGroup(Integer rolesGroupId) {
        log.info("start");
        try {
            String sql = "";
            sql += updateRoleGroup(rolesGroupId);
            sql += updateRoles(rolesGroupId);
            sql += updateRoleGroupRole(rolesGroupId);
            getJdbcTemplateRemote().execute(sql);
        } catch (DataAccessException e) {
            log.error("No es posible actualizar Role Group id:" + rolesGroupId + " e:" + e, e);
        }
        log.info("end");
    }

    private String updateRoleGroup(Integer rolesGroupId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlRoleGroup = new StringBuilder("select id, name, description, deleted ");
        sqlRoleGroup.append(" from roles_group ");
        sqlRoleGroup.append(" where id  = ").append(rolesGroupId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlRoleGroup.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("id");
            String name = (String) row.get("name");
            String description = (String) row.get("description");
            Boolean deleted = (Boolean) row.get("deleted");
            sql.append("select synchronize.merge_role_group(").append(id).append(",");
            sql.append(" '").append(StringEscapeUtils.escapeSql(name)).append("',");
            sql.append(" '").append(StringEscapeUtils.escapeSql(description)).append("', ").append(deleted).append(");");
//            getJdbcTemplateRemote().execute("select synchronize.merge_role_group(" + id + ","
//                    + "'" + StringEscapeUtils.escapeSql(name) + "', '" + StringEscapeUtils.escapeSql(description) + "', "
//                    + deleted + ")");
        }
        return sql.toString();
    }

    private String updateRoles(Integer rolesGroupId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlRoles = new StringBuilder("select id, role_name ");
        sqlRoles.append(" from role r, rel_roles_group_role rrgr ");
        sqlRoles.append(" where r.id = rrgr.role_id ");
        sqlRoles.append(" and rrgr.roles_group_id  = ").append(rolesGroupId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlRoles.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("id");
            String roleName = (String) row.get("role_name");
            sql.append("select synchronize.merge_role(").append(id).append(", ");
            sql.append(" '").append(StringEscapeUtils.escapeSql(roleName)).append("');");
//            getJdbcTemplateRemote().execute("select synchronize.merge_role(" + id + ", "
//                    + "'" + StringEscapeUtils.escapeSql(roleName) + "')");
        }
        return sql.toString();
    }

    private String updateRoleGroupRole(Integer rolesGroupId) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlRoleGroupRole = new StringBuilder("select roles_group_id, role_id ");
        sqlRoleGroupRole.append(" from rel_roles_group_role ");
        sqlRoleGroupRole.append(" where roles_group_id  = ").append(rolesGroupId);
        List<Map<String, Object>> result = getJdbcTemplateLocal().queryForList(sqlRoleGroupRole.toString());
        for (Map<String, Object> row : result) {
            Integer id = (Integer) row.get("roles_group_id");
            Integer roleId = (Integer) row.get("role_id");
            sql.append("select synchronize.merge_rel_role_group_role(").append(id).append(", ").append(roleId).append(");");
//            getJdbcTemplateRemote().execute("select synchronize.merge_rel_role_group_role(" + id + ", "
//                    + roleId + ")");
        }
        return sql.toString();
    }
}
