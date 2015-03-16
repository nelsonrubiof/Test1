<%-- 
    Document   : usersubscription
    Created on : Nov 6, 2013, 9:23:36 AM
    Author     : Sebastian
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" >
    var changesMessage= "<s:text name="alert.user.subscription.changes.lost"/>";
    function setChanged(){  
       if(document.getElementsByName("selectedQueue")[0].value != -1 && document.getElementsByName("selectedCorporate")[0].value!=-1){ 
            document.getElementsByName("selectedCorporate")[0].disabled=true;
            document.getElementsByName("selectedQueue")[0].disabled=true;
        }
    }
    function setUnChanged(){        
       document.getElementsByName("selectedCorporate")[0].disabled=false;
       document.getElementsByName("selectedQueue")[0].disabled=false;
    }
    function proceedAndClearQueue(obj){
        document.getElementsByName("selectedQueue")[0].value=-1;
        obj.form.submit();
    }
    function proceed(obj){
        obj.form.submit();
    }
    function confirmCancel(){
        if (confirm(changesMessage)) {
            setUnChanged();
            return true;
        }
        return false;
    }
    function saveAction(){
        setUnChanged();
        return true;
    }

</script>
</body>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <s:head theme="simple" debug="false"/>
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
        <s:form  namespace="/" action="subscription" method="post">
            <table width="40%" align="center" >
                <tr>
                    <td> 
                        <table> 
                            <s:select  key="label.corporate" onchange="return proceedAndClearQueue(this)" name="selectedCorporate"  listValue="name" listKey="id"  headerKey="-1" headerValue="%{getText('label.select.header.value')}" list="corporates"/>
                        </table> 
                    </td> 
                    <td>
                        <table>
                            <s:select  key="label.queue"  onchange="return proceed(this)"   name="selectedQueue"  headerKey="-1" headerValue="%{getText('label.select.header.value')}" list="queues"/>
                        </table>
                   </td>   
                </tr>
            </table>  
            <br/>
            <br/>
            <br/> 
            <table width="60%" align="center"> 
                <s:optiontransferselect 
                                        key="label.user.subscription"
                                        name="usersAvailable"     
                                        leftTitle="%{getText('label.user.subscription.available')}"
                                        rightTitle="%{getText('label.user.subscription.assigned')}"
                                        list="availableUsers"
                                        multiple="true"
                                        doubleList="assignedUsers"
                                        doubleName="usersAssigned"
                                        allowSelectAll="false"
                                        allowUpDownOnLeft="false"
                                        allowUpDownOnRight="false"
                                        onchange="setChanged();"
                                        addAllToLeftOnclick="setChanged();"
                                        addAllToRightOnclick="setChanged();"
                                        addToLeftOnclick="setChanged();"
                                        addToRightOnclick="setChanged();"
                                        cssStyle="width:150px;"
                                        doubleCssStyle="width:150px"
                                        />                       
            </table>
            <br/>
            <br/>
            <br/> 
            <table width="20%" align="center">
               <tr>   
                    <td>
                        <s:submit theme="simple"  key="label.cancel" name="cancel" onclick="return confirmCancel()" value="true" type="button"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:submit theme="simple" onclick="return saveAction()" key="label.save" name="save" value="true" type="button"/>
                    </td>
                </tr>
            </table>     
        </s:form>
    </body>
</html>
