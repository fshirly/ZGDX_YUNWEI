<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>签到设置</title>
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/resassettype.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendanceConf/attendanceConf_list.js"></script>

  </head>
  
  <body>
    <div class="location">当前位置：${navigationBar }</div>
     <!-- 查询页 -->
     <div class="conditions" id="divFilter">
     <table>
      <tr>
        <td >
          <b>签到日期（起）:</b>
          <input class="easyui-datebox" id="attStartTime_start" name="attStartTime_start" /> 
		  - <input class="easyui-datebox" id="attStartTime_end" name="attStartTime_end" />
        </td>
        <td>
          <b>标题:</b>
          <input  type="text" id="title" />
        </td>
      </tr>
      <tr>
         <td>
          <b>签到日期（止）:</b>
          <input class="easyui-datebox" id="attEndTime_start" name="attEndTime_start" /> 
		  - <input class="easyui-datebox" id="attEndTime_end" name="attEndTime_end" />
         </td>
         <td class="btntd">
               <a href="javascript:void(0);" onclick="platform.attendanceConf.attendanceConfList.reloadTable()">查询</a>
               <a href="javascript:void(0)" onclick="resetForm('divFilter')">重置</a>
            </td>
      </tr>
     </table>
     </div>
      <!-- 表格页 -->
     <div class='datas tops2' >
        <table id="attendanceConflist"></table>
     </div>
  </body>
</html>
