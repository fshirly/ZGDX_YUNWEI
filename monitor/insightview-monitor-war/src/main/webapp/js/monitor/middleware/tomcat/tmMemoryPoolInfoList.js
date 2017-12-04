$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doMemoryPoolInitTables(moID,liInfo);// 数据库列表
		$('#tblMemoryPool').datagrid('hideColumn', 'moId');
	});

// 内存池列表
function doMemoryPoolInitTables(moID,liInfo) {
	var path = getRootName();
	$('#tblMemoryPool')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 120,
						height : 'auto',
						nowrap : false,
						rownumbers : true,
						striped : true,
						border : true,
						singleSelect : false,// 是否单选
					    checkOnSelect : false,
					    selectOnCheck : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns : true,
						url : path
								+ '/monitor/tomcatManage/getTmMemoryPoolInfo?moID='
								+ moID,
						idField : 'fldId',
						columns : [ [
								{
									field : 'memoryUsage',
									title : '内存使用百分比',
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
								}, {
									field : 'memName',
									title : '名称',
									width : 80,
									align : 'center'
								}, {
									field : 'memType',
									title : '类型',
									width : 80,
									align : 'left'
								}, /*{
									field : 'mGRName',
									title : '内存管理器名称',
									width : 80,
									align : 'left'
								},*/ {
									field : 'initSize',
									title : '初始大小(MB)',
									width : 80,
									align : 'left'
								}, {
									field : 'memoryFree',
									title : '空闲内存(MB)',
									width : 80,
									align : 'left'
								}, {
									field : 'maxSize',
									title : '最大值(MB)',
									width : 80,
									align : 'left'
								} ] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
								//自适应部件大小
							window.parent.resizeWidgetByParams(liInfo);
						}
					});
	$(window).resize(function() {
		$('#tblMemoryPool').resizeDataGrid(0, 0, 0, 0);
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
