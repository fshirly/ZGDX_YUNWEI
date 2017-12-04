<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>我关注的资源详情</title>
</head>
<body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/myConcern_detail.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	
	<div id="container">

		<input id="ciID" type="hidden" value="${resciId }"/>
		<div id="resciConcernDetail">
		    <!--配置项变更信息 -->
			<div class="datas" style="height: 420px; margin-top: 10px;">
				<table id="tblCiChangeInfo">
				</table>
			</div>
			<p class="conditionsBtn">
				<a id="myConcernCiChangeInfo_cancle" href="javascript:void(0);">关闭</a>
			</p>
		</div>
		<!--变更信息 -->
		<div id="divResCiChangeInfo" class="divResCiChangeInfo"></div>
	</div>
	
	<!--变更明细 -->
	<div id="divResCiChangeDetails" class="easyui-window" minimizable="false"
		closed="true" modal="true" title="变更明细"
		style="width: 670px; height: 420px;">

		<!--配置项类型信息表 -->
		<div class="datas" style="height: 306px">
			<table id="tblResCiChangeList">
			</table>
		</div>
		<p class="conditionsBtn">
			<a id="myConcernDetail_cancle" href="javascript:void(0);" >取消</a>
		</p>
	</div>
	
</body>
</html>