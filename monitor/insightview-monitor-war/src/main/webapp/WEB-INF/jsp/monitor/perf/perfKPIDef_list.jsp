<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/perf/perfKPIDef_list.js">
        </script>
        <script type="text/javascrict">
            <%String flag=(String)request.getAttribute("flag"); %>
            <%String classID=(String)request.getAttribute("classID"); %>
        </script>
    </head>
    <body>
        <div class="rightContent">
            <input id="flag" type="hidden" value="<%=flag%>"/><input id="classID" type="hidden" value="<%=classID%>"/>
            <div class="location">
                当前位置：运行监测&gt;&gt;运维平台 &gt;&gt;<span>采集指标维护</span>
            </div>
            <div class="conditions" id="divFilter">
                <table>
                    <tr>
                        <td>
                            <b>指标名称：</b>
                            <input type="text" id="txtName" />
                        </td>
                        <td>
                            <b>指标类别：</b>
                            <select id="txtKPICategory" name="txtKPICategory" class="easyui-combobox">
                                <c:forEach items="${categoryLst}" var="vo">
                                    <option value="<c:out value='${vo.kpiCategory}' />"><c:out value="${vo.kpiCategory}" /></option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="btntd">
                            <a href="javascript:void(0);" onclick="reloadTable();">查询</a>
                            <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="datas">
                <table id="tblPerfKPIDef">
                </table>
            </div>
        </div>
    </body>
</html>
