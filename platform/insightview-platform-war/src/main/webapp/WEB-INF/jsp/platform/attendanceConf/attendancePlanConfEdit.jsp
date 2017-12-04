<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <body>
       <fmt:formatDate value="${attendancePlanConf.attStartTime}" pattern="yyyy-MM-dd"  var="attStartTime"/>
       <fmt:formatDate value="${attendancePlanConf.attEndTime}" pattern="yyyy-MM-dd"  var="attEndTime"/>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
     <script type="text/javascript">
       var attendanceId='${attendancePlanConf.attendanceId}';
     </script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendanceConf/attendanceConf_edit.js"></script>
     <div>
      <table id="editattendancePlanConf_edit" class="formtable">
      <tr>
        <td class="title">标题：</td>
        <td colspan="3">
           <input type="hidden" value="${attendancePlanConf.attendanceId}" id="plan_attendance_id"/>
           <input type="hidden" value="${attendancePlanConf.planner}" id="plan_attendance_planner"/>
           <input type="hidden" value="${attendancePlanConf.status}" id="plan_attendance_status"/>
	       <input type="text" style=" width:94%" id="title" name="title" value="${attendancePlanConf.title}"   validator="{'default':'*','length':'1-50'}" msg="{reg:'只能输入1-50位字符！'}" /><dfn>*</dfn>
	     </td>
      </tr>
      <tr>
        <td class="title">签到日期（起）：</td>
        <td><input  id="attStartTime" class="easyui-datebox" name="attStartTime" value="${attStartTime}" validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn>
        </td>
        <td class="title">签到日期（止）：</td>
        <td><input  id="attEndTime" class="easyui-datebox" name="attEndTime" value="${attEndTime}" validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn>
      </tr>
      <tr>
        <td class="title">设置人：</td>
        <td>
        <input type="text" style="background-color:#E5E5E5" disabled="disabled" value="${attendancePlanConf.plannerName}"/>
        </td>
        <td class="title">状态：</td>
        <td><input id="ipt_status" style="background-color:#E5E5E5" disabled="disabled" value="${attendancePlanConf.statename}"/></td>
      </tr>
      </table> 
      <div id='editattendancePlanConf_edit' class="easyui-panel" data-options="title:'签到时间段（最多可设置8个时间段）'" >
      <div class='datas' style="height: 300px; width: 100%;">
          <table  id="attendancePlanConfTimetable_edit"></table>
       </div>
      </div>
      <div class="conditionsBtn">
					<a href="javascript:void(0);" id="attPlanConfedit" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="attPlanConfeditresert" onclick="javascript:parent.$('#popWin').window('close')" class="fltrt">取消</a>
		</div>
    </div>
  </body>
</html>
