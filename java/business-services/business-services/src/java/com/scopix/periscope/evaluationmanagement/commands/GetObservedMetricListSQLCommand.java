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
 * GetObservedMetricListCommand.java
 *
 * Created on 10-06-2008, 10:37:52 AM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.dao.ObservedMetricHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class GetObservedMetricListSQLCommand {

    public List<ObservedMetric> execute(ObservedMetric observedMetric) throws ScopixException {
        ObservedMetricHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(ObservedMetricHibernateDAO.class);

        List<ObservedMetric> observedMetrics = null;

        try {

            observedMetrics = dao.getObservedMetricListSQL(observedMetric);

        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.evidenceManagement.observedMetric.elementNotFound",
                    objectRetrievalFailureException);
        }

        return observedMetrics;
    }
}
