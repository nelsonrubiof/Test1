/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author nelson
 */
@Ignore
public class JCIFSUtilTest {

    private static JCIFSUtil fSUtil;
    private static File fileXml;
    private static File fileTxt;

    public static JCIFSUtil getfSUtil() {
        return fSUtil;
    }

    public static void setfSUtil(JCIFSUtil afSUtil) {
        fSUtil = afSUtil;
    }

    public static File getFileXml() {
        return fileXml;
    }

    public static void setFileXml(File aFileXml) {
        fileXml = aFileXml;
    }

    public static File getFileTxt() {
        return fileTxt;
    }

    public static void setFileTxt(File aFileTxt) {
        fileTxt = aFileTxt;
    }

    public JCIFSUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("JCIFSUtilTest");
        setfSUtil(new JCIFSUtil());
        //The ip address
        jcifs.Config.setProperty("jcifs.netbios.wins", "127.0.0.1");
        //The user name
        jcifs.Config.setProperty("jcifs.smb.client.username", "periscope.data");
        //The password
        jcifs.Config.setProperty("jcifs.smb.client.password", "p3r1sc0p3");
        //The chache policy
        jcifs.Config.setProperty("jcifs.netbios.cachePolicy", "-1");
        //The log level
        jcifs.Config.setProperty("jcifs.util.loglevel", "4");

        //creamos archivo de pruebas
        StringBuilder p = new StringBuilder();
        p.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        p.append("<root>");
        p.append("<value date=\"2010-08-01 00:00\" valuein=\"0\" valueout=\"1\"/>");
        p.append("<value date=\"2010-08-01 00:15\" valuein=\"2\" valueout=\"3\"/>");
        p.append("<value date=\"2010-08-01 00:30\" valuein=\"4\" valueout=\"5\"/>");
        p.append("<value date=\"2010-08-01 00:45\" valuein=\"6\" valueout=\"7\"/>");
        p.append("</root>");
        setFileXml(File.createTempFile("scopix_test", ".xml"));
        FileUtils.writeStringToFile(getFileXml(), p.toString(), "UTF-8");
        setFileTxt(File.createTempFile("scopix_test", ".txt"));
        getfSUtil().createFileSmb(getFileTxt(), "//127.0.0.1/periscope.data/test.txt");

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        getfSUtil().deleteFile("//127.0.0.1/periscope.data/test.txt");
        getfSUtil().deleteFile("//127.0.0.1/periscope.data/test2.xml");
        FileUtils.forceDelete(getFileTxt());
        FileUtils.forceDelete(getFileXml());
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetFileSmb() throws Exception {
        System.out.println("getFileSmb");
        String absolutePath = "//127.0.0.1/periscope.data/test.txt";
        Map<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("size", Integer.valueOf(0));
        Map result = getfSUtil().getFileSmb(absolutePath);
        assertEquals(((Integer) expResult.get("size")).intValue(), ((Integer) result.get("size")).intValue());
        try {
            ((InputStream) result.get("is")).close();
        } catch (IOException e) {
            //no hacemos nada
        }
    }

    @Test
    public void testCreateFileSmb() throws Exception {
        System.out.println("createFileSmb");
        String pathDestino = "//127.0.0.1/periscope.data/test2.xml";
        getfSUtil().createFileSmb(getFileXml(), pathDestino);
        Map result = getfSUtil().getFileSmb(pathDestino);
        assertNotNull(result.get("size"));
        try {
            ((InputStream) result.get("is")).close();
        } catch (IOException e) {
            //no hacemos nada
        }

    }

    @Test
    public void testCreateFileSmb2() throws Exception {
        System.out.println("createFileSmb2");
        String pathDestino = "//127.0.0.1/periscope.data/test.txt";
        getfSUtil().createFileSmb(getFileTxt(), pathDestino);
        //no debe ocurrir nada que que si el archivo existe este debe ser borrado antes
        Map result = getfSUtil().getFileSmb(pathDestino);
        assertNotNull(result.get("size"));
        try {
            ((InputStream) result.get("is")).close();
        } catch (IOException e) {
            //no hacemos nada
        }

    }

    @Test
    public void testMkDirSmb() throws Exception {
        System.out.println("mkDirSmb");
        String pathDirectorio = "//127.0.0.1/periscope.data/kk";
        getfSUtil().mkDirSmb(pathDirectorio);
        getfSUtil().createFileSmb(getFileXml(), pathDirectorio + "/kk.xml");
        Map result = getfSUtil().getFileSmb(pathDirectorio + "/kk.xml");
        assertNotNull(result.get("size"));
        try {
            ((InputStream) result.get("is")).close();
        } catch (IOException e) {
            //no hacemos nada
        }
    }

    @Test
    public void testMkDirSmb2() throws Exception {
        System.out.println("mkDirSmb2");
        String pathDirectorio = "//127.0.0.1/periscope.data/kk";
        getfSUtil().mkDirSmb(pathDirectorio);
        //esperamos que no ocurra ningun error ya que este directorio ya existe
        assertTrue(true);
    }

    @Test
    public void testDeleteFile() throws Exception {
        System.out.println("deleteFile");
        String path = "//127.0.0.1/periscope.data/kk/";
        getfSUtil().deleteFile(path);
        try {
            Map result = getfSUtil().getFileSmb(path + "kk.xml");
            fail("No se borro el archivo");
        } catch (ScopixException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testDeleteFile2() throws Exception {
        System.out.println("deleteFile2");
        String path = "//127.0.0.1/periscope.data/kk/";
        getfSUtil().deleteFile(path);
        //no debe existir excepcion si el directorio no existe
        assertTrue(true);

    }
}
