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

import java.util.List;

/**
 * This interface defines a strategy to find all system classes. This is a non
 * trivial thing in Java, and there is no strategy which works on all
 * situations, so different strategies are used for each specific situation.
 * 
 * @author maximiliano.vazquez
 * 
 */
public interface ClassPathStrategy {

    /**
     *
     * @return
     */
    List<Class> getAllClasses();
}
