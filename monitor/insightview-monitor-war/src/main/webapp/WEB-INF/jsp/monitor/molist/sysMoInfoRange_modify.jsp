<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
	<%
		String mid = (String)request.getAttribute("mid");
	 %>
	</head> 
	<body style="overflow:hidden;"> 
		<script type="text/javascript">
			function autoEventClick(val, $){
			$.val(val);
			}
		</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoRangeModify.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		 <input id="ipt_mid" type="hidden" value="<%=mid %>"/>
		 <input id="monitorProperty" type="hidden" value=""/>
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
							<a href="javascript:void(0);" onclick="toAddManuCate();">添加</a>
						</td>
					</tr>
					</table>
			   </div>
			<!-- begin .datas -->
				<div class="datas">
					<table id="tblSysMoManufactureList">
					</table>
				</div>
			</div>
				
			<div id="othersConfigRangeDiv" style="overflow-x:hidden;">
				<table id="tblOthersConfig" class="formtable1" style="margin:0 auto;">
					<tr>
						<td class="title">对象类型：</td>
						<td>
						<input id="ipt_moClassIDTemp" type="hidden"/>
						<input id ="ipt_moClassID" onfocus="choseMObjectTree();"/><b>*</b>
						</td>
					</tr>
				</table>
			</div>
			<div id="divMObject" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择对象类型" style="width: 400px; height: 250px;">
			<div id="dataMObjectTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>