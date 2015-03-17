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
 */

package com.scopix.periscope.frameworksfoundation.hibernate.config;

import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This class exists only to keep a reference to the Hibernate configuration
 * object and to be able to provide it when necessary.
 * 
 * @author Nelson Rubio
 * @version 1.0.0
 * 
 */
public class HibernateConfigurationHolder {

    /**
     *
     */
    public static AnnotationConfiguration conf;

}
