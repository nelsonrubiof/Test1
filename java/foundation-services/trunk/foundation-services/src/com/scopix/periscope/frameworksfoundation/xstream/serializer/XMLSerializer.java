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
package com.scopix.periscope.frameworksfoundation.xstream.serializer;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * Helper to class to serialize/deserialize objets to XML.
 * 
 * @author <a href="mailto:rcatalfo@gmail.com">Ricardo.Catalfo</a> Creation
 *         Date: 26/03/2007
 */
public class XMLSerializer {

  public static synchronized <E extends IXMLSerializable> E deserialize(String xml) {
    XStream xs = XStreamFactory.getInstance();

    return (E) xs.fromXML(xml);
  }

  /**
   * The public interface of this class. Only objects that implementes
   * IXMLSerializable can be represented in XML.
   * 
   * @param bean The java bean to be serialized.
   * 
   * @return A XML representation of the object
   */
  public static synchronized String serialize(IXMLSerializable bean) {
    XStream xs = XStreamFactory.getInstance();

    return xs.toXML(bean);
  }

  private XMLSerializer() {
  }
}
