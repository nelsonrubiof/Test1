<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterEvidenceServices" namespace="/corporatemanagement" action="evidenceServices!list"/>
<s:url id="listEvidenceServices" namespace="/corporatemanagement" action="evidenceServices!readyList"/>
<s:url id="newEvidenceServices" namespace="/corporatemanagement" action="evidenceServices!newESS"/>
<s:form name="findEvidenceServicesForm" id="findEvidenceServicesForm" action="evidenceServices" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="evidenceServicesServer.url" key="label.url" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="evidenceServicesServer.evidencePath" key="label.evidencePath" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterEvidenceServices}" showLoadingText="false" formId="findEvidenceServicesForm" indicator="waitDiv" targets="evidenceServicesList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="evidenceServicesList" href="%{listEvidenceServices}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newEvidenceServices}" showLoadingText="false" indicator="waitDiv" targets="evidenceServicesTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>