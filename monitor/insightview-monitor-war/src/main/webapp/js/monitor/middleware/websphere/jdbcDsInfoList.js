$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
	$('#tblJdbcDsInfos').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblJdbcDsInfos')
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
	      url : path + '/monitor/websphereManage/getJdbcDsInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '对象标志',
			    width : 80,
			    align : 'center'
			},
	        {
		         field : 'dsName',
		         title : '数据源名称',
		         width : 80,
		         align : 'center',
		         formatter : function(value, row, index) {
					if(value == null){
						return value;
					}else{
						return '<a style="cursor: pointer;" onclick="javascript:toShowDetail('
						+ row.moId
						+ ');">'+value+'</a>'; 
					}
		        			
				}
	        },
//	        {
//		         field : 'jdbcDriver',
//		         title : 'JDBC驱动',
//		         width : 80,
//		         align : 'center'
//	        },
//	        {
	       /* {
		         field : 'jdbcDriver',
		         title : 'JDBC驱动',
		         width : 80,
		         align : 'center'
	        },*/
	        {
		         field : 'initialSize',
		         title : '初始大小',
		         width : 80,
		         align : 'left'
	        },
	        {
		         field : 'maxActive',
		         title : '最大连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'minIdle',
		         title : '最小空闲连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'maxIdle',
		         title : '最大空闲连接数',
		         width : 120,
		         align : 'center'
	        },
	        {
		         field : 'maxWait',
		         title : '最长等待连接时间',
		         width : 120,
		         align : 'center'
	        }
	        ] ],
			onLoadSuccess : function() {
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblJdbcDsInfos').resizeDataGrid(0, 0, 0, 0);
		});
	 }


function toShowDetail(moId){
	//查看配置项页面
	var isExistPop = parent.parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.parent.$('#popWin').window({
	    	title:'数据源信息',
	        width:800,
	        height:400,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/websphereManage/toShowJdbcDsDetail?moID='+moId
	    });
	}else{
		parent.$('#popView').window({
	    	title:'数据源信息',
	        width:800,
	        height:400,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/websphereManage/toShowJdbcDsDetail?flag=1&moID='+moId
	    });
	}
	
	
//	var url = getRootName()+'/monitor/websphereManage/toShowJdbcDsDetail?moID='+moId;
//	window.open(url,"","height=300px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
}