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
 *  ReportingUploadThread.java
 * 
 *  Created on 14-01-2011, 04:55:34 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.reporting.ReportingUploadHelper;
import com.scopix.periscope.reporting.UploadProcess;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class ReportingUploadThread extends Thread {

    private static Logger log = Logger.getLogger(ReportingUploadThread.class);
    private UploadProcess process;

    public void init(UploadProcess up) {
        setProcess(up);
    }

    @Override
    public void run() {
        ReportingUploadHelper helper = new ReportingUploadHelper();
        helper.init(getProcess());
        helper.execute();
    }

    public UploadProcess getProcess() {
        return process;
    }

    public void setProcess(UploadProcess process) {
        this.process = process;
    }
}
