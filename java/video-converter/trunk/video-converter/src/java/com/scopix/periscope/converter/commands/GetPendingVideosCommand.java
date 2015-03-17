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
 *  GetPendingVideosCommand.java
 * 
 *  Created on 20-08-2013, 05:33:43 PM
 * 
 */

package com.scopix.periscope.converter.commands;

import com.scopix.periscope.converter.Video;
import com.scopix.periscope.converter.dao.VideoConverterHibernateDAO;
import com.scopix.periscope.converter.dao.VideoConverterHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Comando para obtener registros de videos pendientes de conversión
 *
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public class GetPendingVideosCommand {

    private VideoConverterHibernateDAO dao;
    private static Logger log = Logger.getLogger(GetPendingVideosCommand.class);
    
    /**
     * Obtiene implementación del DAO para acceso a base de datos
     * 
     * @author carlos polo
     * @date   21-ago-2013
     * @return VideoConverterHibernateDAO implementación del DAO para acceso a base de datos
     */
    public VideoConverterHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(VideoConverterHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(VideoConverterHibernateDAO dao) {
        this.dao = dao;
    }
    
    /**
     * Consulta registros de los videos pendientes de conversión
     * 
     * @author carlos polo
     * @date   21-ago-2013
     * @return List<Video>     lista de videos pendientes de conversión (en caso de existir)
     * @throws ScopixException excepción durante la consulta en base de datos
     */
    public List<Video> execute() throws ScopixException {
        log.info("start");
        List<Video> lstVideosPendientes = getDao().getPendingVideos();
        log.info("end");
        return lstVideosPendientes;
    }
}