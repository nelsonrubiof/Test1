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
 * AddStoreCommand.java
 *
 * Created on 07-05-2008, 02:53:32 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.PeriodInterval;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddStoreCommand {

    public void execute(Store store) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(store);
//        if (store.getEvidenceExtractionServicesServer() != null && store.getEvidenceExtractionServicesServer().
//                getId() != null && store.getEvidenceExtractionServicesServer().
//                getId() > 0) {
//            EvidenceExtractionServicesServer eess = dao.get(store.getEvidenceExtractionServicesServer().
//                    getId(), EvidenceExtractionServicesServer.class);
//            //ahora es add
//            //eess.setStore(store);
//            eess.addStore(store);
//        }

        for (PeriodInterval pi : store.getPeriodIntervals()) {
            pi.setStore(store);
            pi.setId(null);
            dao.save(pi);
        }
    }
}
