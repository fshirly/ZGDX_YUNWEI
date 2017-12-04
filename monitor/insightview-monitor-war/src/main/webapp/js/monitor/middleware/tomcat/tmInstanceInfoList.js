$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
	$('#tblInstance').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblInstance')
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
	      url : path + '/monitor/tomcatManage/getTmInstanceInfo?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
			{
				  field : 'moId',
				  title : '设备Id',
				  width : 80,
				  align : 'center'
			},
	        {
		         field : 'warName',
		         title : '名称',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'uri',
		         title : '资源位置',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'status',
		         title : '状态',
		         width : 80,
		         align : 'left'
	        }
	        ] ],
			onLoadSuccess : function() {
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblInstance').resizeDataGrid(0, 0, 0, 0);
		});
	 }
