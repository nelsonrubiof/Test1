<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterProduct" namespace="/templatemanagement" action="product!list"/>
<s:url id="listProduct" namespace="/templatemanagement" action="product!readyList"/>
<s:url id="newProduct" namespace="/templatemanagement" action="product!newProduct"/>
<s:form name="findProductForm" id="findProductForm" action="product" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="product.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="product.description" key="label.description" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterProduct}" showLoadingText="false" formId="findProductForm" indicator="waitDiv" targets="productList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="productList" href="%{listProduct}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newProduct}" showLoadingText="false" indicator="waitDiv" targets="productDiv" >
    <span><s:text name="label.add"/></span>
</s:a>