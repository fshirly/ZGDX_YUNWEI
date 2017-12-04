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
								<td>
									<b class="title"> 服务名称： </b>
									<label class="input">${tm.serverName}${tm.moalias}</label>
								</td>
								
							</tr>
							<tr>
							
								<td>
									<b class="title"> 可用/持续时间： </b>
									<img title="${tm.operaTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${tm.operStatusName}" border="0"/>${tm.durationTime}
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 告警级别 ：</b>
									<img alt="0" src="${pageContext.request.contextPath}/style/images/levelIcon/${tm.levelicon}" border="0"/>${tm.alarmLevelName}
								</td>
								
							</tr>
							<tr>
								<td>
									<b class="title"> 应用服务器类型： </b>
									<label  class="input">${tm.jmxType}</label>
								</td>
								
							</tr>
							<tr>
							
								<td>
									<b class="title"> JMX版本： </b>
									<label  class="input">${tm.jmxVersion}</label>
								</td>
							</tr>
							<tr>
							
								<td>
									<b class="title"> JVM基本信息： </b>
									<label  class="input">${tm.jvmName}&nbsp; ${tm.jvmVersion}&nbsp; (${tm.jvmVendor})</label>
								</td>
							</tr>
							<!--<tr>
							
								<td>
									<b class="title"> Java厂商/版本： </b>
									<label  class="input">${tm.javaVendor}/${tm.javaVersion}</label>
								</td>
							</tr>
							--><tr>
								<td>
									<b class="title"> 主机名： </b>
									<label  class="input"><a style="cursor: pointer;" onclick="javascript:toShowView('${tm.moId}','${tm.deviceIP}');">${tm.moName}</a></label>
								</td>
							</tr>
							<tr>
							
								<td>
									<b class="title"> 主机操作系统： </b>
									<label  class="input">${tm.oSName}&nbsp;${tm.oSVersion}</label>
								</td>
							</tr>
						
							
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
