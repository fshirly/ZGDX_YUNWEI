$(document).ready(function() {	
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblSelectDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						url : path + '/platform/resCateDefine/listResCateDef',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'resCategoryName',
									title : '产品目录名称',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
									 var to = "&quot;" + row.resCategoryID
										+ "&quot;,&quot;"
										+ row.resCategoryName
										+ "&quot;";									
									return '<a style="cursor: pointer;" onclick="javascript:sel('
										+ to
										+ ');">'+value+'</a>';
									}
								},
								{
									field : 'resCategoryAlias',
									title : '产品别名',
									width : 150,
									align : 'center',
									sortable : true
								},
								{
									field : 'resModel',
									title : '设备型号',
									width : 100,
									align : 'center',									
									sortable : true
								},
								{
									field : 'price',
									title : '参考价格（RMB：元）',
									width : 100,
									align : 'center',									
									sortable : true
								},								
								{
									field : 'warrantyPeriod',
									title : '质保期（个月）',
									width : 100,
									align : 'center',
									sortable : true
								}				
								] ]
					});
}

function reloadTable() { 
		var resCategoryName=$("#resCategoryName").val();
		$('#tblSelectDataList').datagrid('options').queryParams = {
			"resCategoryName" : resCategoryName
		};
		reloadTableCommon('tblSelectDataList');
}

function sel(id,name){
	if(window.opener) { 
		fWindowText1 = window.opener.document.getElementById("ipt_resCategoryID"); 
		fWindowText1.value = id; 
		fWindowText2 = window.opener.document.getElementById("ipt_resCategoryName"); 
		fWindowText2.value = name; 
    	window.close(); 
	 } 
}