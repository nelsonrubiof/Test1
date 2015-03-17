<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="userAddGroup" namespace="/securitymanagement" action="userSecurityManagement" method="addGroupToUser"/>
<s:url id="userRemoveGroup" namespace="/securitymanagement" action="userSecurityManagement" method="removeGroupFromUser"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="userGroups" list="assignRolesGroup" key="label.group.selected" labelposition="top" listKey="id" listValue="name" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userAddGroup}" indicator="waitDiv" targets="userGroupDiv" formId="userEditForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userRemoveGroup}" indicator="waitDiv" targets="userGroupDiv" formId="userEditForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table><s:select name="noUserGroups" list="rolesGroup" key="label.group.noselected" labelposition="top" listKey="id" listValue="name" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
    </tr>
</table>