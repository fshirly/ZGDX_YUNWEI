<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/vhostOrVMDetail.js"></script>

	</head>

	<body>
		<%
			String moID =(String)request.getAttribute("moID");
			String ifMOID =(String)request.getAttribute("ifMOID");
		 %>
		 <input type="hidden" id="moID" value="<%= moID%>"/>
		  <input type="hidden" id="ifMOID" value="<%= ifMOID%>"/>
		<table>
			<tr>
				<td>
					<div class="datas tops5" >
						<table id="tblVHostOrVMDetail">

						</table>
					</div>
				</td>
			</tr>
			
		</table>
	</body>
</html>
