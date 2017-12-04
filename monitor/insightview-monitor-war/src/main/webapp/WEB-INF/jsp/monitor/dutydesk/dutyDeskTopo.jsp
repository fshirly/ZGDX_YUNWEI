<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/dutydesk/dutyDeskTopo.js">
        </script>
    </head>
    <body>
    	<input id="defaultTopoId" value="${defaultTopoId}" type="hidden"/>
        <div id="divTopo">
            <div class="conditions" id="divFilter">
                <table>
					<td>
						<b>拓扑名称：</b>
						<select id="ipt_topos" class="easyui-combobox">
						    <c:forEach items="${topoList}" var="vo">
							<option value="<c:out value='${vo.id}' />" ><c:out value="${vo.topoName}" /></option>	
							</c:forEach>
						</select>
						<td></td>
						<td></td>
						<td><a onclick="toTopoView();" style="cursor: pointer;font-weight: bold;">查看大图</a></td>
					</td>
                </table>
            </div>
            <div id="topoViewDiv">
                <iframe id="component_2" name="component_2" src="${pageContext.request.contextPath}/topo/toTopoMini?topoId=${defaultTopoId}" frameborder="0" style="height: 100%;">
                <!--<iframe id="component_2" name="component_2" src="http://192.168.20.167:8088/insightview/topo/toTopoMini?topoId=${defaultTopoId}" frameborder="0" style="height: 100%;">-->
                </iframe>
            </div>
        </div>
        <script>
            (function(){
                var parent_height = $("#divTopo").closest("body").height();
                $("#divTopo").css("height", parent_height + "px");
                $("#topoViewDiv").css("height", parent_height * 0.9 + "px");
                $(window).resize(function(){
                    //  $('#divTopo').resizeDataGrid(0, 0, 0, 0);
                    var parent_height = $("#divTopo").closest("body").height();
                    $("#divTopo").css("height", parent_height + "px");
                    $("#topoViewDiv").css("height", parent_height * 0.9 + "px");
                    $(".panel-body-noheader.panel-body-noborder").css("width", "100%");
                });
            })()
        </script>
    </body>
</html>
