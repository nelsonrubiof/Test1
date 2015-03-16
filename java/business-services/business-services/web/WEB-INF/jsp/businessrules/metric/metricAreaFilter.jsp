<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <s:select key="label.area" name="metric.area.id" list="areasFilter" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteAll')}"/>
</table>