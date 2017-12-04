<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../common/pageincluded.jsp" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/discover/offlineDiscover.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/discover/offlineSingle_add.js">
        </script>
        <style>
            #offlineDiv .formtable .title {
                min-width: 0;
            }
        </style>
    </head>
    <body onload="selectDiscoverWay(1)">
        <input type="hidden" id="navigationBar" name="navigationBar" value="${navigationBar}"/>
        <form id="formname" method="post">
            <div>
                <div class="location">
                    当前位置：${navigationBar}
                </div>
                <div id="offlineDiv" class="easyui-window" minimizable="false" resizable="false" maximizable="false" closed="false" closable="false" collapsible="false" modal="true" title="设备离线发现" style="width: 900px;height: 450px;">
                    <table class="formtable">
                        <tr style="height: 30px;">
                            <td width="70%" align="left" colspan="12">
                                <input type="radio" checked="" onclick="selectDiscoverWay(1)" value="1" name="selectDiscover">&nbsp;起始IP 
                                &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" onclick="selectDiscoverWay(2)" value="2" name="selectDiscover">&nbsp;子网发现 
                                &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" onclick="selectDiscoverWay(3)" value="3" name="selectDiscover">&nbsp;路由发现 
                                &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" onclick="selectDiscoverWay(4)" value="4" name="selectDiscover">&nbsp;单对象发现 
                            </td>
                        </tr>
                        <tr style="height: 50px">
                            <td colspan="12">
                                <div id="byIPScpoe" style="display: block;">
                                    <table class="formtable" style="margin-left: -10px;">
                                        <tr>
                                            <td class="title">
                                                起始IP&nbsp;:&nbsp;
                                            </td>
                                            <td>
                                                <input type="text" id="startIP1" value="" validator="{'default':'checkEmpty_ipAddr'}"/><b>*</b>
                                            </td>
                                            <td class="title">
                                                &nbsp;&nbsp;&nbsp;&nbsp;终止IP&nbsp;:&nbsp;
                                            </td>
                                            <td>
                                                <input type="text" id="endIP1" value="" validator="{'default':'checkEmpty_ipAddr'}"/><b>*</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title">
                                                选择离线采集机：
                                            </td>
                                            <td>
                                                <input id="ipt_collectorid1" class="input" onclick="choseOfflineCollector(1);" readonly="readonly" validator="{'default':'*'}"/><b>*</b>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="byStartIP">
                                    <table class="formtable" style="margin-left: -10px;">
                                        <tr>
                                            <td class="title">
                                                起始IP&nbsp;:&nbsp;
                                            </td>
                                            <td>
                                                <input type="text" id="startIP2" value="" validator="{'default':'checkEmpty_ipAddr'}"/><b>*</b>
                                            </td>
                                            <td class="title">
                                                网络掩码&nbsp;:&nbsp;
                                            </td>
                                            <td>
                                                <select class="inputs" id="netMask2">
                                                    <option value="255.255.255.252">255.255.255.252</option>
                                                    <option value="255.255.255.248">255.255.255.248</option>
                                                    <option value="255.255.255.240">255.255.255.240</option>
                                                    <option value="255.255.255.224">255.255.255.224</option>
                                                    <option value="255.255.255.192">255.255.255.192</option>
                                                    <option value="255.255.255.128">255.255.255.128</option>
                                                    <option value="255.255.255.0">255.255.255.0</option>
                                                    <option value="255.255.254.0">255.255.254.0</option>
                                                    <option value="255.255.252.0">255.255.252.0</option>
                                                    <option value="255.255.248.0">255.255.248.0</option>
                                                    <option value="255.255.240.0">255.255.240.0</option>
                                                    <option value="255.255.224.0">255.255.224.0</option>
                                                    <option value="255.255.192.0">255.255.192.0</option>
                                                    <option value="255.255.128.0">255.255.128.0</option>
                                                    <option value="255.255.0.0">255.255.0.0</option>
                                                    <option value="255.254.0.0">255.254.0.0</option>
                                                    <option value="255.252.0.0">255.252.0.0</option>
                                                    <option value="255.248.0.0">255.248.0.0</option>
                                                    <option value="255.240.0.0">255.240.0.0</option>
                                                    <option value="255.224.0.0">255.224.0.0</option>
                                                    <option value="255.192.0.0">255.192.0.0</option>
                                                </select>
                                                <b>*</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title">
                                                选择离线采集机：
                                            </td>
                                            <td>
                                                <input id="ipt_collectorid2" class="input" onclick="choseOfflineCollector(2);" readonly="readonly" validator="{'default':'*'}"/><b>*</b>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="byRouteStep">
                                    <table class="formtable" style="margin-left: -10px;">
                                        <tr>
                                            <td class="title">
                                                起始IP&nbsp;:&nbsp;
                                            </td>
                                            <td>
                                                <input type="text" id="startIP3" value="" validator="{'default':'checkEmpty_ipAddr'}"/><b>*</b>
                                            </td>
                                            <td class="title">
                                                &nbsp;&nbsp;&nbsp;&nbsp;发现跳数&nbsp;:&nbsp;
                                            </td>
                                            <td>
                                                <input type="text" id="step3" value="" validator="{'default':'checkEmpty_ptInteger'}"/><b>*</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title">
                                                选择离线采集机：
                                            </td>
                                            <td>
                                                <input id="ipt_collectorid3" class="input" onclick="choseOfflineCollector(3);" readonly="readonly" validator="{'default':'*'}"/><b>*</b>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="singleDiscover">
                                    <input type="hidden" id="authType"><input type="hidden" id="className"><input type="hidden" id="isExistSNMP"><input type="hidden" id="isExsitVmware"><input type="hidden" id="isExistDB"><input type="hidden" id="isExistMiddle"><input type="hidden" id="isExistRoom"><input type="hidden" id="isExistSite"><input type="hidden" id="siteCommunityId" /><input id="moTypeLstJson" type="hidden"/>
                                    <div>
                                        <table id="tblAddDevice" class="formtable" style="margin-left: -15px;">
                                            <tr id="objTypeTr">
                                                <td class="title" style="text-align: right;">
                                                    对象类型：
                                                </td>
                                                <td>
                                                    <input id="ipt_classID" readonly onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
                                                </td>
                                                <td class="title" id="ipTilte">
                                                    对象IP：
                                                </td>
                                                <td id="ipInput">
                                                    <input id="ipt_deviceIP" class="input" validator="{'default':'*','length':'1-64'}" onfocus="isClass();" onblur="isDiscovered();"/><b>*</b>
                                                    <input id="ipt_deviceId" type="hidden" value="" />
                                                </td>
                                            </tr>
                                            <tr id="authTr">
                                                <td id="authsTitle" class="title" style="display: none">
														认证信息：
                                                </td>
                                                <td id="auths" colspan="3">
                                                    <label id="snmpAuth" style="display: none">
                                                        SNMP凭证
                                                    </label>
                                                    <label id="dbAuth" style="display: none">
														数据库凭证
                                                    </label>
                                                    <label id="vmwareAuth" style="display: none">
                                                        Vmware凭证
                                                    </label>
                                                    <label id="middleAuth" style="display: none">
                                                        JMX凭证
                                                    </label>
                                                    <label id="roomAuth" style="display: none">
														机房环境监控凭证
                                                    </label>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <!-- SNMP凭证 -->
                                    <div id="viewSnmp" class="winbox">
                                        <div class="datas">
                                            <table id="tblSNMPCommunity">
                                            </table>
                                        </div>
                                    </div>
                                    <!--Vmware凭证-->
									<div>
	                                    <table id="tblAuthCommunityInfo" class="formtable" style="margin-left: -15px;display: none">
	                                        <tr>
	                                            <td class="title">
													登录端口：
	                                            </td>
	                                            <td colspan="3">
	                                                <input id="ipt_port" type="text" />
	                                            </td>
	                                        </tr>
	                                        <tr id="readCommunitys">
	                                            <td class="title">
													用户名：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_userName" type="text" validator="{'default':'*','length':'1-30'}">
	                                                </input><b>*</b>
	                                            </td>
	                                            <td class="title">
	                                               	 密码：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_password" type="password" validator="{'default':'*','length':'1-30'}">
	                                                </input><b>*</b>
	                                            </td>
	                                        </tr>
	                                    </table>
									</div>
                                    <!-- 数据库凭证 -->
									<input id="db2CommunityId" type="hidden"/>
									<div>
	                                    <table id="tblDBMSCommunity" class="formtable" style="margin-left: -31px;display: none">
	                                        <tr id="isMysql" style="display: none">
	                                            <td class="title">
													数据库类别：
	                                            </td>
	                                            <td colspan="3">
	                                                <input id="ipt_mysqlDbmsType" type="text" readonly><b>*</b>
	                                            </td>
	                                        </tr>
	                                        <tr id="isOracle">
	                                            <td class="title">
													数据库名：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_dbName" type="text" validator="{'default':'*','length':'1-80'}" onblur="initDB2Community()"/><b>*</b>
	                                            </td>
	                                            <td class="title">
	                            			                    数据库类别：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_dbmsType" type="text" readonly><b>*</b>
	                                            </td>
	                                        </tr>
	                                        <tr>
	                                            <td class="title">
													用户名：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_dbUserName" type="text" validator="{'default':'*','length':'1-50'}" /><b>*</b>
	                                            </td>
	                                            <td class="title">
													密码：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_dbPassword" type="password" validator="{'default':'*','length':'1-50'}"/><b>*</b>
	                                            </td>
	                                        </tr>
	                                        <tr>
	                                            <td class="title">
													端口：
	                                            </td>
	                                            <td colspan="3">
	                                                <input id="ipt_dbPort" type="text" onchange="javascript:resetIP();" onblur="javascript:checkPort(3);"/>
	                                            </td>
	                                        </tr>
	                                    </table>
									</div>
									
									<!-- JMX凭证 -->
									<div>
	                                    <table id="tblMiddlewareCommunity" class="formtable" style="margin-left: -15px;display: none">
	                                        <tr id="isShowUserAndPwd" style="display: none">
	                                            <td class="title">
													用户名：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_middleUserName" type="text" validator="{'length':'0-255'}"/>
	                                            </td>
	                                            <td class="title">
													密码：
	                                            </td>
	                                            <td>
	                                                <input id="ipt_middlePassWord" type="password" validator="{'length':'0-255'}" />
	                                            </td>
	                                        </tr>
	                                        <tr id="middlePortTr">
	                                            <td class="title">
													端口：
	                                            </td>
	                                            <td colspan="3">
	                                                <input id="ipt_middlePort" type="text" validator="{'default':'*' ,'length':'1-128'}" onchange="javascript:resetIP();" onblur="checkPort(4)"/><b>*</b>
	                                            </td>
	                                        </tr>
	                                        <tr>
	                                            <td class="title">
													 控制台URL：
	                                            </td>
	                                            <td colspan="3">
	                                                <input id="ipt_url" type="text" class="x2" validator="{'default':'*','length':'1-255'}"/><b>*</b>
	                                            </td>
	                                        </tr>
	                                    </table>
									</div>
									
                                    <!-- 机房环境监控凭证 -->
									<div>
	                                    <table id="tblRoomCommunity" class="formtable" style="margin-left: -5px;display: none">
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
	                                                <input id="ipt_roomPort" type="text" validator="{'default':'*'}" onchange="javascript:resetIP();" onblur="javascript:checkPort(5);"/><b>*</b>
	                                            </td>
	                                        </tr>
	                                    </table>
									</div>
									
									<!-- 选择离线采集机 -->
									<div id="singleCollectorDiv" style="display:none;">
	                                    <table class="formtableSingle" style="margin-left: -34px;" >
	                                    	<tr>
	                                            <td class="title">
	                                               	 选择离线采集机：
	                                            </td>
	                                            <td colspan="3">
	                                                <input id="ipt_collectorid4" class="input" onclick="choseOfflineCollector(4);" readonly="readonly" validator="{'default':'*'}"/><b>*</b>
	                                            </td>
                                        	</tr>
	                                    </table>
									</div>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 150px;width:10px;">
                            <td class="btntd" style="weight:10px;text-align: center;">
                                <a href="javascript:void(0);" id="btnSave" onclick="toAddOfflineDiscover();">确定</a>
                                <a href="javascript:void(0);" id="btnUpdate" onclick="reset();">重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="divAddSnmp" class="easyui-window" minimizable="false" closed="true" maximizable="false" collapsible="false" modal="true" title="新增SNMP凭证" style="width: 800px;">
                    <!--SNMP凭证-->
                    <table id="tblSNMPCommunityInfo" class="formtable">
                        <tr>
                            <td class="title">
								别名：
                            </td>
                            <td>
                                <input id="ipt_alias" type="text" class="input" validator="{'length':'0-128'}" />
                            </td>
                            <td class="title">
								协议版本：
                            </td>
                            <td>
                            <select id="ipt_snmpVersion" class="inputs" onChange=isOrnoCheck();>
                                <option value="0">V1</option>
                                <option value="1">V2</option>
                                <option value="3">V3</option>
                            </select>
                            <b>*</b>
                        </tr>
                        <tr id="readCommunity">
                            <td class="title">
								读团体名：
                            </td>
                            <td>
                                <input id="ipt_readCommunity" type="text" validator="{'default':'*','length':'1-50'}"/><b>*</b>
                            </td>
                            <td class="title">
								写团体名：
                            </td>
                            <td>
                                <input id="ipt_writeCommunity" type="text" />
                            </td>
                        </tr>
                        <tr id="usmUser" style="display: none;">
                            <td class="title">
                                USM用户：
                            </td>
                            <td>
                                <input id="ipt_usmUser" type="text" validator="{'default':'*','length':'1-50'}"/><b>*</b>
                            </td>
                            <td class="title">
								上下文名称：
                            </td>
                            <td>
                                <input id="ipt_contexName" type="text" />
                            </td>
                        </tr>
                        <tr id="authAlogrithm" style="display: none;">
                            <td class="title">
								认证类型：
                            </td>
                            <td>
                                <select id="ipt_authAlogrithm" class="inputs">
                                    <option value="-1">请选择</option>
                                    <option value="MD5">MD5</option>
                                    <option value="SHA">SHA</option>
                                </select>
                            </td>
                            <td class="title">
								认证KEY：
                            </td>
                            <td>
                                <input id="ipt_authKey" type="text" />
                            </td>
                        </tr>
                        <tr id="encryptionAlogrithm" style="display: none;">
                            <td class="title">
								加密算法：
                            </td>
                            <td>
                                <select id="ipt_encryptionAlogrithm" class="inputs">
                                    <option value="-1">请选择</option>
                                    <option value="DES">DES</option>
                                    <option value="3DES">3DES</option>
                                    <option value="AES-128">AES-128</option>
                                    <option value="AES-192">AES-192</option>
                                    <option value="AES-256">AES-256</option>
                                </select>
                            </td>
                            <td class="title">
								加密KEY：
                            </td>
                            <td>
                                <input id="ipt_encryptionKey" type="text" />
                            </td>
                        </tr>
                        <tr id="snmpPort">
                            <td class="title">
                                SNMP端口：
                            </td>
                            <td>
                                <input id="ipt_snmpPort" type="text" value='161' validator="{'default':'*','length':'1-30'}">
                                </input><b>*</b>
                            </td>
                        </tr>
                    </table>
                    <div class="conditionsBtn">
                        <a href="javascript:void(0);" id="btnAddSnmpNow" class="fltrt">确定</a>
                        <a href="javascript:void(0);" id="btnColseSnmpNow" class="fltrt">取消</a>
                    </div>
                </div>
                <div id="divMObject" class="easyui-window" maximizable="false" collapsible="false" minimizable="false" closed="true" modal="true" title="选择对象类型" style="width: 400px; height: 450px;">
                    <div id="dataMObjectTreeDiv" class="dtree" style="width: 100%; height: 200px;">
                    </div>
                </div>
                <div id="divHost" class="easyui-window" maximizable="false" collapsible="false" minimizable="false" closed="true" modal="true" title="选择离线采集机" style="width: 400px; height: 450px;">
                    <div id="dataHostTreeDiv" class="dtree" style="width: 100%; height: 200px;">
                    </div>
                </div>
                </body>
            </html>
