/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceExtractionUserLoginDAO.java
 *
 * Created on 20-01-2010, 10:10:35 AM
 *
 */
package com.scopix.periscope.securitymanagement.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.SecurityExceptionType;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.dto.EvidenceExtractionUserDTO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = EvidenceExtractionUserLoginDAO.class)
public class EvidenceExtractionUserLoginDAO {

    Logger log = Logger.getLogger(EvidenceExtractionUserLoginDAO.class);

    public EvidenceExtractionUserDTO getUserAndPrivileges(String user, String password, String filePath)
            throws ScopixException {
        log.debug("start");
        EvidenceExtractionUserDTO userDTO = null;

        //cargando archivo XML
        List<EvidenceExtractionUserDTO> userDTOList = loadXMLUserFile(filePath);

        //validando usuario
        userDTO = validateUser(user, password, userDTOList);

        log.debug("end");
        return userDTO;
    }

    private List<EvidenceExtractionUserDTO> loadXMLUserFile(String filePath) throws ScopixException {
        log.debug("start");
        List<EvidenceExtractionUserDTO> userDTOList = null;

        try {
            ClassPathResource res = new ClassPathResource(filePath);
            Properties prop = new Properties();
            prop.load(res.getInputStream());

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse(new InputSource(res.getInputStream()));

            // normalize text representation
            doc.getDocumentElement().normalize();
            //log.debug("Root element of the doc is " + doc.getDocumentElement().getNodeName());

            NodeList listOfPersons = doc.getElementsByTagName("user");
            int totalPersons = listOfPersons.getLength();
            log.debug("Numero de Usuarios: " + totalPersons);

            userDTOList = new ArrayList<EvidenceExtractionUserDTO>();

            for (int s = 0; s < listOfPersons.getLength(); s++) {
                EvidenceExtractionUserDTO userDTO = new EvidenceExtractionUserDTO();

                Node firstPersonNode = listOfPersons.item(s);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {


                    Element userElement = (Element) firstPersonNode;

                    //-------
                    NodeList userNameList = userElement.getElementsByTagName("name");
                    Element userNameElement = (Element) userNameList.item(0);

                    NodeList textNameList = userNameElement.getChildNodes();
                    String userName = ((Node) textNameList.item(0)).getNodeValue().trim();
                    log.debug("Name : " + userName);
                    userDTO.setNombre(userName);

                    //-------
                    NodeList userPasswordList = userElement.getElementsByTagName("password");
                    Element userPasswordElement = (Element) userPasswordList.item(0);

                    NodeList textPasswordList = userPasswordElement.getChildNodes();
                    String password = ((Node) textPasswordList.item(0)).getNodeValue().trim();
                    log.debug("Password : " + password);
                    userDTO.setPassword(password);

                    //----
                    NodeList sessionIdList = userElement.getElementsByTagName("sessionId");
                    Element sessionIdElement = (Element) sessionIdList.item(0);

                    NodeList textSessionIdList = sessionIdElement.getChildNodes();
                    String sessionId = ((Node) textSessionIdList.item(0)).getNodeValue().trim();
                    log.debug("SessionId : " + sessionId);
                    userDTO.setSessionId(Long.valueOf(sessionId));

                    //------
                    NodeList privilegesList = userElement.getElementsByTagName("privilege");

                    for (int j = 0; j < privilegesList.getLength(); j++) {
                        Element privilegesElement = (Element) privilegesList.item(j);

                        NodeList textPrivilegesList = privilegesElement.getChildNodes();
                        String privilege = ((Node) textPrivilegesList.item(0)).getNodeValue().trim();
                        log.debug("Privilege : " + privilege);
                        userDTO.getPrivileges().add(privilege);
                    }
                }//end of if clause

                userDTOList.add(userDTO);

            }//end of for loop with s var

        } catch (ParserConfigurationException pcex) {
            log.debug("Error: ", pcex);
            throw new ScopixException("userfile.error", pcex);
        } catch (SAXException saxex) {
            log.debug("Error: ", saxex);
            throw new ScopixException("userfile.error", saxex);
        } catch (FileNotFoundException fnfex) {
            log.debug("Error: ", fnfex);
            throw new ScopixException("userfile.error", fnfex);
        } catch (IOException ioex) {
            log.debug("Error: ", ioex);
            throw new ScopixException("userfile.error", ioex);
        }

        log.debug("end. Count: " + userDTOList.size());
        return userDTOList;
    }

    private EvidenceExtractionUserDTO validateUser(String user, String password, List<EvidenceExtractionUserDTO> userList)
            throws ScopixException {
        log.debug("start");
        EvidenceExtractionUserDTO userDTOParam = new EvidenceExtractionUserDTO();
        EvidenceExtractionUserDTO userDTO = null;

        log.debug("user: " + user);
        log.debug("password: " + password);

        userDTOParam.setNombre(user);
        userDTOParam.setPassword(password);

        Collections.sort(userList, EvidenceExtractionUserDTO.userNameComparator);
        int resp = Collections.binarySearch(userList, userDTOParam, EvidenceExtractionUserDTO.userNameComparator);

        if (resp >= 0) {
            log.debug("usuario existente. Validando clave...");
            Collections.sort(userList, EvidenceExtractionUserDTO.userPasswordComparator);
            resp = Collections.binarySearch(userList, userDTOParam, EvidenceExtractionUserDTO.userPasswordComparator);

            if (resp >= 0) {
                log.debug("Clave validada.");
                userDTO = userList.get(resp);
            } else {
//                throw new PeriscopeSecurityException("login.user_password_incorrect",
//                        PeriscopeSecurityException.Type.ACCESS_DENIED);
                throw new ScopixException(SecurityExceptionType.ACCESS_DENIED.getName());
            }
        } else {
//            throw new PeriscopeSecurityException("login.user_not_found", PeriscopeSecurityException.Type.USER_NOT_FOUND);
            throw new ScopixException(SecurityExceptionType.USER_NOT_FOUND.getName());
        }


        log.debug("end");
        return userDTO;
    }
}
