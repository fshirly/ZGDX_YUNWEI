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
									<b class="title"> 名称： </b>
									<label class="input">${weblogic.serverName}${weblogic.moalias}</label>
								</td>
								<td>
									<b class="title"> 服务版本： </b>
									<label  class="input">${weblogic.jmxVersion}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 运行主机： </b>
									<label  class="input"><a style="cursor: pointer;" onclick="javascript:toShowView('${weblogic.moId}','${weblogic.deviceIP}');">${weblogic.moName}</a></label>
									
								</td>
								<td>
									<b class="title"> 端口号： </b>
									<label  class="input">${weblogic.port}</label>
								</td>
								
							</tr>
							<tr>
								<td>
									<b class="title"> 启动时间： </b>
									<label  class="input"><fmt:formatDate value="${weblogic.startupTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label></label>
								</td>
								<td>
									<b class="title"> Java厂商/版本： </b>
									<label  class="input">${weblogic.javaVendor}/${weblogic.javaVersion}</label>
								</td>
							</tr>
							<tr>
									<td>
									<b class="title"> JVM厂商： </b>
									<label  class="input">${weblogic.jvmVendor}</label>
								</td>
								<td>
									<b class="title"> OS信息： </b>
									<label  class="input">${weblogic.oSName}/${weblogic.oSVersion}</label>
								</td>
							</tr>
							
								<tr>
								<td colspan="2">
									<b class="title"> 堆内存最大值： </b>
									<label  class="input">${weblogic.heapSizeMax}</label>
								</td>
							</tr>
							
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
