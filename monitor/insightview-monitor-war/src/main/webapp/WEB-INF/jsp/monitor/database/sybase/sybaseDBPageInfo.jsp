<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
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
							<td><b class="title"> TxLog数： </b> <label class="input">${db.txLog}</label>
							</td>
							<td><b class="title"> 接收包数： </b> <label class="input">${db.packReceive}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 发送包数： </b> <label class="input">${db.packSent}</label>
							</td>
							<td><b class="title"> 错误包数： </b> <label class="input">${db.packetErrors}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 磁盘读次数： </b> <label class="input">${db.totalDiskRead}</label>
							</td>
							<td><b class="title"> 磁盘写次数： </b> <label class="input">${db.totalDiskWrite}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 磁盘读写错误次数： </b> <label class="input">${db.totalDiskErrors}</label>
							</td>
							<td><b class="title"> cpu处理耗时： </b> <label class="input">${db.cpuBusy}</label>
							</td>
						</tr>
							<tr>
							<td><b class="title"> cpu空闲时间： </b> <label class="input">${db.cpuIdle}</label>
							</td>
							<td><b class="title"> 
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>

</body>

</html>
