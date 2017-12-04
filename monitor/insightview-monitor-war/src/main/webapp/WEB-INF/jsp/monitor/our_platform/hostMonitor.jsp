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
<div class="container allTable">
    <div class="row" style="margin:0 0 20px 0;padding:0">
        <div class="commonTable">
            <div class="tableWarpper">
                <table class="table table-striped table-bordered" id="cpu" >
                    <caption><i class="icon iconfont">&#xe632;</i>主机CPU使用率</caption>
                </table>
                <div class="pull-right" id="fill"></div>
            </div>
        </div>
        <div class="commonTable pull-right">
            <div class="tableWarpper">
                <table class="table table-striped table-bordered" id="host">
                    <caption><i class="icon iconfont">&#xe600;</i>主机硬盘使用率</caption>
                </table>
            </div>

        </div>
    </div>
    <div class="row" style="margin:0 0 20px 0;padding:0">
        <%--<div class="commonTable">--%>
            <%--<div class="tableWarpper">--%>
                <%--<table class="table table-striped table-bordered" id="hardDisk">--%>
                    <%--<caption><i class="icon iconfont">&#xe600;</i>主机硬盘IO</caption>--%>
                <%--</table>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="commonTable pull-left">
            <div class="tableWarpper">
                <table class="table table-striped table-bordered" id="memory">
                    <caption><i class="icon iconfont">&#xe68e;</i>主机内存使用率</caption>
                </table>
            </div>

        </div>
    </div>
    <div class="row" style="margin:0 0 20px 0;padding:0">
        <%--<div class="commonTable">--%>
            <%--<div class="tableWarpper">--%>
                <%--<table class="table table-striped table-bordered" id="port">--%>
                    <%--<caption><i class="icon iconfont">&#xe64b;</i>主机接口流量</caption>--%>
                <%--</table>--%>
            <%--</div>--%>

        <%--</div>--%>
    </div>
</div>

<script>
    require(["app/monitor/hostMonitor"],function(page){
        page.main();
    })
</script>

