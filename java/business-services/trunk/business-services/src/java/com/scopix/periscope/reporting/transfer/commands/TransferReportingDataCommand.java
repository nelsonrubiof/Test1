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
 *  TransferReportingDataCommand.java
 * 
 *  Created on 01-02-2011, 03:20:55 PM
 * 
 */
package com.scopix.periscope.reporting.transfer.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import com.scopix.periscope.reporting.transfer.dao.ReportingUploadDao;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nelson
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransferReportingDataCommand {

    private static Logger log = Logger.getLogger(TransferReportingDataCommand.class);
    private ReportingUploadDao dao;
    private GenericDAO genericDao;
    private ReportingHibernateDAO reportingDAO;
    private boolean cancelProcess;
    public static final String TYPE_UPLOAD_ALL = "ALL";
    public static final String TYPE_UPLOAD_NEW = "NEW";
    public static final String TYPE_UPLOAD_OLD = "OLD";

    public ReportingUploadDao getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingUploadDao.class);
        }
        return dao;
    }

    public void setDao(ReportingUploadDao dao) {
        this.dao = dao;
    }

    public void execute(List<ReportingData> reportingDatas, UploadProcessDetail detail, List<MetricTemplate> metricTemplates)
            throws ScopixException {
        if (reportingDatas != null) {
            getDao().procesaDetailNew(reportingDatas, detail, metricTemplates);
            //terminar transaccion

            //terminamos guardando nuevamente el detalle para que quede actualizado
            getGenericDao().save(detail);
        }
    }

    public GenericDAO getGenericDao() {
        if (genericDao == null) {
            genericDao = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDao;
    }

    public void setGenericDao(GenericDAO genericDao) {
        this.genericDao = genericDao;
    }

    public ReportingHibernateDAO getReportingDAO() {
        if (reportingDAO == null) {
            reportingDAO = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return reportingDAO;
    }

    public void setReportingDAO(ReportingHibernateDAO reportingDAO) {
        this.reportingDAO = reportingDAO;
    }

    public boolean isCancelProcess() {
        return cancelProcess;
    }

    public void setCancelProcess(boolean value) {
        this.cancelProcess = value;
        getDao().setCancelProcess(value);
    }
}
