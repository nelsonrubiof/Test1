<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="evidenceRequestSave" namespace="/businessrulesmanagement" action="evidenceRequest!saveEvidenceRequest"/>
<s:url id="evidenceRequestCancel" namespace="/businessrulesmanagement" action="evidenceRequest!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.evidenceRequest"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="evidenceRequest!saveEvidenceRequest" id="saveERForm" cssStyle="border:1px;width:100%;">
    <s:hidden name="editable"/>
    <s:hidden name="evidenceRequest.id"/>
    <s:textfield key="label.hour" name="hora" required="true"/>
    <s:select name="evidenceRequest.evidenceProvider.id" key="label.evidenceProvider" list="evidenceProviderList" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>    
    <s:select name="type" key="label.evidenceType" list="evidenceTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="showDuration(this.value);"/>
    <s:textfield key="label.duration" name="duration" disabled="%{type!='VIDEO'}" id="duration"/>
    <s:select list="daysList" name="evidenceRequest.day" key="label.days" required="true"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceRequestCancel}" showLoadingText="false" indicator="waitDiv" targets="evidenceRequestTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceRequestSave}" showLoadingText="false" formId="saveERForm" indicator="waitDiv" targets="evidenceRequestTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
    
    