<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>listview预览</title>
<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	//var previewData = window.opener.previewData;
	var previewData = getJsonObj("previewData");
	
	$("#preview111").listview({
		//listviewName : 'listviewdefault',
		//exportExcel : true,
		//initParams : {"name" : "123" , "aa" : "xxx" , "bb" : "yyy"},
		/* frozenColumns : [[
							{field:'id',checkbox:true},
							//{field:'name',width:100}  
		                ]],
		columns : [[
			        
			        //{field:'name',width:100},
			        {field:'title',width:300},
			        {field:'note',width:300},
			        {field:'name',width:300}
			      ]], */
		fitColumns : true,
		//singleSelect : true,
		//autoRowHeight : false,
		striped : true,
		nowrap : false,
		//pagination : true,
		rownumbers : true,
		fit : true,
		//resizeHandle : 'both',
		pagePosition : 'bottom',
		//scrollbarSize : 10 ,
		title : '预览',
		//height : 400,
		//width : '98%',
		preview : true,
		previewData : previewData
	});
});

</script>

</head>

<body>
	<div id="preview111"></div>
	<div id="colon"></div>  

</body>
</html>