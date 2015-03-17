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
 * CheckOldLoginThreat.java
 *
 * Created on 10-11-2009, 06:43:54 PM
 *
 */
package com.scopix.periscope.securitymanagement;

import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * This thread delete old sessions from the static map
 * @author Cesar Abarza
 */
public class CheckOldLoginThread extends Thread {

    private static Logger log = Logger.getLogger(CheckOldLoginThread.class);

    @Override
    public void run() {
        log.debug("start");
        Integer threatSleepTime = SystemConfig.getIntegerParameter("THREAD_SLEEP_TIME_IN_MINUTES");
        log.debug("threadSleepTime = " + threatSleepTime);
        Integer sessionValidTime = SystemConfig.getIntegerParameter("SESSION_VALID_TIME_IN_MINUTES");
        log.debug("sessionValidTime = " + sessionValidTime);
        do {
            try {
                if (SecurityManager.usersPrivileges != null && !SecurityManager.usersPrivileges.isEmpty()) {
                    Set keys = SecurityManager.usersPrivileges.keySet();
                    log.debug("keys lenght = " + keys.size());
                    for (Object sessionId : keys) {
                        HashMap sessionMap = (HashMap) SecurityManager.usersPrivileges.get(sessionId);
                        Calendar cal = (Calendar) sessionMap.get("lastAccess");
                        log.debug("lastAccess = " + cal);
                        Calendar now = Calendar.getInstance();
                        log.debug("now = " + now);
                        now.add(Calendar.MINUTE, sessionValidTime * -1);
                        log.debug("now - sessionValidTime = " + now);
                        if (now.after(cal)) {
                            SecurityManager.usersPrivileges.remove(sessionId);
                            log.debug("session Removed");
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                log.debug("Thread sleep");
                try {
                    sleep(threatSleepTime * 60000);
                } catch (InterruptedException ex) {
                    log.error("Error, " + ex.getMessage());
                }
                log.debug("Thread wake up");
            }
        } while (true);
    }
}
