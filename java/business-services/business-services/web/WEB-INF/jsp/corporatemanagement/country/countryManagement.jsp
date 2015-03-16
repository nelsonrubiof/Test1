<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterCountry" namespace="/corporatemanagement" action="country!list"/>
<s:url id="listCountry" namespace="/corporatemanagement" action="country!readyList"/>
<s:url id="newCountry" namespace="/corporatemanagement" action="country!newCountry"/>
<s:form name="findCountryForm" id="findCountryForm" action="country" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="country.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="country.description" key="label.description" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterCountry}" showLoadingText="false" formId="findCountryForm" indicator="waitDiv" targets="countryList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="countryList" href="%{listCountry}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newCountry}" showLoadingText="false" indicator="waitDiv" targets="countryTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>