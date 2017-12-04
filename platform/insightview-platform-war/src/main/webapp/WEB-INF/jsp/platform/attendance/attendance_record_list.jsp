<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head> 
    <title>人员签到</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/resassettype.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendance/attendance_record_list.js"></script>
  </head>
  
  <body>
     <div class="location">当前位置：${navigationBar }</div>
     
     <div id="currAttRecordInfoTabs" class="easyui-tabs" style="height:520px;overflow-y:auto;">
		<div title="签到 " data-options="closable:false" style="overflow-Y:auto;">
		    <div style="width: 220px;float: left;line-height: 56px;margin-left: 60px;height: 56px;"><input type="hidden" id="nowTime_hidden"/>
		    	<strong id="nowTime"></strong>
		     </div>
		    <div style="  margin-left: 80px;float: left;line-height: 56px;">您有<strong><span id="unchkCount">0</span></strong>处要进行签到，请选择本次要签到的任务计划：<select id="currAttPlanConf" name="currAttPlanConf"></select>   
		    </div>
		    
		    <div style="clear:both;text-align:center;">
		        <img src="${pageContext.request.contextPath}/style/images/u21.png"/>
		        </div>
		    <div class='datas' style="top:220px;" >
			   <table id="tblCurAttRecordInfo" class="tableinfo" ></table>
			</div>
		</div>
		<div title="签到记录" data-options="closable:false" >
		  <div class="conditions">
		     <table>
		       <tr>
		         <td>
		           <b>签到计划：</b>
		           <select id="signPlan">
		           </select>
		         </td>
		         <td>
		           <b>签到日期：</b>
		           <input id="startTime" class="easyui-datebox"/>-<input id="endTime" class="easyui-datebox"/>
		         </td>
		         <td class="btntd">
					<a href="javascript:void(0);" id="reloadTable" >查询</a>
					<a href="javascript:void(0);" id="resetButton"">重置</a>
				</td>
		       </tr>
		     </table>
		  </div>
		  <div class='datas' style="top:105px;" >
		    <table id="tblHisAttRecordInfo"></table>
		  </div>
		</div>
		
	  </div>
     
  </body>
</html>
