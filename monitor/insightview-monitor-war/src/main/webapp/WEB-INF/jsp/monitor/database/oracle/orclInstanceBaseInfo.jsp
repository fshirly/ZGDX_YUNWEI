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
									<b class="title"> 实例名称： </b>
									<label id="lbl_moname" class="input">${orcl.instancename}${orcl.moalias}</label>
								</td>
								<td>
									<b class="title"> 访问端口： </b>
									<label id="lbl_deviceip" class="input">${orcl.port}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 访问服务IP： </b>
									<label id="lbl_rescategoryname" class="input">${orcl.ip}</label>
								</td>
								<td>
									<b class="title"> 所属主机： </b>
									<label id="lbl_os" class="input">${orcl.deviceip}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 所属数据资源： </b>
									<label id="lbl_osversion" class="input">${orcl.dbname}</label>
								</td>
								<td>
									<b class="title"> 启动时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${orcl.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 实例总空间： </b>
									<label  class="input">${orcl.totalsize}</label>
								</td>
								<td>
									<b class="title"> 空闲空间： </b>
									<label  class="input">${orcl.freesize}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 安装路径： </b>
									<label id="lbl_hmMemorysize" class="input">${orcl.installpath}</label>
								</td>
								<td>
									<b class="title"> 告警级别： </b>
									<img alt="0" src="${pageContext.request.contextPath}/style/images/levelIcon/${orcl.levelicon}" border="0"/>${orcl.alarmLevelName}
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> 可用/持续时间： </b>
										<img title="${orcl.operaTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${orcl.operstatus}" border="0"/>${orcl.durationTime}
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
