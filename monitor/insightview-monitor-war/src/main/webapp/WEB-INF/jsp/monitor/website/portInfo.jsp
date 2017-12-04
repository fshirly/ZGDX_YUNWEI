<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" style='height:300px;overflow-y:hidden;'>
						<table id="tblSitePortDetail" class="tableinfo2">
							<tr>
								<td>
									<b class="title"> 可用/持续时间： </b>
									<img title="${sitePort.availableTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${sitePort.availablePng}" border="0"/>${sitePort.durationTime}
								</td>
								
							</tr>
							<tr>
							
								<td>
									<b class="title"> 站点名称： </b>
									<label class="input">${sitePort.siteName}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 站点地址 ：</b>
									<label class="input">${sitePort.ipAddr}</label>
								</td>
								
							</tr>
							<tr>
								<td>
									<b class="title">端口： </b>
									<label class="input">${sitePort.port}</label>
								</td>
								
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
