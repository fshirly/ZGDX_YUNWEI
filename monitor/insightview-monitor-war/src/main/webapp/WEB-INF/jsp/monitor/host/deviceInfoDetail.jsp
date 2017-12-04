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
					<div class="easyui-panel" >
						<table id="tblServDetail" class="tableinfo2">
							<tr>
								<td>
									<b class="title"> ip地址： </b>
									<label id="lbl_deviceip" class="input">${mo.deviceip}</label>
								</td>
								<td>
									<b class="title"> 厂商： </b>
									<label id="lbl_resmanufacturername" class="input">${mo.resmanufacturername}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 设备类型： </b>
									<label id="lbl_restypename" class="input">${mo.restypename}</label>
								</td>
								<td>
									<b class="title"> 设备名称： </b>
									<label id="lbl_moname" class="input">${mo.moname}${mo.moalias}</label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> 设备型号： </b>
									<label id="lbl_rescategoryname" class="input">${mo.rescategoryname}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 最近更新时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${mo.lastupdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
								<td>
									<b class="title"> 可用/持续时间： </b>
									<img title="${mo.operaTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${mo.operstatus}" border="0"/>${mo.durationTime}
								</td>
							</tr>
						</table>
					</div>
				</td>
				
			</tr>

		</table>



	</body>
</html>
