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
 * UpdateRelationEvidenceProviderLocationCommand.java
 *
 * Created on 26-05-2009, 06:52:56 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.RelationEvidenceProviderLocation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Cesar Abarza Suazo
 */
public class UpdateRelationEvidenceProviderLocationCommand {
    private static Logger log = Logger.getLogger(UpdateRelationEvidenceProviderLocationCommand.class);
    public void execute(Integer evidenceProviderId, List<RelationEvidenceProviderLocation> evidenceProviderLocations) throws
            ScopixException {
        log.info("start " + evidenceProviderId);
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            EvidenceProvider ep = dao.get(evidenceProviderId, EvidenceProvider.class);
            Integer areaId = null;
            if (!evidenceProviderLocations.isEmpty()) {
                areaId = evidenceProviderLocations.get(0).getArea().getId();
            }
            
            Set<Integer> areasEp = new HashSet<Integer>();
            for (Area a: ep.getAreas()) {
                areasEp.add(a.getId());
            }
            if (ep.getRelationEvidenceProviderLocationsFrom() != null && !ep.getRelationEvidenceProviderLocationsFrom().
                    isEmpty()) {
                for (RelationEvidenceProviderLocation epl : ep.getRelationEvidenceProviderLocationsFrom()) {
                    RelationEvidenceProviderLocation evidenceProviderLocation = null;
                    for (RelationEvidenceProviderLocation repl : evidenceProviderLocations) {
                        areaId = repl.getArea().getId();
                        if (repl.getLocation().equals(epl.getLocation())
                                && repl.getArea().getId().equals(epl.getArea().getId())) {
                            evidenceProviderLocation = epl;
                            EvidenceProvider epTo = new EvidenceProvider();
                            epTo.setId(repl.getEvidenceProviderTo().getId());
                            evidenceProviderLocation.setEvidenceProviderTo(epTo);
                            evidenceProviderLocation.setDefaultEvidenceProvider(repl.getDefaultEvidenceProvider());
                            evidenceProviderLocation.setViewOrder(repl.getViewOrder());
                            evidenceProviderLocations.remove(repl);
                            break;
                        }
                    }
                    if (evidenceProviderLocation == null && epl.getArea().getId().equals(areaId)) {
                        log.debug("remove " + epl.getId());
                        dao.remove(epl);
                    } else if (evidenceProviderLocation != null) {
                        log.debug("save " + evidenceProviderLocation.getLocation().getName());
                        dao.save(evidenceProviderLocation);
                    }
                }
            }
            if (evidenceProviderLocations != null && !evidenceProviderLocations.isEmpty() && areasEp.contains(areaId)) {
                log.debug("save evidenceProviderLocations " + evidenceProviderLocations.size());
                dao.save(evidenceProviderLocations);
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }

    }
}
