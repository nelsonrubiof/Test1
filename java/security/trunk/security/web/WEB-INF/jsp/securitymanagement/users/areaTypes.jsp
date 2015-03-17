<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="userAddAT" namespace="/securitymanagement" action="userSecurityManagement" method="addAreaTypeToUser"/>
<s:url id="userRemoveAT" namespace="/securitymanagement" action="userSecurityManagement" method="removeAreaTypeFromUser"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="userAreaTypes" list="assignAreaTypes" key="label.areatype.selected" labelposition="top" listKey="id" listValue="descriptionWithCorporate" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userAddAT}" indicator="waitDiv" targets="userAreaTypeDiv" formId="userEditForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{userRemoveAT}" indicator="waitDiv" targets="userAreaTypeDiv" formId="userEditForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table><s:select name="noUserAreaTypes" list="areaTypes" key="label.areatype.noselected" labelposition="top" listKey="id" listValue="descriptionWithCorporate" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
    </tr>
</table>