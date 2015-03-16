<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">
<!--
 
if (<s:property value="refreshLeftPane"/>) {
publishTopic('refreshPlaceList');
}

//-->
</script>

<h3><s:property value="headerText" /></h3>
<s:form action="placeDetail!save">
	<s:hidden name="placeId" />
	<s:hidden name="persistent" />
	<s:textfield label="Name" name="name" required="true" />
	<s:textfield label="Address" name="address" />
	<s:select list="bos.allPlaceTypes" name="placeTypeId" listKey="id"
		listValue="name" label="Type" required="true" />
	<s:select list="possibleParents" name="parentId" listKey="id"
		listValue="name" label="Parent" />
	<s:submit value="%{saveButtonLabel}" targets="placeDetailDiv"
		executeScripts="true" />
</s:form>

<s:if test="persistent">
	<display:table name="evidenceProviders" id="ep">
		<s:if test="#attr.ep != null">
			<s:push value="#attr.ep">
				<display:column property="name" title="Name" />
				<display:column title="">
					<s:url id="editEpUrl" namespace="/evidenceproviders"
						action="evidenceProviderDetail!open">
						<s:param name="evidenceProviderId" value="id" />
					</s:url>
					<s:a href="%{editEpUrl}" targets="placeDetailDiv">edit</s:a>
					<s:url id="deleteEpUrl" action="evidenceProviderDetail!delete"
						namespace="/evidenceproviders">
						<s:param name="evidenceProviderId" value="id" />
						<s:param name="placeId" value="placeId" />
					</s:url>
					<s:a href="%{deleteEpUrl}" targets="placeDetailDiv">delete</s:a>
				</display:column>
			</s:push>
		</s:if>
	</display:table>

	<s:url id="newEpUrl" action="evidenceProviderDetail!openNew"
		namespace="/evidenceproviders">
		<s:param name="placeId" value="placeId" />
	</s:url>
	<s:a href="%{newEpUrl}" targets="placeDetailDiv">New Evidence Provider</s:a>

	<s:url id="newPlaceUrl" action="placeDetail" method="openNew">
		<s:param name="parentId" value="placeId" />
		<s:param name="placeId" value=""/>
	</s:url>
	<br>
	<s:a href="%{newPlaceUrl}" targets="placeDetailDiv">Create new child</s:a>
</s:if>



