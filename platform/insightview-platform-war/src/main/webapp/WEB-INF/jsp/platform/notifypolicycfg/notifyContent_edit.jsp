<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
    <head>
    </head>
    <body>
        <input type="hidden" id="editContentFlag" value="${editContentFlag}"/>
		<input type="hidden" id="type" value="${type}"/>
		<input type="hidden" id="contentId" value="${contentId}"/>
        <c:if test="${type == 1}">
            <div id="divContentEdit">
                <table id="tblSmsContentEdit" class="formtable" style="width: 660px;">
                    <tr>
                        <td class="title">
                            模板名称：
                        </td>
                        <td colspan="3">
                            <input id="ipt_name" validator="{'default':'*','length':'1-100'}"/><b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            模板内容：
                        </td>
                        <td colspan="3">
                            <textarea rows="3" class="x2" id="ipt_content" validator="{'default':'*','length':'1-2000'}"></textarea><b>*</b>
                        </td>
                    </tr>
                </table>
            </div>
        </c:if>
        <c:if test="${type == 2}">
            <div id="divContentEdit" style="overflow:hidden;height:200px;">
                <table id="tblEmailContentEdit" class="formtable" style="width: 660px;">
                    <tr>
                        <td class="title">
                            模板名称：
                        </td>
                        <td colspan="3">
                            <input id="ipt_name" validator="{'default':'*','length':'1-100'}"/><b>*</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">
                            模板内容：
                        </td>
                        <td colspan="3">
                            <textarea rows="3" class="x2" id="ipt_content"/><b style="float: left;margin-top: -15%;margin-left: 91%;">*</b>
                        </td>
                    </tr>
                </table>
            </div>
        </c:if>
        <c:if test="${type == 3}">
            <div id="divContentEdit">
                <table id="tblPhoneContentEdit" class="formtable" style="width: 660px;">
                    <tr id="nameTr">
                        <td class="title">
                            模板名称：
                        </td>
                        <td>
                            <input id="ipt_name" validator="{'default':'*','length':'1-100'}"/><b>*</b>
                        </td>
                        <td class="title">
                            语音通知类型：
                        </td>
                        <td>
                            <input id="ipt_voiceMessageType" type="hidden"/>
							<input type="radio" name="voiceMessageType" onclick="javascript:editVoice()" value="1" checked/>&nbsp;文字模板 &nbsp;
							<input type="radio" name="voiceMessageType" onclick="javascript:editVoice()" value="2"/>&nbsp;录音
                        </td>
                    </tr>
                    <tr id="textRr">
                        <td class="title">
                            文字模板内容：
                        </td>
                        <td colspan="3">
                            <textarea rows="3" class="x2" id="ipt_content" validator="{'default':'*','length':'1-2000'}"></textarea><b>*</b>
                        </td>
                    </tr>
                    <tr id="voiceRr">
                        <td class="title">
                            电话通知录音：
                        </td>
                        <td>
                            <select id="ipt_voiceId">
                                <option value="-1">请选择...</option>
                                <c:forEach items="${voiceList}" var="vo">
                                    <option value="<c:out value='${vo.id}' />"><c:out value="${vo.name}" /></option>
                                </c:forEach>
                            </select><b>*</b>
                        </td>
                    </tr>
                </table>
            </div>
        </c:if>
		
		<div class="conditionsBtn">
			<input type="button" id="btnSave" value="确定" onclick="javascript:toEditContent();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin2').window('close');"/>
		</div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/notifypolicycfg/notifyContent_edit.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-richtext.min.js">
        </script>
        <script type="text/javascript">
            var type = $("#type").val();
            if (type == 2) {
                f('#ipt_content').richtext({
                    height: 80
                });
            }
            else if (type == 3) {
                $('#ipt_voiceId').combobox({
                    panelWidth: '180',
                    editable: false,
                });
                editVoice();
            }
			var editContentFlag = $("#editContentFlag").val();
			var contentId = $("#contentId").val();
		    if (editContentFlag == "update") {
		        initContent(type,contentId);
				var typeId = $("#ipt_policyType").combobox("getValue");
	    		if (typeId != -1) {
					$("#ipt_name").attr("disabled", 'true');
				}else{
					$("#ipt_name").removeAttr("disabled");
				}
		    } 
        </script>
    </body>
</html>
