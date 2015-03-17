/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  SaveMetricCommand.java
 * 
 *  Created on 11-07-2014, 03:59:27 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.vadaro.VadaroEvent;

/**
 * 
 * @author EmO
 */
public class SaveVadaroEventCommand {

    private static final Logger log = Logger.getLogger(SaveVadaroEventCommand.class);

    private GenericDAO genericDAO;

	public void execute(VadaroEvent ev) throws ScopixException {
		log.debug("Almacenando VadaroEvent");
		getGenericDAO().save(ev);
        
    }

    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
