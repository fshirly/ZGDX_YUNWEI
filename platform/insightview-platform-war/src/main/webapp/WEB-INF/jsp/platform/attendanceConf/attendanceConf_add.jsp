<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <body>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
      <div id="attendanceConfInfoTabs"  style="height:520px;overflow-y:auto;">
       <div title="签到计划"  style="overflow-Y:auto;">
         <div id="attendancePlanConfAdd"  ></div>
       </div>
       	<div title="人员配置">
		  <div id="attendanceConfpeoplelist" class="easyui-panel" style="width:800px;overflow-x:hidden"></div>
		</div>
      </div>
      <script type="text/javascript">
       var uri=getRootName();
       $('#attendancePlanConfAdd').panel({
    	   href:uri+'/attendanceConf/attendancePlanConf_add'
        });
       $('#attendanceConfInfoTabs').tabs();
       $('#attendanceConfInfoTabs').tabs('disableTab',1);
      </script>
     
  </body>
</html>
