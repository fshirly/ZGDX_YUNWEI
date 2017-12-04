<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>
	<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/middleware/middlewarecommunity/middlewarecommunity_List.js"></script>
		<div class="rightContent">
			<div class="location">当前位置：${navigationBar}</div>

			<div class="conditions" id="divFilter" style="overflow: auto;">
				<table>
					<tr>
						<td>
							<b>设备IP：</b>
							<input type="text" class="inputs" id="txtDeviceIP" />
							<input type="hidden" class="inputs" id="txtMoID" />
						</td>
						
						<td>
							<b>中间件类型：</b>
							<input type="text" class="inputs" id="txtMiddleWareType" />
						</td>
					
						<!--<td>
							<b>用户名：</b>
							<input type="text" class="inputs" id="txtUserName" />
						</td>
						--><td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>

						</td>
					</tr>
				</table>
			</div>

			<div class="datas">
				<table id="tblMiddleWareCommunity">
				</table>
			</div>
		</div>
	</body>
</html>