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
import com.scopix.periscope.securitymanagement.Role;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class GetRoleCommand {

    Logger log = Logger.getLogger(GetRoleCommand.class);

    public Role execute(int roleId) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        Role role = null;
        try {
            role = dao.get(roleId, Role.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.rol");
        }
        log.debug("end, result = " + role);
        return role;
    }
}
