$(document).ready(
function() {
	$.extend($.fn.datagrid.methods, {
		getChecked : function(jq) {
			var rr = [];
			var rows = jq.datagrid('getRows');
			jq.datagrid('getPanel').find(
					'div.datagrid-cell-check input:checked').each(
					function() {
						var index = $(this).parents('tr:first').attr(
								'datagrid-row-index');
						rr.push(rows[index]);
					});
			return rr;
		}
	});

	$(window).resize(function () {
		$('#tblMyTaskList').datagrid('resize');
	});
	
//	$("#moreTasks").click(function(){
//	    openwindow( '../dashboardPageManage/myTaskManage','',950,500);
//	 });
	        		
	// 页面初始化加载表格
	doInitTable();

});

function doUpdate(id) {
	//openwindow('../taskManage/replyhistory?id=' + id,'',680,440);
	parent.parent.$('#popWin').window({
        title:'编辑任务',
        width:800,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/taskManage/editTask?id=' + id
    });
}

function showReplyHistory(id) {
	parent.parent.$('#popWin').window({
       title:'回复清单',
       width:800,
       height:400,
       minimizable:false,
       maximizable:false,
       collapsible:false,
       modal:true,
       href: getRootName() + '/taskManage/replyhistory?id=' + id
   });
}

function doInitTable() {
	var path = getRootName();
	
	$('#tblMyTaskList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						fitColumns:true,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						//fit : true,// 自动大小
						url : '../dashboardPageManage/queryMyTaskList?status=0',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pageSize:5,
						rownumbers : true,
						columns : [ [
								{
									field : 'title',
									title : '标题',
									align : 'center',
									width :200,
									formatter : function(value, row, index) {
										return '<a  style=\'cursor:pointer\' onclick="javascript:showReplyHistory('
												+ row.id
												+ ');">' + value + '</a>';
									}
								},
								{
									field : 'updateTime',
									title : '报告时间',
									align : 'center',
									width : 120,
									formatter : function(value, row, index) {
										return formatDate(
												new Date(row.updateTime),
												"yyyy-MM-dd hh:mm:ss");
									}
								} ] ]
					});
}