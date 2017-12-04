<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
  <body>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendanceConf/attendanceConf_add.js"></script>
     <div>
      <table id="editattendancePlanConf" class="formtable">
      <tr>
        <td class="title">标题：</td>
        <td colspan="3">
	       <input type="text" style=" width:94%" id="title" name="title"   validator="{'default':'*','length':'1-50'}" msg="{reg:'只能输入1-50位字符！'}" /><dfn>*</dfn>
	     </td>
      </tr>
      <tr>
        <td class="title">签到日期（起）：</td>
        <td><input  id="attStartTime" class="easyui-datebox" name="attStartTime" validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn>
        </td>
        <td class="title">签到日期（止）：</td>
        <td><input  id="attEndTime" class="easyui-datebox" name="attEndTime" validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn>
      </tr>
      <tr>
        <td class="title">设置人：</td>
        <td>
        <input type="text" disabled="disabled" value="${sessionScope.sysUserInfoBeanOfSession.userName}"/>
        <input id="planner" name="planner" type="hidden" value="${sessionScope.sysUserInfoBeanOfSession.id}"/>
        </td>
        <td class="title">状态：</td>
        <td><input id="ipt_status" disabled="disabled" value="未启用"></input></td>
      </tr>
      </table> 
      <div id='attendancePlanConfTime' class="easyui-panel" data-options="title:'签到时间段（最多可设置8个时间段）'" >
      <div class='datas' style="height: 300px; width: 100%;">
          <table id="attendancePlanConfTimetable"></table>
       </div>
      </div>
      <div class="conditionsBtn">
					<a href="javascript:void(0);" id="attPlanConfAdd" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="attPlanConfAddresert" onclick="javascript:parent.$('#popWin').window('close')" class="fltrt">取消</a>
		</div>
    </div>
  </body>
</html>
