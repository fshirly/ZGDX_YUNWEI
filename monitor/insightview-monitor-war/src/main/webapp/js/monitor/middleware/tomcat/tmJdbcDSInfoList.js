$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doJdbcDSInitTables(moID,liInfo);// 数据源列表
	$('#tblJdbcDS').datagrid('hideColumn','moId');
});


// 数据源列表
function doJdbcDSInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblJdbcDS')
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
	      url : path + '/monitor/tomcatManage/getTmJdbcDSInfo?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
	       /* {
		         field : 'status',
		         title : '状态',
		         width : 80,
		         align : 'left'
	        },
	        {
		         field : 'dSName',
		         title : '数据源名称',
		         width : 80,
		         align : 'center'
	        },*/
	        {
		         field : 'jdbcDriver',
		         title : 'JDBC驱动',
		         width : 150,
		         align : 'left'
	        },
	        {
		         field : 'maxActive',
		         title : '最大连接数',
		         width : 70,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'minIdle',
		         title : 'Min空闲连接数',
		         width : 70,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'maxIdle',
		         title : 'Max空闲连接数',
		         width : 70,
		         align : 'left'
	        } ,
	        {
		         field : 'maxWait',
		         title : 'Max等待时间',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'dBUrl',
		         title : '数据库连接串',
		         width : 150,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'userName',
		         title : '用户名',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'passWord',
		         title : '密码',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'initialSize',
		         title : '初始大小',
		         width : 70,
		         align : 'left'
	        }
	        ] ],
			onLoadSuccess : function() {
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblJdbcDS').resizeDataGrid(0, 0, 0, 0);
		});
	 }
