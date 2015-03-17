<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <s:select key="label.area" name="evidenceProvider.areas.id" list="areasFilter" listKey="id" listValue="name" headerKey="-1"
              headerValue="%{getText('select.selecteAll')}" value="%{evidenceProvider.areas.{id}}" />
    
</table>