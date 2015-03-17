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
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAO;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAOImpl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class AddUserCommand {

    private GenericDAO genericDAO;
    private UserLoginHibernateDAO userLoginHibernateDAO;
    private static Logger log = Logger.getLogger(AddUserCommand.class);

    public void execute(PeriscopeUser periscopeUser) throws ScopixException {
        log.debug("start");

        validateUserNotExists(periscopeUser);

        periscopeUser.setId(null);
        periscopeUser.setStartDate(new Date());
        getGenericDAO().save(periscopeUser);
        log.debug("end");
    }

    private void validateUserNotExists(PeriscopeUser periscopeUser) throws ScopixException {

        PeriscopeUser pu = new PeriscopeUser();
        pu.setName(periscopeUser.getName());
        if (getUserLoginHibernateDAO().getUserByName(periscopeUser.getName()) != null) {
            throw new ScopixException("USER_EXISTS");
        }

    }

    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public UserLoginHibernateDAO getUserLoginHibernateDAO() {
        if (userLoginHibernateDAO == null) {
            userLoginHibernateDAO = SpringSupport.getInstance().findBeanByClassName(UserLoginHibernateDAOImpl.class);
        }
        return userLoginHibernateDAO;
    }

    public void setUserLoginHibernateDAO(UserLoginHibernateDAO userLoginHibernateDAO) {
        this.userLoginHibernateDAO = userLoginHibernateDAO;
    }
}
