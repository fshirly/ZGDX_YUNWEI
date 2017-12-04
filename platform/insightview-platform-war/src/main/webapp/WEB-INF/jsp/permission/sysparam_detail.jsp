<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<body>
		<script type="text/javascript">f = $;</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/sysparam_detail.js"></script>
				    	 	<style>
   	 		  .label{
      width:300px;
        max-height: 40px;
        float: left;
        overflow: auto;
        word-wrap: break-word;
      }
    </style>
			<input id="paramId" type="hidden" value="${paramId}">
			<div id="divParamInfo">
				<input id="ipt_paramID" type="hidden" />
				<table id="tblSysparamInfo" class="tableinfo1">
					<tr>
						<td>
							<b class="title"> 参数分类： </b>
							<label id="lbl_paramClass" class="input"></label>
						</td>

					</tr>
					<tr>
						<td>
							<b class="title"> 参数名称： </b>
							<label id="lbl_paramName" class="input"></label>
						</td>

					</tr>
					<tr>
						<td>
							<div style='float:left;'><b class="title"> 参数值： </b></div>
							 <div class="label"><label id="lbl_paramValue"></label></div>
							<img id="scanImg" src="${imgPath}" style="width:180px; height:40px;">
						</td>
					</tr>
					<tr>
						<td>
							<b class="title"> 参数说明： </b>
							<label id="lbl_paramDescr"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭"
						onclick="javascript:void(0);" />
				</div>
			</div>
	</body>
</html>