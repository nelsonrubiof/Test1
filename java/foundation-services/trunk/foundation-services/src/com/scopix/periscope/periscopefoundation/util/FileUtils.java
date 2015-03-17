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
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * This class provides some File related helper methods.
 * 
 * @author maximiliano.vazquez
 * @version 2.0.0
 */
public abstract class FileUtils {

    /**
     * Devuelve el path de la forma "yyyy/MM/dd" a partir de la fecha entregada.
     *              
     * @param fecha Parametro desde donde se extraera el path.
     * @param separator 
     * @return String con el Path
     */
    public static String getPathFromDate(Date fecha, String separator) {
        DateFormat dateYearFormat = new SimpleDateFormat("yyyy");
        DateFormat dateMonthFormat = new SimpleDateFormat("MM");
        DateFormat dateDayFormat = new SimpleDateFormat("dd");

        String path = dateYearFormat.format(fecha) + separator;
        path += dateMonthFormat.format(fecha) + separator;
        path += dateDayFormat.format(fecha);

        return path;
    }

    /**
     * Find all files recursively from specified starting directory.<br>
     * It applies specified filter to exclude files.
     * 
     * @param startingDir 
     * @param fileFilter 
     * @return 
     */
    public static List<File> getAllFilesRecursively(File startingDir, FileFilter fileFilter) {
        List<File> result = new ArrayList<File>();

        File[] filesAndDirs = startingDir.listFiles();

        if (filesAndDirs == null) {
            return null;
        }

        for (File file : filesAndDirs) {

            boolean acceptFile = fileFilter.accept(file);

            if (acceptFile) {
                result.add(file);
            }

            if (!file.isFile()) {
                if (!file.isHidden()) {
                    List<File> deeperList = FileUtils.getAllFilesRecursively(file, fileFilter);
                    result.addAll(deeperList);
                }
            }

        }

        return result;
    }

    /**
     * It returns a {@link File} which represent relative "." based on default
     * resource loader.
     * @return 
     */
    public static File getOutputClassesRootPath() {
        try {
            Resource resource = new DefaultResourceLoader().getResource(".");
            return resource.getFile();
        } catch (IOException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    /**
     * Get Simple filename from full filename
     * @param fullPathFilename
     * @return  
     */
    public static String getSimpleFileNameFromPath(String fullPathFilename) {
        return new File(fullPathFilename).getName();
    }
    
    /**
     * delete recursively a directory
     * @param path
     * @return  
     */
    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     *
     */
    protected FileUtils() {
    }
}
