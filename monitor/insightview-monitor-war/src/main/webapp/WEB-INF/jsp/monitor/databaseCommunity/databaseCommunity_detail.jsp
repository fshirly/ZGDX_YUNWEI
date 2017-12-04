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
		
</head>
	<body>
				<table id="tblDetailInfo" class="tableinfo1">
					<tr>
						<td>
							<b class="title">数据库类型：</b>
							<label  class="input" >${dbmsCommunity.dbmsType}</label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">设备IP：</b>
							<label  class="input" >${dbmsCommunity.ip}</label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">数据库名：</b>
							<label  class="input" >${dbmsCommunity.dbName}</label>
						</td>
					</tr>
					

					<tr>
						<td>
							<b class="title">用户名：</b>
							<label  class="input" >${dbmsCommunity.userName}</label>
						</td>
					</tr>
					<!--<tr>
						<td>
							<b class="title">密码：</b>
							<label  class="input" >${dbmsCommunity.password}</label>
						</td>
					</tr>
					--><tr>
						<td>
							<b class="title">端口：</b>
							<label  class="input" >${dbmsCommunity.port}</label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
				</div>
		</body>
</html>