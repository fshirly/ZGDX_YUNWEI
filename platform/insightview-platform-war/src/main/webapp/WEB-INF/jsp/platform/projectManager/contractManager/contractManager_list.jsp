<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head> 
    <title>合同管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/resassettype.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/projectManager/contractManager/contractManager_list.js"></script>
  </head>
  
  <body>
     <div class="location">当前位置：${navigationBar }</div>
     <!-- 查询页 -->
     <div class="conditions" id="divFilter">
     <table>
       <tr>
	        <td>
	          <b>合同标题：</b>
	          <input type="text"  id="contractTitle"/>
	        </td>
	        <td colspan="2">
	          <b>签订日期：</b>
	          <input class="easyui-datebox" id="timeBegin" name="timeBegin" data-options="editable: false"/> 
			  - <input class="easyui-datebox" id="timeEnd" name="timeEnd" data-options="editable: false"/>
			</td>
       </tr>
       <tr>
            <td>
		        <b>甲方：</b>
		        <input type="text" id='jiafang'/>
            </td>
            <td>
                <b>乙方：</b>
                <input type="text" id='yifang' />
            </td>
            <td class="btntd">
               <a href="javascript:void(0);" onclick="platform.contractManager.contractManagerList.reloadTable()">查询</a>
               <a href="javascript:void(0)" onclick="resetForm('divFilter')">重置</a>
            </td>
       </tr>
     </table>
     </div>
      <!-- 表格页 -->
     <div class='datas tops2' >
        <table id="contractManager"></table>
     </div>
  </body>
</html>
