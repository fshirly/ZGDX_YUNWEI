/**
 * opt //页面操作
 * tableId 
 */
var $dg; // 定义全局变量datagrid
var editRow = undefined; // 定义全局变量：当前编辑的行
var initIACList = function(opt,tableId) {
	var columns = [ [ {
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
//		url : getRootName()+'/monitor/deviceWBListSetting/listPortByDeviceIP?deviceip='+deviceIP+"&opt="+opt+"&type="+type,
		url : getRootName()+'/monitor/internetAccessChainSetting/listChainNameAndGateWayByDeviceIP?deviceip='+deviceIP+"&opt="+opt,
		remoteSort : false,
		idField : 'port',
		singleSelect : false, // 是否单选
		pagination : false, // 分页控件
		rownumbers : true,
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
}
		
function confirm(){
	$('#popWin').window('close');
}

function cancle(){
    $('#popWin').window('close');
}

function initBaseInfo() {
	var selectedRow = JSON.parse($("#selectedRow").val());
	$("#deviceIP").text(selectedRow[0].deviceIP);// 设置设备IP
	$("#connIPAddresss").text(selectedRow[0].connIPAddresss);// 设置设备IP
	var statusForDeviceText = '';
	switch (selectedRow[0].oprateStatus) {
	case "1":
		statusForDeviceText =  "启用";
		break;
	case "0":
		statusForDeviceText =  "不启用";
		break;
	default:
		break;
	}
	$("#operateStatus").text(statusForDeviceText);
	$("#collector").text(selectedRow[0].collectorIPAddr);
}

$(document).ready(function() {
	initBaseInfo();
	initIACList('show','chainSettingList');
});