<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="groupCancel" namespace="/securitymanagement" action="groupSecurityManagement" method="cancel"/>
<s:url id="groupSave" namespace="/securitymanagement" action="groupSecurityManagement" method="save"/>
<s:url id="groupRoles" namespace="/securitymanagement" action="groupSecurityManagement" method="showRoles"/>
<s:if test="editable">
    <h5 align="center"><s:text name="periscope.security.editGoup" /></h5>
</s:if>
<s:else>    
    <h5 align="center"><s:text name="periscope.security.createGroup" /></h5>
</s:else>
<s:actionerror/>
<s:fielderror/>
<s:form id="groupEditForm" name="groupEditForm" namespace="/securitymanagement" action="groupSecurityManagement" cssStyle="border:1px;width:100%">
        <s:hidden name="editable"/>        
        <s:hidden name="rolesGroup.id"/>
        <s:textfield name="rolesGroup.name" key="label.name" required="true"/>
        <s:textfield name="rolesGroup.description" key="label.description" required="true"/>
    <tr>
        <td align="center" colspan="2"><s:div id="rolesDiv" href="%{groupRoles}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>    
    <tr>
        <td>
            <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{groupCancel}" indicator="waitDiv" targets="groupsDiv" >
                <span><s:text name="label.cancel"/></span>
            </s:a>            
        </td>
        <td style="text-align:right">
            <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{groupSave}" formId="groupEditForm" indicator="waitDiv" targets="groupsDiv">
                <span><s:text name="label.save"/></span>
            </s:a>
        </td>        
    </tr>
</s:form>
    