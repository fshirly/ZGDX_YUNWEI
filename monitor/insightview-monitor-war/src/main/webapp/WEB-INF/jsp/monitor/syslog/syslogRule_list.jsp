<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/syslog/syslogRule_list.js">
        </script>
        <div class="location">
            当前位置：${navigationBar}
        </div>
        <div class="conditions" id="divFilter">
            <table>
                <tr>
                    <td>
                        <b>规则名称：</b>
                        <input id="txtRuleName"/>
                    </td>
                    <td>
                        <b>范围：</b>
                        <input type="text" id="txtServerIP" validator="{'default':'ipAddr'}"/>
						<input type="checkbox" id="txtIsAll"/>&nbsp;	包含全局
                    </td>
                    <td class="btntd">
                        <a href="javascript:void(0);" onclick="reloadTable();">查询</a>
                        <a href="javascript:void(0);" onclick="resetSyslogForm();">重置</a>
                    </td>
                </tr>
            </table>
        </div>
        <div class="datas">
            <table id="tblSyslogRule">
            </table>
        </div>
    </body>
</html>
