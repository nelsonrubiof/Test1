/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  SynchronizationManager.java
 * 
 *  Created on 22-06-2011, 12:41:36 PM
 * 
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.commands.GetUserCommand;
import com.scopix.periscope.securitymanagement.commands.GetUserListCommand;
import com.scopix.periscope.securitymanagement.commands.SynchronizeRoleGroupCommand;
import com.scopix.periscope.securitymanagement.commands.SynchronizeUserCommand;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = SynchronizationManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class SynchronizationManager {

    private GetUserCommand userCommand;

    /**
     * Sincroniza un PeriscopeUser a Security de Respaldo actualiza o crea un usuario en el servidor
     *
     * @param periscopeUser
     */
    public void synchronizeUser(Integer periscopeUserId) throws ScopixException {
        SynchronizeUserCommand synchroninizeUserCommand = new SynchronizeUserCommand();
        synchroninizeUserCommand.execute(periscopeUserId);

    }

    /**
     * Sincroniza un PeriscopeUser a Security de Respaldo actualiza o crea un usuario en el servidor
     *
     * @param periscopeUser
     */
    public void synchronizeUsers(List<PeriscopeUserDTO> list) throws ScopixException {
        for (PeriscopeUserDTO periscopeUserDTO : list) {
            synchronizeUser(periscopeUserDTO.getUserId());
        }
    }

    public void synchronizeRoleGroup(Integer rolesGroupId) {
        SynchronizeRoleGroupCommand synchroninizeRoleGroupCommand = new SynchronizeRoleGroupCommand();
        synchroninizeRoleGroupCommand.execute(rolesGroupId);
    }

    public GetUserCommand getUserCommand() {
        return userCommand;
    }

    public void setUserCommand(GetUserCommand userCommand) {
        this.userCommand = userCommand;
    }

    public void synchronizeDeleteUsers(List<Integer> list) throws ScopixException {
        for (Integer periscopeUserId : list) {
            synchronizeUser(periscopeUserId);
        }
    }

    public void synchronizeUserCorporate(Integer corporateId) throws ScopixException {
        GetUserListCommand command = new GetUserListCommand();
        PeriscopeUser puFilter = new PeriscopeUser();
        Corporate corporate = new Corporate();
        corporate.setId(corporateId);
        puFilter.setMainCorporate(corporate);
        List<PeriscopeUser> list = command.execute(puFilter);
        for (PeriscopeUser pu : list) {
            synchronizeUser(pu.getId());
        }
    }
}
