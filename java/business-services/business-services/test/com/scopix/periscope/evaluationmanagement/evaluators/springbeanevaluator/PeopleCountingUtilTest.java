/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import com.scopix.periscope.JCIFSUtil;
import java.io.File;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class PeopleCountingUtilTest {

    private static File fileP1;
    private static File fileP2;
    private static JCIFSUtil fSUtil;

    public static File getFileP1() {
        return fileP1;
    }

    public static void setFileP1(File aFileP1) {
        fileP1 = aFileP1;
    }

    public static File getFileP2() {
        return fileP2;
    }

    public static void setFileP2(File aFileP2) {
        fileP2 = aFileP2;
    }

    public static JCIFSUtil getfSUtil() {
        return fSUtil;
    }

    public static void setfSUtil(JCIFSUtil afSUtil) {
        fSUtil = afSUtil;
    }

    public PeopleCountingUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            System.out.println("PeopleCountingUtilTest");
            //creamos los archivos se seran usados para las pruebas
            setfSUtil(new JCIFSUtil());
            jcifs.Config.setProperty("jcifs.netbios.wins", "127.0.0.1");
            //The user name
            jcifs.Config.setProperty("jcifs.smb.client.username", "periscope.data");
            //The password
            jcifs.Config.setProperty("jcifs.smb.client.password", "p3r1sc0p3");
            //The chache policy
            jcifs.Config.setProperty("jcifs.netbios.cachePolicy", "-1");
            //The log level
            jcifs.Config.setProperty("jcifs.util.loglevel", "4");
            StringBuffer p = new StringBuffer();
            p.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            p.append("<root>");
            p.append("<value date=\"2010-08-01 00:00\" valuein=\"0\" valueout=\"1\"/>");
            p.append("<value date=\"2010-08-01 00:15\" valuein=\"2\" valueout=\"3\"/>");
            p.append("<value date=\"2010-08-01 00:30\" valuein=\"4\" valueout=\"5\"/>");
            p.append("<value date=\"2010-08-01 00:45\" valuein=\"6\" valueout=\"7\"/>");
            p.append("</root>");
            setFileP1(File.createTempFile("scopix_test", ".xml"));
            FileUtils.writeStringToFile(getFileP1(), p.toString(), "UTF-8");
            StringBuffer p2 = new StringBuffer();
            p2.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            p2.append("<root>");
            p2.append("<value date=\"2010-08-01 00:00\" valuein=\"0\" valueout=\"1\"/>");
            p2.append("<value date2=\"2010-08-01 00:00\"  valuein2=\"2\" valueout2=\"3\">");
            p2.append("<value date=\"2010-08-01 00:30\" valueout=\"5\"/>");
            p2.append("</root>");
            setFileP2(File.createTempFile("scopix_test", ".xml"));
            FileUtils.writeStringToFile(getFileP2(), p2.toString(), "UTF-8");
        } catch (IOException e) {
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        try {
            //eliminamos los file del sistema
            FileUtils.forceDelete(getFileP1());
            FileUtils.forceDelete(getFileP2());
        } catch (IOException e) {
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParserXmlPeopleCounting() throws Exception {
        System.out.println("parserXmlPeopleCounting");
        InputStream is = null;
        try {
            is = new FileInputStream(getFileP1());
            List result = PeopleCountingUtil.parserXmlPeopleCounting(is);
            assertEquals(result.size(), 4);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Test
    public void testParserXmlPeopleCounting2() throws Exception {
        System.out.println("parserXmlPeopleCounting2");
        InputStream is = null;
        try {

            List result = PeopleCountingUtil.parserXmlPeopleCounting(is);
            assertEquals(result, null);
        } catch (ScopixException e) {
            //se espera esta ecepcion ya que el no existe xml
            if (e.getCause() instanceof IOException) {
                assertTrue(true);
            } else {
                fail("Se esperaba IOException");
            }
        }
        //aqui se debe ejecutar una excepcion
    }

    @Test
    public void testParserXmlPeopleCounting3() throws Exception {
        System.out.println("parserXmlPeopleCounting3");
        InputStream is = new FileInputStream(getFileP2());
        try {
            List result = PeopleCountingUtil.parserXmlPeopleCounting(is);
            assertEquals(result, null);
        } catch (ScopixException e) {
            //se espera esta ecepcion ya que el xml esta mal constituido
            if (e.getCause() instanceof SAXException) {
                assertTrue(true);
            } else {
                fail("Se esperaba SAXException");
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
        //aqui se debe ejecutar una excepcion
    }


    public void testParserXmlPeopleCounting4() throws Exception {
        System.out.println("parserXmlPeopleCounting4");

        try {
            getfSUtil().createFileSmb(getFileP2(), "//127.0.0.1/periscope.data/xml2.xml");
            PeopleCountingUtil.setJcifsUtil(getfSUtil());
            List result = PeopleCountingUtil.parserXmlPeopleCounting("//127.0.0.1/periscope.data/xml2.xml");
            assertEquals(result, null);
        } catch (ScopixException e) {
            //se espera esta ecepcion ya que el xml esta mal constituido
            if (e.getCause() instanceof SAXException) {
                assertTrue(true);
            } else {
                fail("Se esperaba SAXException");
            }
        }
        //aqui se debe ejecutar una excepcion
    }
}
