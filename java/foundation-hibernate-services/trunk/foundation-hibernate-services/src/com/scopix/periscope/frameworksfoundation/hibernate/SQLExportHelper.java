/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */
package com.scopix.periscope.frameworksfoundation.hibernate;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * This class manage the postgresql functions export.
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
public class SQLExportHelper {

    private static Logger log = Logger.getLogger(SQLExportHelper.class);
    private static List<String> sqlFiles;
    private DataSource dataSource;

    /**
     * funcion de inicializacion para Creacion de Sql
     */
    public void init() {
        sqlFiles = getSQLFiles("sql/");
    }

    /**
     *
     * @param path ruta de archivos
     * @return lista de directiorios
     */
    public List<String> getSQLFiles(String path) {
        log.debug("start, path = " + path);
        List<String> list = new ArrayList<String>();
        try {
            ClassPathResource cpr = new ClassPathResource(path);
            File f = cpr.getFile();
            if (f.isDirectory()) {
                log.debug("directory");
                String[] files = f.list();
                for (String s : files) {
                    log.debug("file = " + s);
                    if (s.endsWith(".sql")) {
                        list.add(path + s);
                    } else {
                        list.addAll(getSQLFiles(path + s + "/"));
                    }
                }
            }
        } catch (Exception e) {
            log.warn("error = " + e.getMessage());
        }
        Collections.sort(list);
        log.debug("end. " + list);
        return list;
    }

    /**
     * This method deletes all tables and regenerates them. It produces total lost of information.
     */
    public void createSQLFunctions() {
        Connection connection = null;
        Statement stmt = null;
        try {
            if (sqlFiles != null) {
                connection = this.dataSource.getConnection();
                for (String file : sqlFiles) {
                    log.debug(file);
                    ClassPathResource cpr = new ClassPathResource(file);
                    Scanner scanner = new Scanner(cpr.getFile());
                    String function = "";
                    while (scanner.hasNextLine()) {
                        function += scanner.nextLine() + "\n";
                    }
                    stmt = connection.createStatement();
                    stmt.execute(function);
                }
            }
        } catch (Exception e) {
            throw new UnexpectedRuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                throw new UnexpectedRuntimeException(e);
            }

        }
    }

    /**
     *
     * @param datasource set para DataSource del sistema
     */
    public void setDataSource(DataSource datasource) {
        this.dataSource = datasource;
    }

    /**
     *
     * @return DataSource de sistema
     */
    public DataSource getDataSource() {
        return this.dataSource;
    }
}