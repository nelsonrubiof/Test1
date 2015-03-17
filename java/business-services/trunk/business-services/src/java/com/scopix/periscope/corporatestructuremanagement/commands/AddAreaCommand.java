/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * AddAreaCommand.java
 *
 * Created on 07-05-2008, 03:36:14 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddAreaCommand {

    private static Logger log = Logger.getLogger(AddAreaCommand.class);

    public void execute(Area area) throws ScopixException {
        log.info("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(area);
        log.info("end");
    }
}
