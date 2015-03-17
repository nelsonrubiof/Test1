/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.periscopefoundation.etl;

import org.apache.log4j.Logger;
import scriptella.interactive.ProgressIndicatorBase;

/**
 *
 * @author nelson
 */
public class PeriscopeProgressIndicator extends ProgressIndicatorBase {

    private static Logger log = Logger.getLogger(PeriscopeProgressIndicator.class);

    @Override
    protected void show(String message, double d) {
        log.info("message: " + message + ", " + d);
    }
}
