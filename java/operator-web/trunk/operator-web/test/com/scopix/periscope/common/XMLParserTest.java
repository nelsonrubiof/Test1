package com.scopix.periscope.common;

import com.scopix.periscope.model.Corporate;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

/**
 * Clase de pruebas de com.scopix.periscope.common.XMLParser
 * 
 * @author Carlos
 */
public class XMLParserTest {
    
    public XMLParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetInstance2() {
        //Crea mock object
        XMLParser xmlParser2 = Mockito.mock(XMLParser.class);
        //Crea instancia de la clase a probar
        XMLParser xmlParser = new XMLParser();
        //Inyecta dependencia
        xmlParser.setInstance(xmlParser2);
        //Invoca método a probar
        Assert.assertNotNull(xmlParser.getInstance());
    }

    @Test
    public void testGetInstance() {
        //Crea instancia de la clase a probar
        XMLParser xmlParser = new XMLParser();
        xmlParser.setInstance(null);
        Assert.assertNotNull(xmlParser.getInstance());
    }

    /**
     * Lectura del archivo appParameters.xml
     * 
     * @author  carlos polo
     * @version 1.0.0
     * @since   6.0
     * @return  List<Corporate>
     * @date    25/03/2013
     */
    @Test
    public void testParseAppParameters() throws IOException, SAXException {
        //Crea instancia de la clase a probar
        XMLParser xmlParser = new XMLParser();
        //Crea mock object
        List<Corporate> lstCorporate = Mockito.mock(List.class);
        //Invoca método a probar
        lstCorporate = xmlParser.parseAppCorporates();
        Assert.assertNotNull(lstCorporate);
        Assert.assertFalse(lstCorporate.isEmpty());
    }
    
    /**
     * Lectura del archivo appParameters.xml
     * 
     * @author  carlos polo
     * @version 1.0.0
     * @since   6.0
     * @return  List<Corporate>
     * @date    25/03/2013
     */
    @Test
    public void testParseAppParameters2() throws IOException, SAXException {
        //Crea instancia de la clase a probar
        XMLParser xmlParser = new XMLParser();
        //Crea mock object
        List<Corporate> lstCorporate = Mockito.mock(List.class);
        //Invoca método a probar
        lstCorporate = xmlParser.parseAppCorporates();
        Assert.assertNotNull(lstCorporate);
        Assert.assertFalse(lstCorporate.isEmpty());
    }

    @Test
    public void testSetInstance() {
        //Crea instancia de la clase a probar
        XMLParser xmlParser = new XMLParser();
        //Crea mock object
        XMLParser xmlParser2 = Mockito.mock(XMLParser.class);
        //Inyecta dependencia
        xmlParser.setInstance(xmlParser2);
        //Invoca método a probar
        Assert.assertNotNull(xmlParser.getInstance());
    }
}