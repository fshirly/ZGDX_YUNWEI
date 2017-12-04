$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblDataList').datagrid('hideColumn','moID');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var mOClassID = $("#mOClassID").val();
	var path = getRootName();
	var uri = path + '/monitor/safeDeviceManager/listCpu?mOClassID=' + mOClassID;
	$('#tblDataList')
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
						        	field : 'moID',
						        	title : '',
						        	width : 40,
						        	align : 'center',
						        	formatter : function(value, row, index) {
						        		return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
						        	}
						        },
								{
									field : 'moName',
									title : '处理器名称',
									width : 80,
									align : 'center'
								},
								{
									field : 'deviceMOName',
									title : '所属设备',
									width : 80,
									align : 'center'
								},
								{
									field : 'deviceIP',
									title : '所属设备IP地址',
									width : 80,
									align : 'center',
									sortable:true,
								},
								{
									field : 'resManufacturerName',
									title : '设备厂商',
									width : 80,
									align : 'center'
								},
								{
									field : 'classLable',
									title : '设备型号',
									width : 80,
									align : 'center'
								},
								{
									field : 'cPUInfo',
									title : '频率',
									width : 80,
									align : 'center'
								}
								/*{
									field : 'adminStatusName',
									title : '最近一次操作类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'instance',
									title : '实例号',
									width : 80,
									align : 'center'
								}*/
								/**,
								{
									field : 'moAlias',
									title : '处理器别名',
									width : 80,
									align : 'center'
								}**/
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"mOClassID" : $("#mOClassID").val(),
		"deviceMOName" : $("#deviceMOName").val(),
		"deviceIP" :$("#deviceIP").val(),
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}