<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="storeSave" namespace="/corporatemanagement" action="stores!saveStore"/>
<s:url id="storeCancel" namespace="/corporatemanagement" action="stores!cancel"/>
<s:url id="showPeriodInterval" namespace="/corporatemanagement" action="stores!showPeriodIntervals"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.store"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="stores!save" id="saveSForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="store.id"/>
    <s:hidden name="store.corporate.id" value="%{corporate.id}"/>
    <s:textfield key="label.name" name="store.name" required="true"/>
    <s:textfield key="label.description" name="store.description" required="true"/>
    <s:textfield key="label.address" name="store.address" required="true"/>
    <s:textfield key="label.corporate" name="store.corporate.name" value="%{corporate.name}" readonly="true"/>
    <s:select name="store.evidenceServicesServer.id" key="label.evidenceServicesServer" list="evidenceServicesServers" listKey="id" listValue="url" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refreshEESS();"/>
    <s:select name="store.evidenceExtractionServicesServer.id" key="label.evidenceExtractionServicesServer" list="evidenceExtractionServicesServers" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:select name="store.country.id" key="label.country" list="countries" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refreshRegion();"/>
    <s:select name="store.region.id" key="label.region" list="regions" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:textfield key="label.timezone" name="store.timeZoneId" required="true"/>
    <s:label key="label.coordenate" />
    <s:textfield key="label.latitude" name="latitudeCoordenate" required="false" cssStyle="width:40px;" />
    <s:textfield key="label.longitude" name="longitudeCoordenate" required="false" cssStyle="width:40px;" />
    <tr>
        <td colspan="2">
            <table width="100%" align="center">
                <s:div href="%{showPeriodInterval}" showLoadingText="false" indicator="waitDiv"  formId="saveSForm" id="addPeriodIntervalDIV"/>
            </table>
        </td>
    </tr>
    
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{storeCancel}" showLoadingText="false" indicator="waitDiv" targets="storesTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{storeSave}" showLoadingText="false" formId="saveSForm" indicator="waitDiv" targets="storesTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:url id="refreshEESS" namespace="/corporatemanagement" action="stores!refreshEESS"/>
<s:submit href="%{refreshEESS}" showLoadingText="false" formId="saveSForm" indicator="waitDiv" targets="storesTabDiv" id="refreshEESSBtn" cssStyle="display:none"/>
<s:url id="refreshRegion" namespace="/corporatemanagement" action="stores!refreshRegions"/>
<s:submit href="%{refreshRegion}" showLoadingText="false" formId="saveSForm" indicator="waitDiv" targets="storesTabDiv" id="refreshRegionBtn" cssStyle="display:none"/>
<s:form name="deletePeriodIntervalForm" id="deletePeriodIntervalForm" cssStyle="display:none;">
    <s:url id="deletePeriodIntervalUrl" action="stores" method="deletePeriodInterval" namespace="/corporatemanagement"/>
    <s:hidden name="periodInterval.id" id="periodIntervalId"/>
    <s:submit href="%{deletePeriodIntervalUrl}" showLoadingText="false" indicator="waitDiv" formId="deletePeriodIntervalForm" cssStyle="display:none;" id="deletePeriodIntervalBtn" targets="addPeriodIntervalDIV"/>
</s:form>
    