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
 * GetObservedSituationEvaluationDTOsCommand.java
 *
 * Created on 08-05-2008, 02:22:31 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAO;
import com.scopix.periscope.extractionplanmanagement.dto.ObservedSituationEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cesar Abarza
 */
public class GetObservedSituationEvaluationDTOsCommand {

    public List<ObservedSituationEvaluationDTO> execute(Date startDate, Date endDate, List<Integer> situationTemplateIds,
            List<Integer> storeIds) throws ScopixException {
        ObservedSituationHibernateDAO dao = SpringSupport.getInstance().
                findBeanByClassName(ObservedSituationHibernateDAOImpl.class);
        List<ObservedSituationEvaluationDTO> dtos = dao.getObservedSituationEvaluationDTOs(startDate, endDate,
                situationTemplateIds, storeIds);
        return dtos;
    }
}
