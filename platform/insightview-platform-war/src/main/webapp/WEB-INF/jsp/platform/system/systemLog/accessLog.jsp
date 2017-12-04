<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
  <head>
    <title>系统访问日志</title>
  	<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
  </head>
  <body>
	  <div id="accessLogGrid"></div>
	  
	  <iframe id="download" src="" width="0" height="0" style="width: 0px; height: 0px; display: none"></iframe>
	  
	  <script type="text/javascript">
	    	$(function(){
	    		initAccessLogGrid();
	    	});
	    	
	    	function initAccessLogGrid(){
	      		$('#accessLogGrid').listview({
	    			listviewName : "accessLog",
	    			frozenColumns : [[{field:'id',hidden:true}]],
	    			columns : [[
			            {field:'ck', checkbox:true},
				        {field:'username',width:'8%',align:'center'},
				        {field:'ip',width:'9%',align:'center'},
				        {field:'moduleName',width:'20%',align:'center'},
				        {field:'url',width:'20%'},
				        {field:'methodName',width:'29%'},
				        {field:'createdTime',width:'11%',align:'center',formatter:function(value,row,index){
			        		if(!value){
			        			return;
			        		}
			        		return formatDate(new Date(value));
				        }}
	    			]],
	    			exportExcel : true,
	    			fitColumns : true,
	    			pagination : true,
	    			rownumbers : true,
	    			fit:true
	    		});
	    	}
	  </script>
  </body>
</html>