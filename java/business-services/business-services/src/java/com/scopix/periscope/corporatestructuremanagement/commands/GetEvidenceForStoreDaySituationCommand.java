/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.StorePlan;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class GetEvidenceForStoreDaySituationCommand {

    private static final Logger log = Logger.getLogger(GetEvidenceForStoreDaySituationCommand.class);
    private EvaluationHibernateDAO dao;

    /**
     * @return the dao
     */
    public EvaluationHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvaluationHibernateDAO dao) {
        this.dao = dao;
    }

    public List<StorePlan> execute(Date date) {
        log.info("start " + date);
        List<StorePlan> ret = getDao().getEvidenceForStoreDaySituation(date);
        log.info("end " + ret.size());
        return ret;
    }
}
