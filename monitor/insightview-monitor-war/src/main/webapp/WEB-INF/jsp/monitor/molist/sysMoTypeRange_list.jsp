<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script> 	
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script> 
  <%
  	String moClassID = (String)request.getAttribute("moClassID");
   %>
  	</head>
  
  <body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script> 
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTypeRangeList.js"></script>
    <style>
      .rightContent{height:100%;}
      #initRange{padding-top:20%;text-align:center;}
      .btSave{
        width: 18%;
        margin: auto;
        height: 50px;
        background: rgb(113, 192, 240);
        border: 1px double rgb(43, 26, 192);
        border-radius: 4px;
        line-height: 50px;
        font-size: large;
        margin-top: 4%;
        cursor: pointer;
      }
    </style>
	  <input id="moClassID" value="<%=moClassID %>" type="hidden"/>
	  <div class="rightContent">
			<div id="rangeLst" class="easyui-window" 
				 	minimizable="false"  resizable="false" maximizable="false" closed="false" closable="false"  
		 			collapsible="false" modal="true" title="配置监测器类型"
					style="width: 800px;height: 450px;top:0px;">
				<div class="datas">
				<table id="tblSysMoTypeRangeList">
				</table>
				</div>
			</div>
			<div id="initRange">
				<b><font size="5">该监测对象类型尚未配置监测器</font></b>
				<br/>
				<!--  <input type="button" id="btnSave" value="开始配置" onclick="javascript:toShowConfig();"/>-->
				<div class="btSave" onclick="javascript:toShowConfig();">开始配置</div>
			</div>
	 </div>
  </body>
</html>