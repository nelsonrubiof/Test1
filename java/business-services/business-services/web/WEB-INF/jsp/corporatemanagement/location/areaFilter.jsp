<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <s:select key="label.area" name="areaId" list="areasFilter" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
</table>