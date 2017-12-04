$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
//	$('#tblTomcatOperSituation').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblWebMoudleInfos')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 120,
	      height : 'auto',
	      nowrap : false,
	      rownumbers:true,
	      striped : true,
	      border : true,
	      ssingleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/websphereManage/getWebMoudleInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
//			{
//			    field : 'moId',
//			    title : '对象标志',
//			    width : 80,
//			    align : 'center'
//			},
			{
		         field : 'servletName',
		         title : 'servlet名称',
		         width : 100,
		         align : 'center'
	        },
	        {
		         field : 'warName',
		         title : 'War包名称',
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
		         field : 'totalRequests',
		         title : '请求数',
		         width : 80,
		         align : 'left'
	        },
	        {
		         field : 'concurrentRequests',
		         title : '并发请求数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'numErrors',
		         title : '报错数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'responseTime',
		         title : '响应时间',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'uri',
		         title : '资源位置',
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
		    $('#tblWebMoudleInfos').resizeDataGrid(0, 0, 0, 0);
		});
	 }
