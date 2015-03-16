<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<br>
<s:set name="target" value="%{'formulaListDiv'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.description')}"/>
<display:table name="formulas" id="fx" pagesize="30" requestURI="wizards/formulaDefinition!readyList.action" sort="list" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.fx != null">
        <s:push value="#attr.fx">
            <display:column title="${title}" sortProperty="description" sortable="true">
                <s:url id="editFormulaUrl" action="formulaDefinition" method="editFormula" namespace="/wizards">
                    <s:param name="formula.id" value="%{id}"/>
                </s:url>
                <s:a href="%{editFormulaUrl}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssStyle="color:blue;">
                    <s:text name="%{description}"/>
                </s:a>
            </display:column>
            <display:column property="formula" sortable="true"/>
            <display:column titleKey="label.formulaType" property="type" sortable="true"/>
            <display:column titleKey="label.compliantType" property="compliantType" sortable="true"/>
            <display:column titleKey="label.situationTemplate">
                <s:iterator value="situationTemplates">
                    <s:property value="name"/>-<s:property value="product.description"/>-<s:property value="areaType.description"/><br>
                </s:iterator>
            </display:column>
            <display:column titleKey="label.store">
                <s:iterator value="stores">
                    <s:property value="description"/><br>
                </s:iterator>
            </display:column>
            <display:column titleKey="label.variable" property="variables"/>
            <display:column titleKey="label.observation" property="observations"/>
            <display:column>
                <s:a cssStyle="color:blue;cursor:hand;" onclick="deleteItem(%{id}, 'formulaId', 'deleteFormulaBtn');">
                    <s:text name="label.delete"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>
</display:table>
<br>