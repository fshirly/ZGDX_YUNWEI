$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doHostIfInitTables(liInfo);// 主机CPU
	});

// 主机CPU
function doHostIfInitTables(liInfo) {
	var path = getRootName();
	var moID =$('#moID').val();
	var ifMOID =$('#ifMOID').val();
	$('#tblHostNetworkIf')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
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
						url : path + '/monitor/interfaceChart/findHostOrNetWorkDetail?moID='+moID+'&IfMOID='+ifMOID,

						idField : 'fldId',
						columns : [ [
								{
									field : 'kpiname',
									title : '指标名称',
									width : 120,
									align : 'center',
								},
								 {
									field : 'ins',
									title : '流入',
									width : 100,
									align : 'center'
								}, 
								{
									field : 'outs',
									title : '流出',
									width : 100,
									align : 'center'
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
					    $('#tblHostNetworkIf').resizeDataGrid(0, 0, 0, 0);
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
