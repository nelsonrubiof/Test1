/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * UpdateSituationCommand.java
 *
 * Created on 02-07-2008, 04:44:28 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateSituationCommand {

    public void execute(Situation situation) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        //PENDING ERROR MESSAGES
        try {
            Situation situationTemp = dao.get(situation.getId(), Situation.class);
            situationTemp.setSituationTemplate(situation.getSituationTemplate());
            List<Metric> metrics = situationTemp.getMetrics();
            situationTemp.setMetrics(situation.getMetrics());

            if (metrics != null) {
                for (Metric m : metrics) {
                    m = dao.get(m.getId(), Metric.class);
                    m.setSituation(null);
                }
            }

            if (situation.getMetrics() != null) {
                for (Metric m : situation.getMetrics()) {
                    m = dao.get(m.getId(), Metric.class);
                    m.setSituation(situationTemp);
                }
            }

        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("PENDING", objectRetrievalFailureException);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new ScopixException("PENDING", dataIntegrityViolationException);
        }
    }
}
