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

import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateCorporateCommand {

    public void execute(Corporate corporate) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        Corporate corporateTemp = null;

        try {
            corporateTemp = dao.get(corporate.getId(), Corporate.class);

            corporateTemp.setName(corporate.getName());
            corporateTemp.setDescription(corporate.getDescription());
            corporateTemp.setUniqueCorporateId(corporate.getUniqueCorporateId());
            corporateTemp.setLogo(corporate.getLogo());

            dao.save(corporateTemp);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }

    }
}
