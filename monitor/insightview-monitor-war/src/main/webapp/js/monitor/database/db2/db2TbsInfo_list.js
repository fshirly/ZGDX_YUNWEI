$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	var moID = $("#moID").val();
	doTbsInitTables(moID,liInfo);
	});

// ORACLE 表空间信息列表
function doTbsInitTables(moID,liInfo) {
	var path = getRootName();
	$('#tblDb2TbsInfo')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 550,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/db2Manage/getDb2TbsList?moID='+moID,

						idField : 'fldId',
						columns : [ [
								{
									field : 'tbsName',
									title : '表空间名称',
									width : 120,
									align : 'center'
								},
								{
									field : 'tbsType',
									title : '表空间类型',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'formatTotalSize',
									title : '总大小',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'formatUsedSize',
									title : '已用',
									width : 80,
									align : 'center'
								},
								{
									field : 'tbUsage',
									title : '使用百分比',
									width : 80,
									align : 'center',
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
	    $('#tblDb2TbsInfo').resizeDataGrid(0, 0, 0, 0);
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
