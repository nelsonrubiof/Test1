/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.periscopefoundation.util.config;

import com.scopix.periscope.frameworksfoundation.hibernate.SQLExportHelper;
import com.scopix.periscope.frameworksfoundation.hibernate.SchemaExportHelper;
import com.scopix.periscope.periscopefoundation.persistence.DAO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.persistence.exceptions.DaoInstanceNotFoundException;
import org.hibernate.SessionFactory;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class HibernateSupport {

    /**
     * Singleton instance
     */
    private static HibernateSupport instance;

    /**
     *
     * @return una instancia de la clase SpringSupport
     */
    public static HibernateSupport getInstance() {
        if (instance == null) {
            instance = new HibernateSupport();
            instance.init();
        }
        return instance;
    }

    private void init() {
    }

    /**
     * Find specified {@link DAO} on application context
     *
     * @param <T> Retorno esperado
     * @param daoClazz class a buscar
     * @return Instancia de Class que estiende de DAO
     * @throws DaoInstanceNotFoundException en caso de Excepcion
     */
    public <T extends DAO> T findDao(Class<T> daoClazz) throws DaoInstanceNotFoundException {

        T dao = SpringSupport.getInstance().findBeanByClassName(daoClazz);
        if (dao == null) {
            throw new DaoInstanceNotFoundException(daoClazz.toString());
        }
        return dao;
    }

    /**
     * Find {@link GenericDAO} on application context
     *
     * @return GenericDAO dao generico definido en arhcivo de configuracion
     */
    public GenericDAO findGenericDAO() {
        return (GenericDAO) SpringSupport.getInstance().findBean("genericDAO");
    }

    /**
     * Find {@link SchemaExportHelper} on application context
     *
     * @return SchemaExportHelper definido en arhcivo de configuracion
     */
    public SchemaExportHelper findSchemaExportHelper() {
        SchemaExportHelper schemaExportHelper = (SchemaExportHelper) SpringSupport.getInstance().findBean("schemaExportHelper");
        return schemaExportHelper;
    }

    /**
     * Find {@link SQLExportHelper} on application context
     *
     * @return SQLExportHelper definido en arhcivo de configuracion
     */
    public SQLExportHelper findSQLExportHelper() {
        SQLExportHelper sqlExportHelper = (SQLExportHelper) SpringSupport.getInstance().findBean("sqlExportHelper");
        return sqlExportHelper;
    }

    /**
     * Find {@link SessionFactory} on application context
     *
     * @return SessionFactory definido en arhcivo de configuracion
     */
    public SessionFactory findSessionFactory() {
        return (SessionFactory) SpringSupport.getInstance().getBeanFactory().getBean("sessionFactory");
    }
}
