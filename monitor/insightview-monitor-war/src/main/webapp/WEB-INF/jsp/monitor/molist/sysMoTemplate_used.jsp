<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTemplate_used.js">
        </script>
        <%	String templateID=(String)request.getAttribute("templateID"); %>
        <input id="ipt_templateID" type="hidden" value="<%= templateID%>"/>
        <div id="divUsedDevice"class="winbox">
            <div class="datas">
                <table id="tblUsedDevice">
                </table>
            </div>
        </div>
        <div class="conditionsBtn">
            <a href="javascript:void(0);" id="btnClose" class="fltrt" onclick="javascript:$('#popWin').window('close');">关闭</a>
        </div>
    </body>
</html>
