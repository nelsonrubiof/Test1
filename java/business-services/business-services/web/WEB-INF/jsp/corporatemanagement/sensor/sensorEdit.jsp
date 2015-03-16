<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="sensorSave" namespace="/corporatemanagement" action="sensor!saveSensor"/>
<s:url id="sensorCancel" namespace="/corporatemanagement" action="sensor!cancel"/>
<s:url id="showArea" namespace="/corporatemanagement" action="sensor!showArea"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.sensor"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="sensor!saveSensor" id="saveSensorForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="sensor.id"/>
    <s:select key="label.store" name="sensor.store.id" list="stores" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refresh('refreshAreaBtn');"/>
    <s:select key="label.area" name="sensor.area.id" list="areas" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:textfield key="label.name" name="sensor.name" required="true"/>
    <s:textfield key="label.description" name="sensor.description" required="true"/>
    <s:textfield key="label.url" name="sensor.url" />
    <s:textfield key="label.user" name="sensor.userName" />
    <s:password key="label.password" name="sensor.userPassword" showPassword="true"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{sensorCancel}" showLoadingText="false" indicator="waitDiv" targets="sensorTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{sensorSave}" showLoadingText="false" formId="saveSensorForm" indicator="waitDiv" targets="sensorTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
<s:submit href="%{showArea}" showLoadingText="false" formId="saveSensorForm" indicator="waitDiv" targets="sensorTabDiv" id="refreshAreaBtn" cssStyle="display:none"/>
    