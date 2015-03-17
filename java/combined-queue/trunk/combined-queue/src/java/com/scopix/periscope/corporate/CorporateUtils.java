/*
 * Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CorporateManagerImpl.java
 *
 * Created on 05/11/20013, 09:14:59 PM
 */
package com.scopix.periscope.corporate;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class CorporateUtils {

    private static List<Corporate> corporates;

    /**
     *
     * @return
     * @throws ScopixException
     */
    public static List<Corporate> getCorporates() throws ScopixException {
        if (corporates == null) {
            setCorporates(CorporatesXmlProcessor.getInstance().execute());
        }
        return corporates;
    }
    
    /**
     *Setter
     * param List<Corporate>
     * 
     */
    private static void setCorporates(List<Corporate> corporates) {
        CorporateUtils.corporates = corporates;
    }

    /**
     * gets corporate pojo for a given id.
     *
     * @param corporateId 
     * @return Corporate
     * @throws ScopixException 
     * @date 25/03/2013
     */
    public static Corporate getCorporate(Integer corporateId) throws ScopixException {
        if (corporates == null) {
            setCorporates(CorporatesXmlProcessor.getInstance().execute());
        }
        for (Corporate corporate : corporates) {

            if (corporate.getId().equals(corporateId)) {
                return corporate;
            }
        }
        throw new ScopixException("Corporate Not found");
    }
}
