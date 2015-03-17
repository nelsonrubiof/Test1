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
 *  VideoConverterHibernateDAO.java
 * 
 *  Created on 19-08-2013, 06:02:12 PM
 * 
 */

package com.scopix.periscope.converter.dao;

import com.scopix.periscope.converter.Video;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.List;

/**
 * Interfáz que define métodos para acceso a base de datos a través del DAO personalizado
 *
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public interface VideoConverterHibernateDAO {
    
    /**
     * Consulta registros de los videos pendientes de conversión
     * 
     * @author carlos polo
     * @date   21-ago-2013
     * @return List<Video>     lista de videos pendientes de conversión (en caso de existir)
     * @throws ScopixException excepción durante la consulta de registros
     */
    List<Video> getPendingVideos() throws ScopixException;
}