<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/notifypolicycfg/notifyPolicyCfg_edit.js"></script>
		<div id="alarmNotifyTabs" class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" style="height: 400px;overflow: auto;">
			<input type="hidden" id="editFlag" value="${editFlag}"/>
			<input type="hidden" id="policyId" value="${policyId}"/>
			<div title="通知策略定义" data-options="closable:false" id="policyDefine">
					<table id="tblNotifyCfgAdd" class="formtable" style="width: 660px;">
						<tr>
							<td class="title">规则名称：</td>
							<td><input id="ipt_policyName" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
							</td>
							<td class="title">策略类型：</td>
							<td>
								<input class="easyui-combobox" id="ipt_policyType" validator="{'default':'*','type':'combobox'}"/>
								<b>*</b>
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
								<input type="radio" name="isSms" onclick="javascript:editSms()" value="1" >&nbsp;是
								&nbsp;
								<input type="radio" name="isSms" onclick="javascript:editSms()" value="0" checked>&nbsp;否
							</td>
						</tr>
					</table>
						<div class="datas">
							<table id="tblSmsList">
							</table>
						</div>
					
					<table style="margin: 10px 0px 10px 69px;">
						<tr>
							<td class="title">是否邮件：</td>
							<td colspan="3"><input id="ipt_isEmail"  type="hidden"></input>
								<input type="radio" name="isEmail" onclick="javascript:editEmail()" value="1">&nbsp;是
								&nbsp;
								<input type="radio" name="isEmail" onclick="javascript:editEmail()" value="0"  checked/>&nbsp;否
							</td>
						</tr>
					</table>
						
						<div class="datas" >
							<table id="tblEmailList">
							</table>
						</div>
					
					<table style="margin: 10px 0px 10px 44px;">
						<tr>
							<td class="title">是否电话语音：</td>
							<td><input id="ipt_isPhone"  type="hidden"></input>
								<input type="radio" name="isPhone" onclick="javascript:editPhone()" value="1">&nbsp;是
								&nbsp;
								<input type="radio" name="isPhone" onclick="javascript:editPhone()" value="0" checked/>&nbsp;否
							</td>
						</tr>
					</table>
					<div class="datas">
						<table id="tblPhoneList">
						</table>
					</div>
					
					<table style="margin: 10px 0px 10px 77px;">	
						<tr>
							<td class="title">规则描述：</td>
							<td colspan="3"><textarea rows="3" style="width: 497px;" id="ipt_descr"  validator="{'length':'0-128'}"></textarea></td>
						</tr>
					</table>
			</div>

			<div id="divNotifyToUsers" title="通知用户" data-options="closable:false" style="overflow: auto;">
			  <div class ="btntd" valign="middle" align="left">
				<h2>通知用户
				 <a class="btntd" onclick="javascript:addCasualUser();">临时添加</a>
				 <a class="btntd" onclick="javascript:addFromUsers();" style="width: 103px;">从用户列表中添加</a>
				 <a class="btntd" onclick="javascript:addDutier();"  style="width: 90px;">添加值班人</a>
				</h2>
			  </div>
			  <div class="datas">
				<table id="tblNotifyToUser"></table>
			  </div>
			</div>
		</div>
		
		<div class="conditionsBtn">
			<input type="button" id="btnSave" value="确定" onclick="javascript:toEditPolicyCfg();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
		<!-- 用户列表 -->
        <div id="divAddUser" class="easyui-window" minimizable="false" closed="true" maximizable="false" collapsible="false" modal="true" title="用户信息" style="width:700px;">
            <div class="winbox">
                <div id="tblSearchUser" class="conditions">
                    <table>
                        <tr>
                            <td>
                                <b>用户名：</b>
                                <input type="text" class="inputs" id="txtFilterUserName" />
                            </td>
                            <td class="btntd">
                                <a href="javascript:void(0);" onclick="reloadUserTable();" class="fltrt">查询</a>
                                <a href="javascript:void(0);" onclick="resetFormFilter();" class="fltrt">重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="conditionsBtn">
                    <input type="button" id=btnAdduser value="确定" /><input type="button" id="btnCloseUser" value="取消" />
                </div>
                <div class="datas">
                    <table id="tblSysUser">
                    </table>
                </div>
            </div>
        </div>
		
		<!-- 临时添加 -->
        <div id="divAddCasualUser" class="easyui-window" minimizable="false" closed="true" maximizable="false" collapsible="false" modal="true" title="临时添加用户" style="width: 600px;">
            <input id="ipt_organizationID" type="hidden" />
            <table id="tblCasualUser" class="formtable1">
                <tr>
                    <td class="title">
                        手机号码：
                    </td>
                    <td>
                        <input id="ipt_mobileNow" class="input" validator="{'default':'phoneNum'}"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        邮箱：
                    </td>
                    <td>
                        <input id="ipt_EmailNow" class="input" validator="{'default':'email'}"/>
                    </td>
                </tr>
            </table>
            <div class="conditionsBtn">
                <a href="javascript:void(0);" id="btnAddCasualUser" class="fltrt">确定</a>
                <a href="javascript:void(0);" id="btnColseCasualUser" class="fltrt">取消</a>
            </div>
        </div>
		
		<!-- 添加值班人 -->
        <div id="divAddDutier" class="easyui-window" minimizable="false" closed="true" maximizable="false" collapsible="false" modal="true" title="添加值班人" style="width: 600px;">
            <table id="tblDutier" class="formtable1">
                <tr>
                    <td class="title">
                        选择值班人：
                    </td>
                    <td>
                        <div id="divChooseDutier">
							<input id="leader" type="checkbox" name="leader" value="-101" />值班带班领导
							<input id="director" type="checkbox" name="director" value="-100" />值班负责人
							<input id="member" type="checkbox" name="member" value="-102" />值班成员
                        </div>
                    </td>
                </tr>
            </table>
			<div style="float:left;color:#686565;margin-top: 15px;margin-left: 145px;" id="tipInfo">
           	 &nbsp;注：值班成员不包括值班带班领导和值班负责人
        	</div>
            <div class="conditionsBtn">
                <a href="javascript:void(0);" id="btnAddDutier" class="fltrt">确定</a>
                <a href="javascript:void(0);" id="btnColseDutier" class="fltrt">取消</a>
            </div>
        </div>
	</body>
</html>
