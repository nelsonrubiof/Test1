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

package com.scopix.periscope.periscopefoundation.persistence.paging;

import java.util.List;

/**
 * 
 * This class represents a page result of a "paged query". It contains a list of
 * elements of the page and a number which represents the global number of
 * elements that the query should return.
 * 
 * @param <E> 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class Page<E> {

    /**
     *
     * @return
     */
    public List<E> getElements() {
    return this.elements;
  }

    /**
     *
     * @return
     */
    public Integer getTotalElements() {
    return this.totalElements;
  }

    /**
     *
     * @param elements
     */
    public void setElements(List<E> elements) {
    this.elements = elements;
  }

    /**
     *
     * @param totalElements
     */
    public void setTotalElements(Integer totalElements) {
    this.totalElements = totalElements;
  }

  private List<E> elements;

  private Integer totalElements;

}
