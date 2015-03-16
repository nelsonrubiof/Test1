/*
 *
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *  VFSUtil.java
 *
 * Created on 26-08-2010, 13:00:00 PM
 */
package com.scopix.periscope;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.io.FilenameUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@SpringBean(rootClass = JCIFSUtil.class)
public class JCIFSUtil implements InitializingBean {

    private static Logger log = Logger.getLogger(JCIFSUtil.class);

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
            log.error("error " + e, e);
        }
    }

    /**
     * <pre>
     *  Levanta un  archivo desde SMB colocandolo en un map
     * </pre>
     *
     * @param absolutePath ruta absoluta
     * @return Map [String, Object] las llaves son is y size con el InputStream y el tamaño respectivo
     * @throws PeriscopeException Excepcion en caso de Error
     */
    public Map<String, Object> getFileSmb(String absolutePath) throws ScopixException {
        log.debug("absolutePath:" + absolutePath);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SmbFile fileImage = new SmbFile("smb:" + absolutePath.replaceAll("\\\\", "/"));
            Integer size = (int) fileImage.length();
            InputStream inputStream = new SmbFileInputStream(fileImage);
            map.put("is", inputStream);
            map.put("size", size);

        } catch (MalformedURLException e) {
            log.error("" + e, e);
            throw new ScopixException(e);
        } catch (SmbException e) {
            log.error("" + e, e);
            throw new ScopixException(e);
        } catch (UnknownHostException e) {
            log.error("" + e, e);
            throw new ScopixException(e);
        }
        return map;
    }

    /**
     * Crea un directorio especifico usando SMB
     *
     * @param pathDirectorio ruta del directorio a crear
     * @throws PeriscopeException Excepcion en caso de Error
     */
    public void mkDirSmb(String pathDirectorio) throws ScopixException {
        try {
            SmbFile file = new SmbFile("smb:" + pathDirectorio.replaceAll("\\\\", "/"));
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (MalformedURLException e) {
            log.error("" + e, e);
            throw new ScopixException(e);
        } catch (SmbException e) {
            log.error("" + e, e);
            throw new ScopixException(e);
        }

    }

    /**
     * Copia File en pathDestino usando SMB
     *
     * @param origen ruta del archivo de origen
     * @param pathDestino ruta de donde se almacenara el archivo
     * @throws PeriscopeException Excepcion en caso de Error
     */
    public void createFileSmb(File origen, String pathDestino) throws ScopixException {
        OutputStream os = null;
        InputStream is = null;
        try {
            String path = pathDestino.replaceAll("\\\\", "/");
            SmbFile smbFile = new SmbFile("smb:" + path);
            if (smbFile.exists()) {
                smbFile.delete();
            } else { //revisamos que exista el directorio para la recepcion de dicho archivo
                String directory = FilenameUtils.getFullPath(path);
                SmbFile smbDirectory = new SmbFile("smb:" + directory);
                if (!smbDirectory.exists()) {
                    smbDirectory.mkdirs();
                }

            }
            smbFile.createNewFile();
            os = new SmbFileOutputStream(smbFile);
            is = new FileInputStream(origen);
            byte[] array = new byte[2048];
            int b = 0;
            while (true) {
                b = is.read(array);
                if (b > 0) {
                    os.write(array, 0, b);
                } else {
                    break;
                }
            }
            os.flush();
        } catch (IOException e) {
            log.error("" + e, e);
            throw new ScopixException("", e);
        } finally {
            try {
                os.close();
            } catch (IOException e2) {
                log.error("" + e2);
            }
            try {
                is.close();
            } catch (IOException e2) {
                log.error("" + e2);
            }
        }

    }

    /**
     * Borra un archivo especifico para reconocer que es directorio lo que se desea borrar debe contener al final "/"
     *
     * @param path nombre completo del archivo, incluyendo ruta
     * @throws PeriscopeException Excepcion en caso de Error
     */
    public void deleteFile(String path) throws ScopixException {
        try {
            SmbFile smbFile = new SmbFile("smb:" + path.replaceAll("\\\\", "/"));
            if (smbFile.exists()) {
                smbFile.delete();
            }
        } catch (MalformedURLException e) {
            log.error("" + e, e);
            throw new ScopixException("", e);
        } catch (SmbException e) {
            log.error("" + e, e);
            throw new ScopixException("", e);
        }

    }
}
