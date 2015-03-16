<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="sendCloseSituationURL" action="stores" namespace="/corporatemanagement" method="sendCloseSituation"/>
<s:url id="goToCloseSituationResultURL" action="stores" namespace="/corporatemanagement" method="goToCloseSituationResult"/>
<s:url id="backURL" action="stores" namespace="/corporatemanagement" method="execute"/>

<s:url id="stAddStore" action="stores"  namespace="/corporatemanagement" method="addStoreCloseSituation"/>
<s:url id="stRemoveStore"  action="stores"   namespace="/corporatemanagement" method="removeStoreCloseSituation"/>
<s:div id="storeDetailExtractionPlanToPast">
    <s:form action="stores" id="closeSituationForm" name="closeSituationForm">
        <tr>
            <td>
                <table>
                    <s:textfield required="true" id="filterDate" key="filter.date" name="closeSituationDate" value="%{yesterday}" size="15" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.closeSituationForm.filterDate);return false;"/>
                </table>
            </td>        
            <td align="center" width="40%">
                <table>
                    <s:select name="storesAssignedIds" list="assignedStore" key="label.store" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>
                </table>
            </td>            
            <td align="center" width="40%">
                <table><s:select name="sitautionsAssignedIds" list="assignedSituationTemplate" key="label.situationTemplate" labelposition="top" listKey="id" listValue="name" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
            </td>
        </tr>
        
        <tr>
            <td colspan="2" align="left">
                <table>
                    <s:a href="%{sendCloseSituationURL}" showLoadingText="false" formId="closeSituationForm" indicator="waitDiv" targets="situationListDIV" cssClass="button">
                        <span><s:text name="label.sendCloseSituation"/></span>
                    </s:a>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <table>
                    <s:div id="situationListDIV" href="%{goToCloseSituationResultURL}" indicator="waitDiv" showLoadingText="false"/>
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