$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	var moID = $("#moID").val();
	doTbsInitTables(moID,liInfo);
	});

// ORACLE 表空间信息列表
function doTbsInitTables(moID,liInfo) {
	var path = getRootName();
	$('#tblDb2BufferPoolInfo')
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
						url : path + '/monitor/db2Manage/getDb2BufferPoolList?moID='+moID,

						idField : 'fldId',
						columns : [ [
										{
											field : 'bufferPoolName',
											title : '缓冲池名称',
											width : 120,
											align : 'center'
										},
										{
											field : 'formatBufferSize',
											title : '缓冲池大小',
											width : 100,
											align : 'center'
										},
										{
											field : 'npages',
											title : '页数',
											width : 80,
											align : 'center',
											formatter : function(value, row) {
								    	   if(value<0){
										   	return '';
										   }else{
										   	return value;
										   }
								    }
										}
										,
										{
											field : 'formatPageSize',
											title : '页大小',
											width : 80,
											align : 'center'
										},
										{
											field : 'bufferPoolHits',
											title : '缓冲池命中率',
											width : 150,
											align : 'center',
											formatter : function(value, row, index) {
												var color = (value <= 75) ? '#ff0000' : (value >= 90 ? '#00ff00' : '#FFFF33');
												return '<div class="easyui-progressbar" color="'
															+ color
															+ '" value="'
															+ value
															+ '" style="width:100px;height:15px;"></div>';
												}
										},
										{
											field : 'indexHits',
											title : '索引缓冲池命中率',
											width : 150,
											align : 'center',
											formatter : function(value, row, index) {
												var color = (value <= 75) ? '#ff0000' : (value >= 90 ? '#00ff00' : '#FFFF33');
												return '<div class="easyui-progressbar" color="'
															+ color
															+ '" value="'
															+ value
															+ '" style="width:100px;height:15px;"></div>';
												}
										},
										{
											field : 'dataPageHits',
											title : '数据页命中率',
											width : 150,
											align : 'center',
											formatter : function(value, row, index) {
												var color = (value <= 75) ? '#ff0000' : (value >= 90 ? '#00ff00' : '#FFFF33');
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
	    $('#tblDb2BufferPoolInfo').resizeDataGrid(0, 0, 0, 0);
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
