<%-- 
    Document   : operatorqueuepymgr
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
<script type="text/javascript" >

    function getQueues(obj) {
        obj.form.submit();
    }

    function isNumberKey(evt) {
        var charCode = (evt.which) ? evt.which : event.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;

        return true;
    }

    function validateNewObject() {

        var corporateAlert = "<s:text name="alert.corporate.select"/>";
        var queueAlert = "<s:text name="alert.queue.select"/>";
        var priorityAlert = "<s:text name="alert.priority.input"/>";
        var message = "";
        if (document.getElementsByName("operatorQueuePriority.corporateId")[0].value == -1) {
            message = message + corporateAlert + "\n";
        }
        if (document.getElementsByName("operatorQueuePriority.operatorQueueName")[0].value == -1) {
            message = message + queueAlert + "\n";
        }
        if (document.getElementsByName("operatorQueuePriority.priority")[0].value == ""
                || document.getElementsByName("operatorQueuePriority.priority")[0].value == null) {
            message = message + priorityAlert + "\n";
        }
        if (message !== "") {
            alert(message);
            return false;
        }
        return true;
    }
    
    function confirmDelete() {
        var deleteMessage = "<s:text name="alert.corporate.delete.current"/>";
        if (confirm(deleteMessage)) {
            return true;
        }
        return false;
    }

</script>
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
        <s:form  namespace="/" action="editOperatorQueuePriority" method="post">
            <table width="70%" align="center" >
                <tr>
                    <td> 
                        <table>   
                            <s:select  key="label.corporate"  name="operatorQueuePriority.corporateId" onchange="getQueues(this)" listValue="name" listKey="id"  headerKey="-1" headerValue="%{getText('label.select.header.value')}" list="corporates"/>
                        </table>      
                    </td> 
                    <td>  
                        <table>   
                            <s:select  key="label.queue" name="operatorQueuePriority.operatorQueueName" listValue="name" listKey="name"  headerKey="-1" headerValue="%{getText('label.select.header.value')}" list="queuesForCorporate"/>
                        </table>    
                    </td>
                    <td>  
                        <table>
                            <s:textfield maxlength="3" size="3" key="label.priority" name="operatorQueuePriority.priority" onkeypress="return isNumberKey(event)"/>
                        </table>
                    </td>
                    <td>
                        <table>  
                            <s:submit key="label.addnew" onclick="return validateNewObject()" type="button" name="addNew" value="true" />
                        </table>
                    </td>
                </tr>
            </table>
            <br/>
            <br/>
            <br/> 
            <table width="60%" align="center"  class="bottomBorder" style="text-align:center">
                <tr>
                    <th><s:text name="label.corporate"/></th>
                    <th><s:text name="label.queue"/></th>
                    <th><s:text name="label.priority"/></th>
                    <th><s:text name="label.action"/></th>
                </tr>
                <s:iterator value="operatorQueuePriorities" status="ctr">
                    <tr>    
                        <td><s:property  value="corporateName" /></td>
                        <td><s:property value="operatorQueueName" /></td>
                        <td><s:textfield maxlength="3" size="3" theme="simple"  name="operatorQueuePriorities[%{#ctr.index}].priority"/></td>
                        <s:hidden name="operatorQueuePriorities[%{#ctr.index}].corporateName" />
                        <s:hidden name="operatorQueuePriorities[%{#ctr.index}].operatorQueueName" />
                        <s:hidden name="operatorQueuePriorities[%{#ctr.index}].corporateId" />
                        <s:hidden name="operatorQueuePriorities[%{#ctr.index}].id" />
                        <td><s:submit theme="simple" key="label.edit" type="button" name="editCurrent" value="%{#ctr.index}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:submit theme="simple" key="label.delete" onclick="return confirmDelete()" type="button" name="deleteCurrent" value="%{#ctr.index}" /></td> 
                    </tr>      
                </s:iterator>    
            </table>            
        </s:form>                
    </body>
</html>
