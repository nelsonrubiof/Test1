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
 *  GetAreaFromAreaTypeStoreCommand.java
 * 
 *  Created on 27-06-2013, 12:50:35 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class GetAreaByAreaTypeStoreCommand {
    
    private static Logger log = Logger.getLogger(GetAreaByAreaTypeStoreCommand.class);
    private EvaluationHibernateDAO evaluationHibernateDAO; 

    /**
     * @return the evaluationHibernateDAO
     */
    public EvaluationHibernateDAO getEvaluationHibernateDAO() {
        if (evaluationHibernateDAO == null) {
            evaluationHibernateDAO = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return evaluationHibernateDAO;
    }

    /**
     * @param evaluationHibernateDAO the evaluationHibernateDAO to set
     */
    public void setEvaluationHibernateDAO(EvaluationHibernateDAO evaluationHibernateDAO) {
        this.evaluationHibernateDAO = evaluationHibernateDAO;
    }
    
    public Area execute(Integer areaTypeId, Integer storeId) {
        log.info("start [areaTypeId:" + areaTypeId + "][storeId:" + storeId + "]");
        
        Area area = getEvaluationHibernateDAO().getAreaByreaTypeStore(areaTypeId, storeId);
        log.info("end area:" + area);
        return area;
    }
}
