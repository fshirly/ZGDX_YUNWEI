<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/offlinecollector/offlineCollector_add.js"></script>
		<input id="serverId" type="hidden" value="${hostInfo.serverid}"/>
		<input id="id" type="hidden" value="${id}"/>
		<input id="installServiceId" type="hidden" value="${installServiceId}"/>
		<input id="oldServerId" type="hidden" value="${hostInfo.serverid}"/>
        <div id="addOfflineCollector">
            <table id="tblAddOfflineCollector" class="formtable">
                <tr>
                    <td class="title">
                        采集机IP：
                    </td>
                    <td>
                        <input id="ipt_ipaddress" value="${hostInfo.ipaddress}" validator="{'default':'checkEmpty_ipAddr','length':'1-128'}" onblur="getHostInfo(this)"/><b>*</b>
                    </td>
                    <td class="title">
                        服务名称：
                    </td>
                    <td>
                       <select id="ipt_installServiceId" class="easyui-combobox" validator="{'default':'*','type':'combobox'}" style="width:180px;" data-options="editable:false">
                       		<option value="">请选择</option>
						    <c:forEach items="${serviceInfoLst}" var="vo">
							<option value="<c:out value='${vo.serviceid}' />" <c:if test="${vo.serviceid eq installServiceId}">selected="selected"</c:if> ><c:out value="${vo.servicename}" /></option>	
							</c:forEach>
						</select><b>*</b>
                    </td>
                </tr>
				<tr>
                    <td class="title">
                        采集机名称：
                    </td>
                    <td>
                        <input id="ipt_servername" value="${hostInfo.servername}" validator="{'length':'0-128'}" />
                    </td>
                    <td class="title">
                       采集机NatIP：
                    </td>
                    <td>
                       <input id="ipt_natipadress" value="${hostInfo.natipadress}" validator="{'default':'ipAddr','length':'0-128'}" />
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        备注：
                    </td>
                    <td colspan="3">
                        <textarea rows="3" class="x2" id="ipt_serverdesc" validator="{'length':'0-128'}">${hostInfo.serverdesc}</textarea>
                    </td>
                </tr>
            </table>
            <div class="conditionsBtn">
                <input type="button" id="btnSave" value="确定" onclick="javascript:toEdit();"/><input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
            </div>
        </div>
    </body>
</html>