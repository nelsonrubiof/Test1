/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.hibernate.jdbc.Work;

/**
 *
 * @author nelson
 */
public class UpdateWork implements Work {

    private static Logger log = Logger.getLogger(UpdateWork.class);
    private String sql;
    private Integer rowsAffected;

    @Override
    public void execute(Connection connection) throws SQLException {
        log.info("start");
        Statement ps = null;
        try {
            ps = connection.createStatement();
            rowsAffected = ps.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Error ejecutando sql " + sql + " " + e, e);
            throw (e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    log.warn("No se pudo Cerrar Statement");
                }
            }
        }
        log.info("end");
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the rowsAffected
     */
    public Integer getRowsAffected() {
        return rowsAffected;
    }
}
