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

import java.io.File;

import org.springframework.util.StringUtils;

/**
 * 
 * This class provides some Class Path related helper methods.
 * 
 * @author maximiliano.vazquez
 */
public abstract class ClassPathUtils {

    /**
     *
     * @param classFile
     * @return
     */
    public static String classFile2ClassName(File classFile) {

    String absoluteRootPath = FileUtils.getOutputClassesRootPath().getAbsolutePath();

    String relativeClassFilePath = StringUtils.delete(classFile.getAbsolutePath(), absoluteRootPath);

    String className = ClassUtils.classFileName2ClassName(relativeClassFilePath);

    return className;
  }

    /**
     *
     * @param classFile
     * @return
     */
    public static Class classFilePath2Class(File classFile) {

    String className = ClassPathUtils.classFile2ClassName(classFile);

    Class clazz = ClassUtils.classNameToClass(className);

    return clazz;
  }

    /**
     *
     */
    protected ClassPathUtils() {
  }

}
