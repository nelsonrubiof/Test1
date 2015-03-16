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
 * FileManager.java
 *
 * Created on 20-08-2009, 04:07:08 PM
 *
 */
package com.scopix.periscope.evidencemanagement;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Cesar Abarza Suazo.
 * @version 1.0.0
 */
@SpringBean(rootClass = FileManager.class)
public class FileManager implements InitializingBean {

    private static Logger log = Logger.getLogger(FileManager.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            //Get and Set connection properties
            ClassPathResource res = new ClassPathResource("jcifs.properties");
            Properties prop = new Properties();
            prop.load(res.getInputStream());
            //The ip address
            jcifs.Config.setProperty("jcifs.netbios.wins", prop.getProperty("jcifs.netbios.wins"));
            //The user name
            jcifs.Config.setProperty("jcifs.smb.client.username", prop.getProperty("jcifs.smb.client.username"));
            //The password
            jcifs.Config.setProperty("jcifs.smb.client.password", prop.getProperty("jcifs.smb.client.password"));
            //The chache policy
            jcifs.Config.setProperty("jcifs.netbios.cachePolicy", prop.getProperty("jcifs.netbios.cachePolicy"));
            //The log level
            jcifs.Config.setProperty("jcifs.util.loglevel", prop.getProperty("jcifs.util.loglevel"));
        } catch (Exception e) {
            log.debug("[init] error " + e.getMessage());
        }
    }

    /**
     * Return the file itself. The map have two keys: "is" and "size". "is" have the file has inputstream and "size" have the file
     * size
     *
     * @param path the file to obtain
     * @return Map keys with file stream and file size
     * @throws MalformedURLException
     * @throws SmbException
     * @throws UnknownHostException
     */
    public Map getFile(String path) throws MalformedURLException, SmbException, UnknownHostException {
        log.debug("[getFile] start");
        SmbFile fileImage = new SmbFile("smb:" + path.replaceAll("\\\\", "/"));
        Integer size = (int) fileImage.length();
        log.debug("[getFile] length: " + size);
        InputStream inputStream = new SmbFileInputStream(fileImage);
        Map map = new HashMap();
        map.put("is", inputStream);
        map.put("size", size);

        log.debug("[getFile] end");
        return map;
    }
}