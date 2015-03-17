<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="groupAddRoles" namespace="/securitymanagement" action="groupSecurityManagement" method="addRoleToGroup"/>
<s:url id="groupRemoveRoles" namespace="/securitymanagement" action="groupSecurityManagement" method="removeRoleFromGroup"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="groupRoles" list="assignRoles" key="label.roles.selected" labelposition="top" listKey="id" listValue="roleName" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{groupAddRoles}" indicator="waitDiv" targets="rolesDiv" formId="groupEditForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{groupRemoveRoles}" indicator="waitDiv" targets="rolesDiv" formId="groupEditForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table><s:select name="noGroupRoles" list="roles" key="label.roles.noselected" labelposition="top" listKey="id" listValue="roleName" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
    </tr>
</table>