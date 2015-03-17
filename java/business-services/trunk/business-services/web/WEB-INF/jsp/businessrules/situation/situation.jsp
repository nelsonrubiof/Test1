<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterSituation" namespace="/businessrulesmanagement" action="situation!list"/>
<s:url id="listSituation" namespace="/businessrulesmanagement" action="situation!readyList"/>
<s:url id="newSituation" namespace="/businessrulesmanagement" action="situation!newSituation"/>
<s:form name="findSituationForm" id="findSituationForm" action="situation" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:select name="situationForm.situationTemplate.id" key="label.situationTemplate" list="situationTemplate" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteAll')}"/><tr>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterSituation}" showLoadingText="false" formId="findSituationForm" indicator="waitDiv" targets="situationList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="10">
            <s:div id="situationList" href="%{listSituation}" indicator="waitDiv" showLoadingText="false" cssStyle="width:100%;"/>
        </td>
    </tr>
</s:form>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newSituation}" showLoadingText="false" indicator="waitDiv" targets="situationTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>
<s:form id="deleteSituationForm" action="situation" cssStyle="display:none;">
    <s:hidden name="situationForm.id" id="situationId"/>
    <s:url id="deleteSituation" namespace="/businessrulesmanagement" action="situation" method="deleteSituation"/>
    <s:submit href="%{deleteSituation}" targets="situationList" id="deleteSituationBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteSituationForm"/>
</s:form>