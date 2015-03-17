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
 *  NextLevelParser.java
 * 
 *  Created on 08-09-2011, 10:55:50 AM
 * 
 */
package com.scopix.periscope;

import com.scopix.periscope.nextlevel.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Utilitario que parsea todas las respuestas generadas desde NextLevel
 * @author nelson
 */
public class NextLevelParser {

    private static Logger log = Logger.getLogger(NextLevelParser.class);

    public static Person parsePerson(String xml) {
        log.info("start");
        Person person = null;
        try {
            Digester dg = new Digester();
            dg.setValidating(false);
            dg.push(new Person());
            dg.addSetNestedProperties("Person");
            dg.addSetProperties("Person");

            person = (Person) dg.parse(new StringReader(xml));
        } catch (IOException e) {
            log.error("IOException [xml:" + xml + "]" + e, e);
        } catch (SAXException e) {
            log.error("SAXException [xml:" + xml + "]" + e, e);
        }
        log.info("end");
        return person;
    }

    public static List<Site> parserSite(String xml) {
        log.info("start");
        List<Site> list = null;
        try {
            Digester dg = new Digester();
            dg.setValidating(false);
            dg.push(new ArrayList<Site>());
            dg.addObjectCreate("*/Site", Site.class);
            dg.addSetNestedProperties("*/Site");
            dg.addSetProperties("*/Site");
            dg.addSetNext("*/Site", "add");
            list = (List<Site>) dg.parse(new StringReader(xml));
        } catch (IOException e) {
            log.error("IOException [xml:" + xml + "]" + e, e);
        } catch (SAXException e) {
            log.error("SAXException [xml:" + xml + "]" + e, e);
        }
        log.info("end");
        return list;
    }

    public static List<DeviceNetworkLookup> parserDeviceNetworkLookups(String xml) {
        log.info("start");
        List<DeviceNetworkLookup> list = null;
        try {
            Digester dg = new Digester();
            dg.setValidating(false);
            dg.push(new ArrayList<DeviceNetworkLookup>());
            dg.addObjectCreate("*/DeviceNetworkLookup", DeviceNetworkLookup.class);
            dg.addSetNestedProperties("*/DeviceNetworkLookup");
            dg.addSetProperties("*/DeviceNetworkLookup");
            dg.addSetNext("*/DeviceNetworkLookup", "add");
            list = (List<DeviceNetworkLookup>) dg.parse(new StringReader(xml));
        } catch (IOException e) {
            log.error("IOException [xml:" + xml + "]" + e, e);
        } catch (SAXException e) {
            log.error("SAXException [xml:" + xml + "]" + e, e);
        }
        log.info("end");
        return list;
    }

    public static List<Camera> parserCameras(String xml) {
        log.info("start");
        List<Camera> list = null;
        if (xml != null && xml.length() > 0) {
            try {
                Digester dg = new Digester();
                dg.setValidating(false);
                dg.push(new ArrayList<Camera>());
                dg.addObjectCreate("*/Camera", Camera.class);
                dg.addSetNestedProperties("*/Camera");
                dg.addSetProperties("*/Camera");
                dg.addSetNext("*/Camera", "add");
                list = (List<Camera>) dg.parse(new StringReader(xml));
            } catch (IOException e) {
                log.error("IOException [xml:" + xml + "]" + e, e);
            } catch (SAXException e) {
                log.error("SAXException [xml:" + xml + "]" + e, e);
            }
        } else {
            log.debug("xml is empty");
            list = new ArrayList<Camera>();
        }
        log.info("end");
        return list;
    }

    public static NLSSEvent parserNLSSEvent(String xml) {
        log.info("start");
        NLSSEvent event = null;
        if (xml != null && xml.length() > 0) {
            try {
                Digester dg = new Digester();
                dg.setValidating(false);
                dg.push(new NLSSEvent());
                dg.addSetNestedProperties("NLSSEvent");
                dg.addSetProperties("NLSSEvent");
                event = (NLSSEvent) dg.parse(new StringReader(xml));
            } catch (IOException e) {
                log.error("IOException [xml:" + xml + "] " + e, e);
            } catch (SAXException e) {
                log.error("SAXException [xml:" + xml + "] " + e, e);
            }
        } else {
            log.debug("xml is empty");
        }
        log.info("end");
        return event;
    }
    
    /**
     * Realiza parsing de las respuestas XML q retorna la operación para la nueva
     * versión de NextLevel
     * 
     * @author  carlos polo
     * @param   xml
     * @version 3.0
     * @date    22-oct-2012
     */
    public static List<NLSSEvent3> parserNLSS3Event(String xml) {
        try {

            Digester dg = new Digester();
            dg.setValidating(false);
            dg.push(new ArrayList<NLSSEvent3>());
            dg.addObjectCreate("*/NLSSEvent", NLSSEvent3.class);
            dg.addSetNestedProperties("*/NLSSEvent");
            dg.addSetProperties("*/NLSSEvent");
            dg.addSetNext("*/NLSSEvent", "add");

            return (List<NLSSEvent3>) dg.parse(new StringReader(xml));

        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (SAXException e) {
            log.error("SAXException " + e, e);
        }

        return null;
    }
}
