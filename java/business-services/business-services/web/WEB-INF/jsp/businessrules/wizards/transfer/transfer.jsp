<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="transferWizard" namespace="/wizards" method="transferProof" id="transferProofURL"/>
<s:url action="transferWizard" namespace="/wizards" method="transferData" id="transferURL"/>
<s:url action="transferWizard" namespace="/wizards" method="showEvaluations" id="showEvaluationsURL"/>
<s:actionerror/>
<s:fielderror/>
<s:actionmessage/>
<s:form name="transferWizardForm" id="transferWizardForm" action="transferWizard" namespace="/wizards">
    <tr><td>
            <table>
                <s:textfield required="true" id="date" key="label.date" name="date" value="%{dateValue}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.transferWizardForm.date);return false;"/>
            </table>
        </td>
        <td style="vertical-align:middle;">
            <s:a cssClass="button" onclick="this.blur();" href="%{transferURL}" showLoadingText="false" formId="transferWizardForm" indicator="waitDiv" targets="transferDiv">
                <span><s:text name="label.transfer"/></span>
            </s:a>
            <s:a cssClass="button" onclick="this.blur();" href="%{transferProofURL}" showLoadingText="false" formId="transferWizardForm" indicator="waitDiv" targets="transferDiv">
                <span><s:text name="label.transferProofs"/></span>
            </s:a>
        </td>
    </tr>
</s:form>
<s:div id="evaluationsDIV" href="%{showEvaluationsURL}" showLoadingText="false" indicator="waitDiv"/>