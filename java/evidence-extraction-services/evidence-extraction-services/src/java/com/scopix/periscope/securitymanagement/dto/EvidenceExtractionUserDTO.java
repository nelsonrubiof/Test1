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
 * EvidenceExtractionUserDTO.java
 *
 * Created on 19-01-2010, 06:40:05 PM
 *
 */
package com.scopix.periscope.securitymanagement.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class EvidenceExtractionUserDTO {

    private String nombre;
    private String password;
    private Long sessionId;
    private List<String> privileges;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPrivileges() {
        if (privileges == null) {
            privileges = new ArrayList<String>();
        }
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public static Comparator<EvidenceExtractionUserDTO> userNameComparator = new Comparator<EvidenceExtractionUserDTO>() {

        public int compare(EvidenceExtractionUserDTO u1, EvidenceExtractionUserDTO u2) {
            int userValue = u1.getNombre().compareTo(u2.getNombre());

            return userValue;
        }
    };
    
    public static Comparator<EvidenceExtractionUserDTO> userPasswordComparator = new Comparator<EvidenceExtractionUserDTO>() {

        public int compare(EvidenceExtractionUserDTO u1, EvidenceExtractionUserDTO u2) {
            int userValue = u1.getPassword().compareTo(u2.getPassword());

            return userValue;
        }
    };
}
