<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  
  <body>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
      <div id="attendanceConfInfoTabsDetail"  style="height:520px;overflow-y:auto;">
       <div title="签到计划"  style="overflow-Y:auto;">
         <div id="attendancePlanConfDetail" ></div>
       </div>
       	<div title="人员配置"  >
		  <div id="attendanceConfpeoplelistDetail"  ></div>
		</div>
      </div>
      <script type="text/javascript">
      var attendanceId='${attendanceId}';
       var uri=getRootName();
       $('#attendancePlanConfDetail').panel({
    	   href:uri+'/attendanceConf/attendancePlanConf_detail?attendanceId='+attendanceId
        });
       $('#attendanceConfpeoplelistDetail').panel({
    	   href:uri+'/attendanceConf/attendancePeopleConf_list_detail?attendanceId='+attendanceId
       });
       $('#attendanceConfInfoTabsDetail').tabs();
      </script>
  </body>
</html>
