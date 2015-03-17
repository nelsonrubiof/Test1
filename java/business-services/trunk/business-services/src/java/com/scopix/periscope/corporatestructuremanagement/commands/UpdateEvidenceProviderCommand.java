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
 * UpdateCorporateCommand.java
 *
 * Created on 25-06-2008, 06:52:56 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderTemplate;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateEvidenceProviderCommand {

    public void execute(EvidenceProvider ep) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        UpdateEvidenceProviderTemplateCommand command = new  UpdateEvidenceProviderTemplateCommand();
        try {
            Set<Integer> areas = new HashSet<Integer>();
            for (Area a : ep.getAreas()) {
                areas.add(a.getId());
            }


            EvidenceProvider aux = dao.get(ep.getId(), EvidenceProvider.class);
            aux.setName(ep.getName());
            aux.setStore(ep.getStore());
            aux.setDescription(ep.getDescription());
            aux.getAreas().clear();
            for (Integer id : areas) {
                Area aAux = new Area();
                aAux.setId(id);
                aux.getAreas().add(aAux);
            }
            aux.setDefinitionData(ep.getDefinitionData());
            aux.setEvidenceProviderType(ep.getEvidenceProviderType());
            for (EvidenceProviderTemplate ept : ep.getEvidenceProviderTemplates()) {
                EvidenceProviderTemplate eptAux = new EvidenceProviderTemplate();
                SituationTemplate st = new SituationTemplate();
                st.setId(ept.getSituationTemplate().getId());
                eptAux.setSituationTemplate(st);
                eptAux.setTemplatePath(ept.getTemplatePath());
                eptAux.setId(ept.getId());
                ept.setEvidenceProvider(aux);
                command.execute(ept);                
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }

    }
}
