$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
//	$('#tblTomcatOperSituation').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblWebSphereOperSituation')
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
	      url : path + '/monitor/websphereManage/getWebSphereOperSituationInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
	        {
		         field : 'levelIcon',
		         title : '告警级别',
		         width : 80,
		         align : 'center',
		         formatter : function(value, row, index) {
			          return '<img src="'
						+ path
						+ '/style/images/levelIcon/'
						+ value
						+ '"/>';
			         }
	        },
	        {
		         field : 'useStatus',
		         title : '可用/持续时间',
		         width : 80,
		         align : 'center',
		         formatter:function(value,row){
						return '<img title="' + row.operaTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.operStatusName + '"/>'+row.durationTime;
				} 
	        },
	        {
		         field : 'avgAvailability',
		         title : '当月可用性',
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
		    $('#tblWebSphereOperSituation').resizeDataGrid(0, 0, 0, 0);
		});
	 }
