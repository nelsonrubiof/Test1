/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Cesar Abarza
 */
public class DeleteFormulaCommand {

    public void execute(Integer id) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.remove(id, Formula.class);
    }
}
