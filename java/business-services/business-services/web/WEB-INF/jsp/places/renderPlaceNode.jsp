<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="placeDetailUrl" action="placeDetail!open">
	<s:param name="placeId" value="id" />
</s:url>

<s:a href="%{placeDetailUrl}" targets="placeDetailDiv">
	<s:property value="indentedName" />
	<br>
</s:a>

<s:iterator value="children">
	<s:include value="renderPlaceNode.jsp" />
</s:iterator>
