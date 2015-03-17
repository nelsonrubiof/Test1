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
 *  GetEvidenceExtractionRequestByPriorizationCommand.java
 * 
 *  Created on 27-03-2012, 12:59:18 PM
 * 
 */

package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAO;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author nelson
 */
public class GetEvidenceExtractionRequestByPriorizationCommand {
    private EvidenceExtractionDAO dao;

    public EvidenceExtractionDAO getDao() {
        if (dao ==null) {
            dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionDAOImpl.class);
        }
        return dao;
    }

    public void setDao(EvidenceExtractionDAO dao) {
        this.dao = dao;
    }

    public List<String> execute(Vector<String> fileNames) { //se elimina , String storeName
//        List<String> fileNamePriorizados = getDao().getEvidenceExtractionRequestByPriorization(fileNames, storeName);
        List<String> fileNamePriorizados = getDao().getEvidenceExtractionRequestByPriorization(fileNames);
        return fileNamePriorizados;
    }
}
