f.namespace('platform.attendanceConf');
platform.attendanceConf.attendanceConfList = (function(){
  var contractManagerList={
	 reloadTable:function(){
		reloadTable();
	 },
	 toShowInfo:function(id){
		toShowInfo(id);
	 },
	 toEditPage:function(id){
			toEditPage(id);
	 },
	 doAttendanceConfdelete:function(id,st){
		 doAttendanceConfdelete(id,st);
	 },
	 toContractor:function(id,zt){
		 toContractor(id,zt);
	 }
	 };
  $(function() {
		var path = getRootName();
		doInitTable();//合同信息列表初始化
	});
  /*
   * 初始化表格
   */
  function doInitTable(){
	  	var path = getRootName();
	  	var uri=path+'/attendanceConf/attendanceConf_list';
	  	$('#attendanceConflist').datagrid({
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
	  		pagination : true,// 分页控件
	  		rownumbers : true,// 行号
	  		/*onLoadSuccess:function(data){
	  		 console.log(data);
	  	   },*/
	  		toolbar :[{
	  			text : '新建',
	  			iconCls : 'icon-add',
	  			handler : function(){
	  			     toadd_show();
	  			}	
	  		},{
	  		   text:'删除',
	  		   iconCls : 'icon-cancel',
	  		   handler : function(){
	  			doBatchProjectContractDel();
	  		}	
	  			
	  		}],
	  		columns : [ [
	  		   {
	  			 field : 'attendanceId',
	               checkbox : true   
	  		   },{
	  			 field : 'title',
	  		     title : '标题',
	  			 width : 120,
	  			 align:'center'
	  		   },{
	  			 field : 'attStartTime',
	  			 title : '签到日期（起）',
	  		     width : 120, 
	  		     align:'center',
	  		     formatter:function(value, row, index){
	  			   if(value!=null && value!="" ){
	  				   return formatDate(new Date(value), "yyyy-MM-dd");
	  			   }
	  		     }
	  		     
	  		   },{
	  			field : 'attEndTime',
	  		    title : '签到日期（止）',
	  			width : 120,
	  			align:'center',
	  			formatter:function(value, row, index){
	  			   if(value!=null && value!="" ){
	  					   return formatDate(new Date(value), "yyyy-MM-dd");				  
	  			   }
	  		     }
	  		   },{
	  			field : 'attcount',
	  			title : '每日签到次数',
	  		    width : 120,
	  		    align:'center'
	  		   },{
	  			field : 'statename',
	  			title : '状态',
	  			width : 120,
	  			align:'center'
	  		  },{
	  			field : 'launchTime',
	  		    title : '启动时间',
	  			width : 120,
	  			align:'center',
	  			formatter:function(value, row, index){
	 			   if(value!=null && value!="" ){
	 					   return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");				  
	 			   }
	 		     }
	  		  },{
	    			field : 'stopTime',
	      		    title : '停止时间',
	      			width : 120,
	      			align:'center',
	      			formatter:function(value, row, index){
	     			   if(value!=null && value!="" ){
	     					   return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");				  
	     			   }
	     		     }
	      		  },{
	  				field : 'ids',
	  				title : '操作',
	  				width : 180,
	  				align : 'center',
	  				formatter : function(value, row, index) {
	      			 
	      			    if(row.status=='1'){
	  					return '<a style="cursor: pointer;" onclick="javascript:platform.attendanceConf.attendanceConfList.toShowInfo('
	  						+ row.attendanceId
	  						+ ');"><img src="'
	  						+ path
	  						+ '/style/images/icon/icon_view.png" title="查看" /></a>&nbsp;<a href="javascript:void(0)" style="cursor: pointer;" title="编辑" onclick="javascript:platform.attendanceConf.attendanceConfList.toEditPage('+
	  						+ row.attendanceId
	  						+')" ><img src="'
	  						+ path
	  						+ '/style/images/icon/icon_modify.png" title="编辑" /></a> &nbsp;<a style="cursor: pointer;" title="启用" onclick="javascript:platform.attendanceConf.attendanceConfList.toContractor('
	  						+ row.attendanceId
	  						+ ',2)"><img src="'
	  						+ path
	  						+ '/style/images/icon/icon_star.png" title="未启用" /></a>&nbsp;<a href="javascript:void(0)" style="cursor: pointer;" title="删除" onclick="javascript:platform.attendanceConf.attendanceConfList.doAttendanceConfdelete('+
	  						+ row.attendanceId  + ',' + row.status 
	  						+')" ><img src="'
	  						+ path
	  						+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
	  				}else if(row.status=='2'){
	  					return '<a style="cursor: pointer;" onclick="javascript:platform.attendanceConf.attendanceConfList.toShowInfo('
							+ row.attendanceId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" title="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="停止" onclick="javascript:platform.attendanceConf.attendanceConfList.toContractor('
							+ row.attendanceId
							+ ',3)"><img src="'
							+ path
							+ '/style/images/icon/icon_stop.png" title="已启用" /></a>&nbsp;<a href="javascript:void(0)" style="cursor: pointer;" title="删除" onclick="javascript:platform.attendanceConf.attendanceConfList.doAttendanceConfdelete('+
							+ row.attendanceId + ',' + row.status 
							+')" ><img src="'
							+ path
							+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
	  			    }else if(row.status=='3'){
	  			    	return '<a style="cursor: pointer;" onclick="javascript:platform.attendanceConf.attendanceConfList.toShowInfo('
						+ row.attendanceId
						+ ');"><img src="'
						+ path
						+ '/style/images/icon/icon_view.png" title="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:platform.attendanceConf.attendanceConfList.doAttendanceConfdelete('
						+ row.attendanceId + ',' + row.status 
						+ ')"><img src="'
						+ path
						+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
	  			    }
	  			 
	  			}}
	  	    ]]
	  		
	  	});
	  	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	  	$(window).resize(function() {
	  		$('#attendanceConflist').resizeDataGrid(0, 0, 0, 0);
	  	});
	  }
  /**
   * 签到计划详情页
   */
  function toShowInfo(id){
  	var uri=getRootName() + '/attendanceConf/attendanceConf_detail?attendanceId='+id;
  		parent.$('#popWin').window({
  			 title : '签到设置详情',
  	         width : 830,
  	         height :650,
  	         minimizable : false,
  			 maximizable : false,
  			 collapsible : false,
  			 draggable:true,
  			 modal : true,
  			 href : uri
  			
  		});
  }
  /**
   *签到配置添加
   */
  function toadd_show(id){
  	var uri=getRootName() + '/attendanceConf/attendanceConf_add';
  		parent.$('#popWin').window({
  			 title : '签到设置添加',
  	         width : 830,
  	         height : 650,
  	         minimizable : false,
  			 maximizable : false,
  			 collapsible : false,
  			 draggable:true,
  			 modal : true,
  			 href : uri
  			
  		});
  }
  /**
   * 签到信息编辑
   */
  function toEditPage(id){
  	var uri=getRootName() + '/attendanceConf/attendanceConf_edit?attendanceId='+id;
  		parent.$('#popWin').window({
  			 title : '签到信息编辑',
  	         width : 830,
  	         height : 650,
  	         minimizable : false,
  			 maximizable : false,
  			 collapsible : false,
  			 draggable:true,
  			 modal : true,
  			 href : uri
  		});	 	
  }
  /**
   * 查询配置项信息 条件(根据查询条件查询配置项信息)
   */
  function reloadTable() {
  	var attStartTime_start = $("#attStartTime_start").datebox('getValue');
  	var attStartTime_end= $("#attStartTime_end").datebox('getValue');
    var attEndTime_start=$("#attEndTime_start").datebox('getValue');
    var attEndTime_end=$("#attEndTime_end").datebox('getValue');
    var title=$("#title").val();
  	var queryParams = {
  		"attStartTime_start" :attStartTime_start ,
  		"attStartTime_end" : attStartTime_end,
  		"attEndTime_start" : attEndTime_start,
  		"attEndTime_end":attEndTime_end,
  		"title":title
  	};
  	$('#attendanceConflist').datagrid('options').queryParams=queryParams;
  	$('#attendanceConflist').datagrid('load');
  	$('#attendanceConflist').datagrid('uncheckAll');
  }
  /*
   * 删除签到信息
   */
  function doAttendanceConfdelete(id, st){
	  var messgae="确定删除此信息?";
	if(2==st) {
		messgae="该签到计划已启动,删除后签到记录不可恢复,是否删除该信息?";
	}else if(3==st){
		messgae="该签到计划已停止,删除后签到记录不可恢复,是否删除该信息?";
	}
  	var path = getRootName();
  	$.messager.confirm("提示", messgae, function(r) {
  		if (r == true) {
  			var uri = path + "/attendanceConf/attendanceConfdelete";
  			var ajax_param = {
  				url : uri,
  				type : "post",
  				datdType : "json",
  				data : {
  					"attendanceId" : id,
  					"t" : Math.random()
  				},
  				error : function() {
  					$.messager.alert("错误", "ajax_error", "error");
  				},
  				success : function(data) {
  					if (true == data || "true" == data) {
  						$.messager.alert("提示", "签到信息删除成功！", "info");
  						reloadTable();
  					}else{
							$.messager.alert("提示", "签到删除失败！", "info");
						} 
  				}
  			}
  			ajax_(ajax_param);
  		}
  	});
  }
  /*
   * 批量删除签到信息
   */
  function doBatchProjectContractDel(){
  	var aa=$('#attendanceConflist').datagrid('getChecked');
  	var path = getRootName();
  	var ids = null;
  	var zt=0;
  	var message="确定删除所选中项?";
  	for ( var i = 0; i < aa.length; i++){
  		var id = aa[i].attendanceId;
  		var status=aa[i].status;
  		if(2==status || 3==status) {
  			message="该签到计划已启动,删除后签到记录不可恢复,是否删除该信息?";
  			zt=zt+1;
  		}
  		if (null == ids){
  			ids = id;
  		}else{
  			ids += ',' + id;
  		}
  	}
  	if(zt>0){
  		message="选中信息中包含已启动（已停止）签到计划,删除后签到记录不可恢复,是否删除该信息?";
  	}
  	if (null != ids){
  		$.messager.confirm("提示", message, function(r){
  			if (r == true){
  				var uri = path + "/attendanceConf/attendanceConfBathchdelete?ids="+ids;
  				var ajax_param = {
  					url : uri,
  					type : "post",
  					datdType : "json",
  					data : {
  					   "t" : Math.random()
  					},
  					success : function(data){
  						if (true == data || "true" == data) {
  							$.messager.alert("提示", "信息删除成功！", "info");
  							reloadTable();
  						}else{
  							$.messager.alert("提示", "信息删除失败！", "info");
  						}
  					}
  				};
  				ajax_(ajax_param);
  			} 
  		});
  	} else {
  		$.messager.alert('提示', '没有任何选中项', 'error');
  	}
  }
  /*
   * 修改状态
   */
  function toContractor(id,zt){
	  	var path = getRootName();
	  	var message='';
	  	if(zt==2){
	  		message="是否启动该签到设置?";
	  	}else if(zt==3){
	  		message="是否停止该签到设置?";
	  	}
	  	$.messager.confirm("提示", message, function(r) {
	  		if (r == true) {
	  			var uri = path + "/attendanceConf/validateStartAttPlanConf";
	  			var ajax_param = {
	  				url : uri,
	  				type : "post",
	  				datdType : "json",
	  				data : {
	  					"attendanceId" : id,
	  					"status":zt,
	  					"t" : Math.random()
	  				},
	  				error : function() {
	  					$.messager.alert("错误", "ajax_error", "error");
	  				},
	  				success : function(data) {
	  					if (true == data || "true" == data) {
	  						ajax_({
	  							url :  path + "/attendanceConf/attendanceplanconfzt",
	  			  				type : "post",
	  			  				datdType : "json",
	  			  				data : {
	  			  					"attendanceId" : id,
	  			  				    "status":zt,
	  			  					"t" : Math.random()
	  			  				},
	  			  				error : function() {
	  			  					$.messager.alert("错误", "ajax_error", "error");
	  			  				},
	  			  				success : function(data) {
	  			  					if(data && true == data) {
		  			  					$.messager.alert("提示", "操作成功！", "info");
		  		  						reloadTable();
	  			  					}
	  			  					
	  			  				}
	  							
	  						});
	  					}
	  					else {
	  						$.messager.alert("提示", "请设置签到时间段和签到人员", "info");
	  						return;
	  					}
	  				}
	  			}
	  			ajax_(ajax_param);
	  		}
	  	}); 
  }
  return contractManagerList;
})()