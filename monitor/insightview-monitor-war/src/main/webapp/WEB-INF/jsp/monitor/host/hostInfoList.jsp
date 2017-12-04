<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/hostInfoList.js"></script>

	</head>

	<body>
		<table>
		<tr>
			<td>
		<div class="easyui-panel" title="设备快照"
						style="width: 600px; height: 320px; padding: 0px; background: #fafafa; border: 0px;">
						<table id="tblSnapshot">

						</table>
					</div>
		</td>
		<td>
		<div class="easyui-panel" title="最近告警"
						style="width: 600px; height: 320px; padding: 0px; background: #fafafa; border: 0px;">
						<table id="tblAlarmActive">

						</table>
					</div>
		</td>
		
				
		</tr>
	
			<tr>
			<td>
					<div class="easyui-panel" title="虚拟内存  Top 10 使用率"
						style="width: 420px; height: 320px; background: #fafafa; border: 0px; position: relative;">
						<table id="tblVirtualMemory">

						</table>
					</div>
				</td>
				
				<td>
					<div class="easyui-panel" title="接口流量 Top10 "
						style="width: 450px; height: 320px; background: #fafafa; border: 0px; position: relative;">
						<table id="tblTopFlows">

						</table>
					</div>
				</td>
				
			</tr>
			
		</table>



	</body>
</html>
