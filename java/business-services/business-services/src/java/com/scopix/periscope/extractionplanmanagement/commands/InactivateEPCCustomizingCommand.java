/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;

/**
 *
 * @author Cesar
 */
public class InactivateEPCCustomizingCommand {

    private GenericDAO dao;

    public void execute(Integer id) {

        ExtractionPlanCustomizing wizardCustomizing = getDao().get(id, ExtractionPlanCustomizing.class);
        
        wizardCustomizing.setActive(false);
        //asigamos la fecha de deshablitacion 
        wizardCustomizing.setDisabledDate(new Date());
    }

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
