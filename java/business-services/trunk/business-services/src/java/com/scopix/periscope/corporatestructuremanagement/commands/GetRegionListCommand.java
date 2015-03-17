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
 * GetRegionListCommand.java
 *
 * Created on 04-08-2008, 07:02:11 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetRegionListCommand {

    public List<Region> execute(Region region) throws ScopixException {
        CorporateStructureManagementHibernateDAOImpl dao = SpringSupport.getInstance().
                findBeanByClassName(CorporateStructureManagementHibernateDAOImpl.class);

        List<Region> regions = null;

        try {
            regions = dao.getRegionList(region);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            //PENDING message
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }

        return regions;
    }
}
