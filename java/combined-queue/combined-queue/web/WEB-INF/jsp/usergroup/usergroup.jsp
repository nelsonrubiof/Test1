<%-- 
    Document   : usersubscription
    Created on : Nov 6, 2013, 9:23:36 AM
    Author     : Sebastian
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" >
    var confirmdelete = "<s:text name="alert.user.group.delete"/>";
    var confirmcancel = "<s:text name="alert.user.group.changes.lost"/>";
    function confirmDelete() {
        if (confirm(confirmdelete)) {
            setUnChanged();
            return true;
        }
        return false;
    }
    function confirmCancel() {
        if (confirm(confirmcancel)) {
            setUnChanged();
            return true;
        }
        return false;
    }
    function setChanged() {
        if (document.getElementsByName("selectedOperatorGroup")[0].value != -1) {
            document.getElementsByName("selectedOperatorGroup")[0].disabled = true;
        }
    }
    function setUnChanged() {
        document.getElementsByName("selectedOperatorGroup")[0].disabled = false;
    }
    function saveList() {
        setUnChanged();
        return true;
    }
    function proceed(obj) {
        obj.form.submit();
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
            <s:if test="hasActionMessages()">
                <div style=" color: green" >
                    <s:actionmessage/>
                </div>
            </s:if> 
            <s:if test="hasActionErrors()">
                <div style=" color: red" >
                    <s:actionerror/>
                </div>
            </s:if> 
        </table> 
        <s:form  namespace="/" action="userGroup" method="post">
            <table width="40%" align="center" >
                <tr>
                    <td> 
                        <table> 
                            <s:textfield  maxlength="50" size="30" key="label.group.name" name="newGroup.groupName"/>
                        </table> 
                    </td>   
                    <td>
                        <table>  
                            <s:submit key="label.addnew" type="button" name="saveNewGroup" value="true" />
                        </table>
                    </td>
                </tr>
            </table> 
            <br/>
            <br/>
            <br/>
            <br/>
            <table width="40%" align="center" >
                <tr>
                    <td> 
                        <table> 
                            <s:select key="label.user.group"  onchange="return proceed(this)" name="selectedOperatorGroup"  listValue="groupName" listKey="id"  headerKey="-1" headerValue="%{getText('label.select.header.value')}" list="operatorsGroups"/>
                        </table> 
                    </td> 
                    <td>
                        <table>
                            <s:submit key="label.delete" type="button" onclick="return confirmDelete()" name="deleteGroup" value="true" />
                        </table>
                    </td>   
                </tr>
            </table> 
            <br/>
            <br/>
            <br/>
            <table width="60%" align="center"> 
                <tr>   
                    <td>
                        <s:optiontransferselect 
                            key="label.user.groups"
                            leftTitle="%{getText('label.user.groups.available')}"
                            rightTitle="%{getText('label.user.groups.assigned')}"
                            name="usersAvailable" 
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
                    </td>
                </tr>
            </table>
            <br/>
            <br/>
            <br/> 
            <table width="20%" align="center">
                <tr>   
                    <td>
                        <s:submit theme="simple"  key="label.cancel" name="cancel" onclick="return confirmCancel()" value="true" type="button"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:submit theme="simple" onclick="return saveList()" key="label.save" name="saveUserList" value="true" type="button"/>
                    </td>
                </tr>
            </table>   
        </s:form>
    </body>
</html>
