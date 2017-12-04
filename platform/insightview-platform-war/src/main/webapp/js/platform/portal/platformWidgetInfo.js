var dataTree = new dTree("dataTree", getRootPatch() + "/plugin/dTree/img/");
dataTree.config.check=true;
$(document).ready(
	function() {
		var portalId = $("#portalId").val();
		var portalName = $("#portalName").val();
		// 页面初始化加载树
		initTree(portalId,portalName);
		doInitWidgetPhoto();
	}
);

//树数据
var _treeData = "";

var _currentNodeId = -1;
var _currentNodeName = "";

function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	reloadTable();
	
}

/*
 * 更新界面
 */
function reloadTable() {
	$("#ipt_widgetId").val(_currentNodeId);
	var widgetId = $("#ipt_widgetId").val();
	doInitWidgetPhoto();
}

/**
 * 初始化树菜单
 */

function initTree(portalId,portalName) {
	var path = getRootPatch();
	var widgetFilter = $('#widgetFilter').val();
	var uri = path + "/monitor/gridsterEdit/findWidgetTreeVal?portalId="+portalId+"&portalName="+portalName;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"widgetFilter" : widgetFilter,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			dataTree.add(0, -1, "窗口部件", "javascript:treeClickAction(null,'无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			var menuWidgetIdArr = data.defaultWidgetIds;
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].widgetId;
				var _name = gtmdlToolList[i].widgetName;
				var _parent = gtmdlToolList[i].widgetGroupId;
				dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
			}
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append(dataTree + "");
			dataTree.setDefaultCheck(menuWidgetIdArr);
			//操作tree对象   
			dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}

//加载某一个部件快照
function doInitWidgetPhoto(){
	var widgetId = $("#ipt_widgetId").val();
	var path = getRootPatch();
	var uri = path + "/monitor/gridsterEdit/showOneWidget";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"widgetId" : widgetId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			$("#imgWidget").attr("src",path+data.previewImage);
			$("#ipt_widgetContent").val(data.widgetContent);
		}
	}
	ajax_(ajax_param);
	
}

function toInsert(){
	var selids=dataTree.getCheckedNodes();  
	console.log("selids = "+selids);
	if (selids != null && selids != "") {
		var path = getRootPatch();
		var uri = path + "/monitor/gridsterEdit/toGetMultWidget?widgetId="+selids;
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
				
				data = decodeURI(data);
//				console.log("widgetData = "+data);
				$("#ipt_widgetContent").val(data);
				var widgetInfos = data.split("</widget>");
				var widgetContentAll = "";
				for ( var int = 0; int < widgetInfos.length; int++) {
					var randomValue = parseInt(100*Math.random());
					if (widgetInfos[int] != "") {
						var widgetId = widgetInfos[int].split("|")[0];
						var widgetContent = widgetInfos[int].split("|")[1];
//						console.log("widgetContent = "+widgetContent);
						//处理widgetName
						var strStart = widgetContent.substring(0,widgetContent.indexOf("name"));
//						console.log("strStart = "+strStart);
						var strMid = widgetContent.substring(widgetContent.indexOf("name"),widgetContent.indexOf("title"));
						strMid = strMid.split("\"")[0]+"\""+strMid.split("\"")[1]+"#"+widgetId+"#"+randomValue+"\"";
//						console.log("strMid = "+strMid);
						var strEnd = widgetContent.substring(widgetContent.indexOf("title"));
//						console.log("strEnd = "+strEnd);
						widgetContent = strStart+" "+strMid+" "+strEnd;
						//处理chart，新增id属性用来更新对应的url
						if(widgetContent.indexOf("chart") >= 0){
							var strStartChart = widgetContent.substring(0,widgetContent.indexOf("type"));
							var strMidChart = " id = \""+(strMid.split("\"")[1].toString()).split("#")[0]+(strMid.split("\"")[1].toString()).split("#")[2]+"\" ";
							var strEndChart = widgetContent.substring(widgetContent.indexOf("type"));
							widgetContent = strStartChart+strMidChart+strEndChart;
						}
						if(widgetContent.indexOf("embed") >= 0){
							var strStartChart = widgetContent.substring(0,widgetContent.indexOf("url"));
							var strMidChart = " id = \""+(strMid.split("\"")[1].toString()).split("#")[0]+(strMid.split("\"")[1].toString()).split("#")[2]+"\" ";
							var strEndChart = widgetContent.substring(widgetContent.indexOf("url"));
							widgetContent = strStartChart+strMidChart+strEndChart;
						}
						widgetContent = widgetContent+"</widget>";
						widgetContentAll = widgetContentAll + widgetContent;
					}
					
				}
				if (window.opener) { 
				     fWindowText1 = window.opener.document.getElementById("ipt_portalContent"); 
				     fWindowText1.focus();  
				     var reg=new RegExp("><","g"); 
				     if(document.selection){
				    	 	var s= window.opener.document.selection.createRange();  
				    	 	s.text=widgetContentAll;
				    	 	fWindowText1.value=formatXml(fWindowText1.value);
				    	 }else{
				    		 var startPos = fWindowText1.selectionStart;  
				    		 var endPos = fWindowText1.selectionEnd;  
				    		 var cursorPos = startPos; 
				    		 var tmpStr = fWindowText1.value;  
				    		 fWindowText1.value = tmpStr.substring(0, startPos) + widgetContentAll + tmpStr.substring(endPos, tmpStr.length);  
				    		 cursorPos += widgetContentAll.length;  
				    		 fWindowText1.selectionStart = fWindowText1.selectionEnd = cursorPos;  
				    		 fWindowText1.value=fWindowText1.value.replace(reg, ">\n<");
				    	 } 
				     window.close();
				} else {
					var tempInfos = widgetContentAll.split("</widget>");
					var tabsIndex = $("#tabsIndex").val();
					var widgetContentNew = "";
					for ( var int2 = 0; int2 < tempInfos.length; int2++) {
						var widgetContentTmp = tempInfos[int2];
						if (widgetContentTmp != "") {
							widgetContentTmp = widgetContentTmp.substring(0,widgetContentTmp.indexOf(">"))+" rowspan=\"9\" colspan=\"30\" col=\"1\" row=\"1\""+widgetContentTmp.substring(widgetContentTmp.indexOf(">"));
							widgetContentTmp = widgetContentTmp + "</widget>";
							widgetContentNew = widgetContentNew + widgetContentTmp;
						}
					}
//					console.log("widgetContentNew = "+widgetContentNew);
					var path = getRootPatch();
					var uri = path + "/platform/platformPortal/initWidgetContent";
					var homeUri = path + "/dashboard/toDashboardList?tabsIndex="+tabsIndex;
					var ajax_param = {
							url : uri,
							type : "post",
							datdType : "json",
							data : {
								"widgetContent" : widgetContentNew,
								"t" : Math.random()
							},
							error : function() {
								$.messager.alert("错误", "ajax_error", "error"); 
							},
							success : function(data) {
								if (data == true) {
									window.location.href = homeUri;
								}
							}
						}
						ajax_(ajax_param);
				}
			}
		}
		ajax_(ajax_param);
	} else {
		var randomValue = parseInt(100*Math.random());
		var widgetId = $("#ipt_widgetId").val();
		var widgetContent = $("#ipt_widgetContent").val();
		//处理widgetName
		var strStart = widgetContent.substring(0,widgetContent.indexOf("name"));
//		console.log("strStart = "+strStart);
		var strMid = widgetContent.substring(widgetContent.indexOf("name"),widgetContent.indexOf("title"));
		strMid = strMid.split("\"")[0]+"\""+strMid.split("\"")[1]+"#"+widgetId+"#"+randomValue+"\"";
//		console.log("strMid = "+strMid);
		var strEnd = widgetContent.substring(widgetContent.indexOf("title"));
//		console.log("strEnd = "+strEnd);
		widgetContent = strStart+" "+strMid+" "+strEnd;
		//处理chart，新增id属性用来更新对应的url
		if(widgetContent.indexOf("chart") >= 0){
			var strStartChart = widgetContent.substring(0,widgetContent.indexOf("type"));
			var strMidChart = " id = \""+(strMid.split("\"")[1].toString()).split("#")[0]+(strMid.split("\"")[1].toString()).split("#")[2]+"\" ";
			var strEndChart = widgetContent.substring(widgetContent.indexOf("type"));
			widgetContent = strStartChart+strMidChart+strEndChart;
		}
		if(widgetContent.indexOf("embed") >= 0){
			var strStartChart = widgetContent.substring(0,widgetContent.indexOf("url"));
			var strMidChart = " id = \""+(strMid.split("\"")[1].toString()).split("#")[0]+(strMid.split("\"")[1].toString()).split("#")[2]+"\" ";
			var strEndChart = widgetContent.substring(widgetContent.indexOf("url"));
			widgetContent = strStartChart+strMidChart+strEndChart;
		}
		if (window.opener) { 
		     fWindowText1 = window.opener.document.getElementById("ipt_portalContent"); 
		     fWindowText1.focus();  
		     var reg=new RegExp("><","g"); 
		     if(document.selection){
		    	 	var s= window.opener.document.selection.createRange();  
		    	 	s.text=widgetContent;
		    	 	fWindowText1.value=formatXml(fWindowText1.value);
		    	 }else{
		    		 var startPos = fWindowText1.selectionStart;  
		    		 var endPos = fWindowText1.selectionEnd;  
		    		 var cursorPos = startPos; 
		    		 var tmpStr = fWindowText1.value;  
		    		 fWindowText1.value = tmpStr.substring(0, startPos) + widgetContent + tmpStr.substring(endPos, tmpStr.length);  
		    		 cursorPos += widgetContent.length;  
		    		 fWindowText1.selectionStart = fWindowText1.selectionEnd = cursorPos;  
		    		 fWindowText1.value=fWindowText1.value.replace(reg, ">\n<");
		    	 } 
		     window.close();
		} else {
			var tabsIndex = $("#tabsIndex").val();
			widgetContent = widgetContent.substring(0,widgetContent.indexOf(">"))+" rowspan=\"9\" colspan=\"15\" col=\"1\" row=\"1\""+widgetContent.substring(widgetContent.indexOf(">"));
			var path = getRootPatch();
			var uri = path + "/platform/platformPortal/initWidgetContent";
			var homeUri = path + "/dashboard/toDashboardList?tabsIndex="+tabsIndex;
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"widgetContent" : widgetContent,
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error"); 
					},
					success : function(data) {
						if (data == true) {
							window.location.href = homeUri;
						}
					}
				}
				ajax_(ajax_param);
		}
	}
	
}

//格式化xml   
function formatXml(str){       
	   var path = getRootName();
     //去除输入框中xmll两端的空格。    
       str = str.replace(/^\s+|\s+$/g,"");    
       var source = new ActiveXObject("Msxml2.DOMDocument");    
      //装载数据    
       source.async = false;    
       source.loadXML(str);       
       // 装载样式单    
       var stylesheet = new ActiveXObject("Msxml2.DOMDocument");    
       stylesheet.async = false;    
       stylesheet.resolveExternals = false;    
       stylesheet.load(path+"/js/monitor/portal/format.xsl");    
          
       // 创建结果对象    
       var result = new ActiveXObject("Msxml2.DOMDocument");    
       result.async = false;    
          
       // 把解析结果放到结果对象中方法1    
       source.transformNodeToObject(stylesheet, result);    
       //alert(result.xml);   
       if(result.xml==''||result.xml==null){   
            alert('xml报文格式错误，请检查');   
            return false;   
           }   
       var finalStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" +result.xml;   
       return finalStr;   
}  

function doClose(){
	if (window.opener == undefined) {
//		history.back();
		var tabsIndex = $('#tabsIndex').val();;
		var path = getRootName();
		var uri = path + "/dashboard/toDashboardList?tabsIndex="+tabsIndex;
		window.location.href = uri;
	} else {
		window.close();
	}
}
