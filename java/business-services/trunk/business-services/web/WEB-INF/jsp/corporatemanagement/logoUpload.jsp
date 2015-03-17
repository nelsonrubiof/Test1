<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", -6000); //prevents caching at the proxy server
%>
<html>
    <head>

        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/screen.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/dataTable.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() + "/css/displaytag/maven-base.css"%>">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/maven-theme.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/site.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/screen.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/button.css"%>" >
        <script language="JavaScript" src="<%=request.getContextPath() + "/js/sha2.js"%>"></script>
        <title>Periscope Customizing</title>
        <s:head theme="ajax" debug="false" />

        <%--
<s:debug /> <hr>
        --%>
        <script type="text/javascript">
            <!--
            <%-- Convenience method to publish a topic --%>

            function sendIt(url){
                document.getElementById('uploadForm').action= url;
                document.getElementById('uploadForm').submit();
            }

            function closeWindow(){
                window.close();
            }

            function showPreview(url){
                document.getElementById('uploadForm').action= url;
                document.getElementById('uploadForm').submit();
            }

            <%--
An attempt at doing double column layout with javascipt.

function performLayout(formId, columns) {
   var formElm = document.getElementById(formId);
   var table = formElm.childNodes[1];
   var rows = table.rows;
   for (var i = 0; i < rows.length - 1; i++) {
     var row1 = rows[i];
     var row2 = rows[i+1];
     var cells2 = row2.cells;
     while (cells2.length) {
        row1.appendChild(cells2[0]);
     }
     table.deleteRow(i+1);
   }
}

function layoutForm(formId, columns) {
    window.setTimeout( function() { performLayout(formId, columns);  }, 0);
}
--%>

    // -->
        </script>
        <script src="../Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
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
    <body>
        <s:url id="previewUrl" namespace="/corporatemanagement" action="corporate!previewFile"/>
        <s:url id="uploadFileUrl" namespace="/corporatemanagement" action="corporate!uploadFile"/>
        <h5 align="center">
            <s:text name="label.modifyTemplatePath"/>&nbsp;<s:text name="label.evidenceProvider"/>
        </h5>
        <s:actionmessage />
        <s:actionerror/>
        <s:form  id="uploadForm" name="uploadForm" cssStyle="border:1px;width:100%" enctype="multipart/form-data" method="POST">
            <s:file name="upload" key="label.uploadImage" id="upload" value="%{fileClientPath}" id="fileClientPath"/>
            <tr>
                <td>
                    <table align="right" style="text-align:right;">
                        <s:a onclick="closeWindow();return false;" cssClass="button">
                            <span><s:text name="label.back"/></span>
                        </s:a>
                    </table>
                </td>
                <td>
                    <table align="right" style="text-align:right;">
                        <s:a onclick="sendIt('%{uploadFileUrl}');return false;" showLoadingText="false" formId="uploadForm" indicator="waitDiv" cssClass="button">
                            <span><s:text name="label.save"/></span>
                        </s:a>
                    </table>
                </td>
                <td>
                    <table align="right" style="text-align:right;">
                        <s:a onclick="showPreview('%{previewUrl}');return false;" showLoadingText="false" indicator="waitDiv" cssClass="button">
                            <span><s:text name="label.viewCurrentLogo"/></span>
                        </s:a>
                    </table>
                </td>
            </tr>
        </s:form>
        <s:if test="#attr.PREVIEW != null">
            <s:url id="showLogoPreview" action="corporate" namespace="/corporatemanagement" method="getLogoPreview"/>
            <img src="${showLogoPreview}" style="position:absolute;left:50px;top:150px;" height="240px" width="320px">
        </s:if>
        <div style="position: absolute; top: 300px;left: 48%;margin-left: -30px;display:none;border:0" id="waitDiv">
            <img src="<%= request.getContextPath() + "/img/wait.gif"%>" />
        </div>
    </body>
</html>

    