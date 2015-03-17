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
 *  VmsGatewayMarkEvidenceFileCommand.java
 * 
 *  Created on 02-08-2012, 04:14:29 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author nelson
 */
public class VmsGatewayMarkEvidenceFileCommand {
    private GenericDAO dao;

    public GenericDAO getDao() {
        if (dao == null){
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public void execute(Integer evidenceFileId, String fileName) {
        EvidenceFile ef = getDao().get(evidenceFileId, EvidenceFile.class);
        if (ef != null) {
            ef.setAlternativeFileName(fileName);
            ef.setDownloadedFile(Boolean.FALSE);
            getDao().save(ef);
        }
    }
    
}
