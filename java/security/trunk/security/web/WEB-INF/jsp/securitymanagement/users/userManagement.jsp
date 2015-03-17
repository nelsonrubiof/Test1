<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="userFilter" namespace="/securitymanagement" action="userSecurityManagement" method="filter"/>
<s:url id="userList" namespace="/securitymanagement" action="userSecurityManagement" method="readyList"/>
<s:url id="userNew" namespace="/securitymanagement" action="userSecurityManagement" method="newUser"/>

<s:div id="userFilterDiv" href="%{userFilter}" indicator="waitDiv" showLoadingText="false"/>
<s:div id="userListDiv" href="%{userList}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{userNew}" showLoadingText="false" indicator="waitDiv" targets="usersDiv" >
    <span><s:text name="label.add"/></span>
</s:a>