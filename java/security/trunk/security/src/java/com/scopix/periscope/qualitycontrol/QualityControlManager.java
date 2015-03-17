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
 * QualityControlManager.java
 *
 * Created on 09-07-2010, 04:30:00 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.commands.GetPeriscopeUsersForAccessReportCommand;
import com.scopix.periscope.qualitycontrol.commands.PeriscopeUsersBackupForAccessReportCommand;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import com.scopix.periscope.securitymanagement.QualityControlManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = QualityControlManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class QualityControlManager {

    private GetPeriscopeUsersForAccessReportCommand accessReportCommand;
    private PeriscopeUsersBackupForAccessReportCommand usersBackupForAccessReportCommand;
    private static Logger log = Logger.getLogger(QualityControlManager.class);

    public List<PeriscopeUserForQualityControlDTO> getPeriscopeUsersForAccessReport(Date start, Date end, long sessionId,
            int corporateId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QualityControlManagerPermissions.ACCESS_REPORT_PERMISSION);
        log.debug("security ok");

        List<PeriscopeUserForQualityControlDTO> list = getAccessReportCommand().execute(start, end, corporateId);

        List<PeriscopeUserForQualityControlDTO> list2 = getUsersBackupForAccessReportCommand().execute(start, end, corporateId);
        //concatenamos con los resultados de SecurityBackup
        list.addAll(list2);
        log.debug("end");
        return list;
    }

    public GetPeriscopeUsersForAccessReportCommand getAccessReportCommand() {
        if (accessReportCommand == null) {
            accessReportCommand = new GetPeriscopeUsersForAccessReportCommand();
        }
        return accessReportCommand;
    }

    public void setAccessReportCommand(GetPeriscopeUsersForAccessReportCommand accessReportCommand) {
        this.accessReportCommand = accessReportCommand;
    }

    /**
     * @return the usersBackupForAccessReportCommand
     */
    public PeriscopeUsersBackupForAccessReportCommand getUsersBackupForAccessReportCommand() {
        if (usersBackupForAccessReportCommand == null) {
            usersBackupForAccessReportCommand = new PeriscopeUsersBackupForAccessReportCommand();
        }
        return usersBackupForAccessReportCommand;
    }

    /**
     * @param value the usersBackupForAccessReportCommand to set
     */
    public void setUsersBackupForAccessReportCommand(PeriscopeUsersBackupForAccessReportCommand value) {
        this.usersBackupForAccessReportCommand = value;
    }
}
