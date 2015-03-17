<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="areaTypeSave" namespace="/corporatemanagement" action="areaType!saveAreaType"/>
<s:url id="areaTypeCancel" namespace="/corporatemanagement" action="areaType!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.areaType"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="areaType!save" id="saveATForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="areaType.id"/>
    <s:textfield key="label.name" name="areaType.name" required="true"/>
    <s:textfield key="label.description" name="areaType.description" required="true"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{areaTypeCancel}" showLoadingText="false" indicator="waitDiv" targets="areaTypeTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{areaTypeSave}" showLoadingText="false" formId="saveATForm" indicator="waitDiv" targets="areaTypeTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    