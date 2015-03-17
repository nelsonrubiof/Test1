/*
 * 
 * Copyright 2007, SCOPIX. All rights reserved.
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

/**
 * 12/03/2007 - 11:33:00
 */
package com.scopix.periscope.periscopefoundation.states.clients;

/**
 * This interface is used by those stateful which want to know when state change
 * was performed.
 * 
 * @author maximiliano.vazquez
 */
public interface StatefulAware {

  void afterStateChange();

}
