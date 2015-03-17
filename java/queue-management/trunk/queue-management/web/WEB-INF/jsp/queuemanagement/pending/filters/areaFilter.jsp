<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <s:select name="filters.area" list="areaArray" key="filter.area" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" />
</table>