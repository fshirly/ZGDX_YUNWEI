<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
  <head>
    <title>系统登录日志</title>
  	<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
  </head>
  <body>
	  <div id="sessionLogGrid"></div>
	  
	  <script type="text/javascript">
	    	$(function(){
	    		initAccessLogGrid();
	    	});
	    	
	    	function initAccessLogGrid(){
	      		$('#sessionLogGrid').listview({
	    			listviewName : "sessionLog",
	    			frozenColumns : [[{field:'id',hidden:true}]],
	    			columns : [[
			            {field:'ck', checkbox:true},
				        {field:'username',width:'10%',align:'center'},
				        {field:'ip',width:'10%',align:'center'},
				        {field:'clientName',width:'10%',align:'center'},
				        {field:'clientAgent',width:'57%',align:'center'},
				        {field:'createdTime',width:'10%',align:'center',formatter:function(value,row,index){
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