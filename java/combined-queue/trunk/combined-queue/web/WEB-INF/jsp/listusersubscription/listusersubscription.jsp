<%-- 
    Document   : listusersubscription
    Created on : Nov 6, 2013, 9:23:36 AM
    Author     : Sebastian
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style type="text/css">
    table.bottomBorder { border-collapse:collapse; }
    table.bottomBorder td, table.bottomBorder th { border-bottom:1px dotted black;padding:5px; }
</style>
</body>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="application.title"/> </title>
    </head>
    <body>
        <jsp:include page="../banner.jsp" />
        <table width="60%" align="center" >
            <s:if test="hasActionErrors()">
                <div style=" color: red" >
                    <s:actionerror/>
                </div>
            </s:if>
        </table>
        <s:form  namespace="/" action="listSubscription" method="post">
            <table width="70%" align="center" >
                <tr>
                    <td>
                        <table>
                            <s:select key="label.userName" name="user" onchange="this.form.submit();"   headerKey="defval" headerValue="%{getText('label.select.header.value')}" list="users"/> 
                        </table>
                    </td>
                </tr>
            </table>
        </s:form>  
        <br/>
        <br/>
        <br/>
        <table width="70%" align="center" class="bottomBorder" style="text-align:center" >
            <tr>
                <th> <s:text name="label.corporate"/></th>
                <th> <s:text name="label.queue"/></th>
            </tr>
            <s:iterator value="subscriptionList"> 
                <tr>
                    <td>
                        <s:property value="operatorQueuePriority.corporateName" /> 
                    </td> 
                    <td>
                        <s:property value="operatorQueuePriority.operatorQueueName" /> 
                    </td>    
                </tr>   
            </s:iterator>  
        </table>                    
    </body>
</html>
