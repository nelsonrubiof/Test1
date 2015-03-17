/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.hibernate.jdbc.Work;

/**
 *
 * @author nelson
 */
public class SelectWork implements Work {

    private static Logger log = Logger.getLogger(SelectWork.class);
    private ResultSet resultSet;
    private String sql;

    @Override
    public void execute(Connection connection) throws SQLException {
        log.info("start");
        Statement ps = null;
        ps = connection.createStatement();
        resultSet = ps.executeQuery(sql);
        log.info("end");
    }

    /**
     * @return the rs
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
}
