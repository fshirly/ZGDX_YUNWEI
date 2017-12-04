$(document).ready(function() {
	var moID = $("#moId").val();
	doDBInitTables(moID);// 数据库列表
//	$('#tblWebEjbMoudleInfos').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID) {
	 var path = getRootName();
	 $('#tblWebEjbMoudleInfos')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 120,
	      height : 'auto',
	      nowrap : false,
	      rownumbers:true,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/websphereManage/getWebEjbMoudleInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '对象标志',
			    width : 80,
			    align : 'center'
			},
	        {
		         field : 'ejbName',
		         title : 'EJB模块名称',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'status',
		         title : '状态',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'uri',
		         title : '资源位置',
		         width : 80,
		         align : 'center'
	        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblWebEjbMoudleInfos').resizeDataGrid(0, 0, 0, 0);
		});
	 }
