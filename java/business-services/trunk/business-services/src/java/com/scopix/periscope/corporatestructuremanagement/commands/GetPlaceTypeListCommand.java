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
 * GetPlaceTypeListCommand.java
 *
 * Created on 31-03-2008, 06:58:43 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.PlaceType;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetPlaceTypeListCommand {

    public List<PlaceType> execute() {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        List<PlaceType> list = dao.getAll(PlaceType.class);

        return list;
    }
}
