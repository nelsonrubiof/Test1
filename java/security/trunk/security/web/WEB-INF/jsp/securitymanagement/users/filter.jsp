<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="userFind" namespace="/securitymanagement" action="userSecurityManagement" method="list"/>
<s:form id="userFindForm" name="userFindForm" namespace="/securitymanagement" action="userSecurityManagement" cssStyle="border:0px;width:100%">
    <tr>
        <td>
            <table><s:textfield name="periscopeUser.name" key="label.username"/></table>
        </td>
        <td>
            <s:a cssClass="button" onclick="this.blur();" href="%{userFind}" showLoadingText="false" formId="userFindForm" indicator="waitDiv" targets="userListDiv" >
                <span><s:text name="filter.filter"/></span>
            </s:a>
        </td>
    </tr>
</s:form>
