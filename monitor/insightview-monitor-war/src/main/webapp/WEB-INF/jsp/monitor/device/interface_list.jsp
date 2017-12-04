<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/interface_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="mOClassID" value="${mOClassID}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>接口IP：</b>
							<input type="text" id="deviceIP" />
						</td>
						<td>
							<b>所属设备：</b>
							<input type="text" id="deviceMOName" />
						</td>
						<td>
							<b>可用状态：</b>
							<select id="operStatus" class="easyui-combobox">
								<option value="">全部</option>
								<c:forEach items="${osMap}" var="entry">
									<option value="<c:out value='${entry.key}' />"><c:out value="${entry.value}" /></option>
								</c:forEach>
							</select>
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<div id="winAlaias" class="easyui-window" title="增加别名" style="width:600px;height:200px"
			  data-options="closed:true,collapsible:false,minimizable:false,maximizable:false">
			  <table id="addTableMotails" class="formtable1">
					<tr>
						<td class="title">接口别名：</td>
						<td>
							<input id="moAlias" name="moAlias" validator="{'default':'*','length':'1-100'}"/><b>*</b>
						</td>
					</tr>
			  </table>
			  <div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toMoAliasAdd();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#winAlaias').window('close');"/>
				</div>
			</div>
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblDataList">
				</table>
			</div>
		</div>	
	</body>
</html>