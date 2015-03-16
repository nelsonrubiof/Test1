<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterSituationTemplate" namespace="/templatemanagement" action="situationTemplate!list"/>
<s:url id="listSituationTemplate" namespace="/templatemanagement" action="situationTemplate!readyList"/>
<s:url id="newSituationTemplate" namespace="/templatemanagement" action="situationTemplate!newSituationTemplate"/>
<s:form name="findSituationTemplateForm" id="findSituationTemplateForm" action="sitiationTemplate" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="situationTemplate.name" key="label.name" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterSituationTemplate}" showLoadingText="false" formId="findSituationTemplateForm" indicator="waitDiv" targets="situationTemplateList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="situationTemplateList" href="%{listSituationTemplate}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newSituationTemplate}" showLoadingText="false" indicator="waitDiv" targets="situationDiv" >
    <span><s:text name="label.add"/></span>
</s:a>