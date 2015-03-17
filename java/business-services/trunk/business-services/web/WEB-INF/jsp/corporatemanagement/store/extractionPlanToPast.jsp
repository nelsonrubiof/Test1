<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="sendExtractionPlanToPastURL" action="stores" namespace="/corporatemanagement" method="sendExtractionPlanToPast"/>
<s:url id="gotoExtractionPlanResultURL" action="stores" namespace="/corporatemanagement" method="gotoExtractionPlanResult"/>
<s:url id="backURL" action="stores" namespace="/corporatemanagement" method="execute"/>

<s:url id="stAddStore" action="stores"  namespace="/corporatemanagement" method="addStore"/>
<s:url id="stRemoveStore"  action="stores"   namespace="/corporatemanagement" method="removeStore"/>
<s:div id="storeDetailExtractionPlanToPast">
    <s:form action="stores" id="extractionPlanPastForm" name="extractionPlanPastForm">
        <tr>
            <td>
                <table>
                    <s:textfield required="true" id="filterDate" key="filter.date" name="extractionPlanToPastDate" value="%{yesterday}" size="15" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.extractionPlanPastForm.filterDate);return false;"/>
                </table>
            </td>        
            <td align="center" width="40%">
                <table>
                    <s:select name="storesAssignedIds" list="assignedStore" key="label.store.selected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>
                </table>
            </td>
            <td align="center" width="20%">
                <table>
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr>
                        <td>
                            <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{stAddStore}" indicator="waitDiv" targets="storeDetailExtractionPlanToPast" formId="extractionPlanPastForm">
                                <span><s:text name="label.add"/></span>
                            </s:a>
                        </td>
                    </tr>                
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr>
                        <td>
                            <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{stRemoveStore}" indicator="waitDiv" targets="storeDetailExtractionPlanToPast" formId="extractionPlanPastForm">
                                <span><s:text name="label.remove"/></span>
                            </s:a>
                        </td>
                    </tr>
                </table>
            </td>
            <td align="center" width="40%">
                <table><s:select name="storesNotAssignedIds" list="noAssignedStore" key="label.store" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
            </td>
        </tr>
        
        <tr>
            <td colspan="2" align="left">
                <table>
                    <s:a href="%{sendExtractionPlanToPastURL}" showLoadingText="false" formId="extractionPlanPastForm" indicator="waitDiv" targets="evidenceRequestListDIV" cssClass="button">
                        <span><s:text name="label.sendExtractionPlanToPast"/></span>
                    </s:a>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <table>
                    <s:div id="evidenceRequestListDIV" href="%{gotoExtractionPlanResultURL}" indicator="waitDiv" showLoadingText="false"/>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="left">
                <table>
                    <s:a href="%{backURL}" showLoadingText="false" indicator="waitDiv" targets="storesTabDiv" cssClass="button">
                        <span><s:text name="label.back"/></span>
                    </s:a>
                </table>
            </td>
        </tr>
    </s:form>
</s:div>