$(document).ready(function() {	
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblDataList').datagrid('hideColumn','moID');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var mOClassID = $("#mOClassID").val();
	var path = getRootName();
	var uri = path + '/monitor/safeDeviceManager/listInterface?mOClassID=' + mOClassID;
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
								    field : 'moID',
								    title : '',
								    width : 40,
								    align : 'center',
								    formatter : function(value, row, index) {
								    	   return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
								    }
								 },
								 {
										field : 'operStatusName',
										title : '可用状态',
										width : 38,
										align : 'center',
										sortable:true,
								 		formatter:function(value,row){
											/*
											var t="unknown.png";;
												if(value=="UP"){
													t="up.png";
												}else if(value=="DOWN"){
													t="down.png";
												}
												return '<img title="' + value + '" src="' + path
													+ '/style/images/levelIcon/' + t + '"/>';*/
											return '<img title="' + row.operaTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.operstatusdetail +'"/>';		
										}
								},
								{
									field : 'ifName',
									title : '接口名称',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if(row.parentMoClassId==7||row.parentMoClassId==5||row.parentMoClassId==6||row.parentMoClassId==59||row.parentMoClassId==60){
											var to = "&quot;" + row.moID
											+ "&quot;,&quot;" + row.deviceMOID
											+ "&quot;,&quot;" + row.parentMoClassId
											+ "&quot;,&quot;" + row.deviceIP
											+ "&quot;,&quot;" + value
											+ "&quot;"
											return "<a href='javascript:void(0)' onclick='showInterfaceView("+to+")'"
											+ ">"
											+ value + "</a>";
										}else{
											return value;
										}
										
										}
								},
								{
									field : 'deviceIP',
									title : 'IP地址',
									width : 80,
									sortable:true,
									align : 'center'
								},
								{
									field : 'deviceMOName',
									title : '所属设备',
									width : 80,
									align : 'center'
								}
								,
								{
									field : 'ifMtu',
									title : '最大传输单元',
									width : 80,
									align : 'center',
									formatter : function(value, row) {
								    	   if(value<0){
										   	return '';
										   }else{
										   	return value;
										   }
								    }
								},
								{
									field : 'ifSpeed',
									title : '接口速率',
									width : 80,
									align : 'center'
								},
								{
									field : 'ifTypeName',
									title : '接口类型名称',
									width : 80,
									align : 'center'
								},
								{
									field : 'isCollect',
									title : '是否采集',
									width : 80,
									align : 'center',
								 formatter : function(value, row, index) {
										 if((row.instance==row.sourcePort && row.ifOperStatus==1)|| value =="1"){
											 if(value !="1"){
												 UpdateIfInfo();
											 }
											 return '<input type="checkbox" name="selectRadio'+index+'" id="selectRadio' + index + '"  checked="checked"   onclick="doUpdateIfInfo('+row.moID+','+row.ifType+',this)" />是 ';
										 }else{
											 return '<input type="checkbox" name="selectRadio'+index+'" id="selectRadio' + index + '"   onclick="doUpdateIfInfo('+row.moID+','+row.ifType+',this)" />是 ';
										 }
							    }
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
		"deviceIP" : $("#deviceIP").val(),
		"deviceMOName" : $("#deviceMOName").val(),
		"operStatus" : $("#operStatus").combobox("getValue")
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	var index =$('#index').val();
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_networkIfId"); 
	     fWindowText1.value = moid; 
	     window.opener.findNetworkIfInfo();
	     window.close();
	} 
}  

//预览视图
function showInterfaceView(moID,deviceMOID,parentMoClassId,deviceIp,ifName){
	var portalName = "PHost/Interface";
	if (parentMoClassId == 5) {
		var portalName = "Router/Interface";
	} else if (parentMoClassId == 6) {
		var portalName = "Switcher/Interface";
	} else if (parentMoClassId == 8) {
		var portalName = "VHost/Interface";
	} else if (parentMoClassId == 9) {
		var portalName = "VM/Interface";
	}else if (parentMoClassId == 59) {
		var portalName = "SwitcherL2/Interface";
	}else if (parentMoClassId == 60) {
		var portalName = "SwitcherL3/Interface";
	}
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
				var title = "设备IP"+deviceIp+"_接口"+ifName+"视图";
				var urlParams = "moID="+deviceMOID+"&moClass="+parentMoClassId+"&IfMOID="+moID+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView?"+urlParams;
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
					window.parent.frames.location = uri;
				}
					
			
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}

function doUpdateIfInfo(moid,ifType,id){
	if(ifType == 24){
		$.messager.alert("提示","此接口为回环接口，采集到的数据无实际意义！","info");
	}else{
		if(id.checked){
			value=1;
		}else{
			value =0;
		}
	var path = getRootPatch();
	var uri=path+"/monitor/deviceManager/doUpdateIfInfo"
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"moID" : moid,
				"isCollect":value,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				 if(data==true){
//					 $.messager.alert("提示","更新采集任务成功！","info");
				 }else{
					 $.messager.alert("提示","采集任务失败！","error");
					 id.checked=false;
				 }
			}
		};
		ajax_(ajax_param);
		
	}
}


/***
 * 默认采集
 * @param moid
 * @param id
 * @return
 */
function UpdateIfInfo(){
	 
	// 处理默认被选中的采集
	value=1;
	var path = getRootPatch();
	var uri=path+"/monitor/deviceManager/UpdateIfInfo"
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"isCollect":value,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				 if(data==true){
//					 $.messager.alert("提示","更新采集任务成功！","info");
				 }else{
					 $.messager.alert("提示","采集任务失败！","error");
				 }
			}
		};
		ajax_(ajax_param);
}