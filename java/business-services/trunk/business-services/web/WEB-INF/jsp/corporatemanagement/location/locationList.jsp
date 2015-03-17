<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="saveLocations" namespace="/corporatemanagement" action="locationEP!save"/>
<s:form id="saveLocationForm" name="saveLocationForm" action="locationEP" namespace="/corporatemanagement">
    <s:hidden name="areaId" id="areaId" value="%{areaId}" />
    <table class="tableLocations">
        <tr>
            <th>&nbsp;</th>
            <th><s:text name="label.left"/></th>
            <th><s:text name="label.right"/></th>
            <th><s:text name="label.top"/></th>
            <th><s:text name="label.bottom"/></th>
            <th><s:text name="label.default"/></th>
            <th><s:text name="label.viewOrder"/></th>
        </tr>

        <s:iterator value="%{evidenceProviderWithRelationses}">
            <tr>
                <td>
                    <s:hidden name="%{evidenceProvider.id}"/>
                    <s:text name="%{evidenceProvider.description}"/>
                </td>
                <td>
                    <table>
                        <s:select name="left_%{evidenceProvider.id}" list="%{evidenceProviders(evidenceProvider.id)}" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" value="%{leftPosition.evidenceProviderTo.id}"/>
                    </table>
                </td>
                <td>
                    <table>
                        <s:select name="right_%{evidenceProvider.id}" list="%{evidenceProviders(evidenceProvider.id)}" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" value="%{rightPosition.evidenceProviderTo.id}"/>
                    </table>
                </td>
                <td>
                    <table>
                        <s:select name="top_%{evidenceProvider.id}" list="%{evidenceProviders(evidenceProvider.id)}" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" value="%{topPosition.evidenceProviderTo.id}"/>
                    </table>
                </td>
                <td>
                    <table>
                        <s:select name="bottom_%{evidenceProvider.id}" list="%{evidenceProviders(evidenceProvider.id)}" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" value="%{bottomPosition.evidenceProviderTo.id}"/>
                    </table>
                </td>
                <td>
                    <table>
                        <input type="radio" name="defaultEvidenceProvider" id="defaultEvidenceProvider" value="${evidenceProvider.id}"
                               <s:if test="%{defaultEvidenceProvider!=null && defaultEvidenceProvider==true}">
                                   checked
                               </s:if>
                               >
                    </table>
                </td>
                <td>
                    <table>
                        <s:select name="viewOrder_%{evidenceProvider.id}" list="%{positions}" headerKey="-1" headerValue="%{getText('select.selecteOne')}" value="%{viewOrder}"/>
                    </table>
                </td>
            </tr>
        </s:iterator>

    </table>
</s:form>
<s:a cssClass="button" onclick="this.blur();" href="%{saveLocations}" showLoadingText="false" indicator="waitDiv" targets="locationsTabDiv" formId="saveLocationForm">
    <span><s:text name="label.save"/></span>
</s:a>