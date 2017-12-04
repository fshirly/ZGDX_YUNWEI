$(document).ready(function() {
	doInitTables();// 数据文件
	$('#tblDb2InsList').datagrid('hideColumn','moId');
});


// 数据文件
function doInitTables() {
	 var path = getRootName();
	 $('#tblDb2InsList')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 520,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/db2Manage/listInstance',
	      idField : 'fldId',
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      columns : [ [
			{
			    field : 'moId',
			    title : '',
			    width : 100,
			    align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			   },
	        {
	         field : 'ip',
	         title : '数据库服务IP',
	         width : 100,
	         align : 'center',
	         formatter : function(value, row, index) {
//					if(flag !=null && flag !=""){ 
//	        			return value;
//					}else {
						var to = "&quot;" + row.moid
						+ "&quot;,&quot;" + "oracle"
						+ "&quot;,&quot;" + value
						+ "&quot;"
						return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
//					}	
				}
	        },
	        {
	         field : 'port',
	         title : '端口',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'formatTime',
	         title : '启动时间',
	         width : 120,
	         align : 'center'
	        },
	        {
	         field : 'nodeNum',
	         title : '节点数',
	         width : 100,
	         align : 'center'
	        }
	        ] ]
	     });
	 $(window).resize(function() {
		    $('#tblDb2InsList').resizeDataGrid(0, 0, 0, 0);
		});
	 }


