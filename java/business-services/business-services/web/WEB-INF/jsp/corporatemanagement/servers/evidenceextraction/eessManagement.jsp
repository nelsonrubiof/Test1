<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterEvidenceExtractionServices" namespace="/corporatemanagement" action="evidenceExtractionServices!list"/>
<s:url id="listEvidenceExtractionServices" namespace="/corporatemanagement" action="evidenceExtractionServices!readyList"/>
<s:url id="newEvidenceExtractionServices" namespace="/corporatemanagement" action="evidenceExtractionServices!newEESS"/>
<s:form name="findEvidenceExtractionServices" id="findEvidenceExtractionServices" action="evidenceExtractionServices" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="evidenceExtractionServicesServer.url" key="label.address" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterEvidenceExtractionServices}" showLoadingText="false" formId="findEvidenceExtractionServices" indicator="waitDiv" targets="evidenceExtractionServicesList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="evidenceExtractionServicesList" href="%{listEvidenceExtractionServices}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newEvidenceExtractionServices}" showLoadingText="false" indicator="waitDiv" targets="evidenceExtractionTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>