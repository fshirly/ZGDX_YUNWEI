$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblDataList').datagrid('hideColumn','moid');
	}else{
		$('#tblDataList').datagrid('hideColumn','moids');
	}
	if ($("#dbmstype").val() == "54") {
		$('#tblDataList').datagrid('hideColumn','operStatusName');
	}
	if($("#dbmstype").val() == "14"){
		 $('div.datagrid-toolbar a').eq(0).hide();
	}else{
		$('div.datagrid-toolbar a').eq(0).show();
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var dbmstype = $("#dbmstype").val();
	var path = getRootName();
	var uri = path + '/monitor/dbObjMgr/listDataBase?dbmstype='+dbmstype;
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
						idField : '',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号	
						toolbar : [
						           /*{
							'text' : '提交到CMDB',
							'iconCls' : 'icon_cmdb',
							handler : function(){
							showCMDB();
							}
						},*/
						           {
								'text' : '套用模板',
								'iconCls' : 'icon_usedtemp',
								handler : function(){
									toUseTemplate();
								}
							},{
								'text' : '删除',
								'iconCls' : 'icon-cancel',
								handler : function() {
									doDeleDBObjects();
								}
							}],
						columns : [ [
								{
									field : 'id',
									checkbox : true
								},
								{
										field : 'moid',
										title : '',
										width : 40,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
										}
								},
								{
									field : 'operstatus',
									title : '可用/持续时间',
									width : 80,
									align : 'left',
									sortable:true,
									formatter:function(value,row){
									if(row.operaTip == null || row.operaTip == ""){
										return row.durationTime;
									}else{
										return '<img title="' + row.operaTip + '" src="' + path
										+ '/style/images/levelIcon/' + row.operstatus + '"/>'+row.durationTime;
									}
								}  
								 },
								{
									field : 'alarmLevelName',
									title : '告警级别',
									width : 60,
									align : 'center',
									sortable:true,
								 	formatter:function(value,row){
										var val = value;
										var t = row.levelicon;
										if(val==null || val==""){
											val="正常";
											t="right.png";
										}
										return '<img src="' + path
												+ '/style/images/levelIcon/' + t + '"/>' + val;
									} 
								},
								{
									field : 'moname',
									title : '数据库服务IP',
									width : 120,
									align : 'center',
									sortable:true,
									formatter : function(value, row, index) {
										if(flag !=null && flag !=""){ 
						        			return value;
										}else if(row.dbmstype == "db2" ||row.dbmstype == "mssql" ||row.dbmstype == "sybase"){
											return value;
										}else {
											var to = "&quot;" + row.moid
											+ "&quot;,&quot;" + row.dbmstype
											+ "&quot;,&quot;" + "_" +value
											+ "&quot;"
											return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
										}	
									}
								},
								{
									field : 'moalias',
									title : '别名',
									width : 100,
									align : 'center'
								},
								{
									field : 'dbmstype',
									title : '数据库类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'serverversion',
									title : '服务版本',
									width : 150,
									align : 'center'
								},
								/**
								{
									field : 'ip',
									title : '服务IP',
									width : 80,
									align : 'center'
								},	
								*/						
								{
									field : 'port',
									title : '访问端口',
									width : 80,
									align : 'center'
								},
								{
									field : 'moClassId',
									title : '类型',
									hidden:true,//隐藏
									width :.80
								},
								{
									field : 'moids',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moid
										+ "&quot;,&quot;" + row.ip
										+ "&quot;,&quot;" + row.dbmstype
										+ "&quot;,&quot;" + row.port
											+ "&quot;,&quot;" + row.moalias
										+ "&quot;"
									 var dis = "&quot;" + row.dbmstype
										+ "&quot;,&quot;" + row.ip
										+ "&quot;"
									 	return ' <a href="javascript:doRediscover('+to+');"  class="fltrt"><img src =" '+path+'/style/images/icon/icon_refresh.png" title="重新发现" /></a>&nbsp;'
									 	+'<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
										+ to
										+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
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
		"dbmstype" : $("#dbmstype").val(),
		"ip" : $("#ip").val(),
		"dbName" : $("#dbName").val()
	};
	reloadTableCommon_1('tblDataList');
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


function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleInfo();
	     window.close();
	} 
}  

/**
 * 重新发现
 * @param moid
 * @return
 */
function doRediscover(moid,deviceip,dbmstype,port){
//	console.log("deviceip==="+deviceip+",  dbmstype===="+dbmstype)
	var moClassId = 15;
	if(dbmstype =="oracle"||dbmstype=="Oracle"){
		moClassId = 15;
	}else if(dbmstype =="mysql"||dbmstype=="Mysql"){
		moClassId = 16;
	}else if(dbmstype =="db2"||dbmstype=="DB2"){
		moClassId = 54;
	}else if(dbmstype =="sybase"||dbmstype=="Sybase"){
		moClassId = 81;
	}else if(dbmstype =="mssql"||dbmstype=="MsSql"){
		moClassId = 86;
	}
	if(moClassId == 15 || moClassId == 16 ||moClassId == 81 || moClassId == 86){
		$.messager.confirm("提示","确定要重新发现?",function(r){
			if (r == true) {
				var path = getRootPatch();
				var uri = path + "/monitor/deviceManager/doRediscover?moClassId="+moClassId+"&deviceip="+deviceip+"&port="+port;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if(true == data.flag || "true" == data.flag){
						//				console.log("data===="+data)
						var taskid = data.taskid;
						showDeviceTask(taskid,moid,deviceip,dbmstype,port);
					}
				}
				}
				ajax_(ajax_param);
			}
		});
	}else  if(moClassId == 54){
		parent.parent.$('#popWin2').window({
			title:'选择数据库名',
		    width:400,
		    height:300,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    modal:true,
		    href: getRootName() + "/monitor/deviceManager/toGetDbNames?deviceip="+deviceip+"&port="+port+"&dbmstype="+dbmstype+"&moClassId="+moClassId+"&moid="+moid
		});
	}
}

/**
 * 跳转至设备任务界面
 * @return
 */
function showDeviceTask(taskid,moid,deviceip,dbmstype,port){
	var moClassId = 15;
	if(dbmstype =="oracle"||dbmstype=="Oracle"){
		moClassId = 15;
	}else if(dbmstype =="mysql"||dbmstype=="Mysql"){
		moClassId = 16;
	}else if(dbmstype =="sybase"||dbmstype=="Sybase"){
		moClassId = 81;
	}else if(dbmstype =="mssql"||dbmstype=="MsSql"){
		moClassId = 86;
	}
	parent.parent.$('#popWin').window({
		title:'设备任务',
	    width:800,
	    height:300,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/deviceManager/showDeviceTask?taskid='+taskid+'&moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&port='+port
	});
}

/**
 * 打开采集配置
 * @param moid
 * @param deviceip
 * @param moClassId
 * @param nemanufacturername
 * @param taskId
 * @return
 */
function toSet(moid,deviceip,dbmstype,port,moalias){
	var moClassId = 15;
	if(dbmstype =="oracle"||dbmstype=="Oracle"){
		moClassId = 15;
	}else if(dbmstype =="mysql"||dbmstype=="Mysql"){
		moClassId = 16;
	}else if(dbmstype =="db2"||dbmstype=="DB2"){
		moClassId = 54;
	}else if(dbmstype =="sybase"||dbmstype=="Sybase"){
		moClassId = 81;
	}else if(dbmstype =="mssql"||dbmstype=="MsSql"){
		moClassId = 86;
	}
	if(moClassId == 15 || moClassId == 16 ||moClassId == 81 || moClassId == 86){
		parent.parent.$('#popWin').window({
			title:'采集设备配置',
		    width:800,
		    height:500,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    modal:true,
		    href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&port='+port+'&moAlias='+moalias
		});
	}else if(moClassId == 54){
		parent.parent.$('#popWin2').window({
			title:'选择数据库名',
		    width:400,
		    height:300,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    modal:true,
		    href: getRootName() + "/monitor/deviceManager/toGetDbNames?deviceip="+deviceip+"&port="+port+"&dbmstype="+dbmstype+"&moClassId="+moClassId+"&moid="+moid+'&moAlias='+moalias+"&isForPerfSet=true"
		});
	}
}



/**
 * 跳转至CMDB界面
 * @return
 */
function showCMDB(){
	var rows  = $('#tblDataList').datagrid('getSelections');
	var flag = null;
	if(rows.length>0){
		var moClassId = rows[0].moClassId;
		var ids=rows[0].moid;
		
		for(var i=1; i<rows.length; i++){
			ids+=',' +rows[i].moid;
			if( moClassId != rows[i].moClassId){
				flag = "1";
			}	
		}
		if(flag == null){
				parent.parent.$('#popWin').window({
				title:'资源分类',
			    width:700,
			    height:400,
			    minimizable:false,
			    maximizable:false,
			    collapsible:false,
			    modal:true,
			    href: getRootName() + '/monitor/envManager/showCmdb?moClassId='+moClassId+"&moids="+ids
			});
		}else{
			$.messager.alert('提示', '设备类型不一致', 'error');
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}

function toUseTemplate(){
	var id=$("#id").val();
	var moIDs = null;
	var rows  = $('#tblDataList').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '没有任何选中项!', 'info');
	} else {
		for (var i=0;i<rows.length;i++) {
			console.log("rows[i].moid = "+rows[i].moid);
			if (null == moIDs) {
				moIDs = rows[i].moid;
			} else {
				moIDs += ',' + rows[i].moid;
			}
		}
		if(null != moIDs){
			console.log("moIDs = "+moIDs);
			// 查看配置项页面
			parent.parent.$('#popWin').window({
		    	title:'套用模板',
		        width:750,
		        height:450,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/monitor/sysMoTemplate/toShowUseTemplateView?moClassID='+id+'&moIDs='+moIDs
		    });
		}
	}
	
}


Array.prototype.in_array = function(e) {  
	 for(i=0;i<this.length;i++){  
		 if(this[i] == e){
			 return true;  
		 } 
	 }  
return false;  
}



 
function doDeleDBObjects(){
	var ids=null;
	
	var rows  = $('#tblDataList').datagrid('getChecked');
	for(var i =0;i<rows.length;i++){
		if (null == ids) {
			ids = rows[i].moid+"_"+rows[i].moClassId;
		} else {
			ids += "," + rows[i].moid+"_"+rows[i].moClassId;
		}
	}
	toAffirmDB(ids);
}

function closephydlgTask(){
	$('#dlgDB').panel('close');
	$('#dlgDB').panel('destroy');
}
/**
 * 确认删除该设备是否有采集任务
 * @return
 */
function toAffirmDB(ids){
	if (null != ids) {	
	// 校验其是否有采集任务
	object = checkTaskStatus(ids);
	
	if(null != object && object !=""){
		var dlgdb = "<div id='dlgDB'><div class='conditionsBtn'>"
			+"<a href='javascript:void(0);' onclick='javascript:closephydlgTask()' id='btntaskSave'>关闭</a></div></div>";
		var dlgTask = $(dlgdb).appendTo(document.body).dialog({
			title:'提示',
			width: 400,
			height: 300,
			content: '',
			modal:true,
			closed: true,
			onClose:function(){
			$('#dlgDB').panel('destroy');
			}
		});
	var serviceTab =$('<table id="DBData"></table>').prependTo(dlgTask.dialog("body")).datagrid({
			title:'删除的设备中含有以下关联任务，请先将关联任务删除',
			nowrap : false,
			fit : true,// 自动大小
			fitColumns:true,
		    columns:[[    
		        {field:'type',title:'关联任务类型', width:100},
		        {field:'className',title:'对象类型/任务名称', width:100},    
		        {field:'deviceIp',title:'设备IP/源对象名称', width:100}     
		    ]] 
		}); 
		$('#DBData').datagrid('loadData',object);
		dlgTask.dialog("open");
		return ;
	}
		$.messager.confirm("提示", "确定要删除该设备？", function(r) {
		if (r == true) {	
			$('#dlgDB').panel('close');
			$('#dlgDB').panel('destroy');
			var uri =getRootName()+"/monitor/deleteMonitorObject/deleteMonitor";
			var load ="<div id='loading' style='margin-top: 30px; margin-left: 20px;'><img src =' "+getRootName()+"/style/images/loading2.gif' /></div>";
			var loadlog = $(load).dialog({
				title:'正在删除中，请稍后....',
				width: 200,
				height: 120,
				modal:true,
				closable:false
			});
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"MOID":ids
				},
				success : function(data) {
					loadlog.panel("close");
					if (true === data) {
						$.messager.alert("提示", "删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}
/**
 * 查询该设备是否有采集任务
 * @param ip
 * @return
 */
function checkTaskStatus(ids){
	var objectTaskData="";
		var ajax_param = {
				url : getRootName()+"/monitor/deleteMonitorObject/checkTask",
				type : "post",
				datdType : "json",
				async:false,
				data : {
				"MOID":ids
				},
		success : function(data) {
					objectTaskData =data;
				}
		};
		ajax_(ajax_param);
	return objectTaskData;
}
