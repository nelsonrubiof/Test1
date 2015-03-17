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
 *  FFMPEGProcessBuilder.java
 * 
 *  Created on 03-07-2013, 10:52:00 AM
 * 
 */
package com.scopix.periscope.converter.ffmpeg;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Controlador encargado de la inicialización de los procesos de
 * conversión de videos
 *
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public class FFMPEGProcessBuilder {
    
    private String[] params;
    private static Logger log = Logger.getLogger(FFMPEGProcessBuilder.class);

    /**
     * Ejecuta comando de conversión de videos con sus correspondientes
     * parámetros
     * 
     * @author carlos polo
     * @date   03-jul-2013
     * @return Process proceso de conversión ejecutado
     * @throws ScopixException excepción durante la conversión
     */
    public Process start() throws ScopixException {
        log.info("start");
        Process process = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.redirectErrorStream(true);
            builder.command(getParams());
            //builder.directory(new File("/tmp"));
            process = builder.start();
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
        return process;
    }

    /**
     * @return the params
     */
    public String[] getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(String[] params) {
        this.params = params;
    }   
}