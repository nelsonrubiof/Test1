/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceExtractionLoginCommand.java
 *
 * Created on 20-01-2010, 10:20:06 AM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.dao.EvidenceExtractionUserLoginDAO;
import com.scopix.periscope.securitymanagement.dto.EvidenceExtractionUserDTO;

/**
 *
 * @author Gustavo Alvarez
 */
public class EvidenceExtractionLoginCommand {

    public EvidenceExtractionUserDTO execute(String user, String password, String filePath)  throws ScopixException {
        EvidenceExtractionUserDTO userDTO = null;
        EvidenceExtractionUserLoginDAO dao = SpringSupport.getInstance().findBeanByClassName(
                EvidenceExtractionUserLoginDAO.class);

        userDTO = dao.getUserAndPrivileges(user, password, filePath);

        return userDTO;
    }
}
