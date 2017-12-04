
/**
 * 全局变量 项目根路径
 */
var path = getRootName();

$(document).ready(function() {
	doInitTable();
});



/**
 * 初始化加载待分配事项列表
 * 
 * @author zhengzhen
 */
function doInitTable() {
	var path = getRootName();
	$('#tblAllocateEventList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : '300',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						//fit : true,// 自动大小
						fitColumns:true,
						url : path + '/eventManage/queryToBeAllocatedList',
						 // sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						rownumbers : true,// 行号
						pagination : true,// 分页控件
						columns : [ [
								{
									field : 'title',
									title : '标题',
									width : 240,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a  style=\'cursor:pointer\' onclick="javascript:showDetail('
												+ row.id
												+ ');">' + value + '</a>';
									}
								},
								{
									field : 'requesterName',
									title : '报告人',
									width : 100,
									align : 'center',
								},
								{
									field : 'bookTime',
									title : '报告时间',
									width : 140,
									align : 'center',
									formatter : function(value, row, index) {
										return formatDate(
												new Date(row.bookTime),
												"yyyy-MM-dd hh:mm:ss");
									}
								},
								{
									field : 'requestMode',
									title : '报告方式',
									width : 140,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return '电话报告';
										}
										if (value == 2) {
											return 'web自助';
										}
										if (value == 3) {
											return '电子邮件';
										}
										if (value == 4) {
											return '即时通讯';
										}
									}
								},
								{
									field : 'id',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style=\'cursor:pointer\' title="分配" class="easyui-tooltip" onclick="javascript:toAllocate('
										+ row.id
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_allot.png" alt="分配" /></a>';
									}
								} ] ]
					});

}

/**
 * 刷新表格
 */
function reloadTable() {
	reloadTableCommon('#tblAllocateEventList');
}

/**
 * 查看详情
 * @param id
 */
function showDetail(id){
	parent.parent.$('#popWin').window(
			{
				title : '待分配事件详情',
				width : 600,
				height : 400,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				modal : true,
				href : path + "/eventManage/showAllocatedDetail?id=" + id
			});
	
}

/**
 * 跳转到分配页面
 * @param id
 */
function toAllocate(id){
	parent.parent.$('#popWin').window(
			{
				title : '分配',
				width : 600,
				height : 400,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				modal : true,
				onClose : function() {
					 window.location.reload();
				},
				href : path + "/eventManage/toAllocate?id=" + id,
				onLoad : function(){
					parent.parent.$('#allocate').click(function(){
						parent.parent.doAllocate(function(){
							parent.$('#LtoBeAllocatedEvent77 .sDashboard-icon.sDashboard-refresh-icon').click();
						});
					});
				}
			});
}