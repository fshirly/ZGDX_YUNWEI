<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
	</head>

	<body onunload="checkLeave()">
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/fui/plugin/fui-richtext.min.js"></script>
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/fui/locale/fui-lang-zh_CN.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmnotifycfg/alarmNotifyCfg_add.js"></script>
		<div id="alarmNotifyTabs" class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" style="height: 400px;overflow: auto;">
			<input type="hidden" id="policyID"/>
		
			<div title="通知策略定义" data-options="closable:false" >
					<table id="tblAlarmNotifyCfgAdd" class="formtable" style="width: 660px;">
						<tr>
							<td class="title">规则名称：</td>
							<td><input id="ipt_policyName" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
							</td>
							<td class="title">重复告警条件（次）：</td>
							<td><input id="ipt_alarmOnCount" class="input" value="0" validator="{'default':'checkEmpty_noInteger'}"/><b>*</b>
							</td>
						</tr>
						<tr>
							<td class="title">最大发送次数：</td>
							<td colspan="3">
								<input id="ipt_maxTimes" class="input" value="3" validator="{'default':'ptInteger'}"/>
							</td>
						</tr>
						<tr>
							<td class="title">是否短信：</td>
							<td colspan="3"><input id="ipt_isSms"  type="hidden"></input>
								<input type="radio" name="isSms" onclick="javascript:editSms()" value="1" checked>&nbsp;是
								&nbsp;
								<input type="radio" name="isSms" onclick="javascript:editSms()" value="0"/>&nbsp;否
							</td>
						</tr>
						<tr id='sms1'>
							<td class="title" id='smsTitle'>短信内容模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_smsAlarmContent" validator="{'length':'0-800'}">\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}发生告警：\${alarm.alarmContent}。</textarea></td>
						</tr>
						<tr id='sms2'>
							<td class="title" id='smsTitle'>清除告警短信内容模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_smsClearAlarmContent" validator="{'length':'0-800'}">\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}于\${alarm.startTime}发生的告警事件：\${alarm.alarmTitle}已清除！</textarea></td>
						</tr>
						<tr id='sms3'>
							<td class="title" id='smsTitle'>删除告警短信内容模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_smsDeleteAlarmContent" validator="{'length':'0-800'}">\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}于\${alarm.startTime}发生的告警事件：\${alarm.alarmTitle}已删除！</textarea></td>
						</tr>
						<tr>
							<td class="title">是否邮件：</td>
							<td colspan="3"><input id="ipt_isEmail"  type="hidden"></input>
								<input type="radio" name="isEmail" onclick="javascript:editEmail()" value="1" checked>&nbsp;是
								&nbsp;
								<input type="radio" name="isEmail" onclick="javascript:editEmail()" value="0"/>&nbsp;否
							</td>
						</tr>
	
						<tr id="email1">
							<td class="title">邮件内容模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_emailAlarmContent">尊敬的\${user.userName}:<br/>&nbsp;&nbsp;&nbsp;&nbsp;你好！\${alarm.sourceMOClassName}设备\${alarm.sourceMOIPAddress}下的\${alarm.moClassName}:\${alarm.moName}，于\${alarm.startTime}发生告警： \${alarm.alarmTitle}，告警的等级为：\${alarm.alarmLevelName}，告警的类型为：\${alarm.alarmTypeName}。具体的告警详情如下：<br/>&nbsp;&nbsp;&nbsp;&nbsp;\${alarm.alarmContent}<br/><br/>飞搏运维管理平台<br/>江苏飞搏软件股份有限公司</textarea></td>
						</tr>
						<tr id='email2'>
							<td class="title" id='smsTitle'>清除告警邮件内容模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_emailClearAlarmContent" >尊敬的\${user.userName}:<br/>&nbsp;&nbsp;&nbsp;&nbsp;你好！\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}于\${alarm.startTime}发生的告警事件：\${alarm.alarmTitle}已清除！<br/><br/>飞搏运维管理平台<br/>江苏飞搏软件股份有限公司</textarea></td>
						</tr>
						<tr id='email3'>
							<td class="title" id='smsTitle'>删除告警邮件内容模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_emailDeleteAlarmContent" >尊敬的\${user.userName}:<br/>&nbsp;&nbsp;&nbsp;&nbsp;你好！\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}于\${alarm.startTime}发生的告警事件：\${alarm.alarmTitle}已删除！<br/><br/>飞搏运维管理平台<br/>江苏飞搏软件股份有限公司</textarea></td>
						</tr>
						
						<tr>
							<td class="title">是否电话语音：</td>
							<td><input id="ipt_isPhone"  type="hidden"></input>
								<input type="radio" name="isPhone" onclick="javascript:editPhone()" value="1" checked>&nbsp;是
								&nbsp;
								<input type="radio" name="isPhone" onclick="javascript:editPhone()" value="0"/>&nbsp;否
							</td>
							
							<td id ="voiceTitle" class="title">语音通知类型：</td>
							<td id="voiceTd"><input id="ipt_voiceMessageType"  type="hidden"></input>
								<input type="radio" name="voiceMessageType" onclick="javascript:editVoice()" value="1" checked>&nbsp;文字模板
								&nbsp;
								<input type="radio" name="voiceMessageType" onclick="javascript:editVoice()" value="2"/>&nbsp;录音
							</td>
						</tr>
						<tr id="phone1">
							<td class="title">电话语音模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_phoneAlarmContent">\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}发生告警：\${alarm.alarmContent}。</textarea></td>
						</tr>
						<tr id='phone2'>
							<td class="title">清除告警电话语音模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_phoneClearAlarmContent" >\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}于\${alarm.startTime}发生的告警事件：\${alarm.alarmTitle}已清除！</textarea></td>
						</tr>
						<tr id='phone3'>
							<td class="title">删除告警电话语音模板：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_phoneDeleteAlarmContent" >\${alarm.sourceMOName} \${alarm.sourceMOIPAddress}于\${alarm.startTime}发生的告警事件：\${alarm.alarmTitle}已删除！</textarea></td>
						</tr>
						<tr id="voice1">
						    <td class="title">告警电话通知录音：</td>
							<td>
								<select id="ipt_alarmVoiceID" class="easyui-combobox" data-options="panelWidth:180,editable:false">
									<option value="-1">请选择...</option>
								    <c:forEach items="${voiceList}" var="vo">
										<option value="<c:out value='${vo.id}' />" ><c:out value="${vo.name}" /></option>	
									</c:forEach>
								</select>
							</td>
							<td class="title">清除告警电话通知录音：</td>
							<td>
								<select id="ipt_clearAlarmVoiceID" class="easyui-combobox" data-options="panelWidth:180,editable:false">
									<option value="-1">请选择...</option>
								    <c:forEach items="${voiceList}" var="vo">
									<option value="<c:out value='${vo.id}' />" ><c:out value="${vo.name}" /></option>	
									</c:forEach>
								</select>
							</td>
						</tr>
						
						<tr id="voice2">
							<td class="title">删除告警电话通知录音：</td>
							<td>
								<select id="ipt_deleteAlarmVoiceID" class="easyui-combobox" data-options="panelWidth:180,editable:false">
									<option value="-1">请选择...</option>
								    <c:forEach items="${voiceList}" var="vo">
									<option value="<c:out value='${vo.id}' />" ><c:out value="${vo.name}" /></option>	
									</c:forEach>
								</select>
							</td>
						</tr>
						
						<tr>
							<td class="title">规则描述：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_descr"  validator="{'length':'0-128'}"></textarea></td>
						</tr>
					</table>
			</div>

			<div id="divNotifyToUsers" title="通知用户" data-options="closable:false" style="overflow: auto;">
			  <div class ="btntd" valign="middle" align="left">
				<h2>通知用户
				 <a class="btntd" onclick="javascript:getNow();">临时添加</a>
				 <a class="btntd" onclick="javascript:getFromUsers();" style="width: 103px;">从用户列表中添加</a>
				 <a class="btntd" onclick="javascript:getDutier();"  style="width: 90px;">添加值班负责人</a>
				</h2>
			  </div>
			  <div class="datas">
				<table id="tblNotifyToUser"></table>
			  </div>
			</div>

			<div title="通知过滤" data-options="closable:false" style="overflow: auto;">
				<div class="datas">
					<table id="viewNotifyFilter"></table>
				</div>
			</div>
		</div>
		
		<div class="conditionsBtn">
			<input type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	
		
		<div id="divAddUserNow" class="easyui-window" minimizable="false"
				closed="true" maximizable="false" collapsible="false" modal="true"
				title="临时添加用户" style="width: 600px;">
				<input id="ipt_organizationID" type="hidden" />
				<table id="tblUserNow" cellspacing="10" class="formtable1">
					<tr>
						<td class="title">手机号码：</td>
						<td>
							<input id="ipt_mobileNow" class="input" validator="{'default':'phoneNum'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">邮箱：</td>
						<td>
							<input id="ipt_EmailNow" class="input" validator="{'default':'email'}"/>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnAddNow" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnColseNow" class="fltrt">取消</a>
				</div>
			</div>
			<div id='event_select_dlg' class='event_select_dlg'></div>
		    <div id="divAddUser" class="easyui-window" minimizable="false" closed="true" maximizable="false" collapsible="false" modal="true" title="用户信息">
				<div class="winbox">
				<div id="tblSearchUser" class="conditions" >
				  <table>
					<tr>
						<td>
							<b>用户名：</b>
							<input type="text" class="inputs" id="txtFilterUserName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadUserTable();"
								class="fltrt">查询</a>
							<a href="javascript:void(0);"
								onclick="resetFormFilter();" class="fltrt">重置</a>
						</td>
					</tr>
				  </table>
				</div>
				<div class="conditionsBtn">
					<input type="button" id=btnAdduser value="确定" />
					<input type="button" id="btnCloseUser" value="取消" />
				</div>
				
				<div class="datas">
					<table id="tblSysUser"></table>
				</div>
			</div>
		  </div>
	</body>
</html>
