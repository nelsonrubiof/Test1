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
import com.scopix.periscope.securitymanagement.RolesGroup;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class UpdateRolesGroupCommand {
    
    Logger log = Logger.getLogger(UpdateRolesGroupCommand.class);

    public void execute(RolesGroup rolesGroup) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            RolesGroup aux = dao.get(rolesGroup.getId(), RolesGroup.class);
            aux.setName(rolesGroup.getName());
            aux.setDescription(rolesGroup.getDescription());
            aux.setDeleted(rolesGroup.isDeleted());
            aux.setRoles(rolesGroup.getRoles());
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = "+objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.rolesGroup");
        }
        log.debug("end");
    }
}
