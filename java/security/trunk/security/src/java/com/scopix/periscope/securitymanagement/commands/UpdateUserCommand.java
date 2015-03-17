/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetUserListCommand.java
 *
 * Created on 16-06-2008, 01:35:24 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class UpdateUserCommand {

    private GenericDAO dao;
    private static Logger log = Logger.getLogger(UpdateUserCommand.class);

    public void execute(PeriscopeUser periscopeUser) throws ScopixException {
        log.debug("start");

        try {
            PeriscopeUser aux = getDao().get(periscopeUser.getId(), PeriscopeUser.class);
            aux.setName(periscopeUser.getName());
            aux.setPassword(periscopeUser.getPassword());
            aux.setUserState(periscopeUser.getUserState());
            aux.setEmail(periscopeUser.getEmail());
            aux.setModificationDate(new Date());
            aux.setDeleted(periscopeUser.isDeleted());
            aux.setRolesGroups(periscopeUser.getRolesGroups());
            aux.setAreaTypes(periscopeUser.getAreaTypes());
            aux.setStores(periscopeUser.getStores());
            aux.setCorporates(periscopeUser.getCorporates());
            aux.setFullName(periscopeUser.getFullName());
            aux.setJobPosition(periscopeUser.getJobPosition());
            aux.setMainCorporate(periscopeUser.getMainCorporate());
            //se almacena el usuario antes de salir para realizar el cambio en base de datos
            getDao().save(aux);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.user");
        }
        log.debug("start");
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
