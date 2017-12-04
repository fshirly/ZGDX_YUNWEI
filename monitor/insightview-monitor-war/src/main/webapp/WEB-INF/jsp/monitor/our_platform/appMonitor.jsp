<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${pageContext.request.contextPath}/style/kssc-css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/style/kssc-css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/style/kssc-css/iconfont.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/style/kssc-css/jquery.dataTables.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/style/kssc-css/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/style/kssc-css/style.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/monitor/kssc-js/lib/require.js"></script>
<script src="${pageContext.request.contextPath}/js/monitor/kssc-js/lib/config.js"></script>
<div class="container">
    <table class="table table-bordered table-striped" id="app"></table>
</div>
<script>
    require(["app/monitor/appMonitor"],function(page){
        page.main()
    })
</script>
