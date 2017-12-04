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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/syslog/syslogRule_detail.js">
        </script>
        <%
        String ruleID =(String)request.getAttribute("ruleID"); %>
        <div style="height:350px;overflow:auto;">
            <div id="divRuleDetail">
                <input type="hidden" id="ruleID" value="<%= ruleID%>"/>
                <table id="tblSyslogRuleDetail" class="tableinfo">
                    <tr>
                        <td>
                            <div style='float:left;'>
                                <b class="title">规则名称：</b>
                            </div>
                            <div class="label2">
                                <label id="lbl_ruleName">
                                </label>
                            </div>
                        </td>
                        <td>
                            <div style='float:left;'>
                                <b class="title">适用范围：</b>
                            </div>
                            <div class="label2">
                                <label id="lbl_serverIP">
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style='float:left;'>
                                <b class="title">syslog事件：</b>
                            </div>
                            <div class="label2">
                                <label id="lbl_alarmOID" class="x2">
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style='float:left;'>
                                <b class="title">syslog过滤表达式：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_filterExpression" class="x2">
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style='float:left;'>
                                <b class="title">说明：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_note" class="x2">
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
			<br/>
			<h2>已经下发的采集机</h2>
			<br/>
            <div class="datas">
                <table id="tblViewCollector">
                </table>
            </div>
            <div class="conditionsBtn">
                <input type="button" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
            </div>
        </div>
    </body>
</html>