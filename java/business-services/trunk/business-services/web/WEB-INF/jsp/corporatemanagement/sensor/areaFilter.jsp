<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <s:select key="label.area" name="sensor.area.id" list="areasFilter" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteAll')}"/>
</table>