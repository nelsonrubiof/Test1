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

import com.scopix.periscope.frameworksfoundation.hibernate.config.HibernateConfigurationHolder;
import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * This class manage database schema with help of {@link SchemaExport}.
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
public class SchemaExportHelper {

    private SchemaExportHelper() {
    }

    /**
     *
     * @return DataSource para el sistema
     */
    public DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * This method deletes all tables and regenerates them. It produces total lost of information.
     */
    public void resetSchemaDB() {

        Connection connection = null;
        try {

            connection = DataSourceUtils.getConnection(this.dataSource);

            AnnotationConfiguration conf = HibernateConfigurationHolder.conf;
            SchemaExport export = new SchemaExport(conf, connection);
            export.execute(false, true, false, false);

        } catch (HibernateException e) {
            throw new UnexpectedRuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new UnexpectedRuntimeException(e);
            }

        }
    }

    /**
     *
     * @param datasource inejccion de DataSource al sistema
     */
    public void setDataSource(DataSource datasource) {
        this.dataSource = datasource;
    }
    private DataSource dataSource;
}
