<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
		<table border="1" width="100%">
			<tr>
				<td colspan="2">
					<input id="organization" type="radio" name="xxx"/><label class="title" for="organization">按组织架构</label>&nbsp;&nbsp;&nbsp;
					<input id="workGroup" type="radio" name="xxx"/><label class="title" for="workGroup">按工作组</label>
				</td>
			</tr>
			<tr height="160">
				<td width="50%">
					<ul id="treeId"></ul>
				</td>
				<td width="50%">
					<ul id="personId"></ul>
				</td>
			</tr>
		</table>
    </body>
</html>
