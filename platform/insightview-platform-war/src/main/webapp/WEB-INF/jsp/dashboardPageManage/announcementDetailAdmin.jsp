<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 统一引用样式 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/style/css/reset.css" />
<!-- mainframe -->
<title>通知公告详细信息</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
</head>

<body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/rapidCreateAnnouncement.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/announcementDetailAdmin.js"></script>
	<c:if test="${not empty navigationBar }">
		<div class="location">当前位置：${navigationBar}</div>
	</c:if>
	<div class="taskl">
		<input type="hidden" name="pageID" id="pageID" value="backlogPage">
		<div class="easyui-layout" style="height:400px">
			<div data-options="region:'west',border:false" style="width: 280px; padding: 10px">
				<div id="rapidTab" class="easyui-tabs" style="width: 270px; height: 360px">
					<div title="快速查询" style="padding: 10px;background: #E4EDF2;" class="conditions"  id="divFilter">
						<table align="center" style="display:block;">
							<tr>
								<td class="title">标题：</td>
								<td><input type="text" id="txtFilterTitle" /></td>
							</tr>
							<tr>
								<td class="title">有效期：</td>
								<td><input id="deadLineFilter"/></td>
							</tr>
							<tr>
								<td></td>
								<td class="btntd">
									<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
									<a href="javascript:void(0);" onclick="resetForm1();">重置</a>
								</td>
							</tr>
						</table>
					</div>
					<div title="快速创建" style="padding: 10px;background: #E4EDF2;" class="conditions" id="divFilter1">
						<table align="center" id="announcementTab" style="display:block;">
							<tr>
								<td colspan="2"><div id="errorText"></div></td>
							</tr>
							<tr>
								<td class="title">标题：</td>
								<td><input type="text" name="ipt_title1" id="ipt_title1" validator="{'default':'*','length':'1-120'}"><dfn>*</dfn></td>
							</tr>
							<tr>
								<td>创建者：</td>
								<td><input id="ipt_creator1" value="${createrName }" readonly="readonly" validator="{'default':'*'}"></input> <input type="hidden"
									id="creater1" name="creater" value="${createrName }" /></td>
							</tr>
							<tr>
								<td class="title">有效期：</td>
								<td><input type="text" name="ipt_deadLine1"
									id="ipt_deadLine1" validator="{'default':'*','type':'datetimebox'}"><dfn>*</dfn></td>
							</tr>
							<tr>
								<td class="title">说明：</td>
								<td><textarea id="ipt_summary1" name="ipt_summary1" rows="8" validator="{'default':'*','length':'0-500'}"></textarea></td>
							</tr>
							<tr>
								<td></td>
								<td class="btntd">
									<a id="commitBtn">创建</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="taskr">
		<div 
			class="tabstable" id="noticeListTab">
			
				<div title="当前通知公告" >
					<table id="tblAnnouncementList" title="通知公告列表">
					</table>
				</div>
				<div title="已过期">
					<table id="tblAnnouncementList1" title="已过期列表">
					</table>
				</div>
			
		</div>
	</div>
</body>
</html>