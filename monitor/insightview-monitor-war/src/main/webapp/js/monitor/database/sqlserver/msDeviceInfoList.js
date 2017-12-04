$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
	$('#tblDevice').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID) {
	var type = $("#type").val();
	 var path = getRootName();
	  var uri=path;
	 if(type=="sybase"){
	 	 uri+='/monitor/sybaseManage/getServiceInfoList?moID='+moID
	 }else{
	 	uri+='/monitor/msManage/getServiceInfoList?moID='+moID
	 }
	 $('#tblDevice')
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
	      url : uri,
	      idField : 'fldId',
	      columns : [ [
	        {
		         field : 'serverMoId',
		         title : '设备编号',
		         width : 40,
		         align : 'left'
	        },
	        {
		         field : 'deviceName',
		         title : '设备名称',
		         width : 40,
		         align : 'left'
	        },
	        {
		         field : 'physicalName',
		         title : '设备物理名称',
		         width : 100,
		         align : 'left'
	        },
	        {
		         field : 'totalSize',
		         title : '总空间大小',
		         width : 40,
		         align : 'left'
	        },
	        {
		         field : 'usedSize',
		         title : '已用空间',
		         width : 40,
		         align : 'left'
	        },
	        {
		         field : 'freeSize',
		         title : '空闲空间',
		         width : 40,
		         align : 'left'
	        },
	        {
		         field : 'deviceDescr',
		         title : '设备描述',
		         width : 200,
		         align : 'left'
	        }
	        ] ],
	        onLoadSuccess : function() {
				$('.easyui-progressbar').progressbar().progressbar(
						'setColor');
				//自适应部件大小
		 		window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblDevice').resizeDataGrid(0, 0, 0, 0);
		});
	 }
