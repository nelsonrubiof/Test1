/*
 * 
 * Copyright © 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * DateConverter.java
 *
 * Created on 06-06-2008, 04:49:17 PM
 *
 */
package com.scopix.periscope.frameworksfoundation.struts2.xwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import ognl.DefaultTypeConverter;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
public class DateConverter extends DefaultTypeConverter {

    Logger log = Logger.getLogger(DateConverter.class);

    private static final String DEFAULT_FORMAT = "dd-MM-yyyy";

    private static final String PROPERTY_NAME = "xwork-conversion.properties";

    private static Properties prop = null;

    public DateConverter() {
        ClassPathResource res = new ClassPathResource(PROPERTY_NAME);
        try {
            prop = new Properties();
            prop.load(res.getInputStream());
        } catch (Exception e) {
            log.warn("[convertValue]", e);
        }
    }

    @Override
    public Object convertValue(Map ognlContext, Object value, Class toType) {
        Object date = null;
        String format = null;
        try {
            format = DEFAULT_FORMAT;
            if (prop != null && prop.contains("date.format")) {
                format = prop.getProperty("date.format");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(((String[]) value)[0]);
        } catch (ParseException ex) {
            log.warn("[convertValue]", ex);
        }
        return date;
    }
}
