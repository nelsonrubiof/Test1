/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.nextlevel.utilities;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.commands.ExtractEvidenceThread;
import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class GetClipFileThread extends ExtractEvidenceThread {

    private static Logger log = Logger.getLogger(GetClipFileThread.class);
    private String file;
    private String storeName;
    private String urlGateway;
    private String user;
    private String pwd;

    public GetClipFileThread(String nameNextLevel, String storeName, String urlGateway, String user, String pwd) {
        this.setName("GetClipFileThread_" + nameNextLevel);
        this.file = nameNextLevel;
        this.storeName = storeName;
        this.urlGateway = urlGateway;
        this.user = user;
        this.pwd = pwd;
    }

    @Override
    @SuppressWarnings("static-access")
    public void run() {
        log.info("start [file: " + file + "]");
        try {
            //Recuperamos el file desde NextLevel
            File tmp = SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class).getNextLevel(urlGateway, user, pwd).getClip(file, storeName);

            if (tmp == null) {
                log.error("No se recibe file solicitado");
            } else {
                SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class).nextLevelVideoReady(file, tmp);
            }
        } catch (Exception e) {
            log.error("No es posible terminar proceso " + e, e);
        }
        log.info("end");
    }
//    public Properties getProp() {
//        if (prop == null) {
//            try {
//                ClassPathResource res = new ClassPathResource("system.properties");
//                prop = new Properties();
//                prop.load(res.getInputStream());
//            } catch (IOException e) {
//                prop = null;
//                log.error("No es posible cargar properties " + e, e);
//            }
//        }
//        return prop;
//    }
//
//    public void setProp(Properties prop) {
//        this.prop = prop;
//    }
}
