$(document).ready(function() {
	var moID = $("#moId").val();
	doDBInitTables(moID);// 数据库列表
	$('#tblDB').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID) {
	 var path = getRootName();
	 $('#tblDB')
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
	      url : path + '/monitor/myManage/getMyDBInfo?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
	        {
		         field : 'dbName',
		         title : '数据库名称',
		         width : 80,
		         align : 'left'
	        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblDB').resizeDataGrid(0, 0, 0, 0);
		});
	 }
