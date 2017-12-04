<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  
  <body>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/subnetInfo_list.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<div class="rightContent">
	  	<div class="conditions" id="divFilter">
	  	 <form id="frmExport"
				action="${pageContext.request.contextPath}/platform/subnetViewManager/exportAllVal">
	  	  <input name="colNameArrStr" id="iptColName" type="hidden" /> 
		  <input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
		  <input name="exlName" id="iptExlName" value="子网全局信息列表.xls" type="hidden" />
	  	  <table>
	  	  	<tr>
	  	  	  <td colspan="2">
	  	  	      <b>筛选地址范围：</b>
	  	  		  <input id="txtStartIp" type="text"/> - 
	    		  <input id="txtEndIp" type="text"/>
	  	  	  </td>
	  	  		
	  	  	  <td>
				  <b>部门名称：</b>
				  <input type="text" id="txtDeptName" />
			  </td>
			</tr>
			<tr>
			  <td>&nbsp;&nbsp;&nbsp;
				  <b>子网容量：</b>
			 	   <select class="easyui-combobox" id="txtTotalNum">
					<option value="-1">请选择</option>
					<option value="16">16</option>
					<option value="32">32</option>
					<option value="64">64</option>
					<option value="128">128</option>
					<option value="256">256</option>
					<option value="512">512</option>
					<option value="1024">1024</option>
					<option value="2048">2048</option>
				  </select>
			  </td>
			  <td></td>
			  <td class="btntd">
				  <a href="javascript:void(0);"	onclick="reloadTable();">查询</a>
				  <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			  </td>
	  	  	</tr>
	  	  </table>
	  	 </form>
	  	</div>
	  	
	  	<div class="datas tops3">
			<table id="tblSubnetInfoList">
			</table>
		</div>
	</div>
	
	<div id="divExportAll" class="easyui-window" minimizable="false"
			closed="true" modal="true" title="选择导出列"
			style="width: 600px; height: 400px; display: none">
			<table cellspacing="5" class="tdpad">
				<tr>
					<td style='vertical-align: bottom;'><select id="selLeft"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect" onclick="doLoseTargetFocus()">
							<option alt="ipAddress">网络地址</option>
							<option alt="subNetMark">子网掩码</option>
							<option alt="usedNum">占用地址数</option>
							<option alt="freeNum">空闲地址数</option>
							<option alt="totalNum">子网容量</option>
							<option alt="broadCast">广播地址</option>
							<option alt="gateway">网关</option>
							<option alt="dnsAddress">DNS</option>
							<option alt="subNetTypeName">所属网络类型</option>
							<option alt="descr">子网描述</option>
					</select></td>
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