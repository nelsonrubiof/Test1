/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
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
package com.scopix.periscope.periscopefoundation.queuemanager;

/**
 *
 * This class represents a priority to be used when a queue operation is performed. It allows us to define a queue order.<br>
 * Based on priority values, an object could be inserted on different queue positions.<br>
 * Higher values represents higher priorities.
 *
 * @author maximiliano.vazquez
 *
 */
public class Priority implements Comparable<Priority> {

    /**
     * Creates a new {@link Priority} with specefied priority value.
     */
    public static Priority create(Integer value) {
        Priority priority = new Priority();
        priority.value = value;
        return priority;
    }

    private Priority() {
    }

    /**
     * Defines how {@link Priority} objects are compared
     */
    public int compareTo(Priority o) {
        return this.value.compareTo(o.value);
    }

    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
    private Integer value;
}
