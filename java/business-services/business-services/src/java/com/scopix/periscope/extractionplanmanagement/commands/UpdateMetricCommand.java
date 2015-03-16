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
 * AddSituationCommand.java
 *
 * Created on 01-04-2008, 12:38:57 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateMetricCommand {

    public void execute(Metric metric) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        Metric aux = dao.get(metric.getId(), Metric.class);
        aux.setArea(metric.getArea());
        aux.setStore(metric.getStore());
        aux.setMetricOrder(metric.getMetricOrder());
        aux.setMetricTemplate(metric.getMetricTemplate());
        aux.setSituation(metric.getSituation());
        List<EvidenceRequest> evidenceRequests = aux.getEvidenceRequests();
        aux.setEvidenceRequests(metric.getEvidenceRequests());
        //Remover los antiguos
        if (evidenceRequests != null) {
            for (EvidenceRequest er : evidenceRequests) {
                er = dao.get(er.getId(), EvidenceRequest.class);
                er.setMetric(null);
            }
        }
        //Agregar los antiguos
        if (metric.getEvidenceRequests() != null) {
            for (EvidenceRequest er : metric.getEvidenceRequests()) {
                er = dao.get(er.getId(), EvidenceRequest.class);
                er.setMetric(aux);
            }
        }
    }
}