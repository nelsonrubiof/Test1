/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceExtractionAdminServiceImpl.java
 * 
 * Created on 19-05-2008, 04:17:54 PM
 */
package com.scopix.periscope.admin;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionplanmanagement.EServerDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author marko.perich
 */
// @CustomWebService(serviceClass = EvidenceExtractionAdminService.class)
@WebService(endpointInterface = "com.scopix.periscope.admin.EvidenceExtractionAdminService")
@SpringBean(rootClass = EvidenceExtractionAdminService.class)
@Transactional
public class EvidenceExtractionAdminServiceImpl implements EvidenceExtractionAdminService {

    Logger log = Logger.getLogger(EvidenceExtractionAdminServiceImpl.class);

    private ExtractionManager extractionManager;

    @Override
    public void resetDb() {
        // Validate no tables created
        try {
            HibernateSupport.getInstance().findGenericDAO().getAll(EvidenceFile.class);
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

    }

    @Override
    public void resetListeners() {
        log.debug("Start");

        // recuperamos todos los store y los inicializamos en caso de llegar nuevos
        EServerDAO dao = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);

        // inicializamos los store
        List<String> storeNames = dao.getStoreNamesAvailables();

        for (String storeName : storeNames) {
            try {
                getExtractionManager().startMotionDetection(storeName);
            } catch (ScopixException e) {
                log.debug("Unable to start motion detection for: " + storeName);
            }
        }
        log.debug("End");
    }

    public ExtractionManager getExtractionManager() {
        if (extractionManager == null) {
            extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        }
        return extractionManager;
    }
}
