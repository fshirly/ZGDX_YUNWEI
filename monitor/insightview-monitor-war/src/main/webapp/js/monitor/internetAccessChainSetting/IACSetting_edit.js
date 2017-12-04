/**
 * 初始化采集机列表
 */
var ii;
function initCollectorListTable(selectedCollectorID) {
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
			align : 'center',
			formatter: function(value,row,index){
				if(row.serverid==selectedCollectorID){
					ii = index;
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
		onSelect : function(rowIndex,rowData){
			$("#collectorListDiv").attr('selectedCollector',rowData.serverid);
		},
		onLoadSuccess : function(data){
			$(this).parent().find(".datagrid-header-row > td[field='serverid']").find("input").hide();
			if(ii!=undefined){
				$(this).datagrid('checkRow', ii);
				$(this).datagrid('selectRow', ii);
			}
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
		} else {
			$.messager.alert("提示", "请选择要删除的行", "info");
		}
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
	var deviceIP = $("#deviceIP").text();

	$dg = $('#'+tableId).datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		fitColumns : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		url : getRootName()+'/monitor/internetAccessChainSetting/listChainNameAndGateWayByDeviceIP?deviceip='+deviceIP+"&opt="+opt,
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
	if(!checkInfo("#infoEditDiv")){
		return;
	}
	if(!$("#collectorsList").datagrid("getChecked").length){
		$.messager.alert("提示", "请选择采集机", "info");
		return;
	}
	if(!$dg.datagrid('validateRow',editRow)){
		return;
	};
	$dg.datagrid('endEdit',editRow);
	var insertedRows = $dg.datagrid("getChanges","inserted");
	var deletedRows = $dg.datagrid("getChanges","deleted");
	if (insertedRows.length) {
		var ajax_param = {
			url : getRootName()
					+ '/monitor/internetAccessChainSetting/addIACSettings',
			data : {
				"insertedRows" : JSON.stringify(insertedRows),
				"collectorId" : function() {
					return $("#collectorsList").datagrid("getChecked")[0].serverid
				},
				"deviceIP" : $("#deviceIP").text(),
				"connIPAddresss" : $("#connIPAddresss").val(),
				"operateStatus" : $("input[type='radio'][name$='Device']:checked").val()
			},
			type : 'post',
			dataType : 'json',
			async:false,
			success : function(data) {
				if (data.success == "true") {
				} else {
					$.messager.alert("错误", "链路新增失败", "error");
				}
			},
			error : function() {
				$.messager.alert("错误", "链路新增失败", "error");
			}
		}
		$.ajax(ajax_param);
	}
	if (deletedRows.length) {
		var ajax_param = {
			url : getRootName()
					+ '/monitor/internetAccessChainSetting/deleteChainSetting',
			data : {
				"deletedRows" : JSON.stringify(deletedRows)
			},
			type : 'post',
			dataType : 'json',
			async:false,
			success : function(data) {
				if (data.success == "true") {
				} else {
					$.messager.alert("错误", "链路删除失败", "error");
				}
			},
			error : function() {
				$.messager.alert("错误", "链路删除失败", "error");
			}
		}
		$.ajax(ajax_param);
	}
	confirmOperateStatusForDevice();
}

function confirm(){
	saveData();
}

function cancle(){
    $('#popWin').window('close');
}

function initBaseInfo() {
	var selectedRow = JSON.parse($("#selectedRow").val());
	$("#deviceIP").text(selectedRow[0].deviceIP);// 设置设备IP
	$("#connIPAddresss").val(selectedRow[0].connIPAddresss);// 设置设备IP
	$("#connIPAddresss").attr("connIPAddresssOldVal",selectedRow[0].connIPAddresss);// 设置设备IP
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
	initCollectorListTable(selectedRow[0].collectorID);
}

/**
 * 测试连接地址、启用状态和采集机更改
 */
function confirmOperateStatusForDevice(){
	var selectedRow = JSON.parse($("#selectedRow").val());//从父页面带过来的行
	var operateStautsOldVal = selectedRow[0].oprateStatus;
	var operateStatusNewVal = $("input[type='radio'][name$='Device']:checked").val();
	var collectorOldVal = selectedRow[0].collectorID;
	if($('#collectorsList').datagrid('getChecked').length){
		var collectorNewVal = $('#collectorsList').datagrid('getChecked')[0].serverid;
	}else{
		var collectorNewVal = collectorOldVal;
	}
	var connIPAddresssNewVal = $("#connIPAddresss").val();
	var connIPAddresssOldVal = $("#connIPAddresss").attr("connIPAddresssOldVal");
        $.ajax({
                url: getRootName() + '/monitor/internetAccessChainSetting/editIACSettings',
                data: {
                    "selectedRows": $("#selectedRow").val(),
                    "operateStatusForDevice": operateStatusNewVal,
                    "collectorForDevice":collectorNewVal,
                    "oldCollector":collectorOldVal,
                    "connIPAddresss":$("#connIPAddresss").val(),
                    "connIPAddresssChanged":connIPAddresssNewVal!=connIPAddresssOldVal,
                    "operateStatusChanged":operateStatusNewVal!=operateStautsOldVal,
                    "collectorChanged":collectorNewVal!=collectorOldVal
                },
                type: 'post',
                async:false,
                dataType: 'json',
                success: function(data) {
                	parent.frames[1].$("#tblIACSettingList").datagrid('reload');
                	$('#popWin').window('close');
                },
                error: function() {
                    $.messager.alert("提示", "互联网接入链路启用状态或采集机修改失败", "error");
                }
        });
}

$(document).ready(function() {
	initBaseInfo();
	initIACList('edit','chainSettingList');
});