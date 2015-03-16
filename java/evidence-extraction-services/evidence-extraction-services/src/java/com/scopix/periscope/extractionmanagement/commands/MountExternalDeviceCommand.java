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
 * MountExternalDeviceCommand.java
 *
 * Created on 06-01-2010, 06:17:53 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.scheduler.devicemanager.DeviceUtil;

/**
 *
 * @author Gustavo Alvarez
 */
public class MountExternalDeviceCommand {

    public boolean execute() throws ScopixException {
        DeviceUtil deviceUtil = SpringSupport.getInstance().findBeanByClassName(DeviceUtil.class);
        boolean resp = false;

        try {
            resp = deviceUtil.mountDevice();
        } catch (Exception ex) {
            throw new ScopixException(ex);
        }

        return resp;
    }

}