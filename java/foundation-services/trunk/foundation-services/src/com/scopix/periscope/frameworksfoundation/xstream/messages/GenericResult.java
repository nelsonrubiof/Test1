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

package com.scopix.periscope.frameworksfoundation.xstream.messages;

/**
 * This class provides a generic result to controllers. It may return an error
 * or ok result, nothing more. Useful for messages composed only of these return
 * codes.
 * 
 * @author <a href="mailto:rcatalfo@gmail.com">Ricardo.Catalfo</a> Creation
 *         Date: 26/03/2007
 */
public class GenericResult extends Result {

  /**
   * Creates a result object with error result code and a description.
   * 
   * @param desc The description for the result object.
   */
  public static Result getError(final String desc) {
    Result output = new GenericResult();
    output.setResultCode(Result.ERROR);

    if (desc == null) {
      output.setResultDescription("No Error Description");
    } else {
      output.setResultDescription(desc);
    }

    return output;
  }

  /**
   * Creates a result object with ok result code and a description.
   */
  public static Result getOK() {
    Result output = new GenericResult();
    output.setResultCode(Result.OK);
    output.setResultDescription("OK");

    return output;
  }
}
