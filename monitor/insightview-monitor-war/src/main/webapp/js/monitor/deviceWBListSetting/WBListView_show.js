$(document).ready(function() {
	initTabEvent();
	initBaseInfo();
});

/**
 * 加载设备接口黑名单
 * @return
 */
function doInitBlackList(opt) {
	var path = getRootName();
	var deviceip = $("#deviceIP").text();
	var type = type||'1';//黑白名单类型
	var portType = $("#portType").text();//设备接口/端口类型
	var opt = "show"||opt;
	var	uri = path + "/monitor/deviceWBListSetting/listInterfaceByDeviceIP?deviceip="+deviceip+"&opt="+opt+"&type="+type;
	$('#interfaceBlackList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						fitColumns : true,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : '',// event主键
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						resizable  : true,
						columns : [ [
								{
									field : 'ifname',
									title : '接口名',
									width : 100,
									align : 'center'
								},
								{
									field : 'blackOprateStatus',
									title : '启用状态',
									width : 100,
									align : 'center',
									formatter : function(value,row,index){
										switch (row.blackOprateStatus){
										case "0":
											return "未启用";
											break;
										case "1":
											if(row.type==type){
												return "启用";
											}
											break;
										default:
											return "N/A";
											break;
										}
									}
								}
								]]
					});
	$(window).resize(function() {
        $('#interfaceBlackList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载设备接口白名单
 * @return
 */
function doInitWhiteList(opt) {
	var path = getRootName();
	var deviceip = $("#deviceIP").text();
	var type = type||'2';//黑白名单类型
	var portType = $("#portType").text();//设备接口/端口类型
	var opt = "show"||opt;
	var	uri = path + "/monitor/deviceWBListSetting/listInterfaceByDeviceIP?deviceip="+deviceip+"&opt="+opt+"&type="+type;
	$('#interfaceWhiteList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						fitColumns : true,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : '',// event主键
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						resizable  : true,
						columns : [ [
								{
									field : 'ifname',
									title : '接口名',
									width : 100,
									align : 'center'
								},
								{
									field : 'blackOprateStatus',
									title : '启用状态',
									width : 100,
									align : 'center',
									formatter : function(value,row,index){
										switch (row.blackOprateStatus){
										case "0":
											return "未启用";
											break;
										case "1":
											return "启用";
											break;
										default:
											return "N/A";
											break;
										}
									}
								}
								]]
					});
	$(window).resize(function() {
        $('#interfaceWhiteList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * opt //页面操作
 * tableId 
 * type　//黑白名单类型
 */
var initPortBlackList = function(opt,tableId,type) {
	var $dg; // 定义全局变量datagrid
	var columns = [ [ {
		field : 'port',
		title : '端口号',
		align : 'center',
		width : 100
	}, {
		field : 'portType',
		title : '服务端口类型',//'TCP/UDP'
		align : 'center',
		width : 100
	}, {
		field : 'blackOprateStatus',
		title : '状态',
		align : 'center',
		width : 100,
		formatter:function(value,row,index){
			var options = {'1':'启用','0':'不启用'};
			return options[value];
		},
	} ]];
	var portService = {
			field:'portService',
			title : '端口对应服务',//'当为白名单时需要填端口对应服务'
			align : 'center',
			width : 100
	};
	if(type=='2'){//'当为白名单时需要填端口对应服务'
		columns[0].push(portService);
	}
	// 设备IP
	var deviceIP = $("#deviceIP").text();

	$dg = $('#'+tableId).datagrid({
		iconCls : 'icon-edit', // 图标
		width : 'auto',
		height : 'auto',
		fitColumns : true,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : true, // 是否可折叠的
		url : getRootName()+'/monitor/deviceWBListSetting/listPortByDeviceIP?deviceip='+deviceIP+"&opt="+opt+"&type="+type,
		remoteSort : false,
		idField : 'port',
		singleSelect : true, // 是否单选
		pagination : false, // 分页控件
		rownumbers : true,
		columns : columns,
		onBeforeEdit : function(index, row) {
			row.editing = true;
			$dg.datagrid('refreshRow', index);
		},
		onAfterEdit : function(index, row) {
			row.editing = false;
			$dg.datagrid('refreshRow', index);
		},
		onCancelEdit : function(index, row) {
			row.editing = false;
			$dg.datagrid('refreshRow', index);
		}
	});
}

function initBaseInfo() {
	var selectedRow = JSON.parse($("#selectedRow").val());
	$("#deviceIP").text(selectedRow[0].deviceIP);// 设置设备IP
	var portTypeText = '';
	switch (selectedRow[0].portType) {
	case "1":
		portTypeText =  "接口";
		$("#collectorForDeviceTr").hide();
		break;
	case "2":
		portTypeText =  "服务端口";
		$("#collectorForDeviceTr").show();
		$("#collectorForDevice").text(selectedRow[0].collectorIPAddr);
		break;
	default:
		break;
	}
	$("#portType").text(portTypeText);//设置接口类型值
	$("#portType").attr('portType',selectedRow[0].portType);
	var statusForDeviceText = '';
	switch (selectedRow[0].oprateStatus) {
	case "1":
		statusForDeviceText =  "启用";
		break;
	case "0":
		statusForDeviceText =  "不启用";
		break;
	default:
		break;
	}
	$("#statusForDevice").text(statusForDeviceText);
	$("#statusForDevice").attr('statusForDevice',selectedRow[0].oprateStatus);
}

function initTabEvent(){
	var blackListDataGrid = null;
	var whiteListDataGrid = null;
	$('#showWBSetting').tabs({    
	    border:true,    
	    onSelect:function(title){
//	    	if(title=='黑名单'&&blackListDataGrid==null){
//	    		doInitBlackList("show");
//	    		blackListDataGrid = "inited";
//	    	}else if(title=='白名单'&&whiteListDataGrid==null){
//	    		doInitWhiteList("show");
//	    		whiteListDataGrid = "inited";
//	    	}
	    	
	    	if(title=='黑名单'&&blackListDataGrid==null){
	    		//'1=黑名单  2=白名单'
	    		if($("#portType").attr("portType")=="1"){//接口/端口类型
	    			doInitBlackList("show");
	    			blackListDataGrid = "inited";
	    			$('#interfaceBlackListDiv').show();
	    			$('#portBlackListDiv').hide();
	    		}else if($("#portType").attr("portType")=="2"){
	    			$('#interfaceBlackListDiv').hide();
	    			$('#portBlackListDiv').show();
	    			var opt = "show";
	    			initPortBlackList(opt,'portBlackList','1');
	    		}
	    	}else if(title=='白名单'&&whiteListDataGrid==null){
	    		//'1=黑名单  2=白名单'
	    		if($("#portType").attr("portType")=="1"){//接口/端口类型
	    			doInitWhiteList("show");
	    			whiteListDataGrid = "inited";
	    			$('#interfaceWhiteListDiv').show();
	    			$('#portWhiteListDiv').hide();
	    		}else if($("#portType").attr("portType")=="2"){
	    			$('#interfaceWhiteListDiv').hide();
	    			$('#portWhiteListDiv').show();
	    			var opt = "show";
	    			initPortBlackList(opt,'portWhiteList','2');
	    		}
	    	}
	    }    
	});  
}
function confirm(){
    $('#popWin').window('close');
}

function cancle(){
    $('#popWin').window('close');
}