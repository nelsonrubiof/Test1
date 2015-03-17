<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filter" action="nFirst!list" namespace="/queuemanagement"/>
<table>
    <tr>
        <td>
            <table>
                <s:select required="true" id="quantity" key="filter.quantity" name="quantity" list="quantityValues"/>
            </table>
        </td>

        <td colspan="3">               
                <s:a href="%{filter}" showLoadingText="false" formId="nfirstForm" indicator="waitDiv" targets="nfirstListTable" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>            
        </td>
    </tr>
</table>
