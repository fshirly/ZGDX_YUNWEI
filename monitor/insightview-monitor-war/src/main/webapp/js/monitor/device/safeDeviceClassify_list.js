
$(document).ready(function() {
	var flag=$('#flag').val();
	var moClassID = $("#mOClassID").val();
	doInitTable();
	if(flag !=null && flag !=""){ 
		$('#tblDataList').datagrid('hideColumn','moids');
	}else{
		$('#tblDataList').datagrid('hideColumn','moid');
	}
	if(moClassID="62"){
		$('#tblDataList').datagrid('hideColumn','os');
		$('#tblDataList').datagrid('hideColumn','moClassId');
		$('#tblDataList').datagrid('hideColumn','resid');
	}
	
//	if(moClassID=="75"){
//		$('#tblDataList').datagrid('hideColumn','operstatusdetail');
//		$('#tblDataList').datagrid('hideColumn','alermlevelInfo');
//		$('div.datagrid-toolbar a').eq(0).hide();
//		$('div.datagrid-toolbar a').eq(1).show();
//	}else{
//		if(moClassID.indexOf(",")>=0 && "6,59,60".indexOf(moClassID) == -1){
//			 $('div.datagrid-toolbar a').eq(1).hide();
//		}else{
//			if(moClassID=="9"){
//				$('div.datagrid-toolbar a').eq(1).hide();
//			}else{
//				$('div.datagrid-toolbar a').eq(1).show();
//			}
//		}
//	}
//	if( moClassID=="7,8,9" || moClassID=="7" || moClassID=="8" || moClassID=="9" || moClassID=="75" ){
//		$('#tblDataList').datagrid('hideColumn','nemanufacturername');
//	}else{
//		$('#tblDataList').datagrid('hideColumn','os');
//	}
	doInitManOption();//初始化下拉框数据
	initOrgTree();//初始化下拉树（管理域）
	
	

});

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"classID" : $("#mOClassID").val(),
		"deviceip" : $("#deviceip").val(),
		"moname" : $("#moname").val(),
		"nemanufacturername" : $("#nemanufacturername").select2("val"),
		"operstatus" : $("#operstatus").combobox("getValue"),
		"ismanage" : $("#ismanage").combobox("getValue"),
		"domainName" : $("#domainName").val()		
	};	
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function doInitManOption(){
	$('#nemanufacturername').createSelect2({
		uri : '/platform/manufacturer/readManufacturerInfo',
		idAttr : 'e',
		name : 'resManuFacturerName',
		id : 'resManuFacturerId'
	});	
}

/**
 * 初始化下拉树（管理域）
 */
var _orgTreeData = "";
function initOrgTree() {
	var path = getRootPatch();
	var uri = path + "/platform/managedDomain/findManagedDomainTreeVal";
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
			dataOrgTree = new dTree("dataOrgTree", path + "/plugin/dTree/img/");
			dataOrgTree.add(0, -1, "选择管理域", "");

			// 得到树的json数据源
			var datas = eval('(' + data.menuLstJson + ')');
			_orgTreeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].domainId;
				var _nameTemp = gtmdlToolList[i].domainName;
				var _parent = gtmdlToolList[i].parentId;

				dataOrgTree.add(_id, _parent, _nameTemp,
						"javascript:hiddenDTreeSetVal('domainName','"
								+ _id + "','" + _nameTemp + "');");
			}
			$('#dataOrgTreeDiv').append(dataOrgTree + "");
		}
	}
	ajax_(ajax_param);
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var mOClassID = $("#mOClassID").val();
	var flag=$('#flag').val();
	var path = getRootName();
	var uri = path + '/monitor/safeDeviceManager/listDevice?classID=' + mOClassID;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : true,
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
						toolbar : [{
							'text' : '同步到资源库',
							'iconCls' : 'icon_cmdb',
							handler : function(){
							showCMDB();
								}
							},
							{
								'text' : '套用模板',
								'iconCls' : 'icon_usedtemp',
								handler : function(){
									toUseTemplate();
								}
							},
							{
								'text' : '删除',
								'iconCls' : 'icon-cancel',
								handler : function() {
									doDeleMoniObjects();
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
										field : 'operstatusdetail',
										title : '可用/持续时间',
										width : 80,
										align : 'left',
										sortable:true,
										formatter:function(value,row){
//											alert(row.operaTip+"|"+row.operstatusdetail+"|"+row.durationTime);
											return '<img title="' + row.operaTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.operstatusdetail +'"/>'+row.durationTime;
											}
								},
								{
									field : 'alermlevelInfo',
									title : '告警级别',
									width : 58,
									align : 'center',
									sortable:true,
									formatter:function(value,row){
										var val = value;
										var t = row.levelIcon;
										if(val==null || val==""){
											val="正常";
											t="right.png";
										}
										return '<img src="' + path
												+ '/style/images/levelIcon/' + t + '"/>' + val;
									} 
								},
								{  
									field : 'deviceip',
									title : '设备IP',
									width : 80,
									align : 'center',
									sortable:true,
									formatter : function(value, row, index) {
						        			if((row.moClassId != 0 && row.moClassId!=75) && (flag ==null || flag =="") && row.nemanufacturerid != 0){
						        				var to = "&quot;" + row.moid
												+ "&quot;,&quot;" + value
												+ "&quot;"
						        				return '<a style="cursor: pointer;" onclick="javascript:toShowView('
												+ to
												+ ');">'+value+'</a>'; 
							        		}else{
							        			return value;
							        		}
									}
								},
								{
									field : 'moname',
									title : '设备名称',
									width : 170,
									align : 'center'
								}/*,{
									field : 'createtimeLong',
									title : '发现时间',
									width : 70,
									align : 'center'
								}*/,{
									field : 'moalias',
									title : '设备别名',
									width : 70,
									align : 'center'
								},
								
								{
									field : 'os',
									title : '操作系统',
									width : 65,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == null || value == "") {
											return '-';
										}else{
											if(value && value.length > 10){
								        		value2 = value.substring(0,10) + "...";
												return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
								        	}else{
												return value;
											}
										}
									}
								},
								{
									field : 'nemanufacturername',
									title : '设备厂商',
									width : 65,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == null || value == "") {
											return '-';
										}else{
											return value;
										}
									}
								},
								{
									field : 'necategoryname',
									title : '设备类型', 
									width : 65,
									align : 'center'
								}/*,{
									field : 'source',
									title : '设备来源', 
									width : 37,
									align : 'center'
								}*/,
								{
									field : 'ismanageinfo',
									title : '是否管理', 
									width : 37,
									align : 'center'
								},
								{
									field : 'iPAddress',
									title : '所属采集机',
									width : 80,
									align : 'center',
									formatter : function(value, row,index) {
										if((flag ==null || flag =="" ) && (value != null && value !="")){
						        			return '<a style="cursor: pointer;" onclick="javascript:toShowCollector('
											+ row.necollectorid
											+ ');">'+value+'</a>'; 
						        		}else{
						        			return value;
						        		}
									}
								},
								{
									field : 'domainName',
									title : '管理域',
									width : 80,
									align : 'center'
								}
								,
								{
									field : 'moClassId',
									title : '类型',
									hidden:true,//隐藏
									width :.50
								},
								{
									field : 'resid',
									title : '类型id',
									hidden:true,//隐藏
									width :.50
								},
								{
									field : 'moids',
									title : '操作',
									width : 60,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moid
										+ "&quot;,&quot;" + row.deviceip
										+ "&quot;,&quot;" + row.moClassId
										+ "&quot;,&quot;" + row.nemanufacturername
										+ "&quot;,&quot;" + row.taskId
										+ "&quot;,&quot;" + row.moalias
										+ "&quot;"
									 var dis = "&quot;" + row.moClassId
										+ "&quot;,&quot;" + row.deviceip
										+ "&quot;"
										if(row.moClassId !=9){
											return ' <a href="javascript:doRediscover('+to+');"  class="fltrt"><img src =" '+path+'/style/images/icon/icon_refresh.png" title="重新发现" /></a>&nbsp;'
											+'<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
											+ to
											+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
										}else{
											return '<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
											+ to
											+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';
										}
							    }
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

function resetFormFilter(controlId) {
	resetForm(controlId);
	$("#nemanufacturername").select2("val", "-1");
	$("#operstatus").combobox("setValue", "-1");
	$("#ismanage").combobox("setValue", "-1");
}

/**
 * 重新发现
 * @param moid
 * @return
 */					
function doRediscover(moid,deviceip,moClassId,nemanufacturername,taskId){
//	console.log("deviceip==="+deviceip+",  moClassId===="+moClassId)
	$.messager.confirm("提示","确定要重新发现?",function(r){
		if (r == true) {
			var path = getRootPatch();
			var uri = path + "/monitor/deviceManager/doRediscover?moClassId="+moClassId+"&deviceip="+deviceip;
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
						showDeviceTask(taskid,moid,deviceip,moClassId,nemanufacturername,taskId);
					}
				}
			}
			ajax_(ajax_param);
		}
	});
}

/**
 * 跳转至设备任务界面
 * @return
 */
function showDeviceTask(taskid,moid,deviceip,moClassId,nemanufacturername,taskId){
	parent.parent.$('#popWin').window({
		title:'设备任务',
	    width:800,
	    height:300,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/deviceManager/showDeviceTask?taskid='+taskid+'&moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&nemanufacturername='+nemanufacturername
	});
}
//查看设备视图
function toShowView(moid,moname){
	var path = getRootPatch();
	var uri = path + "/monitor/discover/selectMoClass";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moid" : moid,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
//			viewDevicePortal(moid,data);
			toShowTabs(moid,data,moname);
		}
	}
	ajax_(ajax_param);
	
}


function toShowTabs(moid,moClassName,moName){
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
				var moClass="safedevice";
				var title = '安全设备'+moName+'视图';
				if(moClassName=="Firewall"){
					moClass="Firewall";
					title = '防火墙'+moName+'视图';
				}else if(moClassName=="Shutter"){
					moClass="Shutter";
					title = '光闸'+moName+'视图';
				}else if(moClassName=="VPN"){
					moClass="VPN";
					title = 'VPN'+moName+'视图';
				}
				if(moClassName=="MobileAppAgent"){
					moClass="mobileappagent";
					title = '移动应用代理'+moName+'视图';
				}
				if(moClassName=="LoadBalance"){
					moClass="loadbalance";
					title = '负载均衡'+moName+'视图';
				}
				if(moClassName=="ProxyGateway"){
					moClass="proxygateway";
					title = '代理网关'+moName+'视图';
				}
				if(moClassName=="Probe"){
					moClass="probe";
					title = '探针'+moName+'视图';
				}
				if(moClassName=="Gatekeeper"){
					moClass="gatekeeper";
					title = '网闸'+moName+'视图';
				}
                var urlParams = "?moID=" + moid + "&moClass=" + moClass + "&flag=device";
                var uri = path + "/monitor/gridster/showPortalView" + urlParams;
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + uri + '" style="width:100%;height:100%;"></iframe>';
                //该视图已经打开
                if (parent.tabsLst.in_array(title) == true) {
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
                }
                else {
                    parent.$('#tabs_window').tabs('add', {
                        title: title,
                        content: content,
                        closable: true
                    });
                    parent.tabsLst.push(title);
                }
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}

function toSet(moid,deviceip,moClassId,nemanufacturername,taskId,moalias){
	parent.parent.$('#popWin').window({
	title:'采集设备配置',
    width:800,
    height:500,
    minimizable:false,
    maximizable:false,
    collapsible:false,
    modal:true,
    href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&nemanufacturername='+nemanufacturername+'&moAlias='+moalias
});
}

/**
 * 查询该设备上是否已部署服务
 */
function queryService(ip,checkTaskId,vcentIP){
	var objectData="";
		var ajax_param = {
				url : getRootName()+"/monitor/deleteMonitorObject/queryDeviceServie",
				type : "post",
				datdType : "json",
				async:false,
				data : {
				"deviceIP":ip,
				"moid":checkTaskId,
				"vcentIP":vcentIP
				},
		success : function(data) {
					objectData =data;
		 }
		};
		ajax_(ajax_param);
		return objectData;
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


function doDeleMoniObjects(){
	closePanle();
	closeDlgTask();
	var checkTaskId =null;
	var ids=null;
	var ip=null;
	var object="";
	var rows  = $('#tblDataList').datagrid('getChecked');
	var vcentIP="";
	for(var i =0;i<rows.length;i++){
		if(rows[i].moClassId =="75"){
			vcentIP += '\'' +rows[i].deviceip+'\''+",";
		}
		if (null == ids) {
			checkTaskId =rows[i].id; 
			ids = rows[i].id+"_"+rows[i].moClassId;
			ip ='\'' +rows[i].deviceip+'\'';
		} else {
			checkTaskId +=","+rows[i].id;
			ids += "," + rows[i].id+"_"+rows[i].moClassId;
			ip +=",\'"+ rows[i].deviceip+'\'';
		}
	}
	// 如果该设备上部署了服务，需先将其服务删除
	var middelServiceIds="";
	var dbServiceIDs="";
	var VMIDs="";
	var newids="";
	if(ip !=null){
		// 查询设备是否部署服务
		if(vcentIP !=null && vcentIP !=""){
			vcentIP = vcentIP.substring(0,vcentIP.length-1);
		}
		object = queryService(ip,checkTaskId,vcentIP);
		if(null!=object && object !=""){
			for(var j=0;j<object.length;j++){
				newids += object[j].moid+"_"+object[j].moclassID+",";
			}
			var sb = "<div id='dlg'><div class='conditionsBtn'>"
			+"<a href='javascript:toAffirm(\""+ids+"\",\""+newids+"\");'  id='btnSave'>确定</a>"
			+"<a href='javascript:void(0);' onclick='javascript:closePanle()' id='btnBack'>取消</a></div></div>";
			var dlg = $(sb).appendTo(document.body).dialog({
				title:'提示',
				width: 450,
				height: 450,
				content: '',
				modal:true,
				closed: true,
				onClose:function(){
					$('#dlg').panel('destroy');
				}
			});
		var serviceTab =$('<table id="serviceData"></table>').prependTo(dlg.dialog("body")).datagrid({
				title:'该设备上有以下服务，是否需要一起删除？',
				width : 400,
				height : 420,
				nowrap : false,
				fit : true,// 自动大小
				fitColumns:true,
				modal:true,
			    columns:[[  
		            {field:'moid',hidden:true},  
		            {field:'moclassID',hidden:true},  
					{field:'serviceName',title:'服务名称', width:100},  
			        {field:'type',title:'类型', width:100},    
			        {field:'ip',title:'设备IP/设备名称', width:180,
			        	formatter:function(value,row){ 
						var t = row.ip;
						if(t=="no IP" || t=="no ip"){
								return row.moname;
						 }else{
							 return value; 
						 }
					}}   
			    ]]
			    
			}); 
			$('#serviceData').datagrid('loadData',object);
			dlg.dialog("open");
			return;
		}		
	}
	
	toAffirm(ids,newids);
}

/**
 * 关闭对话层
 * @return
 */
function closePanle(){
	$('#dlg').panel('close');
	$('#dlg').panel('destroy');
}

function closeDlgTask(){
	$('#dlgtask').panel('close');
	$('#dlgtask').panel('destroy');
}

/**
 * 确认删除该设备是否有采集任务
 * @return
 */
function toAffirm(ids,newids){
	if (null != ids) {		
	var	object="";
	// 校验其是否有采集任务
		var idstring=ids+","+newids;
	object = checkTaskStatus(idstring);
	if(null != object && object !=""){
		var dlgsb = "<div id='dlgtask'><div class='conditionsBtn'>"
			+"<a href='javascript:void(0);' onclick='javascript:closeDlgTask()' id='btntaskSave'>关闭</a></div></div>";
		var dlgTask = $(dlgsb).appendTo(document.body).dialog({
			title:'提示',
			width: 400,
			height: 300,
			content: '',
			modal:true,
			closed: true,
			onClose:function(){
			$('#dlgTask').panel('destroy');
			}
		});
	var serviceTab =$('<table id="taskData"></table>').prependTo(dlgTask.dialog("body")).datagrid({
			title:'删除的设备中含有以下关联任务，请先将关联任务删除',
			fit : true,// 自动大小
			fitColumns:true,
		    columns:[[    
		        {field:'type',title:'关联任务类型', width:100},
		        {field:'className',title:'对象类型/任务名称', width:100},    
		        {field:'deviceIp',title:'设备IP/源对象名称', width:100}   
		    ]] 
		}); 
		$('#taskData').datagrid('loadData',object);
		closePanle();
		dlgTask.dialog("open");
		return ;
	}
		$.messager.confirm("提示", "确定要删除该设备？", function(r) {
		if (r == true) {	
			$('#dlg').panel('close');
			$('#dlg').panel('destroy');
			$('#dlgTask').panel('close');
			$('#dlgTask').panel('destroy');
			var uri =getRootName()+"/monitor/deleteMonitorObject/deleteMonitor";
			var load ="<div id='loading'  style='margin-top: 30px; margin-left: 20px;'><img src =' "+getRootName()+"/style/images/loading2.gif' /></div>";
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
					"MOID":idstring
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
 * 套用模板
 */
function toUseTemplate(){
	var id=$("#id").val();
	var moIDs = null;
	var rows  = $('#tblDataList').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '没有任何选中项!', 'info');
	} else {
		for (var i=0;i<rows.length;i++) {
//			console.log("rows[i].moid = "+rows[i].moid);
			if (null == moIDs) {
				moIDs = rows[i].moid;
			} else {
				moIDs += ',' + rows[i].moid;
			}
		}
		if(null != moIDs){
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
/**
 * 跳转至CMDB界面
 * @return
 */
function showCMDB(){
	var rows  = $('#tblDataList').datagrid('getSelections');
	var relationPath=$("#relationPath").val();
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
			    href: getRootName() + '/monitor/deviceManager/showCmdb?moClassId='+moClassId+"&moids="+ids+"&relationPath="+relationPath
			});
		}else{
			$.messager.alert('提示', '设备类型不一致', 'error');
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}
/*所属采集机*/
function toShowCollector(necollectorid){
	var path = getRootPatch();
	var uri=path+"/monitor/perfTask/toPerfTaskList?serverId="+necollectorid;
	var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
	var isExistTabs = parent.document.getElementById("tabs_window");
	parent.$('#tabs_window').tabs('add',{
	         title:'采集任务',
	         content:content,
	         closable:true
	});	
}
