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
 *  CreateExtractionPlanCustomizingCommand.java
 * 
 *  Created on 23-09-2010, 03:24:50 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.Date;

/**
 *
 * @author nelson
 */
public class CreateExtractionPlanCustomizingCommand {

    private GenericDAO dao;

    /**
     * Alamacena un Extraction Plan Customizing para la tupla Store-SituationTemplate
     *
     * @param st SituationTemplate del nuevo EPC
     * @param s Store del nuevo EPC
     * @param user usuario que realiza la transaccion
     * @return Nuevo Epc
     */
    public ExtractionPlanCustomizing execute(SituationTemplate st, Store s, String user) {
        //antes de continuar levantamos los datos
        s = getDao().get(s.getId(), Store.class);
        st = getDao().get(st.getId(), SituationTemplate.class);
        //creamos el nuevo epc
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setSituationTemplate(st);
        epc.setStore(s);
        epc.setAreaType(st.getAreaType());
        epc.setCreationDate(new Date());
        epc.setCreationUser(user);
        getDao().save(epc);
        return epc;

    }

    /**
     * Recuperar el Dao Generico para Almacenamiento
     *
     * @return GenericDAO
     */
    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    /**
     * Se setea un Dao de Forma Extrarna
     *
     * @param dao GenericDAO para efectos de Testing
     */
    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
