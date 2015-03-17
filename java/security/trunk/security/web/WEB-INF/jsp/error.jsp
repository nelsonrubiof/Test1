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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/screen.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/dataTable.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() + "/css/displaytag/maven-base.css" %>">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/maven-theme.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/site.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/screen.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/dataTable.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/button.css"%>" >
        <script language="JavaScript" src="<%=request.getContextPath() + "/js/sha2.js"%>"></script>
        <title>Periscope Customizing</title>
        <s:head theme="ajax" debug="false" />
    </head>
    <body>
        <br>
        <h1 align="center"><s:text name="label.errorPage"/></h1>
        <br>
        <s:actionerror/>             
        <br>
        <a href="<%= request.getContextPath()%>">
            <img src="/business-services/img/go.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.reloadPage"/>'><s:text name="label.reloadPage"/>
        </a>
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
