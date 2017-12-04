$(document).ready(function() {
	doInitTable();
});


/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
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
						url : path + '/platform/resTypeDefine/listResTypeDefine',
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
									field : 'resTypeName',
									title : '类型名称',
									width : 180,
									align : 'left',
									formatter : function(value, row, index) {
										 var to = "&quot;" + row.resTypeID
											+ "&quot;,&quot;"
											+ row.resTypeName
											+ "&quot;";									
										return '<a style="cursor: pointer;" onclick="javascript:sel('
											+ to
											+ ');">'+value+'</a>';
									}
								},
								{
									field : 'resTypeAlias',
									title : '类型别名',
									width : 180,
									align : 'center',
								},
								{
									field : 'resTypeDescr',
									title : '类型描述',
									width : 200,
									align : 'center',									
								},
								{
									field : 'icon',
									title : '图标',
									width : 100,
									align : 'center',									
								},
								{
									field : 'createTime',
									title : '创建时间',
									width : 100,
									align : 'center',									
								} ] ]
					});
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	var resTypeName=$("#resTypeName").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"resTypeName" : resTypeName
	};
	reloadTableCommon('tblDataList');
}

function sel(id,name){
	if(window.opener) { 
		fWindowText1 = window.opener.document.getElementById("ipt_resTypeID"); 
		fWindowText1.value = id; 
		fWindowText2 = window.opener.document.getElementById("ipt_resTypeName"); 
		fWindowText2.value = name; 
    	window.close();  
	 } 
}
