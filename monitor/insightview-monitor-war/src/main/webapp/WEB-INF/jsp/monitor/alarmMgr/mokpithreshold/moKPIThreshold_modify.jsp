<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/mokpithreshold/moKPIThreshold_modify.js">
        </script>
        <%        String ruleID =(String)request.getAttribute("ruleID"); %>
        <div id="divThresholdModify">
            <input type="hidden" id="ruleID" value="<%= ruleID%>"/><input id="ipt_deviceId" type="hidden" value="" /><input id="ipt_volumnsId" type="hidden" value="" /><input id="ipt_memoriesId" type="hidden" value="" /><input id="ipt_networkIfId" type="hidden" value="" /><input id="ipt_cpusId" type="hidden" value="" /><input id="ipt_oracleId" type="hidden" value="" /><input id="ipt_middleWareId" type="hidden" value="" /><input id="ipt_threadPoolId" type="hidden" value="" /><input id="ipt_classLoadId" type="hidden" value="" /><input id="ipt_jdbcDSId" type="hidden" value="" /><input id="ipt_jdbcPoolId" type="hidden" value="" /><input id="ipt_memPoolId" type="hidden" value="" /><input id="ipt_middleJTAId" type="hidden" value="" /><input id="ipt_middlewareJvmId" type="hidden" value="" /><input id="ipt_runObjId" type="hidden" value="" /><input id="ipt_oracleTbsMoId" type="hidden" value="" /><input id="ipt_oracleRollSegMoId" type="hidden" value="" /><input id="ipt_oracleDataFileMoId" type="hidden" value="" /><input id="ipt_oracleSgaMoId" type="hidden" value="" /><input id="ipt_oracleDbMoId" type="hidden" value="" /><input id="ipt_oracleInsMoId" type="hidden" value="" /><input id="ipt_mysqlServerId" type="hidden" value="" /><input id="ipt_mysqlDBId" type="hidden" value="" /><input id="ipt_mysqlSysVarId" type="hidden" value="" /><input id="ipt_j2eeAppId" type="hidden" value="" /><input id="ipt_middleJMSId" type="hidden" value="" /><input id="relationPath" type="hidden" value="" /><input id="ipt_webModuleId" type="hidden" value="" /><input id="ipt_servletId" type="hidden" value="" /><input id="ipt_zoneManagerId" type="hidden" value="" /><input id="ipt_moReadMoId" type="hidden" value="" /><input id="ipt_moTagMoId" type="hidden" value="" /><input id="parantClassId" type="hidden" /><input id="ipt_db2InsMoId" type="hidden" value="" /><input id="ipt_db2DbMoId" type="hidden" value="" /><input id="ipt_db2DbMoId2" type="hidden" value="" /><input id="ipt_db2BufferPoolMoId" type="hidden" value="" /><input id="ipt_db2TbsMoId" type="hidden" value="" /><input id="ipt_msServerMoId" type="hidden" value="" /><input id="ipt_msDeviceMoId" type="hidden" value="" /><input id="ipt_msSQLDbMoId" type="hidden" value="" /><input id="ipt_sybaseServerMoId" type="hidden" value="" /><input id="ipt_sybaseDeviceMoId" type="hidden" value="" /><input id="ipt_sybaseDbMoId" type="hidden" value="" /><input id="ipt_webSiteMoID" type="hidden"/><input id="ipt_siteType" type="hidden"/>
            <table id="tblThresholdModify" class="formtable1">
                <tr>
                    <td class="title">
                        指标名称：
                    </td>
                    <td>
                        <input id ="ipt_kpiID" type="hidden"/><input id ="ipt_kpiClassID" type="hidden"/><input id="ipt_kpiName" readonly="readonly" validator="{'default':'*','length':'1-64'}" /><b>*</b>
                        <a id="btnChoseKpi" href="javascript:loadPerfKPIDef();">选择</a>
                        <a id="btnClearKpi" href="javascript:clearPerfKPIDef();" style="display:none">清空</a>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        对象类型：
                    </td>
                    <td>
                        <input class="easyui-combobox" id ="ipt_classID"/><b>*</b>
                    </td>
                </tr>
                <tr id="isShow1">
                    <td class="title">
                        源对象：
                    </td>
                    <td>
                    <input id ="ipt_sourceMOID" type="hidden"/><input id="ipt_sourceMOName" readonly="readonly" /><a id="btnChoseSource" href="javascript:loadSource();">&nbsp;选择</a>
                    <a id="btnClearSource" href="javascript:clearSource();" style="display:none">清空</a>
                </tr>
                <tr id="isShow" style="display: none">
                    <td class="title">
                        管理对象：
                    </td>
                    <td>
                        <input id ="ipt_moID" type="hidden"/><input id="ipt_moName" readonly="readonly" /><a id="btnChoseMo" href="javascript:loadMoRescource();">&nbsp;选择</a>
                        <a id="btnClearMo" href="javascript:clearMoRescource();" style="display:none">清空</a>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        上限阈值：
                    </td>
                    <td>
                        <input class="inputVal" id="ipt_upThreshold" validator="{'default':'ptInteger'}" onfocus="javascript:isHaveUpThreshold()"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        上限回归阈值：
                    </td>
                    <td>
                        <input class="inputVal" id="ipt_upRecursiveThreshold" onfocus="javascript:isUpThreshold()" validator="{'default':'ptInteger'}"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        下限阈值：
                    </td>
                    <td>
                        <input class="inputVal" id="ipt_downThreshold" validator="{'default':'ptInteger'}"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        下限回归阈值：
                    </td>
                    <td>
                        <input class="inputVal" id="ipt_downRecursiveThreshold" onfocus="javascript:isDownThreshold()" validator="{'default':'ptInteger'}"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                       单位：
                    </td>
                    <td>
                        <textarea rows="3" id="ipt_descr" readonly="readonly"></textarea>
                    </td>
                </tr>
            </table>
            <div class="conditionsBtn">
                <input type="button" id="btnSave" value="确定" onclick="javascript:toUpdate();"/><input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
            </div>
        </div>
        <div id="divMObject" class="easyui-window" maximizable="false" collapsible="false" minimizable="false" closed="true" modal="true" title="选择对象类型" style="width: 400px; height: 450px;">
            <div id="dataMObjectTreeDiv" class="dtree" style="width: 100%; height: 200px;">
            </div>
        </div>
    </body>
</html>
