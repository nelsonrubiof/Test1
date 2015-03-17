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
 */

package com.scopix.periscope.periscopefoundation.view;

import com.scopix.periscope.frameworksfoundation.xstream.messages.GenericResult;
import com.scopix.periscope.frameworksfoundation.xstream.serializer.IXMLSerializable;
import com.scopix.periscope.frameworksfoundation.xstream.serializer.XMLSerializer;

import org.springframework.web.servlet.view.AbstractView;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class a view used to render xml.<br>
 * It is created from an {@link IXMLSerializable} object and it serialize this
 * object to xml.
 * 
 * @author <a href="mailto:rcatalfo@gmail.com">Ricardo.Catalfo</a> Creation
 *         Date: 26/03/2007
 * @version 1.1.0
 */
public class XMLView extends AbstractView {

    /**
     *
     */
    public XMLView() {
    this.serializable = GenericResult.getOK();
  }

    /**
     *
     * @param serializable
     */
    public XMLView(IXMLSerializable serializable) {
    this.serializable = serializable;
  }

    /**
     *
     * @param map
     * @param req
     * @param res
     * @throws Exception
     */
    @Override
  protected void renderMergedOutputModel(Map map, HttpServletRequest req, HttpServletResponse res) throws Exception {

    String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    res.getOutputStream().write(xmlHeader.getBytes());

    String xml = XMLSerializer.serialize(this.serializable);
    byte[] xmlBytes = xml.getBytes();

    res.getOutputStream().write(xmlBytes);

    if (this.getContentType() == null) {
      res.setContentType("text/xml");
    } else {
      res.setContentType(this.getContentType());
    }
  }

  private IXMLSerializable serializable;

}
