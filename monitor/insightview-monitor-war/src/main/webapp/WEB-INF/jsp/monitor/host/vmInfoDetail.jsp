<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
		<table  style='width:100%;'>
		<input id="moid" value="${mo.moid}" type="hidden"/>
		<input id="phyDeviceIP" value="${mo.phyDeviceIP}" type="hidden"/>
			<tr>
				<td>
					<div class="easyui-panel" 
						>
						<table id="tblServDetail" class="tableinfo2">
							<tr>
								<td>
									<b class="title"> 虚拟机名称： </b>
									<label id="lbl_moname" class="input">${mo.moname}${mo.moalias}</label>
								</td>
								<td>
									<b class="title"> 虚拟机IP： </b>
									<label id="lbl_deviceip" class="input">${mo.deviceip}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 操作系统： </b>
									<label id="lbl_os" class="input">${mo.os}</label>
								</td>
								<td>
									<b class="title"> CPU频率： </b>
									<label id="lbl_cpuallocated" class="input">${mo.cpuallocated}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> CPU个数： </b>
									<label id="lbl_cpunumber" class="input">${mo.cpunumber}</label>
								</td>
								<td>
									<b class="title"> 内存大小： </b>
									<label id="lbl_memory" class="input">${mo.memory}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 已用内存： </b>
									<label id="lbl_memoryoverhead" class="input">${mo.memoryoverhead}</label>
								</td>
								<td>
									<b class="title"> 连接状态： </b>
									<label id="lbl_connectstatus" class="input">${mo.connectstatus}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 最近更新时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${mo.lastupdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
</label>
								</td>
								<td>
									<b class="title"> 宿主机IP： </b>
									<a id="look" name="look" style="cursor: pointer;">${mo.phyDeviceIP}</a>
								</td>
							</tr>
						</table>
					</div>
				</td>
				
			</tr>

		</table>
<script>
			 $("#look").bind('click', function(){
			 	toShowView($("#moid").val(),$("#phyDeviceIP").val());
				}
				);
		</script>
	</body>
</html>
