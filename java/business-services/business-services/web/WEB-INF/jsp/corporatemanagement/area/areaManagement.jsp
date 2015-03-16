<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterArea" namespace="/corporatemanagement" action="areas!list"/>
<s:url id="listArea" namespace="/corporatemanagement" action="areas!readyList"/>
<s:url id="newArea" namespace="/corporatemanagement" action="areas!newArea"/>
<s:form name="findAreaForm" id="findAreaForm" action="areas" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="area.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="area.description" key="label.description" />
            </table>
        </td>
        <td>
            <table>
                <s:select name="area.store.id" key="label.store" list="stores" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
            </table>
        </td>
        <td>
            <table>
                <s:select name="area.areaType.id" key="label.areaType" list="areaTypes" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterArea}" showLoadingText="false" formId="findAreaForm" indicator="waitDiv" targets="areaList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="areaList" href="%{listArea}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newArea}" showLoadingText="false" indicator="waitDiv" targets="areasTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>