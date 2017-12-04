<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/syslog/syslogRuleAddCollector.js">
        </script>
        <%
        String ruleID =(String)request.getAttribute("ruleID");
		 %>
        <input type="hidden" id="ruleID" value="<%= ruleID%>"/>
        <div class="winbox" style="height: 375px;">
            <div class="conditions" id="divFilter">
                <table id="tblSearchCollector">
                    <tr>
                        <td>
                            <b>采集机IP</b>
                            <input id="txtIPAddress"/>
                        </td>
                        <td class="btntd">
                            <a href="javascript:void(0);" onclick="reloadUserTable();" class="fltrt">查询</a>
                            <a href="javascript:void(0);" onclick="resetForm('divFilter');" class="fltrt">重置</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="datas">
                <table id="tblAddCollectors">
                </table>
            </div>
        </div>
        <div class="conditionsBtn">
            <input type="button" value="确定" onclick="javascript:doAddCollectors();"/><input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
        </div>
    </body>
</html>