/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * FFmpegProcessBuilder.java
 * 
 * Created on 13-05-2013, 06:08:26 PM
 */
package com.scopix.periscope.ffmpeg;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class FFmpegProcessBuilder {

    private static Logger log = Logger.getLogger(FFmpegProcessBuilder.class);
    private String[] param;

    /**
     * Ejecuta un commnad injectado en los parametros
     * 
     * @return Process ejecutado
     */
    public Process start() {
        log.info("start");
        Process p = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.redirectErrorStream(true); // This is the important part
            builder.command(getParam());
            builder.directory(new File("/tmp"));
            p = builder.start();
        } catch (IOException e) {
            log.error(e, e);
        }
        log.info("end");
        return p;
    }

    /**
     * @return the param
     */
    public String[] getParam() {
        return param;
    }

    /**
     * @param param the param to set
     */
    public void setParam(String[] param) {
        this.param = param;
    }
}
