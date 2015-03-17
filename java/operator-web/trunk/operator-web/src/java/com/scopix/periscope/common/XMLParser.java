package com.scopix.periscope.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import com.scopix.periscope.model.Corporate;
import com.scopix.periscope.model.Equivalence;
import com.scopix.periscope.model.Office;

/**
 * Clase encargada de la lectura del archivo appParameters.xml para obtener la parametrización de los clientes de la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "static-access", "unchecked" })
public class XMLParser {

    private static XMLParser instance;
    private static Logger log = Logger.getLogger(XMLParser.class);

    /**
     * Obtiene instancia única de la clase
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @return XMLParser instancia de la clase
     * @date 25/03/2013
     */
    public static XMLParser getInstance() {
        if (instance == null) {
            instance = new XMLParser();
        }
        return instance;
    }

    public void setInstance(XMLParser instance) {
        this.instance = instance;
    }

    /**
     * Lectura del archivo appParameters.xml
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @return List<Corporate>
     * @date 25/03/2013
     */
    public List<Corporate> parseAppCorporates() {
        log.info("start");
        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.push(new ArrayList<Corporate>());
            digester.addObjectCreate("*/BusinessServices/Corporate", Corporate.class);
            digester.addSetNestedProperties("*/BusinessServices/Corporate");
            digester.addSetProperties("*/BusinessServices/Corporate");
            digester.addSetNext("*/BusinessServices/Corporate", "add");

            ClassPathResource res = new ClassPathResource("appParameters.xml");

            return (List<Corporate>) digester.parse(res.getInputStream());

        } catch (IOException ex) {
            log.error(ex);
        } catch (SAXException ex) {
            log.error(ex);
        }
        log.info("end");
        return null;
    }

    /**
     * Lectura del archivo appParameters.xml
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @return List<Office>
     * @date 23/07/2014
     */
    public List<Office> parseAppOffices() {
        log.info("start");
        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.push(new ArrayList<Office>());
            digester.addObjectCreate("*/BusinessServices/Office", Office.class);
            digester.addSetNestedProperties("*/BusinessServices/Office", "equivalences", null);
            // digester.addSetProperties("*/BusinessServices/Office", "equivalences" , null); cuando recibe atributos
            digester.addSetNext("*/BusinessServices/Office", "add");

            digester.addObjectCreate("*/BusinessServices/Office/equivalences", ArrayList.class);
            digester.addSetNext("*/BusinessServices/Office/equivalences", "setEquivalences");

            digester.addObjectCreate("*/BusinessServices/Office/equivalences/equivalence", Equivalence.class);
            digester.addSetNestedProperties("*/BusinessServices/Office/equivalences/equivalence");
            // digester.addSetProperties("*/BusinessServices/Office/equivalences/equivalence");
            digester.addSetNext("*/BusinessServices/Office/equivalences/equivalence", "add");

            ClassPathResource res = new ClassPathResource("appParameters.xml");

            return (List<Office>) digester.parse(res.getInputStream());

        } catch (IOException ex) {
            log.error(ex);
        } catch (SAXException ex) {
            log.error(ex);
        }
        log.info("end");
        return null;
    }
}