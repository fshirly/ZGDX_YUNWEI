$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
		$('#tblMONetworkIf').datagrid('showColumn','moID');
	}else{
		$('#tblMONetworkIf').datagrid('hideColumn','moID');
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var deviceMOID = $("#deviceMOID").val();
	var path = getRootName();
	$('#tblMONetworkIf')
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
						url : path + '/monitor/alarmmgr/moKPIThreshold/selectNetworkIf?deviceMOID='+deviceMOID,
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
									field : 'ifName',
									title : '接口名称',  
									width : 40,
									align : 'center',
									sortable : true,
								},
								{
									field : 'instance',
									title : '接口索引', 
									width : 30,
									align : 'center',
									sortable : true,
								},
								{
									field : 'ifType',
									title : '接口类型', 
									width : 30,
									align : 'center',
									sortable : true,
								},
								{
									field : 'ifMtu',
									title : '接口最大传输单元', 
									width : 50,
									align : 'center',
									sortable : true,
								},
								{
									field : 'ifAdminStatus',
									title : '接口管理状态',
									width : 40,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if (value == 1) {
											return '已被管';
										}else{
											return '未被管';
										}
									}
								},
								{
									field : 'ifOperStatus',
									title : '接口操作状态',
									width : 40,
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
									field : 'ifDescr',
									title : '接口描述', 
									width : 70,
									align : 'center',
									sortable : true,
								}]]
					});
		$(window).resize(function() {
		    $('#tblMONetworkIf').resizeDataGrid(0, 0, 0, 0);
		});
}


function reloadTable() { 
	var ifName = $("#txtIfName").val(); 
	$('#tblMONetworkIf').datagrid('options').queryParams = {
		"ifName" : ifName
	};
	reloadTableCommon_1('tblMONetworkIf');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	var index =$('#index').val();
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_networkIfId"); 
	     fWindowText1.value = moid; 
	     window.opener.findNetworkIfInfo();
	     window.close();
	} 
}  



