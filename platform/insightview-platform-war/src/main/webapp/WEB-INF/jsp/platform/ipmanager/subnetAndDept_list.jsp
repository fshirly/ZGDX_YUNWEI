<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  
  <body>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/subnetAndDept_list.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<%
		String subNetId =(String)request.getAttribute("subNetId");
	 %>
	<div class="rightContent">
	<input type="hidden" id="subNetId" value="<%= subNetId%>"/>
	  	<div class="conditions" id="divFilter">
	  	 <form id="frmExport"
				action="${pageContext.request.contextPath}/platform/subnetViewManager/exportSubnetAndDeptVal">
	  	  <input name="colNameArrStr" id="iptColName" type="hidden" /> 
		  <input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
		  <input name="belongSubnetId" id="belongSubnetId" type="hidden"/> 
		  <input name="exlName" id="iptExlName" value="子网信息列表.xls" type="hidden" />
	  	  <table>
	  	  	<tr>
	  	  	  <td>
				  <b>部门名称：</b>
				  <input type="text" id="txtDeptName" />
			  </td>
			  <td class="btntd">
				  <a href="javascript:void(0);"	onclick="reloadTable();">查询</a>
				  <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			  </td>
	  	  	</tr>
	  	  </table>
	  	 </form>
	  	</div>
	  	
	  	<div class="datas tops1">
			<table id="tblSubnetAndDeptList">
			</table>
		</div>
	</div>
	
	<div id="divExportSubAndDepet" class="easyui-window" minimizable="false"
			closed="true" modal="true" title="选择导出列"
			style="width: 600px; height: 400px;">
			<table cellspacing="5" class="tdpad">
				<tr>
					<td style='vertical-align: bottom;'>
						<select id="selLeft"
							multiple="multiple" style="width: 150px; height: 200px"
							class="dataSelect" onclick="doLoseTargetFocus()">
								<option alt="deptName">部门名称</option>
								<option alt="usedNum">占用地址数</option>
								<option alt="preemptNum">空闲地址数</option>
								<option alt="totalNum">总计</option>
						</select>
					</td>
					<td style="width: 30px; text-align: center;">
						<button id="img_L_AllTo_R" type="button" style="width: 50px">>>></button>
						<button id="img_L_To_R" type="button" style="width: 50px">></button> <br />
						<button type="button" onclick="upSelectedOption()" style="width: 50px">上移</button><br /> 
						<button type="button" onclick="downSelectedOption()" style="width: 50px">下移</button><br /> 
						<button id="img_R_To_L" type="button" style="width: 50px"><</button> <br />
						<button id="img_R_AllTo_L" type="button" style="width: 50px"><<<</button> 
					</td>
					<td style="vertical-align: bottom;"><select id="selRight"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect" onclick="doLoseSourceFocus()">
					</select></td>
				</tr>
			</table>
			<div class="conditionsBtn">
				<button onclick="doExport()" type="button">导出</button>
			</div>
		</div>
  </body>
</html>