$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doHostIfInitTables(liInfo);// 主机CPU
	});

// 主机CPU
function doHostIfInitTables(liInfo) {
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
						fitColumns:true,
						sortName:'instance',	
						sortOrder:'asc',
						remoteSort: false,
						url : path + '/monitor/interfaceChart/findHostOrNetWork',

						idField : 'fldId',
						columns : [ [
									{
										field : 'instance',
										title : '接口号',
										width : 100,
										hidden:true
									}, 
										{
									field : 'operstatus',
									title : '连通性',
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
							        		if(row.moclassid != 0){
							        			var to = "&quot;" + row.moid
												+ "&quot;,&quot;" + row.ifMOID
												+ "&quot;,&quot;" + row.deviceip
												+ "&quot;,&quot;" + value
												+ "&quot;"
							        			return '<a style="cursor: pointer;" onclick="javascript:toShowIfView('
												+ to
												+ ');">'+value+'</a>'; 
							        		}else{
							        			return value;
							        		}
											
										}
								}, 
								{
									field : 'ifdescr',
									title : '接口描述',
									width : 150,
									align : 'center'
								}, 
								{
									field : 'inusage',
									title : '流入带宽(%)',
									width : 100,
									align : 'center'
								}, 
								{
									field : 'outusage',
									title : '流出带宽(%)',
									width : 100,
									align : 'center'
								}, 
								{
									field : 'errors',
									title : '错包数(个)',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
							        		if(value==-1){
							        			return "";
							        		}else{
							        			return value;
							        		}
										}
								}, 
								{
									field : 'discards',
									title : '丢包数(个)',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
							        		if(value==-1){
							        			return "";
							        		}else{
							        			return value;
							        		}
										}
								},
								{
									field : 'ifspeed',
									title : '接口速率',
									width : 80,
									align : 'center'
								},
								{
									field : 'flows',
									title : '接口流量',
									width : 80,
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
