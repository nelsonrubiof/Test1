/*
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
package com.scopix.periscope.periscopefoundation.util.classeslocator.strategies;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.ClassPathUtils;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.classeslocator.NonHiddenClassFilesFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is an implmentation of {@link ClassPathStrategy} which works based
 * on using:
 * 
 * <pre>
 * new DefaultResourceLoader().getResource(&quot;.&quot;);
 * </pre>
 * 
 * as root path to find all java files.
 * 
 * @author maximiliano.vazquez
 * 
 */
public class OutputClassesClassPathStrategy implements ClassPathStrategy {

    /**
     *
     * @return
     */
    public List<Class> getAllClasses() {
    File classesDir = FileUtils.getOutputClassesRootPath();

    List<File> filesClasses = FileUtils.getAllFilesRecursively(classesDir, new NonHiddenClassFilesFilter());

    if (filesClasses == null) {
      throw new UnexpectedRuntimeException("No se encontraron clases en la ruta: \"" + classesDir.getAbsolutePath()
          + "\", revise que esa sea la ruta donde efectivamente se encuentran los fuentes del proyecto");
    }

    List<Class> classes = new ArrayList();

    for (File fileClass : filesClasses) {
      Class clazz = ClassPathUtils.classFilePath2Class(fileClass);
      classes.add(clazz);
    }

    return classes;
  }

}
