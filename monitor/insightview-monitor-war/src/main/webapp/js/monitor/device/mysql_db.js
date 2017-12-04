$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblDataList').datagrid('hideColumn','moId');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var path = getRootName();
	var sqlServerMOID = $("#sqlServerMOID").val();
	if(sqlServerMOID == "" || sqlServerMOID == null || sqlServerMOID == "null"){
		sqlServerMOID = -1;
	}
	var uri = path + '/monitor/dbObjMgr/listMysqlDB?sqlServerMOID='+sqlServerMOID;
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
										field : 'moId',
										title : '',
										width : 40,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
										}
								},
								{
									field : 'ip',
									title : 'IP',
									width : 80,
									sortable:true,
									align : 'center'									
								},
								{
									field : 'serverName',
									title : '所属数据库服务',
									width : 120,
									align : 'center'									
								},
								{
									field : 'dbName',
									title : '数据库名称',
									width : 80,
									align : 'center'
								}								
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
		"serverName" : $("#serverName").val(),
		"dbName" : $("#dbName").val()
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_mysqlDBId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMysqlDBInfo();
	     window.close();
	} 
}  