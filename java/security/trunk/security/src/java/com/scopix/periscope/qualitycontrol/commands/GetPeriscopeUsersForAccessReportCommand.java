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
 * GetPeriscopeUsersForAccessReportCommand.java
 *
 * Created on 09-07-2010, 04:41:43 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAO;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetPeriscopeUsersForAccessReportCommand {

    private static Logger log = Logger.getLogger(GetPeriscopeUsersForAccessReportCommand.class);

    public List<PeriscopeUserForQualityControlDTO> execute(Date start, Date end, int corporateId) throws ScopixException {
        log.info("start");
        QualityControlHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(QualityControlHibernateDAO.class);

        List<PeriscopeUserForQualityControlDTO> list = dao.getPeriscopeUsersForAccessReports(start, end, corporateId);
        log.info("end");
        return list;
    }
}
