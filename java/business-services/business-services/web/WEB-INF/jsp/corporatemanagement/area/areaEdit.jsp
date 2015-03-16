<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="areaSave" namespace="/corporatemanagement" action="areas!saveArea"/>
<s:url id="areaCancel" namespace="/corporatemanagement" action="areas!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.area"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="areas!save" id="saveATForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="area.id"/>
    <s:textfield key="label.name" name="area.name" required="true"/>
    <s:textfield key="label.description" name="area.description" required="true"/>
    <s:select name="area.store.id" key="label.store" list="stores" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:select name="area.areaType.id" key="label.areaType" list="areaTypes" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{areaCancel}" showLoadingText="false" indicator="waitDiv" targets="areasTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{areaSave}" showLoadingText="false" formId="saveATForm" indicator="waitDiv" targets="areasTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    