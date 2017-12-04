/**
 * 初始化采集机列表
 */
function initCollectorListTable() {
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
		idField : 'serverid',// event主键
		singleSelect : true,// 是否单选
//		checkOnSelect : true,
//		selectOnCheck : false,
		resizable : true,
		rownumbers : true,
		columns : [ [ {
			field : 'serverid',
			checkbox : true
		}, {
			field : 'ipaddress',
			title : '采集机IP',
			width : 100,
			align : 'center'
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
		onLoadSuccess : function(data){
			$(this).parent().find(".datagrid-header-row > td[field='serverid']").find("input").hide();
		},
		onSelect : function(rowIndex,rowData){
			$("#collectorListDiv").attr('selectedCollector',rowData.serverid);
		}
	});
	$(window).resize(function() {
		$('#collectorsList').resizeDataGrid(0, 0, 0, 0);
	});
}
 
/**
 * opt //页面操作
 * tableId 
 */
var $dg; // 定义全局变量datagrid
var editRow = undefined; // 定义全局变量：当前编辑的行
var initIACList = function(opt,tableId) {
	var addRow = function() {
		var newRowIndex = 0;
		if (editRow != undefined) {
			var isValid = $dg.datagrid('validateRow', editRow);// 验证正在编辑行
			if (!isValid) {
				return;
			}
			$dg.datagrid("endEdit", editRow);
			newRowIndex = editRow + 1;
		} else {
			newRowIndex = editRow || 0;
		}
		$dg.datagrid("insertRow", {
			index : newRowIndex, // index start with 0
			row : {}
		});
		// 将新插入的那一行开户编辑状态
		$dg.datagrid("beginEdit", newRowIndex);
		// 给当前编辑的行赋值
		editRow = newRowIndex;
	};
	var delRow = function(){
		// 删除时先获取选择行
		var rows = $dg.datagrid("getChecked");
		rows.reverse();
		// 选择要删除的行
		if (rows.length > 0) {
			$.messager.confirm("提示", "您确定要删除吗?", function(r) {
				if (r) {
					$.each(rows, function(i, n) {
						if (n.editing) {
							$dg.datagrid('deleteRow',editRow);
						} else {
							var rowIndex = $dg.datagrid('getRowIndex',n);
							$dg.datagrid('deleteRow',rowIndex);
						}
					});
				}
				editRow = undefined;
				$dg.datagrid("unselectAll");
			});
			reBuildRowIndex();
		} else {
			$.messager.alert("提示", "请选择要删除的行", "error");
		}
	};
	var reBuildRowIndex = function(){
		var rows = $dg.datagrid('getRows');
	};
	var mytoolbar = [{
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			addRow();
		}
	},'-', {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			delRow();
		}
	}];
	var columns = [ [ {
		field : 'rowIds',
		checkbox:true
	},{
		field : 'portService',
		title : '链路名称',
		align : 'center',
		width : 100,
		editor : {
			type : 'validatebox',
			options:{
				required : true
			}
		}
	}, {
		field : 'gatewayIP',
		title : '链路接入网关',
		align : 'center',
		width : 100,
		editor : {
			type : 'validatebox',
			options : {
				required : true,
				validType : 'ip'
			}
		}
	}]];
	
	// 设备IP
	var deviceIP = $("#ipt_deviceIP").val();

	$dg = $('#'+tableId).datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		fitColumns : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		url : '',
//		url : getRootName()+'/monitor/deviceWBListSetting/listPortByDeviceIP?deviceip='+deviceIP+"&opt="+opt+"&type="+type,
		remoteSort : false,
		idField : 'port',
		singleSelect : false, // 是否单选
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
		}
	});
	
	var jsdata = '{"rows":[]}';
	$dg.datagrid("loadData", JSON.parse(jsdata));
	return $dg;
}
		
function endEdit($dg) {
	var rows = $dg.datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		$dg.datagrid('endEdit', i);
	}
}


function saveData() {
	if(!checkInfo('#tblAlarmAdd')){
		return;
	}
	var insertedRows = $dg.datagrid("getRows");
//	if(!insertedRows.length){
//		$.messager.alert("提示", "请添加链路名称与网关", "info");
//		return;
//	}
	if(!$dg.datagrid('validateRow',editRow)){
		$.messager.alert("提示", "链路名称与网关填写不正确", "error");
		return;
	}
	if(!$("#collectorsList").datagrid("getChecked").length){
		$.messager.alert("提示", "请选择采集机", "info");
		return;
	}
	$dg.datagrid('endEdit',editRow);
	var ajax_param = {
		url : getRootName() + '/monitor/internetAccessChainSetting/addIACSettings?'+$("form").serialize(),
		data : {
			"insertedRows" : JSON.stringify(insertedRows)
			,"collectorId" : function(){
					return $("#collectorsList").datagrid("getChecked")[0].serverid
				}
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if(data.ipExist=="false"){
				$.messager.alert("提示", "设备IP不在监测对象列表中!", "info");
			}else if(data.success=="true"){
				$.messager.alert("提示", "保存成功", "info");
				$('#popWin').window('close');
				parent.frames[1].$("#tblIACSettingList").datagrid('reload');
			}else{
				$.messager.alert("错误", "保存失败", "error");
			}
		},
		error : function() {
			$.messager.alert("错误", "保存失败", "error");
		}
	}
	$.ajax(ajax_param);
}

function confirm(){
	saveData();
}

function cancle(){
    $('#popWin').window('close');
}

function rowBlurListener(){
	document.addEventListener('change', function(e) {
		if (e.target.className.indexOf("datagrid-editable-input")>=0 && $dg.datagrid("validateRow",editRow)) {
			$dg.datagrid("endEdit",editRow);
			editRow = undefined;
		}
	})
}

$(document).ready(function() {
	initCollectorListTable();
	initIACList('add','chainSettingList');
	rowBlurListener();
});