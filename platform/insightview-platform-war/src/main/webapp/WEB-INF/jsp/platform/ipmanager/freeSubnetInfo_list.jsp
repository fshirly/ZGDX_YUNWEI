<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  
  <body>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/freeSubnetInfo_list.js"></script>
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
				action="${pageContext.request.contextPath}/platform/subnetViewManager/exportFreeVal">
	  	  <input name="colNameArrStr" id="iptColName" type="hidden" /> 
		  <input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
		  <input name="belongSubnetId" id="belongSubnetId" type="hidden"/> 
		  <input name="exlName" id="iptExlName" value="子网空闲信息列表.xls" type="hidden" />
	  	  <table>
	  	  	<tr>
	  	  	  <td>
	  	  	      <b>筛选地址范围：</b>
	  	  		  <input id="txtStartIp" type="text" class="s50"/> - 
	    		  <input id="txtEndIp" type="text" class="s50"/>
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
			<table id="tblFreeSubnetInfoList">
			</table>
		</div>
	</div>
	
	<div id="divExportFree" class="easyui-window" minimizable="false"
			closed="true" modal="true" title="选择导出列"
			style="width: 600px; height: 400px;">
			<table cellspacing="5" class="tdpad">
				<tr>
					<td style='vertical-align: bottom;'>
						<select id="selLeft"
							multiple="multiple" style="width: 150px; height: 200px"
							class="dataSelect" onclick="doLoseTargetFocus()">
								<option alt="ipAddress">IP地址</option>
								<option alt="subNetMark">子网掩码</option>
								<option alt="gateway">网关</option>
								<option alt="dnsAddress">DNS</option>
								<option alt="subNetTypeName">所属网络类型</option>
								<option alt="note">备注</option>
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