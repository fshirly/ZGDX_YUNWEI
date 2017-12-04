<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/hostOrNetWorkDetail.js"></script>

	</head>

	<body>
	 <%
		String moID =(String)request.getAttribute("moID");
		String ifMOID =(String)request.getAttribute("ifMOID");
	 %>
	 <input type="hidden" id="moID" value="<%= moID%>"/>
	 <input type="hidden" id="ifMOID" value="<%= ifMOID%>"/>
	 <input id="liInfo" value="${liInfo}" type="hidden"/>
		<table>
			<tr>
				<td>
					<div class="datas tops5" style='height:200px;overflow-y:auto;'>
						<table id="tblHostNetworkIf" >

						</table>
					</div>
				</td>
			</tr>
			
		</table>
	</body>
</html>
