<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="productSave" namespace="/templatemanagement" action="product!saveProduct"/>
<s:url id="productCancel" namespace="/templatemanagement" action="product!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.templateManager.product"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="product!save" id="saveProductForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="product.id"/>
    <s:textfield key="label.name" name="product.name" required="true"/>
    <s:textfield key="label.description" name="product.description" required="false"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{productCancel}" showLoadingText="false" indicator="waitDiv" targets="productDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{productSave}" showLoadingText="false" formId="saveProductForm" indicator="waitDiv" targets="productDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    