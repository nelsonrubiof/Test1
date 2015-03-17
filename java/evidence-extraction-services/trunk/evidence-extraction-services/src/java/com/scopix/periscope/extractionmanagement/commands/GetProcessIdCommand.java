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
 * GetProcessIdCommand.java
 *
 * Created on 03-04-2009, 03:34:20 PM
 *
 */


package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetProcessIdCommand {

    public Integer execute() throws ScopixException {
        EvidenceExtractionDAOImpl dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionDAOImpl.class);
        Integer processId = null;
        try {
            processId = dao.getProcessId();
        } catch (Exception e) {
            throw new ScopixException(e);
        }

        return processId;
    }
}