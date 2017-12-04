<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <body>
       
       <script type="text/javascript">
          var attendanceId='${attendancePlanConf.attendanceId}';
       </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendanceConf/attendanceConf_Detail.js"></script>
    <fmt:formatDate value="${attendancePlanConf.attStartTime}" pattern="yyyy-MM-dd"  var="temp_attStartTime"/>
    <fmt:formatDate value="${attendancePlanConf.attEndTime}" pattern="yyyy-MM-dd"  var="temp_attEndTime"/>
    <div>
     <table id="tblattendanceplanInfo" class="tableinfo">
     <tr>
          <td colspan="2">
            <b class="title" style="float:left;">标题：</b>
            <label class="input">${attendancePlanConf.title}</label>
          </td>
     </tr>
     <tr>
         <td>
            <b class="title">设置人：</b><label class="input">${attendancePlanConf.plannerName}</label>
          </td>
          <td >
             <b class="title">状态：</b><label class="input">${attendancePlanConf.statename}</label>
          </td>
     </tr>
     <tr>
          <td>
            <b class="title">签到日期（起）：</b><label class="input">${temp_attStartTime}</label>
          </td>
          <td >
             <b class="title">签到日期（止）：</b><label class="input">${temp_attEndTime}</label>
          </td>
     </tr>
     </table>
      <div id='editattendancePlanConf_Detail' class="easyui-panel" data-options="title:'签到时间段'" >
      <div class='datas' style="height: 300px; width:100%;">
          <table id="attendancePlanConfTimetable_Detail"></table>
       </div>
      </div>
      <div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnattdanceplanDetailclose" class="fltrt" onclick="javascript:parent.$('#popWin').dialog('close');">关闭</a>
	  </div>
    </div>
  </body>
</html>
