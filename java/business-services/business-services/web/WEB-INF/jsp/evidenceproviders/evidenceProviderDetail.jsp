<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<h3><s:property value="headerText" /></h3>
<s:form action="evidenceProviderDetail!save">
	<s:hidden name="evidenceProviderId" />
	<s:hidden name="placeId" />
	<s:hidden name="persistent" />
	<s:textfield label="Name" name="name" required="true" />
	<s:submit value="%{saveButtonLabel}" targets="placeDetailDiv" />
</s:form>
