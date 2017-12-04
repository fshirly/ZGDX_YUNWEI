var urlMaps = getMap();
var widgetMaps = getMap();
var liIdMaps = getMap();
var liInfoMaps = getMap();
var widgetIndex;
var liInfo;
var widgetNames = "";
var tabsIndex;
//datagrid部件src
var ifrSrcMaps = getMap(); 
//其他src
var otherSrcMaps = getMap();
//所有方法参数
var widgetParamsMap = getMap();

var i = 0;
var f;
var ws = [];
var others = [];




/*function updateNextIframe(){
	var is = $.makeArray($("iframe"));
	for(var i = 0; i < is.length; i++) {
		if("" == is[i].src){
			is[i].src = getRootPatch() + ifrSrcMaps[ss[i]+"_"];
			return;
		}	
	}
	$.each(ws, function(k, v){
		resizeWidget(v);
	});
}*/

function updateNextIframe(){
	for(var i = 0; i < ss.length; i++) {
		if($("#"+ss[i]).attr("src") == undefined){
			$("#"+ss[i]).attr("src", getRootPatch()+ifrSrcMaps[ss[i]+"_"]);
			return;
		}	
	}
	$.each(ws, function(k, v){
		resizeWidget(v);
	});
	parent.parent.$.messager.progress('close');
}

function toChangePortalStyle(){
	var widgetIndex = "";
	for ( var i = 0; i < gridster.length; i++) {
		widgetIndex +=JSON.stringify(gridster[i].serialize())+"||";
	}
	widgetIndex=widgetIndex.substring(0, widgetIndex.lastIndexOf("||"));
	
// console.log(">>>>>>"+JSON.stringify(widgetIndex));
	var constent = window.opener.document.getElementById("ipt_portalContent").value;
// alert(constent);
	var path = getRootPatch();
	var moType = $("#moType").val();
	if (widgetNames != "") {
		widgetNames = widgetNames.substring(0, widgetNames.length-1);
	}
	if(moType == "platform"){
		uri = path + "/platform/platformPortalEdit/toChangePortalStyle";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"portalContent" : constent,
					"widgetIndex" :  widgetIndex,
					"urlMaps":JSON.stringify(urlMaps),
					"widgetMaps":JSON.stringify(widgetMaps),
					"widgetNames" : widgetNames,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if(data == ""){
						$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
					}else{
						window.opener.document.getElementById("ipt_portalContent").value = data.portalContent;
						window.close();
					}
					
				}
			}
			ajax_(ajax_param);
	}else{
		var uri = path + "/monitor/gridsterEdit/toChangePortalStyle";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"portalContent" : constent,
					"widgetIndex" :  widgetIndex,
					"widgetNames" : widgetNames,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if(data == ""){
						$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
					}else{
						window.opener.document.getElementById("ipt_portalContent").value = data.portalContent;
						window.close();
					}
				}
			}
			ajax_(ajax_param);
	}

}


/**
 * function iFrameHeight() { var ifm= document.getElementById("iframeName"); var
 * subWeb = document.frames ? document.frames["iframeName"].document :
 * ifm.contentDocument; if(ifm != null && subWeb != null) { ifm.height =
 * subWeb.body.scrollHeight; } }
 * 
 * function showDetail(url){ window.parent.frames.location = url; }
 */

function openChartLine(time,url){
	url = getRootPatch()+url+"&time="+time;
	window.parent.open(url,"","height=600px,width=1100px,left=200,top=150,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");	
}

var map = getMap();
function gaugeAutoRefresh(myChart,option,widgetName,fieldName,url){
	var timeTicket;
	var hideValue = $("#"+widgetName+"Hide").val();
	if(hideValue == "start"){
		timeTicket = setInterval(function (){
			var path = getRootPatch();
			var uri = url;
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
					var resultData = eval(data);
					option.series[0].data[0].value = resultData["data"][fieldName][0]["value"];
					myChart.setOption(option, true);
					$("#"+widgetName+"Hide").val("stop");
				}
			}
			ajax_(ajax_param);
		    
		},2000);
		map.put(widgetName,timeTicket);
	}
	if(hideValue == "stop"){
		clearInterval(map.get(widgetName));
		$("#"+widgetName+"Hide").val("start");
	}
}


function barAutoRefresh(myChart,option,widgetName,fieldName,url){
	var barTimeTicket;
	var hideValue = $("#"+widgetName+"Hide").val();
	if(hideValue == "start"){
		barTimeTicket = setInterval(function (){
			var path = getRootPatch();
			var uri = url;
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
					var resultData = eval(data);
					option.series[0].data = resultData["data"][fieldName];
					myChart.setOption(option, true);
					$("#"+widgetName+"Hide").val("stop");
				}
			}
			ajax_(ajax_param);
		    
		},2000);
		map.put(widgetName,barTimeTicket);
	}
	if(hideValue == "stop"){
		clearInterval(map.get(widgetName));
		$("#"+widgetName+"Hide").val("start");
	}

}


function embedAutoRefresh(widgetName,url){
	var embedTimeTicket;
	var hideValue = $("#"+widgetName+"Hide").val();
	if(hideValue == "start"){
		embedTimeTicket = setInterval(function (){
			$("#"+widgetName+"Frame").attr("src",url);
		},2000);
		$("#"+widgetName+"Hide").val("stop");
		map.put(widgetName,embedTimeTicket);
	}
	if(hideValue == "stop"){
		clearInterval(map.get(widgetName));
		$("#"+widgetName+"Hide").val("start");
	}
	
	
}

// 编辑完加载部件

function doReLoadData(time,widgetTitle,url,widgetName,widgetId){
	var reg=new RegExp(";","g");
	url = url.replace(reg,"&");
	urlMaps.put(widgetName,url);
	widgetMaps.put(widgetId,widgetTitle);
	eval("L"+widgetName+"('24H','"+widgetTitle+"','"+url+"')");
}


// 定义简单Map
function getMap() {// 初始化map_,给map_对象增加方法，使map_像Map
         var map_ = new Object();     
         map_.put = function(key, value) {     
             map_[key+'_'] = value;     
         };     
         map_.get = function(key) {     
             return map_[key+'_'];     
         };     
         map_.remove = function(key) {     
             delete map_[key+'_'];     
         };     
         map_.keyset = function() {     
             var ret = "";     
             for(var p in map_) {     
                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {     
                     ret += ",";     
                     ret += p.substring(0,p.length-1);     
                 }     
             }     
             if(ret == "") {     
                 return ret.split(",");     
             } else {     
                 return ret.substring(1).split(",");     
             }     
         };     
         return map_;     
}   


function toChangePortal(){
	var widgetIndex = "";
	for ( var i = 0; i < gridster.length; i++) {
		widgetIndex +=JSON.stringify(gridster[i].serialize())+"||";
	}
	widgetIndex=widgetIndex.substring(0, widgetIndex.lastIndexOf("||"));
	
// alert(JSON.stringify(widgetMaps));
	var constent = $("#contentXml").val();
	var portalName = $("#portalName").val();
// alert(constent);
	
	var path = getRootPatch();
	var moType = $("#moType").val();
	var userId = $("#userId").val();
	if (widgetNames != "") {
		widgetNames = widgetNames.substring(0, widgetNames.length-1);
	}
	if(moType == "platform"){
		uri = path + "/platform/platformPortalEdit/toChangePortalStyle";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"portalContent" : constent,
					"widgetIndex" :  widgetIndex,
					"urlMaps":JSON.stringify(urlMaps),
					"widgetMaps":JSON.stringify(widgetMaps),
					"widgetNames" : widgetNames,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if(data == ""){
						$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
					}else{
						window.opener.document.getElementById("ipt_portalContent").value = data.portalContent;
						window.close();
					}
					
				}
			}
			ajax_(ajax_param);
	}else{
		var portalChName = "";
		if(moType == "homepage"){
			portalChName = "首页";
		}else if(moType == "informationDesk"){
			portalChName = "服务台";
		}
// var uri = path + "/monitor/gridsterEdit/toChangePortalStyle";
		uri = path + "/platform/platformPortalEdit/toChangePortalStyle?flag=homepage";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"portalContent" : constent,
					"widgetIndex" :  widgetIndex,
					"urlMaps":JSON.stringify(urlMaps),
					"widgetMaps":JSON.stringify(widgetMaps),
					"widgetNames" : widgetNames,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if (userId != "") {
						var uri=path+"/monitor/gridsterEdit/modifyPortalInfo";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"portalName":portalName,
									"portalContent":data.portalContent,
									"portalDesc":$("#portalDesc").val(),
									"portalType":2,
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data==true){
								$.messager.alert("提示","视图修改成功！","info");
								$(".panel.window").css("top","233px");
							}else{
								$.messager.alert("提示","视图修改失败！","error");
								$(".panel.window").css("top","233px");
							}
						}
						};
							ajax_(ajax_param);
					} else {
						var userAccount = $("#userAccount").val();
						var uri=path+"/monitor/gridsterEdit/addPortalInfo";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"portalName":"homepage"+userAccount,
									"portalContent":data.portalContent,
									"portalDesc":userAccount+portalChName,
									"portalType":2,
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data==true){
								$.messager.alert("提示","视图修改成功！","info");
								$(".panel.window").css("top","233px");
							}else{
								$.messager.alert("提示","视图修改失败！","error");
								$(".panel.window").css("top","233px");
							}
						}
						};
							ajax_(ajax_param);
					}
				
				}
			}
			ajax_(ajax_param);
	}

}

// 用户第一次登录，自动保存首页视图
function doSaveHomepage(){
	var widgetIndex = "";
	for ( var i = 0; i < gridster.length; i++) {
		widgetIndex +=JSON.stringify(gridster[i].serialize())+"||";
	}
	widgetIndex=widgetIndex.substring(0, widgetIndex.lastIndexOf("||"));
	var constent = $("#contentXml").val();
	var portalName = $("#portalName").val();
	var path = getRootPatch();
	var moType = $("#moType").val();
	var userId = $("#userId").val();
	var uri = path + "/platform/platformPortalEdit/toChangePortalStyle?flag=homepage";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"portalContent" : constent,
				"widgetIndex" :  widgetIndex,
				"urlMaps":JSON.stringify(urlMaps),
				"widgetMaps":JSON.stringify(widgetMaps),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error"); 
			},
			success : function(data) {
				var userId = $("#userId").val();
				if (userId == "") {
					var userAccount = $("#userAccount").val();
					var uri=path+"/monitor/gridsterEdit/addPortalInfo";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"portalName":"homepage"+userAccount,
								"portalContent":data.portalContent,
								"portalType":2,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						
					}
					};
						ajax_(ajax_param);
				}
			}
		}
		ajax_(ajax_param);
}



function resizeWidgetByParams(liInfoAndIndex){
//	ws.push(liInfoAndIndex);
	resizeWidget(liInfoAndIndex);
	updateNextIframe();	
}

function resizeWidget(liInfoAndIndex){
//	console.log(">>>>>>>>>>1 "+new Date());
	var widgetIndex = liInfoAndIndex.split("|")[0];
	var liId = liInfoAndIndex.split("|")[1];
	var liWidth = liInfoAndIndex.split("|")[2];
	var isExist = $("#"+liId).find("iframe").contents().find(".datagrid-view2").length;
	if (isExist > 0) {
		var tr_count = $("#"+liId).find("iframe").contents().find(".datagrid-view2").find(".datagrid-body").find(".datagrid-btable").find("tr").length;
		if (tr_count <= 2) {
			gridster[0].resize_widget(gridster[0].$widgets.eq(widgetIndex), liWidth, 4);
		} else if (tr_count > 2 && tr_count < 10) {
			gridster[0].resize_widget(gridster[0].$widgets.eq(widgetIndex),liWidth,tr_count+2);
		} else if (tr_count >= 10) {
			gridster[0].resize_widget(gridster[0].$widgets.eq(widgetIndex),liWidth,10);
		} 
	}
//	console.log(">>>>>>>>>>2 "+new Date());
}

function removeWidget(liId){
// console.log("liId = "+liId);
	var liIdNew = liId.substring(1,liId.length);
	var tempStr = liId.substring(1,2);
	if (isnumericvalid(tempStr) == true) {
		var numTemp = getNum(liId.substring(2));
		if (numTemp != "") {
			liIdNew = liId.substring(1,liId.indexOf(numTemp))+"#"+liId.substring(liId.indexOf(numTemp));
		}
	} else {
		var num = getNum(liId);
		if (num != "") {
			liIdNew = liId.substring(1,liId.indexOf(num))+"#"+liId.substring(liId.indexOf(num));
		}
	}
	widgetNames = widgetNames + liIdNew +",";
	var liIndex = $('.gridster li').index($("#"+liId)[0]);
	gridster[0].remove_widget( $('.gridster li').eq(liIndex) );// 删除某个
}

$.AutoiFrame = function(_o,widgetIndex,liInfo){
    if($.support.msie){
        $('#'+_o).ready(resizeWidget(widgetIndex,liInfo));
    }else{
        $('#'+_o).load(resizeWidget(widgetIndex,liInfo));
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

function getNum(str){
	var value = str.replace(/[^0-9]/ig,""); 
	return value;
	}

/**
 * 判断字符串是否为数字
 * 
 * @param str
 * @return
 */
function isnumericvalid(value){
	var Regx = /^[A-Za-z0-9]*$/;
	if (Regx.test(value)){
		return true;
	}
	else{
		return false;
	} 
}

function loadIfrSrc(){
	var count = 0;
	var srcLst = [];
	for(var key in ifrSrcMaps){
		if(key != "put" && key != "get" && key != "remove" && key != "keyset"){
			var ifrId = key.substring(0,key.length-1);
			$("#"+ifrId).attr("src",getRootPatch()+ifrSrcMaps[key]);
			srcLst.push(ifrId);
			count ++;
			if(count == 4){
				removeIfrMap(srcLst);
				break;
			}else{
				if(count == ifrSrcMaps.keyset().length){
					removeIfrMap(srcLst);
					break;
				}
			}
		}
		
	}
}

function removeIfrMap(srcLst){
	for ( var int = 0; int < srcLst.length; int++) {
		ifrSrcMaps.remove(srcLst[int]);
	}
}

function refreshIfrView(ifrName){
	var url = otherSrcMaps[ifrName+"_"];
//	console.log(otherSrcMaps);
//	console.log("url1 = "+url);
	if(url == null || url == undefined){
		console.log("url2 = "+url);
		url = ifrSrcMaps[ifrName+"_"];
	}
//	console.log("url3 = "+url);
//	console.log("ifrName = "+ifrName);
	$("#"+ifrName).attr("src", getRootPatch()+url);
}

/**
 * function evalFuns(){ for ( var int = 0; int < funLists.length; int++) {
 * console.log("funLists["+int+"] >>>> "+funLists[int]); eval(funLists[int]); } }
 * 
 * function insertParam(time,widgetName,widgetTitle,url,flag){
 * funLists.push('L'+widgetName+'("'+time+'","'+widgetTitle+'","'+url+'",'+flag+');'); }
 */
