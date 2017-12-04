<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>值班管理</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/select2/select2.css"/>
<style type="text/css">
.td_border_no {border-width: 0 0 0 0 !important;}
.td_border_one {border-width: 1px 0 0 0 !important;}
.import_btn {
	border: 1px solid;
	border-color: #7ca3bf #b8d0e0 #b8d0e0 #7ca3bf;
	background: #ffffff;
	width: 40px !important;
	position: absolute;
	cursor: pointer;
	text-align: center;
	height: 22px;
	margin-right: -20px;
	line-height: 22px;}
</style>
	  <div class="conditions">
		 <table>
			<tr>
				<td><b>带班领导：</b>				
					<select id="duty_leader">
						<option value="-1">全部</option>
					</select>
				</td>
				<td><b>值班日期：</b>				
					<input id="search_startTime" class="easyui-datebox" data-options="editable:false,panelWidth:'100%'"/> -
					<input id="search_endTime" class="easyui-datebox" data-options="editable:false,panelWidth:'100%'"/>
				</td>
				<td><b>值班人：</b>				
					<select id="duty_watch">
						<option value="-1">全部</option>
					</select>
				</td>
				<td class="btntd">
					 <a href="javascript:dutymanager.duty.search();">查询</a>		
					 <a href="javascript:dutymanager.duty.reset();">重置</a> 
				</td>
			</tr>
		</table>
	</div>
	<div class="datas">
		<table id="tblDutyDataList">
		</table>
	</div>	
	<div id="duty_toolbar">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="dutymanager.duty.toAdd();">新建</a>
	    <a class="easyui-linkbutton" data-options="iconCls:'icon-execl',plain:true" onclick="dutymanager.duty.importData();">数据导入</a>
	    <a class="easyui-linkbutton" data-options="iconCls:'icon-execl',plain:true" onclick="javascript:doExport();">数据导出</a>
	    <a class="easyui-linkbutton" data-options="iconCls:'icon-exchange',plain:true" onclick="javascript:toChange();">调班</a>
	</div>
	<div id="duty_import" style="display: none">
		<table class="formtable" style="width: 330px;">
				<tr>
					<td class="title">值班信息文件：</td>
					<td style="position: relative">
						<input type="file" accept="xls" name="file" id="duty_file"/>
						<input value="导入" readonly="readonly" class="import_btn"/>
					</td>
				</tr>
				<tr>
					<td class="title">文件模板下载：</td>
					<td>
						<a onclick="dutymanager.duty.template();">模板下载</a>
					</td>
				</tr>
		</table>
		<div id="msg_duty"></div>
		<div class="conditionsBtn">
		<a onclick="javascript:f('#duty_import').dialog('close');">关闭</a>
	</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/duty_search.js"></script>
</body>
</html>