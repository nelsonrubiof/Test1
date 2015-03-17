/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * Range.java
 * 
 * Created on 02-12-2013, 04:35:54 PM
 */

package com.scopix.periscope.operatorimages;

/**
 *
 * @author carlos polo
 * @version 2.0.0
 */
public class Range {
    public long start;
    public long end;
    public long length;
    public long total;

    /**
     * Construct a byte range.
     * 
     * @param start Start of the byte range.
     * @param end End of the byte range.
     * @param total Total length of the byte source.
     */
    public Range(long start, long end, long total) {
        this.start = start;
        this.end = end;
        this.length = end - start + 1;
        this.total = total;
    }
}