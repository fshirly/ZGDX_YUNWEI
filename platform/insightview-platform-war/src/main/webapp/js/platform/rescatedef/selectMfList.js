$(document).ready(function() {
	doInitTable(); // 初始化表格
});

/*
 * 页面加载初始化表格 @author zhurt
 */
function doInitTable() {
	var path = getRootName();
	$('#tblManufacturer')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped :true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						url : path + '/platform/manufacturer/listManufacturer',  //web控制器中定义
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
								{
									field : 'resManuFacturerName',
									title : '厂商名称',
									width : 180,
									align : 'left',
									formatter : function(value, row, index) {
										 var to = "&quot;" + row.resManuFacturerId
											+ "&quot;,&quot;"
											+ row.resManuFacturerName
											+ "&quot;";									
										return '<a style="cursor: pointer;" onclick="javascript:sel('
											+ to
											+ ');">'+value+'</a>';
									}
								}
								,{
									field : 'contacts',
									title : '联系人',
									width : 90,
									align : 'center'
								},{
									field : 'contactsTelCode',
									title : '联系电话',
									width : 90,
									align : 'right'
								},{
									field : 'email',
									title : 'E-mail',
									width : 110,
									align : 'center'
								},
								{
									field : 'techSupportTel',
									title : '技术服务电话',
									width : 80,
									align : 'center'
								} ] ]
					});
}

function reloadTable(){
	var mfName=$("#txtFilterManufacturerName").val();
	$('#tblManufacturer').datagrid('options').queryParams = {
		"resManuFacturerName" : mfName
	};
	reloadTableCommon('tblManufacturer');
}

function sel(id,name){
	if(window.opener) { 
		fWindowText1 = window.opener.document.getElementById("ipt_resManufacturerID"); 
		fWindowText1.value = id; 
		fWindowText2 = window.opener.document.getElementById("ipt_resManufacturerName"); 
		fWindowText2.value = name; 
    	window.close();  
	 } 
}