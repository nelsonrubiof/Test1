/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * RefreshMondrianCacheCommand.java
 *
 * Created on 08-11-2012, 03:12:45 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class RefreshMondrianCacheCommand {
    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(RefreshMondrianCacheCommand.class);

    /**
     * Refrescar mondrian
     * 
     * @param mondrianCacheURL url a invocar
     */
    public void execute(String mondrianCacheURL) {
        log.info(START);
        int code = 0;
        try {
            while (code != 200) {
                URL urlflush = new URL(mondrianCacheURL);
                HttpURLConnection connection = (HttpURLConnection) urlflush.openConnection();
                connection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.3 "
                        + "MEGAUPLOAD 1.0");
                connection.addRequestProperty("query", "xmla");
                code = connection.getResponseCode();
                log.debug("code = " + code);
                BufferedReader dis = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = dis.readLine()) != null) {
                    log.debug("inputLine: " + inputLine);
                }
                dis.close();
            }
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage(), ex);
        }
        log.info(END);
    }
}