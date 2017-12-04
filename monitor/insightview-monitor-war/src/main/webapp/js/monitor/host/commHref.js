
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
				var moClass="host";
				var title = '主机'+moName+'视图';
				if(moClassName=="VHost"){
					moClass="vhost";
					title = '宿主机'+moName+'视图';
				}
				if(moClassName=="VM"){
					moClass="vm";
					title = '虚拟机'+moName+'视图';
				}
				if(moClassName=="Router"){
					moClass="router";
					title = '路由器'+moName+'视图';
				}
				if(moClassName=="Switcher"){
					moClass="switcher";
					title = '交换机'+moName+'视图';
				}
				if(moClassName=="SwitcherL2"){
					moClass="switcherl2";
					title = '二层交换机'+moName+'视图';
				}
				if(moClassName=="SwitcherL3"){
					moClass="switcherl3";
					title = '三层交换机'+moName+'视图';
				}
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
				var isExistPop = parent.parent.document.getElementById("tabs_window");
				var isPartentTabs = parent.document.getElementById("tabs_window");
				if(isExistPop != null){
					if (parent.parent.tabsLst && parent.parent.tabsLst.in_array(title) == true) {
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
					}
					else {
						parent.parent.$('#tabs_window').tabs('add',{
					    	title:title,
					    	content:content,
				            closable:true
					    });
						parent.parent.tabsLst && parent.parent.tabsLst.push(title);
					}
				}else if(isPartentTabs != null){
					if (parent.tabsLst != null && parent.tabsLst.in_array(title) == true) {
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
						parent.$('#tabs_window').tabs('add',{
				            title:title,
				            content:content,
				            closable:true
				        });
						parent.tabsLst.push(title);
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


function viewDevicePortal(moid,moClassName,moName){
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
				var title = "主机"+moName+"视图";
				var moClass="host";
				if(moClassName=="VHost"){
					moClass="vhost";
					title = "宿主机"+moName+"视图";
				}
				if(moClassName=="VM"){
					moClass="vm";
					title = "虚拟机"+moName+"视图";
				}
				if(moClassName=="Interface"){
					moClass="vm";
					title = "接口"+moName+"视图";
				}
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
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

//查看接口视图
function toShowIfView(moid,ifMoId,deviceIp,ifName){
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
			viewInterfacePortal(moid,ifMoId,data,deviceIp,ifName);
		}
	}
	ajax_(ajax_param);
	
}


//接口视图
function viewInterfacePortal(moid,ifMoId,moClassName,deviceIp,ifName){
	var portalName = "PHost/Interface";
	if (moClassName == 'Router') {
		var portalName = "Router/Interface";
	} else if (moClassName == 'Switcher') {
		var portalName = "Switcher/Interface";
	} else if (moClassName == 'VHost') {
		var portalName = "VHost/Interface";
	} else if (moClassName == 'VM') {
		var portalName = "VM/Interface";
	} else if (moClassName == 'SwitcherL2') {
		var portalName = "SwitcherL2/Interface";
	}else if (moClassName == 'SwitcherL3') {
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
				var moClass="host";
				if(moClassName=="VHost"){
					moClass="vhost";
				}
				if(moClassName=="VM"){
					moClass="vm";
				}
				if (moClassName == 'Router') {
					moClass="router";
				}
				if (moClassName == 'Switcher') {
					moClass="switcher";
				}
				if (moClassName == 'SwitcherL2') {
					moClass="switcherl2";
				}
				if (moClassName == 'SwitcherL3') {
					moClass="switcherl3";
				}
				var urlParams="?moID="+moid+"&IfMOID="+ifMoId+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
				var title = '设备IP'+deviceIp+'_接口'+ifName+'视图';
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

