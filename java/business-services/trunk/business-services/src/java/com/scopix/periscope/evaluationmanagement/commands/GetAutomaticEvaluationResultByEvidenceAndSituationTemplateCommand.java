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
 * GetAutomaticEvaluationResultByEvidenceCommand.java
 *
 * Created on 08-05-2008, 03:21:19 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.AutomaticEvaluationResult;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;

/**
 *
 * @author Emo
 */
public class GetAutomaticEvaluationResultByEvidenceAndSituationTemplateCommand {

    private static Logger log = Logger.getLogger(GetAutomaticEvaluationResultByEvidenceAndSituationTemplateCommand.class);
    private EvaluationHibernateDAO evaluationHibernateDAO;

	public AutomaticEvaluationResult execute(Evidence evidence, SituationTemplate situationTemplate) throws ScopixException {
        log.info("start");

		AutomaticEvaluationResult result = null;
		result = getEvaluationHibernateDAO().getAutomaticEvaluationResultByEvidenceIdAndSituationTemplateId(evidence.getId(),
				situationTemplate.getId());
        log.info("end");
        return result;
    }

    public EvaluationHibernateDAO getEvaluationHibernateDAO() {
        if (evaluationHibernateDAO == null) {
            evaluationHibernateDAO = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return evaluationHibernateDAO;
    }

}
