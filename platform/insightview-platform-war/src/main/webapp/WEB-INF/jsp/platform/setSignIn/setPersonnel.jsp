<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../plugin/easyui/themes/default/easyui.css" />
    <script type="text/javascript">
      var attendanceId='${attendanceId}';
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script> 
   <div class="datas tops1" >
    <div style="width:800px;height:465px;overflow-x:hidden">
     <table id="personnelTable"></table>
     </div>
  </div>
   <div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnattdancepeopleDetailclose" class="fltrt" onclick="javascript:parent.$('#popWin').dialog('close');">关闭</a>
	  </div>
  <script src="${pageContext.request.contextPath}/js/platform/setSignIn/setPersonnel.js"></script>
</body>
</html>