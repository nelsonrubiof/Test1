<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="countrySave" namespace="/corporatemanagement" action="country!saveCountry"/>
<s:url id="countryCancel" namespace="/corporatemanagement" action="country!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.country"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="country!save" id="saveCountryForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="country.id"/>
    <s:textfield key="label.name" name="country.name" required="true"/>
    <s:textfield key="label.description" name="country.description" required="true"/>
    <s:textfield key="label.code" name="country.code" required="true" />
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{countryCancel}" showLoadingText="false" indicator="waitDiv" targets="countryTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{countrySave}" showLoadingText="false" formId="saveCountryForm" indicator="waitDiv" targets="countryTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    