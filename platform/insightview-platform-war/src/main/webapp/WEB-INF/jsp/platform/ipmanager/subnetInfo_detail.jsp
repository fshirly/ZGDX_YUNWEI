<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
		<style>
            .label {
                width: 500px;
                max-height: 100px;
                float: left;
                overflow: auto;
                word-wrap: break-word;
            } .label2 {
                width: 180px;
                max-height: 100px;
                float: left;
                overflow: auto;
                word-wrap: break-word;
            }
        </style>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/platform/ipmanager/subnetInfo_detail.js"></script>
		<%
			String subNetId =(String)request.getAttribute("subNetId");
		 %>
	<div id="divSubnetDetail">
		<input type="hidden" id="subNetId" value="<%= subNetId%>"/>
		<table id="tblSubnetDetail" class="tableinfo">
			<tr>
				<td>
					<b class="title">网络地址：</b>
					<label id="lbl_netAddress" ></label>
				</td>
				<td>
					<b class="title">子网掩码：</b>
					<label id="lbl_subNetMark"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">占用地址数：</b>
					<label id="lbl_usedNum" ></label>
				</td>
				<td>
					<b class="title">空闲地址数：</b>
					<label id="lbl_freeNum" ></label>
				</td>
			</tr>
			<tr>
				
				<td>
					<b class="title">子网容量：</b>
					<label id="lbl_totalNum" ></label>
				</td>
				<td>
					<b class="title">广播地址：</b>
					<label id="lbl_broadCast" ></label>
				</td>
			</tr>
			<tr>
				
				<td>
					<b class="title">网关：</b>
					<label id="lbl_gateway" ></label>
				</td>
				<td>
					<b class="title">DNS：</b>
					<label id="lbl_dnsAddress"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">所属网络类型：</b>
					<label id="lbl_subNetTypeName"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style='float:left;'>
					  <b class="title">子网描述：</b>
					</div>
                    <div class="label">
					  <label id="lbl_descr" class="x2"></label>
					</div>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input type="button" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
	</body>
</html>
