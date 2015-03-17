<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="regionSave" namespace="/corporatemanagement" action="region!saveRegion"/>
<s:url id="regionCancel" namespace="/corporatemanagement" action="region!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.region"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="region!save" id="saveRegionForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="region.id"/>
    <s:textfield key="label.name" name="region.name" required="true"/>
    <s:textfield key="label.description" name="region.description" required="true"/>
    <s:select name="region.country.id" key="label.country" list="countries" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{regionCancel}" showLoadingText="false" indicator="waitDiv" targets="regionTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{regionSave}" showLoadingText="false" formId="saveRegionForm" indicator="waitDiv" targets="regionTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    