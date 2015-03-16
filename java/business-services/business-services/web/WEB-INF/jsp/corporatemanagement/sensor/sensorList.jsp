<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'sensorList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="sensors" id="at" pagesize="30" requestURI="corporatemanagement/sensor!readyList.action" sort="list" defaultsort="3" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column titleKey="label.store" property="store.description" sortable="true"/>
            <display:column titleKey="label.area" property="area.description" sortable="true"/>
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editSensor" action="sensor" method="editSensor" namespace="/corporatemanagement">
                    <s:param name="sensor.id" value="id"/>
                </s:url>
                <s:a href="%{editSensor}" showLoadingText="false" indicator="waitDiv" targets="sensorTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'sensorId', 'deleteSensorBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteSensorForm" action="sensor" cssStyle="display:none;">
    <s:hidden name="sensor.id" id="sensorId"/>
    <s:url id="deleteSensor" namespace="/corporatemanagement" action="sensor" method="deleteSensor"/>
    <s:submit href="%{deleteSensor}" targets="sensorList" id="deleteSensorBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteSensorForm"/>
</s:form>