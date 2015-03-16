/*
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
 *
 */
package com.scopix.periscope.admin.services.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.peoplefilter.request.GhostbusterRequestProcessor;
import com.scopix.periscope.admin.services.AdminService;
import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * Service implementation. see {@link AdminService}
 *
 * @author maximiliano.vazquez
 * @version 2.0.0
 */
@WebService(endpointInterface = "com.scopix.periscope.admin.services.AdminService")
@SpringBean(rootClass = AdminService.class)
@Transactional
public class AdminServiceImpl implements AdminService {

    private Logger log = Logger.getLogger(AdminServiceImpl.class);

    /**
     * Clean database
     */
    public void resetDb() {
        //Validate no tables created
        try {
            HibernateSupport.getInstance().findGenericDAO().getAll(Corporate.class);
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
	public void reloadGhostbusterConfig() {
		log.debug("Start");
		GhostbusterRequestProcessor processor = SpringSupport.getInstance()
				.findBeanByClassName(GhostbusterRequestProcessor.class);
		
		processor.reloadConfig();
		log.debug("End");
	}
}
