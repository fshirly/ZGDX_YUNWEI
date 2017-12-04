$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doJdbcDSInitTables(moID,liInfo);// 数据源列表
	$('#tblThreadPool').datagrid('hideColumn','moId');
});


// 数据源列表
function doJdbcDSInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblThreadPool')
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
	      url : path + '/monitor/websphereManage/getWasThreadPoolInfo?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
	        {
		         field : 'threadName',
		         title : '池名称',
		         width : 120,
		         align : 'left'
	        },
	        {
		         field : 'maxConns',
		         title : '最大线程数',
		         width : 80,
		         align : 'left'
	        },
	        {
		         field : 'minConns',
		         title : '最小线程数',
		         width : 80,
		         align : 'left'
	        },
	        {
		         field : 'inactivityTimeOut',
		         title : '线程不活跃超时',
		         width : 80,
		         align : 'left'
	        }
	        ,
	       /* {
		         field : 'growable',
		         title : '是否允许超出最大尺寸',
		         width : 80,
		         align : 'left'
	        }
	        ,*/
	        {
		         field : 'createThreads',
		         title : '创建线程数',
		         width : 80,
		         align : 'left'
	        } ,
	        {
		         field : 'activeThreads',
		         title : '活跃线程数',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'destroyThreads',
		         title : '销毁线程数',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'poolSize',
		         title : '线程池大小',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'percentMaxed',
		         title : '线程全部使用百分比',
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
		    $('#tblThreadPool').resizeDataGrid(0, 0, 0, 0);
		});
	 }

