$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblRoomList').datagrid('hideColumn','moID');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var parentID = $("#parentID").val();
	if(parentID =="" || parentID == null){
		parentID = -1;
	}
	var path = getRootName();
	var uri = path + '/monitor/envManager/listRoom?parentID='+parentID;
	$('#tblRoomList')
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
						remoteSort : true,
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
								    width : 100,
								    align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								},
								{
									field : 'readerID',
									title : '阅读器编号',
									width : 50,
									align : 'center',
								 
								 },
								{
									field : 'readerLabel',
									title : '阅读器名称',
									width : 80,
									align : 'center',
								 
								 },
								{
									field : 'iPAddress',
									title : '阅读器IP',
									width : 100,
									align : 'center'
								},
								{
									field : 'port',
									title : '端口',
									width : 60,
									align : 'center'
								},
								{
									field : 'enabled',
									title : '是否启用',
									width : 80,
									align : 'center'
								},
								{
									field : 'firmwareVersion',
									title : '固件版本',
									width : 80,
									align : 'center'
								},
								{
									field : 'deviceIP',
									title : 'Zone Manger IP',
									width : 120,
									align : 'center'
								},
								{
									field : 'connectionEncrypted',
									title : '连接是否加密',
									width : 80,
									align : 'center'
								},
								{
									field : 'moClassId',
									title : '类型',
									hidden:true,//隐藏
									width :.80
								},
								{
									field : 'moID',
									title : 'ID',
									hidden:true,//隐藏
									width :.80
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblRoomList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblRoomList').datagrid('options').queryParams = {
		"readerName" : $("#readerName").val(),
		"iPAddress" : $("#iPAddress").val()
	};
	reloadTableCommon_1('tblRoomList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}


function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_moReadMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMOReadInfo();
	     window.close();
	} 
} 