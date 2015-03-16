/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * VadaroParser.java
 * 
 * Created on 08-07-2014, 11:48:05 AM
 */
package com.scopix.periscope.vadaro;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.digester.Digester;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;

/**
 *
 * @author Nelson
 */
public class VadaroParser {

    private static final Logger log = Logger.getLogger(VadaroParser.class);

    public static VadaroEvent parserEvent(String xml, String timeZoneCamera) {
        log.info("start");
        String[] elements = new String[] { "Time", "Service", "Name", "Entered", "Exited", "Abandoned", "Length", "WaitTime",
                "ServiceTime" };
        String[] properties = new String[] { "time", "service", "name", "entered", "exited", "abandoned", "length", "waitTime",
                "serviceTime" };
        VadaroEvent event = null;
        if (xml != null && xml.length() > 0) {
            try {
                String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";
                Locale locale = Locale.getDefault();
                DateLocaleConverter converter = new DateLocaleConverter(locale, pattern);
                converter.setLenient(true);
                ConvertUtils.register(converter, java.util.Date.class);

                Digester dg = new Digester();

                dg.setValidating(false);
                dg.push(new VadaroEvent());
                dg.addSetNestedProperties("Event", elements, properties);
                dg.addSetProperties("Event", elements, properties);
                event = (VadaroEvent) dg.parse(new StringReader(xml));

                double dif = TimeZoneUtils.getDiffInHoursTimeZone("GMT0", timeZoneCamera);
                event.setTime(DateUtils.addHours(event.getTime(), (int) dif));
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

    public static String generateXml(VadaroEvent vadaroEvent) {
        String xml = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilderNew = docBuilderFactory.newDocumentBuilder();
            Document docNew = docBuilderNew.newDocument();
            Element root = docNew.createElement("root");
            docNew.appendChild(root);

            Element event = docNew.createElement("event");
            event.setAttribute("time", DateFormatUtils.format(vadaroEvent.getTime(), "yyyy-MM-dd HH:mm:ss"));
            event.setAttribute("service", vadaroEvent.getService());
            event.setAttribute("cameraName", vadaroEvent.getName());
            event.setAttribute("entered", vadaroEvent.getEntered().toString());
            event.setAttribute("exited", vadaroEvent.getExited().toString());
            event.setAttribute("abandoned", vadaroEvent.getAbandoned().toString());
            event.setAttribute("length", vadaroEvent.getLength().toString());
            event.setAttribute("waitTime", vadaroEvent.getWaitTime().toString());
            event.setAttribute("serviceTime", vadaroEvent.getServiceTime().toString());
            root.appendChild(event);

            DOMSource domSource = new DOMSource(docNew);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            xml = writer.toString();
        } catch (ParserConfigurationException | DOMException | TransformerFactoryConfigurationError | TransformerException e) {
            log.error(e, e);
        }
        return xml;
    }

}
