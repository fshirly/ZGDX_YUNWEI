<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>

	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/acUPS/acUPS_add.js"></script>
		<div id="divWebsiteAdd"  style="height: 450px;overflow: auto;">
		 <input id="moTypeLstJson" type="hidden"/>
		<form id="ipt_acUpsFrom">
			<table id="acInfo" class="formtable" >
				<tr>
					<td class="title">监控类型：</td>
					<td>
						<select class="easyui-combobox" id="ipt_moClassID">
							<option value="96">空调监控</option>
							<option value="73">UPS监控</option>
						</select>
					</td>
					<td class="title">设备IP：</td>
					<td>
						<input  id="ipt_deviceIP" class="input" validator="{'default':'checkEmpty_ipAddr','length':'0-128'}"/><b>*</b>
					</td>
				</tr>
			</table>
						
				<!--SNMP凭证-->
				<table id="tblSNMPCommunityInfo" class="formtable">
				  <tr>
				  	<td class="title">别名： </td>
					<td>
						<input id="ipt_alias" type="text" class="input" validator="{'length':'0-128'}" />
					</td>
				  
				    <td class="title"> 协议版本： </td>
					<td>
						<select id="ipt_snmpVersion" class="inputs"	onChange=isOrnoCheck(); >
							<option value="0"> V1 </option>
							<option value="1"> V2 </option>
							<option value="3"> V3 </option>
						</select><b>*</b>
				  </tr>
				
				  <tr id="readCommunity">
						<td class="title"> 读Community： </td>
						<td>
							<input id="ipt_readCommunity" type="text"  validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title"> 写Community： </td>
						<td>
							<input id="ipt_writeCommunity" type="text" />
						</td>
					</tr>
					
					<tr id="usmUser" style="display: none;">
						<td class="title"> USM用户： </td>
						<td>
							<input id="ipt_usmUser" type="text" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title"> 上下文名称： </td>
						<td>
							<input id="ipt_contexName" type="text" />
						</td>
					</tr>
					<tr id="authAlogrithm" style="display: none;">
						<td class="title"> 认证类型： </td>
						<td>
							<select id="ipt_authAlogrithm" class="inputs">
								<option value="-1"> 请选择 </option>
								<option value="MD5"> MD5 </option>
								<option value="SHA"> SHA </option>
							</select>
						</td>
						<td class="title"> 认证KEY： </td>
						<td>
							<input id="ipt_authKey" type="text" />
						</td>
					</tr>

					<tr id="encryptionAlogrithm" style="display: none;">
						<td class="title"> 加密算法： </td>
						<td>
							<select id="ipt_encryptionAlogrithm" class="inputs">
								<option value="-1"> 请选择 </option>
								<option value="DES"> DES </option>
								<option value="3DES"> 3DES </option>
								<option value="AES-128"> AES-128 </option>
								<option value="AES-192"> AES-192 </option>
								<option value="AES-256"> AES-256 </option>
							</select>
						</td>
						<td class="title"> 加密KEY： </td>
						<td>
							<input id="ipt_encryptionKey" type="text" />
						</td>
					</tr>
					<tr id="snmpPort">
						<td class="title"> SNMP端口： </td>
						<td>
							<input id="ipt_snmpPort" type="text" value='161' validator="{'default':'*','length':'1-30'}"></input><b>*</b>
						</td>
					</tr>
				</table>
			
			<div id= "testDiv">
			 <div id="testBtn" class ="btntd" style="float: right; margin-right: 102px;">
				<a class="btntd" onclick="javascript:getTestCondition();">测试</a>
			  </div>
			 <div id="sucessTip"  style=" padding-top: 40px; text-align: center; display: none">
				 <img src="${pageContext.request.contextPath}/style/images/no_repeat.gif" />
				 <span>&nbsp;&nbsp;测试成功!</span>
			 </div>
			 <div id="errorTip"  style=" padding-top: 40px; text-align: center; display: none">
			 	 <img src="${pageContext.request.contextPath}/style/images/alarm/delete.png" />
				 <span>&nbsp;&nbsp;测试失败，请检查网络是否连通或参数配置是否正确!</span>
			 </div>
			</div>
			<div  id="temp" style="display:none">
			  <table id="tblChooseTemplate" class="formtable1" style="margin-left:48px;margin-top: 15px;">
				<tr>
					<td class="title">选择模板：</td>
					<td style="float: left;">
						<input class="easyui-combobox" id="ipt_templateID"/>
					</td>
				</tr>
			</table>
			<table id="monitor" class="formtable" style="margin-left: 111px;">
			<tr id="monitorTitle"><td class="title" style="float: left;">监测器配置</td></tr>
			</table> 
			</div>
			</form>
			<div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:insertCondition();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
	</body>
</html>
