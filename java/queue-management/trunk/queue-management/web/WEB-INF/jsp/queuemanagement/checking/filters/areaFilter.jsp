<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <s:select name="filters.area" list="areaArray" key="filter.area" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
</table>