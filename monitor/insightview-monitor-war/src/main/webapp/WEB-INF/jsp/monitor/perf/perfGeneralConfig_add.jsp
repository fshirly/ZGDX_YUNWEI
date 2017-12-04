<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head>
	<body>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/perf/perfGeneralConfig_add.js"></script>
		<div id="divPerfSetting">
			<input type="hidden" id="className" />
			<table id="tblPerfSetting" class="formtable1">
				<tr>
					<td class="title">
						对象类型：
					</td>
					<td>
						<input id="ipt_mobjectClassID"  readonly onfocus="javascript:choseMObjectTree();" validator="{'default':'*'}" />
						<b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">
						采集周期(min)：
					</td>
					<td>
						<select id="ipt_collectPeriod" class="inputs" >
							<option value="2">
								2
							</option>
							<option value="5" selected="selected">
								5
							</option>
							<option value="10">
								10
							</option>
							<option value="15">
								15
							</option>
							<option value="20">
								20
							</option>
							<option value="30">
								30
							</option>
							<option value="60">
								60
							</option>
							<option value="90">
								90
							</option>
							<option value="120">
								120
							</option>
						</select>
						<b>*</b>
					</td>
				</tr>
			</table>
			<div class="conditionsBtn">
				<input type="button" id="btnClose" value="确定" onclick="javascript:toSetCollectPeriod();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
				
		<div id="divMObject" class="easyui-window" maximizable="false"
		  collapsible="false" minimizable="false" closed="true" modal="true"
		  title="选择对象类型" style="width: 400px; height: 450px;">
			<div id="dataMObjectTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>