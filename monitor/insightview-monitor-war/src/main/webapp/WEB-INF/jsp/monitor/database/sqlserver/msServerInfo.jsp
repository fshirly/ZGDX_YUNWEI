<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblInstanceDetail" class="tableinfo2">
							<tr>
								<td>
									<b class="title"> 服务名称： </b>
									<label id="lbl_moname" class="input">${ms.serverName}${ms.moalias}</label>
								</td>
							</tr>
							<tr>
							<td>
									<b class="title"> 访问端口： </b>
									<label id="lbl_deviceip" class="input">${ms.port}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 访问服务IP： </b>
									<label id="lbl_rescategoryname" class="input">${ms.ip}</label>
								</td>
								
							</tr>
							<tr>
								
								<td>
									<b class="title"> 启动时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${ms.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr>
							<tr>
							<td>
									<b class="title"> 告警级别： </b>
									<img alt="0" src="${pageContext.request.contextPath}/style/images/levelIcon/${ms.levelicon}" border="0"/>${ms.alarmLevelName}
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 可用/持续时间： </b>
										<img title="${ms.operaTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${ms.operstatus}" border="0"/>${ms.durationTime}
								</td>
							</tr>
							<tr>
								
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
