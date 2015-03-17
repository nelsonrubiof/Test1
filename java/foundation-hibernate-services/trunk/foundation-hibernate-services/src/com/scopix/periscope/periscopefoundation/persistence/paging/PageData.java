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

import org.springframework.util.Assert;

/**
 * This class represents info requested to a paged query. It has two numbers
 * representing offset and limit of query.
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class PageData {

  /**
   * Creates a new {@link PageData} based on specified parameters.
   * @param firstResult
   * @param maxResults
   * @return  
   */
  public static PageData newPageData(int firstResult, int maxResults) {

    Assert.state(firstResult >= 1, "El primer elemento debe ser mayor o igual que cero");
    Assert.state(maxResults >= 0, "La cantidad maxima de resultados debe ser mayor o igual que cero");

    PageData pageData = new PageData();

    pageData.setFirstResult(firstResult);
    pageData.setMaxResults(maxResults);

    return pageData;
  }

    /**
     *
     * @return
     */
    public int getFirstResult() {
    return this.firstResult;
  }

    /**
     *
     * @return
     */
    public int getMaxResults() {
    return this.maxResults;
  }

    /**
     *
     * @param firstResult
     */
    public void setFirstResult(int firstResult) {
    this.firstResult = firstResult;
  }

    /**
     *
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
    this.maxResults = maxResults;
  }

  private int firstResult;

  private int maxResults;

}
