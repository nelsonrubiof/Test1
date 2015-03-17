/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ExceptionHttpServletResponseWrapper extends
  HttpServletResponseWrapper {

  public ExceptionHttpServletResponseWrapper(HttpServletResponse response) {
   super(response);
  }

  @Override
  public void setStatus(int statusCode) {
   if (statusCode == 500) {
     super.setStatus(200);
   }
  }
}


