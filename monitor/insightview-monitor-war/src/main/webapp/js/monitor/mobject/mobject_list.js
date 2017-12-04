$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
		$('#tblMObject').datagrid('showColumn','classId');
	}else{
		$('#tblMObject').datagrid('hideColumn','classId');
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblMObject')
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
						url : path + '/monitor/alarmmgr/moKPIThreshold/selectMObject',
						remoteSort : true,
						onSortColumn:function(sort,order){
							// alert("sort:"+sort+",order："+order+"");
						},
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						
						columns : [ [
						        {
									field : 'classId',
									title : '',
									width : 70,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('
												+ value
												+ ');">选择</a>';
									}
						        },
								{  
									field : 'className',
									title : '对象类名称',  
									width : 40,
									align : 'center',
									sortable : true,
								},
								{
									field : 'classLable',
									title : '对象类标题',
									width : 50,
									align : 'center',
									sortable : true,
								},
								{
									field : 'descr',
									title : '描述', 
									width : 80,
									align : 'center',
									sortable : true,
								},
								{
									field : 'attributeTableName',
									title : '对象属性表名',
									width : 40,
									align : 'center',
									sortable : true,
								}]]
					});
}
/*
 * 更新表格
 */
function reloadTable(){
	var className = $("#txtClassName").val();
	$('#tblMObject').datagrid('options').queryParams = {
		"className" : className
	};
	reloadTableCommon_1('tblMObject');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
		 fWindowText1 = window.opener.document.getElementById("ipt_classID");
	     fWindowText1.value = moid; 
	     window.opener.findMObjectDefInfo();
	     window.close();
	} 
}
