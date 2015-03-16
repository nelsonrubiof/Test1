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
 *  NextLevelParserTest.java
 * 
 *  Created on 08-09-2011, 11:21:00 AM
 * 
 */
package com.scopix.periscope;

import com.scopix.periscope.nextlevel.Camera;
import com.scopix.periscope.nextlevel.DeviceNetworkLookup;
import com.scopix.periscope.nextlevel.NLSSEvent;
import com.scopix.periscope.nextlevel.Site;
import com.scopix.periscope.nextlevel.Person;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author nelson
 */
public class NextLevelParserTest {

    public NextLevelParserTest() {
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
    public void testParsePerson() {
        System.out.println("parsePerson");
        Person expResult = Mockito.mock(Person.class);
        //seteamos la respusta esperada para algun metodo
        Mockito.when(expResult.getCustomerID()).thenReturn("b580a309-64d1-ee3f-b61f-3dedf381f620");
        Mockito.when(expResult.getSiteID()).thenReturn("host0000-next-leve-lsec-uritysystems");
        //Avisamos al mock que esta completo        
        Person result = NextLevelParser.parsePerson(xmlPerson);
        //EasyMock.verify(expResult);
        Assert.assertEquals(expResult.getCustomerID(), result.getCustomerID());
        Assert.assertEquals(expResult.getSiteID(), result.getSiteID());
    }

    @Test
    public void testParserSite() {
        System.out.println("parserSite");
        List<Site> expResult = new ArrayList<Site>();
        Site site = Mockito.mock(Site.class);
        Mockito.when(site.getSiteID()).thenReturn("93c56b03-aa0d-2eac-e37f-e7aa14c422fe");
        
        expResult.add(site);

        List<Site> result = NextLevelParser.parserSite(xmlSite);
        Assert.assertEquals(expResult.get(0).getSiteID(), result.get(0).getSiteID());
    }

    @Test
    public void testParserDeviceNetworkLookups() {
        System.out.println("parserDeviceNetworkLookups");
        List<DeviceNetworkLookup> expResult = new ArrayList<DeviceNetworkLookup>();
        DeviceNetworkLookup deviceNetworkLookup = Mockito.mock(DeviceNetworkLookup.class);
        Mockito.when(deviceNetworkLookup.getDeviceWebPort()).thenReturn(50013);
        
        expResult.add(deviceNetworkLookup);

        List<DeviceNetworkLookup> result = NextLevelParser.parserDeviceNetworkLookups(xmlDeviceNetworkLookup);
        Assert.assertEquals(expResult.get(0).getDeviceWebPort(), result.get(0).getDeviceWebPort());
    }

    @Test
    public void testParserCameras() {
        System.out.println("parserCameras");
        List<Camera> expResult = new ArrayList<Camera>();
        Camera camera = Mockito.mock(Camera.class);
        Mockito.when(camera.getDeviceID()).thenReturn("d22cb8f1-33a7-429d-a61b-cfa5abd04a47");
        
        expResult.add(camera);

        List<Camera> result = NextLevelParser.parserCameras(xmlCameras);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(expResult.get(0).getDeviceID(), result.get(0).getDeviceID());
    }

    @Test
    public void testParserNLSSEvent() {
        System.out.println("parserNLSSEvent1");
        NLSSEvent expResult = Mockito.mock(NLSSEvent.class);

        //seteamos la respusta esperada para algun metodo
        Mockito.when(expResult.getEventType()).thenReturn(13);
        NLSSEvent result = NextLevelParser.parserNLSSEvent(xmlEvent1);
        //EasyMock.verify(expResult);
        Assert.assertEquals(expResult.getEventType(), result.getEventType());
    }

    @Test
    public void testParserNLSSEvent2()  {
        System.out.println("parserNLSSEvent2");
        NLSSEvent expResult = Mockito.mock(NLSSEvent.class);

        //seteamos la respusta esperada para algun metodo
        Mockito.when(expResult.getEventType()).thenReturn(44);
        NLSSEvent result = NextLevelParser.parserNLSSEvent(xmlEvent2);
        //EasyMock.verify(expResult);
        Assert.assertEquals(expResult.getEventType(), result.getEventType());
    }
    
    
    @Test
    public void testParserNLSS3Event() {
        String xml = "";
        List expResult = null;
        List result = NextLevelParser.parserNLSS3Event(xml);
        Assert.assertEquals(expResult, result);
        
    }

    /**
     * definicion de xml de pruebas
     */
    static final String xmlPerson = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<Person xmlns=\"http://www.nlss.com/Gateway\" >"
            + "	 <personID>master00-0000-0000-0000-000000000000</personID>"
            + "	 <employeeNumber></employeeNumber>"
            + "	 <siteID>host0000-next-leve-lsec-uritysystems</siteID>"
            + "	 <customerID>b580a309-64d1-ee3f-b61f-3dedf381f620</customerID>"
            + "	 <prefix></prefix>"
            + "	 <suffix></suffix>"
            + "	 <firstName>Master</firstName>"
            + "	 <middleName></middleName>"
            + "	 <lastName>Master</lastName>"
            + "	 <preferredName></preferredName>"
            + "	 <title></title>"
            + "	 <location></location>"
            + "	 <department></department>"
            + "	 <supervisor></supervisor>"
            + "	 <personTypeID>0</personTypeID>"
            + "	 <personStatusID>0</personStatusID>"
            + "	 <PIN></PIN>"
            + "	 <ADA></ADA>"
            + "	 <enableTrace></enableTrace>"
            + "	 <notes></notes>"
            + "	 <lastModified>0</lastModified>"
            + "	 <lastModifiedBy>NA</lastModifiedBy>"
            + "	 <cardCount>0</cardCount>"
            + "	 <primaryCredential>0</primaryCredential>"
            + "	 <userID>eyal.shats@scopixsolutions.com</userID>"
            + "	 <password>ultrea</password>"
            + "	 <userTypeID>0</userTypeID>"
            + "	 <passwordKey>0</passwordKey>"
            + "	 <passwordKeyCreated>0</passwordKeyCreated>"
            + "	 <rms>1</rms>"
            + "</Person>";
    static final String xmlSite = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<RecordList  xmlns=\"http://www.nlss.com/Gateway\" >"
            + "<Site>"
            + "        <siteID>93c56b03-aa0d-2eac-e37f-e7aa14c422fe</siteID>"
            + "        <customerID>b580a309-64d1-ee3f-b61f-3dedf381f620</customerID>"
            + "        <siteName>SCOPIX</siteName>"
            + "        <siteAddress1>1350 Bayshore Hwy</siteAddress1>"
            + "        <siteAddress2>Suite 665</siteAddress2>"
            + "        <siteAddressCity>Burlingame</siteAddressCity>"
            + "        <siteAddressState>CA</siteAddressState>"
            + "        <siteAddressCountry>229</siteAddressCountry>"
            + "        <siteAddressZipCode>94010</siteAddressZipCode>"
            + "        <siteCode>210</siteCode>"
            + "        <timezoneInfo>America/Los_Angeles</timezoneInfo>"
            + "        <ntpServerIp>ntp.ubuntu.com</ntpServerIp>"
            + "        <ntpSync>1</ntpSync>"
            + "        <coordX>300.0000000</coordX>"
            + "        <coordY>386.0000000</coordY>"
            + "        <rms>1</rms>"
            + "        <status>0</status>"
            + "</Site>"
            + "</RecordList>";
    static final String xmlDeviceNetworkLookup = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<RecordList xmlns=\"http://www.nlss.com/Gateway\" >"
            + "<DeviceNetworkLookup>"
            + "        <gatewayID>d863523a-33f9-11e0-9b4b-00012ebc4f48</gatewayID>"
            + "        <deviceName>nlss-GW500-00012EBC4F48</deviceName>"
            + "        <firmwareVersion>2.2.16330</firmwareVersion>"
            + "        <customerID>b580a309-64d1-ee3f-b61f-3dedf381f620</customerID>"
            + "        <deviceIp>nextls.net</deviceIp>"
            + "        <deviceWebPort>50013</deviceWebPort>"
            + "        <deviceRtmpPort>60013</deviceRtmpPort>"
            + "        <dataKey></dataKey>"
            + "        <status>1</status>"
            + "</DeviceNetworkLookup>"
            + "</RecordList>";
    static final String xmlCameras = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<RecordList  xmlns=\"http://www.nlss.com/Gateway\" >"
            + "<Camera>"
            + "        <deviceID>d22cb8f1-33a7-429d-a61b-cfa5abd04a47</deviceID>"
            + "        <deviceName>AXIS-00408CB20953</deviceName>"
            + "        <deviceDescription>AXIS-M3203-IP: 192.168.1.55</deviceDescription>"
            + "        <deviceLocation></deviceLocation>"
            + "        <systemContact>http://www.axis.com/</systemContact>"
            + "        <deviceModel>AXIS-M3203</deviceModel>"
            + "        <serialNumber>00408CB20953</serialNumber>"
            + "        <macAddress>00:40:8C:B2:09:53</macAddress>"
            + "        <firmwareVersion>N/A</firmwareVersion>"
            + "        <firmwareReleasedDate>3617566116562547501</firmwareReleasedDate>"
            + "        <logicVersion></logicVersion>"
            + "        <logicReleasedDate>142707814118482228</logicReleasedDate>"
            + "        <bootVersion></bootVersion>"
            + "        <bootReleasedDate>120259870723</bootReleasedDate>"
            + "        <rescueVersion></rescueVersion>"
            + "        <rescueReleasedDate>133144969219</rescueReleasedDate>"
            + "        <hardwareVersion></hardwareVersion>"
            + "        <systemObjectID></systemObjectID>"
            + "        <realDeviceID></realDeviceID>"
            + "        <connectionState>0</connectionState>"
            + "        <adminState>2</adminState>"
            + "        <coordX>0.0000000</coordX>"
            + "        <coordY>0.0000000</coordY>"
            + "        <coordZ>0.0000000</coordZ>"
            + "        <isPTZCamera>0</isPTZCamera>"
            + "        <discoveredIPAddress>192.168.1.55</discoveredIPAddress>"
            + "        <defaultUsername></defaultUsername>"
            + "        <defaultPassword></defaultPassword>"
            + "        <assignedGatewayID>d863523a-33f9-11e0-9b4b-00012ebc4f48</assignedGatewayID>"
            + "        <primaryChannelID>-1</primaryChannelID>"
            + "        <customStreamURL></customStreamURL>"
            + "        <customStreamType>0</customStreamType>"
            + "        <isMulticastEnabled>0</isMulticastEnabled>"
            + "        <activeStreams>0</activeStreams>"
            + "        <activeAnalytics>0</activeAnalytics>"
            + "        <activeForensics>0</activeForensics>"
            + "        <activeRecordings>0</activeRecordings>"
            + "        <isInputPort>0</isInputPort>"
            + "        <isOutputPort>0</isOutputPort>"
            + "</Camera>"
            + "<Camera>"
            + "        <deviceID>1a16c98b-6e12-78fb-24c2-36f3527159d8</deviceID>"
            + "        <deviceName>Axis-Burlingame</deviceName>"
            + "        <deviceDescription>Axis-Burlingame</deviceDescription>"
            + "        <deviceLocation></deviceLocation>"
            + "        <systemContact></systemContact>"
            + "        <deviceModel>RTSP</deviceModel>"
            + "        <serialNumber></serialNumber>"
            + "        <macAddress>00:00:00:00:00:00</macAddress>"
            + "        <firmwareVersion></firmwareVersion>"
            + "        <firmwareReleasedDate>0</firmwareReleasedDate>"
            + "        <logicVersion></logicVersion>"
            + "        <logicReleasedDate>0</logicReleasedDate>"
            + "        <bootVersion></bootVersion>"
            + "        <bootReleasedDate>0</bootReleasedDate>"
            + "        <rescueVersion></rescueVersion>"
            + "        <rescueReleasedDate>0</rescueReleasedDate>"
            + "        <hardwareVersion></hardwareVersion>"
            + "        <systemObjectID></systemObjectID>"
            + "        <realDeviceID></realDeviceID>"
            + "        <connectionState>1</connectionState>"
            + "        <adminState>1</adminState>"
            + "        <coordX>0.0000000</coordX>"
            + "        <coordY>0.0000000</coordY>"
            + "        <coordZ>0.0000000</coordZ>"
            + "        <isPTZCamera>0</isPTZCamera>"
            + "        <discoveredIPAddress>192.168.1.55</discoveredIPAddress>"
            + "        <defaultUsername>root</defaultUsername>"
            + "        <defaultPassword>scpxgene</defaultPassword>"
            + "        <assignedGatewayID>d863523a-33f9-11e0-9b4b-00012ebc4f48</assignedGatewayID>"
            + "        <primaryChannelID>0</primaryChannelID>"
            + "        <customStreamURL>rtsp://192.168.1.55:554/axis-media/media.amp?</customStreamURL>"
            + "        <customStreamType>1</customStreamType>"
            + "        <isMulticastEnabled>0</isMulticastEnabled>"
            + "        <activeStreams>1</activeStreams>"
            + "        <activeAnalytics>0</activeAnalytics>"
            + "        <activeForensics>0</activeForensics>"
            + "        <activeRecordings>1</activeRecordings>"
            + "        <isInputPort>0</isInputPort>"
            + "        <isOutputPort>0</isOutputPort>"
            + "</Camera>"
            + "</RecordList>";
    static final String xmlEvent1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<NLSSEvent xmlns:nc=\"http://www.nlss.com/NLSSCommands\" xmlns=\"http://www.nlss.com/Gateway\">"
            + "   <eventID>4d3eb53e-1f91-42be-af77-f3fd4bb4d155</eventID>"
            + "   <customerID>b580a309-64d1-ee3f-b61f-3dedf381f620</customerID>"
            + "   <siteID>93c56b03-aa0d-2eac-e37f-e7aa14c422fe</siteID>"
            + "   <eventTime>1315506289914824</eventTime>"
            + "   <eventCategory>0</eventCategory>"
            + "   <eventType>13</eventType>"
            + "   <eventSeverityID>6</eventSeverityID>"
            + "   <eventDescription>Axis-Burlingame Stream 0 - VIDEO/AUDIO</eventDescription>"
            + "   <eventResource>1a16c98b-6e12-78fb-24c2-36f3527159d8</eventResource>"
            + "   <displayUI>1</displayUI>"
            + "   <payload><![CDATA[/var/www/nlss/images/clips/1a16c98b-6e12-78fb-24c2-36f3527159d8_1314702550000000_1314702930000000.flv,7aaba48b-eb60-ec79-0abb-67e7c56f9b6a]]></payload>"
            + "</NLSSEvent>";
    static final String xmlEvent2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<NLSSEvent xmlns:nc=\"http://www.nlss.com/NLSSCommands\" xmlns=\"http://www.nlss.com/Gateway\">"
            + "   <eventID>dc4760ea-2583-b663-18c2-99987da26f87</eventID>"
            + "   <customerID>b580a309-64d1-ee3f-b61f-3dedf381f620</customerID>"
            + "   <siteID>93c56b03-aa0d-2eac-e37f-e7aa14c422fe</siteID>"
            + "   <eventTime>1315509261180473</eventTime>"
            + "   <eventCategory>6</eventCategory>"
            + "   <eventType>44</eventType>"
            + "   <eventSeverityID>8</eventSeverityID>"
            + "   <eventDescription>eyal.shats@scopixsolutions.com</eventDescription>"
            + "   <eventResource>master00-0000-0000-0000-000000000000</eventResource>"
            + "   <payload>eyal.shats@scopixsolutions.com</payload>"
            + "</NLSSEvent>";

}
