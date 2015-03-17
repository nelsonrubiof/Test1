<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="userAddCorporate" namespace="/securitymanagement" action="userSecurityManagement" method="addCorporateToUser"/>
<s:url id="userRemoveCorporate" namespace="/securitymanagement" action="userSecurityManagement" method="removeCorporateFromUser"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="userCorporates" list="assignCorporates" key="label.corporate.selected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userAddCorporate}" indicator="waitDiv" targets="userCorporateDiv" formId="userEditForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userRemoveCorporate}" indicator="waitDiv" targets="userCorporateDiv" formId="userEditForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table><s:select name="noUserCorporates" list="corporates" key="label.corporate.noselected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
    </tr>
</table>