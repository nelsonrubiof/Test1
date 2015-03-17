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

package com.scopix.periscope.periscopefoundation.util;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;


/**
 * This class provides some Class related helper methods.
 * 
 * @author maximiliano.vazquez
 */
public abstract class ClassUtils {

    /**
     *
     * @param name
     * @return
     */
    public static String classFileName2ClassName(String name) {

    name = name.substring(0, (name.length() - ".class".length()));

    name = name.replaceAll("\\\\", ".");
    name = name.replaceAll("/", ".");

    if (name.startsWith(".")) {
      name = name.substring(1);
    }

    return name;

  }

    /**
     *
     * @param className
     * @return
     */
    public static Class classNameToClass(String className) {
    Class<?> clazz;
    try {
      clazz = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
    } catch (ClassNotFoundException e) {
      throw new UnexpectedRuntimeException(e);
    }
    return clazz;
  }

    /**
     *
     */
    protected ClassUtils() {
  }

}
