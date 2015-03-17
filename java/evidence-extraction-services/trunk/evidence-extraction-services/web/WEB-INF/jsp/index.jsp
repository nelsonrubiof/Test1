<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Evidence Manager</title>
        <s:head theme="ajax" debug="false" />

        <script type="text/javascript">
            <!--

            // -->
        </script>

        <style type="text/css">
            <!--
            .style3 {
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 16px;
                color: #333333;
            }

            .style4 {
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 12px;
                color: #333333;
                font-weight: bold;
                text-align: center;

            }

            body{
                overflow-x: hidden;
            }

            #home_top_frame{
                position: relative;
                top: -10px;
                left: -10px;
                overflow: hidden;
            }

            #login_frame{
                position: relative;
                top: -15px;
                overflow-x: hidden;
                overflow-y: auto;
            }
            -->
        </style>
    </head>
    <s:url id="menuPrincipal" action="uploadEvidenceToExternalDevice" method="showMenuPrincipal" namespace="/"/>
    <body>
        <s:div id="login_frame" href="%{menuPrincipal}"/>
    </body>
</html>
