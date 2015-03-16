<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterRegion" namespace="/corporatemanagement" action="region!list"/>
<s:url id="listRegion" namespace="/corporatemanagement" action="region!readyList"/>
<s:url id="newRegion" namespace="/corporatemanagement" action="region!newRegion"/>
<s:form name="findRegionForm" id="findRegionForm" action="region" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="region.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="region.description" key="label.description" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterRegion}" showLoadingText="false" formId="findRegionForm" indicator="waitDiv" targets="regionList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="regionList" href="%{listRegion}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newRegion}" showLoadingText="false" indicator="waitDiv" targets="regionTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>