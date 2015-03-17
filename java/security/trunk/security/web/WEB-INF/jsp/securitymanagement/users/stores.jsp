<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="userAddStore" namespace="/securitymanagement" action="userSecurityManagement" method="addStoreToUser"/>
<s:url id="userRemoveStore" namespace="/securitymanagement" action="userSecurityManagement" method="removeStoreFromUser"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="userStores" list="assignStores" key="label.store.selected" labelposition="top" listKey="id" listValue="descriptionWithCorporate" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userAddStore}" indicator="waitDiv" targets="userStoreDiv" formId="userEditForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userRemoveStore}" indicator="waitDiv" targets="userStoreDiv" formId="userEditForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table><s:select name="noUserStores" list="stores" key="label.store.noselected" labelposition="top" listKey="id" listValue="descriptionWithCorporate" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
    </tr>
</table>