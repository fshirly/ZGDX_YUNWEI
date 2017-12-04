<%@ page language="java"  pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file = "../../common/pageincluded.jsp" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
    	<%
			String taskId =(String)request.getAttribute("taskId");
			String flag =(String)request.getAttribute("flag");
			String serverId =(String)request.getAttribute("serverId");
			String serverName =(String)request.getAttribute("serverName");
		 %>
		 <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/perf/offlinePerfTask_edit.js"></script>
		 <script type="text/javascript"	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		 <input type="hidden" id="taskId" value="<%= taskId%>"/>
		 <input type="hidden" id="serverId" value="<%= serverId%>"/>
		 <input type="hidden" id="flag" value="<%= flag%>"/>
		 <input type="hidden" id="serverName" value="<%= serverName%>"/>
        <div id="divEditTask" style="height:360px;overflow: auto;">
            <div>
				<input id="ipt_taskId" type="hidden" />
				<input id="ipt_moId" type="hidden" />
				<input id="ipt_status" type="hidden" />
				<input type="hidden" id="authType"/>
				<input id="ipt_dbmsServerId" type="hidden" />
				<input id="ipt_middleWareId" type="hidden" />
				<input id="ipt_zoneManagerId" type="hidden" />
				<input id="ipt_webSiteMoID" type="hidden"/>
				<input id="collectPeriod" type="hidden"/>
				<input id="templateID" type="hidden"/>
				<input id="moTypeLstJson" type="hidden"/>
                <table id="tblEditTask" class="formtable">
                    <tr id="classFlag">
                        <td class="title">
                            对象类型：
                        </td>
                        <td colspan="3">
                            <input id="ipt_moClassId" class="input" onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
                        </td>
                    </tr>
                    <tr id="isShowIPTr">
                        <td class="title">
                            设备IP：
                        </td>
                        <td>
                            <input id="ipt_deviceIp" readonly class="input" validator="{'default':'*','length':'1-64'}" /><b>*</b>
                            <input id="ipt_deviceId" type="hidden" value=""/><a href="javascript:loadDeviceInfo();" id="btnChose">选择</a>
                        </td>
                        <td class="title">
                            所属管理域：
                        </td>
                        <td>
                            <input id="ipt_domainName" readonly class="input"/><input id="ipt_domainId" type="hidden"/>
                        </td>
                    </tr>
                    <tr id="isShowSiteTr" style="display: none;">
                        <td class="title">
                            站点名称：
                        </td>
                        <td colspan="3">
                            <input id="ipt_siteName" readonly class="input" validator="{'default':'*'}" /><b>*</b>
                            <input id="ipt_deviceId" type="hidden" value=""/><a href="javascript:loadSiteInfo();" id="btnChose2">选择</a>
                        </td>
                    </tr>
                    <tr id="isShowSiteTr2" style="display: none;">
                        <td class="title">
                            监控地址：
                        </td>
                        <td colspan="3">
                            <input id="ipt_siteUrl" readonly validator="{'default':'*'}" class="x2"/>
                        </td>
                    </tr>
                    <tr id="hostFlag">
                        <td class="title">
                            所属采集机：
                        </td>
                        <td>
                            <input type="hidden" id="ipt_oldCollectorId" class="input"/><input id="ipt_serverId" class="input" onfocus="choseHostTree();"/><a id="btnUnChose" href="javascript:cancelChose();" style="display: none">清空</a>
                        </td>
                    </tr>
                    <tr id="deviceInfo">
                        <td class="title">
                            设备厂商：
                        </td>
                        <td>
                            <input id="ipt_deviceManufacture" readonly class="input"/>
                        </td>
                        <td class="title">
                            设备型号：
                        </td>
                        <td>
                            <input id="ipt_deviceType" readonly class="input"/>
                        </td>
                    </tr>
                    <tr id="collectorTr">
                        <td class="title">
                            选择离线采集机：
                        </td>
                        <td>
                            <input id="ipt_collectorId" class="input" onclick="choseOfflineCollector();" readonly="readonly" validator="{'default':'*'}"/><b>*</b>
                        </td>
                    </tr>
                </table>
                <!--SNMP凭证-->
                <table id="tblSNMPCommunityInfo" class="formtable" style="display: none">
                    <tr>
                        <td class="title">
                            SNMP版本：
                        </td>
                        <td colspan="3">
                            <input type="hidden" id="ipt_snmpVersion" readonly="readonly" disabled="disabled"/><input type="text" id="ipt_snmpVersion1" readonly="readonly" disabled="disabled"/>
                        </td>
                    </tr>
                    <tr id="V1_snmp" style="display: none;">
                        <td class="title">
                            管理对象：
                        </td>
                        <td>
                            <input id="ipt_moNameV1" class="inputV1" disabled="disabled" readonly/><input id="ipt_moName" type="hidden" disabled="disabled"/>
                        </td>
                        <td class="title">
                            SNMP端口：
                        </td>
                        <td>
                            <input id="ipt_snmpPortV1" value='161' validator="{'default':'*','length':'1-128'}" readonly="readonly" disabled="disabled"/><b>*</b>
                            <input id="ipt_snmpPort" type="hidden" />
                        </td>
                    </tr>
                    <tr id="V1_readCommunity" style="display: none;">
                        <td class="title">
                            读团体名：
                        </td>
                        <td>
                            <input id="ipt_readCommunityV1" value="public" validator="{'default':'*','length':'1-50'}" class="inputV1" readonly="readonly" disabled="disabled"/><b>*</b>
                            <input id="ipt_readCommunity" type="hidden"/>
                        </td>
                        <td class="title">
                            写团体名：
                        </td>
                        <td>
                            <input id="ipt_writeCommunityV1" validator="{'length':'0-50'}" class="inputV1" readonly="readonly" disabled="disabled"/><input id="ipt_writeCommunity" type="hidden"/>
                        </td>
                    </tr>
                    <tr id="V3_snmp" style="display: none;">
                        <td class="title">
                            管理对象：
                        </td>
                        <td>
                            <input id="ipt_moNameV3" class="inputV3" readonly disabled="disabled"/>
                        </td>
                        <td class="title">
                            SNMP端口：
                        </td>
                        <td>
                            <input id="ipt_snmpPortV3" value='161' validator="{'default':'*','length':'1-128'}" readonly="readonly" disabled="disabled"/><b>*</b>
                        </td>
                    </tr>
                    <tr id="V3_readCommunity" style="display: none;">
                        <td class="title">
                            读团体名：
                        </td>
                        <td>
                            <input id="ipt_readCommunityV3" value="public" validator="{'default':'*','length':'1-50'}" class="inputV3" readonly="readonly" disabled="disabled"/><b>*</b>
                        </td>
                        <td class="title">
                            写团体名：
                        </td>
                        <td>
                            <input id="ipt_writeCommunityV3" validator="{'length':'0-50'}" class="inputV1" readonly="readonly" disabled="disabled"/>
                        </td>
                    </tr>
                    <tr id="V3_usm" style="display: none;">
                        <td class="title">
                            上下文名称：
                        </td>
                        <td>
                            <input id="ipt_contexName" class="input2" readonly="readonly" disabled="disabled"/>
                        </td>
                        <td class="title">
                            安全名：
                        </td>
                        <td>
                            <input id="ipt_usmUser" class="input2" validator="{'default':'*','length':'1-50'}" readonly="readonly" disabled="disabled"/><b>*</b>
                        </td>
                    </tr>
                    <tr id="V3_auth" style="display: none;">
                        <td class="title">
                            认证类型：
                        </td>
                        <td>
                            <input id="ipt_authAlogrithm" class="input2" readonly="readonly" disabled="disabled"/>
                        </td>
                        <td class="title">
                            认证KEY：
                        </td>
                        <td>
                            <input id="ipt_authKey" class="input2" readonly="readonly" disabled="disabled"/>
                        </td>
                    </tr>
                    <tr id="V3_encryption" style="display: none;">
                        <td class="title">
                            加密类型：
                        </td>
                        <td>
                            <input id="ipt_encryptionAlogrithm" class="input2" readonly="readonly" disabled="disabled"/>
                        </td>
                        <td class="title">
                            加密KEY：
                        </td>
                        <td>
                            <input id="ipt_encryptionKey" class="input2" readonly="readonly" disabled="disabled"/>
                        </td>
                    </tr>
                </table><!--Vmware凭证-->
                <table id="tblAuthCommunityInfo" class="formtable" style="display: none">
                    <tr>
                        <td class="title">
                            登录端口：
                        </td>
                        <td colspan="3">
                            <input id="ipt_port" type="text" class="input"/>
                        </td>
                    </tr>
                    <tr id="readCommunitys">
                        <td class="title">
                            用户名：
                        </td>
                        <td>
                            <input id="ipt_userName" type="text" validator="{'default':'*','length':'1-50'}" class="input">
                            </input><b>*</b>
                        </td>
                        <td class="title">
                            密码：
                        </td>
                        <td>
                            <input id="ipt_password" type="password" validator="{'default':'*','length':'1-50'}" class="input">
                            </input><b>*</b>
                        </td>
                    </tr>
                </table>
                <!-- 数据库凭证 -->
                <table id="tblDBMSCommunity" class="formtable" style="display: none">
                    <tr>
                        <td class="title">
                            数据库名：
                        </td>
                        <td>
                            <input id="ipt_dbName" type="text" validator="{'default':'*','length':'1-80'}" class="input" onblur="initDB2Community()"/><b>*</b>
                        </td>
                        <td class="title">
                            数据库类别：
                        </td>
                        <td>
                            <input id="ipt_dbmsType" class="input" type="text" readonly/><b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            用户名：
                        </td>
                        <td>
                            <input id="ipt_dbUserName" type="text" class="input" validator="{'default':'*','length':'1-50'}" /><b>*</b>
                        </td>
                        <td class="title">
                            密码：
                        </td>
                        <td>
                            <input id="ipt_dbPassword" type="password" class="input" validator="{'default':'*','length':'1-50'}"/><b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            端口：
                        </td>
                        <td colspan="3">
                            <input id="ipt_dbPort" class="input" type="text" validator="{'default':'*'}"/><b>*</b>
                        </td>
                    </tr>
                </table>
                <!-- 中间件认证 -->
                <table id="tblMiddleWareCommunity" class="formtable" style="display: none">
                    <tr>
                        <td class="title">
                            中间件名称：
                        </td>
                        <td>
                            <input id="ipt_middleWareName" class="input" type="text" readonly/><b>*</b>
                        </td>
                        <td class="title">
                            中间件类型：
                        </td>
                        <td>
                            <input id="ipt_middleWareType" class="input" type="text" readonly><b>*</b>
                        </td>
                    </tr>
                    <tr id="isShowUserAndPwd" style="display: none">
                        <td class="title">
                            用户名：
                        </td>
                        <td>
                            <input id="ipt_middleUserName" class="input" type="text" />
                        </td>
                        <td class="title">
                            密码：
                        </td>
                        <td>
                            <input id="ipt_middlePassword" class="input" type="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            端口：
                        </td>
                        <td colspan="3">
                            <input id="ipt_middlePort" type="text" class="input" validator="{'default':'*','length':'1-128'}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            控制台URL：
                        </td>
                        <td colspan="3">
                            <input id="ipt_url" type="text" class="x2" validator="{'default':'*','length':'1-150'}" msg="{reg:'1-150位字符！'}" onfocus="urlClick();"/><b>*</b>
                        </td>
                    </tr>
                </table>
                <!-- 机房环境监控凭证 -->
                <table id="tblRoomCommunity" class="formtable" style="display: none">
                    <tr>
                        <td class="title">
                            用户名：
                        </td>
                        <td>
                            <input id="ipt_roomUserName" type="text" validator="{'length':'0-128'}"/>
                        </td>
                        <td class="title">
                            密码：
                        </td>
                        <td>
                            <input id="ipt_roomPassWord" type="password" validator="{'length':'0-128'}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            端口：
                        </td>
                        <td colspan="3">
                            <input id="ipt_roomPort" type="text" validator="{'default':'*'}" /><b>*</b>
                        </td>
                    </tr>
                </table>
                <!-- ftp凭证 -->
                <table id="tblFtpCommunity" class="formtable" style="display: none">
                    <tr>
                        <td class="title">
                            用户名：
                        </td>
                        <td>
                            <input id="ipt_isAuth" class="input" type="hidden"/><input id="ipt_ftpIPAddr" class="input" type="hidden"/><input id="ipt_ftpUserName" type="text" validator="{'default':'*','length':'1-128'}"/><b>*</b>
                        </td>
                        <td class="title">
                            密码：
                        </td>
                        <td>
                            <input id="ipt_ftpPassWord" type="password" validator="{'default':'*','length':'1-128'}" /><b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            端口：
                        </td>
                        <td colspan="3">
                            <input id="ipt_ftpPort" type="text" validator="{'default':'*'}" readonly="readonly"/><b>*</b>
                        </td>
                    </tr>
                </table>
                <!-- http凭证 -->
                <table id="tblHttpCommunity" class="formtable" style="display: none">
                    <tr style="float: left;">
                        <td class="title">
                            请求方式：
                        </td>
                        <td colspan="3">
                            <input id="ipt_httpUrl" class="input" type="hidden"/><input id="ipt_requestMethod" class="input" type="hidden"/><input type="radio" id="ipt_requestMethod1" name="requestMethod" value="1" checked style="width:13px">&nbsp;GET &nbsp;<input type="radio" id="ipt_requestMethod2" name="requestMethod" value="2" style="width:13px"/>&nbsp;POST
                            &nbsp;<input type="radio" id="ipt_requestMethod3" name="requestMethod" value="3" style="width:13px"/>&nbsp;HEAD
                        </td>
                    </tr>
                </table>
                <table id="tblChooseTemplate" class="formtable1" style="margin-left:48px;">
                    <tr>
                        <td class="title">
                            选择模板：
                        </td>
                        <td style="float: left;">
                            <input class="easyui-combobox" id="ipt_templateID"/>
                        </td>
                    </tr>
                </table>
                <table id="monitor" class="formtable" style="margin-left: 111px;">
                <tr id="monitorTitle">
                    <td class="title" style="float: left;">
                        监测器配置
                    </td>
                </tr>
            </div>
            <div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:toEdit();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
        </div>
        <div id="divMObject" class="easyui-window" maximizable="false" collapsible="false" minimizable="false" closed="true" modal="true" title="选择对象类型" style="width: 400px; height: 450px;">
            <div id="dataMObjectTreeDiv" class="dtree" style="width: 100%; height: 200px;">
            </div>
        </div>
        <div id="divHost" class="easyui-window" maximizable="false" collapsible="false" minimizable="false" closed="true" modal="true" title="选择所属采集机" style="width: 400px; height: 450px;">
            <div id="dataHostTreeDiv" class="dtree" style="width: 100%; height: 200px;">
            </div>
        </div>
    </body>
</html>
