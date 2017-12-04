<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/dutydesk/dutyDeskRoom3D.js">
        </script>
    </head>
    <body>
    	<input id="defaultRoomId" value="${defaultRoomId}" type="hidden"/>
        <div id="roomDiv">
            <div class="conditions" id="divFilter">
                <table>
					<td>
						<b>机房名称：</b>
						<select id="ipt_rooms" class="easyui-combobox">
						    <c:forEach items="${roomList}" var="vo">
							<option value="<c:out value='${vo.roomId}' />" ><c:out value="${vo.roomName}" /></option>	
							</c:forEach>
						</select>
						<td></td>
						<td></td>
						<td><a onclick="toRoomView();" style="cursor: pointer;font-weight: bold;">查看大图</a></td>
					</td>
                </table>
            </div>
            <div id="roomViewDiv">
               <iframe id="component_2" name="component_2" src="${pageContext.request.contextPath}/room3dController/room3dDutyDeskView?roomId=${defaultRoomId}&amp;viewType=alarmView&amp;drivceType=null" frameborder="0">
                <!--<iframe id="component_2" name="component_2" src="http://192.168.20.167:8088/insightview/room3dController/room3dHomeView?roomId=${defaultRoomId}&amp;viewType=alarmView&amp;drivceType=null" frameborder="0">-->
                </iframe>
            </div>
        </div>
		
		 <script>
            (function(){
                var parent_height = $("#roomDiv").closest("body").height();
                $("#roomDiv").css("height", parent_height + "px");
                $("#roomViewDiv").css("height", parent_height * 0.9 + "px");
                $(window).resize(function(){
                    //  $('#roomDiv').resizeDataGrid(0, 0, 0, 0);
                    var parent_height = $("#roomDiv").closest("body").height();
                    $("#roomDiv").css("height", parent_height + "px");
                    $("#roomViewDiv").css("height", parent_height * 0.9 + "px");
                    $(".panel-body-noheader.panel-body-noborder").css("width", "100%");
                });
            })()
        </script>
    </body>
</html>
