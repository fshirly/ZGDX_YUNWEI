$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag == "null" || flag =="" || flag ==null){ 
		$('#tblOrclDbList').datagrid('hideColumn','moId');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var path = getRootName();
	var uri = path + '/monitor/orclDbManage/listOrclDbInfos';
	$('#tblOrclDbList')
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
						remoteSort : false,
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
									field : 'dbName',
									title : '数据库名称',
									width : 38,
									align : 'center'
								 },
								{
									field : 'ip',
									title : '数据库服务IP',
									width : 60,
									sortable:true,
									align : 'center',
									formatter : function(value, row, index) {
										if(flag !=null && flag !=""){ 
						        			return value;
										}else {
											var to = "&quot;" + row.moId
											+ "&quot;,&quot;" + "oracle"
											+ "&quot;,&quot;" + value
											+ "&quot;"
											return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
										}	
									}
								},
								{
									field : 'formatTime',
									title : '创建时间',
									width : 120,
									align : 'center'
								},
								{
									field : 'openMode',
									title : '打开模式',
									width : 80,
									align : 'center'
								},
								{
									field : 'logMode',
									title : '日志模式',
									width : 120,
									align : 'center'
								},
								] ]
					});
	$(window).resize(function() {
        $('#tblOrclDbList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblOrclDbList').datagrid('options').queryParams = {
		"dbName" : $("#dbName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblOrclDbList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}


//查看视图
function viewDevicePortal(moid,dbmstype,moName){
	var title = 'oracle'+moName+'视图';
	var moClass=dbmstype;
	var moClassName="Oracle";
	if(dbmstype=="mysql"){
		title = 'mysql'+moName+'视图';
		moClassName="Mysql";
	}	
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
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
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


function sel(moId){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleDbMoId"); 
	     fWindowText1.value = moId; 
	     window.opener.findOracleDbInfo();
	     window.close();
	} 
}  