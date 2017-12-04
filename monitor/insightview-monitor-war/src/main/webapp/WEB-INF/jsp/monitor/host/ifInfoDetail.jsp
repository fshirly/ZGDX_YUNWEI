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
					<div class="easyui-panel" style='overflow-x:hidden;'>
						<table id="tblServDetail" class="tableinfo2">
							<tr>

								<td>
									<b class="title"> 接口名称： </b>
									<label id="lbl_ifname" class="input">${mo.ifname}</label>
								</td>
								<td>
									<b class="title"> 接口描述： </b>
									<label id="lbl_ifdescr" class="input"  title="${mo.ifdescr}">${mo.descStr}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 别名： </b>
									<label id="lbl_ifalias" class="input">${mo.ifalias}</label>
								</td>
								<td>
									<b class="title"> 设备IP： </b>
									<label id="lbl_deviceip" class="input">${mo.deviceip}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 设备名称： </b>
									<label id="lbl_moname" class="input">${mo.moname}</label>
								</td>
								<td>
									<b class="title"> 接口号： </b>
									<label id="lbl_instance" class="input">${mo.instance}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 接口类型： </b>
									<label id="lbl_iftype" class="input">${mo.iftype}</label>
								</td>
								<td>
									<b class="title"> 管理状态： </b>
									<label id="lbl_ifadminstatus" class="input">${mo.ifadminstatus}</label>
								</td>
							</tr>
								<tr>
								<td>
									<b class="title"> 可用状态： </b>
									<img title="${mo.operaTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${mo.operstatus}" border="0"/>
								</td>
								<td>
									<b class="title"> 最近更新时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${mo.lastupdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr>
						</table>
					</div>
				</td>
				
			</tr>

		</table>



	</body>
</html>
