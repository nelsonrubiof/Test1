<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -6000); //prevents caching at the proxy server
%>
<html>
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
        <title>Operator Supervisor Queue Management</title>
        <s:head theme="ajax" debug="false" />
        
        <%-- 
<s:debug /> <hr>
        --%>
        <script type="text/javascript">
            <!-- 
            <%-- Convenience method to publish a topic --%>
            function publishTopic(name) {
                dojo.event.topic.publish(name);
            }

            dojo.event.topic.subscribe("/refreshOperatorQueue", function(data, type, request) {
                var value = document.getElementById('operatorQueues').value;
                document.getElementById('queue').value = value;
                document.getElementById('updateSelectedOperatorQueueLink').click();
            });
            
            dojo.event.topic.subscribe("/refreshPendingList", function(data, type, request) {
                if(type=='load'){
                    publishTopic("/refreshPendingListTable");
                }
            });
               
               
            dojo.event.topic.subscribe("/refreshDeletedList", function(data, type, request) {
                if(type=='load'){
                    publishTopic("/refreshDeletedListTable");
                }
            });
            
            dojo.event.topic.subscribe("/refreshCheckingList", function(data, type, request) {
                if(type=='load'){
                    publishTopic("/refreshCheckingListTable");
                }
            });
            
            function changeSelection(name){
                var objs = document.getElementsByName(name);
                if(objs!=null){
                    for(var i=0; i<objs.length; i++){
                        objs[i].checked = !objs[i].checked;
                    }
                }
            }
            
            function changePwd(){
                document.getElementById('password').value=hex_sha256(document.getElementById('password').value);
            }
            
            function stopRKey(evt) {
                var evt = (evt) ? evt : ((event) ? event : null);
                var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
                if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}
            }

            document.onkeypress = stopRKey; 
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
    <s:url id="head" action="queueManagementIndex" method="showBanner"/>
    <s:url id="body" action="queueManagementIndex" method="showLogin"/> 
    <body>
        <s:div id="home_top_frame" href="%{head}"/>
        <s:div id="principal" href="%{body}" />
        <div style="position: absolute; top: 300px;left: 48%;margin-left: -30px;display:none;border:0" id="waitDiv">           
            <img src="<%= request.getContextPath() + "/img/wait.gif"%>" />
        </div>
        <div style="position: absolute; top: 300px;left: 48%;margin-left: -30px;display:none;border:0" id="changeOperatorqueueDiv">
        </div>
        <div>
            <iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%= request.getContextPath() + "/js/calendar/themes/Normal/ipopeng.htm"%>" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"/>
        </div>
    </body>
</html>