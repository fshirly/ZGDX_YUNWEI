<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <style>
            .txtAndCheckbox {
                background: #ffffff none repeat scroll 0 0;
                border-color: #7ca3bf #b8d0e0 #b8d0e0 #7ca3bf;
                border-image: none;
                border-style: solid;
                border-width: 1px;
                line-height: 22px;
                height: 150px;
                overflow: auto;
                padding: 0 2px;
                width: 176px;
            } .normalInput {
                color: #a1a1a1;
            }
        </style>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/syslog/syslogTask_modify.js">
        </script>
        <%        String taskID =(String)request.getAttribute("taskID"); %>
        <input type="hidden" id="taskID" value="<%= taskID%>"/><input type="hidden" id="moIds"/><input type="hidden" id="deviceips"/>
        <div id="divEditSyslog" style="height:360px;overflow:auto">
            <table id="tblEditSyslog" class="formtable">
                <tr>
                    <td class="title">
                        规则名称：
                    </td>
                    <td>
                        <input id="ipt_taskName" validator="{'default':'*','length':'1-128'}" /><b>*</b>
                    </td>
                    <td class="title">
                        适用范围：
                    </td>
                    <td>
                        <input id="ipt_isAll" class="input" type="hidden"/><input type="radio" id="ipt_isAll0" name="isAll" value="1" onclick="edit();" checked style="width:13px"/>&nbsp;全局&nbsp;<input type="radio" id="ipt_isAll1" name="isAll" value="2" onclick="edit();" style="width:13px"/>&nbsp;指定设备
                    </td>
                </tr>
                <tr id="trServerIp" style="display:none">
                    <td class="title">
                        设备IP：
                    </td>
                    <td colspan="3">
                        <textarea rows="3" id="ipt_serverIP" readonly="readonly"></textarea>
                        <a href="javascript:chooseServerIP();">选择</a>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        事件信息源：
                    </td>
                    <td>
                        <div rows="5" id="ipt_facility" class="txtAndCheckbox">
                        </div>
                    </td>
                    <td class="title">
                        事件级别：
                    </td>
                    <td>
                        <div rows="5" id="ipt_severity" class="txtAndCheckbox">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                    </td>
                    <td>
                        <input type="button" value="全选" onclick="selectAllFacility();" style="width: 50px;line-height:0px;"><input type="button" value="全不选" onclick="selectNoFacility();" style="width: 50px;line-height:0px;">
                    </td>
                    <td class="title">
                    </td>
                    <td>
                        <input type="button" value="全选" onclick="selectAllSeverity();" style="width: 50px;line-height:0px;"><input type="button" value="全不选" onclick="selectNoSeverity();" style="width: 50px;line-height:0px;">
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        关键字：
                    </td>
                    <td colspan="3">
                        <input class="x2" id="ipt_keyword" validator="{'length':'0-60'}" style="width:499px" value="多个关键字用','分隔" onFocus="if (this.value==this.defaultValue){this.className = 'x2'; this.value='';}" onblur="if (this.value==''){this.className = 'normalInput x2'; this.value=this.defaultValue;}">
                        </input>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        说明：
                    </td>
                    <td colspan="3">
                        <textarea rows="3" class="x2" id="ipt_note" style="width:499px" validator="{'length':'0-100'}"></textarea>
                    </td>
                </tr>
            </table>
            <div class="conditionsBtn">
                <input type="button" value="确定" onclick="javascript:toUpdate();"/><input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
            </div>
        </div>
    </body>
</html>