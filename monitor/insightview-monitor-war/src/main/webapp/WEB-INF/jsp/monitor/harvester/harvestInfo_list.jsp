<%@ page language="java"  pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <link rel="stylesheet" type="text/css" 
	href="${pageContext.request.contextPath}/style/monitor/harvester/harvestInfo_list.css"/>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/harvester/harvestInfo_list.js"></script>
	</head>
  <body>
  
	  	<div class="location">
			当前位置：${navigationBar}
		</div>
		
		<div class="easyui-tabs" data-options="fit:true,plain:true" id="serverHostConfigTabs" style="height:450px;">
			<div id="divHarvestInfo" title="运维主机" data-options="closable:false"
					style="overflow: scroll;">
			<div id="harvestInfo">
			</div>
			</div>
			
			<div id="divHarvestTaskInfo" title="运维服务" data-options="closable:false"
					style="overflow: scroll;">
			<div id="harvestTaskInfo">

			</div>
			</div>
			
			<div id="divDomainSetting" title="采集机所辖管理域" data-options="closable:false"
					style="overflow: auto;">
				<div id="dataTreeDiv" class="treetable" style="top: 77px;"></div>
				<div class="treetabler">
					<div class="datas tops1">
						<table id="tblDomain"></table>
					</div>
				</div>
			</div>
		
		</div><!--
		  <div class="conditions" id="divFilter">
		    	<p class="conditionOne">
		    	<input type="radio" name="item"  checked="checked" onclick="harvest('harinfo')"/>采集机视角    
		        <input type="radio" name="item"  onclick="harvest('harTaskinfo')"/>采集任务视角    
		    	</p>
		    </div>
		   
		    <div class="datas" id="harvestInfo">
				<table id="tblHarvester">
		
				</table>
			</div>
			 <div class="datas" id="harvestTaskInfo" style="display:none;width:100%;">
				<table id="tblHarvestTask">
				</table>
			</div>-->
			
			<div id="divWarning" class="easyui-window" collapsible="false" minimizable="false" maximized="false"
				closed="true" modal="true" title="提示"
				style="width: 300px; height: 150px;">
				<br/>
				正在重启，请稍等。。。
			</div>
  </body>
</html>
