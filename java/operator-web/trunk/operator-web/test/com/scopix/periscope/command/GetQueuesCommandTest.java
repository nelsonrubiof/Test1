package com.scopix.periscope.command;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase de pruebas de com.scopix.periscope.command.GetQueuesCommand
 * 
 * @author Carlos
 */
public class GetQueuesCommandTest {
    
    public GetQueuesCommandTest() {
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
    public void testExecute() throws Exception {
//        //Instancia clase a probar
//        GetQueuesCommand command = new GetQueuesCommand();
//        //Crea mock objects
//        Class queuesWs = Mockito.mock(Class.class);
//        //Define comportamientos
//        Mockito.when(queuesWs.getOperatorQueues()).thenReturn(null);
//        //Invoca método a probar, verifica ejecución
//        Assert.assertTrue(command.execute(queuesWs).size()==1);
    }
    
//    @Test
//    public void testExecute2() throws Exception {
//        //Instancia clase a probar
//        GetQueuesCommand command = new GetQueuesCommand();
//        //Crea mock objects
//        Class queuesWs = Mockito.mock(Class.class);
//        ArrayOfOperatorQueueDTO arrayOpQueues = Mockito.mock(ArrayOfOperatorQueueDTO.class);
//        //Define comportamientos
//        Mockito.when(queuesWs.getOperatorQueues()).thenReturn(arrayOpQueues);
//        Mockito.when(arrayOpQueues.getOperatorQueueDTO()).thenReturn(null);
//        //Invoca método a probar, verifica ejecución
//        Assert.assertTrue(command.execute(queuesWs).size()==1);
//    }
//    
//    @Test
//    public void testExecute3() throws Exception {
//        //Instancia clase a probar
//        GetQueuesCommand command = new GetQueuesCommand();
//        //Crea mock objects
//        Class queuesWs = Mockito.mock(Class.class);
//        List<OperatorQueueDTO> lstOpQueueDTO = Mockito.mock(List.class);
//        ArrayOfOperatorQueueDTO arrayOpQueues = Mockito.mock(ArrayOfOperatorQueueDTO.class);
//        //Define comportamientos
//        Mockito.when(queuesWs.getOperatorQueues()).thenReturn(arrayOpQueues);
//        Mockito.when(arrayOpQueues.getOperatorQueueDTO()).thenReturn(lstOpQueueDTO);
//        Mockito.when(lstOpQueueDTO.isEmpty()).thenReturn(true);
//        //Invoca método a probar, verifica ejecución
//        Assert.assertTrue(command.execute(queuesWs).size()==1);
//    }
//    
//    @Test
//    public void testExecute4() throws Exception {
//        //Instancia clase a probar
//        GetQueuesCommand command = new GetQueuesCommand();
//        //Crea mock objects
//        Class queuesWs = Mockito.mock(Class.class);
//        JAXBElement jaxbElement = Mockito.mock(JAXBElement.class);
//        Iterator<OperatorQueueDTO> iterator = Mockito.mock(Iterator.class);
//        OperatorQueueDTO queueDTO = Mockito.mock(OperatorQueueDTO.class);
//        List<OperatorQueueDTO> lstOpQueueDTO = Mockito.mock(List.class);
//        //Define comportamientos
//        Mockito.when(queuesWs.getOperatorQueues()).thenReturn(arrayOpQueues);
//        Mockito.when(arrayOpQueues.getOperatorQueueDTO()).thenReturn(lstOpQueueDTO);
//        Mockito.when(lstOpQueueDTO.isEmpty()).thenReturn(false);
//        Mockito.when(lstOpQueueDTO.iterator()).thenReturn(iterator); 
//        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
//        Mockito.when(iterator.next()).thenReturn(queueDTO);
//        Mockito.when(queueDTO.getId()).thenReturn(jaxbElement);
//        Mockito.when(queueDTO.getName()).thenReturn(jaxbElement);
//        //Invoca método a probar, verifica ejecución
//        Assert.assertTrue(command.execute(queuesWs).size()==2);
//    }
//    
//    @Test
//    public void testExecute5() throws Exception {
//        //Instancia clase a probar
//        GetQueuesCommand command = new GetQueuesCommand();
//        //Crea mock objects
//        Class queuesWs = Mockito.mock(Class.class);
//        PeriscopeException periscopeException = Mockito.mock(PeriscopeException.class);
//
//        try{
//            //Define comportamientos
//            Mockito.when(queuesWs.getOperatorQueues()).thenThrow(periscopeException);
//            Mockito.when(periscopeException.getMessage()).thenReturn("message");
//            //Invoca método a probar
//            command.execute(queuesWs);
//        } catch (ScopixException ex) {
//            Assert.assertEquals(ex.getMessage(), "message");
//        }
//    }
//    
//    @Test
//    public void testExecute6() throws Exception {
//        //Instancia clase a probar
//        GetQueuesCommand command = new GetQueuesCommand();
//        //Invoca método a probar, verifica ejecución
//        Assert.assertTrue(command.execute(null).size()==1);
//    }
}