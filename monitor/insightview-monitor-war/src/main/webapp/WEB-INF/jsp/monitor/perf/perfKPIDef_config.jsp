<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/perf/perfKPIDef_config.js">
        </script>
        <%        String kpiID =(String)request.getAttribute("kpiID");
        String classID =(String)request.getAttribute("classID"); %>
        <div style="height:310px;overflow:auto">
            <div id="configPerfKPIDefDiv">
                <input id="r_kpiID" value="<%= kpiID%>" type="hidden" /><input id="r_classID" value="<%= classID%>" type="hidden" />
                <table id="tblConfigPerfKPIDef" class="formtable">
                    <tr>
                        <td class="title">
                            父对象类型：
                        </td>
                        <td>
                            <input id="ipt_moClassID" onfocus="choseMObjectTree();" validator="{'default':'*'}"/><b>*</b>
                        </td>
                        <td class="title">
                            是否支持阈值：
                        </td>
                        <td>
                            <input id="ipt_isSupport" class="input" type="hidden"/><input type="radio" name="isSupport" value="1" checked>&nbsp;是 &nbsp;<input type="radio" name="isSupport" value="2"/>&nbsp;否
                        </td>
                    </tr>
                </table>
            </div>
            <br/>
            <!-- 显示已經配置的指标与对象关系 --><h2>已配置的指标与对象关系 </h2>
            <br/>
            <div class="datas top2">
                <table id="tblKPIOfMOClass">
                </table>
            </div>
            <div class="conditionsBtn">
                <input type="button" value="确定" onclick="javascript:toConfig();"/><input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
            </div>
        </div>
        <div id="divMObject" class="easyui-window" maximizable="false" collapsible="false" minimizable="false" closed="true" modal="true" title="选择对象类型" style="width: 400px; height: 450px;">
            <div id="dataMObjectTreeDiv" class="dtree" style="width: 100%; height: 200px;">
            </div>
        </div>
    </body>
</html>
