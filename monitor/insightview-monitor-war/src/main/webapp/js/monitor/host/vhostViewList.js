$(document).ready(function() {
	// doHostChartInitTables();// 图表视图
		doVHostViewInitTables();// 列表视图

	});

// 宿主机列表视图
function doVHostViewInitTables() {
	var path = getRootName();
	$('#tblVMViewList')
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
						url : path + '/monitor/hostManage/getVHostViewList',

						idField : 'fldId',
						columns : [ [
								{
									field : 'devicestatus',
									title : '设备状态',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == null) {
											value = "unknown.png";
											row.operaTip="未知";
										}
										return '<img title="' + row.operaTip + '"  src="' + path
												+ '/style/images/levelIcon/'
												+ value + '"/>';
									}
								},
								{
									field : 'levelicon',
									title : '当前告警级别',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
										return '<img src="' + path
												+ '/style/images/levelIcon/'
												+ value + '"/>'
												+ row.alarmLevelName;
									}
								},
								{
									field : 'moname',
									title : '设备名称',
									width : 150,
									align : 'center'
								},
								{
									field : 'deviceip',
									title : '设备IP',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										if (row.moClassId != 0) {
											var to = "&quot;" + row.moid
													+ "&quot;,&quot;" + value
													+ "&quot;"
											return '<a style="cursor: pointer;" onclick="javascript:toShowView('
													+ to
													+ ');">'
													+ value
													+ '</a>';
										} else {
											return value;
										}
									}
								}/*
									 * ,{ field : 'restypename', title :
									 * '虚拟机类型', width : 150, align : 'center' }
									 */, {
									field : 'cpuvalue',
									title : 'CPU',
									width : 150,
									align : 'center'
								}, {
									field : 'memoryvalue',
									title : '内存',
									width : 150,
									align : 'center'
								} ] ]
					});
}
