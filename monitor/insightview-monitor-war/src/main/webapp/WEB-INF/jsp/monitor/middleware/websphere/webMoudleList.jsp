<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/middleware/websphere/webMoudleList.js"></script>
	<%
		String moId = (String)request.getAttribute("moId");
	 %>
	</head>

	<body>
	<input id="liInfo" value="${liInfo}" type="hidden"/>
		<table>
		
			<tr>
				<td>
					<div class="datas tops5">
						<input type="hidden" id="moId" value="<%=moId %>"/>
						<table id="tblWebMoudleInfos">

						</table>
					</div>
				</td>
			</tr>
			
		</table>
	</body>
</html>
