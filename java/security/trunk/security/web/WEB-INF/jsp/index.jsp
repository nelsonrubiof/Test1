<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
         
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
            
            function publishTopic(name) {
                dojo.event.topic.publish(name, null);
            }
            
            function disableYesNoType(){
                var id= document.getElementById('metricType').value;
                if(id=="YES_NO"){
                    document.getElementById('yesNoType').disabled=false;
                }else{
                    document.getElementById('yesNoType').disabled=true;
                }
            }
            
            function deleteItem(id, hiddenId, buttonId){
                var resp = confirm('<s:text name="confirm.delete"/>');
                if(resp){
                    document.getElementById(hiddenId).value=id;
                    document.getElementById(buttonId).click();
                }
            }
            
            function updatePlan(id, hiddenId, buttonId){
                var resp = confirm('<s:text name="confirm.updatePlan"/>'+ ' id=' + id + '; hiddenId ='+ hiddenId + '; buttonId =' +buttonId);
                if(resp){
                    document.getElementById(hiddenId).value=id;
                    document.getElementById(buttonId).click();
                }
            }
            
            function changePwdLogin(){
                document.getElementById('password').value=hex_sha256(document.getElementById('password').value);
            }
            
            function changePwd(){
                if(document.getElementById('pwdChange').value!=""){
                    document.getElementById('pwd1').value=hex_sha256(document.getElementById('pwd1').value);
                    document.getElementById('pwd2').value=hex_sha256(document.getElementById('pwd2').value);
                }
            }
            
            function refreshEESS(){
                document.getElementById('refreshEESSBtn').click();
            }
            
            function refreshRegion(){
                document.getElementById('refreshRegionBtn').click();
            }
            
            function refresh(id){
                document.getElementById(id).click();
            }
            
            function showDuration(value){
                if(value=='VIDEO'){
                    document.getElementById('duration').disabled=false;
                }else{
                    document.getElementById('duration').disabled=true;
                }
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
    <s:url id="head" action="customizing" method="showBanner" namespace="/"/>
    <s:url id="body" action="customizing" method="showLogin" namespace="/"/> 
    <body>
        <s:div id="home_top_frame" href="%{head}"/>
        <s:div id="principal" href="%{body}" />
        <div style="position: absolute; top: 300px;left: 48%;margin-left: -30px;display:none;border:0" id="waitDiv">           
            <img src="<%= request.getContextPath() + "/img/wait.gif"%>" />
        </div>
    </body>
</html>