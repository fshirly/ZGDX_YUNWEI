<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/syslog/syslogTask_list.js">
        </script>
        <div class="location">
            当前位置：${navigationBar}
        </div>
        <div class="conditions" id="divFilter">
            <table>
                <tr>
                    <td>
                        <b>任务ID：</b>
                        <input type="text" id="txtTaskID"/>
                    </td>
                    <td>
                        <b>规则名称：</b>
                        <input type="text" id="txtRuleName"/>
                    </td>
                    <td>
                        <b>采集机： </b>
                        <input type="text" id="txtCollectorName"/>
                    </td>
                    <td class="btntd">
                        <a href="javascript:void(0);" onclick="reloadTable();">查询</a>
                        <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
                    </td>
                </tr>
            </table>
        </div>
        <div class="datas">
            <table id="tblSyslogTask">
            </table>
        </div>
    </body>
</html>
