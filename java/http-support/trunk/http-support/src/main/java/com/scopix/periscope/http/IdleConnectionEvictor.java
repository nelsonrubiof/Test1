package com.scopix.periscope.http;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;

/**
 * Clase para monitorear conexiones en espera y/o expiradas
 * 
 * @author carlos polo
 */
public class IdleConnectionEvictor extends Thread {

    private volatile boolean shutdown;
    private final PoolingHttpClientConnectionManager connManager;
    private static final Logger log = Logger.getLogger(IdleConnectionEvictor.class);

    public IdleConnectionEvictor(PoolingHttpClientConnectionManager connManager) {
        super();
        this.connManager = connManager;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(30000);
                    //Close expired connections
                    log.debug("closeExpiredConnections");
                    connManager.closeExpiredConnections();
                    //Optionally, close connections 
                    //that have been idle longer than 5 sec
                    connManager.closeIdleConnections(10, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
    	log.info("start");
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
        log.info("end");
    }
}