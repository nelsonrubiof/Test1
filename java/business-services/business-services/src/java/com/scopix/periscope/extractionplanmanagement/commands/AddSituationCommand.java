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

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddSituationCommand {
    private GenericDAO dao;

    public void execute(Situation situation) throws ScopixException {
        //GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        getDao().save(situation);

        if (situation.getMetrics() != null) {
            for (Metric m : situation.getMetrics()) {
                m = getDao().get(m.getId(), Metric.class);
                m.setSituation(situation);
            }
        }
    }

    public GenericDAO getDao() {
        if (dao == null){
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
