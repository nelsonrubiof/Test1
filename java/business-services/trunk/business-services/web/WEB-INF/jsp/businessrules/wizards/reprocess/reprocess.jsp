<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="reProcessWizard" namespace="/wizards" method="reprocess" id="reprocessURL"/>
<s:actionerror/>
<s:fielderror/>
<s:actionmessage/>
<s:form name="reprocessWizardForm" id="reprocessWizardForm" action="reProcessWizard" namespace="/wizards">
    <tr><td>
            <table>
                <s:textfield required="true" id="startDateReprocess" key="label.startDate" name="startDate" value="%{startDateValue}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.reprocessWizardForm.startDateReprocess);return false;"/>
            </table>
        </td>
        <td>
            <table>
                <s:textfield required="true" id="endDateReprocess" key="label.endDate" name="endDate" value="%{endDateValue}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.reprocessWizardForm.endDateReprocess);return false;"/>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table>
                <s:select name="situationTemplateIds" key="label.situationTemplate" list="situationTemplates" listKey="id" listValue="name" multiple="true" size="10" cssStyle="width:300px;" required="true"/>
            </table>
        </td>
        <td>
            <table>
                <s:select name="storeIds" key="label.store" list="stores" listKey="id" listValue="description" multiple="true" size="10" cssStyle="width:300px;" required="true"/>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <s:a cssClass="button" onclick="this.blur();" href="%{reprocessURL}" showLoadingText="false" formId="reprocessWizardForm" indicator="waitDiv" targets="reprocessDiv">
                <span><s:text name="label.reprocess"/></span>
            </s:a>
        </td>
        <td>
            &nbsp;
        </td>
    </tr>
</s:form>