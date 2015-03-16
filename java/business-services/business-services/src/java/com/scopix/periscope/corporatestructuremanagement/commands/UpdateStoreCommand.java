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
 * UpdateStoreCommand.java
 *
 * Created on 27-06-2008, 01:54:56 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.PeriodInterval;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateStoreCommand {

    public void execute(Store store) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            Store aux = dao.get(store.getId(), Store.class);
            aux.setAddress(store.getAddress());
            aux.setCorporate(store.getCorporate());
            aux.setDescription(store.getDescription());
            aux.setEvidenceExtractionServicesServer(store.getEvidenceExtractionServicesServer());
            aux.setEvidenceServicesServer(store.getEvidenceServicesServer());
            aux.setName(store.getName());
            aux.setLatitudeCoordenate(store.getLatitudeCoordenate());
            aux.setLongitudeCoordenate(store.getLongitudeCoordenate());
            aux.setCountry(store.getCountry());
            aux.setRegion(store.getRegion());
            aux.setTimeZoneId(store.getTimeZoneId());
            List<PeriodInterval> piAux = aux.getPeriodIntervals();
            List<PeriodInterval> piNew = store.getPeriodIntervals();
            for (PeriodInterval pi : piAux) {
                dao.remove(pi);
            }
            for (PeriodInterval pi : piNew) {
                pi.setStore(aux);
                pi.setId(null);
                dao.save(pi);
            }

        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }

    }
}
