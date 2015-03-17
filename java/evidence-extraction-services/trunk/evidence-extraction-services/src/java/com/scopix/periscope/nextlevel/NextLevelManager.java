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
 *  NextLevelManager.java
 * 
 *  Created on 29-02-2012, 05:22:58 PM
 * 
 */
package com.scopix.periscope.nextlevel;

import com.scopix.periscope.NextLevelParser;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.nextlevel.utilities.*;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = NextLevelManager.class) //, initMethod = "init"
public class NextLevelManager {

    private boolean flag;
    private NextLevel nextLevel;
    private Set<String> storeInit;
    private Map<String, HttpClient> clientStore;
    private IdleConnectionEvictor connEvictor;
    private EventListenerThread eventListenerThread;
    /* carlos polo 22-oct-2012. Se crean nuevas variables para la nueva versión de integración entre EES y NextLevel,
     * adicionalmente se crea una estructura de datos en donde se guardará la referencia de los archivos solicitados
     * y la fecha/hora de la solicitud para validación del timeout de la misma.
     */
    private HashMap<String, NextLevel3> hmNextLevel3;
    private HashMap<String, Calendar> hmArchivosTimeOut;
    private HashMap<String, NextLevel3EventListenerThread> hmEventListeners;
    private static Logger log = Logger.getLogger(NextLevelManager.class);
    private static final String PROPERTIES_NEXTLEVEL3_VIDEOFORMAT = "nextLevel3.videoFormat";

    public void upEvents(String storeName, String urlGateway, String user, String pass) {
        //Creamos un Event Listener para escuchar los enventos NextLevel
        if (!getStoreInit().contains(storeName)) {
            getStoreInit().add(storeName);
            eventListenerThread = new EventListenerThread();
            eventListenerThread.init(urlGateway, storeName, user, pass); //primera urlGateway,
            eventListenerThread.start();
        }
    }

    /**
     * Inicializa hilo que escuchará eventos para la generación de videos en la nueva versión de integración entre EES y NextLevel
     *
     * @author carlos polo
     * @param storeName nombre del store
     * @param urlGateway dominio
     * @param user usuario
     * @param pass password
     * @version 3.0
     * @date 22-oct-2012
     */
    public void upNextLevel3Events(String storeName, String urlGateway, String user, String pass) {
        log.debug("Inicio upNextLevel3Events");

        if (!getStoreInit().contains(storeName)) {
            getStoreInit().add(storeName);

            if (hmEventListeners == null) {
                hmEventListeners = new HashMap<String, NextLevel3EventListenerThread>();
            }

            //Si llegan varias solicitudes para un mismo store, reutiliza el mismo hilo
            if (hmEventListeners.get(storeName) == null) {
                log.debug("Se crea hilo de escucha de eventos (NextLevel3EventListenerThread) para el store: " + storeName);
                NextLevel3EventListenerThread nextLevel3EventListenerThread = new NextLevel3EventListenerThread();
                nextLevel3EventListenerThread.init(urlGateway, storeName, user, pass);
                nextLevel3EventListenerThread.start();

                hmEventListeners.put(storeName, nextLevel3EventListenerThread);
            } else {
                log.debug("Ya existe un hilo de escucha de eventos (NextLevel3EventListenerThread) para el store: " + storeName);
            }
        }
    }

    public HttpClient getClient(String storeName, int portHttps) {
        HttpClient client = getClientStore().get(storeName);
        if (client == null) {
            try {
                ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
                cm.setMaxTotal(100);
                client = new DefaultHttpClient(cm);

                HttpParams params = new BasicHttpParams();
                params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000L);
                SSLSocketFactory sf = getSSLSocketFactory();
                SSLSocket socket = (SSLSocket) sf.createSocket(params);
                socket.setEnabledCipherSuites(new String[]{"SSL_RSA_WITH_RC4_128_MD5"});
                Scheme sch = new Scheme("https", portHttps, sf);
                client.getConnectionManager().getSchemeRegistry().register(sch);

                //Se ocupa para revisar las conexiones expiradas o idle
                connEvictor = new IdleConnectionEvictor(cm);
                connEvictor.setName("connEvictor_" + storeName);
                connEvictor.start();

                getClientStore().put(storeName, client);

            } catch (IOException e) {
                log.error("No se pudo configurar cliente " + e, e);
            }
        }
        return client;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory sf = null;
        TrustManager easyTrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        try {
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, new TrustManager[]{easyTrustManager}, new SecureRandom());
            sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (NoSuchAlgorithmException e) {
            log.error(e, e);
        } catch (KeyManagementException e) {
            log.error(e, e);
        }
        return sf;
    }

    public void shutdown(String storeName, Integer portHttps, String urlGateway, String user, String pass) {
        log.info("start");
        //bajamos el thread de validacion
        connEvictor.shutdown();
        try {
            connEvictor.join();
        } catch (InterruptedException e) {
            log.error("No se pudo bajar validacion de conexiones " + e, e);
        }

        //bajamos el cliente
        getClient(storeName, portHttps).getConnectionManager().shutdown();
        //una vez bajado el cliente        
        if (eventListenerThread == null || !eventListenerThread.isAlive()) {
            upEvents(storeName, urlGateway, user, pass);
        }

        log.info("end");
    }

    public void reconect(String storeName, Integer portHttps, String urlGateway, String user, String pass) {
        // bajar client
        shutdown(storeName, portHttps, urlGateway, user, pass);
        // levantar client
        loginGatewayNextLevel(storeName, urlGateway, user, pass);
    }

    public void loginGatewayNextLevel(String storeName, String urlGateway, String user, String pass) {
        log.info("start");
        String ret = null;
        while (ret == null || ret.length() == 0) {
            ret = getNextLevel(urlGateway, user, pass).gatewayLogon(storeName);
        }
        log.info("end");
    }

    /**
     * Ejecuta operación de autenticación con NextLevel para la nueva versión
     *
     * @author carlos polo
     * @param storeName
     * @param urlGateway
     * @param user
     * @param pass
     * @version 3.0
     * @date 23-oct-2012
     */
    public void loginGatewayNextLevel3(String storeName, String urlGateway, String user, String pass) {
        log.info("Inicio loginGatewayNextLevel3");
        String ret = null;
        while (ret == null || ret.length() == 0) {
            ret = getNextLevel3(storeName, urlGateway, user, pass).gatewayLogon(storeName);
        }
        log.info("Fin loginGatewayNextLevel3");
    }

    public String postNextLevel3(String url, String data, String storeName, Integer portHttps,
            String urlGateway, String user, String pass) throws ScopixException {

        log.info("Inicio postNextLevel3 [url:" + url + "]");

        //Valida si la conexión NextLevel para el store se encuentra autenticada
        log.debug("Conexión NextLevel para el store: " + storeName + ": " + getHmNextLevel3().get(storeName).isLogged());
        if (!getHmNextLevel3().get(storeName).isLogged()) {
            loginGatewayNextLevel3(storeName, urlGateway, user, pass);
        }

        StringBuilder xmlResponse = new StringBuilder();
        BufferedReader br = null;
        HttpPost post = new HttpPost(url);

        try {
            post.setEntity(new StringEntity(data));

            HttpResponse response = getClient(storeName, portHttps).execute(post);
            HttpEntity resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
                log.error("The Post method is not implemented by this URI");
                return null;
            }
            log.debug("Response content length: " + resEntity.getContentLength());

            br = new BufferedReader(new InputStreamReader(resEntity.getContent()));
            String readLine;
            while (((readLine = br.readLine()) != null)) {
                xmlResponse.append(readLine);
            }
            br.close();

            EntityUtils.consume(resEntity);
        } catch (HttpHostConnectException e) {
            //no existe conexion al server
            getNextLevel3(storeName, urlGateway, user, pass).setLogged(false);
            throw new ScopixException("NO_CONECTION", e);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException " + e, e);
        }
        log.debug("Fin postNextLevel3");
        return xmlResponse.toString();
    }

    public String post(String url, String data, String storeName, Integer portHttps, String urlGateway, String user, String pass)
            throws ScopixException {
        log.info("start [url:" + url + "]");

        if (getNextLevel(urlGateway, user, pass).isLogged()) {
            loginGatewayNextLevel(storeName, urlGateway, user, pass);
        }

        StringBuilder xmlResponse = new StringBuilder();
        BufferedReader br = null;
        HttpPost post = new HttpPost(url);

        try {
            post.setEntity(new StringEntity(data));

            HttpResponse response = getClient(storeName, portHttps).execute(post);
            HttpEntity resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
                log.error("The Post method is not implemented by this URI");
                return null;
            }
            log.debug("Response content length: " + resEntity.getContentLength());

            br = new BufferedReader(new InputStreamReader(resEntity.getContent()));
            String readLine;
            while (((readLine = br.readLine()) != null)) {
                xmlResponse.append(readLine);
            }
            br.close();

            EntityUtils.consume(resEntity);
        } catch (HttpHostConnectException e) {
            //no existe conexion al server
            throw new ScopixException("NO_CONECTION", e);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException " + e, e);
        }
        log.debug("end");
        return xmlResponse.toString();

    }

    public void get(String url, String storeName, Integer portHttps, String urlGateway, String user, String pass) {
        log.info("start");

        if (getNextLevel(urlGateway, user, pass).isLogged()) {
            loginGatewayNextLevel(storeName, urlGateway, user, pass);
        }
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = getClient(storeName, portHttps).execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
                log.error("The Post method is not implemented by this URI");
                return;
            }
            HttpEntity resEntity = response.getEntity();
            ByteArrayOutputStream xmlResponse = new ByteArrayOutputStream();
            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                xmlResponse.write(buf, 0, len);
            }
            EntityUtils.consume(resEntity);
            log.debug(xmlResponse.toString());
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException " + e, e);
        }
        log.info("end");
    }

    /**
     * Ejecuta operación para obtener un video exportado
     *
     * @author carlos polo
     * @param urlStr
     * @param storeName
     * @param portHttps
     * @version 3.0
     * @return File
     * @date 23-oct-2012
     */
    public File getNextLevel3File(String urlStr, String storeName, Integer portHttps) {
        log.info("Inicio getNextLevel3File [url:" + urlStr + "]");
        File tmp = null;
        try {
            //Obtiene el formato parametrizado para archivos de video
            ExtractionManager extractionManager = getExtractionManager();
            String suffix = extractionManager.getStringProperties(PROPERTIES_NEXTLEVEL3_VIDEOFORMAT); //ej: .flv

            //creamos el file temporal donde recibiremos el response
            tmp = File.createTempFile("tempflv", suffix);
            OutputStream out = new FileOutputStream(tmp);
            HttpGet get = new HttpGet(urlStr);
            HttpResponse response = getClient(storeName, portHttps).execute(get);
            HttpEntity resEntity = response.getEntity();
            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            EntityUtils.consume(resEntity);
        } catch (IOException e) {
            log.error("Error en peticion getFile " + e, e);
            tmp = null;
        }
        return tmp;

    }

    public File getFile(String urlStr, String storeName, Integer portHttps) {
        log.info("start [url:" + urlStr + "]");
        File tmp = null;
        try {
            //creamos el file temportal donde recibiremos el reponse
            tmp = File.createTempFile("tempflv", ".flv");
            OutputStream out = new FileOutputStream(tmp);
            HttpGet get = new HttpGet(urlStr);
            HttpResponse response = getClient(storeName, portHttps).execute(get);
            HttpEntity resEntity = response.getEntity();
            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            EntityUtils.consume(resEntity);
        } catch (IOException e) {
            log.error("Error en peticion getFile " + e);
            tmp = null;
        }
        return tmp;

    }

    public void getEvent(String urlStr, String storeName, Integer portHttps, String user, String pass) throws ScopixException {
        log.info("start [url:" + urlStr + "][store:" + storeName + "][portHttps:" + portHttps + "]");

        try {
            HttpGet get = new HttpGet(urlStr);

            HttpResponse response = getClient(storeName, portHttps).execute(get);
            HttpEntity resEntity = response.getEntity();
            ByteArrayOutputStream xmlResponse = new ByteArrayOutputStream();
            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                if (buf[0] != 0) {
                    xmlResponse.write(buf, 0, len);
                } else {
                    NLSSEvent evt = NextLevelParser.parserNLSSEvent(xmlResponse.toString());
                    log.debug(evt != null ? evt.getEventType() : "Evento recuperado null");
                    if (evt != null && evt.getEventType() == 13 && evt.getEventCategory() == 0) {
                        //pasar nombre de file a manager para lanzar la pedida de este a nextlevel
                        //y colocarlo en el evidenceFile asociado
                        String name = evt.getNameFile();
                        SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).
                                addGetFileByEvent(name, storeName, urlStr, user, pass);
                    }
                    xmlResponse.reset();
                }
            }
            EntityUtils.consume(resEntity);
        } catch (HttpHostConnectException e) {
            log.error("HttpHostConnectException " + e, e);
            throw new ScopixException("NO_CONECTION", e);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException " + e, e);
        }
        log.info("end");
    }

    /**
     * Obtiene eventos generados a partir de solicitudes de video para la nueva versión de NextLevel
     *
     * @author carlos polo
     * @param urlStr
     * @param storeName
     * @param portHttps
     * @param user
     * @param pass
     * @version 3.0
     * @throws PeriscopeException
     * @date 22-oct-2012
     */
    public void getNextLevel3Event(String urlStr, String storeName, Integer portHttps, String user, String pass)
            throws ScopixException {
        log.info("start getNextLevel3Event [url:" + urlStr + "][store:" + storeName + "][portHttps:" + portHttps + "]");

        try {
            //Valida que no esté en uso la lectura de eventos
            if (!flag) {
                flag = true;

                //Valida si la conexión NextLevel para el store se encuentra autenticada
                if (!getHmNextLevel3().get(storeName).isLogged()) {
                    log.debug("La conexión para el store: " + storeName
                            + " no se encuentra autenticada, por lo tanto se invocará operación de autenticación");
                    loginGatewayNextLevel3(storeName, urlStr, user, pass);
                } else {
                    log.debug("La conexión para el store: " + storeName + " ya se encuentra autenticada");
                }

                HttpGet get = new HttpGet(urlStr);
                HttpResponse response = getClient(storeName, portHttps).execute(get);

                HttpEntity resEntity = response.getEntity();
                ByteArrayOutputStream xmlResponse = new ByteArrayOutputStream();
                byte[] buf = new byte[8192];
                int len;

                while ((len = resEntity.getContent().read(buf)) > 0) {
                    xmlResponse.write(buf, 0, len);
                }

                String strResponse = xmlResponse.toString();

                if (strResponse != null && !strResponse.trim().equals("")) {
                    List<NLSSEvent3> lstEventos = NextLevelParser.parserNLSS3Event(strResponse);

                    if (lstEventos != null && !lstEventos.isEmpty()) {
                        log.debug("Se encontraron eventos!");
                        for (NLSSEvent3 event : lstEventos) {
                            String eventResource = event.getEventResource(); //<deviceId> enviado en el generateClip

                            if (hmArchivosTimeOut != null && !hmArchivosTimeOut.isEmpty()) {
                                //Recorre la lista de archivos solicitados vigentes
                                Iterator it = hmArchivosTimeOut.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry e = (Map.Entry) it.next();

                                    //La llave es el nameNextLevel enviado en el NextLevel3VideoExtractionCommand
                                    //nameNextLevel = evidenceProvider.getUuid() + "_" + startUTC * 1000 + "_" + 
                                    //endUTC + EXTENSION;
                                    String fileName = e.getKey().toString();
                                    String[] file = StringUtils.split(fileName, "_");

                                    String deviceId1 = file[0];
                                    String start1 = file[1];
                                    String stop1 = file[2];

                                    int index = stop1.indexOf(".");
                                    stop1 = stop1.substring(0, index);

                                    if (eventResource != null && deviceId1 != null) {
                                        if (eventResource.equalsIgnoreCase(deviceId1)) {
                                            file = StringUtils.split(event.getPayload(), ",");
                                            fileName = FilenameUtils.getName(file[0]);

                                            file = StringUtils.split(fileName, "_");

                                            String start2 = file[1];
                                            String stop2 = file[2];

                                            index = stop2.indexOf(".");
                                            stop2 = stop2.substring(0, index);

                                            //Verifica que sea el evento solicitado del mismo intérvalo de tiempo
                                            if (start1.equalsIgnoreCase(start2) && stop1.equalsIgnoreCase(stop2)) {
                                                log.debug("Start1 del hmArchivosTimeOut: " + start1
                                                        + " es igual al start2 del evento, lo mismo para el stop: " + stop1);
                                                log.debug("FileName del evento: " + fileName);

                                                //pasar nombre de file a manager para lanzar la pedida de este a nextlevel
                                                //y colocarlo en el evidenceFile asociado
                                                SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).
                                                        addGetFileByEventNextLevel3(fileName, storeName, urlStr, user, pass);

                                                //Elimina la referencia del archivo recién exportado, para que
                                                //posteriormente no sea tenído en cuenta
                                                hmArchivosTimeOut.remove(fileName);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        log.debug("No hay eventos todavía...");
                    }
                } else {
                    log.debug("No hay eventos todavía...");
                }

                EntityUtils.consume(resEntity);
            }
        } catch (HttpHostConnectException e) {
            flag = false;
            log.error("HttpHostConnectException " + e, e);
            getNextLevel3(storeName, urlStr, user, pass).setLogged(false);
            throw new ScopixException("NO_CONECTION", e);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException " + e, e);
        }
        flag = false;
        log.info("end getNextLevel3Event");
    }

    public NextLevel getNextLevel(String urlGateway, String userGateway, String pwdGateway) {
        if (nextLevel == null) {
            //debemos generar uno con los datos desde las properties
            nextLevel = new NextLevel(urlGateway, userGateway, pwdGateway);
        }
        return nextLevel;
    }

    public void setNextLevel(NextLevel value) {
        this.nextLevel = value;
    }

    public Set<String> getStoreInit() {
        if (storeInit == null) {
            storeInit = new HashSet<String>();
        }
        return storeInit;
    }

    public void setStoreInit(Set<String> storeInit) {
        this.storeInit = storeInit;
    }

    public Map<String, HttpClient> getClientStore() {
        if (clientStore == null) {
            clientStore = new HashMap<String, HttpClient>();
        }
        return clientStore;
    }

    public void setClienteStore(Map<String, HttpClient> value) {
        this.clientStore = value;
    }

    /**
     * @return the hmArchivosTimeOut
     */
    public HashMap<String, Calendar> getHmArchivosTimeOut() {
        if (hmArchivosTimeOut == null) {
            hmArchivosTimeOut = new HashMap<String, Calendar>();
        }
        return hmArchivosTimeOut;
    }

    /**
     * @param hmArchivosTimeOut the hmArchivosTimeOut to set
     */
    public void setHmArchivosTimeOut(HashMap<String, Calendar> hmArchivosTimeOut) {
        this.hmArchivosTimeOut = hmArchivosTimeOut;
    }

    /**
     * Retorna instancia de NextLevel3 correspondiente al store
     *
     * @author carlos polo
     * @param storeName
     * @param urlGateway
     * @param userGateway
     * @param pwdGateway
     * @version 3.0
     * @return NextLevel3
     * @date 24-oct-2012
     */
    public NextLevel3 getNextLevel3(String storeName, String urlGateway, String userGateway, String pwdGateway) {

        NextLevel3 nextLevel3 = getHmNextLevel3().get(storeName);

        if (nextLevel3 == null) {
            nextLevel3 = new NextLevel3(urlGateway, userGateway, pwdGateway);
            getHmNextLevel3().put(storeName, nextLevel3);
        }

        return nextLevel3;
    }

    /**
     * Obtiene única instancia del tipo ExtractionManager
     *
     * @author carlos polo
     * @version 3.0
     * @return ExtractionManager
     * @date 23-oct-2012
     */
    public ExtractionManager getExtractionManager() {
        return SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
    }

    /**
     * @return the hmNextLevel3
     */
    public HashMap<String, NextLevel3> getHmNextLevel3() {
        //Estructura para mantener referencia de un objeto NextLevel3 por store
        if (hmNextLevel3 == null) {
            hmNextLevel3 = new HashMap<String, NextLevel3>();
        }
        return hmNextLevel3;
    }

    /**
     * @param hmNextLevel3 the hmNextLevel3 to set
     */
    public void setHmNextLevel3(HashMap<String, NextLevel3> hmNextLevel3) {
        this.hmNextLevel3 = hmNextLevel3;
    }
}