$(document).ready(function() {
	var flag = false;
	doInitTables(flag);
	initTree();
	doInitTaskTable(flag);
	doInitDomainTable();
});

var  datagrid_height = 150;
/**
 * 初始化加载页面多个ip地址信息(运维主机)
 * 
 * @return
 */
var objectData=null;
function doInitTables(flag) {
	var ajax_param = {
				url :getRootName() + '/monitor/harvesterManage/listHarvestInfo',
				type : "post",
				datdType : "json",
				async:false,
		success : function(data) {
				objectData = data;
						if (!data || !data.rows || data.rows.length <= 0) {
						return;
						
					}

					var tabs = {}, rows = data.rows, size = rows.length, tmpIp = null;
					for ( var i = 0; i < size; i++) {
						// 当前行的ip
						tmpIp = rows[i].ipaddress;
						
						if (!(tabs[tmpIp] instanceof Array)) {
							tabs[tmpIp] = [];
						}
						tabs[tmpIp].push(rows[i]);
					}
					// 移除原有datagrid
					var box = $('#harvestInfo').empty();

					// 遍历每一个ip，重新构建对应的datagrid
					var ip_counts = 0;
					for ( var key in tabs) {
						ip_counts ++;
						// var tabId = 'id' + key.replace(/\D/g, '');
						var tabId = 'id' + key.replace(/\s|\./g, '')
								+ new Date().getTime();
						box.append('<table id="' + tabId + '"></table>');
						$('#' + tabId).datagrid(
										{
											title : key, // 设置标题为ip
											width : 'auto',
											height : 'auto',
											nowrap : false,
											striped : true,
											border : true,
											collapsible : false,// 是否可折叠的
											//fit : true,// 自动大小
											fitColumns:true,
											remoteSort : false,
											idField : 'fldId',
											singleSelect : false,// 是否单选
											checkOnSelect : true,
											selectOnCheck : true,
											pagination : false,// 分页控件
											rownumbers : false,// 行号
											columns : [ [

													{
														field : 'servicename',
														title : '服务名称',
														width : 130,
														align : 'center'
													},
													{
														field : 'installdir',
														title : '安装目录',
														width : 350,
														align : 'center'
													},
													{
														field : 'registertime',
														title : '服务注册时间',
														width : 190,
														align : 'center'
													},
													{
														field : 'id',
														title : '任务内容',
														width : 90,
														align : 'center',
														formatter : function(value, row,index) {
														return '<a href="'+getRootName()+'/monitor/perfTask/toPerfTaskList?serverId='+row.serverid+'">查看</a>';
														}
													},
													{
														field : 'currentversion',
														title : '程序版本',
														width : 100,
														align : 'center'
													},
													{
														field : 'servicestatus',
														title : '状态',
														width : 90,
														align : 'center',
														formatter : function(value, row, index) {
															if(value==1){
																return '正常';
															}else if(value==2){
																return '<font color="red">挂起</font>';
															}
														}
													},
													{
														field : 'lastchangetime',
														title : '最近状态时间',
														width : 190,
														align : 'center'
													},
													{
														field : 'ids',
														title : '操作',
														width : 90,
														align : 'center',
														formatter : function(value, row,index) {
															var path = getRootPatch();
															var res = "&quot;" + row.id
															+ "&quot;,&quot;" + row.ipaddress
															+ "&quot;,&quot;" + row.processName
															+ "&quot;,&quot;" + row.lastchangetime
															+ "&quot;,&quot;" + row.installdir
															+ "&quot;,&quot;" +'#' + tabId
															+ "&quot;,&quot;" +index
															+ "&quot;"
															
															var delparams = "&quot;" + row.id
															+ "&quot;,&quot;" +'#' + tabId
															+ "&quot;,&quot;" +row.serverid
															+ "&quot;,&quot;" + row.processName
															+ "&quot;,&quot;" +index
															+ "&quot;,&quot;" +1
															+ "&quot;"
															if(row.processName != "Tomcat"&&row.processName!="monitorRestService"){
																flag = true;
																if(row.servicestatus == 2){
																	return '<a style="cursor: pointer;" title="强制重启" onclick="javascript:doRestart('
																	+res
																	+ ');"><img src =" '+path+'/style/images/icon/icon_restart.png" alt="强制重启" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
																	+ delparams
																	+ ');"><img src="'
																	+ path
																	+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
																}else{
																	return '<a style="cursor: pointer;" title="强制重启" onclick="javascript:doRestart('
																	+res
																	+ ');"><img src =" '+path+'/style/images/icon/icon_restart.png" alt="强制重启" /></a>';
																}
															}else{
																if(row.servicestatus == 2){
																	flag = true;
																	return '<a style="cursor: pointer;" onclick="javascript:toDel('
																	+ delparams
																	+ ');"><img src="'
																	+ path
																	+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
																}else{
																	return '';
																}
															}
														}
													} ] ]
										}).datagrid('loadData', {
									"rows" : tabs[key]
								});
								if(flag == true){
									$('#' + tabId).datagrid('showColumn','ids');
								}else{
									$('#' + tabId).datagrid('hideColumn','ids');
								}
								flag = false;
					}
				}
		};
		ajax_(ajax_param);
}

/**
 * 加载页面采集机任务 并初始化表格（运维服务）
 * 
 * @return
 */
function doInitTaskTable(flag) {
	
			 var data = objectData;
					// 返回数据为空
						if (!data || !data.rows || data.rows.length <= 0) {
							return;
						}

						var tabs = {}, rows = data.rows, size = rows.length, tmpIp = null;
						for ( var i = 0; i < size; i++) {
							
							// 当前行的任务类型
							// tmpIp = rows[i].constantitemname; 为了不用在前台的数据字典中再次维护，统一维护后台的SysServiceInfo表
							tmpIp = rows[i].servicename;
							if (!(tabs[tmpIp] instanceof Array)) {
								tabs[tmpIp] = [];
							}

							tabs[tmpIp].push(rows[i]);
						}

						// 移除原有datagrid
						var box = $('#harvestTaskInfo').empty();

						// 遍历每一个服务类型，重新构建对应的datagrid
						var ip_counts = 0;
						for ( var key in tabs) {
							ip_counts ++;
							var tabId = 'id' + key
									+ new Date().getTime();
//							console.log("key"+key+"    :"+tabs[key]);
//							console.log("tabId==="+tabId);
							box.append('<table id="' + tabId + '" ></table>');
							$('#' + tabId).data('id', '#' + tabId).datagrid(
											{
												title : key, // 设置标题为任务类型
												width : 'auto',
												height : 'auto',
												nowrap : false,
												striped : true,
												border : true,
												collapsible : false,// 是否可折叠的
												//fit : true,// 自动大小
												fitColumns:true,
												remoteSort : false,
												idField : 'fldId',
												singleSelect : true,// 是否单选
												checkOnSelect : true,
												selectOnCheck : true,
												pagination : false,// 分页控件
												rownumbers : false,// 行号
												columns : [ [
														{
															field : 'ipaddress',
															title : '采集机IP',
															width : 130,
															align : 'center'
														},
														{
															field : 'servicename',
															title : '服务名称',
															width : 150,
															align : 'center'
														},
														{
															field : 'installdir',
															title : '安装目录',
															width : 350,
															align : 'center'
														},
														{
															field : 'registertime',
															title : '服务注册时间',
															width : 120,
															align : 'center'
														},
														{
															field : 'id',
															title : '任务内容',
															width : 80,
															align : 'center',
															formatter : function(
																	value, row,
																	index) {
															return '<a href="'+getRootName()+'/monitor/perfTask/toPerfTaskList?serverId='+row.serverid+'">查看</a>';
															}
														},
														{
															field : 'currentversion',
															title : '程序版本',
															width : 80,
															align : 'center'
														},
														{
															field : 'servicestatus',
															title : '状态',
															width : 60,
															align : 'center',
															formatter : function(value, row, index) {
																if(value==1){
																	return '正常';
																}else if(value==2){
																	return '<font color="red">挂起</font>';
																}
															}
														},
														{
															field : 'ids',
															title : '操作',
															width : 90,
															align : 'center',
															formatter : function(value, row,index) {
															var path = getRootPatch();
															var res = "&quot;" + row.id
															+ "&quot;,&quot;" + row.ipaddress
															+ "&quot;,&quot;" + row.processName
															+ "&quot;,&quot;" + row.lastchangetime
															+ "&quot;,&quot;" + row.installdir
															+ "&quot;,&quot;" +'#' + tabId
															+ "&quot;,&quot;" +index
															+ "&quot;"

															var delparams = "&quot;" + row.id
															+ "&quot;,&quot;" +'#' + tabId
															+ "&quot;,&quot;" +row.serverid
															+ "&quot;,&quot;" + row.processName
															+ "&quot;,&quot;" +index
															+ "&quot;,&quot;" +2
															+ "&quot;"
															if(row.processName != "Tomcat"){
																 flag = true;
																if(row.servicestatus == 2){
																	return '<a style="cursor: pointer;" title="强制重启" onclick="javascript:doRestart('
																	+res
																	+ ');"><img src =" '+path+'/style/images/icon/icon_restart.png" alt="强制重启" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
																	+ delparams
																	+ ');"><img src="'
																	+ path
																	+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
																}else{
																	return '<a style="cursor: pointer;" title="强制重启" onclick="javascript:doRestart('
																	+res
																	+ ');"><img src =" '+path+'/style/images/icon/icon_restart.png" alt="强制重启" /></a>';
																}
															}else{
																
																if(row.servicestatus == 2){
																	flag =true ;
																	return '<a style="cursor: pointer;" onclick="javascript:toDel('
																	+ delparams
																	+ ');"><img src="'
																	+ path
																	+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
																}else{
																	return '';
																}
//																console.log("tabId=="+tabId+" ,flag=="+flag);
																
															}
															}
														} ] ]
											}
											
									).datagrid('loadData', {
										"rows" : tabs[key]
									});
							
									if(flag == true){
		//								console.log(">>>>");
										$('#' + tabId).datagrid('showColumn','ids');
									}else{
										$('#' + tabId).datagrid('hideColumn','ids');
									}
									flag = false;
						}
}

var _treeData = "";
var _currentNodeId = -1;
var _currentNodeName = "";

/**
 * 点击树
 */
function treeClickAction(id, name) {
//	console.log("click    id==="+id);
	_currentNodeId = id;
	_currentNodeName = name;
//	console.log("click    _currentNodeId==="+_currentNodeId);
	reloadTable();
	
}

/**
 * 初始化采集机树
 */
function initTree() {
	var path = getRootPatch();
	var uri = path + "/monitor/harvesterManage/findHostLst";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1,"采集机", "javascript:treeClickAction('0','无');");

			// 得到树的json数据源
			var datas = eval('(' + data.hostListJson + ')');
			_treeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].serverid;
				var _nameTemp = gtmdlToolList[i].ipaddress;
				var _parent = gtmdlToolList[i].parentId;

				dataTree.add(_id, _parent, _nameTemp,"javascript:treeClickAction('" + _id + "','" + _nameTemp + "');");
			}
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append(dataTree + "");
			dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}

/**
 * 加载管理域表格
 * 
 */
function doInitDomainTable() {
	var path = getRootName();
	$('#tblDomain')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/harvesterManage/listDomain',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '配置所辖管理域',
							'iconCls' : 'icon-add',
							handler : function() {
								toConfig();
							}
						} ],
						columns : [ [
					         	{
									field : 'ipaddress',
									title : '采集机IP',
									width : 250
								},
								{
									field : 'servername',
									title : '采集机名称',
									width : 150
								},
							
								{
									field : 'domainName',
									title : '管理域名称',
									width : 250,
									align : 'center',
									formatter : function(value, row, index) {
										if(row.domainID==-1){
											return "所有管理域";
										}else{
											return value;
										}
									},
								} ] ]
					});
	$(window).resize(function() {
        $('#tblDomain').resizeDataGrid(0, 0, 0, 0);
    });
}

/*
 * 更新管理域表格 
 */
function reloadTable() {
	var serverid = _currentNodeId;
	$('#tblDomain').datagrid('options').queryParams = {
		"serverid" : _currentNodeId,
	};
	reloadTableCommon('tblDomain');
}


/**
 * 配置采集机所辖管理域
 * @return
 */
function toConfig(){
	var serverid = _currentNodeId;
	if(serverid != -1 && serverid != 0){
		parent.$('#popWin').window({
	    	title:'配置采集机所辖管理域',
	        width:400,
	        height:500,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/harvesterManage/toDomainConfig?serverid='+serverid
	    });
	}else{
		$.messager.alert("错误", "请先选择采集机！", "info");
	}
	
}

/**
 * 强制重启采集程序
 */
function doRestart(id,ipaddress,processName,lastchangetime,installdir,tbl,index){
//	console.log("id==="+id+"服务进程名称==="+processName+"  ,IP=="+ipaddress+" ,最紧更新时间==="+lastchangetime+"  ,安装目录=="+installdir+" ,表格=="+tbl+"  ,index==="+index);
//	$(tbl).datagrid('refreshRow', index );
	$.messager.confirm("提示","确定强制重启采集程序?",function(r){
		if (r == true) {
			openWarning();
			var path = getRootName();
			var uri = path + "/monitor/harvesterManage/doRestart?lastchangetime="+lastchangetime;
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" :id,
					"ipaddress" : ipaddress,
					"processName" : processName,
					"installdir":installdir,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
//					console.log("重启结果===="+data);
					if (1 == data.result || "1" == data.result) {
						var servicestatus = data.servicestatus;
						var lastchangetime = data.lastchangetime;
						$.messager.alert("提示", "强制重启采集程序成功！", "info");
						$("#divWarning").dialog('close');
						$(tbl).datagrid('updateRow', { 
							index:index, 
							row:{
							servicestatus:servicestatus,
							lastchangetime:lastchangetime,
							ids:-1
						}
						} );
						
					} else if (-1 == data.result || "-1" == data.result){
						$("#divWarning").dialog('close');
						$.messager.alert("错误", "强制重启采集程序失败！", "error");
						var servicestatus = data.servicestatus;
						var lastchangetime = data.lastchangetime;
//						console.log("servicestatus=="+servicestatus+" , lastchangetime=="+lastchangetime+"  ,tbl==="+tbl);
						$(tbl).datagrid('updateRow', { 
							index:index, 
							row:{
							servicestatus:servicestatus,
							lastchangetime:lastchangetime,
							ids:-1
						}
						} );
					}else  if (0 == data.result || "0" == data.result){
						$("#divWarning").dialog('close');
						$.messager.alert("提示", "没有对应的凭证，请先配置凭证！", "info");
					}else  if (2 == data.result || "2" == data.result){
						$("#divWarning").dialog('close');
						$.messager.alert("错误", "以Telnet方式登录连接服务器异常！", "error");
					}else  if (3 == data.result || "3" == data.result){
						$("#divWarning").dialog('close');
						$.messager.alert("错误", "以SSH方式登录连接服务器异常！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
	$(".panel.window.messager-window").css("top","248px");
}


function openWarning(){
	$("#divWarning").dialog({
		title:'提示',
        width:300,
        height : 150,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
	}).dialog('open');
}

/**
 * 删除
 * @param id
 * @return
 */
function toDel(id,tabId,serverid,processName,index,flag){
	//console.log("flag===="+flag);
//	console.log("serverid===="+serverid);
	$.messager.confirm("提示","确定删除此服务?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/harvesterManage/delHarvestInfo";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"serverid" :serverid,
					"processName":processName,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(true == data.isUsed || "true" == data.isUsed){
						if (true == data.delResultFlag || "true" == data.delResultFlag) {
//						console.log(">>>>");
							$(tabId).datagrid('getRows').length
							$.messager.alert("提示", "删除成功！", "info");
							var length = $(tabId).datagrid('getRows').length - 1;
							console.log("删除后的行数："+length);
							if(length <= 0){
								console.log("删除主机");
//							$(tabId).datagrid('deleteRow', index);
								doInitTables();
								doInitTaskTable();
//							$(tabId).hide();
							}else{
								console.log(">>删除当行");
								$(tabId).datagrid('deleteRow', index);
								if(flag == 1 || flag == "1"){
									doInitTaskTable();
								}else{
									doInitTables();
								}
							}
						} else {
							$.messager.alert("提示", "删除失败！", "error");
						}
					}else{
						$.messager.alert("提示", "该采集机还有采集任务正在使用，请先至【运行监测-运维平台-采集任务列表】中删除其采集任务！", "info");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}