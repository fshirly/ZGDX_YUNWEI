<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head>

	<body>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/website/webSite_add.js"></script>
		<div id="divWebsiteAdd"  style="height: 450px;overflow: auto;">
		 <input type="hidden" id="isExistSite">
	     <input type="hidden" id="siteCommunityId" />
	     <input id="moTypeLstJson" type="hidden"/>
		
			<table class="formtable" >
				<tr style="float:left;margin-left: 4px;">
					<td class="title">监控类型：</td>
					<td colspan="3">
						<input class="easyui-combobox" id="ipt_moClassID" onblur="javascript:setDefualt(0);"/><b>*</b>
					</td>
				</tr>
			</table>
			<!--DNS监控 -->
			<table id="tblSiteDNS" class="formtable" style="display: none;">
				<tr>
					<td class="title">站点名称：</td>
					<td colspan="3">
						<input id="ipt_dnsSiteName" type="text" validator="{'length':'1-40'}"  class="x2" /><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">监控地址：</td>
					<td colspan="3">
						<input id="ipt_domainName" type="text" class="x2" validator="{'default':'domain','length':'1-128'}"/><b>*</b>
					</td>
				</tr>
				<%--<tr>
					<td class="title">监控周期：</td>
					<td colspan="3">
						<input id="ipt_dnsPeriod" type="text" validator="{'default':'checkEmpty_ptInteger'}"/>&nbsp;分钟&nbsp;<b>*</b>
					</td>
				</tr>
				--%><tr>
					<td class="title">期望解析IP：</td>
					<td colspan="3">
						<input id="ipt_dnsIPAddr" type="text" class="x2" validator="{'length':'0-128'}"  class="input"/>
					</td>
				</tr>
				<tr>
					<td class="title">站点说明：</td>
					<td colspan="3">
					    <textarea rows="3" id="ipt_dnsSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
					</td>
				</tr>
			</table>
		
			<!--FTP监控 -->
			<div style="margin-left: -10px;">
			<table id="tblSiteFTP" class="formtable" style="display: none">
				<tr>
					<td class="title">站点名称：</td>
					<td colspan="3">
						<input id="ipt_ftpSiteName" class="x2" type="text" validator="{'length':'1-40'}" /><b>*</b>
					</td>
				</tr>
				<tr>	
					<td class="title">监控地址：</td>
					<td colspan="3">
						<input id="ipt_ftpIPAddr" class="x2" type="text" validator="{'default':'ipAddr','length':'1-128'}" onblur="initFTPCommunity();"/><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">FTP端口号：</td>
					<td colspan="3">
						<input id="ipt_ftpPort" type="text" validator="{'default':'checkEmpty_ptInteger'}"  class="input"/><b>*</b>
					</td>
				</tr>
				<%--<tr>
					<td class="title">监控周期：</td>
					<td colspan="3">
						<input id="ipt_ftpPeriod" type="text" validator="{'default':'checkEmpty_ptInteger'}"/>&nbsp;分钟&nbsp;<b>*</b>
					</td>
				</tr>
				--%><tr>
					<td class="title">站点说明：</td>
					<td colspan="3">
					    <textarea rows="3" id="ipt_ftpSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
					</td>
				</tr>
				<tr>
					<td class="title">FTP身份验证选项：</td>
					<td colspan="3">
						<input id="ipt_isAuth" class="input"  type="hidden"/>
						<input type="radio" id="ipt_isAuth1" name="isAuth"  value="1" checked style="width:13px" onclick="javascript:edit();">&nbsp;匿名登录
						&nbsp;
						<input type="radio" id="ipt_isAuth2" name="isAuth" value="2" style="width:13px" onclick="javascript:edit();"/>&nbsp;需要身份验证
					</td>
				</tr>
			</table>
			</div>
			<table id="isShowFtpcom"  class="formtable"  style="display: none">
				<tr>
					<td class="title">用户名：</td>
					<td>
						<input id="ipt_ftpUserName" type="text" validator="{'default':'*','length':'1-128'}"  class="input"/><b>*</b>
					</td>
					<td class="title">密码：</td>
					<td>
						<input id="ipt_ftpPassWord" type="password" validator="{'default':'*','length':'1-128'}"  class="input"/><b>*</b>
					</td>
				</tr>
			</table>
		
			<!--http监控 -->
			<table id="tblSiteHttp" class="formtable" style="display: none">
				<tr>
					<td class="title">站点名称：</td>
					<td colspan="3">
						<input id="ipt_httpSiteName"  class="x2"  type="text" validator="{'length':'1-40'}" /><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">监控地址：</td>
					<td colspan="3">
						<input id="ipt_httpUrl" type="text"  class="x2" validator="{'default':'url','length':'1-5000'}" onblur="initHttpCommunity();"/><b>*</b>
					</td>
				</tr>
				<%--<tr>	
					<td class="title">监控周期：</td>
					<td colspan="3">
						<input id="ipt_httpPeriod" type="text" validator="{'default':'checkEmpty_ptInteger'}"/>&nbsp;分钟&nbsp;<b>*</b>
					</td>
				</tr>
				--%><tr>
					<td class="title">请求方式：</td>
					<td colspan="3">
						<input id="ipt_requestMethod" class="input"  type="hidden"/>
						<input type="radio" id="ipt_requestMethod1" name="requestMethod"  value="1" checked style="width:13px">&nbsp;GET
						&nbsp;
						<input type="radio" id="ipt_requestMethod2" name="requestMethod" value="2" style="width:13px"/>&nbsp;POST
						&nbsp;
						<input type="radio" id="ipt_requestMethod3" name="requestMethod" value="3" style="width:13px"/>&nbsp;HEAD
					</td>
				</tr>
				<tr>
					<td class="title">站点说明：</td>
					<td colspan="3">
						<textarea rows="3" id="ipt_httpSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
					</td>
				</tr>
			</table>
			
			<!--TCP监控 -->
			<div>
			<table id="tblSiteTcp" class="formtable" style="display: none">
				<tr>
					<td class="title">站点名称：</td>
					<td colspan="3">
						<input id="ipt_tcpSiteName" class="x2" type="text" validator="{'length':'1-40'}" /><b>*</b>
					</td>
				</tr>
				<tr>	
					<td class="title">监控地址：</td>
					<td colspan="3">
						<input id="ipt_tcpIPAddr" class="x2" type="text" validator="{'default':'ipAddr','length':'1-128'}" onblur="initFTPCommunity();"/><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">端口号：</td>
					<td colspan="3">
						<input id="ipt_tcpPort" type="text" validator="{'default':'checkEmpty_ptInteger'}"  class="input"/><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">站点说明：</td>
					<td colspan="3">
					    <textarea rows="3" id="ipt_tcpSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
					</td>
				</tr>
			</table>
			</div>
			
			<div id= "testDiv" style="display: none">
			 <div id="testBtn" class ="btntd" style="float: right; margin-right: 102px;">
				<a class="btntd" onclick="javascript:getTestSite();">测试</a>
			  </div>
			 <div id="sucessTip"  style=" padding-top: 40px; text-align: center; display: none">
				 <img src="${pageContext.request.contextPath}/style/images/no_repeat.gif" />&nbsp;&nbsp;测试成功！
			 </div>
			 <div id="errorTip"  style=" padding-top: 40px; text-align: center; display: none">
			 	 <img src="${pageContext.request.contextPath}/style/images/alarm/delete.png" />
				&nbsp;&nbsp; 测试失败，请检查网络是否连通或参数配置是否正确！
			 </div>
			</div>
			
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
			
			<div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:toAddSite();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
			
			
		</div>
	</body>
</html>
