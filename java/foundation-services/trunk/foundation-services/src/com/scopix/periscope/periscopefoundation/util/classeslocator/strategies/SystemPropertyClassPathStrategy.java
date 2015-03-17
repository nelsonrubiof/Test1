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
import org.apache.log4j.Logger;

/**
 * This class is an implmentation of {@link ClassPathStrategy} which works based
 * on system property "java.class.path" which contains all clasess on classpath.
 * 
 * @author maximiliano.vazquez
 * 
 */
public class SystemPropertyClassPathStrategy implements ClassPathStrategy {

    private Logger log = Logger.getLogger(SystemPropertyClassPathStrategy.class);

    /**
     *
     * @return
     */
    public List<Class> getAllClasses() {
        log.debug("[getAllClasses] start");
        String classpathProperty = System.getProperty("java.class.path");
        log.debug("[getAllClasses] classpathProperty: " + classpathProperty);
        String[] classPathPropertyUnit = classpathProperty.split(";");
        log.debug("[getAllClasses] classpathPropertyUnit.length: " + classPathPropertyUnit.length);
        List<File> allClasspathFiles = new ArrayList();

        for (String classpathUnit : classPathPropertyUnit) {
            log.debug("[getAllClasses] classpathUnit: " + classpathUnit);
            File classpathUnitFile = new File(classpathUnit);
            log.debug("[getAllClasses] classpathUnitFile: " + classpathUnitFile);
            if (!classpathUnitFile.isDirectory()) {
                log.debug("[getAllClasses] is a File");
                continue;
            }

            List<File> filesInsideClasspathUnit = FileUtils.getAllFilesRecursively(classpathUnitFile,
                    new NonHiddenClassFilesFilter());
            log.debug("[getAllClasses] filesInsideClasspathUnit: " + filesInsideClasspathUnit);
            if (filesInsideClasspathUnit != null) {
                log.debug("[getAllClasses] filesInsideClasspathUnit.size(): " + filesInsideClasspathUnit.size());
                allClasspathFiles.addAll(filesInsideClasspathUnit);
            }
        }

        if (allClasspathFiles.isEmpty()) {
            log.debug("[getAllClasses] Empty");
            throw new UnexpectedRuntimeException("No se encontraron clases procesando el classpath: " + classpathProperty);
        }

        List<Class> classes = new ArrayList();

        for (File fileClass : allClasspathFiles) {
            Class clazz = ClassPathUtils.classFilePath2Class(fileClass);
            classes.add(clazz);
        }
        log.debug("[getAllClasses] end");
        return classes;

    }
}
