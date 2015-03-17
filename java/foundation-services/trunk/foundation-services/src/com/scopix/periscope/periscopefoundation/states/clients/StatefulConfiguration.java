/*
 * 
 * Copyright 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */

/**
 * Oct 2, 2006 - 10:53:58 AM
 */
package com.scopix.periscope.periscopefoundation.states.clients;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotations mark classes and indicated them which class configures his
 * states graph. Configurator class must have method:<br>
 * public static void configure(StateConfigurationHolder configuration)
 * 
 * @author maximiliano.vazquez
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface StatefulConfiguration {
  Class<?> configuratorClass();
}
