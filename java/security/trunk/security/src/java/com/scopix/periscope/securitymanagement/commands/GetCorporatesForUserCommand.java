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
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.dao.SecurityManagementHibernateDAO;
import com.scopix.periscope.securitymanagement.dto.CorporateConverter;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetCorporatesForUserCommand {

    Logger log = Logger.getLogger(GetCorporatesForUserCommand.class);

    public List<CorporateDTO> execute(String userName) throws ScopixException {
        log.debug("start");
        List<CorporateDTO> corporateDTOs = new ArrayList<CorporateDTO>();
        SecurityManagementHibernateDAO dao = SpringSupport.getInstance().
                findBeanByClassName(SecurityManagementHibernateDAO.class);
        CorporateConverter converter = new CorporateConverter();

        // crear filtro para obtener el usuario
        PeriscopeUser periscopeUser = new PeriscopeUser();
        periscopeUser.setName(userName);

        List<PeriscopeUser> periscopeUsers = dao.getUserList(periscopeUser);
        // crear CorporateDTO para cada Corporate del usuario seleccionado
        if (periscopeUsers.size() > 0) {
            for (Corporate c : periscopeUsers.get(0).getCorporates()) {
                if (c.getActive()) {
                    corporateDTOs.add(converter.convertToDTO(c));
                }
            }
        }

        Collections.sort(corporateDTOs, corporateDTOComparator);
        log.debug("end");
        return corporateDTOs;
    }

    private Comparator<CorporateDTO> corporateDTOComparator = new Comparator<CorporateDTO>() {

        public int compare(CorporateDTO o1, CorporateDTO o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    };
}
