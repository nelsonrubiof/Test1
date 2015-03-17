/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.nextlevel.utilities;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class IdleConnectionEvictor extends Thread {

    private final ClientConnectionManager connMgr;
    private volatile boolean shutdown;
    private static Logger log = Logger.getLogger(IdleConnectionEvictor.class);

    public IdleConnectionEvictor(ClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(30000);
                    // Close expired connections
                    //log.debug("closeExpiredConnections");
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    //log.debug("closeIdleConnections");
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException " + e, e);
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
