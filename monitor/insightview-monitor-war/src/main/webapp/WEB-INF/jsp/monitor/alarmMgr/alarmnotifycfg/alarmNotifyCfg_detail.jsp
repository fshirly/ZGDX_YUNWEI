<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
	<style>
	      .label{
	      	width:500px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	      .label2{
	        width:180px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	      
	    </style>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmnotifycfg/alarmNotifyCfg_detail.js"></script>
		<%
			String policyID =(String)request.getAttribute("policyID");
			String smsFlag =(String)request.getAttribute("smsFlag");
			String emailFlag =(String)request.getAttribute("emailFlag");
		 %>
		<div id="alarmNotifyTabs" class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" style="height: 400px;overflow: auto;">
		<input type="hidden" id="policyID" value="<%= policyID%>"/>
		<input type="hidden" id="smsFlag" value="<%= smsFlag%>"/>
		<input type="hidden" id="emailFlag" value="<%= emailFlag%>"/>
		
			<div title="通知策略定义" data-options="closable:false">
				<table id="tblAlarmNotifyCfgView" class="tableinfo">
						<tr>
							<td>
								<div style='float:left;'><b class="title">规则名称：</b></div>
								<div class="label2"><label id="lbl_policyName"></label></div>
							</td>
							<td>
								<div style='float:left;'><b class="title">重复告警条件（次）：</b></div>
								<div class="label2"><label id="lbl_alarmOnCount"></label></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style='float:left;'><b class="title">最大发送次数：</b></div>
								<div  class="label">
								  <label id="lbl_maxTimes"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style='float:left;'><b class="title">是否短信：</b></div>
								<div class="label"><label id="lbl_isSms" ></label></div>
							</td>
						</tr>
						<tr>
							<td id="sms1" colspan="2">
								<div style='float:left;'><b class="title">短信内容模板：</b></div>
								<div class="label">
								  <label id="lbl_smsAlarmContent" >
								</label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="sms2" colspan="2">
								<div style='float:left;'><b class="title">清除告警短信内容模板：</b></div>
								<div class="label">
								  <label id="lbl_smsClearAlarmContent" >
								</label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="sms3" colspan="2">
								<div style='float:left;'><b class="title">删除告警短信内容模板：</b></div>
								<div class="label">
								  <label id="lbl_smsDeleteAlarmContent" >
								</label>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style='float:left;'><b class="title">是否邮件：</b></div>
								<div class="label"><label id="lbl_isEmail"></label></div>
							</td>
						</tr>
	
						<tr>
							<td id="email1" colspan="2">
								<div style='float:left;'><b class="title">邮件内容模板：</b></div>
								<div style="float:right;width:500px;">
								  <label id="lbl_emailAlarmContent"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="email2" colspan="2">
								<div style='float:left;'><b class="title">清除告警邮件内容模板：</b></div>
								<div style="float:right;width:500px;">
								  <label id="lbl_emailClearAlarmContent"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="email3" colspan="2">
								<div style='float:left;'><b class="title">删除告警邮件内容模板：</b></div>
								<div style="float:right;width:500px;">
								  <label id="lbl_emailDeleteAlarmContent"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div style='float:left;'><b class="title">是否电话语音：</b></div>
								<div class="label2"><label id="lbl_isPhone"></label></div>
							</td>
							<td id="voiceType">
								<div style='float:left;'><b class="title">语音通知类型：</b></div>
								<div class="label2"><label id="lbl_voiceMessageType"></label></div>
							</td>
						</tr>
						<tr>
							<td id="phone1" colspan="2">
								<div style='float:left;'><b class="title">电话语音模板：</b></div>
								<div  class="label">
								  <label id="lbl_phoneAlarmContent"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="phone2" colspan="2">
								<div style='float:left;'><b class="title">清除告警电话语音模板：</b></div>
								<div  class="label">
								  <label id="lbl_phoneClearAlarmContent"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="phone3" colspan="2">
								<div style='float:left;'><b class="title">删除告警电话语音模板：</b></div>
								<div  class="label">
								  <label id="lbl_phoneDeleteAlarmContent"></label>
								</div>
							</td>
						</tr>
						<tr id="voice1">
							<td>
								<div style='float:left;'><b class="title">告警电话通知录音：</b></div>
								<div class="label2"><label id="lbl_alarmVoiceName"></label></div>
							</td>
							<td id="voiceType">
								<div style='float:left;'><b class="title">清除告警电话通知录音：</b></div>
								<div class="label2"><label id="lbl_clearAlarmVoiceName"></label></div>
							</td>
						</tr>
						<tr  id="voice2">
							<td colspan="2">
								<div style='float:left;'><b class="title">删除告警电话通知录音：</b></div>
								<div class="label">
								  <label id="lbl_deleteAlarmVoiceName"></label>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style='float:left;'><b class="title">规则描述：</b></div>
								<div class="label"><label id="lbl_descr" ></label></div>
							</td>
						</tr>
					</table>
			</div>

			<div id="divNotifyToUsers" title="通知用户" data-options="closable:false" style="overflow: hidden;">
				<h2>通知用户</h2>
				<div class="datas">
					<table id="viewNotifyToUsers"></table>
				</div>
			</div>

			<div title="通知过滤" data-options="closable:false" style="overflow: auto;">
				 <h2>过滤条件清单</h2>
				<div class="datas">
					<table id="viewNotifyFilter"></table>
				</div>
			</div>
		</div>
		
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</body>
</html>
