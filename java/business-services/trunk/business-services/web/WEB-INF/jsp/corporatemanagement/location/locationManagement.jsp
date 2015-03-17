<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterLocations" namespace="/corporatemanagement" action="locationEP!list"/>
<s:url id="showAreaFilter" namespace="/corporatemanagement" action="locationEP!showAreaFilter"/>
<s:form name="findLocationsForm" id="findLocationsForm" action="locationEP" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:select key="label.store" name="storeId" list="stores" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshAreaLocationsFilter');" required="true"/>
            </table>
        </td>
        <td>
            
                <s:div href="%{showAreaFilter}" id="filterAreaDiv" listenTopics="/refreshAreaLocationsFilter" formId="findLocationsForm" theme="ajax" showLoadingText="false"/>
            
        </td>            
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterLocations}" showLoadingText="false" formId="findLocationsForm" indicator="waitDiv" targets="locationsList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="locationsList"  indicator="waitDiv" showLoadingText="false"/>
    