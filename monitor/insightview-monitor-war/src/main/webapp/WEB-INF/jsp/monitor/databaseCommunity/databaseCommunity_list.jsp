<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<!-- mainframe -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/databaseCommunity/databaseCommunityList.js"></script>
	</head>
	<body>
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
							<b>数据库名称：</b>
							<input type="text" class="inputs" id="txtDBName" />
						</td>
						<td>
							<b>数据库类型：</b>
							<input type="text" class="inputs" id="txtDBMSType" />
						</td>
					</tr>
					<tr>	
						<td>
							<b>用户名：</b>
							<input type="text" class="inputs" id="txtUserName" />
						</td>
						<td>&nbsp;</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>

						</td>
					</tr>
				</table>
			</div>

			<!-- begin .datas -->
			<div class="datas tops2">
				<table id="tblDatabaseCommunity">

				</table>
			</div>
		</div>
	</body>
</html>