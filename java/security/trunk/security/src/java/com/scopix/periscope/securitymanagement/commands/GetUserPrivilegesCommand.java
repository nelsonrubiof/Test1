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
 * GetPermitsUserCommand.java
 *
 * Created on 30-04-2008, 04:29:36 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAOImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetUserPrivilegesCommand {

    Logger log = Logger.getLogger(GetUserPrivilegesCommand.class);

    public List execute(String user, String password) {
        log.debug("start");
        UserLoginHibernateDAOImpl dao = SpringSupport.getInstance().findBeanByClassName(UserLoginHibernateDAOImpl.class);
        List<String> list = null;
        try {
            PeriscopeUser periscopeUser = dao.getUser(user, password);

            list = dao.getUserPrivileges(periscopeUser);
            if (list != null) {
                Collections.sort(list);
            } else {
                list = new ArrayList<String>();
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
        }
        log.debug("end, result = " + list);
        return list;
    }
}
