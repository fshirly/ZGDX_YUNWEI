<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/perf/perfKPIDef_modify.js"></script>
		<%
			String kpiID =(String)request.getAttribute("kpiID");
		 %>
		  <div id="addPerfKPIDefDiv">
		  <input id="kpiID" value="<%= kpiID%>" type="hidden" />
				<table id="tblAddPerfKPIDef" class="formtable">
					<tr>
						<td class="title">
							指标名称：
						</td>
						<td>
							<input id="ipt_name"  validator="{'default':'*','length':'1-200'}" /><b>*</b>
						</td>
						<td class="title">
							指标英文名：
						</td>
						<td>
							<input id="ipt_enName"  validator="{'length':'0-200'}" />
						</td>
					</tr>
					
					<tr>
						<td class="title">
							对象类型：
						</td>
						<td>
							<input id="ipt_classID" readonly="readonly" onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
						</td>
						<td class="title">
							指标类别：
						</td>
						<td>
							<input id="ipt_kpiCategory"  validator="{'default':'*','length':'1-128'}" /><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							单位：
						</td>
						<td>
							<input id="ipt_quantifier"  validator="{'default':'*','length':'1-30'}" /><b>*</b>
						</td>
						<td class="title">
							值类型：
						</td>
						<td>
						  <select id="ipt_valueType" class="inputs">
							<option value="0">
								数值型
							</option>
							<option value="1">
								文本型
							</option>
						  </select>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							汇总规则：
						</td>
						<td colspan="3" >
						  <select id="ipt_amountType" class="inputs">
							<option value="-1">
								不汇总
							</option>
							<option value="0">
								求和
							</option>
							<option value="1">
								平均值
							</option>
							<option value="2">
								最大值
							</option>
							<option value="3">
								最小值
							</option>
							<option value="4">
								记录个数
							</option>
						  </select><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							数据有效性范围：
						</td>
						<td colspan="3" >
							<textarea rows="3" class="x2" id="ipt_valueRange" validator="{'default':'*','length':'0-255'}"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							备注：
						</td>
						<td colspan="3" >
							<textarea rows="3" class="x2" id="ipt_note" validator="{'length':'0-100'}"></textarea>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:toUpdate();"/>
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