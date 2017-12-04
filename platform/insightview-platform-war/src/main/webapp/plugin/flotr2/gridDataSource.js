
/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	$('#example')
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
						fitColumns:true,
						url :'datagrid_data.json',
						
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						
						columns : [ [
								
						        {
						        	field : 'itemid',
						        	title : 'itemid',
									width : 30,
									align : 'center'
						        },
								{
									field : 'productid',
									title : '用户名',
									width : 30,
									align : 'center',
								},
								{
									field : 'listprice',
									title : '用户姓名',
									width : 30,
									align : 'center'
								},
								{
									field : 'unitcost',
									title : '手机号码',
									width : 30,
									align : 'center'
								},
								{
									field : 'attr1',
									title : '用户类型',
									width : 40,
									align : 'center'
									
								},
								{
									field : 'status',
									title : '自动锁定', 
									width : 20,
									align : 'center'
									
								}
								 ] ]
					});
   
}

