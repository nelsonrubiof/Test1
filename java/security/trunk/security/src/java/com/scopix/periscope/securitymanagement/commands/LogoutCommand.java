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
 * LogoutCommand.java
 *
 * Created on 29-04-2008, 09:17:44 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.UserLogin;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAOImpl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class LogoutCommand {

    Logger log = Logger.getLogger(LogoutCommand.class);

    public void execute(Long sessionId) {
        log.debug("start");
        UserLoginHibernateDAOImpl dao = SpringSupport.getInstance().findBeanByClassName(UserLoginHibernateDAOImpl.class);
        UserLogin userLogin = dao.get(sessionId);
        userLogin.setLogoutDate(new Date());
        dao.save(userLogin);
        log.debug("end");
    }
}
