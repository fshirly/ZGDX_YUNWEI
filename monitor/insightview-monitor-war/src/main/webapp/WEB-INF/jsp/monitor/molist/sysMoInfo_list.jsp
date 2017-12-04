<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>	
	<body>  
	<script type="text/javascript">
	function autoEventClick(val, $){
	$.val(val);
	//console.info(arguments);
	}
	
	</script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoList.js"></script>
	
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>	
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>	
			<div class="conditions" id="divFilter">
					<table>
					<tr>
						<td>
							<b>监测器类型：</b>
							<select id="txtMoType" name="txtMoType" class="easyui-combobox">
							<option value="-1"></option>
						    <c:forEach items="${moTypeList}" var="vo">
							<option value="<c:out value='${vo.key}' />" ><c:out value="${vo.value}" /></option>	
							</c:forEach>
						</select>
						</td>
						<td>
							<b>监测器名称：</b>
							<input type="text" id="txtMoName" />
						</td>
						<td>
							&nbsp;
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
					<tr>
						<td>
							<b>监测对象性质：</b>
							<select class="easyui-combobox" id="txtMoProperty" panelHeight="80"><option value="-1"></option>
							<option value="0">设备</option>
							<option value="1">其他</option>
							</select>
						</td>
						<td id="moClassTd">
							<b>监测对象类型：</b>
							<input id ="txtMoClassName" onfocus="choseMObjectTree();"/>
						</td>
						<td id="resManuTd" >
							<b>厂商：</b>
							<input type="text" name="ManufacturerName" id="txtMoResManufacturerName"/>
							<input type="hidden" id="txtMoResManufacturerID"/>
						</td>
						<td id="resCateTd">
							<b>型号：</b>
							<input id="txtMoResCategoryName"></input>  
							<input type="hidden" id="txtMoResCategoryID"/>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas tops2">
				<table id="tblSysMoInfoList">
				</table>
			</div>
		</div>	
		<div id="divMObject" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择对象类型" style="width: 400px; height: 400px;">
			<div id="dataMObjectTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>