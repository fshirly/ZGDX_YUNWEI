$(document).ready(function() {
	var liInfo = $("#liInfo").val();
		doVHostCPUInitTables();// 宿主机CPU
		doVHostMemoryInitTables();// 宿主机内存
		doVHostDatastoreInitTables();// 宿主机数据存储
		doVHostDiskInitTables();// 宿主机硬盘
		doVHostFlowsInitTables(liInfo);// 宿接口流量
		$(".easyui-tabs").css({
			"height":"300px",
			"overflow-y":"auto"
		});
	});

// 宿主机CPU
function doVHostCPUInitTables() {
	var path = getRootName();
	$('#tblCPU').datagrid( {
		iconCls : 'icon-edit',// 图标
		width : 530,
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
		url : path + '/monitor/hostManage/findCPUDetailInfo?moClass=vhost',

		idField : 'fldId',
		columns : [ [

		 {
			field : 'instance',
			title : 'CPU编号',
			width : 100,
			align : 'center'
		}, {
			field : 'perusage',
			title : 'CPU单核使用率',
			width : 100,
			align : 'center'
		}, {
			field : 'cpused',
			title : '总使用时间',
			width : 100,
			align : 'center'
		}
		, {
			field : 'cpuidle',
			title : '总空闲时间',
			width : 100,
			align : 'center'
		}
		] ]
	});
	$(window).resize(function() {
	    $('#tblCPU').resizeDataGrid(0, 0, 0, 0);
	});
}
// 宿主机内存
function doVHostMemoryInitTables() {
	var path = getRootName();
	$('#tblMemory').datagrid( {
		iconCls : 'icon-edit',// 图标
		width : 530,
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		singleSelect : true, // 是否单行选择
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		fitColumns:true,
		url : path + '/monitor/hostManage/findMemoryDetailInfo?moClass=vhost',//findVHostDetailMemoryInfo

		idField : 'fldId',
		columns : [ [

		{
			field : 'memorysize',
			title : '内存大小',
			width : 80,
			align : 'center'
		}, {
			field : 'phymemfree',
			title : '内存空闲大小',
			width : 150,
			align : 'center'
		}, {
			field : 'phymemusage',
			title : '内存使用率',
			width : 120,
			align : 'center'
		}
		, {
			field : 'phymemactive',
			title : '活动内存',
			width : 80,
			align : 'center'
		}
		, {
			field : 'phymemoverhead',
			title : '系统开销内存',
			width : 150,
			align : 'center'
		}, {
			field : 'phymemshared',
			title : '共享内存',
			width : 100,
			align : 'center'
		}
		
		] ]
	});
	$(window).resize(function() {
	    $('#tblMemory').resizeDataGrid(0, 0, 0, 0);
	});
}
//宿主机 数据存储
function doVHostDatastoreInitTables() {
	var path = getRootName();
	$('#tblDatastore').datagrid( {
		iconCls : 'icon-edit',// 图标
		width : 530,
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		singleSelect : true, // 是否单行选择
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		fitColumns:true,
		url : path + '/monitor/hostManage/findDatastoreDetailInfo?moClass=vhost',

		idField : 'fldId',
		columns : [ [

		{
			field : 'moname',
			title : '数据存储名称',
			width : 100,
			align : 'center'
		}, {
			field : 'datastoreusage',
			title : '数据存储使用率',
			width : 120,
			align : 'center'
		}, {
			field : 'datastorefree',
			title : '空闲容量',
			width : 100,
			align : 'center'
		}
		, {
			field : 'datastorerspeed',
			title : '读速度',
			width : 80,
			align : 'center'
		}, {
			field : 'datastorewspeed',
			title : '写速度',
			width : 80,
			align : 'center'
		}
		, {
			field : 'datastorerrequest',
			title : '读请求数',
			width : 80,
			align : 'center'
		}, {
			field : 'datastorewrequest',
			title : '写请求数',
			width : 80,
			align : 'center'
		}
		] ]
	});
	$(window).resize(function() {
	    $('#tblDatastore').resizeDataGrid(0, 0, 0, 0);
	});
}
// 宿主机硬盘
function doVHostDiskInitTables() {
	var path = getRootName();
	$('#tblDisk').datagrid( {
		iconCls : 'icon-edit',// 图标
		width : 530,
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		singleSelect : true, // 是否单行选择
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		fitColumns:true,
		url : path + '/monitor/hostManage/findDiskDetailInfo?moClass=vhost',

		idField : 'fldId',
		columns : [ [

		{
			field : 'rawdescr',
			title : '磁盘描述',
			width : 100,
			align : 'center'
		}, {
			field : 'diskrate',
			title : 'I/O使用率',
			width : 120,
			align : 'center'
		}, {
			field : 'diskrspeed',
			title : '读盘速度',
			width : 100,
			align : 'center'
		}, {
			field : 'diskwspeed',
			title : '写盘速度',
			width : 100,
			align : 'center'
		}, {
			field : 'diskrrequest',
			title : '读请求数',
			width : 100,
			align : 'center'
		}, {
			field : 'diskwrequest',
			title : '写请求数',
			width : 100,
			align : 'center'
		} ] ]
	});
	$(window).resize(function() {
	    $('#tblDisk').resizeDataGrid(0, 0, 0, 0);
	});
}

// 宿主机接口
function doVHostFlowsInitTables(liInfo) {
	var path = getRootName();
	$('#tblFlows').datagrid( {
		iconCls : 'icon-edit',// 图标
//		width : 530,
//		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		singleSelect : true, // 是否单行选择
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		fitColumns:true,
		url : path + '/monitor/hostManage/findFlowsDetailInfo?moClass=vhost',//findVMDetailFlowsInfo

		idField : 'fldId',
		columns : [ [
		 {
			field : 'ifname',
			title : '接口名称',
			width : 100,
			align : 'center'
			/* formatter : function(value, row, index) {
	        		if(row.moClassId != 0){
	        			var to = "&quot;" + row.devicemoid
						+ "&quot;,&quot;" + row.moid
						+ "&quot;,&quot;" + row.deviceip
						+ "&quot;,&quot;" + value
						+ "&quot;"
	        			return '<a style="cursor: pointer;" onclick="javascript:toShowIfView('
						+ to
						+ ');">'+value+'</a>'; 
	        		}else{
	        			return value;
	        		}
					
				}*/
		},
		/* {
			field : 'operstatus',
			title : '接口状态',
			width : 100,
			align : 'center'
		},*/ {
			field : 'inflows',
			title : '流入流量',
			width : 100,
			align : 'center'
		}, {
			field : 'outflows',
			title : '流出流量',
			width : 100,
			align : 'center',
		}, {
			field : 'ifUsage',
			title : '总利用率',
			width : 100,
			align : 'center',
		}, {
			field : 'inusage',
			title : '流入利用率',
			width : 100,
			align : 'center',
		}, {
			field : 'outusage',
			title : '流出利用率',
			width : 100,
			align : 'center',
		}, {
			field : 'ifspeed',
			title : '接口速率',
			width : 100,
			align : 'center',
		}
		] ],
	onLoadSuccess: function(){   
		//自适应部件大小
		window.parent.resizeWidgetByParams(liInfo);
	}   
	});
	$(window).resize(function() {
	    $('#tblFlows').resizeDataGrid(0, 0, 0, 0);
	});
}
