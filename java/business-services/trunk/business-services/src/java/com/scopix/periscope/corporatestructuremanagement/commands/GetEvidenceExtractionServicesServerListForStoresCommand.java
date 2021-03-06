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
 * GetEvidenceExtractionServicesServerListCommand.java
 *
 * Created on 11-03-2013, 04:10:36 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetEvidenceExtractionServicesServerListForStoresCommand { 
    public List<EvidenceExtractionServicesServerDTO> execute(List<String> stores) {
        CorporateStructureManagementHibernateDAO dao = SpringSupport.getInstance().
                findBeanByClassName(CorporateStructureManagementHibernateDAOImpl.class);

        List<EvidenceExtractionServicesServerDTO> list = dao.getEvidenceExtractionServicesServerListForStores(stores);

        return list;
    }
}
