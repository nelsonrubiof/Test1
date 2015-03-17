package com.scopix.periscope.nextlevel.utilities;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/*
 * 
 * Copyright (c) 2012, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 * Hilo para el procesamiento de eventos que se generan a partir de solicitudes de
 * exportación de videos para la nueva versión de integración entre EES y NextLevel
 *
 * @author    carlos polo
 * @created   19-oct-2012
 *
 */
@SuppressWarnings("static-access")
public class NextLevel3EventListenerThread extends Thread {

    int count;
    private String user;
    private String pass;
    public String domain;
    private long timeout;
    private long waitTime;
    private String storeName;
    private boolean shutdown;
    private Integer portHttps;
    private NextLevelManager nextLevelManager;
    private HashMap<String, Calendar> hmArchivosTimeOut; //Almacena referencia de archivos solicitados y su hora de solicitud
    private static Logger log = Logger.getLogger(NextLevel3EventListenerThread.class);
    private static final String PROPERTIES_NEXTLEVEL3_TIMEOUT = "nextLevel3.timeoutInSec";
    private static final String PROPERTIES_NEXTLEVEL3_WAIT    = "nextLevel3.eventWaitTimeInSec";

    public void init(String domain, String storeName, String user, String pass) {
        Date now = new Date();
        this.setName("Event_" + storeName + "_" + DateFormatUtils.format(now, "yyyyMMddHHmmss"));

        this.user = user;
        this.pass = pass;
        this.domain = domain;
        this.storeName = storeName;
        
        try {
            URL aURL = new URL(domain);
            this.portHttps = aURL.getPort();
            
            /*carlos polo 23-oct-2012. Obtiene parametrización para el timeout de las solicitudes y para el tiempo de
            espera de los ciclos del hilo*/
            ExtractionManager extractionManager = getExtractionManager();
            timeout = extractionManager.getLongProperties(PROPERTIES_NEXTLEVEL3_TIMEOUT);
            waitTime = extractionManager.getLongProperties(PROPERTIES_NEXTLEVEL3_WAIT);
            
        } catch (MalformedURLException e) {
            log.error("No se recibe puerto en solicitud " + domain);
            this.portHttps = 1;
        }
    }
    
    @Override
    public void run() {
        log.info("start");
        //El hilo se mantendrá en ejecución para realizar cada determinado tiempo las solicitudes
        while (!shutdown) {
            try {
                try {
                    //Tiempo de espera antes de volver a solicitar eventos, la parametrización está en segundos, se convierte a milis.
                    log.debug("Se dormira el presente hilo por " + waitTime + " segundos.");
                    Thread.currentThread().sleep(waitTime * 1000);

                } catch (InterruptedException e) {
                    log.error("No se pudo interrumpir el ciclo por 1 seg" + e, e);
                }

                //Invoca operación para obtener eventos generados en caso que existan
                getNextLevelManager().getNextLevel3Event(domain + "/api/nlssgateway/v1/NLSSEvent/eventType/13/isLocked/0/"
                        + "displayUI/1/ORDER/eventTime/SORT/desc/LIMIT/100/", storeName, portHttps, user, pass);
                
                //Valida las solicitudes que hayan cumplído el timeout para ser eliminadas
                validarTimeOutSolicitudes();

            } catch (ScopixException e) {
                log.error("Error general en Thread " + e, e);
            } catch (Exception e) {
                //esto puede ocurrir si los properties no estan o otro error no especificado
                log.error("Error general en Thread " + e, e);
                count = 0;
            }
        }
    }
    
    /**
     * Valida las solicitudes que hayan cumplído el timeout para ser eliminadas
     * 
     * @author  carlos polo
     * @version 3.0
     * @date    23-oct-2012
     */
    private void validarTimeOutSolicitudes(){
        log.debug("Inicio validarTimeOutSolicitudes");
        Calendar horaActual = Calendar.getInstance();
        List<String> lstLlavesEliminar = new ArrayList<String>(); //Almacena llaves de archivos que cumplieron timeout, para ser eliminados del hmArchivosTimeOut

        /*Obtiene archivos solicitados para calcular si se ha cumplido el timeout de acuerdo a la 
        hora en que se originó la solicitud*/
        hmArchivosTimeOut = getNextLevelManager().getHmArchivosTimeOut();

        Iterator it = hmArchivosTimeOut.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            //Obtiene la fecha/hora de la solicitud del archivo
            Calendar horaSolicitud = hmArchivosTimeOut.get(e.getKey().toString());
            if(horaSolicitud!=null){
                log.debug("Validando timeout de solicitud para archivo: " + e.getKey().toString());
                //Obtiene la representación de las fechas/horas en milisegundos
                long milisActual    = horaActual.getTimeInMillis();
                long milisSolicitud = horaSolicitud.getTimeInMillis();

                //Calcula la diferencia en milisengundos
                long diffMilis = milisActual - milisSolicitud;
                //Calcula la diferencia en segundos
                long diffSeconds = diffMilis / 1000;

                log.debug("Segundos transcurridos desde inicio de la solicitud: " + diffSeconds + ", timeout: " + timeout);
                
                //Valida si el timeout para esa solicitud ya se ha cumplido
                if(diffSeconds >= timeout){
                    /*Adiciona a la lista que almacena la referencia de los archivos para eliminar, no se elimina directamente
                    del HashMap porque se generaría una excepción del tipo java.util.ConcurrentModificationException dado que estamos
                    iterando dicha estructura*/
                    lstLlavesEliminar.add(e.getKey().toString());
                    log.debug("Timeout cumplido para la solicitud del archivo: " + e.getKey().toString() + 
                            ". Tiempo transcurrido (segundos): " + diffSeconds + ", timeout configurado (segundos): " + timeout);
                }else{
                    log.debug("No se ha cumplido el timeout para el archivo: " + e.getKey().toString());
                }
            }
        }

        //Valida si la lista de archivos para eliminar x timeout no está vacía
        if(!lstLlavesEliminar.isEmpty()){
            for(String llave : lstLlavesEliminar){
                log.debug("Eliminado del hmArchivosTimeOut el archivo: " + llave);
                //Elimina la referencia del archivo porque se cumplió el timeout
                hmArchivosTimeOut.remove(llave);
                getExtractionManager().getAlternativesFileName().remove(llave);
            }
        }
        log.debug("Fin validarTimeOutSolicitudes");
    }
    
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * @return the nextLevelManager
     */
    public NextLevelManager getNextLevelManager() {
        if(nextLevelManager==null){
            nextLevelManager = SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class);
        }
        return nextLevelManager;
    }

    /**
     * @param nextLevelManager the nextLevelManager to set
     */
    public void setNextLevelManager(NextLevelManager nextLevelManager) {
        this.nextLevelManager = nextLevelManager;
    }
    
    /**
     * Obtiene única instancia del tipo ExtractionManager
     * 
     * @author  carlos polo
     * @version 3.0
     * @return  ExtractionManager
     * @date    23-oct-2012
     */
    public ExtractionManager getExtractionManager(){
        return SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
    }
}