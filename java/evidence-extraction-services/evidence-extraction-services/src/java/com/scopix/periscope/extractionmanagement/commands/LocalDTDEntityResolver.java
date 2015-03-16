/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase para resolver la ruta del DTD asociado a un xml
 * se resuelve de forma local
 * @author nelson
 */
public class LocalDTDEntityResolver implements EntityResolver {

    private String pathDtd;

    public LocalDTDEntityResolver(String pathDtd) {
        this.pathDtd = pathDtd;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        ClassPathResource cpr = new ClassPathResource(getPathDtd());
        InputStream is = cpr.getInputStream();
        return new InputSource(is);

    }

    public String getPathDtd() {
        return pathDtd;
    }

    public void setPathDtd(String pathDtd) {
        this.pathDtd = pathDtd;
    }
}
