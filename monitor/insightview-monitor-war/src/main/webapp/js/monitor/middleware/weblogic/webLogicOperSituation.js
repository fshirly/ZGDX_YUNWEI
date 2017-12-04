$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// WebLogic列表
});


// WebLogic列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblWebLogicOperSituation')
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
	      url : path + '/monitor/weblogicManage/getWebLogicOperSituationInfos?moID='+moID,
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
		         formatter : function(value, row, index) {
	        	return '<img title="' + row.operaTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.operStatusName + '"/>'+row.durationTime;
			          
			         }
	        },
	        {
		         field : 'upTime',
		         title : '已运行时间',
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
		    $('#tblWebLogicOperSituation').resizeDataGrid(0, 0, 0, 0);
		});
	 }
