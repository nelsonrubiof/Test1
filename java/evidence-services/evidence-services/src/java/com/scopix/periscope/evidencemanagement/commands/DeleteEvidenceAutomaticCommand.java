/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.extractionservicesserversmanagement.dao.ExtractionPlanDetailDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class DeleteEvidenceAutomaticCommand {

    private static Logger log = Logger.getLogger(DeleteEvidenceAutomaticCommand.class);
    private ExtractionPlanDetailDAO dao;

    /**
     * @return the dao
     */
    public ExtractionPlanDetailDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanDetailDAO.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(ExtractionPlanDetailDAO dao) {
        this.dao = dao;
    }

    public void execute(Evidence evidence) throws ScopixException {
        log.info("start");
        getDao().deleteAutomaticEvidence(evidence.getId(), evidence.getExtractionPlanDetail().getId());
        log.info("end");
    }
}
