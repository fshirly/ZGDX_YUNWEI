<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
	</head> 
	<body> 
		<script type="text/javascript">
			function autoEventClick(val, $){
			$.val(val);
			}
		</script>
			<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoAdd.js"></script>
		 <style>
		   input,textarea,span{
		       margin-left:6px; 
		   }
		   span > input,span >span{
		    margin-left:0;
		   }
		   #updateSysMoInfoDiv{
		      overflow:hidden;
		   }
		   a:hover
			{ 
			 border:1px solid;
			 border-color: #5cb8e6 #297ca6 #297ca6 #5cb8e6;
			}
		 </style>
		  <input id="ipt_mid" type="hidden"/>
		  <div id="addSysMoInfoDiv">
				<table id="tblAddSysMoInfo" class="formtable" >
					<tr>
						<td class="title">
							监测器名称：
						</td>
						<td colspan="3">
							<input id="ipt_moName"  validator="{'default':'*','length':'1-30'}" /><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							监测器调度类名：
						</td>
						<td>
							<input id="ipt_moClassPre" value="Job" style="width:18px;border-right: 0 none; margin-right: -8px;text-align: right;" readonly="readonly" /><input id="ipt_moClass" validator="{'default':'en','length':'0-47'}"  style="width: 156px;border-left: 0 none;"/><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							监测器类型：
						</td>
						<td>
						  <select id="ipt_monitorTypeName" name="asfas" class="easyui-combobox" panelWidth="180">
								<!--<c:if test="${!empty typeMap}">
									--><c:forEach items="${typeMap}" var="entry">
										<option value="<c:out value='${entry.key}' />"><c:out value='${entry.value}' /></option>
									</c:forEach>										
						  		<!--</c:if>	-->	
						  </select><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							监测对象性质：
						</td>
						<td>
						  <select id="ipt_moProperty" class="easyui-combobox" panelHeight="80" panelWidth="180">
							<option value="0">设备</option>
							<option value="1">其他</option>
						  </select><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							默认采集周期：
						</td>
						<td>
							<input id="ipt_doIntervals" class="inputs" type="text" style="width:60px;" validator="{'default':'ptInteger'}" />&nbsp;
							<select id="unit" class="inputs" style="width:60px;">
							<option value="1">
								分
							</option>
							<option value="2">
								小时
							</option>
							<option value="3">
								天
							</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="title">
							监测器描述：
						</td>
						<td colspan="3" >
							<textarea rows="6" cols="50" class="x2" id="ipt_moDescr" validator="{'length':'0-100'}"></textarea>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<input type="button" id="btnSave1" value="确定" onclick="javascript:toAdd();"/>
				<input type="button" id="btnSave1" value="配置适用范围" onclick="javascript:toConfigRange();"/>
				<input type="button" id="btnClose1" value="取消" onclick="javascript:$('#popWin').window('close');"/>
				</div>
			</div>
			<!-- 配置适用范围 -->
			<div id="deviceConfigRangeDiv">
				<input id="resManufacturerID" type="hidden"/>
				<input id="resCategoryID" type="hidden"/>
				<div class="conditions" id="divFilter">
					<table>
					<tr>
						<td>
							<b>厂商：</b>
							<input id="ipt_moResManufacturerName" name="ipt_moResManufacturerName"></input>  
							<input id="ipt_moResManufacturerId" type="hidden"/>
						</td>
						<td>
							<b>型号：</b>
							<input id="ipt_moResCategoryName"></input>  
							<input id="ipt_moResCategoryId" type="hidden"/>
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="toAddManuCate();" >添加</a>
						</td>
					</tr>
					</table>
			   </div>
			<!-- begin .datas -->
				<div class="datas">
					<table id="tblSysMoManufactureList">
					</table>
				</div>
				<div class="conditionsBtn">
				<input type="button" id="btnSave2" value="确定" onclick="$('#popWin').window('close');window.frames['component_2'].reloadTable();"/>
				</div>
			</div>
				
			<div id="othersConfigRangeDiv">
				<table id="tblOthersConfig" class="formtable1">
					<tr>
						<td class="title">对象类型：</td>
						<td>
						<input id ="ipt_moClassID" onfocus="choseMObjectTree();"/><b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<input type="button" id="btnSave3" value="确定" onclick="javascript:toAddOthersRange();"/>
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