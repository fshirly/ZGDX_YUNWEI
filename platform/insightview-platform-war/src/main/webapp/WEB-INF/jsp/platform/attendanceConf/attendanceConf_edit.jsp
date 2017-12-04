<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  
  <body>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
      <div id="attendanceConfInfoTabsedit"  style="height:520px;overflow-y:auto;">
       <div title="签到计划"  style="overflow-Y:auto;">
         <div id="attendancePlanConfedit" ></div>
       </div>
       	<div title="人员配置"  >
		  <div id="attendanceConfpeopleeditlist"  ></div>
		</div>
      </div>
      <script type="text/javascript">
      var attendanceId='${attendanceId}';
      var index='${index}';
       var uri=getRootName();
       $('#attendancePlanConfedit').panel({
    	   href:uri+'/attendanceConf/attendancePlanConf_edit?attendanceId='+attendanceId
        });
       $('#attendanceConfpeopleeditlist').panel({
    	   href:uri+'/attendanceConf/attendancePeopleConf_list?attendanceId='+attendanceId
       });
       $('#attendanceConfInfoTabsedit').tabs({
    	   onSelect:function(title,index){
             if(index==0){
            	 $('#attendancePlanConfedit').panel('refresh',uri+'/attendanceConf/attendancePlanConf_edit?attendanceId='+attendanceId);
             }else if(index==1){
            	 $('#attendanceConfpeopleeditlist').panel('refresh',uri+'/attendanceConf/attendancePeopleConf_list?attendanceId='+attendanceId);  
             }
    	   }
       });
       if(index=='1'){
    	   $('#attendanceConfInfoTabsedit').tabs('select',1);
       }else{
    	   $('#attendanceConfInfoTabsedit').tabs('select',0);
       }
      </script>
  </body>
</html>
