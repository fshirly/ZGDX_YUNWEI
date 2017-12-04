$(document).ready(
	function() {
		// 页面初始化加载树
		initTree();
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
function initTree() {
	var path = getRootPatch();
	var widgetFilter = $('#widgetFilter').val();
	var uri = path + "/monitor/gridsterEdit/findWidgetTreeVal";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "窗口部件", "javascript:treeClickAction(null,'无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
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
	var widgetContent = $("#ipt_widgetContent").val();
	if(window.opener) { 
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

