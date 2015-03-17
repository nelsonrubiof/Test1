/*
 * 
 * Copyright © 2007, SCOPIX. All rights reserved.
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

package com.scopix.periscope.frameworksfoundation.xstream.messages;

import com.scopix.periscope.frameworksfoundation.xstream.serializer.IXMLSerializable;

/**
 * This abstract class defines the generic result that is returned by
 * controllers. All results must have at least an error and ok string message.
 * 
 * @author ricardo.catalfo
 * 
 */
public abstract class Result implements IXMLSerializable {

  public static final String ERROR = "1";

  public static final String OK = "0";

  /**
   * Returns result code
   * 
   */
  public final String getResultCode() {
    return this.resultCode;
  }

  /**
   * Returns result description
   * 
   */
  public final String getResultDescription() {
    return this.resultDescription;
  }

  /**
   * Sets result code.
   * 
   * @param codigo The code string to be set
   */
  public final void setResultCode(final String codigo) {
    this.resultCode = codigo;
  }

  /**
   * Sets result description.
   * 
   * @param descripcion The description to be set
   */
  public final void setResultDescription(final String descripcion) {
    this.resultDescription = descripcion;
  }

  private String resultCode = "0";

  private String resultDescription = "OK";

}
