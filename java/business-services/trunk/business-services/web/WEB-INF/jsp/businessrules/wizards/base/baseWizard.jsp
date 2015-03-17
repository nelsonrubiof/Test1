<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="baseWizard" namespace="/wizards" method="firstLoadEPC" id="firstLoadEPCURL"/>
<s:url action="baseWizard" namespace="/wizards" method="firstLoadSTSR" id="firstLoadSTSRURL"/>
<s:url id="showObservationsURL" namespace="/wizards" action="baseWizard" method="showObservations"/>
<s:actionerror/>
<s:fielderror/>
<s:form name="baseWizardForm" id="baseWizardForm" action="baseWizard" namespace="/wizards">
    <tr><td><table>
                <s:select name="storeId"
                          list="stores" key="label.store" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
            </table></td>
        <td><table>
                <s:select name="wizardCustomizing.situationTemplate.id"
                          list="situationTemplates" key="label.situationTemplate" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
            </table></td>
    </tr>
    <tr>
        <td colspan="3">
            <s:div href="%{firstLoadSTSRURL}" showLoadingText="false" indicator="waitDiv" id="STSRDiv" cssStyle="display:block;"/>
        </td>
    </tr>
</s:form>
<s:submit href="%{showObservationsURL}" targets="compliantObservationsDiv" id="showObservationsForCompliantBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="baseWizardForm"/>
<s:submit href="%{showObservationsURL}" targets="notCompliantObservationsDiv" id="showObservationsForNotCompliantBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="baseWizardForm"/>
<s:submit href="%{showObservationsURL}" targets="indicatorObservationsDiv" id="showObservationsForIndicatorBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="baseWizardForm"/>

