<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
    </head>
    <body>
        <style>
            .label {
                width: 340px;
                max-height: 100px;
                float: left;
                overflow: auto;
                word-wrap: break-word;
            } #editRule h2 {
                background: #d1f0ff;
            }
        </style>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventredefine/alarmEventRedefine_detail.js"></script>
        <div class="easyui-tabs" data-options="tabPosition:'left'" id="editRuleTab" style="overflow: auto;height:400px;margin-right: -10px;margin-left: -10px;">
            <input type="hidden" id="ruleId" value='${ruleId}'>
			<input type="hidden" id="editMoClassId" value='${moClassId}'>
			<input type="hidden" id="isSame" value='${rule.isSame}'>
			<input type="hidden" id="editMoClassName" value='${moClassName}'>
            <div id="divRuleInfo" title="基本信息">
                <table id="tblRuleInfo" class="tableinfo1">
                    <tr>
                        <td>
                            <div style='float:left;'>
                                <b class="title">规则名称：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_ruleName"> ${rule.ruleName}</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div style='float:left;'>
                                <b class="title">是否启用：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_isEnable">
                                    <c:if test="${rule.isEnable == 1}">是</c:if>
                                    <c:if test="${rule.isEnable == 2}">否</c:if>
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div style='float:left;'>
                                <b class="title">规则描述：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_ruleDesc">${rule.ruleDesc}</label>
                            </div>
                        </td>
                    </tr>
                </table>
                <div class="conditionsBtn">
                    <input type="button" value="下一步" onclick="javascript:selectTab(1);"/>
					<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
                </div>
            </div>
            <div id="divResource" title="监测对象">
                <table class="formtable" style="margin-top:0px;width: 100%;">
                    <tr style="background:#d1f0ff;height: 32px;">
                        <td class="title" style="float:left;margin-left: -30px;">
							资源类型：
                        </td>
                        <td style="float:left;">
							<div id="moClassId" style="padding-top: 6px;">${moClassName}</div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td style="padding-left: 10px;">
							已选资源列表
                        </td>
                        <td style="float: right; margin-right: 10px;">
							 说明：已选资源必须是同类型资源
                        </td>
                    </tr>
                </table>
                <div class="datas" style="margin-left: 3px;width:695px;">
                    <table id="tblMORes">
                    </table>
                </div>
                <div class="conditionsBtn">
                    <input type="button" value="上一步" onclick="javascript:selectTab(0);"/><input type="button" value="下一步" onclick="javascript:selectTab(2);"/><input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
                </div>
            </div>
            <div id="divAlarmLevel" title="告警等级">
                <input id="ipt_isSame" class="input" type="hidden"/>
                <table class="formtable" style="margin-top:0px;width: 100%;">
                    <tr style="background:#d1f0ff;">
                        <td style="padding-left: 10px;">
                            <input type="radio" name="isSame" id="isSame1" value="1" checked style="width:13px" onclick="editIsSame();">&nbsp;所有告警事件相同告警等级
                        </td>
                        <td style="float: right; margin-right: 10px;">
                            <input type="radio"name="isSame" id="isSame2" value="2" style="width:13px" onclick="editIsSame();"/>&nbsp;不同告警事件定义不同告警等级
                        </td>
                    </tr>
                </table>
                <div id="divSame">
                    <table class="tableinfo1">
                    	 <tr>
	                        <td>
	                            <div style='float:left;'>
	                                <b class="title">告警等级：</b>
	                            </div>
	                            <div class="label">
	                               <label id="lbl_alarmLevelId"></label>
	                            </div>
	                        </td>
	                    </tr>
                        <tr>
							<td>
	                            <div style='float:left;'>
	                                <b class="title">  告警等级颜色：</b>
	                            </div>
	                            <div class="label">
	                               <div id="lbl_alarmlevelVal" style="width: 50px;text-align: center;height: 18px;"></div>
	                            </div>
	                        </td>
                        </tr>
                    </table>
                </div>
                <div id="divDifferent">
                    <div class="datas" style="margin-left: 3px;width:695px;">
                        <table id="tblDefinedEvent">
                        </table>
                    </div>
                </div>
                <div class="conditionsBtn">
                    <input type="button" value="上一步" onclick="javascript:selectTab(1);"/>
					<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
                </div>
            </div>
        </div>
    </body>
</html>