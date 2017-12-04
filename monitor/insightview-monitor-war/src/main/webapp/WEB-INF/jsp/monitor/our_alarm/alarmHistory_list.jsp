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
<style>
	.alarm-title{
		font-weight: 400;
	}
	.form-control {
		height: 28px;
		line-height: 28px;
		font-size: 13px;
		border-radius: 0px;
		padding: 1px 2px;
		box-shadow: 0 0 5px #ddd inset;
	}
	.btn {
		padding: 3px 12px;
		width: 80px;
	}
	#searchDiv {
		background: #fff;
		padding: 15px;
		margin-bottom: 15px;
		width: 100%;
		border: 1px solid #e6e6e6;
		border-top: 0px;
		-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
		-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
		transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	}
	.form-inline{
		height: 28px;
		margin: 0;
	}
	.form-group{
		margin-right: 20px;
	}
	#searchBtn{
		background-color: #00479d;
		border-color: #00479d;
	}
	#resetBtn{
		background: #ff8a0d;
		border-color: #ff8a0d;
	}
</style>
<div class="tableScroll">
	<div class="container">
		<div id="searchDiv">
			<form class="form-inline">
				<div class="pull-left form-group">
					<label class="alarm-title">告警标题:</label>
					<input type="text" class="form-control title" style="width: 200px">
				</div>
				<div class="pull-left">
					<a id="searchBtn" class="btn btn-primary">查询</a>
					<a id="resetBtn" class="btn btn-warning">重置</a>
				</div>
			</form>
		</div>


		<table class="table table-bordered table-striped" id="alarmHistoryList"></table>
	</div>
</div>

<script>
	var grid;
	require(["app/alarmHistory_list"],function(page){
		page.main()
	})
</script>