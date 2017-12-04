<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
	
		<table style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" style='height:300px;overflow-y:hidden;'>
						<table id="tblInstanceDetail" class="tableinfo2">
							<tr>
								<td colspan="2">
									<b class="title"> 名称： </b>
									<label class="input">${was.serverName}${was.moalias}</label>
								</td>
								<!--<td>
									<b class="title"> 启动时间： </b>
									<label  class="input"><fmt:formatDate value="${was.startupTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label></label>
								</td>
							--></tr>
							<tr>
								<td>
									<b class="title"> 服务版本： </b>
									<label  class="input">${was.jmxVersion}</label>
								</td>
								<!--<td>
									<b class="title"> OS信息： </b>
									<label  class="input">${was.oSName}/${was.oSVersion}</label>
								</td>-->
								<td>
									<b class="title"> 发现时间： </b>
										<label  class="input"><fmt:formatDate value="${was.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label></label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> JMX类型：</b>
									<label  class="input">${was.jmxType}</label>
								</td>
								<td>
									<b class="title"> 协议类型： </b>
									<label  class="input">${was.portType}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 端口号： </b>
									<label  class="input">${was.port}</label>
								</td>
								<td>
									<b class="title"> 最近更新时间： </b>
									<label  class="input"><fmt:formatDate value="${was.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label></label>
								</td>
							</tr>
							<tr>
							<td>
									<b class="title"> 运行主机： </b>
									<label  class="input"><a style="cursor: pointer;" onclick="javascript:toShowView('${was.moId}','${was.deviceIP}');">${was.moName}</a></label>
									
								</td>
									<td>
									<b class="title"> IP地址： </b>
									<label  class="input">${was.ip}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> JTA名称： </b>
									<label  class="input">${was.jtaName}</label>
								</td>
								<td>
									<b class="title"> JTA状态： </b>
									<label  class="input">${was.jtaStatus}</label>
								</td>
							</tr>
								<tr>
								<td>
									<b class="title"> 用户名： </b>
									<label  class="input">${was.userName}</label>
								</td>
								<td>
									<b class="title"> 密码： </b>
									<label  class="input">${was.passWord}</label>
								</td>
							</tr>
								<tr>
							
								<td colspan="2">
									<b class="title"> JMS信息： </b>
									<label  class="input">${was.jmsName}</label>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
