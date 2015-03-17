<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>SCOPIX Logout</title>

        <style type="text/css">
            <!--
            .style3 {
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 14px;
                color: #333333;
            }
            -->
        </style>
    </head>

    <body>
        <p align="center" class="style3">
            <a href="<%= request.getContextPath() + "/customizing.action"%>" target="_parent"><s:text name="logout.redirect"/></a>
        </p>
    </body>
</html>
