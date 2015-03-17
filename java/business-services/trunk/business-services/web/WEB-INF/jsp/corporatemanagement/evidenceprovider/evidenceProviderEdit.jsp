    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="evidenceProviderSave" namespace="/corporatemanagement" action="evidenceProvider!saveEvidenceProvider"/>
<s:url id="evidenceProviderCancel" namespace="/corporatemanagement" action="evidenceProvider!cancel"/>
<s:url id="showArea" namespace="/corporatemanagement" action="evidenceProvider!showArea"/>
<s:url id="showPattern" namespace="/corporatemanagement" action="evidenceProvider!showPattern"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.evidenceProvider"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="evidenceProvider!saveEvidenceProvider" id="saveEPForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="evidenceProvider.id"/>
    <s:select key="label.store" name="evidenceProvider.store.id" list="stores" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refresh('refreshAreaBtn');"/>
    <s:select key="label.area" name="evidenceProvider.areas.id" list="areas"
              listKey="id"
              listValue="description"
              headerKey="-1"
              required="true" multiple="true" size="15" value="%{evidenceProvider.areas.{id}}" /><!--%{getValuesAreas(evidenceProvider.areas)} headerValue="%{getText('select.selecteOne')}"-->
    <s:textfield key="label.name" name="evidenceProvider.name" required="true"/>
    <s:textfield key="label.description" name="evidenceProvider.description" required="true"/>
    <s:select key="label.evidenceProviderType" name="evidenceProvider.evidenceProviderType.id" list="evidenceProviderTypes" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refresh('refreshPatternBtn');"/>
    <s:textfield key="label.definitionData" name="evidenceProvider.definitionData" required="true" id="definitionData" cssStyle="width:500px;"/>
    <s:if test="%{editable}">
        <s:textfield key="label.templatePath" name="evidenceProvider.templatePath" readonly="true" cssStyle="width:600px;"/>
    </s:if>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceProviderCancel}" showLoadingText="false" indicator="waitDiv" targets="evidenceProviderTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceProviderSave}" showLoadingText="false" formId="saveEPForm" indicator="waitDiv" targets="evidenceProviderTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
<s:submit href="%{showArea}" showLoadingText="false" formId="saveEPForm" indicator="waitDiv" targets="evidenceProviderTabDiv" id="refreshAreaBtn" cssStyle="display:none"/>
<s:submit href="%{showPattern}" showLoadingText="false" formId="saveEPForm" indicator="waitDiv" targets="evidenceProviderTabDiv" id="refreshPatternBtn" cssStyle="display:none"/>
    