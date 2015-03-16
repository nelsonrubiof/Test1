/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.commands;

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
public class GetUrlsBeeCommand {

    private static final Logger log = Logger.getLogger(GetUrlsBeeCommand.class);
    private EvaluationHibernateDAO dao;

    /**
     * @return the dao
     */
    public EvaluationHibernateDAO getDao() {
        if(dao == null){
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

    public List<String> execute(Date date) {
        List<String> ret = getDao().getUrlsBee(date);
        
        
        return ret;
    }
    
}
