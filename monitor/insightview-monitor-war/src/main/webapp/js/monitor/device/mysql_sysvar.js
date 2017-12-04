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
	var uri = path + '/monitor/dbObjMgr/listMysqlSysVar?sqlServerMOID='+sqlServerMOID;
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
									width : 120,
									sortable:true,
									align : 'center'									
								},
								{
								    field : 'varName',
								    title : '变量名称',
								    width : 100,
								    align : 'center'
								
								}
								,
						        {
							         field : 'varValue',
							         title : '变量值',
							         width : 100,
							         align : 'center'	 
						        }
						        ,
						        {
							         field : 'varChnName',
							         title : '变量中文名称',
							         width : 100,
							         align : 'center',
						        }
						        ,
						        {
							         field : 'dynamicVarType',
							         title : '动态系统变量类型',
							         width : 100,
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
	var varName = $("#selName").val();
	var opera = $("#opera").val();
	var txtValue= $("#txtValue").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"varName" : varName,
		"opera" : opera,
		"txtValue" : txtValue,
	};
	reloadTableCommon_1('tblDataList');        
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_mysqlSysVarId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMysqlSysVarInfo();
	     window.close();
	} 
} 