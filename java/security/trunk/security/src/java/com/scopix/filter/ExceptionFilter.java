/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ExceptionFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ExceptionHttpServletResponseWrapper wrapper = new ExceptionHttpServletResponseWrapper(httpResponse);

        chain.doFilter(request, wrapper);
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}
