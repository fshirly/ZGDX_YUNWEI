<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/cpuInfoList.js"></script>
<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
		<table>
		<input id="liInfo" value="${liInfo}" type="hidden"/>
		<input id="moClass" value="${moClass}" type="hidden"/>
		<input id="cpuNum" value="${cpuNum}" type="hidden"/>
		<input id="topOrder" value="${topOrder}" type="hidden"/>
		<input id="timeBegin" value="${timeBegin}" type="hidden"/>
		<input id="timeEnd" value="${timeEnd}" type="hidden"/>
			<tr>
				<td>
					<div class="datas tops5">
						<table id="tblHostTopCPU">

						</table>
					</div>
				</td>
			</tr>
			
		</table>
	</body>
</html>
