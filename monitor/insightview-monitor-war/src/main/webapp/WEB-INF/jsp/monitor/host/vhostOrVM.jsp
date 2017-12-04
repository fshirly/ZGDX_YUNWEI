<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/vhostOrVM.js"></script>
<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
		<table>
		
			<tr>
				<td>
					<div class="easyui-panel" 
						style="width: 650px; height: 320px; padding: 0px; background: #fafafa; border: 0px;">
						<table id="tblHostNetworkIf">

						</table>
					</div>
				</td>
			</tr>
			
		</table>
	</body>
</html>
