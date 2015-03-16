/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceServicesAdminServiceImpl.java
 *
 * Created on 18-06-2008, 01:59:39 AM
 *
 */
package com.scopix.periscope.admin;

import com.scopix.periscope.extractionservicesserversmanagement.EvidenceProvider;
import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author marko.perich
 */
//@CustomWebService(serviceClass = EvidenceServicesAdminService.class)
@WebService(endpointInterface = "com.scopix.periscope.admin.EvidenceServicesAdminService")
@SpringBean(rootClass = EvidenceServicesAdminService.class)
@Transactional
public class EvidenceServicesAdminServiceImpl implements EvidenceServicesAdminService {

    private static Logger log = Logger.getLogger(EvidenceServicesAdminServiceImpl.class);

    @Override
    public void resetDb() {
        try {
            HibernateSupport.getInstance().findGenericDAO().getAll(EvidenceProvider.class);
            log.debug("no resetDb");
            throw new Exception("The database is already created");
        } catch (Exception e) {
            log.debug("e = " + e.getMessage());
            if (e instanceof InvalidDataAccessResourceUsageException) {
                log.debug("doing resetDb");
                HibernateSupport.getInstance().findSchemaExportHelper().resetSchemaDB();
                HibernateSupport.getInstance().findSQLExportHelper().createSQLFunctions();
            } else {
                throw new UnexpectedRuntimeException(e);
            }
        }

//        SpringSupport.getInstance().findSchemaExportHelper().resetSchemaDB();
    }
}