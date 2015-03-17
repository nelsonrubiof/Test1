/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * GetCorporatesForUserCommand.java
 *
 * Created on 15-06-2010, 05:08:56 PM
 *
 */
package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAOImpl;
import com.scopix.periscope.securitymanagement.dto.RolesGroupConverter;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetRolesGroupsForUserCommand {

    Logger log = Logger.getLogger(GetRolesGroupsForUserCommand.class);

    public List<RolesGroupDTO> execute(String userName) throws ScopixException {
        log.debug("start");
        List<RolesGroupDTO> rolesGroupDTOs = new ArrayList<RolesGroupDTO>();
        UserLoginHibernateDAOImpl dao = SpringSupport.getInstance().findBeanByClassName(UserLoginHibernateDAOImpl.class);
        RolesGroupConverter converter = new RolesGroupConverter();

        PeriscopeUser periscopeUser = dao.getUserByName(userName);
        // crear CorporateDTO para cada Corporate del usuario seleccionado
        if (periscopeUser != null) {
            for (RolesGroup r : periscopeUser.getRolesGroups()) {
                rolesGroupDTOs.add(converter.convertToDTO(r));
            }
        }

        Collections.sort(rolesGroupDTOs, rolesGroupDTOComparator);
        log.debug("end");
        return rolesGroupDTOs;
    }

    private Comparator<RolesGroupDTO> rolesGroupDTOComparator = new Comparator<RolesGroupDTO>() {

        @Override
        public int compare(RolesGroupDTO o1, RolesGroupDTO o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    };
}
