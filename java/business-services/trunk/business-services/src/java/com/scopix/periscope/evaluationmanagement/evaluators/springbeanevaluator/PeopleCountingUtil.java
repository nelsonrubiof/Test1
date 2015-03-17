/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import com.scopix.periscope.JCIFSUtil;
import com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator.dto.PeopleCountingDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Cesar
 */
@SpringBean(rootClass = PeopleCountingUtil.class)
public class PeopleCountingUtil {

    private static Logger log = Logger.getLogger(PeopleCountingUtil.class);

    private static JCIFSUtil jcifsUtil;

    private static void setDateConvert() {
        String pattern = "yyyy-MM-dd HH:mm";
        Locale locale = Locale.getDefault();
        DateLocaleConverter converter = new DateLocaleConverter(locale, pattern);
        converter.setLenient(true);
        ConvertUtils.register(converter, java.util.Date.class);
    }

    public static JCIFSUtil getJcifsUtil() {
        if (jcifsUtil == null) {
            jcifsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        }
        return jcifsUtil;
    }

    public static void setJcifsUtil(JCIFSUtil aJcifsUtil) {
        jcifsUtil = aJcifsUtil;
    }

    private PeopleCountingUtil() {
    }

    public static List<PeopleCountingDTO> transfornPeopleCounting(String path) throws ScopixException {
        List<PeopleCountingDTO> peopleCountingDTOs = new ArrayList<PeopleCountingDTO>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        InputStream is = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            //Read existen document XML
            docBuilderFactory.setValidating(true);
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new EntityResolver() {

                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    ClassPathResource cpr = new ClassPathResource("dtd/SCPPeopleCounting.dtd");
                    InputStream is = cpr.getInputStream();
                    return new InputSource(is);
                }
            });
            Map map = getJcifsUtil().getFileSmb(path);
            is = (InputStream) map.get("is");
            Document doc = docBuilder.parse(is);
            // normalize text representation
            doc.getDocumentElement().normalize();

            NodeList rootList = doc.getElementsByTagName("root");
            for (int i = 0; i < rootList.getLength(); i++) {
                Node root = rootList.item(i);
                if (root.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) root;

                    NodeList nodeList = element.getElementsByTagName("value");
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Element elem = (Element) nodeList.item(j);
                        String date = elem.getAttribute("date");
                        String valueIn = elem.getAttribute("valuein");
                        String valueOut = elem.getAttribute("valueout");

                        PeopleCountingDTO dto = new PeopleCountingDTO();
                        dto.setDate(sdf.parse(date));
                        dto.setValueIn(Integer.parseInt(valueIn));
                        dto.setValueOut(Integer.parseInt(valueOut));
                        peopleCountingDTOs.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            throw new ScopixException(e);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                log.debug("error: " + ex.getMessage());
            }
        }
        return peopleCountingDTOs;
    }

    /**
     * transforma un xml con la siguiente estructura en una lista de PeopleCountingDTO
     * <root>
     * <value date="2010-08-01 00:00" valuein="0" valueout="0"/>
     * </root>
     */
    public static List<PeopleCountingDTO> parserXmlPeopleCounting(InputStream is) throws ScopixException {
        List<PeopleCountingDTO> lista = new ArrayList<PeopleCountingDTO>();

        try {
            setDateConvert();
            Digester dg = new Digester();
            dg.push(lista);
            //creamos el objeto a parsear
            dg.addObjectCreate("*/value", PeopleCountingDTO.class);
            //recuperamos todos los aributos especificas del xml
            //se utiliza de esta forma dado que el xml viene con valuein y con valueout en vez de de valueIn y valueOut
            dg.addSetProperties("*/value", new String[]{"valuein", "valueout"}, new String[]{"valueIn", "valueOut"});
            //recuperamos todos los propiedades especificas del xml
            //dg.addSetNestedProperties("*/value");
            //al momento de cerrarse se aggrega la padre es decir a la lista
            dg.addSetNext("*/value", "add");
            dg.parse(is);
        } catch (IOException e) {
            log.error("error: IO " + e);
            throw new ScopixException(e);
        } catch (SAXException e) {
            log.error("error: SAX " + e);
            throw new ScopixException(e);
        }
        return lista;
    }

    /**
     * recibe la ruta desde un directorio samba
     */
    public static List<PeopleCountingDTO> parserXmlPeopleCounting(String path) throws ScopixException {
        log.info("start [path:" + path + "]");
        List<PeopleCountingDTO> lista = new ArrayList<PeopleCountingDTO>();

        InputStream is = null;
        try {

//            Map<String, Object> map = getJcifsUtil().getFileSmb(path);
//            is = (InputStream) map.get("is");
            is = new FileInputStream(path);
            lista = parserXmlPeopleCounting(is);
        } catch (FileNotFoundException e) {
            log.error("Error loading file " + path + " e:" + e, e);
            throw new ScopixException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    log.error("no es posible cerrar is path:" + path);
                }
            }
        }
        log.info("end [size:" + lista.size() + "]");
        return lista;
    }

}
