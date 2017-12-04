<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>KSCC运维</title>

<link rel="shortcut icon" href="${sessionScope.filePath}${ sessionScope.LogoIcon}" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/css/login.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login/login.js"></script>
<style>
	body{
		background: url("style/images/kssc-img/loginBg.png");
		background-size:cover ;
	}
	.logo{
		background:none;
		width: 0 !important;
	}
	.login{
		text-align: center;
		background:none;
		width: 280px;
		margin: 0 auto;
	}
	#main{
		top:45% !important;
	}
	.login .content{
		width: 280px;
		float: none;
		margin: 0 auto;
	}
	.login .content .inputtext {
		width: 280px;
		height: 34px;
		line-height: 34px;
		background: none;
		margin-bottom: 15px;
	}
	.inputtext input{
		width:280px !important;
		box-sizing: border-box;
		height: 34px;
		border-radius: 15px;
		padding-left: 50px;
		border: 1px solid #ccc  !important;
		-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
		-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
		transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	}
    #iptUserAccount{
		background: url("style/images/kssc-img/username.png") 5% center no-repeat #7d91c2  !important;
        color:#fff !important;
	}
	#iptUserPassword{
		background: url("style/images/kssc-img/password.png") 5% center no-repeat #7d91c2  !important;
        color:#fff !important;
	}
	.login .btnsubmit{
		float:none;
		margin: 0;
	}
	.login .content .tips {
		height: 20px;
		margin: 20px 0 5px 0 !important;
	}
	.login .btnsubmit input {
		background: #0b5073 url(../images/btn_arrow.png) no-repeat;
		height: 34px;
		width: 280px;
		border: 0 none;
		border-radius: 15px;
		padding-left: 0px!important;
		background: #00aeff!important;
		border: 1px solid #008ed0!important;
		color: #ffffff;
		letter-spacing: 10px;
		font-size: 14px;
	}
</style>

</head>
<body>

<div id="main"> 
</div>

<script type="text/javascript">

<% 

String flag  = (String)request.getSession(true).getAttribute("SPRING_SECURITY_LAST_EX_MSG");

if(flag == null) {
	flag = "";
}

if(!flag.trim().equals("")) {
	request.getSession().setAttribute("SPRING_SECURITY_LAST_EX_MSG", "");
	request.getSession().setAttribute("SPRING_SECURITY_LAST_EX_MSG_2", flag);
 %>
	top.location.href = getRootName() + '/login.jsp';
<% } else {
	flag = (String)request.getSession(true).getAttribute("SPRING_SECURITY_LAST_EX_MSG_2");
	request.getSession().setAttribute("SPRING_SECURITY_LAST_EX_MSG_2", "");
	if(flag == null) {
		flag = "";
	}
%>
	document.all.main.innerHTML=
	'<table>                                                                                                                                                   '+
	'<tbody>                                                                                                                                                   '+
	'	<tr>                                                                                                                                                   '+
	<%--'		<td class="logo" align="center">                                                                                                                   '+--%>
	<%--'			<div><img src="${sessionScope.filePath}${ sessionScope.LoginIcon}" onerror="this.src=\'${pageContext.request.contextPath}/style/images/logo.png\'"/></div>                                                              '+--%>
	<%--'		</td>                                                                                                                                              '+--%>
	'		<td class="login">  ' +
	'			<div><img src="${pageContext.request.contextPath}/style/images/kssc-img/logoWhite.png"> </div>                                                                                                                           '+
	'			<form id="loginForm" name="loginForm" action="fable_security" method="post">                                                                   '+
	'				<div class="content">                                                                                                                      '+
	'					<div class="tips"><%=flag%></div>                                                                                                      '+
	'					<div class="inputtext"><input type="text" value="" id="iptUserAccount" name="username"/></div>                     '+
	'					<div class="inputtext"><input type="password" value="" id="iptUserPassword" name="password"/></div>          '+
	'					<!--<div>                                                                                                                                  '+
	'						<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" />                                    '+
	'						<label for="_spring_security_remember_me">记住密码</label>                                                                         '+
	'						<input class="btnreset" type="reset" onclick="doReset();" onmouseover="doChange(this);" onmouseout="doBack(this);" value="重置" />'+
	'					</div>-->                                                                                                                                 '+
	'				</div>                                                                                                                                     '+
	'				<div class="btnsubmit">                                                                                                                    '+
	'					<input type="submit" onmouseover="doChange(this);" onmouseout="doBack(this);" value="登录" />                                             '+
	'				</div>                                                                                                                                     '+
	'			</form>                                                                                                                                        '+
	'		</td>                                                                                                                                              '+
	'	</tr>                                                                                                                                                  '+
	<%--'	<tr>                                                                                                                                                   '+--%>
	<%--'		&lt;%&ndash; <td colspan="2" id="footer">版权所有&nbsp;&copy;&nbsp;&nbsp;${ sessionScope.CopyRight}</td> 2013-2014&nbsp;&nbsp;&nbsp;江苏飞搏软件技术有限公司&ndash;%&gt;                                              '+--%>
	<%--'		<td colspan="2" id="footer">系统适用浏览器下载：<a title="支持系统所有功能,点击下载" id="downloadForWin7">google浏览器(win7)</a>&nbsp;&nbsp;<a title="支持系统所有功能,点击下载" id="downloadForXp">google浏览器(xp)</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版权所有&nbsp;&copy;&nbsp;&nbsp;<c:if test="${ sessionScope.CopyRight eq '' }">2013-2014&nbsp;&nbsp;&nbsp;江苏飞搏软件技术有限公司</c:if>${ sessionScope.CopyRight}&nbsp;&nbsp;${sessionScope.Version}</td>                                 '+--%>
	<%--'	</tr>                                                                                                                                                  '+--%>
	'</tbody>                                                                                                                                                  '+
	'</table>                                                                                                                                                  ';
<% } %>
</script>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</html>