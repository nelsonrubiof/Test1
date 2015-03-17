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
 *  TestParseXml.java
 * 
 *  Created on 27-02-2014, 11:41:18 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator.dto.PeopleCountingDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author Nelson
 */
public class TestParseXml {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {

            PropertiesConfiguration pc = new PropertiesConfiguration("system.properties");

            String[] situationsEquals = pc.getStringArray("situationEquals");

            Map<Integer, Set<Integer>> situationsEq = new HashMap<Integer, Set<Integer>>();

            for (String eq : situationsEquals) {
                System.out.println("eq: " + eq);
                String[] st = StringUtils.split(eq, "=");
                Integer stId = Integer.parseInt(st[0]);
                String[] others = StringUtils.split(st[1], "|");
                for (String other : others) {
                    Integer o = Integer.parseInt(other);
                    addDataMap(stId, o, situationsEq);
                }
            }
            System.out.println("map: " + situationsEq);

            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root><value date=\"2014-02-27 09:10\" valuein=\"3\" valueout=\"4\"/></root>";

            // convert String into InputStream
            InputStream is = new ByteArrayInputStream(str.getBytes());
            List<PeopleCountingDTO> peopleCountingDTOs = PeopleCountingUtil.parserXmlPeopleCounting(is);
            System.out.println("peopleCountingDTOs " + peopleCountingDTOs);

            Date d = DateUtils.parseDate("2014-02-27 08:55:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
            int suma = 0;
            if (peopleCountingDTOs != null) {
                /**
                 * > ini e.getEvidenceDate() <= fin (e.getEvidenceDate()) + 15 Minutos se recorren todos los registros y se
                 * evaluan solo los que esten en el rango de los 15 minutos desde la fecha de la evidencia
                 */
                Calendar iniCiclo = Calendar.getInstance();
                iniCiclo.setTime(d);
                //iniCiclo.add(Calendar.MINUTE, -15);

                Calendar finCiclo = Calendar.getInstance();
                finCiclo.setTime(d);
                finCiclo.add(Calendar.MINUTE, 15);

                for (PeopleCountingDTO dto : peopleCountingDTOs) {
                    if (dto.getDate().getTime() > iniCiclo.getTimeInMillis()
                        && dto.getDate().getTime() <= finCiclo.getTimeInMillis()) {
                        suma = suma + dto.getValueIn();
                    }
                }
            }

        } catch (ScopixException e) {
            System.out.println(e);
        } catch (ParseException e) {
            System.out.println(e);
        } catch (ConfigurationException e) {
            System.out.println(e);
        }
    }

    private static void addDataMap(Integer stId1, Integer stId2, Map<Integer, Set<Integer>> situationsEq) {
        if (situationsEq.containsKey(stId1)) {
            situationsEq.get(stId1).add(stId2);
        } else {
            Set<Integer> set = new HashSet<Integer>();
            set.add(stId2);
            situationsEq.put(stId1, set);
        }

        if (situationsEq.containsKey(stId2)) {
            situationsEq.get(stId2).add(stId1);
        } else {
            Set<Integer> set = new HashSet<Integer>();
            set.add(stId1);
            situationsEq.put(stId2, set);
        }
    }

}
