var path = getRootName();
$(document).ready(function() {
	var flag = $("#flag").val();
	if (flag == "choose") {
		doInitChooseTable();
	} else {
		doInitTable();
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	$('#tblProcessList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/process/listProcessInfo',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'moId',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'moId',
						        	checkbox : true
						        },
						        {
									field : 'processName',
									title : '进程名称',
									width : 130,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 20){
							        		value2 = value.substring(0,20) + "...";
											 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
							        }
								},
								{
									field : 'processID',
									title : '进程号',
									width : 80,
									align : 'center',
								},
								{
									field : 'deviceIp',
									title : 'IP地址',
									width : 60,
									align : 'center'
								},
								{
									field : 'deviceName',
									title : '所属设备',
									width : 60,
									align : 'center'
								},
								{
									field : 'processState',
									title : '进程状态',
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return "运行中";
										} else if (value == 2) {
											return "可运行的";
										} else if (value == 3) {
											return "不可运行的";
										} else if (value == 4) {
											return "无效的";
										}
										
									}
								},
								{
									field : 'cpuValue',
									title : '占用CPU时间',
									width : 60,
									align : 'center'
								},
								{
									field : 'memValue',
									title : '占用内存大小',
									width : 60,
									align : 'center'
								},
								{
									field : 'processType',
									title : '进程类型',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return "未知";
										} else if (value == 2) {
											return "系统进程";
										} else if (value == 3) {
											return "设备驱动";
										} else if (value == 4) {
											return "应用进程";
										}
										
									}
								}
								 ] ]
					});
    $(window).resize(function() {
        $('#tblProcessList').resizeDataGrid(0, 0, 0, 0);
    });
}


function doInitChooseTable() {
	$('#tblProcessList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/process/listProcessInfo',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'moId',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'moId',
						        	title : '',
								    width : 100,
								    align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
						        },
						        {
									field : 'operStatus',
									title : '可用状态',
									width : 80,
									align : 'center',
									formatter:function(value,row){
									var t="unknown.png";;
									if(value=="UP"){
										t="up.png";
									}else if(value=="DOWN"){
										t="down.png";
									}
									return '<img title="' + value + '" src="' + path
											+ '/style/images/levelIcon/' + t + '"/>';
								} 
								},
								{
									field : 'alarmLevel',
									title : '告警级别',
									width : 80,
									align : 'center',
									formatter:function(value,row){
									var val = value;
									var t = row.levelIcon;
									if(val==0 || val==null || val==""){
										val="正常";
										t="right.png";
									}
									return '<img src="' + path
											+ '/style/images/levelIcon/' + t + '"/>' + val;
								} 
								},
								{
									field : 'processID',
									title : 'PID',
									width : 80,
									align : 'center',
								},
								{
									field : 'processName',
									title : '进程名称',
									width : 130,
									align : 'center'
								},
								{
									field : 'processType',
									title : '进程类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'deviceIp',
									title : '对应主机IP',
									width : 60,
									align : 'center'
								} ] ]
					});
    $(window).resize(function() {
        $('#tblProcessList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblProcessList').datagrid('options').queryParams = {
		"deviceIp" : $("#txtDeviceIp").val(),
		"processName" : $("#txtProcessName").val()
	};
	reloadTableCommon_1('tblProcessList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moId){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_processMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findProcessInfo();
	     window.close();
	} 

}