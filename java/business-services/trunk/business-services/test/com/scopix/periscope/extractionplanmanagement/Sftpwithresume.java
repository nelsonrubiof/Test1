/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  Sftpwithresume.java
 * 
 *  Created on Aug 14, 2014, 5:31:15 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.scheduler.exception.FileManagerException;
import com.scopix.periscope.scheduler.fileManager.FileManager;
import com.scopix.periscope.scheduler.fileManager.FileManagerFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

/**
 *
 * @author Sebastian
 */
public class Sftpwithresume {
    
//     @Test
//    public void testFileTransmittion() throws FileNotFoundException, FileManagerException, IOException {
//        FileManager fileManager = FileManagerFactory.getFilemanager("SFTP2");
//        String path = new File("").getAbsolutePath();
//        fileManager.connectAndLogin("64.151.127.242", "periscope.data", "periscope");
//        try {
//            fileManager.putFileResuming(path + "\\catalog.pdf", "prueba/catalog.pdf");
//        } catch (Exception e) {
//            FileManager fileManager2 = FileManagerFactory.getFilemanager("SFTP2");
//            fileManager2.connectAndLogin("64.151.127.242", "periscope.data", "periscope");
//            fileManager2.putFileResuming(path + "\\catalog.pdf", "prueba/catalog.pdf");
//        } finally {
//            fileManager.disconnect();
//        }
//    }

    @Test
    public void deleteFileTransmittion() throws FileNotFoundException, FileManagerException, IOException {
        FileManager fileManager = FileManagerFactory.getFilemanager("SFTP2");
        String path = new File("").getAbsolutePath();
        fileManager.connectAndLogin("64.151.127.242", "periscope.data", "periscope");
        try {
            fileManager.deleteFile( "prueba/catalog.pdf");
        } catch (Exception e) {
            String test = null;
            e.printStackTrace();
        } finally {
            fileManager.disconnect();
        }
    }
}
