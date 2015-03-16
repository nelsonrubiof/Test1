<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="formId" value="%{'findEvidenceRequestForm'}" scope="request"/>

<s:url id="filterEvidenceRequest" namespace="/businessrulesmanagement" action="evidenceRequest!list"/>
<s:url id="listEvidenceRequest" namespace="/businessrulesmanagement" action="evidenceRequest!readyList"/>
<s:url id="newEvidenceRequest" namespace="/businessrulesmanagement" action="evidenceRequest!newEvidenceRequest"/>
<s:form name="findEvidenceRequestForm" id="findEvidenceRequestForm" action="evidenceRequest" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:select name="evidenceRequest.evidenceProvider.id" key="label.evidenceProvider" list="evidenceProviderList" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="initHour" key="label.initHour" maxlength="8" cssStyle="width:60px"/>
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="endHour" key="label.endHour" maxlength="8" cssStyle="width:60px"/>
            </table>
        </td>
        <td>
            <table>
                <s:select name="type" key="label.evidenceType" list="evidenceTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteAll')}"/>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterEvidenceRequest}" showLoadingText="false" formId="findEvidenceRequestForm" indicator="waitDiv" targets="evidenceRequestList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="10">
                <s:div id="evidenceRequestList" href="%{listEvidenceRequest}" indicator="waitDiv" showLoadingText="false" cssStyle="width:100%;"/>            
        </td>
    </tr>
</s:form>
<s:form id="deleteEvidenceRequestForm" action="evidenceRequest" cssStyle="display:none;">
    <s:hidden name="evidenceRequest.id" id="evidenceRequestId"/>
    <s:url id="deleteEvidenceRequest" namespace="/businessrulesmanagement" action="evidenceRequest" method="deleteEvidenceRequest"/>
    <s:submit href="%{deleteEvidenceRequest}" targets="evidenceRequestList" id="deleteEvidencerequestBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteEvidenceRequestForm"/>
</s:form>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newEvidenceRequest}" showLoadingText="false" indicator="waitDiv" targets="evidenceRequestTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>