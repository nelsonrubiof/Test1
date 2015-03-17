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
 * GetAreaTypeCommand.java
 *
 * Created on 07-05-2008, 07:24:42 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.dao.SecurityManagementHibernateDAO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetCorporateListCommand {

    private static Logger log = Logger.getLogger(GetCorporateListCommand.class);

    public List<Corporate> execute(Corporate corporate) throws ScopixException {
        log.debug("start");
        SecurityManagementHibernateDAO dao = SpringSupport.getInstance().
                findBeanByClassName(SecurityManagementHibernateDAO.class);

        List<Corporate> corporates = null;

        try {
            corporates = dao.getCorporateList(corporate);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            //PENDING message
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }

        log.debug("end");
        return corporates;
    }
}
