/**
 * 全局变量 项目根路径
 */
var path = getRootName();
var homePortalLst;
var eidtPortalName; 	
var eidtPortalDesc;
$(document).ready(function() {
	var tabsEditIndex = $("#tabsEditIndex").val();
//	console.log("tabsEditIndex === "+tabsEditIndex);
	uri = path + "/platform/platformPortal/toGetAllHomepages";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"portalType" : 2,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error"); 
			},
			success : function(data) {
//				console.log("data = "+data);
				if(data != null && data !=""){
					homePortalLst = data.split(",");
					if (tabsEditIndex != null && tabsEditIndex != "null") {
						$('#tabs_window').tabs({
							fit:true,
							selected:parseInt(tabsEditIndex),
							onSelect: function(title,index) {
								$('#tabsIndex').val(index);
//								console.log("index = "+index);
								tabsEditIndex = index;
								var portalNameVal = homePortalLst[tabsEditIndex]==undefined ? "":homePortalLst[tabsEditIndex].split(";")[0];
								$("#eidtPortalName").val(portalNameVal);
								eidtPortalName = portalNameVal;
//								eidtPortalDesc = decodeURI(homePortalLst[tabsEditIndex].split(";")[1]);
								if (portalNameVal == "homepage") {
									$("#ifr"+portalNameVal).attr("src",path+"/platform/platformPortal/showHomePagePortal?moType=homepage&flag=device");
								} else if (portalNameVal == "informationDesk"){
									$("#ifr"+portalNameVal).attr("src",path+"/platform/platformPortal/showHomePagePortal?moType=informationDesk&flag=device");
								} else {
									$("#ifr"+portalNameVal).attr("src",path+"/platform/platformPortal/showHomePagePortal?portalName="+portalNameVal+"&flag=device");
								}
							
							}
						});
					} else {
						$('#tabs_window').tabs({
							fit:true,
							onSelect: function(title,index) {
								$('#tabsIndex').val(index);
								var portalNameVal = homePortalLst[index]==undefined ? "":homePortalLst[index].split(";")[0];
								$("#eidtPortalName").val(portalNameVal);
								eidtPortalName = portalNameVal;
//								eidtPortalDesc = decodeURI(homePortalLst[index].split(";")[1]);
								if (portalNameVal == "homepage") {
									$("#ifr"+portalNameVal).attr("src",path+"/platform/platformPortal/showHomePagePortal?moType=homepage&flag=device");
								} else if (portalNameVal == "informationDesk"){
									$("#ifr"+portalNameVal).attr("src",path+"/platform/platformPortal/showHomePagePortal?moType=informationDesk&flag=device");
								} else {
									$("#ifr"+portalNameVal).attr("src",path+"/platform/platformPortal/showHomePagePortal?portalName="+portalNameVal+"&flag=device");
								}
								
							}
						});
					}
				}
			}
		}
		ajax_(ajax_param);
});

function viewMore(viewPath,newTitle){
	var uri=path + viewPath
	var content = '<iframe id="ifr2" scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
	parent.$('#tabs_window').tabs('add',{
        title:newTitle,
        content:content,
        closable:true
    });
	
	/*$('#tabs_window').tabs('select', 1);
	var serviceTab = $('#tabs_window').tabs('getSelected');
	$('#tabs_window').tabs('update',{
		 tab:serviceTab,
		 options:{
			 title:newTitle,
			 href: path + viewPath
		 }
	 });*/
}

function toChangePortal(){
	var index = $('#tabsIndex').val();
	var ifr = document.getElementById('ifr'+homePortalLst[index].split(";")[0]); 
	var win = ifr.window || ifr.contentWindow; 
	win.toChangePortal(); 
}

//动态新增tabs页签
function toShowTabsInfo(url,title,name,ifrName){
	var uri=path+url;
	var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;" name="'+ifrName+'"></iframe>';
	if(name == "noiframe"){
		$('#tabs_window').tabs('add',{
			id:name,
			title:title,
			href:uri,
			closable:true
		});
	}else{
		$('#tabs_window').tabs('add',{
			id:name,
			title:title,
			content:content,
			closable:true
		});
	}
}

//打开部件页面
function toOpenWidgets() {
	var tabsIndex = $('#tabsIndex').val();;
	var path = getRootName();
	var uri = path + "/platform/platformPortalEdit/toShowWidget?tabsIndex="+tabsIndex+"&portalName="+eidtPortalName;
	window.location.href = uri;
}

/**
 * 打开新增页面
 * 
 * @return
 */
function doOpenAdd(){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'新增视图',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/platformPortalEdit/toShowAdd'
    });
}

/**
 * 删除视图
 * @return
 */
function toDelPortal(){
	$.messager.confirm("提示", "确定删除当前视图？", function(r) {
		if (r == true) {
			eidtPortalDesc = $('#tabs_window').tabs('getSelected').panel('options').title; 
			eidtPortalName = $('#tabs_window').tabs('getSelected').panel('options').id;
//			console.log("eidtPortalName === "+eidtPortalName);
//			console.log("eidtPortalDesc === "+eidtPortalDesc);
			var uri = path + "/platform/platformPortalEdit/deletePortalView";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"portalName" : eidtPortalName,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data == true) {
						$("#tabs_window").tabs("close",eidtPortalDesc);
					} else {
						$.messager.alert("提示", "该视图删除失败！", "error");
					}
				}
			}
			ajax_(ajax_param);
		}
	});
}
