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
 * LoginCommand.java
 *
 * Created on 29-04-2008, 09:17:37 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.SecurityExceptionType;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.UserLogin;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAO;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAOImpl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class LoginCommand {

    private UserLoginHibernateDAO dao;
    private static Logger log = Logger.getLogger(LoginCommand.class);

    public Long execute(String user, String password, Integer corporateId, String application) throws ScopixException {
        log.info("start [user:" + user + "][password:" + password + "][corporateId:" + corporateId + "]"
                + "[application:" + application + "]");

        PeriscopeUser periscopeUser = getDao().getUser(user, password);
        if (periscopeUser == null) {
            throw new ScopixException(SecurityExceptionType.USER_NOT_FOUND.name());
        }

        Corporate corporate = null;
        //verify the user is allowed to login into the corporate
        if (corporateId != null) {
            Boolean canLoginInCorporate = false;
            if (corporateId == 0) {
                canLoginInCorporate = true;
            } else {
                for (Corporate corp : periscopeUser.getCorporates()) {
                    if (corp.getId().equals(corporateId)) {
                        corporate = corp;
                        canLoginInCorporate = true;
                    }
                }
                if (!canLoginInCorporate) {
                    throw new ScopixException(SecurityExceptionType.ACCESS_DENIED.name());
                }
            }
        }

        //login register
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName(user);
        userLogin.setDescription("LOGIN");
        userLogin.setLoginDate(new Date());
        userLogin.setPeriscopeUser(periscopeUser);
        userLogin.setCorporate(corporate);
        userLogin.setApplicationName(application);
        getDao().save(userLogin);

        log.info("end");
        return (userLogin.getId());
    }

    public UserLoginHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(UserLoginHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(UserLoginHibernateDAO dao) {
        this.dao = dao;
    }
}
