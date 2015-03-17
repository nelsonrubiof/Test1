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
 * CheckSecurityCommand.java
 *
 * Created on 12-05-2008, 01:28:21 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.securitymanagement.CheckSecurity;
import java.util.Date;

/**
 *
 * @author Gustavo Alvarez
 */
public class CheckSecurityCommand {

    public void execute(CheckSecurity checkSecurity) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        checkSecurity.setPrivilegeAccessTime(new Date());
        
        dao.save(checkSecurity);
    }
}
