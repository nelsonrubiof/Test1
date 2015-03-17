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

package com.scopix.periscope.periscopefoundation.util.classeslocator;

import java.io.File;
import java.io.FileFilter;

/**
 * This class is a {@link FileFilter} which only includes files which represent
 * classes
 * 
 * @author maximiliano.vazquez
 */
public class NonHiddenClassFilesFilter implements FileFilter {

  public boolean accept(File file) {

    if (file.isDirectory()) {
      return false;
    }

    if (file.isHidden()) {
      return false;
    }

    if (!file.getName().endsWith(".class")) {
      return false;
    }

    return true;
  }
}
