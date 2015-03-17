<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="groupFind" namespace="/securitymanagement" action="groupSecurityManagement" method="list"/>
<s:form id="groupFindForm" name="groupFindForm" namespace="/securitymanagement" action="groupSecurityManagement" cssStyle="border:0px;width:100%">
    <tr>
        <td>
            <table><s:textfield name="rolesGroup.name" key="label.name"/></table>
        </td>
        <td>
            <table><s:textfield name="rolesGroup.description" key="label.description"/></table>
        </td>
        <td>
            <s:a cssClass="button" onclick="this.blur();" href="%{groupFind}" showLoadingText="false" formId="groupFindForm" indicator="waitDiv" targets="groupListDiv" >
                <span><s:text name="filter.filter"/></span>
            </s:a>
        </td>
    </tr>
</s:form>
