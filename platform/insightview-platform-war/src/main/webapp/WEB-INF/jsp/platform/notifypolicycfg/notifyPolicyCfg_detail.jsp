<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <style>
            .label {
                width: 500px;
                max-height: 100px;
                float: left;
                overflow: auto;
                word-wrap: break-word;
            } .label2 {
                width: 180px;
                max-height: 100px;
                float: left;
                overflow: auto;
                word-wrap: break-word;
            }
        </style>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/notifypolicycfg/notifyPolicyCfg_detail.js">
        </script>
        <div id="alarmNotifyTabs" class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" style="height: 400px;overflow: auto;">
            <input type="hidden" id="policyId" value="${policyId}"/>
            <div title="通知策略定义" data-options="closable:false">
                <table id="tblAlarmNotifyCfgView" class="tableinfo" style="margin-bottom:-5px;">
                    <tr>
                        <td>
                            <div style='float:left;'>
                                <b class="title">规则名称：</b>
                            </div>
                            <div class="label2">
                                <label id="lbl_policyName">
                                </label>
                            </div>
                        </td>
                        <td>
                            <div style='float:left;'>
                                <b class="title">策略类型：</b>
                            </div>
                            <div class="label2">
                                <label id="lbl_typeName">
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style='float:left;'>
                                <b class="title">最大发送次数：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_maxTimes">
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="border-bottom: none;">
                            <div style='float:left;'>
                                <b class="title" style="color: #666666;font-weight: normal;">是否短信：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_isSms">
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
                <div class="datas">
                    <table id="tblSmsList">
                    </table>
                </div>
                <table style="margin: 10px 0px 10px 89px;">
                    <tr>
                        <td colspan="2">
                            <div style='float:left;'>
                                <b class="title" style="color: #666666;font-weight: normal;">是否邮件：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_isEmail">
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
                <div class="datas">
                    <table id="tblEmailList">
                    </table>
                </div>
                <table style="margin: 10px 0px 10px 64px;" style="margin-left: 55px;">
                    <tr>
                    <td>
                        <div style='float:left;'>
                            <b class="title" style="color: #666666;font-weight: normal;">是否电话语音：</b>
                        </div>
                        <div class="label2">
                            <label id="lbl_isPhone">
                            </label>
                        </div>
                    </td>
                </table>
                <div class="datas">
                    <table id="tblPhoneList">
                    </table>
                </div>
                <table style="margin: 10px 0px 10px 87px;">
                    <tr>
                        <td colspan="2">
                            <div style='float:left;'>
                                <b class="title" style="color: #666666;font-weight: normal;">规则描述：</b>
                            </div>
                            <div class="label">
                                <label id="lbl_descr">
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
			
            <div id="divNotifyToUsers" title="通知用户" data-options="closable:false" style="overflow: hidden;">
                <h2>通知用户</h2>
                <div class="datas">
                    <table id="tblNotifyToUser">
                    </table>
                </div>
            </div>
        </div>
        <div class="conditionsBtn">
            <input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
        </div>
    </body>
</html>