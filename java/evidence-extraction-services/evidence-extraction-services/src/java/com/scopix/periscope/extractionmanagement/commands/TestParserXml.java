/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.commands.parser.CntGroupXmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.CntSetXmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.CntXmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.CognimaticsPeopleCounter141XmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CntGroupXmlDefinition212;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CntSetXmlDefinition212;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CntXmlDefinition212;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CognimaticsPeopleCounter212XmlDefinition;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.digester.Digester;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author nelson
 */
public class TestParserXml {

    private static Logger log = Logger.getLogger(TestParserXml.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ScopixException {
        try {
//            InputStream is = FileUtils.openInputStream(new File("C:/Users/Nelson/Documents/scopix/Clientes/HT/AR_cam5_04042011.xml"));
            InputStream is = FileUtils.openInputStream(new File("C:/test/camtest.xml"));
//            CognimaticsPeopleCounter212ExtractionCommand command = new CognimaticsPeopleCounter212ExtractionCommand();
//            Map<String, Map<String, Integer>> ret = command.readPeopleCountingFile(is,
//                    new CognimaticsPeopleCounter212ExtractionRequest());

            Map<String, Map<String, Integer>> ret = new LinkedHashMap<String, Map<String, Integer>>();

            CognimaticsPeopleCounter212XmlDefinition cpcxd = parserInputStream(is);
            long dateTime = 0;
            Calendar cal = Calendar.getInstance();
            Calendar initCarga = Calendar.getInstance();
            initCarga.add(Calendar.DATE, -7);
            for (CntSetXmlDefinition212 cntset : cpcxd.getCntset()) {
                dateTime = (cntset.getStarttime() * 1000) - (cal.get(Calendar.DST_OFFSET)
                    + cal.get(Calendar.ZONE_OFFSET));
                cal.setTimeInMillis(dateTime);
                for (CntGroupXmlDefinition212 cntgroup : cntset.getCntGroups()) {
                    cal.add(Calendar.SECOND, cntset.getDelta());
                    Integer valueIn = 0;
                    Integer valueOut = 0;
                    for (CntXmlDefinition212 cnt : cntgroup.getCnts()) {
                        switch (cnt.getTypeid()) {
                            case 3: //IN
                                valueIn = cnt.getValue();
                                break;
                            case 4: //OUT
                                valueOut = cnt.getValue();
                                break;
                            default:
                                break;
                        }
                    }
                    //solo se agregaran resultados para fechas mayores que 1 semana atras
                    if (initCarga.getTime().before(cal.getTime())) {
                        Map<String, Integer> dato = new HashMap<String, Integer>();
                        String dateKey = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd HH:mm");
                        if (ret.containsKey(dateKey)) {
                            Map<String, Integer> aux = ret.get(dateKey);
                            valueIn = valueIn + aux.get("valueIn");
                            valueOut = valueOut + aux.get("valueOut");
                        }
                        dato.put("valueIn", valueIn);
                        dato.put("valueOut", valueOut);
                        ret.put(dateKey, (dato));
                    }

                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("date;valueIn;valueOut\n");
            for (String key : ret.keySet()) {
                Map<String, Integer> dato = ret.get(key);
                sb.append(key).append(";");
                sb.append(dato.get("valueIn").toString()).append(";");
                sb.append(dato.get("valueOut").toString()).append("\n");
            }
            FileUtils.writeStringToFile(new File("c:/test/xmlParserEEsCm5.csv"), sb.toString());

        } catch (IOException ex) {
        }

    }

    private static CognimaticsPeopleCounter212XmlDefinition parserInputStream(InputStream is) {
        CognimaticsPeopleCounter212XmlDefinition cpcxd = null;
        try {
            /**
             * parseamos el is para el xml de la camara
             */
            Digester dg = new Digester();
            dg.setValidating(false);
            //debido a que el xml tiene un dtd de definicion el cual no se tiene acceso directo ya qye no es una url
            dg.setEntityResolver(new LocalDTDEntityResolver("dtd/appdata1_41.dtd"));
            dg.push(new CognimaticsPeopleCounter212XmlDefinition());
            dg.addObjectCreate("*/cntset", CntSetXmlDefinition212.class);
            dg.addSetProperties("*/cntset");
            dg.addSetNestedProperties("*/cntset", "cntgroup", null);
            dg.addSetNext("*/cntset", "addCntset");
            
            dg.addObjectCreate("*/cntset/cntgroup", CntGroupXmlDefinition212.class);
            dg.addSetProperties("*/cntset/cntgroup");
            dg.addSetNestedProperties("*/cntset/cntgroup", "cnt", null);
            dg.addSetNext("*/cntset/cntgroup", "addCntGroup");
            
            dg.addObjectCreate("*/cntset/cntgroup/cnt", CntXmlDefinition212.class);
            dg.addSetProperties("*/cntset/cntgroup/cnt");
            dg.addCallMethod("*/cntset/cntgroup/cnt", "setValue", 0, new Class[]{Integer.class});
            dg.addSetNestedProperties("*/cntset/cntgroup/cnt");
            dg.addSetNext("*/cntset/cntgroup/cnt", "addCnt");
            cpcxd = (CognimaticsPeopleCounter212XmlDefinition) dg.parse(is);

        } catch (IOException e) {
            log.error("error recuperando inputStream " + e, e);
            cpcxd = null;
        } catch (SAXException e) {
            log.error("error parseando xml de camara " + e, e);
            cpcxd = null;
        }
        return cpcxd;
    }
}
