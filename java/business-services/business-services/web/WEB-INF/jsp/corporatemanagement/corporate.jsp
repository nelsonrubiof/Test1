<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="addCorporate" namespace="/corporatemanagement" action="corporate" method="newCorporate"/>
<s:url id="updateCorporate" namespace="/corporatemanagement" action="corporate" method="updateCorporate"/>
<s:url id="enableEditMode" namespace="/corporatemanagement" action="corporate" method="enableEditMode"/>
<s:url id="uploadLogo" namespace="/corporatemanagement" action="corporate" method="uploadFile"/>
<s:actionerror/>
<s:form id="corporateForm" action="corporate" namespace="/corporatemanagement" >
    <s:hidden name="corporate.logo"/>
    <s:textfield name="corporate.name" key="label.corporate" required="true" disabled="%{editMode==false}" />
    <p>
    <s:textfield name="corporate.description" key="label.description" required="true" disabled="%{editMode==false}" />
    <p>
    <s:textfield name="corporate.uniqueCorporateId" key="label.uniqueCorporateId" required="true" disabled="%{editMode==false}" />
    <s:checkbox name="logo" label="Logo" value="%{corporate.logo!=null}" disabled="true"/>
    <p>
    <s:if test="%{corporate!=null}">
        <s:if test="%{editMode==true}">
            <s:a cssClass="button" onclick="this.blur();" href="%{updateCorporate}" showLoadingText="false" indicator="waitDiv" targets="corporateTabDiv" formId="corporateForm">
                <span><s:text name="label.save"/></span>
            </s:a>
        </s:if>
        <s:else>
            <s:a cssClass="button" onclick="this.blur();" href="%{enableEditMode}" showLoadingText="false" indicator="waitDiv" targets="corporateTabDiv" formId="corporateForm">
                <span><s:text name="label.edit"/></span>
            </s:a>
            &nbsp;&nbsp;&nbsp;
            <s:a cssClass="button" onclick="uploadLogo();this.blur();" showLoadingText="false" >
                <span><s:text name="label.uploadLogo"/></span>
            </s:a>
        </s:else>
    </s:if>
    <s:else>
        <s:a cssClass="button" onclick="this.blur();" href="%{addCorporate}" showLoadingText="false" indicator="waitDiv" targets="corporateTabDiv" formId="corporateForm">
            <span><s:text name="label.add"/></span>
        </s:a>
    </s:else>
</s:form>