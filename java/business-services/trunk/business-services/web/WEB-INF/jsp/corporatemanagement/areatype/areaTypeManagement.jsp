<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterAreaType" namespace="/corporatemanagement" action="areaType!list"/>
<s:url id="listAreaType" namespace="/corporatemanagement" action="areaType!readyList"/>
<s:url id="newAreaType" namespace="/corporatemanagement" action="areaType!newAreaType"/>
<s:form name="findAreaTypeForm" id="findAreaTypeForm" action="areaType" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="areaType.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="areaType.description" key="label.description" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterAreaType}" showLoadingText="false" formId="findAreaTypeForm" indicator="waitDiv" targets="areaTypeList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="areaTypeList" href="%{listAreaType}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newAreaType}" showLoadingText="false" indicator="waitDiv" targets="areaTypeTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>