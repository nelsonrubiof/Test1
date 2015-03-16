<%@ taglib prefix="s" uri="/struts-tags"%>


<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setDateHeader("Expires", -6000); //prevents caching at the proxy server
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="application.title"/> </title>
    </head>
    <body>
        <br>
        <h1 align="center"><s:text name="label.errorPage"/></h1>
        <br>           
        <br>
        <s:if test="#attr.noShow == null">
            <a href="<%= request.getContextPath()%>">
                <img src="img/go.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.reloadPage"/>'><s:text name="label.reloadPage"/>
            </a>
        </s:if>
        <p>
            <s:property value="%{exception.message}"/>
        </p>
        <hr/>
        <h3>Technical Details</h3>
        <p>
            <s:property value="%{exceptionStack}"/>
        </p>  
    </body>
</html>
