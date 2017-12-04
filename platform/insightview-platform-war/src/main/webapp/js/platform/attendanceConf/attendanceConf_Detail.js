f.namespace('platform.attendanceConf');
platform.attendanceConf.attendanceConfDetail = (function(){
	  $(function() {
			var path = getRootName();
			doInitTable();//合同信息列表初始化
		});
	  function doInitTable(){
		  	var path = getRootName();
		  	var uri=path+'/attendanceConf/attendancePeriodConflist?attendanceId='+attendanceId;
		  	$('#attendancePlanConfTimetable_Detail').datagrid({
		  		width : 'auto',
		  		height : 'auto',
		  	    nowrap : true,
		  		striped : true,
		  		border : true,
		  		collapsible : false,// 是否可折叠的
		  		fit : true,// 自动大小
		  		fitColumns:true,
		  		url : uri,
		  		remoteSort : true,					
		  		idField : 'attendanceId',
		  		singleSelect : false,// 是否单选
		  		checkOnSelect : false,
		  		selectOnCheck : true,
		  		rownumbers : true,// 行号
		  		columns : [ [
		  		  {
		  			 field : 'startTime',
		  		     title : '开始时间',
		  			 width : 120,
		  			 align:'center'
		  			 /*formatter:function(value, row, index){
		  			   if(value!=null && value!="" ){
		  				   return formatDate(new Date(value), "hh:mm");
		  			   }
		  		     }*/
		  		   },{
		  			 field : 'endTime',
		  			 title : '结束时间',
		  		     width : 120, 
		  		     align:'center'
		  		     /*formatter:function(value, row, index){
		  			   if(value!=null && value!="" ){
		  				   return formatDate(new Date(value), "hh:mm");
		  			   }
		  		     }*/
		  		     
		  		   }
		  	    ]]
		  		
		  	});
		  	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
		  	$(window).resize(function() {
		  		$('#attendancePlanConfTimetable_Detail').resizeDataGrid(0, 0, 0, 0);
		  	});
		  }
	
})();