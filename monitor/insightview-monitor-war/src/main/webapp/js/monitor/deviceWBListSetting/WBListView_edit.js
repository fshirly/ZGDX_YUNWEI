$(document).ready(function() {
	initTabEvent();
	initBaseInfo();
});

/**
 * 加载设备接口黑名单
 * @return
 */
function doInitBlackList(opt) {
	var path = getRootName();
	var deviceip = $("#deviceIP").text();
	var type = type||'1';
	var opt = "edit"||opt;
	var	uri = path + "/monitor/deviceWBListSetting/listInterfaceByDeviceIP?deviceip="+deviceip+"&opt="+opt+"&type="+type;
	$('#interfaceBlackList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						fitColumns : true,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : '',// event主键
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						resizable  : true,
						toolbar : [ {
							'text' : '启用',
							'iconCls' : 'icon-start',
							handler : function() {
								toStart(type,"interfaceBlackList");
							}
						}, {
							'text' : '禁用',
							'iconCls' : 'icon-stop',
							handler : function() {
								toStop(type,"interfaceBlackList");
							}
						},{
							'text' : '重置',
							'iconCls' : 'icon-reload',
							handler : function() {
								toResetInteface(type,'interfaceBlackList');
							}
						} ],
						columns : [ [
								{
									field : 'ifMOID',
									checkbox : true
								},
								{
									field : 'ifname',
									title : '接口名',
									width : 100,
									align : 'center'
								},
								{
									field : 'moalias',
									title : '接口别名',
								},
								{
									field : 'ifTypeName',
									title : '接口类型'
								},
								{
									field : 'operstatus',
									title : '操作状态',
									formatter : function(value,row,index){
										  if(value==1){
											  return "<span style='color:green'>UP</span>";
										  }else if(value==2){
											  return "<span style='color:red'>DOWN</span>";
										  }else{
											  return "<span style='color:#C8C8C8'>未知</span>";
										  }
									}
									
								},
								{
									field : 'blackOprateStatus',
									title : '启用状态',
									width : 100,
									align : 'center',
									formatter : function(value,row,index){
										switch (row.blackOprateStatus){
										case "0":
											return "未启用";
											break;
										case "1":
											if(row.type==type){
												return "启用";
											}
											break;
										default:
											return "N/A";
											break;
										}
									}
								}
								]]
					});
	$(window).resize(function() {
        $('#interfaceBlackList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载设备接口白名单
 * @return
 */
function doInitWhiteList(opt) {
	var path = getRootName();
	var deviceip = $("#deviceIP").text();
	var type = type||'2';
	var opt = "edit"||opt;
	var	uri = path + "/monitor/deviceWBListSetting/listInterfaceByDeviceIP?deviceip="+deviceip+"&opt="+opt+"&type="+type;
	$('#interfaceWhiteList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						fitColumns : true,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : '',// event主键
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						resizable  : true,
						toolbar : [ {
							'text' : '启用',
							'iconCls' : 'icon-start',
							handler : function() {
                                toStart(type,"interfaceWhiteList");
							}
						}, {
							'text' : '禁用',
							'iconCls' : 'icon-stop',
							handler : function() {
                                toStop(type,"interfaceWhiteList");
							}
						},{
							'text' : '重置',
							'iconCls' : 'icon-reload',
							handler : function() {
								toResetInteface(type,'interfaceWhiteList');
							}
						} ],
						columns : [ [
								{
									field : 'ifMOID',
									checkbox : true
								},
								{
									field : 'ifname',
									title : '接口名',
									width : 100,
									align : 'center'
								},
								{
									field : 'moalias',
									title : '接口别名',
								},
								{
									field : 'ifTypeName',
									title : '接口类型'
								},
								{
									field : 'operstatus',
									title : '操作状态',
									formatter : function(value,row,index){
										  if(value==1){
											  return "<span style='color:green'>UP</span>";
										  }else if(value==2){
											  return "<span style='color:red'>DOWN</span>";
										  }else{
											  return "<span style='color:#C8C8C8'>未知</span>";
										  }
									}
									
								},
								{
									field : 'blackOprateStatus',
									title : '启用状态',
									width : 100,
									align : 'center',
									formatter : function(value,row,index){
										switch (row.blackOprateStatus){
										case "0":
											return "未启用";
											break;
										case "1":
											return "启用";
											break;
										default:
											return "N/A";
											break;
										}
									}
								}
								]]
					});
	$(window).resize(function() {
        $('#interfaceWhiteList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * opt //页面操作
 * tableId 
 * type　//黑白名单类型
 */
var $dg; // 定义全局变量datagrid
var editRow; // 定义全局变量：当前编辑的行
var initPortBlackList = function(opt,tableId,type) {
	editRow = undefined;
	var doDelSerivcePort = function(row){//删除当前编辑的行或已经保存的行【服务端口】
		var delUrl = getRootName()+"/monitor/deviceWBListSetting/delPort";
		$.post(delUrl,row,function(data){
			if(data&&data.success=="true"){
				$.messager.alert("提示", "删除成功", "info");
				$dg.datagrid("reload");
			}else{
				$.messager.alert("错误", "删除失败", "error");
			}
		},"json");
	};
	
	var mytoolbar = [
			{
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					// 添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
					if (editRow != undefined) {
					}
					// 添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
					if (editRow == undefined) {
						$dg.datagrid("insertRow", {
							index : 0, // index start with 0
							row : {
							}
						});
						// 将新插入的那一行开户编辑状态
						$dg.datagrid("beginEdit", 0);
						// 给当前编辑的行赋值
						editRow = 0;
					}
				
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					// 修改时要获取选择到的行
					var rows = $dg.datagrid("getSelections");
					// 如果只选择了一行则可以进行修改，否则不操作
					if (rows.length == 1) {
						// 修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
						$dg.datagrid("endEdit", editRow);
						// 获取到当前选择行的下标
						var index = $dg.datagrid("getRowIndex", rows[0]);
						// 开启编辑
						$dg.datagrid("beginEdit", index);
						// 把当前开启编辑的行赋值给全局变量editRow
						editRow = index;
						// 当开启了当前选择行的编辑状态之后，
						// 应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
						$dg.datagrid("unselectAll");
					}
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					
					// 删除时先获取选择行
					var rows = $dg.datagrid("getSelections");
					// 选择要删除的行
					if (rows.length > 0) {
						$.messager.confirm("提示", "您确定要删除吗?", function(r) {
							if (r) {
								$.each(rows,function(i,n){
									if(n.editing){
										$dg.datagrid('rejectChanges');
										editRow = undefined;
									}else{
										doDelSerivcePort(n);
									}
								});
							}
							$dg.datagrid("unselectAll");
						});
					} else {
						$.messager.alert("提示", "请选择要删除的行", "error");
					}
				}
			}, {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if(!$("#collectorsList").datagrid('getChecked').length){
						$.messager.alert("提示", "请确保已勾选采集机", "info");
						return;
					}
					var isValid = $dg.datagrid('validateRow', editRow);//验证正在编辑行
					if (!isValid) {
						$.messager.alert("提示", "请确保填写的信息完整并且正确", "info");
						return;
					}
					endEdit($dg);
					// 保存新插入行
					var insertedRows = $dg.datagrid('getChanges','inserted');
					$.each(insertedRows,function(i,n){
						n.deviceip = $("#deviceIP").text();//设备IP
						n.collectorID = $("#collectorListDiv").attr('selectedCollector');//采集机ID
					});
					if (insertedRows.length) {
						doInsertAlarmBlackListForDevicePort(insertedRows,type,tableId);
					}
					// 保存修改的行
					var updatedRows = $dg.datagrid('getChanges','updated');
					if(updatedRows.length){
						doUpdateAlarmBlackListForDevicePort(updatedRows);
					}
				}
			}, {
				text : '取消编辑',
				iconCls : 'icon-redo',
				handler : function() {
					// 取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
					editRow = undefined;
					$dg.datagrid("rejectChanges");
					$dg.datagrid("unselectAll");
				}
			} ];

	var columns = [ [ {
		field : 'port',
		title : '端口号',
		align : 'center',
		width : 100,
		editor : {
			type : 'numberbox',
			options:{
				required : true
			}
		}
	}, {
		field : 'portType',
		title : '服务端口类型',//'TCP/UDP'
		align : 'center',
		width : 100,
		editor : {
			type : 'combobox',
			options : {
				valueField : 'portTypeValue',
				textField : 'portTypeText',
				data : [ {
					'portTypeValue' : 'TCP',
					'portTypeText' : 'TCP'
				},{
					'portTypeValue' : 'UDP',
					'portTypeText' : 'UDP'
				} ],
				editable : false,
				required : true
			}
		}
	}, {
		field : 'blackOprateStatus',
		title : '状态',
		align : 'center',
		width : 100,
		formatter:function(value,row,index){
			var options = {'1':'启用','0':'不启用'};
			return options[value];
		},
		editor : {
			type : 'combobox',
			options : {
				valueField : 'oprateStatusValue',
				textField : 'OprateStatusText',
				data : [ {
					'oprateStatusValue' : '1',
					'OprateStatusText' : '启用'
				},
				{
					'oprateStatusValue' : '0',
					'OprateStatusText' : '不启用'
				} ],
				editable : false,
				required : true
			}
		}
	} ]];
	var portService = {
			field:'portService',
			title : '端口对应服务',//'当为白名单时需要填端口对应服务'
			align : 'center',
			width : 100,
			editor : {
				type : 'validatebox',
				options:{
					required : true
				}
			}
	};
	if(type=='2'){//'当为白名单时需要填端口对应服务'
		columns[0].push(portService);
	}
	// 设备IP
	var deviceIP = $("#deviceIP").text();

	$dg = $('#'+tableId).datagrid({
		iconCls : 'icon-edit', // 图标
		width : 'auto',
		height : 'auto',
		fitColumns : true,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : true, // 是否可折叠的
		url : getRootName()+'/monitor/deviceWBListSetting/listPortByDeviceIP?deviceip='+deviceIP+"&opt="+opt+"&type="+type,
		remoteSort : false,
		idField : 'port',
		singleSelect : true, // 是否单选
		pagination : false, // 分页控件
		rownumbers : true,
		toolbar : mytoolbar,
		columns : columns,
		onBeforeEdit : function(index, row) {
			row.editing = true;
			$dg.datagrid('refreshRow', index);
		},
		onAfterEdit : function(index, row) {
			row.editing = false;
			$dg.datagrid('refreshRow', index);
		},
		onCancelEdit : function(index, row) {
			row.editing = false;
			$dg.datagrid('refreshRow', index);
		}
	});
	
	var jsdata = '{"rows":[]}';
	$dg.datagrid("loadData", JSON.parse(jsdata));
}

function endEdit($dg) {
	var rows = $dg.datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		$dg.datagrid('endEdit', i);
	}
}
/**
 *  服务端口启用黑白名单插入数据
 */
function doInsertAlarmBlackListForDevicePort(insertedRows,type,id) {
	var ajax_param = {
		url : getRootName() + '/monitor/deviceWBListSetting/addWBSettings',
		data : {
			"selectedRows" : JSON.stringify(insertedRows),
			"type" : type,
			"portType" : $('#portType').attr("portType"),
			"deviceip":$("#deviceIP").text()
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if(data.portExisted=="true"){
				$.messager.alert("错误", "端口"+data.port+"已经配置过黑白名单", "error");
				$dg.datagrid("beginEdit",editRow);
			}else{
				$('#' + id).datagrid("reload");
				$.messager.alert("提示", "保存成功", "info");
				// 保存后，复原到一般状态
				editRow = undefined;
			}
			$dg.datagrid("reload");
		},
		error : function() {
			$.messager.alert("提示", "保存失败", "error");
		}
	}
	$.ajax(ajax_param);
}

/**
 * 修改当前已经保存的行【服务端口】
 */
function doUpdateAlarmBlackListForDevicePort(updatedRow){
	var updUrl = getRootName() + "/monitor/deviceWBListSetting/updPort";
	$.post(updUrl,updatedRow[0],function(data){
		if(data.portExisted=="true"){
			$.messager.alert("错误", data.port+"已经配置过黑白名单", "error");
			$dg.datagrid("beginEdit",editRow);
		}else if(data&&data.success=="true"){
			$.messager.alert("提示", "保存成功", "info");
			// 保存后，复原到一般状态
			editRow = undefined;
			$dg.datagrid("reload");
		}else{
			$.messager.alert("错误", "保存失败", "error");
		}
	},"json");
}

function initTabEvent(){
	$('#editWBSetting').tabs({
	    border:true,
	    onSelect:function(title){
	    	if(title=='黑名单'){
	    		//'1=黑名单  2=白名单'
				if($("#portType").attr("portType")=="1"){//接口/端口类型
					doInitBlackList("edit");
					$('#interfaceBlackListDiv').show();
					$('#portBlackListDiv').hide();
				}else if($("#portType").attr("portType")=="2"){
					if(!$("#collectorsList").datagrid('getChecked').length){
						$(this).tabs('select',0);
		    			$.messager.alert("提示", "请选择采集机", "error");
					}
					$('#interfaceBlackListDiv').hide();
					$('#portBlackListDiv').show();
					var opt = "edit";
					initPortBlackList(opt,'portBlackList','1');
				}
	    	}else if(title=='白名单'){
	    		//'1=黑名单  2=白名单'
				if($("#portType").attr("portType")=="1"){//接口/端口类型
					doInitWhiteList("edit");
					$('#interfaceWhiteListDiv').show();
					$('#portWhiteListDiv').hide();
				}else if($("#portType").attr("portType")=="2"){
					if(!$("#collectorsList").datagrid('getChecked').length){
						$(this).tabs('select',0);
		    			$.messager.alert("提示", "请选择采集机", "error");
					}
					$('#interfaceWhiteListDiv').hide();
					$('#portWhiteListDiv').show();
					var opt = "edit";
					initPortBlackList(opt,'portWhiteList','2');
				}
	    	}
	    }
	});
}

function initBaseInfo() {
	var selectedRow = JSON.parse($("#selectedRow").val());
	$("#deviceIP").text(selectedRow[0].deviceIP);// 设置设备IP
	var portTypeText = '';
	switch (selectedRow[0].portType) {
	case "1":
		portTypeText =  "接口";
		break;
	case "2":
		portTypeText =  "服务端口";
		break;
	default:
		break;
	}
	$("#portType").text(portTypeText);//设置接口类型值
	$("#portType").attr('portType',selectedRow[0].portType);
    switch (selectedRow[0].oprateStatus) {
    case "1":
        $("input[type='radio'][value='1']").attr('checked', 'checked');
        break;
    case "0":
        $("input[type='radio'][value='0']").attr('checked', 'checked');
        break;
    default:
        break;
    }

	$('#collectorListDiv').hide();
	if ($("#portType").attr('portType') == "1") {
		$('#collectorListDiv').hide();
	} else if ($("#portType").attr('portType') == "2") {
		$('#collectorListDiv').show();
		// 初始化采集机列表选择的行,传入父页面选择的设备所用采集机ID
		initCollectorListTable(selectedRow[0].collectorID);
	}
}

function initCollectorListTable(selectedCollectorID) {
	var rowIndex;
	$('#collectorsList').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		fitColumns : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		url : getRootName()+"/monitor/deviceWBListSetting/getCollectorList",
		remoteSort : false,
		idField : '',// event主键
		singleSelect : true,// 是否单选
//		checkOnSelect : true,
//		selectOnCheck : false,
		resizable : true,
		columns : [ [ {
			field : 'serverid',
			checkbox : true
		}, {
			field : 'ipaddress',
			title : '采集机IP',
			width : 100,
			align : 'center',
			formatter: function(value,row,index){
				if(row.serverid==selectedCollectorID){
					rowIndex = index;
				}
				return value;
			}
		}, {
			field : 'isOffline',
			title : '采集机类型',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				switch (row.isOffline) {
				case null:
					return "在线";
					break;
				default:
					return "离线";
					break;
				}
			}
		} ] ],
		onCheck : function(rowIndex,rowData){
			$("#collectorListDiv").attr('selectedCollector',rowData.serverid);
		},
		onLoadSuccess : function(data){
			$(this).parent().find(".datagrid-header-row > td[field='serverid']").find("input").hide();
			if(rowIndex!=undefined){
				$(this).datagrid('checkRow', rowIndex);
				$(this).datagrid('selectRow', rowIndex);
			}
		}
	});
	$(window).resize(function() {
		$('#collectorsList').resizeDataGrid(0, 0, 0, 0);
	});
}

// 启用
function toStart(type, that) {
    var selectedRows = $("#" + that).datagrid("getChecked");
	if(!selectedRows.length){
		$.messager.alert("提示","没有选中项","info");
		return;
	}
    var portType = $('#portType').attr('portType'); //接口类型
    if (selectedRows.length > 0) {
        $.ajax({
                url: getRootName() + '/monitor/deviceWBListSetting/editWBSettings', // 跳转到 action
                data: {
                    "selectedRows": JSON.stringify(selectedRows),
                    "portType": portType,
                    "type": type,
                    "operateStatus": 1//启用
                },
                type: 'post',
                dataType: 'json',
                success: function(data) {
                    $.messager.alert("提示", "启用成功", "info");
                    $('#'+that).datagrid('reload');
                    $('#'+that).datagrid('uncheckAll');
                },
                error: function() {
                    $.messager.alert("提示", "启用失败", "error");
                }
        });
    }
}
//不启用
    function toStop(type, that) {
        var selectedRows = $("#" + that).datagrid("getChecked");
    	if(!selectedRows.length){
    		$.messager.alert("提示","没有选中项","info");
    		return;
    	}
        var ipt_portType = $('#portType').attr('portType'); //接口类型
        if (selectedRows.length > 0) {
            $.ajax({
                    url: getRootName() + '/monitor/deviceWBListSetting/editWBSettings', // 跳转到 action
                    data: {
                        "selectedRows": JSON.stringify(selectedRows),
                        "portType": ipt_portType,
                        "type": type,
                        "operateStatus": 0 //不启用
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function(data) {
                        $.messager.alert("提示", "禁用成功", "info");
                        $('#'+that).datagrid('reload');
                        $('#'+that).datagrid('uncheckAll');
                    },
                    error: function() {
                        $.messager.alert("提示", "禁用失败", "error");
                    }
            });
        }
}

/**
 * 设备启用状态更改
 */
function confirmOperateStatusForDevice(){
	var selectedRow = JSON.parse($("#selectedRow").val());//从父页面带过来的行
	var operateStautsOldVal = selectedRow[0].oprateStatus;
	var operateStatusNewVal = $("input[type='radio'][name$='Device']:checked").val();
	var collectorOldVal = selectedRow[0].collectorID;
	if($("#portType").attr('portType') == "2"&&$('#collectorsList').datagrid('getChecked').length){
		var collectorNewVal = $('#collectorsList').datagrid('getChecked')[0].serverid;
	}else{
		var collectorNewVal = collectorOldVal;
	}
        $.ajax({
                url: getRootName() + '/monitor/deviceWBListSetting/editWBSettings',
                data: {
                    "selectedRows": $("#selectedRow").val(),
                    "operateStatusForDevice": operateStatusNewVal,
                    "collectorForDevice":collectorNewVal,
                    "portType":$('#portType').attr('portType'),
                    "operateStatusChanged":operateStatusNewVal!=operateStautsOldVal,
                    "collectorChanged":collectorNewVal!=collectorOldVal,
                    "oldCollector":collectorOldVal
                },
                type: 'post',
                dataType: 'json',
                success: function(data) {
                	parent.frames[1].$("#tblWBSettingList").datagrid('reload');
                },
                error: function() {
                	parent.frames[1].$("#tblWBSettingList").datagrid('reload');
                    $.messager.alert("提示", "设备黑白名单启用状态或采集机修改失败", "error");
                }
        });
}

function toResetInteface(type,id){
	var selectedRows = $('#'+id).datagrid("getChecked");
	if(!selectedRows.length){
		$.messager.alert("提示","没有选中项","info");
		return;
	}
	var optStatusConfirmedRows = [];
	$.each(selectedRows,function(i,n){
		if(n.blackOprateStatus!=null){
			optStatusConfirmedRows.push(n);
		}
	});
	if(optStatusConfirmedRows.length>0){
		$.ajax({
			 url:getRootName()+'/monitor/deviceWBListSetting/resetWBSettingForInterface',// 跳转到 action
		     data:{ 
		    	 "selectedRows":JSON.stringify(optStatusConfirmedRows),
		     	},
		     type:'post',
		     dataType:'json',
		     success:function(data) {
		    	 if(data.success=="true"){
		    		 $.messager.alert("提示","重置成功","info");
		    	 }else{
	    		 $.messager.alert("提示","重置失败","info");
		    	 }
		    	 $('#'+id).datagrid("reload")
		    	 $('#'+id).datagrid('uncheckAll');
		     },
		     error : function() {
		    	 $('#'+id).datagrid("reload")
		    	 $.messager.alert("提示","重置失败","error");
		     }
		});
	}else{
		$.messager.alert("提示","未选择可重置行","info");
	}
}

function confirm(){
	confirmOperateStatusForDevice();//启用状态变更、采集机变更
    $('#popWin').window('close');
}

function cancle(){
    $('#popWin').window('close');
}
