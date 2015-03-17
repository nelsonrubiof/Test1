<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="groupFilter" namespace="/securitymanagement" action="groupSecurityManagement" method="filter"/>
<s:url id="groupList" namespace="/securitymanagement" action="groupSecurityManagement" method="readyList"/>
<s:url id="groupNew" namespace="/securitymanagement" action="groupSecurityManagement" method="newGroup"/>

<s:div id="groupFilterDiv" href="%{groupFilter}" indicator="waitDiv" showLoadingText="false"/>
<s:div id="groupListDiv" href="%{groupList}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{groupNew}" showLoadingText="false" indicator="waitDiv" targets="groupsDiv" >
    <span><s:text name="label.add"/></span>
</s:a>