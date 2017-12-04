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
					<div class="easyui-panel" style='height:290px;overflow-y:hidden;'>
						<table id="tblServDetail" class="tableinfo2">
							<tr>
								<td>
									<b class="title"> 主机名称： </b>
									<label id="lbl_moname" class="input">${mo.moname}${mo.moalias}</label>
								</td>
								<td>
									<b class="title"> 主机IP： </b>
									<label id="lbl_deviceip" class="input">${mo.deviceip}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 厂商： </b>
									<label id="lbl_resmanufacturername" class="input">${mo.resmanufacturername}</label>
								</td>
								<td>
									<b class="title"> 操作系统： </b>
									<label id="lbl_os" class="input">${mo.os}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 系统版本： </b>
									<label id="lbl_osversion" class="input">${mo.osversion}</label>
								</td>
								<td>
									<b class="title"> CPU频率： </b>
									<label id="lbl_cpucores" class="input">${mo.cpucores}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 物理CPU数： </b>
									<label id="lbl_phyprocessors" class="input">${mo.phyprocessors}</label>
								</td>
								<td>
									<b class="title"> 逻辑CPU数： </b>
									<label id="lbl_logicalprocessors" class="input">${mo.logicalprocessors}</label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> CPU型号： </b>
									<label id="lbl_processortype" class="input">${mo.processortype}</label>
								</td>
								<!--<td>
									<b class="title"> CPU使用率： </b>
									<label id="lbl_cpuusage" class="input">${mo.cpuusage}</label>
								</td>
							--></tr>
							<tr>
								<td>
									<b class="title"> 内存使用量： </b>
									<label id="lbl_memoryusage" class="input">${mo.memoryusage}</label>
								</td>
								<td>
									<b class="title"> 连接状态： </b>
									<label id="lbl_connectionstatus" class="input">${mo.connectionstatus}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 网络适配器数： </b>
									<label id="lbl_nics" class="input">${mo.nics}</label>
								</td>
								<td>
									<b class="title"> 主机总线适配器数： </b>
									<label id="lbl_hbas" class="input">${mo.hbas}</label>
								</td>
							</tr>
							<%-- <tr>
								<td>
									<b class="title"> 最近更新时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${mo.lastupdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr> --%>
						</table>
					</div>
				</td>
				
			</tr>

		</table>



	</body>
</html>
