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
									<b class="title"> 主机名称： </b>
									<label id="lbl_moname" class="input">${mo.moname}${mo.moalias}</label>
								</td>
								<td>
									<b class="title"> 主机IP： </b>
									<label id="lbl_deviceip" class="input">${mo.deviceip}</label>
								</td>
							</tr>
							<tr>
								<!-- <td>
									<b class="title"> 主机型号： </b>
									<label id="lbl_rescategoryname" class="input">${mo.rescategoryname}</label>
								</td> -->
								<td>
									<b class="title"> 操作系统： </b>
									<label id="lbl_os" class="input">${mo.os}</label>
								</td>
								<td>
									<b class="title"> 系统版本： </b>
									<label id="lbl_osversion" class="input">${mo.osversion}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> CPU个数： </b>
									<label id="lbl_cpucount" class="input">${mo.cpucount}</label>
								</td>
								<td>
									<b class="title"> 接口个数： </b>
									<label id="lbl_ifcount" class="input"><script>document.write(parseInt(${mo.ifcount}));</script></label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 内存大小： </b>
									<label id="lbl_hmemorysize" class="input">${mo.hmemorysize}</label>
								</td>
								<td>
									<b class="title"> 虚拟内存大小： </b>
									<label id="lbl_hmMemorysize" class="input">${mo.hmVirMemorysize}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 硬盘大小： </b>
									<label id="lbl_sumdisksize" class="input">${mo.sumdisksize}</label>
								</td>
								<td>
									<b class="title"> 最近更新时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${mo.lastupdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
</label>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
