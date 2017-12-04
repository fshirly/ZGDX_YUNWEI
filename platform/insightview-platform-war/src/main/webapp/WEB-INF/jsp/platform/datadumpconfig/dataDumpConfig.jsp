<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../common/pageincluded.jsp" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/datadumpconfig/dataDumpConfig.js">
        </script>
        <div class="location">
            ${navigationBar}
        </div>
        <div class="easyui-tabs" id="dataDumpConfig">
            <div id="divDataDumpConfig" title="转储/清理策略" style="overflow: auto;">
                <table id="tblDataDumpConfig" class="formtable1">
                    <tr>
                        <td class="title">
                            原始数据保留时间：
                        </td>
                        <td>
                            <input type="text" id="originalDataRetentionTime" class="easyui-numberbox" data-options="min:1" validator="{'default':'*'}" />&nbsp;天<b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            转储数据保留时间：
                        </td>
                        <td>
                            <select id="dumpDataRetentionTime" class="easyui-combobox" validator="{'default':'*','type':'combobox'}">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>&nbsp;月<b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            执行时间：
                        </td>
                        <td>
                            <label style="vertical-align: middle;">
                                每天&nbsp;
                            </label>
                            <input id="executeTime" style="width:150px;" class="easyui-timespinner" data-options="showSeconds:false" validator="{'default':'*','type':'timespinner'}"/><b>*</b>
                        </td>
                    </tr>
                </table>
                <div class="conditionsBtn">
                    <a onclick="doSetDataDumpConfig();">确定</a>
                </div>
            </div>
            <div id="divDumpList" title="转储列表" style="overflow: auto;">
                <h2 style="background: #ebeff2 none repeat scroll 0 0; line-height: 24px; padding-left: 10px;">已配置转储的数据库表</h2>
                <div class="datas tops3">
                    <table id="tblDumpList">
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
