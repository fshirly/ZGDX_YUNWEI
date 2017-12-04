<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	
	</head>

	<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/tzSnapshotInfoList.js"></script>
		<input id="liInfo" value="${liInfo}" type="hidden"/>
		<input id="moClass" value="${moClass}" type="hidden"/>
		<table>
		
			<tr>
				<td>
						<div class="datas tops5">
						<table id="tblSnapshot">

						</table>
					</div>
				</td>
			</tr>
			
		</table>
	</body>
</html>
