<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url action="transferWizard" namespace="/wizards" method="getOSEData" id="getOSEDataURL"/>
<s:url action="transferWizard" namespace="/wizards" method="getIndicatorData" id="getIndicatorDataURL"/>
<s:actionerror/>
<s:fielderror/>
<s:form name="evaluationsForm" id="evaluationsForm" action="transferWizard" namespace="/wizards">
    <s:hidden id="readyForDownload" name="readyForDownload"/>
    <tr>
        <td colspan="2">
            <span>
                <s:text name="label.getEvaluations"/>
            </span>
        </td>
    </tr>
    <tr>
        <td>
            <table>
                <s:textfield required="true" id="startDate" key="label.startDate" name="startDate" value="%{startDateValue}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.evaluationsForm.startDate);return false;"/>
            </table>
        </td>
        <td>
            <table>
                <s:textfield required="true" id="endDate" key="label.endDate" name="endDate" value="%{endDateValue}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.evaluationsForm.endDate);return false;"/>
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
            <s:a cssClass="button" onclick="this.blur();" href="%{getOSEDataURL}" showLoadingText="false" formId="evaluationsForm" indicator="waitDiv" targets="evaluationsDIV">
                <span><s:text name="label.getOSE"/></span>
            </s:a>
        </td>
        <td style="vertical-align:middle;">
            <s:a cssClass="button" onclick="this.blur();" href="%{getIndicatorDataURL}" showLoadingText="false" formId="evaluationsForm" indicator="waitDiv" targets="evaluationsDIV">
                <span><s:text name="label.getIndicator"/></span>
            </s:a>
        </td>
    </tr>
</s:form>
<s:url id="fakeURL" action="transferWizard" namespace="/wizards" method="fake"/>
<s:div id="fakeDIV" notifyTopics="/downloadFile" href="%{fakeURL}" showLoadingText="false"/>