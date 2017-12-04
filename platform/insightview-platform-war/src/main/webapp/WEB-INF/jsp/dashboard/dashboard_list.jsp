<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title></title>
	<link href="${pageContext.request.contextPath}/css/kssc-css/bootstrap.min.css"  rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/style/kssc-css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/kssc-css/iconfont.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/kssc-css/jquery.dataTables.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/kssc-css/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/kssc-css/style.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/kssc-js/lib/require.js"></script>
	<script src="${pageContext.request.contextPath}/js/kssc-js/lib/config.js"></script>
</head>
<body>
<div class="container-box">
	<div class="content-header">
		<i class="icon iconfont back" id = "contextMenu" title="返回全国地图">&#xe67f;</i>
		<span class="title font1">试点地区线图运行状态图</span>
	</div>

	<ul class="clearfix content-box">
		<li class="left-box">
			<div class="ajustBtn"><i class="icon iconfont">&#xe6ad;</i></div>
			<div class="appChart"><div id="app"></div></div>
			<div class="operate">
				<div class="storageClouds" style="cursor: pointer;"><i class="icon iconfont " title="天翼云存储平台">&#xe639;</i><span>天翼云存储平台</span></div>
				<div class="cloudPlatform"  style="cursor: pointer;"><i class="icon iconfont " title="新视通云平台">&#xe641;</i><span>新视通云平台</span></div>
				<%--<i class="icon iconfont back" id = "contextMenu" title="返回全国地图">&#xe67f;</i>--%>
			</div>
		</li>
		<li class="right-box">
            <ul class="initContent">
                <li class="cloud clearfix">
					<div class="right-title font1">天翼云存储状态：</div>
					<div class="clearfix right-content-box">
						<div class="right-item font2 pull-left w-50"><span>连通性：</span><span class="connectivity"></span></div>
						<div class="right-item font2 pull-right w-50"><span>使用率：</span><span class="rate"></span></div>
					</div>

				</li>
                <li class="code clearfix">
					<div class="right-title font1">编解码器状态：</div>
					<div class="clearfix right-content-box">
						<div class="right-item font2  pull-left w-50"><span>正常：</span><span class="normal"><img width="20" height="20" src="${pageContext.request.contextPath}/style/images/kssc-img/loading.gif"></span><span class="unit">（个）</span></div>
						<div class="right-item font2 pull-right w-50"><span>异常：</span><span class="color-f17801 abnormal"><img width="20" height="20" src="${pageContext.request.contextPath}/style/images/kssc-img/loading.gif"></span><span class="unit color-f17801">（个）</span></div>
					</div>
				</li>
                <li>
                    <div class="portStatus pull-left  w-50">
						<div class="right-title font1">新视通接口状态：</div>
						<div class="clearfix">
							<div class="right-item font2" style="margin-bottom:15px"><span>正常：</span><span class="normalPort"></span><span class="unit">（个）</span></div>
							<div class="right-item font2"><span>异常：</span><span class="color-f17801">0<span class="unit">（个）</span></span></div>
						</div>
					</div>
                    <div class="serveHealthy pull-right  w-50">
						<div class="right-title font1">KSCC服务器健康度：</div>
						<div class="font2 right-item">70%</div>
					</div>
                </li>
            </ul>
			<div class="warpper"></div>
		</li>
	</ul>
</div>
</body>
<script>
	require(["echartJs"],function(page){
		page.main()
	})
</script>
</html>