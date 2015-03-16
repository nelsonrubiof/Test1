/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetSituationCommand.java
 *
 * Created on 01-04-2008, 01:16:10 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetMetricListCommand {
    private ExtractionPlanCustomizingDAO dao;
    private static Logger log = Logger.getLogger(GetMetricListCommand.class);

    public List<Metric> execute(Metric metric) throws ScopixException {
        log.info("start");
        List<Metric> metrics = getDao().getMetricList(metric);
        log.info("end");
        return metrics;
    }

    public ExtractionPlanCustomizingDAO getDao() {
        if(dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanCustomizingDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ExtractionPlanCustomizingDAO dao) {
        this.dao = dao;
    }
}
