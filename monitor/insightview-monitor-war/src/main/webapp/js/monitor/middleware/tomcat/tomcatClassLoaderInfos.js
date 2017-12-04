$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
	$('#tblTomcatClassLoad').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblTomcatClassLoad')
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
	      url : path + '/monitor/tomcatManage/getTomcatClassLoaderInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
	                   {
	      		         field : 'moId',
	      		         title : '资源标识',
	      		         width : 80,
	      		         align : 'center'
	      	        },
	        {
		         field : 'totalLoadedClasses',
		         title : '当前总类数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'unloadedClasses',
		         title : '已卸载类总数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'loadedClasses',
		         title : '已加载类总数',
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
		    $('#tblTomcatClassLoad').resizeDataGrid(0, 0, 0, 0);
		});
	 }
