/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.nextlevel.utilities;

import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class EventListenerThread extends Thread {

    private static Logger log = Logger.getLogger(EventListenerThread.class);
    public String domain;
    int count;
    private boolean shutdown;
    private String storeName;
    private String user;
    private String pass;
    private Integer portHttps;

    public void init(String domain, String storeName, String user, String pass) {
        Date now = new Date();
        this.setName("Event_" + storeName + "_" + DateFormatUtils.format(now, "yyyyMMddHHmmss"));

        this.domain = domain;
        this.storeName = storeName;

        this.user = user;
        this.pass = pass;
        try {
            URL aURL = new URL(domain);
            this.portHttps = aURL.getPort();
        } catch (MalformedURLException e) {
            log.error("No se recibe puerto en solicitud " + domain);
            this.portHttps = 1;
        }
    }

    @Override
    public void run() {
        log.info("start");
        //dejamos corriendo la solicitud de eventos
        while (!shutdown) {
            //en caso de que la solicitud se pierda
            try {
                try {
                    //esperamos 1 segundo antes de volver a pedir eventos
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    log.error("no se pudo interrumpir el ciclo por 1 seg" + e, e);
                }

                SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class).getEvent(
                        domain + "/api/events.php", storeName, portHttps, user, pass);
                log.debug("Se pierde conexion intentamos reconectar");
                SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class).loginGatewayNextLevel(storeName,
                        domain, user, pass);

            } catch (ScopixException e) {
                log.error("Error general en Thread " + e, e);
            } catch (Exception e) {
                //esto puede ocurrir si los properties no estan o otro error no especificado
                log.error("Error general en Thread " + e, e);
                count = 0;
            }
        }

    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
