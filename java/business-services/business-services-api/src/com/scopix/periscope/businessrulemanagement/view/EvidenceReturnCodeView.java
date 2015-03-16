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
package com.scopix.periscope.businessrulemanagement.view;


/**
 * This class represents a DTO. <br>
 * It is used to send and receive data out of the application, such as web
 * services or via web.
 * 
 * @author maximiliano.vazquez
 * 
 */
public class EvidenceReturnCodeView {

  public final String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  private String value;

}
