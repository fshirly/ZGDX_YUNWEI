var path=getRootName();//全局变量

$(document).ready(function() {	
	doInitTableColCfg();
	doInitTableSound();
	doInitTableFilter();
	doInitAlarmLevel();//告警级别下拉框
});
/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableColCfg() {
	var viewCfgID = $("#viewCfgID").val();
	var uri	= path + "/monitor/alarmView/listColCfg?viewCfgID="+viewCfgID;
	$('#colCfgtblDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : '',//event主键
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '启用',
							'iconCls' : 'icon-start',
							handler : function() {
								toAddColCfg();
							}
						},{
							'text' : '禁用',
							'iconCls' : 'icon-stop',
							handler : function() {
								doDelColCfg();
							}
						}],
						columns : [ [
								        {
									        	 field : 'colID',
									        	 checkbox : true
									    },
									    {
											field : 'cfgID',
											title : '状态',
											width : 80,
											align : 'center',
											formatter : function(value, row, index) {
									    		if(value=="" || value==null){
									    			return "禁用";
									    		}else{
									    			return "<span style='color:blue;'>启用</span>";
									    		}
											}
										},
										{
											field : 'colName',
											title : '列名',
											width : 100,
											align : 'center'
										},
										{
											field : 'colTitle',
											title : '标题',
											width : 100,
											align : 'center'									
										},
										{
											field : 'colWidth',
											title : '列宽（px）',
											width : 65,
											align : 'center'
										},
										{
											field : 'colOrder',
											title : '显示顺序',
											width : 65,
											align : 'center'
										},
										{
											field : 'colValueOrder',
											title : '排序序号',
											width : 65,
											align : 'center'
										},
										{
											field : 'isVisible',
											title : '是否显示',
											width : 65,
											align : 'center',
											formatter : function(value, row, index) {
												if(value==0){
													return "否";
												}else if(value==1){
													return "是";
												}else{
													return "";
												}
											}
										},
										{
											field : 'ids',
											title : '操作',
											width : 50,
											align : 'center',
											formatter : function(value, row, index) {
												if(row.cfgID=="" || row.cfgID==null){
													return "";
												}else{
													return '<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdateCfgDlg('
													+ row.cfgID
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_modify.png" alt="修改" /></a>'; 
												}
											}
										} ] ]
					});
	$(window).resize(function() {
        $('#colCfgtblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableSound() {
	var viewCfgID = $("#viewCfgID").val();
	var uri = path + "/monitor/alarmView/listSound?viewCfgID="+viewCfgID;
	$('#soundtblDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : 'fldId',//event主键
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '添加',
							'iconCls' : 'icon-add',
							handler : function() {
								toAddSound();
							}
						}],
						columns : [ [
								{
									field : 'alarmLevelName',
									title : '告警等级',
									width : 200,
									align : 'left'
								},
								{
									field : 'soundFileURL',
									title : '声音',
									width : 200,
									align : 'center'									
								},
								{
									field : 'loopTime',
									title : '时间（秒）',
									width : 80,
									align : 'center'
								},
								{
									field : 'ids',
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="删除" onclick="javascript:doDelSound('
												+ row.cfgID 
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]
					});
	$(window).resize(function() {
        $('#soundtblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableFilter() {
	var viewCfgID = $("#viewCfgID").val();
	var uri = path + "/monitor/alarmView/listFilter?viewCfgID="+viewCfgID;
	$('#filtertblDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',						
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : 'fldId',//event主键
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '告警等级',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectAlarmLevel();
							}
						}, {
							'text' : '告警类型',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectAlarmType();
							}
						}, {
							'text' : '告警源对象',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectMOSource();
							}
						}, {
							'text' : '告警事件',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectAlarmEvent();
							}
						} ],
						columns : [ [
								{
									field : 'filterKey',
									title : '过滤关键字',
									width : 200,
									align : 'left',
									formatter : function(value, row, index) {
										var rtnVal = "";
										if(value=="AlarmLevel"){
											rtnVal = "告警等级";
										}else if(value=="AlarmType"){
											rtnVal = "告警类型";
										}else if(value=="AlarmSourceMOID"){ 
											rtnVal = "告警源对象";
										}else if(value=="AlarmDefineID"){
											rtnVal = "告警事件";
										}
										return rtnVal;
									}
								},
								{
									field : 'filterValeName',
									title : '过滤值',
									width : 200,
									align : 'center'									
								},
								{
									field : 'ids',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="删除" onclick="javascript:doDelFilter('
												+ row.filterID 
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]	
					});
	$(window).resize(function() {
        $('#filtertblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 告警级别下拉框
 * @return
 */
function doInitAlarmLevel(){
	var path = getRootPatch();
	var uri = path + "/monitor/alarmView/initAlarmLevel";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			// 得到树的json数据源
			$("#alarmLevelID").empty();
			var datas = eval('(' + data.alarmLstJson + ')');
			for (var i = 0; i < datas.length; i++) {
				var _id = datas[i].alarmLevelID;
				var _name = datas[i].alarmLevelName;
				$("#alarmLevelID").append("<option value='"+_id+"'>"+_name+"</option>");
			}			
		}
	}
	ajax_(ajax_param);
}



/**
 * 修改处理
 * @return
 */
function doUpdate(){	
	if (checkForm()) {	
		var viewCfgID = $("#viewCfgID").val();
		var uri=path+"/monitor/alarmView/updateAlarmView";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
			"viewCfgID" : $("#viewCfgID").val(),
			"cfgName" : $("#cfgName").val(),
			"userDefault" : $('input[name="userDefault"]:checked').val(),
			"defaultRows" : $("#defaultRows").val(),
			"defaultInterval" : $("#defaultInterval").val(),
			"descr" : $("#descr").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					parent.$('#popWin').window('close');
					window.frames['component_2'].reloadAllTable(viewCfgID);
				} else {
					$.messager.alert("提示", "信息修改失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 验证表单信息
 * 
 */
function checkForm() {
	var cfgName = $("#cfgName").val();	
	if(cfgName =="" || cfgName ==null){
		$.messager.alert("提示", "视图名称不能为空！", "warning");
		return false;
	}
	var defaultRows = $("#defaultRows").val();
	var defaultInterval = $("#defaultInterval").val();
	if((defaultRows =="" || defaultRows ==null) && (defaultInterval=="" || defaultInterval==null)){
		$.messager.alert("提示", "告警加载条数和加载时间不能同时为空！", "warning");
		return false;
	}else {
	  if(defaultRows !="" && defaultRows !=null){
		if (!(/^[0-9]*[1-9][0-9]*$/.test(defaultRows))){
			$.messager.alert("提示", "告警加载条数只能输入正整数！", "warning", function(e) {
				$("#defaultRows").val("");
			});
			return false;
		}
		if (defaultRows>500){
			$.messager.alert("提示", "告警加载条数不能超过500条！", "warning", function(e) {
				$("#defaultRows").val("");
			});
			return false;
		}
	  }
	  if(defaultInterval !="" && defaultInterval !=null){
		 if (!(/^[0-9]*[1-9][0-9]*$/.test(defaultInterval))){
				$.messager.alert("提示", "加载时间只能输入正整数！", "warning", function(e) {
					$("#defaultInterval").val("");
				});
				return false;
		 }
		if (defaultInterval>24){
			$.messager.alert("提示", "加载时间不能超过24小时！", "warning", function(e) {
				$("#defaultInterval").val("");
			});
			return false;
		}
	  }
	}
	return true;
}

//告警级别选择
function toSelectAlarmLevel(){
	var viewCfgID = $("#viewCfgID").val();
	var uri=path+"/monitor/alarmView/toSelectAlarmLevel?viewCfgID="+viewCfgID;
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
	 $('#event_select_dlg').window({
		    title: '告警级别选择',
		    width: 620,
		    height: 450,
		    modal: true,
  		    onBeforeOpen : function(){
                if($(".event_select_dlg").size() > 1){
                    $('.event_select_dlg:first').parent().remove();
                }
            },
		    href: uri
		});	
}

//告警类型选择
function toSelectAlarmType(){
	var viewCfgID = $("#viewCfgID").val();
	var uri=path+"/monitor/alarmView/toSelectAlarmType?viewCfgID="+viewCfgID;
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	$('#event_select_dlg').window({
	    title: '告警类型选择',
	    width: 620,
	    height: 450,
	    modal: true,
		    onBeforeOpen : function(){
            if($(".event_select_dlg").size() > 1){
                $('.event_select_dlg:first').parent().remove();
            }
        },
	    href: uri
	});
}

//告警源对象选择
function toSelectMOSource(){
	var viewCfgID = $("#viewCfgID").val();
	var uri=path+"/monitor/alarmView/toSelectMOSource?viewCfgID="+viewCfgID;
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	$('#event_select_dlg').window({
	    title: '告警源对象选择',
	    width: 660,
	    height: 470,
	    modal: true,
		    onBeforeOpen : function(){
            if($(".event_select_dlg").size() > 1){
                $('.event_select_dlg:first').parent().remove();
            }
        },
	    href: uri
	});
}

//告警事件选择
function toSelectAlarmEvent(){
	var viewCfgID = $("#viewCfgID").val();
	var uri=path+"/monitor/alarmView/toSelectAlarmEvent?viewCfgID="+viewCfgID;
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	$('#event_select_dlg').window({
	    title: '告警事件选择',
	    width: 620,
	    height: 450,
	    modal: true,
		    onBeforeOpen : function(){
            if($(".event_select_dlg").size() > 1){
                $('.event_select_dlg:first').parent().remove();
            }
        },
	    href: uri
	});
}





function toClosed(){
	parent.$('#popWin').window('close');
}

/**
 * 增加显示列
 * @return
 */
function toAddColCfg(){
			var ids = null;	
			var rows  = $('#colCfgtblDataList').datagrid('getSelections');
			var flag = null;
			for(var i=0; i<rows.length; i++){
				if(rows[i].cfgID != null && rows[i].cfgID !="" ){
					flag = "1";
				}
				if (null == ids) {
					ids = rows[i].colID;
				} else {
					ids += ',' + rows[i].colID;
				}
		   }			
		   if (null != ids) {
			  if(flag == null){
				  var viewCfgID = $("#viewCfgID").val();
				  var uri =path + "/monitor/alarmView/addColCfgDlg";
				  var ajax_param = {
						  url : uri,
						  type : "post",
						  datdType : "json",
						  data : {
					  	  "id":ids,
					  	  "viewCfgID":viewCfgID
				  		  },
				  		  success : function(data) {
				  			  if (true == data || "true" == data) {
				  				  doInitTableColCfg();
				  			  } else {
				  				  $.messager.alert("提示", "信息启用失败！", "error");
				  			  }
				  		  }
				  };
				 ajax_(ajax_param);
			  }else{
				  $.messager.alert("提示", "错误选择已启用的信息！", "error");
			  }
		   }else{
			$.messager.alert('提示', '没有任何选中项', 'error');
		   }
}

/**
 * 删除过滤条件
 * @param id
 * @return
 */
function doDelFilter(id){
	$.messager.confirm("操作提示", "您确定删除此信息吗？", function (data) {
        if (data) {
        	var uri = path + "/monitor/alarmView/delFilterDlg";
        	var ajax_param = {
        		url : uri,
        		type : "post",
        		datdType : "json",
        		data : {
        			"filterID" : id,
        			"t" : Math.random()
        		},
        		error : function() {
        			$.messager.alert("错误", "ajax_error", "error");
        		},
        		success : function(data) {
        			if (true == data || "true" == data) {
        				doInitTableFilter();
        			} else {
        				$.messager.alert("提示", "信息删除失败！", "error");
        			}
        		}
        	}
        	ajax_(ajax_param);
        }            
    });	
}

function doSelect(filterKey,filterVal){
		var viewCfgID = $("#viewCfgID").val();
		//保存数据		
		 var uri=path+"/monitor/alarmView/addFilterDlg?filterValeName="+filterVal;
		 var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
			 	"viewCfgID" : viewCfgID,
				"filterKey" : filterKey
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					doInitTableFilter();
				} else {
					$.messager.alert("提示", "信息增加失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
}

//显示列操作

/**
 * 修改显示列
 * @param id
 * @return
 */
function toUpdateCfgDlg(id){	
	initCfgUpdateVal(id);
	$('#divColcfgEdit').dialog('open');
}

/**
 * 根据显示列ID获取对象
 * 
 */
function initCfgUpdateVal(id) {
	resetForm('colCfgTab');
	var uri=path+"/monitor/alarmView/findCfgDlg";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"cfgID" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			if(data.isVisible==1){
				$("#isVisible").prop("checked","checked");
			}else{
				$("#isVisible").removeAttr("checked");
			}	
		}
	}
	ajax_(ajax_param);
}

/**
 * 删除显示列
 * @param id
 * @return
 */
function doDelColCfg(){
	var ids = null;	
	var rows  = $('#colCfgtblDataList').datagrid('getSelections');
	var flag = null;
	for(var i=0; i<rows.length; i++){
		if(rows[i].cfgID == null || rows[i].cfgID =="" ){
			flag = "1";
		}
		if (null == ids) {
			ids = rows[i].cfgID;
		} else {
			ids += ',' + rows[i].cfgID;
		}
	}
  
	if (null != ids && rows.length>0) {
	  if(flag == null){
		  var uri = path + "/monitor/alarmView/delColCfgDlg";
		  var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"cfgID" : ids,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					doInitTableColCfg();
				} else {
					$.messager.alert("提示", "信息禁用失败！", "error");
				}
			}
		  }
		  ajax_(ajax_param);
	  }else{
		  $.messager.alert("提示", "错误选择已禁用的信息！", "error");
	  }
   }else{
	$.messager.alert('提示', '没有任何选中项', 'error');
   }
}

/**
 * 列修改处理
 */
function doUpdateCfgDlg(){	
	if (checkCfgForm()) {
		var isVisible ="";
		if($("#isVisible").is(":checked")){
			isVisible = 1;
		}else{
			isVisible = 0;
		}
		var uri=path + "/monitor/alarmView/updateCfgDlg";		
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"cfgID" : $("#ipt_cfgID").val(),
				"colWidth" : $("#ipt_colWidth").val(),
				"colOrder" : $("#ipt_colOrder").val(),
				"isVisible" : isVisible,
				"colValueOrder" : $("#ipt_colValueOrder").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#divColcfgEdit').dialog('close');
					doInitTableColCfg();
				} else {
					$.messager.alert("提示", "信息修改失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	}
}
/**
 * 验证表单信息
 * 
 */
function checkCfgForm() {
	var colWidth = $("#ipt_colWidth").val();
	var flag = /^[+]?[0-9]+\d*$/i.test(colWidth);//验证是否为整数
	if(!flag){
		$.messager.alert("提示", "列宽请输入正整数！", "warning");		
		$('#ipt_colWidth').val("");
		$('#ipt_colWidth').focus();
		return ;
	}
	var colOrder = $("#ipt_colOrder").val();
	flag = /^[+]?[0-9]+\d*$/i.test(colOrder);//验证是否为正整数
	if(!flag){
		$.messager.alert("提示", "显示顺序请输入正整数！", "warning");		
		$('#ipt_colOrder').val("");
		$('#ipt_colOrder').focus();
		return ;
	}		
	var colValueOrder = $("#ipt_colValueOrder").val();
	flag = /^[+]?[0-9]+\d*$/i.test(colValueOrder);//验证是否为正整数
	if(!flag){
		$.messager.alert("提示", "排序序号请输入正整数！", "warning");		
		$('#ipt_colValueOrder').val("");
		$('#ipt_colValueOrder').focus();
		return ;
	}
	return true;
}

function closeCfgDialg(){
	$('#divColcfgEdit').dialog('close');
}

//通知声音操作
/**
 * 增加声音文件
 * @return
 */
function toAddSound(){
	resetForm('soundTab');
	$('#divSoundAdd').dialog('open');
}

/**
 * 删除声音
 * @param id
 * @return
 */
function doDelSound(id){
	$.messager.confirm("操作提示", "您确定删除此信息吗？", function (data) {
        if (data) {
        	var uri = path + "/monitor/alarmView/delSoundDlg";
        	var ajax_param = {
        		url : uri,
        		type : "post",
        		datdType : "json",
        		data : {
        			"cfgID" : id,
        			"t" : Math.random()
        		},
        		error : function() {
        			$.messager.alert("错误", "ajax_error", "error");
        		},
        		success : function(data) {
        			if (true == data || "true" == data) {
        				doInitTableSound();
        			} else {
        				$.messager.alert("提示", "信息删除失败！", "error");
        			}
        		}
        	}
        	ajax_(ajax_param);
        }            
    });
}

function closeSoundDialog(){
	$('#divSoundAdd').dialog('close');
}

function doAddSound(){	
	if (checkSoundForm()) {	
		//验证告警等级是否已用		
		var uri=path+"/monitor/alarmView/addSoundDlg";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"alarmLevelID" : $("#alarmLevelID").val(),
				"soundFileURL" : $("#soundFileURL").val(),
				"loopTime" : $("#loopTime").val(),
				"viewCfgID" : $("#viewCfgID").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				var flag = data.flag;
				if (flag=="1") {
					$('#divSoundAdd').dialog('close');
					doInitTableSound();
				}else if(flag=="2"){
					$.messager.alert("提示", "该告警等级已有声音文件！", "warning");
					$('#alarmLevelID').get(0).selectedIndex=0;
				}else if(flag=="3"){
					$('#divSoundAdd').dialog('close');
					$.messager.alert("提示", "信息新增失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	}
}
/**
 * 验证表单信息
 * 
 */
function checkSoundForm(){
	var alarmLevelID = $("#alarmLevelID").val();
	if(alarmLevelID==null || alarmLevelID==""){
		$.messager.alert("提示", "请选择告警等级！", "warning");
		return ;		
	}
	var soundFileURL = $("#soundFileURL").val();
	if(soundFileURL==null || soundFileURL==""){
		$.messager.alert("提示", "请选择声音文件！", "warning");
		return ;		
	}
	var loopTime = $("#loopTime").val();
	var flag = /^[+]?[1-9]+\d*$/i.test(loopTime);//验证是否为正整数
	if(!flag){
		$.messager.alert("提示", "时间窗口（秒）请输入正整数！", "warning");		
		$('#loopTime').val("");
		$('#loopTime').focus();
		return ;
	}
	return true;
}