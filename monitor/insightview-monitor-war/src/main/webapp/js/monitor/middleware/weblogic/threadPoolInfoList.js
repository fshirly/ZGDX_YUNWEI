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
	      url : path + '/monitor/weblogicManage/getWebLogicThreadPoolInfo?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
	        {
		         field : 'threadName',
		         title : '池名称',
		         width : 120,
		         align : 'left'
	        },
	        {
		         field : 'executeThreadTotal',
		         title : '线程总数',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'executeThreadIdle',
		         title : '空闲线程数',
		         width : 80,
		         align : 'left'
	        } ,
	        {
		         field : 'pendingUserRequest',
		         title : '暂挂用户请求数',
		         width : 80,
		         align : 'left'
	        },
	        {
		         field : 'threadPoolUsage',
		         title : '线程池使用率',
		         width : 80,
		         align : 'left',
		         formatter : function(value, row, index) {
						var color = (value <= 75) ? '#00ff00' : (value >= 90 ? '#ff0000' : '#FFFF33');
						return '<div class="easyui-progressbar" color="'
								+ color
								+ '" value="'
								+ value
								+ '" style="width:100px;height:15px;"></div>';
					}
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
		    $('#tblThreadPool').resizeDataGrid(0, 0, 0, 0);
		});
	 }

$.fn.progressbar.methods.setColor = function(jq, color) {
	return jq.each(function() {
		var opts = $.data(this, 'progressbar').options;
		var color = color || opts.color || $(this).attr('color');
		$(this).css('border-color', color).find(
				'div.progressbar-value .progressbar-text').css(
				'background-color', color);
	});
}
