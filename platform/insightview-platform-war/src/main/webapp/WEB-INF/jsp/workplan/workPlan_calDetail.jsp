<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作计划详情</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
	<table id="workPlanDetail" class="tableinfo">
		<tr>
			<td><b class="title">计划名称：</b> <label>${workPlan.title }</label></td>
			<td><b class="title">计划类型：</b> <label>${workPlan.planType }</label></td>
		</tr>
		<tr>
			<td><b class="title">创建人：</b> <label>${workPlan.createrName }</label></td>
			<td><b class="title">相关人员：</b> <label>${workPlan.relationPersons }</label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">计划日期：</b> 
			<label><fmt:formatDate value='${workPlan.planStart }' pattern='yyyy-MM-dd HH:mm'/> - 
			<fmt:formatDate value='${workPlan.planEnd }' pattern='yyyy-MM-dd HH:mm'/></label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">计划内容：</b> <label>${workPlan.planContent }</label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">附件：</b> 
			<label><input id="previousFileNameLook" type="hidden" value="${workPlan.file}" name="attachmentUrl" />
			<a id="downloadFileLook" title="下载文件"></a></label>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a href="javascript:doDel(${workPlan.id });">删除</a>
		<a href="javascript:toEdit(${workPlan.id });">编辑</a>
	</div>
	
	<script type="text/javascript">
		initDownLinkTag("downloadFileLook", $("#previousFileNameLook").val());
		
		var path = getRootName();
		function doDel(id) {
			$.messager.confirm('提示', '是否确定删除该值班计划？', function(r) {
				if(r) {
					$.ajax({
						url: path+'/workPlan/deleteWorkPlan?id='+id,
						success: function(data) {
							if(data == 'success') {
								$('#plan_calendar').window('close');
								//重新加载events事件
								$('#workPlan_calendar').fullCalendar('refetchEvents');
								//刷新主页面的工作计划列表
								window.opener.workplan.list.reload();
							}
						}
					});
				}
			});
		}
		function toEdit(id) {
			$('#plan_calendar').window('close');
			workplan.calendar.toEdit(id);
		}
	</script>
</body>
</html>