$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
		$('#tblMOCPUs').datagrid('showColumn','moID');
	}else{
		$('#tblMOCPUs').datagrid('hideColumn','moID');
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var deviceMOID = $("#deviceMOID").val();
	var path = getRootName();
	$('#tblMOCPUs')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/alarmmgr/moKPIThreshold/selectCPUsList?deviceMOID='+deviceMOID,
						remoteSort : true,
						onSortColumn:function(sort,order){
							// alert("sort:"+sort+",order："+order+"");
						},
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
									field : 'moID',
									title : '',
									width : 20,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('
												+ value
												+ ');">选择</a>';
									}
						        },
								{  
									field : 'moName',
									title : 'CPU名称',  
									width : 40,
									align : 'center',
									sortable : true,
								},
								{
									field : 'operStatus',
									title : '可用状态',
									width : 30,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if (value == 1) {
											return 'UP';
										}else if (value == 2) {
											return 'DOWN';
										}else{
											return '未知';
										}
									}
								},
								{
									field : 'alarmLevel',
									title : '告警级别', 
									width : 40,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if (value == 1) {
											return '紧急';
										}else if (value == 2) {
											return '严重';
										}else if(value == 3) {
											return '一般';
										}else if(value == 4) {
											return '提示';
										}else if(value == 5) {
											return '未确定';
										}else{
											return '正常';
										}
									}
								},{
									field : 'deviceMOName',
									title : '所属设备', 
									width : 90,
									align : 'center',
									sortable : true,
								},{
									field : 'instance',
									title : 'CPU实例号', 
									width : 30,
									align : 'center',
									sortable : true,
								},
								{
									field : 'mIBModule',
									title : '所属MIBModule', 
									width : 40,
									align : 'center',
									sortable : true,
								}]]
					});
		$(window).resize(function() {
		    $('#tblMOCPUs').resizeDataGrid(0, 0, 0, 0);
		});
}


function reloadTable() { 
	var moName = $("#txtMOName").val(); 
	$('#tblMOCPUs').datagrid('options').queryParams = {
		"moName" : moName
	};
	reloadTableCommon_1('tblMOCPUs');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	var index =$('#index').val();
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_cpusId"); 
	     fWindowText1.value = moid; 
	     window.opener.findCPUsInfo();
	     window.close();
	} 
}  



