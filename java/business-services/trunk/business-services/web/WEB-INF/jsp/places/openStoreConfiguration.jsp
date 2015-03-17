<%@ taglib prefix="s" uri="/struts-tags"%>

<table>
	<tbody>
		<tr>
			<td valign="top"><s:div theme="ajax">
				<s:push value="storeRoot">
					<s:include value="renderStoreNode.jsp" />
				</s:push>

			</s:div></td>
			<td><s:div id="placeDetailDiv">Please click on any of the tree nodes.</s:div>
			</td>
		</tr>
	</tbody>
</table>
