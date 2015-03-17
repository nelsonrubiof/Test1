<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/screen.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/dataTable.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() + "/css/displaytag/maven-base.css" %>">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/maven-theme.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/site.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/screen.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/displaytag/dataTable.css"%>" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() + "/css/button.css"%>" >
        <script language="JavaScript" src="<%=request.getContextPath() + "/js/sha2.js"%>"></script>
        <title>Security Management</title>
        <s:head theme="ajax" debug="false" />
    </head>
    <body>
        
        <%-- 
<s:debug /> <hr>
        --%>
        
        
        <script type="text/javascript">
            <!-- 
            <%-- Convenience method to publish a topic --%>
            function publishTopic(name) {
                dojo.event.topic.publish(name, null);
            }
            
            function changePwd(){
                if(document.getElementById('pwdChange').value!=""){
                    document.getElementById('pwd1').value=hex_sha256(document.getElementById('pwd1').value);
                    document.getElementById('pwd2').value=hex_sha256(document.getElementById('pwd2').value);
                }
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
        
        <s:url id="userManagement" namespace="/securitymanagement" action="userSecurityManagement"/>
        <s:url id="groupManagement" namespace="/securitymanagement" action="groupSecurityManagement"/>        

        <h1><s:text name="periscope.security.welcome" /></h1>
        
        <s:tabbedPanel id="mainTabs" doLayout="false" cssClass="tabs" cssStyle="width:100%;">
            <s:div id="usersDiv" key="tab.securitymanagement.userManagement" cssClass="optionTabs" href="%{userManagement}" listenTopics="refreshIndex"/>
            <s:div id="groupsDiv" key="tab.securitymanagement.groupManagement" cssClass="optionTabs" href="%{groupManagement}" listenTopics="refreshIndex"/>
        </s:tabbedPanel>
    </body>
</html>