var flag='';
$(document).ready(function() {
	flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
		$('#tblZoneManager').datagrid('showColumn','moID');
	}else{
		$('#tblZoneManager').datagrid('hideColumn','moID');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblZoneManager')
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
						url : path + '/monitor/perfTask/selectZoneManagerList?&moClassId='+$("#moClassId").val(),
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
									field : 'ipAddress',
									title : 'Zone Manager IP', 
									width : 100,
									align : 'center',
								},
								{
									field : 'port',
									title : '端口', 
									width : 80,
									align : 'center',
								},
								{
									field : 'moClassName',
									title : '对象类型', 
									width : 80,
									align : 'center',
								},
								{
									field : 'domainName',
									title : '管理域', 
									width : 100,
									align : 'center',
								}]]
					});
		$(window).resize(function() {
		    $('#tblZoneManager').resizeDataGrid(0, 0, 0, 0);
		});
}

/**
 * 更新表格数据
 * @return
 */
function reloadTable() { 
	var ip = $("#ip").val(); 
	$('#tblZoneManager').datagrid('options').queryParams = {
		"ipAddress" : ip
	};
	reloadTableCommon_1('tblZoneManager');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 选择 
 * @return
 */
function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_zoneManagerId"); 
	     fWindowText1.value = moid; 
    	 window.opener.findZoneManagerInfo();
	     window.close();
	} 
}  

