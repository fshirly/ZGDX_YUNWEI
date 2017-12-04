<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 统一引用样式 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />

<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/announcementDetail.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<title>通知公告详细信息</title>
</head>

<body>
	<div class="rightContent">
		<input type="hidden" name="pageID" id="pageID" value="backlogPage">
		<div class="easyui-layout" style="width: 100%; height: 100%;">
			<div data-options="region:'west',border:false"
				style="width: 400px; padding: 10px">
				<div class="easyui-tabs" style="width: 380px; height: 360px">

					<div title="查询通知公告" style="padding: 10px; display: none"
						class="conditions" id="divFilter">
						<table align="center">
							<tr>
								<td>标题：</td>
								<td><input type="text" class="inputs" id="txtFilterTitle" /></td>
							</tr>
							<tr>
								<td>有效期：</td>
								<td><select id="deadLineFilter" class="inputs"
									style="width: 132px">
										<option value="-1">请选择</option>
										<option value="0">本周</option>
										<option value="1">本月</option>
										<option value="2">本年度</option>
								</select></td>
							</tr>
							<tr>
								<td colspan="2" align="right">
									<p class="formBtn">
										<a href="javascript:void(0);" onclick="reloadTable();"
											class="fltrt">查询</a>
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div data-options="region:'center',border:false"
				style="padding: 10px;">
				<div id="noticeListTab" class="easyui-tabs"
					style="width: 750px; height: 360px">
					<div title="当前通知公告" style="padding: 10px">
						<table id="tblAnnouncementList" title="通知公告列表">

						</table>
					</div>
					<div title="已过期" style="padding: 10px">
						<table id="tblAnnouncementList1" title="已过期列表">

						</table>
					</div>
				</div>
			</div>
			<div id="divAddTableyy" class="easyui-window" minimizable="false"
				closed="true" modal="true" title="角色信息"
				style="width: 380px; height: 320px; margin: 10px" align="center">
				<input id="ipt_id" type="hidden" />
				<table id="tblAnnouncementInfo" cellspacing="2">
					<br>
					<tr>
						<td>标题：</td>
						<td><input id="ipt_title" /> <dfn>*</dfn></td>
					</tr>
					<tr>
						<td>类型：</td>
						<td><input id="ipt_typeId"></input></td>
					</tr>
					<tr>
						<td>创建者：</td>
						<td><input id="ipt_creator"></input> <input type="hidden"
							id="creater" name="creater" value="" /> <dfn>*</dfn></td>
					</tr>
					<tr>
						<td>创建时间：</td>
						<td><input id="ipt_createTime"></input></td>
					</tr>
					<tr>
						<td>期限：</td>
						<td><input id="ipt_deadLine"></input></td>
					</tr>
					<tr>
						<td>总结：</td>
						<td><textarea id="ipt_summary"
								style="width: 150px; height: 30px;"></textarea></td>
					</tr>
					<tr id="sr">
						<td colspan="2" align="right">
							<p class="formBtn">
								<a href="javascript:void(0);" id="btnUpdate" class="fltrt">重置</a>
								<a href="javascript:void(0);" id="btnSave" class="fltrt">保存</a>
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>