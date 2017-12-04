<%@ page language="java" pageEncoding="utf-8"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/base.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/reset.css" />
		<!-- mainframe -->

		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
	</head>

	<body>
		<style>
	      .label{
	      width:500px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	       .label2{
	      width:180px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	    </style>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/perf/perfKPIDef_detail.js"></script>
		<%
			String kpiID =(String)request.getAttribute("kpiID");
		 %>
	<div id="divTaskDetail"  style="height: 360px;overflow: auto">
		<input type="hidden" id="kpiID" value="<%= kpiID%>"/>
		<table id="tblTaskDetail" class="tableinfo">
			<tr>
				<td>
					<div style='float:left;'><b class="title">指标名称：</b></div>
					<div class="label2"><label id="lbl_name"></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">指标英文名：</b></div>
					<div class="label2"><label id="lbl_enName"></label></div>
				</td>
			</tr>
			<tr>
				<td>
					<div style='float:left;'><b class="title">对象类型：</b></div>
					<div class="label2"><label id="lbl_className" ></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">指标类别：</b></div>
					<div class="label2"><label id="lbl_kpiCategory" ></label></div>
				</td>
			</tr>
			<tr>
				<td>
					<div style='float:left;'><b class="title">单位：</b></div>
					<div class="label2"><label id="lbl_quantifier" ></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">值类型：</b></div>
					<div class="label2"><label id="lbl_valueType" ></label></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style='float:left;'><b class="title">汇总规则：</b></div>
					<div class="label2"><label id="lbl_amountType" ></label></div>
				</td>
				<%--<td>
					<div style='float:left;'><b class="title">是否支持阈值:</b></div>
					<div class="label2"><label id="lbl_isSupport" ></label></div>
				</td>
			--%></tr>
			
			<tr>
				<td colspan="2">
					<div style='float:left;'><b class="title">数据有效性范围：</b></div>
					<div class="label"><label id="lbl_valueRange"></label></div>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<div style='float:left;'><b class="title">备注：</b></div>
					<div class="label"><label id="lbl_note" class="x2"></label></div>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
	</body>
</html>
