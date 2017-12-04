<%@ page language="java" pageEncoding="utf-8" %>
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/syslog/syslogTask_detail.js">
        </script>
        <%
        String taskID =(String)request.getAttribute("taskID"); %>
        <div id="divTaskDetail" style="height:350px;overflow:auto;">
            <input type="hidden" id="taskID" value="<%= taskID%>"/>
            <table id="tblSyslogTaskDetail" class="tableinfo">
                <tr>
                    <td>
                        <div style='float:left;'>
                            <b class="title">任务ID：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_taskID">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div style='float:left;'>
                            <b class="title">规则名称：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_ruleName">
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style='float:left;'>
                            <b class="title">所属采集机：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_collectorName">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div style='float:left;'>
                            <b class="title">最近状态时间：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_lastStatusTime">
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style='float:left;'>
                            <b class="title">任务创建者：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_creatorName">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div style='float:left;'>
                            <b class="title">创建时间：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_createTime">
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style='float:left;'>
                            <b class="title">操作进度：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_progressStatus">
                            </label>
                        </div>
                    </td>
                    <td>
                        <div style='float:left;'>
                            <b class="title">最近操作结果：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_lastOPResult">
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div style='float:left;'>
                            <b class="title">错误信息：</b>
                        </div>
                        <div class="label">
                            <label id="lbl_errorInfo" class="x2">
                            </label>
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