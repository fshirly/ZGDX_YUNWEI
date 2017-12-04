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
						width : 780,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						url : path + '/platform/snmpPen/listSnmpPen',
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
									field : 'pen',
									title : 'PEN',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('
												+ value
												+ ');">'+value+'</a>';
									}
								},
								{
									field : 'enterpriseOid',
									title : '企业OID',
									width : 100,
									align : 'center',
									sortable : true
								},
								{
									field : 'organization',
									title : '企业名称',
									width : 100,
									align : 'center',									
									sortable : true
								},
								{
									field : 'orgAddress',
									title : '企业地址',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'postCode',
									title : '邮政编码',
									width : 80,
									align : 'center',
									sortable : true
								},
								
								{
									field : 'contactTelephone',
									title : '联系电话',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'contactPerson',
									title : '联系人',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'orgEmail',
									title : 'Email',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'resManufacturerName',
									title : '关联厂商',
									width : 80,
									align : 'center',									
									sortable : true
								}								
								] ]
					});
}

function reloadTable() { 
		var organizationName=$("#organizationName").val();
		$('#tblSelectDataList').datagrid('options').queryParams = {
			"organization" : organizationName
		};
		reloadTableCommon('tblSelectDataList');
}

function sel(id){
	if(window.opener) { 
		fWindowText1 = window.opener.document.getElementById("ipt_pen"); 
		fWindowText1.value = id; 
    	window.close(); 
	 } 
}