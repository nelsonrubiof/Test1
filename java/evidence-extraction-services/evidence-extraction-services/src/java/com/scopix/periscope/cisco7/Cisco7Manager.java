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
 *  Cisco7Manager.java
 * 
 *  Created on 09-01-2014, 11:36:36 AM
 * 
 */
package com.scopix.periscope.cisco7;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;

import com.scopix.periscope.cisco7.Vsm7ApiPojos.Device;
import com.scopix.periscope.cisco7.thread.KeepAliveThread;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 *
 * @author carlos polo
 */
@SpringBean(rootClass = Cisco7Manager.class)
public class Cisco7Manager implements DisposableBean {

    private HashMap<String, Device> hmCameras;
    private PropertiesConfiguration configuration;
    private HashMap<String, KeepAliveThread> hmKeepAlive;
    private HashMap<String, VsomServices> hmVsomServices;
    private static final String KEEP_ALIVE = "cisco7.keepAlive";
    private static Logger log = Logger.getLogger(Cisco7Manager.class);
    private static final String AUTHENTICATION_RETRY = "cisco7.authentication.retry";
    private static final String KEEP_ALIVE_INTERVAL_IN_SECS = "cisco7.keepAlive.interval";

    /**
     * Obtiene instancia del vsomServices (con autenticación incluída)
     * 
     * @param storeName
     * @param vsomIpAddress
     * @param vsomUser
     * @param vsomPassword
     * @param vsomDomain
     * @param vsomProtocol
     * @param vsomPort
     * @param mediaServerProtocol
     * @param mediaServerPort
     * @return
     * @throws ScopixException 
     */
    public synchronized VsomServices getVsomServices(String storeName, String vsomIpAddress, 
            String vsomUser, String vsomPassword, String vsomDomain, String vsomProtocol, 
            String vsomPort, String mediaServerProtocol, String mediaServerPort) throws ScopixException {

        log.info("start, storeName: ["+storeName+"], vsomDomain: ["+vsomDomain+"]");
        //obtiene map con instancias de vsomServices por store
        VsomServices vsomServices = getHmVsomServices().get(storeName);
        log.debug("instancia de vsomServices para el store: [" + storeName + "] : [" + vsomServices + "]");

        if(vsomServices==null){
            int retryNumber = getAuthenticationRetryNumber();
            log.debug("no existe instancia de vsomServices para el store "
                    + "["+storeName+"], se crea y se adiciona al map del cisco7Manager. \n "
                    + "vsomIpAddress: ["+vsomIpAddress+"], vsomUser: ["+vsomUser+"], vsomDomain: ["+vsomDomain+"], "
                    + "vsomProtocol: ["+vsomProtocol+"], vsomPort: ["+vsomPort+"], "
                    + "mediaServerProtocol: ["+mediaServerProtocol+"], mediaServerPort: ["+mediaServerPort+"], "
                    + "retryNumber: ["+retryNumber+"]");

            //inicializa objeto y autentica con el VMS
            vsomServices = new VsomServices(vsomIpAddress, vsomUser, vsomPassword, 
                    vsomDomain, vsomProtocol, vsomPort, mediaServerProtocol, mediaServerPort, retryNumber);

            getHmVsomServices().put(storeName, vsomServices);
            
            String keepAlive = getKeepAliveVariable();
            log.debug("keepAlive: [" + keepAlive + "]");
            if(keepAlive!=null && "S".equalsIgnoreCase(keepAlive)){
                initKeepAliveThread(storeName, vsomServices);
            }
        }
        log.info("end, vsomServices: [" + vsomServices + "], storeName: [" + storeName + "]");
        return vsomServices;
    }
    
    /**
     * Obtiene cámara
     * 
     * @param cameraName
     * @param vsomServices
     * @return
     * @throws PeriscopeException 
     */
    public synchronized Device getCamera(String cameraName, VsomServices vsomServices, String storeName) throws ScopixException {
        log.info("start, cameraName: [" + cameraName + "], storeName: [" + storeName + "]");
        String key = cameraName+"_"+storeName;
        Device camera = getHmCameras().get(key);

        if(camera==null){
            camera = vsomServices.getCameraByExactName(cameraName);
            getHmCameras().put(key, camera);
        }
        log.info("end, camera: [" + camera + "], storeName: [" + storeName + "]");
        return camera;
    }

    /**
     * Inicializa hilo de verificación de sesión
     *
     * @param storeName
     * @param vsomServices
     */
    public void initKeepAliveThread(String storeName, VsomServices vsomServices) {
    	log.info("start, storeName: ["+storeName+"]");
    	KeepAliveThread keepAliveThread = getHmKeepAlive().get(storeName);
    	
    	if(keepAliveThread==null){
    		Long threadInterval = getKeepAliveIntervalInSecs();
    		log.debug("threadInterval en segundos: ["+threadInterval+"]");
            keepAliveThread = new KeepAliveThread(vsomServices, threadInterval);
            keepAliveThread.start();

            log.debug("keepAlive creado para store: [" + storeName + "]");
            getHmKeepAlive().put(storeName, keepAliveThread);
    	}
        log.info("end");
    }

    /**
     * Detiene hilos que mantienen vivas las diferentes sesiones con VSOM
     */
    public void stopKeepAliveThreads(){
        log.info("start");
        if (getHmKeepAlive() != null && !getHmKeepAlive().isEmpty()) {
            for (Map.Entry<String, KeepAliveThread> entry : getHmKeepAlive().entrySet()) {
                KeepAliveThread thread = entry.getValue();
                thread.setStop(true);
            }
        }
        log.info("end");
    }
    
    /**
     * Cierra sesión en los VSOMs activos 
     */
	@Override
	public void destroy() throws Exception {
		log.info("start");
        try {
			if (getHmVsomServices() != null && !getHmVsomServices().isEmpty()) {
			    for (Map.Entry<String, VsomServices> entry : getHmVsomServices().entrySet()) {
			    	VsomServices vsomService = entry.getValue();
			    	//cierra sesión en el correspondiente vsom
			        vsomService.logout();
			    }
			}
		} catch (Exception e) {
			log.warn("error cerrando sesion en instancias de VSOM: ["+e.getMessage()+"]", e);
		}
        log.info("end");
	}

    /**
     * @return the hmVsomServices
     */
    public HashMap<String, VsomServices> getHmVsomServices() {
        if (hmVsomServices == null) {
            hmVsomServices = new HashMap<String, VsomServices>();
        }
        return hmVsomServices;
    }

    /**
     * @param hmVsomServices the hmVsomServices to set
     */
    public void setHmVsomServices(HashMap<String, VsomServices> hmVsomServices) {
        this.hmVsomServices = hmVsomServices;
    }

    /**
     * Retorna variable que indica intérvalo de ejecución para el hilo keepAlive en segundos
     *
     * @return
     */
    public Long getKeepAliveIntervalInSecs() {
        return getConfiguration().getLong(KEEP_ALIVE_INTERVAL_IN_SECS, new Long(240));
    }

    /**
     * Retorna variable que indica si debe iniciarse el hilo de verificación de sesión
     *
     * @return
     */
    public String getKeepAliveVariable() {
        return getConfiguration().getString(KEEP_ALIVE, "N");
    }

    /**
     * Retorna el número parametrizado para los intentos de autenticación
     *
     * @return
     */
    public Integer getAuthenticationRetryNumber() {
        return getConfiguration().getInteger(AUTHENTICATION_RETRY, 0);
    }

    /**
     * @return the configuration
     */
    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades [" + e.getMessage() + "]", e);
            }
        }
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }
    
    /**
     * @return the hmCameras
     */
    public HashMap<String, Device> getHmCameras() {
        if(hmCameras==null){
            hmCameras = new HashMap<String, Device>();
        }
        return hmCameras;
    }

    /**
     * @return the hmKeepAlive
     */
    public HashMap<String, KeepAliveThread> getHmKeepAlive() {
        if(hmKeepAlive==null){
            hmKeepAlive = new HashMap<String, KeepAliveThread>();
        }
        return hmKeepAlive;
    }
}