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
 *  ScopixParseUtils.java
 * 
 *  Created on 10-07-2014, 12:54:39 PM
 * 
 */
package com.scopix.periscope;

import com.scopix.periscope.vadaro.VadaroEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author Nelson
 */
public class ScopixParseUtils {
    
    private static final Logger log = Logger.getLogger(ScopixParseUtils.class);

    /**
     * Parse a XML in Object VadaroEvent 
     * Example xml
     * <pre>
     * {@code 
     * <?xml version="1.0" encoding="UTF-8" standalone="no"?>
     *   <root>
     *     <event abandoned="1" cameraName="Queue0" entered="0" exited="0" length="0" 
     *          service="QueueCounting" serviceTime="0" time="2014-07-09 19:13:46" waitTime="0"/>
     *   </root>
     *}
     * </pre>
     * @param pathFile path the file convert to in Object
     * @return VadaroEvent Object convertions 
     */
    public static VadaroEvent parseEvent(String pathFile) {
        VadaroEvent event = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        try {
            Locale locale = Locale.getDefault();
            DateLocaleConverter converter = new DateLocaleConverter(locale, pattern);
            converter.setLenient(true);
            ConvertUtils.register(converter, java.util.Date.class);
            
            Digester dg = new Digester();
            
            dg.setValidating(false);
            dg.push(new VadaroEvent());
            dg.addSetNestedProperties("*/event");
            dg.addSetProperties("*/event");
            event = (VadaroEvent) dg.parse(new FileReader(new File(pathFile)));
        } catch (IOException e) {
            log.error(e, e);
        } catch (SAXException e) {
            log.error(e, e);
        }
        return event;
    }
}
