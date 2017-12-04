$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
	$('#tblJdbcPoolInfos').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblJdbcPoolInfos')
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
	      url : path + '/monitor/websphereManage/getJdbcPoolInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '对象标志',
			    width : 80,
			    align : 'center'
			},
//	        {
//		         field : 'status',
//		         title : '状态',
//		         width : 80,
//		         align : 'center'
//	        },
	       /* {
		         field : 'status',
		         title : '状态',
		         width : 80,
		         align : 'center'
	        },*/
	        {
		         field : 'dsName',
		         title : '名称',
		         width : 80,
		         align : 'center'
	        },
//	        {
//		         field : 'jdbcDriver',
//		         title : 'JDBC驱动',
//		         width : 80,
//		         align : 'left'
//	        },
	      /*  {
		         field : 'jdbcDriver',
		         title : 'JDBC驱动',
		         width : 80,
		         align : 'left'
	        },*/
	        {
		         field : 'minConns',
		         title : '最小连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'maxConns',
		         title : '最大连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'curConns',
		         title : '当前连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'connTimeOut',
		         title : '连接超时',
		         width : 80,
		         align : 'center'
	        }
	        ] ],
			onLoadSuccess : function() {
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblJdbcPoolInfos').resizeDataGrid(0, 0, 0, 0);
		});
	 }
