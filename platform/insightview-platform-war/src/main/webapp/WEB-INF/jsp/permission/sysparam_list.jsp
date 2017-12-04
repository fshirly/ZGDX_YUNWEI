<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<body>
		<script type="text/javascript">f = $;</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/sysparam.js"></script>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>

			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<!--<td>
							<b>参数分类：</b>
							<input type="text" class="inputs" id="txtFilterParamClass" />
						</td>-->
						<td>
							<b>参数名称：</b>
							<input type="text" class="inputs" id="txtFilterParamName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick=resetForm('divFilter');;
>重置</a>

						</td>
					</tr>
				</table>
			</div>
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblSysparam">

				</table>
			</div>
			<!-- end .datas -->
		</div>
	</body>
</html>