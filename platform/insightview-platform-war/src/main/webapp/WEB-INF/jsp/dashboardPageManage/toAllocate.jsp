<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css"
		href="../plugin/easyui/themes/default/easyui.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	 <link rel="stylesheet" 
        href="${pageContext.request.contextPath}/plugin/select2/select2.css" /> 
    <script  type="text/javascript" 
        src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
    <script type="text/javascript" 
        src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
    <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/toBeAllocatedEvent.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/dashboardPageManage/toAllocate.js"></script>
		
	<div>
		<table id="updateProviderInfo" class="formtable1">
					<tr>
						<td class="title">标题：</td>
						<td>
							<input id="id" name="id" value="${event.id }" type="hidden"/>
							<input id="serialNO" name="serialNO" value="${event.serialNO }" type="hidden"/>
							<input id="mFlag" name="mFlag" value="${event.mFlag }" type="hidden"/>
							<input id="title" name="title" value="${event.title }"/>
						</td>
					</tr>
					<tr>
						<td class="title">报告人：</td>
						<td>
							<select id="requester" name="requester" value="${event.requester}" validator="{'default':'*','type':'select2'}">
                                <option value="-1">请选择...</option>
                            </select><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">报告方式：</td>
						<td><input id="requestMode" name="requestMode" value="${event.requestMode }" validator="{'min-max':'0~10000'}"/><dfn>*</dfn></td>
					</tr>
					<tr>
						<td class="title">报告时间：</td>
						<td>
							<input id="bookTime" name="bookTime" value="${event.bookTime }"/>
						</td>
					</tr>
					<tr>
						<td class="title">描述：</td>
						<td>
							<textarea id="description" name="description" rows="6" class="easyui-validatebox">${event.description }</textarea>
						</td>
					</tr>
					<tr>
						<td class="title">事件性质：</td>
						<td>
							<select id="kind" class="easyui-combobox" name="kind" style="width:182px;">
   								<option value="0">故障</option>
    							<option value="1">请求</option>
							</select><dfn>*</dfn>
						</td>
					</tr>
		</table>
		<p class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');">关闭</a>
		    <a href="javascript:void(0);" id="allocate">分配</a>
		</p>
	</div>	
</body>
</html>
