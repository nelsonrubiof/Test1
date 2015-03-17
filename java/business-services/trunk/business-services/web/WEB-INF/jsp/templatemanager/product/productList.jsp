<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'productList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="products" id="product" pagesize="30" requestURI="templatemanagement/product!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.product != null">
        <s:push value="#attr.product">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editProduct" action="product" method="editProduct" namespace="/templatemanagement">
                    <s:param name="product.id" value="id"/>
                </s:url>
                <s:a href="%{editProduct}" showLoadingText="false" indicator="waitDiv" targets="productDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'productId', 'deleteProductBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteProductForm" action="product" cssStyle="display:none;">
    <s:hidden name="product.id" id="productId"/>
    <s:url id="deleteProduct" namespace="/templatemanagement" action="product" method="deleteProduct"/>
    <s:submit href="%{deleteProduct}" targets="productList" listenTopics="/deleteProduct" id="deleteProductBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteProductForm"/>
</s:form>