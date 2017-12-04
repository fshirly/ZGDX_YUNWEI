$(document).ready(function() {
	doHostIfInitTables();// 主机CPU
	});

// 主机CPU
function doHostIfInitTables() {
	var path = getRootName();
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
						url : path + '/monitor/interfaceChart/findVHostOrVM',

						idField : 'fldId',
						columns : [ [
								{
									field : 'operstatus',
									title : '状态',
									width : 120,
									align : 'center',
									formatter:function(value,row){
										return '<img title="' + row.operaTip + '" src="' + path
										+ '/style/images/levelIcon/' + row.operstatus + '"/>';
								}
								},
								 {
									field : 'ifname',
									title : '接口名称',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return "<a href='/fable/monitor/hostManage/toShowVHostOrVMDetail?IfMOID="
												+ row.ifMOID+"&moID="+row.moid
												+ "' target='_blank'>"
												+ value + "</a>";
									}
								}, 
								{
									field : 'inflows',
									title : '接口流入量',
									width : 100,
									align : 'center'
								}, 
								{
									field : 'outflows',
									title : '接口流出量',
									width : 100,
									align : 'center'
								}, 
								{
									field : 'inpackets',
									title : '接口流入总包数',
									width : 100,
									align : 'center'
								}, 
								{
									field : 'outpackets',
									title : '接口流出总包数',
									width : 100,
									align : 'center'
								}
								] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
						}
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
