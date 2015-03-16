/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  SaveEvidenceTransmissionStrategyCommand.java
 * 
 *  Created on Jul 31, 2014, 12:04:36 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvidenceTransmitionStrategy;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Sebastian
 */
public class SaveEvidenceTransmissionStrategyCommand {

    private GenericDAO genericDAO;

    /**
     * Saves an EvidenceTransmitionStrategy
     * @param etsd
     */
    public void execute(EvidenceTransmitionStrategy etsd) {     
        getGenericDAO().save(etsd);
    }

    /**
     * @return the genericDAO
     */
    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    /**
     * @param genericDAO the genericDAO to set
     */
    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
