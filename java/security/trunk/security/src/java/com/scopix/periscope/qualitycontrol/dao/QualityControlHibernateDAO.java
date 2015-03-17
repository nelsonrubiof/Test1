/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QualityControlHibernateDAO.java
 *
 * Created on 09-07-2010, 05:25:58 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.qualitycontrol.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = QualityControlHibernateDAO.class)
public class QualityControlHibernateDAO extends DAOHibernate<PeriscopeUser, Integer> {

    private static Logger log = Logger.getLogger(QualityControlHibernateDAO.class);

    public List<PeriscopeUserForQualityControlDTO> getPeriscopeUsersForAccessReports(Date start, Date end, int corporateId)
            throws ScopixException {
        log.debug("start");
        List<PeriscopeUserForQualityControlDTO> list = new ArrayList<PeriscopeUserForQualityControlDTO>();

        Session session = null;
        PreparedStatement st = null;
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
            session = this.getSession();
            con = session.connection();
            st = con.prepareStatement(sb.toString());
            rs = st.executeQuery();
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
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.debug("end");
        return list;
    }
}
