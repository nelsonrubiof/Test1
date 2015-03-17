<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="usrCancel" namespace="/securitymanagement" action="userSecurityManagement" method="cancel"/>
<s:url id="usrSave" namespace="/securitymanagement" action="userSecurityManagement" method="save"/>
<s:url id="userShowGroups" namespace="/securitymanagement" action="userSecurityManagement" method="showGroups"/>
<s:url id="userShowStores" namespace="/securitymanagement" action="userSecurityManagement" method="showStores"/>
<s:url id="userShowCorporates" namespace="/securitymanagement" action="userSecurityManagement" method="showCorporates"/>
<s:url id="userShowAreaTypes" namespace="/securitymanagement" action="userSecurityManagement" method="showAreaTypes"/>
<s:if test="editable">
    <h5 align="center"><s:text name="periscope.security.editUser" /></h5>
</s:if>
<s:else>    
    <h5 align="center"><s:text name="periscope.security.createUser" /></h5>
</s:else>
<s:actionerror/>
<s:fielderror/>
<s:form id="userEditForm" name="userEditForm" namespace="/securitymanagement" action="userSecurityManagement" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <input type="hidden" name="pwdChange" id="pwdChange"/>
    <s:hidden name="periscopeUser.id"/>
    <s:textfield name="periscopeUser.name" key="label.username" required="true"/>
    <s:select name="userState" key="label.userState" required="true" list="userStates" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('label.selectOne')}"/>
    <s:textfield name="periscopeUser.fullName" key="label.fullName" required="true"/>
    <s:select name="periscopeUser.mainCorporate.id" key="label.mainCorporate" required="true" list="allCorporates" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('label.selectOne')}"/>
    <s:textfield name="periscopeUser.jobPosition" key="label.jobPosition" required="true"/>
    <s:textfield name="periscopeUser.email" key="label.email" required="true"/>
    <s:password id="pwd1" name="periscopeUser.password" key="label.password" required="true" showPassword="true" onchange="document.getElementById('pwdChange').value='true'"/>
    <s:password id="pwd2" name="password2" key="label.repeatPassword" required="true" showPassword="true" onchange="document.getElementById('pwdChange').value='true'"/>
    <tr>
        <td align="center" colspan="2" ><strong><s:text name="label.rolesGroup"/></strong></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><s:div key="label.rolesGroup" labelposition="top" id="userGroupDiv" href="%{userShowGroups}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><strong><s:text name="label.areaType"/></strong></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><s:div key="label.areaType" labelposition="top" id="userAreaTypeDiv" href="%{userShowAreaTypes}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><strong><s:text name="label.store"/></strong></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><s:div key="label.store" labelposition="top" id="userStoreDiv" href="%{userShowStores}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><strong><s:text name="label.corporate"/></strong></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><s:div key="label.corporate" labelposition="top" id="userCorporateDiv" href="%{userShowCorporates}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>
    <tr>
        <td>
            <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{usrCancel}" indicator="waitDiv" targets="usersDiv" >
                <span><s:text name="label.cancel"/></span>
            </s:a>            
        </td>
        <td style="text-align:right">
            <s:a cssClass="button" onclick="changePwd();this.blur();" showLoadingText="false" href="%{usrSave}" formId="userEditForm" indicator="waitDiv" targets="usersDiv">
                <span><s:text name="label.save"/></span>
            </s:a>
        </td>        
    </tr>
</s:form>
    