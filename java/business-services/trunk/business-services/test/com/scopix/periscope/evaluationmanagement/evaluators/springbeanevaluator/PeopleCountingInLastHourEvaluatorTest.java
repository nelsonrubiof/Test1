/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import mockup.ObserverMetricMock;
import mockup.GenericDAOMockup;

import org.apache.log4j.Logger;

import java.io.File;

import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.JCIFSUtil;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
@Ignore
public class PeopleCountingInLastHourEvaluatorTest {

    private static File file;
    private static Logger log = Logger.getLogger(PeopleCountingInLastHourEvaluatorTest.class);
    private static JCIFSUtil vfsUtil;

    public static File getFile() {
        return file;
    }

    public static void setFile(File aFile) {
        file = aFile;
    }

    public PeopleCountingInLastHourEvaluatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            System.out.println("PeopleCountingInLastHourEvaluatorTest");
            vfsUtil = new JCIFSUtil();
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

            vfsUtil.mkDirSmb("//127.0.0.1/periscope.data/corpTest/stTest/2010/08/01/");
            //FileUtils.forceMkdir(new File("/periscope.data/test/corpTest/stTest/2010/08/01/"));
            StringBuilder fileXml = new StringBuilder();
            fileXml.append("<?xml version='1.0' encoding='UTF-8'?>");
            fileXml.append("<root>");
            fileXml.append("<value date='2010-08-01 17:00' valueIn='0' valueout='1'/>");
            fileXml.append("<value date='2010-08-01 16:45' valuein='2' valueout='1'/>");
            fileXml.append("<value date='2010-08-01 16:15' valuein='4' valueout='1'/>");
            fileXml.append("<value date='2010-08-01 13:30' valuein='6' valueout='1'/>");
            fileXml.append("<value date='2010-08-01 16:30' valuein='6' valueout='1'/>");
            fileXml.append("</root>");
            setFile(File.createTempFile("scopix", ".xml"));
            FileUtils.writeStringToFile(getFile(), fileXml.toString(), "UTF-8");

            vfsUtil.createFileSmb(new File(getFile().getAbsolutePath()),
                                  "//127.0.0.1/periscope.data/corpTest/stTest/2010/08/01/test.xml");
//            vfsUtil.createFileSmb(new File("c:/test/glendale_out.xml"),
//                    "//127.0.0.1/periscope.data/corpTest/stTest/2010/12/05/test.xml");
        } catch (IOException e) {
            log.error("No es posible crear datos de prueba", e);
        } catch (Exception e) {
            log.error("No es posible crear datos de prueba " + e, e);
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        try {
            //JCIFSUtil vfsUtil = new JCIFSUtil();
            vfsUtil.deleteFile("//127.0.0.1/periscope.data/corpTest/");
            FileUtils.forceDelete(getFile());
        } catch (ScopixException e) {
            log.error("No es posible borrar el directorio de pruebas");
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    public void testEvaluate() throws Exception {
        System.out.println("evaluate");
        ObservedMetric om = ObserverMetricMock.genNewObservedMetric();
        GenericDAOMockup dao = new GenericDAOMockup();
        Integer pendingEvaluationId = 1;
        PeopleCountingInLastHourEvaluator instance = new PeopleCountingInLastHourEvaluator();
        //generar dao
        PeopleCountingUtil.setJcifsUtil(vfsUtil);
        instance.setDao(dao);
        instance.evaluate(om, pendingEvaluationId);
        //si llegamos a este punto significa que logramos ejecutar correctamente el test
        if (dao.getBusinessObject() instanceof EvidenceEvaluation) {
            assertEquals(((EvidenceEvaluation) dao.getBusinessObject()).getEvidenceResult().intValue(), 12);
        } else {
            fail("Se esperaba un objeto EvidenceEvaluation");
        }


    }

}
