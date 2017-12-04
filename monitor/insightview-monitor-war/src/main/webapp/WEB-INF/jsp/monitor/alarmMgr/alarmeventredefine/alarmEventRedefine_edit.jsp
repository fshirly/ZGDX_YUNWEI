<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head></head>
    <body>
        <style>
            #editRule h2 {
                background: #d1f0ff;
            }
        </style>
	<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/style/css/base.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventredefine/alarmEventRedefine_edit.js">
    </script>
    <div class="easyui-tabs" data-options="tabPosition:'left'" id="editRuleTab" style="overflow: auto;height:400px;margin-right: -10px;margin-left: -10px;">
        <input type="hidden" id="ruleId" value='${ruleId}'>
        <input type="hidden" id="editMoClassId" value='${moClassId}'>
		<input type="hidden" id="isSame" value='${rule.isSame}'>
        <input type="hidden" id="editMoClassName" value='${moClassName}'>
        <input type="hidden" id="levelJson" value='${levelJson}'>
        <div id="divRuleInfo" title="基本信息">
            <table id="tblRuleInfo" class="formtable1">
                <tr>
                    <td class="title">
                        规则名称：
                    </td>
                    <td>
                        <input id="ipt_ruleName" value="${rule.ruleName}" validator="{'default':'*','length':'1-64'}" onblur="javascript:checkRuleName();"></input><b>*</b>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        是否启用：	
                    </td>
                    <td>
                        <input id="ipt_isEnable" class="input" type="hidden"/>
						<c:if test="${rule.isEnable == null or rule.isEnable == 1}">
							<input type="radio" name="isEnable" value="1" checked style="width:13px">&nbsp;是 &nbsp;
							<input type="radio" name="isEnable" value="2" style="width:13px"/>&nbsp;否
						</c:if>
						<c:if test="${rule.isEnable == 2}">
							<input type="radio" name="isEnable" value="1" style="width:13px">&nbsp;是 &nbsp;
							<input type="radio" name="isEnable" value="2" checked style="width:13px"/>&nbsp;否
						</c:if>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        规则描述：
                    </td>
                    <td>
                        <textarea rows="3" id="ipt_ruleDesc" validator="{'length':'0-2000'}">${rule.ruleDesc}</textarea>
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
	                <td class="title" style="float:left;margin-left: -30px;">资源类型：</td>
	                <td style="float:left;">
		                    <input id="moClassId" class="inputs" onClick='showTree(this,"addressFatherId")' alt="-1" readonly="readonly"/>
		                    <div id="combdtree" class="dtreecob">
		                        <div id="dataResTreeDiv" class="dtree" style="width: 100%; height: 200px; overflow: auto;">
		                        </div>
		                        <div class="dBottom">
		                            <a href="javascript:hiddenDTree();">关闭</a>
		                        </div>
		                    </div>
	                </td>
					<td></td>
					<td></td>
	            </tr>
	            <tr>
	                <td style="padding-left: 10px;">已选资源列表</td>
	                <td style="float: right; margin-right: 10px;">说明：已选资源必须是同类型资源</td>
	            </tr>
	            
	        </table>
			<div class="datas" style="margin-left: 3px;width:695px;">
				<table id="tblMORes">
				</table>
			</div>
			<div class="conditionsBtn">
		        <input type="button" value="上一步" onclick="javascript:selectTab(0);"/>
		        <input type="button" value="下一步" onclick="javascript:selectTab(2);"/>
				<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
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
				<table class="formtable">
					<tr>
	                	<td class="title">告警等级：</td>
						<td>
	                        <select id="ipt_alarmLevelId" class="easyui-combobox">
	                            <c:forEach items="${alarmLevelList}" var="vo">
	                                <option value="<c:out value='${vo.alarmLevelID}' />"><c:out value="${vo.alarmLevelName}" /></option>
	                            </c:forEach>
	                        </select><b>*</b>
						</td>
	                </tr>
					<tr>
	                	<td class="title">告警等级颜色：</td>
						<td>
							<div id="alarmlevelVal" style="width: 50px;text-align: center;height: 18px;">
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
		        <input type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
				<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		    </div>
        </div>
    </div>
    
</body>
