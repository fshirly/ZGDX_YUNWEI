<%@ page language="java"  pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.lang.*;" %>
<%@ include file = "../../common/pageincluded.jsp" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/perf/offlinePerfTask_list.js">
        </script>
    </head>
    <body>
        <div class="rightContent">
            <div class="location">
                当前位置：${navigationBar}
            </div>
            <div class="conditions" id="divFilter">
                <table>
                    <tr>
                        <td>
                            <b>任务ID：</b>
                            <input type="text" id="txtTaskId"/>
                        </td>
                        <td>
                            <b>被采设备：</b>
                            <input id="txtDeviceIp" validator="{'default':'ipAddr','length':'1-15'}"/>
                        </td>
                        <td>
                            <b>采集机： </b>
                            <input type="text" id="txtServerName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>任务状态：</b>
                            <select class="easyui-combobox" id="txtTaskStatus" data-options="editable:false">
                                <option value="-2">请选择</option>
                                <option value="0">运行中</option>
                                <option value="1">已停止</option>
                            </select>
                        </td>
                        <td>
                            &nbsp;
                        </td>
                        <td class="btntd">
                            <a href="javascript:void(0);" onclick="reloadTable();">查询</a>
                            <a href="javascript:void(0);" onclick="resetFormDiv('divFilter');">重置</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="datas tops2">
                <table id="tblPerfTask">
                </table>
            </div>
        </div>
    </body>
</html>
