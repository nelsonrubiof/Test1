/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evidencemanagement.ProcessEES;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceExtractionServicesServerDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class GetProcessIdByExtractionPlanProcessEESCommand {

    private static Logger log = Logger.getLogger(GetProcessIdByExtractionPlanProcessEESCommand.class);
    private EvidenceExtractionServicesServerDAO dao;

    /**
     * @return the dao
     */
    public EvidenceExtractionServicesServerDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionServicesServerDAO.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvidenceExtractionServicesServerDAO dao) {
        this.dao = dao;
    }

    /**
     * recupera el processId generado en ES para la tupla ExtractionPlan processId de EES
     */
    public ProcessEES execute(ExtractionPlan extractionPlan, Integer eesProcessId) throws ScopixException {
        log.info("start");
        ProcessEES processEES = null;
        try {
            log.debug("extractionPlan.getId() " + extractionPlan.getId() + " eesProcessId:" + eesProcessId);
            processEES = getDao().getProcessEES(extractionPlan.getId(), eesProcessId);
            if (processEES == null) {
                //debemos crear un nuevo process
                Integer newProcessId = getDao().getNextProcessIdAutoGenerado();
                processEES = new ProcessEES();
                processEES.setExtractionPlan(extractionPlan);
                processEES.setProcessIdEes(eesProcessId);
                processEES.setProcessIdLocal(newProcessId);
                HibernateSupport.getInstance().findGenericDAO().save(processEES);
            }
        } catch (Exception e) {
            throw new ScopixException(e);
        }
        log.info("end " + processEES);
        return processEES;
    }
}
