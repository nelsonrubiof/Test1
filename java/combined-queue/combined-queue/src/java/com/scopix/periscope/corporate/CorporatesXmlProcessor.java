package com.scopix.periscope.corporate;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

/**
 * Retrieves corporate information from appParameters.xml file
 *
 * @version 1.0.0
 * @since 6.0
 */
public class CorporatesXmlProcessor {

    private static CorporatesXmlProcessor instance;
    private static Logger log = Logger.getLogger(CorporatesXmlProcessor.class);

    /**
     * Singleton
     *
     * @return getCorporatesCommand
     * @date 25/03/2013
     */
    public static CorporatesXmlProcessor getInstance() {
        if (instance == null) {
            instance = new CorporatesXmlProcessor();
        }
        return instance;
    }

    /**
     * singleton
     *
     * @param instance
     */
    public void setInstance(CorporatesXmlProcessor instance) {
        this.instance = instance;
    }

    /**
     * read and parse info from file
     *
     * @return List<Corporate>
     * @throws ScopixException 
     * @date 25/03/2013
     */
    public List<Corporate> execute() throws ScopixException {
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
            throw new ScopixException(ex.getMessage());
        } catch (SAXException ex) {
            log.error(ex);
            throw new ScopixException(ex.getMessage());
        }
    }
}