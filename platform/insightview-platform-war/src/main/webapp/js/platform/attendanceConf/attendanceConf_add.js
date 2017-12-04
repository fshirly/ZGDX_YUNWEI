f.namespace('platform.attendanceConf');
platform.attendanceConf.attendanceConfAdd = (function(){
	var index=0;
	var attendanceConfAdd={
	   doAttendanceConfdelete:function(index){
		 doAttendanceConfdelete(index);
	  }
	};
	$.extend($.fn.datagrid.defaults.editors, {
		timespinner : {
			init : function (container, options) {
				var input = $('<input/>').appendTo(container);
				input.timespinner(options);
				return input
			},
			getValue : function (target) {
				var val = $(target).timespinner('getValue');
				return val;
			},
			setValue : function (target, value) {
				$(target).timespinner('setValue', value);
			},
			resize : function (target, width) {
				var input = $(target);
				if ($.boxModel == true) {
					input.resize('resize', width - (input.outerWidth() - input.width()));
				} else {
					input.resize('resize', width);
				}
			}
		}
	});
	
	$("#attStartTime").datebox({
		editable: false
	});
	$("#attEndTime").datebox({
		editable: false
	});
	
     $('#attPlanConfAdd').click(attplanConf_edit_submit);
	  $(function() {
			var path = getRootName();
			/*$.ajax({
				datdType : "json",
				url: path+'/attendanceConf/attendanceConfztcombox',
				success : function(data){
				$('#ipt_status').combobox({
					valueField : 'id',
					textField : 'neir',
					//url: path+'/attendanceConf/attendanceConfztcombox',
					data:data,
					editable : false,
					value:'1',
					panelWidth:180
				})
			  }
			});*/
			doInitTable();
		});
	  function doInitTable(){
		  	var path = getRootName();
		  	var uri='';
		  	$('#attendancePlanConfTimetable').datagrid({
		  		width : 'auto',
		  		height : 'auto',
		  	    nowrap : true,
		  		striped : true,
		  		border : true,
		  		collapsible : false,// 是否可折叠的
		  		fit : true,// 自动大小
		  		fitColumns:true,
		  		url : uri,
		  		editor:{
		  		  type:'timespinner'
		  	    },
		  		remoteSort : true,					
		  		idField : 'attPeriodId',
		  		singleSelect : false,// 是否单选
		  		checkOnSelect : false,
		  		singleSelect : true,
		  		rownumbers : true,// 行号
		  		toolbar :[{
		  			text : '增加',
		  			iconCls : 'icon-add',
		  			handler : function(){
		  			     toadd_row();
		  			}			
		  		}],
		  		columns : [ [
		  		  {
		  			 field : 'startTime',
		  		     title : '开始时间',
		  			 width : 160,
		  			 align:'center',
		  			editor:{
				  		  type:'timespinner',
				  		  options:{
		  			          separator:":",
		  			          showSeconds:false,
		  			          min:'00:00',
		  			          max:'23:58',
		  			          required:true,
		  			          missingMessage:'值不可为空'
		  		                  }
				  	    }
		  		   },{
		  			 field : 'endTime',
		  			 title : '结束时间',
		  		     width : 160, 
		  		     align:'center',
				     editor:{
					  		type:'timespinner',
					  		 options:{
			  			     separator:":",
			  			     showSeconds:false,
			  			     min:'00:00',
			  			     max:'23:59',
			  			     required:true,
			  			     missingMessage:'值不可为空'
			  		     }
					 }
		  		     
		  		   },{
		  				field : 'ids',
		  				title : '操作',
		  				width : 100,
		  				align : 'center',
		  				formatter : function(value, row, index) {
		      		    return '<a href="javascript:void(0)" style="cursor: pointer;" title="删除" onclick="javascript:platform.attendanceConf.attendanceConfAdd.doAttendanceConfdelete('+
		  						+ index
		  						+')" ><img src="'
		  						+ path
		  						+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
		  			 
		  			}}
		  	    ]],
		  	  onClickCell:function(rowIndex, field, value){
			  		if (!validator_attplanConf_edit_time()) {
		  		   		return;
		  		   	}
		  		  	var $timeEdit = $('#attendancePlanConfTimetable'), count=$timeEdit.datagrid('getRows').length;
		  		  	for (var i = 0; i < count; i++) {
		  		  		if (i === rowIndex) {
		  		  			$timeEdit.datagrid("beginEdit",rowIndex);
		  		  		} else {
		  		  			$timeEdit.datagrid('endEdit', i);
		  		  		}
		  		  	}
		  	    }
		  	});
		  	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
		  	$(window).resize(function() {
		  		$('#attendancePlanConfTimetable').resizeDataGrid(0, 0, 0, 0);
		  	});
		  }
	  /*
	   * 添加行
	   */
	  function toadd_row(){
		 if(validator_attplanConf_edit_time()){
			 var count=$('#attendancePlanConfTimetable').datagrid('getRows').length;
			 if(count<8){
			 var  id=index+1;
			 index=index+1;
			 var index_select=$('#attendancePlanConfTimetable').datagrid("getSelected");
			 /*if(index_select!=null){
				 var index_1=$('#attendancePlanConfTimetable').datagrid("getRowIndex",index_select);
					 $('#attendancePlanConfTimetable').datagrid('insertRow',{index: index_1+1,row:{ 
						  startTime:'', 
						  endTime:'', 
						  attPeriodId:id}});	
			 }else{*/
				 $('#attendancePlanConfTimetable').datagrid('appendRow',{ 
					  startTime:'', 
					  endTime:'', 
					  attPeriodId:id});
			 //}
			  $('#attendancePlanConfTimetable').datagrid('selectRecord',id);
			  var row=$('#attendancePlanConfTimetable').datagrid('getRowIndex',id);
			  $('#attendancePlanConfTimetable').datagrid("beginEdit",row);
			 }else{
				 $.messager.alert("提示", "签到时间段最多8个！", "info");
			 }
		 }
		 
	  }
	  /*
	   * 时间段效验,不可为空
	   */
	  function validator_attplanConf_edit_time(){
		 var count=$('#attendancePlanConfTimetable').datagrid('getRows').length;
		 var flag=true;
		  for(var i=0;i<count;i++){
			   flag=$('#attendancePlanConfTimetable').datagrid('validateRow', i);
			   if(!flag){
				   $.messager.alert("提示", "签到时间段不可为空值！", "info");
				   return flag;
			   }
			   $('#attendancePlanConfTimetable').datagrid('endEdit', i);
		  }
		  flag=validator_attplanConf_time_2();
		  return flag;
	  }
	 
	  /*
	   * 判断值是否正确（顺序添加，不能交叉，该方法已废弃）
	   */
	  function validator_attplanConf_edit_time_1(){
		  var rows=$('#attendancePlanConfTimetable').datagrid('getRows');
		  var flag=true;
		  var count=rows.length;
		  for(var i=0;i<count;i++){
			  var time_start=rows[i].startTime.split(":");
		      var time_end=rows[i].endTime.split(":");
			 if(i==0){
				 if(parseInt(time_start[0])<parseInt(time_end[0])){
				 }else if(parseInt(time_start[0])==parseInt(time_end[0])){
					 if(parseInt(time_start[1])>=parseInt(time_end[1])){
						 tooltip_fun(true);
						 $('#attendancePlanConfTimetable').datagrid("beginEdit",i);
						 flag=false;
						 break;
					 }
				 }else if(parseInt(time_start[0])>parseInt(time_end[0])){
					 tooltip_fun(true);
					 $('#attendancePlanConfTimetable').datagrid('beginEdit', i);
					 flag=false;
					 break;
				 }
			 }else{
				 var lasttime_end=rows[i-1].endTime.split(":");
				 if(parseInt(time_start[0])>parseInt(lasttime_end[0])){
					 if(parseInt(time_start[0])<parseInt(time_end[0])){
						 
					 }else if(parseInt(time_start[0])==parseInt(time_end[0])){
						 if(parseInt(time_start[1])>=parseInt(time_end[1])){
							 tooltip_fun(true);
							 $('#attendancePlanConfTimetable').datagrid("beginEdit",i);
							 flag=false;
							 break;
						 }
					 }else if(parseInt(time_start[0])>parseInt(time_end[0])){
						 tooltip_fun(true);
						 $('#attendancePlanConfTimetable').datagrid('beginEdit', i);
						 flag=false;
						 break;
					 }
					 
				 }else if(parseInt(time_start[0])==parseInt(lasttime_end[0])){
					 if(parseInt(time_start[1])>=parseInt(lasttime_end[1])){
						 if(parseInt(time_start[0])<parseInt(time_end[0])){
							
						 }else if(parseInt(time_start[0])==parseInt(time_end[0])){
							 if(parseInt(time_start[1])>=parseInt(time_end[1])){
								 tooltip_fun(true);
								 $('#attendancePlanConfTimetable').datagrid("beginEdit",i);
								 flag=false;
								 break;
							 }
						 }else if(parseInt(time_start[0])>parseInt(time_end[0])){
							 tooltip_fun(true);
							 $('#attendancePlanConfTimetable').datagrid('beginEdit', i);
							 flag=false;
							 break;
						 }
						 
					 }else{
						 tooltip_fun(false);
						 $('#attendancePlanConfTimetable').datagrid('beginEdit', i);
						 flag=false;
						 break;
					 }
				 }else if(parseInt(time_start[0])<parseInt(lasttime_end[0])){
					 $('#attendancePlanConfTimetable').datagrid('beginEdit', i);
					 tooltip_fun(false);
					 flag=false;
					 break;
				 }
			 }
		  }
		  return flag;
	  }
	  /*
	   * 提示
	   */
	  function tooltip_fun(a){
		  if(a){
			  $.messager.alert("提示", "开始时间必须小于结束时间！", "info");
		  }else{
			  //$.messager.alert("提示", "开始时间必须不小于上一次结束时间！", "info");
			$.messager.alert("提示", "签到时间段之间不能交叉或者包含！", "info");
		  }
	  }
	  /*
	   * 删除一行
	   */
	  function doAttendanceConfdelete(index){
		  $('#attendancePlanConfTimetable').datagrid('deleteRow', index);
	  }
	  /*
	   * 添加信息
	   */
	  function attplanConf_edit_submit(){
		  if(validator_attplanConf_edit_time()&&checkInfo("#editattendancePlanConf")&&valiudatorTime()&&validator_attplanConf_time_2()){	
			  validatorAttStartTime();
		  }
	  }
	  
	  /*
	   * 校验时间
	   */
	  function  valiudatorTime(){
		  var flag=true;
		  var attStartTime_date=new Date($("#attStartTime").datebox("getValue").split("-")[0],$("#attStartTime").datebox("getValue").split("-")[1],$("#attStartTime").datebox("getValue").split("-")[2]);
		  var attEndTime_date=new Date($("#attEndTime").datebox("getValue").split("-")[0],$("#attEndTime").datebox("getValue").split("-")[1],$("#attEndTime").datebox("getValue").split("-")[2]);
		  var temp_today=new Date();
		  var today=new Date(temp_today.getFullYear(),temp_today.getMonth()+1,temp_today.getDate());
		  if(attEndTime_date<attStartTime_date){
			  $.messager.alert("提示", "签到截止日期不应该早于起始日期！", "info");
			  flag=false;
		  }else if(attEndTime_date<today||attStartTime_date<today){
			  $.messager.alert("提示", "签到起始日期不得早于当前日期！", "info");
			  flag=false;
		  }else{
			  if(attEndTime_date>attStartTime_date ||$("#attStartTime").datebox("getValue")==$("#attEndTime").datebox("getValue")){
				  flag=true;
			  }else{
				  flag=false;
			  }
		  }
		 
		  return flag;
	  }
	  /*
	   * 签到日期开始时间校验（起）
	   */
	  function validatorAttStartTime(){
		  validatorTitleDouble();
		 /* var flag=true
		  var path = getRootName();
		  var uri =path+"/attendanceConf/validatorattStartTime";
		  var data={
		     attStartTime:$("#attStartTime").datebox("getValue"),
		     attEndTime:$("#attEndTime").datebox("getValue"),
		     "t" : Math.random()
		  }
		  var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data :data,
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true==data || "true" == data) {
							addattplanConfInfo();
						}else{
							$.messager.alert("提示", "签到日期已设定,不可重复！", "error");
						}
	
					}
				};
			   ajax_(ajax_param);*/
	  }
	  /*
	   * 添加信息
	   */
	  function addattplanConfInfo(){
			  var array_push=$('#attendancePlanConfTimetable').datagrid("getData").rows;
			  var path = getRootName();
			  var uri =path+"/attendanceConf/attendancePlanConfInfo_add";
			  var data={
			     title:$("#title").val(),
			     attStartTime:$("#attStartTime").datebox("getValue"),
			     attEndTime:$("#attEndTime").datebox("getValue"),
			     planner:$("#planner").val(),
			     status:1,
			     plantimes:JSON.stringify(array_push),
			     "t" : Math.random()
			  }
			  var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data :data,
						error : function() {
							$.messager.alert("错误", "ajax_error", "error");
						},
						success : function(data) {
							if (data!=0 && data!=null) {
							 window.frames['component_2'].platform.attendanceConf.attendanceConfList.reloadTable();
							  $.messager.confirm("提示", "是否进入人员配置？", function(r) {
								  if(r==true){
									/* var peopleurl=path+'/attendanceConf/attendancePeopleConf_list?attendanceId='+data;
									 parent.$('#attendanceConfInfoTabs').tabs('disableTab',0);
									 parent.$('#attendanceConfInfoTabs').tabs('enableTab',1);
									 parent.$('#attendanceConfInfoTabs').tabs('select',1);
									 parent.$('#attendanceConfpeoplelist').panel('open').panel('refresh',peopleurl);*/
									 var url=path+'/attendanceConf/attendanceConf_edit?attendanceId='+data+"&index=1";
									 parent.$('#popWin').window('refresh',url);
									 
								  }else{
									parent.$('#popWin').window('close');
								  }
								});
								
							} else {
								$.messager.alert("提示", "签到信息添加失败！", "error");
							}
		
						}
					};
				   ajax_(ajax_param);
		  }
	  /*
	   * 判断标题是否重复
	   */
	  function validatorTitleDouble(){
		  var path = getRootName();
		  var uri =path+"/attendanceConf/attendanceConfvalidatortitle";
		  var data={
			title:$("#title").val()
           }
           var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data :data,
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							$.messager.alert("提示", "签到信息标题不能重复！", "error");
						}else{
							 addattplanConfInfo();
						}
	
					}
		};
	    ajax_(ajax_param);
	  }
	  //------------------------------------时间段判断改进------------------------------------------//
	  /*
	   * 判断时间段是否有交叉(前台)
	   */
	  function validator_attplanConf_time_2(){
		  var flag=true;
		  var rows=$('#attendancePlanConfTimetable').datagrid('getRows');
		  var date={};
		  var push_date=[];
		  var count=rows.length;
		  //判断结束时间大于开始时间
		  for(var i=0;i<count;i++){
			  var time_start=rows[i].startTime.split(":");
		      var time_end=rows[i].endTime.split(":"); 
		      if(parseInt(time_start[0])>parseInt(time_end[0])){
		    	  tooltip_fun(true);
		    	  $('#attendancePlanConfTimetable').datagrid("beginEdit",i);
		    	  flag=false;
		    	  return flag;
		      }else if(parseInt(time_start[0])==parseInt(time_end[0])){
		    		 if(parseInt(time_start[1])>=parseInt(time_end[1])){
						 tooltip_fun(true);
						 $('#attendancePlanConfTimetable').datagrid("beginEdit",i);
						 flag=false;
						 return flag;
					 }
		      }
		  }
		  //创建临时对象
		  for(var i=0;i<count;i++){
			  date.id=i;
			  date.time_start=rows[i].startTime;
			  date.time_end=rows[i].endTime;
			  push_date.push(date);
		  }
		  //判断是否交叉
		 for(var j=0;j<push_date.length;j++){
			 for(var z=0;z<count;z++){
				  var current_time_start=new Date(1970,1,1,rows[z].startTime.split(":")[0],rows[z].startTime.split(":")[1],0);//当前要判断的开始时间
				  var current_time_end=new Date(1970,1,1,rows[z].endTime.split(":")[0],rows[z].endTime.split(":")[1],0);//当前要判断的结束时间
				  var time_start=new Date(1970,1,1,push_date[j].time_start.split(":")[0],push_date[j].time_start.split(":")[1],0);//容器中要判断的值
				  var time_end=new Date(1970,1,1,push_date[j].time_end.split(":")[0],push_date[j].time_end.split(":")[1],0);//容器中要判断的值
				  if((time_start>current_time_start && time_start<current_time_end)|| (time_end>current_time_start && time_end<current_time_end)||
					 (time_start<current_time_start && time_end> current_time_end)){
					  flag=false;
					  $('#attendancePlanConfTimetable').datagrid("beginEdit",count-1);
					  tooltip_fun(false);
				      return flag;
				  }
			      
			  }
		 }
		 return flag;
	  }
	  //------------------------------------时间段判断改进------------------------------------------//
	  return attendanceConfAdd;
})()