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
									<b class="title"> 光闸名称： </b>
									<label id="lbl_moname" class="input">${mo.mOName}</label>
								</td>
								<td>
									<b class="title"> 光闸IP： </b>
									<label id="lbl_deviceip" class="input">${mo.deviceIP}</label>
								</td>
							</tr>
							<!--<tr>
								
								<td>
									<b class="title"> 硬件版本： </b>
									<label id="lbl_os" class="input">${mo.hardwareVer}</label>
								</td>
								<td>
									<b class="title"> 软件版本： </b>
									<label id="lbl_osversion" class="input">${mo.softwareVer}</label>
								</td>
							</tr>-->
							<tr>
								<td>
									<b class="title"> CPU个数： </b>
									<label id="lbl_cpucount" class="input">${mo.cpucount}</label>
								</td>
								<td>
									<b class="title"> 接口个数： </b>
									<label id="lbl_ifcount" class="input">${mo.ifcount}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 内存大小： </b>
									<label id="lbl_hmemorysize" class="input">${mo.memorySize}</label>
								</td>
								<td>
									<b class="title"> 虚拟内存大小： </b>
									<label id="lbl_hmMemorysize" class="input">${mo.vMemorySize}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 硬盘大小： </b>
									<label id="lbl_sumdisksize" class="input">${mo.diskSize}</label>
								</td>
								<td>
									<b class="title">最近更新时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${mo.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
