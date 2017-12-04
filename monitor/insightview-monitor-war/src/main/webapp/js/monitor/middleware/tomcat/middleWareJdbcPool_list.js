$(document).ready(function() {	
	var flag=$('#flag').val();
	if(flag ==null || flag ==""){ 
		doInitTable();
		$('#tblDataList').datagrid('hideColumn','moId');
	}else{
		doInitChooseTable();
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var parentMoId = $("#parentMoId").val();
	var jmxType = $("#jmxType").val();
	var flag = $("#flag").val();
	var path = getRootName();
	var uri = path + '/monitor/DeviceTomcatManage/listMiddleWareJdbcPool?parentMoId=' + parentMoId+'&jmxType='+jmxType;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : uri,
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
						       {
								    field : 'moId',
								    title : '',
								    width : 40,
								    align : 'center',
								    formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								 },
								 {
									field : 'serverName',
									title : '应用服务名称',
									width : 80,
									align : 'center'
									
								},
								 {
									field : 'dsName',
									title : '池名称',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if(jmxType==53){ 
											var to = "&quot;" + row.moId
											+ "&quot;,&quot;" + row.dsName
											+ "&quot;,&quot;" + row.ip
											+ "&quot;"
											return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
										}else {
											return value;
										}
									}
								},
								{
									field : 'ip',
									title : '服务IP',
									width : 90,
									align : 'center'
								},
								{
									field : 'jdbcDriver',
									title : 'JDBC驱动',
									width : 80,
									align : 'center'
								},
								{
									field : 'poolURL',
									title : '连接池URL',
									width : 80,
									align : 'center'
								},
								{
									field : 'driverName',
									title : '驱动类完全名称',
									width : 80,
									align : 'center'
								},
								{
									field : 'minConns',
									title : '最小连接数',
									width : 80,
									align : 'center'
								},
								{
									field : 'maxConns',
									title : '最大连接数',
									width : 80,
									align : 'center'
								},
								{
									field : 'curConns',
									title : '当前连接数',
									width : 80,
									align : 'center'
								},
								{
									field : 'connTimeOut',
									title : '连接超时',
									width : 80,
									align : 'center'
								},
								{
									field : 'status',
									title : '状态',
									width : 80,
									align : 'center'
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

function doInitChooseTable() {
	var parentMoId = $("#parentMoId").val();
	var jmxType = $("#jmxType").val();
	var flag = $("#flag").val();
	var path = getRootName();
	var uri = path + '/monitor/DeviceTomcatManage/listMiddleWareJdbcPool?parentMoId=' + parentMoId+'&jmxType='+jmxType;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : uri,
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
						       {
								    field : 'moId',
								    title : '',
								    width : 40,
								    align : 'center',
								    formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								 },
								 {
									field : 'serverName',
									title : '应用服务名称',
									width : 80,
									align : 'center'
									
								},
								 {
									field : 'dsName',
									title : '池名称',
									width : 80,
									align : 'center',
								},
								{
									field : 'ip',
									title : '服务IP',
									width : 100,
									align : 'center'
								},
								{
									field : 'jdbcDriver',
									title : 'JDBC驱动',
									width : 80,
									align : 'center'
								},
								{
									field : 'poolURL',
									title : '连接池URL',
									width : 80,
									align : 'center'
								},
								{
									field : 'driverName',
									title : '驱动类完全名称',
									width : 85,
									align : 'center'
								},
								{
									field : 'curConns',
									title : '当前连接数',
									width : 80,
									align : 'center'
								},
								{
									field : 'status',
									title : '状态',
									width : 80,
									align : 'center'
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"mOClassID" : $("#mOClassID").val(),
		"serverName" : $("#serverName").val(),
		"dsName" : $("#dsName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon('tblDataList');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_jdbcPoolId"); 
	     fWindowText1.value = moid; 
	     window.opener.findJdbcPoolInfo();
	     window.close();
	} 
}  

//查看视图
function viewDevicePortal(moId,dsName,ip){
	var title = 'JDBC连接池'+dsName+'视图';
	var moClass='jdbcpool';
	var moClassName="JdbcPool";
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/viewDevicePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : moClassName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == true){
				var urlParams="?moID="+moId+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
				var iWidth = window.screen.availWidth -210; //弹出窗口的宽度190
				var y = window.screen.availHeight-102;
				var y2 = y-29;
				var iHeight = window.screen.availHeight-265; //弹出窗口的高度;
				var iTop = 165;
				var iLeft = 188;
//				window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
				var isExistTabs = parent.parent.document.getElementById("tabs_window");
				var isPartentTabs = parent.document.getElementById("tabs_window");
				if(isPartentTabs != null){
					parent.$('#tabs_window').tabs('add',{
				    title:title,
				    content:content,
				    closable:true
				       });
				}else if(isExistTabs != null){
					parent.parent.$('#tabs_window').tabs('add',{
					    title:title,
					    content:content,
					    closable:true
					    });
				}else{
//					window.parent.frames.location = uri;
					window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
				}
					
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}