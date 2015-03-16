<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterSensor" namespace="/corporatemanagement" action="sensor!list"/>
<s:url id="listSensor" namespace="/corporatemanagement" action="sensor!readyList"/>
<s:url id="newSensor" namespace="/corporatemanagement" action="sensor!newSensor"/>
<s:url id="showAreaFilter" namespace="/corporatemanagement" action="sensor!showAreaFilter"/>
<s:form name="findSensorForm" id="findSensorForm" action="sensor" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="sensor.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="sensor.description" key="label.description" />
            </table>
        </td>
        <td>
            <table>
                <s:select key="label.store" name="sensor.store.id" list="stores" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteAll')}" onchange="publishTopic('/refreshAreaSensorFilter');"/>
            </table>
        </td>
        <td>
            
                <s:div href="%{showAreaFilter}" id="filterAreaDiv" listenTopics="/refreshAreaSensorFilter" formId="findSensorForm" theme="ajax" showLoadingText="false"/>
            
        </td>            
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterSensor}" showLoadingText="false" formId="findSensorForm" indicator="waitDiv" targets="sensorList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="sensorList" href="%{listSensor}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newSensor}" showLoadingText="false" indicator="waitDiv" targets="sensorTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>