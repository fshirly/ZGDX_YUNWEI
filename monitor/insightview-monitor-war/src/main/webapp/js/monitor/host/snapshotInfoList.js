$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doSnapshotInitTables(liInfo);// 设备快照

	});

// 设备快照
function doSnapshotInitTables(liInfo) {
	var path = getRootName();
	$('#tblSnapshot')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 900,
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
//						url : path + '/monitor/hostManage/getSnapshotInfo',
						url : path + '/monitor/hostManage/querySnapshotInfo',
						idField : 'fldId',
						columns : [ [
								{
									field : 'classlable',
									title : '设备类型',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										if(row.moClassID == 5 ){
											return "<a href='javascript:void(0)' onclick='showRouterView("+row.moClassID+")'"
											+ " target='_blank'>"
											+ value + "</a>";
										}else if(row.moClassID == 6 ){
											return "<a href='javascript:void(0)' onclick='showRouterView("+row.moClassID+")'"
											+ " target='_blank'>"
											+ value + "</a>";
										}
										else{
											return '<a style="cursor: pointer;" onclick="javascript:toShowTypeList('
											+ row.moClassID
											+ ');">'+row.classlable+'</a>'; 
											}
										}
										
										/**return "<a href="+path+"/monitor/hostManage/toShowTypeInfo?moClass="
												+ row.moclassid
												+ " target='_blank'>"
												+ value + "</a>";*/
									
								},
								{
									field : 'alarmCount',
									title : '告警数量',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
//									var url=getRootName() +'/monitor/alarmActive/toAlarmActiveList?moClassID='+row.moclassid;
//						        	return '<a href='+url+' target="_blank"><font color="red">'+row.alarmcount+"</font></a>";
									return '<a style="cursor: pointer;" onclick="javascript:toShowAlarmList('
									+ row.moClassID
									+ ');">'+row.alarmCount+'</a>'; 
						        	
									}
								},
								{
									field : 'perfvalue',
									title : '设备数量',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										return "<font color='red'>"
												+ row.alarmDeviceCount
												+ "</font>/" + row.countDevice;
									}
								} ] ],
						onLoadSuccess: function(){   
							//自适应部件大小
							window.parent.resizeWidgetByParams(liInfo)
						}   
						
					});
	$(window).resize(function() {
	    $('#tblSnapshot').resizeDataGrid(0, 0, 0, 0);
	});
}


function toShowAlarmList(moclassid){
	var url=getRootName() +'/monitor/alarmActive/toAlarmActiveList?moClassID='+moclassid+'&host=snapshot';
	var content = '<iframe id="alarmHost" name="alarmHost" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	var title = '告警列表';
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
		window.parent.frames.location = uri;
	}
}


//预览视图
function showRouterView(moclassid){
	var portalName = 'Device';
//	var portalName = 'Router';
//	if(moclassid==6){
//		 portalName = 'Switcher';
//	}
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/viewDevicePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : portalName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == true){
				var title = "路由器视图";
				var moClass="router";
				if(moclassid==6){
					title = "交换机视图";
					moClass="switcher";
				}
				var uri=path+"/monitor/gridster/showPortalView?flag=device";
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
					window.parent.frames.location = uri;
				}
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}
function toShowTypeList(moclassid){
	var url=getRootName() +'/monitor/hostManage/toShowTypeInfo?moClass='+moclassid;
	var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	var title = '';
	if(moclassid=="7"){
		title = '物理主机列表';
	}else if (moclassid=="8"){
		title = '虚拟宿主机列表';
	}
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
		window.parent.frames.location = uri;
	}
}