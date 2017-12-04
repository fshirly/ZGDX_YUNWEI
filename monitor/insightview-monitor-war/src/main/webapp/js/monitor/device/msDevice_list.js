$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag == "null" || flag =="" || flag ==null){ 
		$('#tblMsDeviceList').datagrid('hideColumn','moId');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var path = getRootName();
	var serverMoId = $("#serverMoId").val();
	if(serverMoId != null && serverMoId != ""){
		var uri = path + '/monitor/msDbManage/listMsDeviceInfos?serverMoId='+serverMoId;
	}else{
		var uri = path + '/monitor/msDbManage/listMsDeviceInfos';
	}
	$('#tblMsDeviceList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : uri,
						remoteSort : false,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
										{
												field : 'moId',
												title : '',
												width : 40,
												align : 'center',
												formatter : function(value, row, index) {
													return '<a style="cursor: pointer;" onclick="javascript:sel('+ value +');">选择</a>';
												}
										},
										{
											field : 'deviceName',
											title : '设备名称',
											width : 100,
											align : 'center'
										 },
											{
												field : 'physicalName',
												title : '物理名称',
												width : 100,
												align : 'center'
											 },
										 {
												field : 'ip',
												title : '服务IP',
												width : 80,
												sortable:true,
												align : 'center'
										},
										{
											field : 'deviceDescr',
											title : '设备描述',
											width : 80,
											align : 'center'
										},
										{
											field : 'totalSize',
											title : '总大小',
											width : 80,
											align : 'center'
										},
										{
											field : 'usedSize',
											title : '使用大小',
											width : 80,
											align : 'center'
										},
										{
											field : 'freeSize',
											title : '空闲大小',
											width : 80,
											align : 'center'
										}
										
										] ]
					});
	$(window).resize(function() {
        $('#tblMsDeviceList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblMsDeviceList').datagrid('options').queryParams = {
		"deviceName" : $("#deviceName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblMsDeviceList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moId){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_msDeviceMoId"); 
	     fWindowText1.value = moId; 
	     window.opener.findMsDeviceInfo();
	     window.close();
	} 
}  