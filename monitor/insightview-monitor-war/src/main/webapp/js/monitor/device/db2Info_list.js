$(document).ready(function() {
	var flag=$('#flag').val();
	if(flag == "null" || flag =="" || flag ==null){ 
		doInitTable();
		$('#tblDb2InfoList').datagrid('hideColumn','moId');
	}else{
		doInitChooseTable();
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var instanceMOID=$('#instanceMOID').val();
	if(instanceMOID==null || instanceMOID=="" || instanceMOID=="null"){
		instanceMOID=-1;
	}
	var dbmsMoId=$('#dbmsMoId').val();
	if(dbmsMoId == null || dbmsMoId == "" || dbmsMoId == "null"){
    	dbmsMoId = -1;
    }
	var path = getRootName();
	var uri = path + '/monitor/db2Manage/listDb2Infos?instanceMOID='+instanceMOID+'&dbmsMoId='+dbmsMoId;
	$('#tblDb2InfoList')
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
									field : 'databaseName',
									title : '数据库名称',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										var to = "&quot;" + row.moId
										+ "&quot;,&quot;" + "db2_db"
										+ "&quot;,&quot;" + value +"_" +row.instanceName
										+ "&quot;"
										return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
								}
								 },
								 {
										field : 'instanceName',
										title : '数据实例名',
										width : 60,
										align : 'center'
									},
								{
									field : 'ip',
									title : '数据库服务IP',
									width : 100,
									sortable:true,
									align : 'center'
								},
								{
									field : 'databasePath',
									title : '数据库路径',
									width : 150,
									align : 'center'
								},
								{
									field : 'formatTime',
									title : '连接时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'databaseStatus',
									title : '数据库状态',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
									if(value == 0){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/up.png'
										+ '"/>'+"活跃";
									}else if(value == 1){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/3.png'
										+ '"/>'+"停顿－暂挂";
									}else if(value == 2){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/unknown.png'
										+ '"/>'+"已停顿";
									}else if(value == 3){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/4.png'
										+ '"/>'+"正在前滚";
									}
								}
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDb2InfoList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 选择
 * @return
 */
function doInitChooseTable() {
	var flag=$('#flag').val();
	var instanceMOID=$('#instanceMOID').val();
	if(instanceMOID==null || instanceMOID=="" || instanceMOID=="null"){
		instanceMOID=-1;
	}
	var dbmsMoId=$('#dbmsMoId').val();
	if(dbmsMoId == null || dbmsMoId == "" || dbmsMoId == "null"){
    	dbmsMoId = -1;
    }
	var path = getRootName();
	var uri = path + '/monitor/db2Manage/listDb2Infos?instanceMOID='+instanceMOID+'&dbmsMoId='+dbmsMoId;
	$('#tblDb2InfoList')
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
									field : 'databaseName',
									title : '数据库名称',
									width : 80,
									align : 'center',
								 },
								 {
										field : 'instanceName',
										title : '数据实例名',
										width : 60,
										align : 'center'
									},
								{
									field : 'ip',
									title : '数据库服务IP',
									width : 100,
									align : 'center'
								},
								{
									field : 'databasePath',
									title : '数据库路径',
									width : 150,
									align : 'center'
								},
								{
									field : 'formatTime',
									title : '连接时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'databaseStatus',
									title : '数据库状态',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
									if(value == 0){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/up.png'
										+ '"/>'+"活跃";
									}else if(value == 1){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/3.png'
										+ '"/>'+"停顿－暂挂";
									}else if(value == 2){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/unknown.png'
										+ '"/>'+"已停顿";
									}else if(value == 3){ 
					        			return '<img src="'
										+ path
										+ '/style/images/levelIcon/4.png'
										+ '"/>'+"正在前滚";
									}
								}
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDb2InfoList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDb2InfoList').datagrid('options').queryParams = {
		"databaseName" : $("#databaseName").val(),
		"ip" : $("#ip").val(),
		"instanceName" : $("#instanceName").val()
	};
	reloadTableCommon_1('tblDb2InfoList');
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
	if(dbmstype=="db2_db"){
		title = 'db2_db'+moName+'视图';
		moClassName="DB2_DB";
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
					if (parent.tabsLst.in_array(title) == true) {
//						$.messager.alert("提示","该设备视图已经打开！","info");
						//跳转到已经打开的视图页面
	                    parent.$('#tabs_window').tabs('select', title);
	                    var tab = parent.$('#tabs_window').tabs('getSelected');
	                    //更新视图
	                    parent.$('#tabs_window').tabs('update', {
	                        tab: tab,
	                        options: {
	                            title: title,
	                            content: content,
	                            closable: true,
	                            selected: true
	                        }
	                    });
					} else {
						parent.$('#tabs_window').tabs('add',{
						    title:title,
						    content:content,
						    closable:true
						       });
							parent.tabsLst.push(title);
					}
					
				}else if(isExistTabs != null){
					if (parent.parent.tabsLst.in_array(title) == true) {
//						$.messager.alert("提示","该设备视图已经打开！","info");
						//跳转到已经打开的视图页面
	                    parent.parent.$('#tabs_window').tabs('select', title);
	                    var tab = parent.parent.$('#tabs_window').tabs('getSelected');
	                    //更新视图
	                    parent.parent.$('#tabs_window').tabs('update', {
	                        tab: tab,
	                        options: {
	                            title: title,
	                            content: content,
	                            closable: true,
	                            selected: true
	                        }
	                    });
					} else {
						parent.parent.$('#tabs_window').tabs('add',{
						    title:title,
						    content:content,
						    closable:true
						    });
						parent.parent.tabsLst.push(title);
					}
					
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
		var isMoid = $("#isMoid").val();
		if(isMoid == "true" || isMoid == true){
			fWindowText2 = window.opener.document.getElementById("ipt_db2DbMoId2"); 
			fWindowText2.value = moId; 
			window.opener.findDB2DbInfo2();
		}else{
			fWindowText1 = window.opener.document.getElementById("ipt_db2DbMoId"); 
			fWindowText1.value = moId; 
			window.opener.findDB2DbInfo();
		}
		window.close();
	} 
}  