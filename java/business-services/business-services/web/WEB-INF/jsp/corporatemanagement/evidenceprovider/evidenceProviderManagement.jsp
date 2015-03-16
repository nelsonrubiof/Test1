<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterEvidenceProvider" namespace="/corporatemanagement" action="evidenceProvider!list"/>
<s:url id="listEvidenceProvider" namespace="/corporatemanagement" action="evidenceProvider!readyList"/>
<s:url id="newEvidenceProvider" namespace="/corporatemanagement" action="evidenceProvider!newEvidenceProvider"/>
<s:url id="showAreaFilter" namespace="/corporatemanagement" action="evidenceProvider!showAreaFilter"/>
<s:form name="findEvidenceProviderForm" id="findEvidenceProviderForm" action="evidenceProvider" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="evidenceProvider.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="evidenceProvider.description" key="label.description" />
            </table>
        </td>
        <td>
            <table>
                <s:select key="label.evidenceProviderType" name="evidenceProvider.evidenceProviderType.id" list="evidenceProviderTypes" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteAll')}"/>
            </table>
        </td>
        <td>
            <table>
                <s:select key="label.store" name="evidenceProvider.store.id" list="stores" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteAll')}" onchange="publishTopic('/refreshAreaFilter');"/>
            </table>
        </td>
        <td>
                <s:div href="%{showAreaFilter}" id="filterAreaDiv" listenTopics="/refreshAreaFilter" formId="findEvidenceProviderForm" theme="ajax" showLoadingText="false"/>
            
        </td>            
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterEvidenceProvider}" showLoadingText="false" formId="findEvidenceProviderForm" indicator="waitDiv" targets="evidenceProviderList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="evidenceProviderList" href="%{listEvidenceProvider}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newEvidenceProvider}" showLoadingText="false" indicator="waitDiv" targets="evidenceProviderTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>