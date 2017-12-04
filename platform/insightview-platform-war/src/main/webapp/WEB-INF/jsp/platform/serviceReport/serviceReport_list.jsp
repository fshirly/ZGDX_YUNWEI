<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>服务报告</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
<!-- mainframe -->
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<link
	href="${pageContext.request.contextPath}/plugin/select2/select2.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>

</head>
<body>
	<div class="rightContent">
		<div class="location">
			当前位置：${navigationBar }
		</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>报告名称：</b>
						<input type="text" id="name" />
					</td>
					<td>
						<b>报告人：</b> 
						<select id="reporter" name="reporter">
                             <option value="-1">全部</option>
                        </select>
					</td>
					<td colspan="2">
						<b>报告时间：</b>
						<input class="easyui-datetimebox" id="startTime" name="startTime" /> 
						- <input class="easyui-datetimebox" id="endTime" name="endTime" />
					</td>
					<td class="btntd">
						<a id="conditions_query" href="javascript:void(0);">查询</a>
						<a id="conditions_reset" href="javascript:void(0);">重置</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="datas tops">
			<table id="tblServiceReport" />
		</div>
	</div>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/serviceReport/serviceReport_list.js"></script>
</body>
</html>