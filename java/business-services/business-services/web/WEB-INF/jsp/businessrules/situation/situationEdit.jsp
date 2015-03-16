<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="situationSave" namespace="/businessrulesmanagement" action="situation!saveSituation"/>
<s:url id="situationCancel" namespace="/businessrulesmanagement" action="situation!cancel"/>
<s:url id="showMetrics" namespace="/businessrulesmanagement" action="situation!showMetrics"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.businessRuleManagement.situation"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="situation!saveSituation" id="saveSituationForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="situationForm.id"/>
    <s:textfield key="label.description" name="situationForm.description" required="true"/>
    <s:select name="situationForm.situationTemplate.id" key="label.situationTemplate" list="situationTemplate" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshList');"/><tr>
    <tr>
        <td align="center" colspan="2">
            <s:div id="mDiv" href="%{showMetrics}" showLoadingText="false" formId="saveSituationForm" indicator="waitDiv" cssStyle="text-align:center;" listenTopics="/refreshList"/>
        </td>
    </tr>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{situationCancel}" showLoadingText="false" indicator="waitDiv" targets="situationTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{situationSave}" showLoadingText="false" formId="saveSituationForm" indicator="waitDiv" targets="situationTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
    
    