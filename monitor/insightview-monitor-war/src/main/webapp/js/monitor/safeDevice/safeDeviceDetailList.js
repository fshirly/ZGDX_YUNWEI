$(document).ready(function(){
    var liInfo = $("#liInfo").val();
    doHostCPUInitTables(liInfo);// 安全设备-防火墙CPU
    doHostMemoryInitTables(liInfo);// 安全设备-防火墙内存
    doHostDiskInitTables(liInfo);// 安全设备-防火墙硬盘
    doFlowsInitTables(liInfo);// 接口流量
    /*$(".easyui-tabs").css({
     "height":"230px",
     "overflow-y":"auto"
     });*/
});
var maxRowsCount = 0;
// 安全设备-防火墙CPU
function doHostCPUInitTables(liInfo){
    var path = getRootName();
    $('#tblCPU').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 530,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/safeDeviceManager/findCPUDetailInfo?moClass=Firewall',
        
        idField: 'fldId',
        columns: [[{
            field: 'instance',
            title: 'CPU编号',
            width: 80,
            align: 'center'
        }, {
            field: 'perusage',
            title: 'CPU使用率',
            width: 80,
            align: 'center'
        }]],
		
		onLoadSuccess: function(data){
			var length = data.rows.length;
			if(length > maxRowsCount){
	            //自适应部件大小
	            window.parent.resizeWidgetByParams(liInfo);
				maxRowsCount = length;
			}
        }
    });
    $(window).resize(function(){
        $('#tblCPU').resizeDataGrid(0, 0, 0, 0);
    });
}

// 安全设备-防火墙内存
function doHostMemoryInitTables(liInfo){
    var path = getRootName();
    $('#tblMemory').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 530,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/safeDeviceManager/findMemoryDetailInfo?moClass=Firewall',
        
        idField: 'fldId',
        columns: [[{
            field: 'phymemsize',
            title: '内存大小',
            width: 80,
            align: 'center'
        }, {
            field: 'phymemfree',
            title: '内存空闲大小',
            width: 100,
            align: 'center'
        }, {
            field: 'phymemusage',
            title: '内存使用率',
            width: 100,
            align: 'center'
        }]],
		
		onLoadSuccess: function(data){
			var length = data.rows.length;
			var result = length > maxRowsCount;
			if(length > maxRowsCount){
	            //自适应部件大小
	            window.parent.resizeWidgetByParams(liInfo);
				maxRowsCount = length;
			}
        }
    });
    $(window).resize(function(){
        $('#tblMemory').resizeDataGrid(0, 0, 0, 0);
    });
}

// 安全设备-防火墙硬盘
function doHostDiskInitTables(liInfo){
    var path = getRootName();
    $('#tblDisk').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 530,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/safeDeviceManager/findDiskDetailInfo?moClass=Firewall',
        idField: 'fldId',
        
        columns: [[{
            field: 'rawdescr',
            title: '磁盘分区',
            width: 250,
            align: 'center'
        }, {
            field: 'disksize',
            title: '磁盘容量',
            width: 120,
            align: 'center'
        }, {
            field: 'diskfree',
            title: '磁盘空闲容量',
            width: 120,
            align: 'center'
        }, {
            field: 'diskusage',
            title: '磁盘使用率',
            width: 120,
            align: 'center'
        }]],
		
		onLoadSuccess: function(data){
			var length = data.rows.length;
			if(length > maxRowsCount){
	            //自适应部件大小
	            window.parent.resizeWidgetByParams(liInfo);
				maxRowsCount = length;
			}
        },
    });
    $(window).resize(function(){
        $('#tblDisk').resizeDataGrid(0, 0, 0, 0);
    });
}

// 安全设备-防火墙接口
function doFlowsInitTables(liInfo){
    var path = getRootName();
    $('#tblFlows').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 530,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/safeDeviceManager/findFlowsDetailInfo?moClass=Firewall',// findVMDetailFlowsInfo
        idField: 'fldId',
        columns: [[{
            field: 'ifname',
            title: '接口名称',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                if (row.moClassId != 0) {
                    var to = "&quot;" + row.devicemoid +
                    "&quot;,&quot;" +
                    row.moid +
                    "&quot;,&quot;" +
                    row.deviceip +
                    "&quot;,&quot;" +
                    value +
                    "&quot;"
                    return '<a style="cursor: pointer;" onclick="javascript:toShowIfView(' +
                    to +
                    ');">' +
                    value +
                    '</a>';
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'operstatus',
            title: '接口状态',
            width: 100,
            align: 'center'
        }, {
            field: 'inflows',
            title: '流入流量',
            width: 100,
            align: 'center'
        }, {
            field: 'outflows',
            title: '流出流量',
            width: 100,
            align: 'center'
        }, {
            field: 'indiscards',
            title: '流入丢包',
            width: 100,
            align: 'center'
        }, {
            field: 'outdiscards',
            title: '流出丢包',
            width: 100,
            align: 'center'
        }, {
            field: 'inerrors',
            title: '流入错包',
            width: 100,
            align: 'center'
        }, {
            field: 'outerrors',
            title: '流出错包',
            width: 100,
            align: 'center'
        }]],
		
		onLoadSuccess: function(data){
			var length = data.rows.length;
			var result = length > maxRowsCount;
			if(length > maxRowsCount){
	            //自适应部件大小
	            window.parent.resizeWidgetByParams(liInfo);
				maxRowsCount = length;
			}
        }
    });
    
    $(window).resize(function(){
        $('#tblFlows').resizeDataGrid(0, 0, 0, 0);
    });
}
