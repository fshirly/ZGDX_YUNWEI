<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<body>
		<script type="text/javascript">f = $;</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/sysparam_modify.js"></script>
	
		<input id="paramId" type="hidden" value="${paramId}">
			<div id="divUpdateSysParam">
				<form id="parForm">
				<input id="ipt_paramID" type="hidden" />
				<table id="tblSysparamInfo" class="formtable">
					<tr>
						<td class="title">
							参数分类：
						</td>
						<td>
							<input id="ipt_paramClass" class="input" disabled="disabled"></input>
						</td>

					</tr>
					<tr>
						<td class="title">
							参数名称：
						</td>
						<td>
							<input id="ipt_paramName" class="input" disabled="disabled"></input>
						</td>

					</tr>
					<tr>
						<td class="title">
							参数值：
						</td>
						<td id="paramValue">
							<textarea id="ipt_paramValue" rows="3" validator="{'default':'*' ,'length':'1-128'}"></textarea><b>*</b>
						</td>
						<td id="uploadImg" align="left">
							<div style="width: 180px; height: 40px; border: 1px solid #B7B7BC;">
								<img id="scanImg" src="${imgPath}" style="width:180px; height:40px;">
							</div>
						</td>
						<td id="selectFile">
							<div style="text-align: left;">
							<input type="file" id="userIcon" name="userIcon" style="width:160px"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="title">
							参数说明：
						</td>
						<td>
							<input id="ipt_paramDescr" disabled="disabled"></input>
						</td>
					</tr>

				</table>
				</form>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave">确定</a>
					<a href="javascript:void(0);" id="btnUpdate">取消</a>

				</div>
			</div>
	</body>
</html>