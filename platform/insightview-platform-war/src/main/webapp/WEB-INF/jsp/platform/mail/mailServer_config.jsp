<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/mail/sysmailserver.js"></script>
	</head>
	<body>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>

			<div class="easyui-window" id="divMailInfo"
				 minimizable="false"  resizable="false" maximizable="false" closed="false" closable="false"  
		 collapsible="false" modal="true" title="邮件服务器配置"
				style="width: 700px;">
				<input id="ipt_id" type="hidden" />
				<table id="tblMailServerInfo" class="formtable">
					<tr>
						<td class="title">
							邮件服务器地址：
						</td>
						<td>
							<input id="ipt_mailServer" />
							<b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							端口：
						</td>
						<td>
							<input id="ipt_port" maxlength="11" value='25' />
							<b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							超时时间：
						</td>
						<td>
							<input id="ipt_timeout" maxlength="11" value='10' />
							&nbsp;&nbsp;秒
						</td>
					</tr>
				
					<tr>
						<td class="title">
							发送者邮箱：
						</td>
						<td>
							<input id="ipt_senderAccount" /><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							<input type="checkbox" id="ipt_isAuth"
								onclick=
	isOrnoCheck();
checked="checked" value='1' />
							是否需要认证
						</td>
					</tr>
					<tr id="name">
						<td class="title">
							用户名：
						</td>
						<td>
							<input id="ipt_userName" />
						</td>
					</tr>
					
					<tr id="pwd">
						<td class="title">
							密码：
						</td>
						<td>
							<input id="ipt_password" type="password" />
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<a  onclick="doAdd();">确定</a>
							<!--  <a id="btnUpdate"  onclick="cancle();">取消</a>-->
					
				</div>
			</div>
		</div>
	</body>
</html>