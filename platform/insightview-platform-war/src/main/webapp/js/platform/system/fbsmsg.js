﻿
function showError()
{ 	
	var title='错误';
	var msg='操作失败！';
	if(arguments.length ==1)
		msg=arguments[0];
	if(arguments.length ==2){
		title=arguments[0];
		msg=arguments[1];
	}
	window.parent.$.messager.alert(title,msg,'error');	
}
function showMessage()
{
	var title='提示';
	var msg='提示信息内容！';
	if(arguments.length ==1)
		msg=arguments[0];
	
	window.parent.$.messager.alert(title,msg,'info');
}
function showSuccess()
{   

	//var title='提示';
	//var msg='操作成功！';
	var callBack=function(){return true;};
	var param =['提示','操作成功',callBack];
	if(arguments.length<=3){
		var index = 0;
		for(var i =0;i<arguments.length;i++){
			switch(typeof(arguments[i])){
				case 'string':
					param[i] = arguments[i];
					index++;
					continue;
				case 'function':
					param[2]=arguments[i];
					continue;
			}
		}
		if(index==1){
			param[1]=param[0];
			param[0]="提示";
		}
	}
	//if(arguments.length ==1)
	//	msg=arguments[0];
	//if(arguments.length ==2){
	//	title=arguments[0];
	//	msg=arguments[1];
	//}	
	//if(arguments.length ==3){
	//	title=arguments[0];
	//	msg=arguments[1];
	//	callBack = arguments[2];
	//}
	window.parent.$.messager.alert(param[0],param[1],'success',param[2]);	
	
} 

function showConfirm(){
	var callBack=function(){return true;};
	var param =['提示','确定删除吗？',callBack];
	if(arguments.length<=3){
		var index = 0;
		for(var i =0;i<arguments.length;i++){
			switch(typeof(arguments[i])){
				case 'string':
					param[i] = arguments[i];
					index++;
					continue;
				case 'function':
					var func=arguments[i];
					
					param[2] = function(r){
						if (r){func();}
					};
					continue;
			}
		}
		if(index==1){
			param[1]=param[0];
			param[0]="提示";
		}
	}
	window.parent.$.messager.confirm(param[0],param[1],param[2]);
}

function showWarning()
{   
	var title='警告';
	var msg='警告信息内容！';
	if(arguments.length ==1)
		msg=arguments[0];
	
	window.parent.$.messager.alert(title,msg,'warning');	
	
} 
/**关闭Default页面弹出窗口 刷新页面
 * BY wdj
 * */
function closeModalWin() {
	parent.updateTabs(); 
}

/**不关闭页面弹出窗口 刷新页面
 *
 * */
function refreshList() {
	parent.refresh(); 
}


/**关闭Default页面弹出窗口 不刷新页面
 * BY wdj
 * */
function closeModalWinChlid(name) {
	parent.$('#div_info_'+name).window('close', true);
//	parent.refreshAJ(refreshName); 
};



/**
 * 关闭Default页面弹出窗口 不刷新
 * */
function closeModalWinoOff () { 
	parent.$('#div_info').window('close', true);  
}


/**Default 页面  DIV层弹出
 * BY wdj
 * */
function showModalWin(url, title, width, height,scrolling,number) { 

	url=encodeURI(url);
 
	var strWidth;
	var strHeight;
	if (width == null){
		strWidth = 800;
	} else{
		strWidth = width;
	}
	if (height == null){
		strHeight = 500;
	} else{
		strHeight = height;
	}
	if (url != null) {
		var name='first';
		var divId='div_info';
		if(number){
			name=number;
			divId=divId+'_'+number;
		}
		// 隐藏竖直滚动条
		$("#"+divId).css({"overflow":"scroll", "overflow-y":"hidden"});
		if(scrolling){
		}else{
			scrolling='no';
		}
		var content = '<iframe name=\"'+name+'\" scrolling=\"'+scrolling+'\" frameborder=\"0\" src=\"' + url + '\" style=\"width:100%\;height:100%\;\"></iframe>';
		parent.$('#'+divId).window({ 
			modal:true,
			shadow:false,
			draggable: false,
			title: title,
			animate: false,
		    collapsible: false,        //折叠
		    minimizable: false,        //最小化
		    maximizable: false,        //最大化
		    resizable: false,        //改变窗口大小
		    content: content,  
			width: strWidth,
			height: strHeight
		});
	}
}

/**
 * 从父页面弹出子页面
 */
function showWindow(id, url, title, width, height){
	width = !width ? 600 : width;
	height = !height ? 600 : height;
	$("#"+id).css({"overflow":"scroll", "overflow-y":"hidden"});// 隐藏竖直滚动条
	var content = '<iframe scrolling="false" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
	$('#'+id).window({ 
		title: title,  
	    content:content,  
	    width: width,    
	    height: height,
		modal:true,
		shadow:false,//在窗体显示的时候是否显示阴影，默认true
		draggable: false,//是否能够拖拽窗口，默认为true
		animate: false,
	    collapsible: false,        //折叠
	    minimizable: false,        //最小化
	    maximizable: false,        //最大化
	    resizable: false        //能否改变窗口大小，默认为true
	});
}

/**Default 页面  DIV层弹出
 * BY wdj
 * */
function showModalWinForDlkp(url, title, width, height,scrolling,number) { 

	url=encodeURI(url);
 
	var strWidth;
	var strHeight;
	if (width == null)
	strWidth = 800;
	else
	strWidth = width;
	if (height == null)
	strHeight = 500;
	else
	strHeight = height;
	if (url != null) {
	var name='first';
	var divId='div_info';
	if(number){
	name=number;
	divId=divId+'_'+number;
	}
	if(scrolling){
	}else{
		scrolling='no';
	}
	var content = '<iframe name=\"'+name+'\" scrolling=\"'+scrolling+'\" frameborder=\"0\" src=\"' + url + '\" style=\"width:100%\;height:100%\;\"></iframe>';
	parent.parent.$('#'+divId).window({ 
		modal:true,
		shadow:false,
		draggable: false,
		title: title,
		animate: false,
	    collapsible: false,        //折叠
	    minimizable: false,        //最小化
	    maximizable: false,        //最大化
	    resizable: false,        //改变窗口大小
	    content: content,  
		width: strWidth,
		height: strHeight
		});
	}
}

/**Default 页面  2级DIV层弹出
 * BY wdj
 * */

function showModalWinForZjba(url, title, width, height,scrolling) { 

	url=encodeURI(url);
 
	var strWidth;
	var strHeight;
	if (width == null)
	strWidth = 800;
	else
	strWidth = width;
	if (height == null)
	strHeight = 500;
	else
	strHeight = height;
	if (url != null) {
	var name='sed';
	var divId='div_info_zjba_tabs';
 
	if(scrolling){
	}else{
		scrolling='no';
	} 
	var content = '<iframe name=\"'+name+'\" id=\"'+name+'\" scrolling=\"'+scrolling+'\" frameborder=\"0\" src=\"' + url + '\" style=\"width:100%\;height:100%\;\"></iframe>';
	parent.$('#'+divId).window({ 
		modal:true,
		draggable: false,
		title: title,
		animate: false,
	    collapsible: false,        //折叠
	    minimizable: false,        //最小化
	    maximizable: false,        //最大化
	    resizable: false,        //改变窗口大小
	    content: content,  
		width: strWidth,
		height: strHeight,
		top:(parent.$(window).height() + 100 - strHeight) * 0.5,
		left: (parent.$(window).width() + 400 - strWidth) * 0.5
		});
	}
}

/**Default 页面  DIV层弹出
 * BY wdj
 * */
function openModalWin(url, title, width, height,scrolling,number) { 

	url=encodeURI(url);
 
	var strWidth;
	var strHeight;
	if (width == null){
		strWidth = 800;
	} else{
		strWidth = width;
	}
	if (height == null){
		strHeight = 500;
	} else{
		strHeight = height;
	}
	if (url != null) {
		var name='first';
		var divId='div_info';
		if(number){
			name=number;
			divId=divId+'_'+number;
		}
		// 隐藏竖直滚动条
		$("#"+divId).css({"overflow":"scroll", "overflow-y":"hidden"});
		if(scrolling){
		}else{
			scrolling='no';
		}
		var content = '<iframe name=\"'+name+'\" scrolling=\"'+scrolling+'\" frameborder=\"0\" src=\"' + url + '\" style=\"width:100%\;height:100%\;\"></iframe>';
		$('#'+divId).window({ 
			modal:true,
			shadow:false,
			draggable: false,
			title: title,
			animate: false,
		    collapsible: false,        //折叠
		    minimizable: false,        //最小化
		    maximizable: false,        //最大化
		    resizable: false,        //改变窗口大小
		    content: content,  
			width: strWidth,
			height: strHeight
		});
	}
}




function checkToolBar(toolbar,ObjectName){  
	var rqUrl=getRootPath()+"/Admin/Security/getOperation" ;  
	
	$.ajax({
        type: "POST",         
        async:false,
        url: rqUrl , 
        data:{ObjectName:ObjectName},
        dataType: "json", 
        error: function(data) {  
        	for(index=0;index<toolbar.length;index++)
        		toolbar[index].disabled=true;
        },
        success: function(data) {  
      		 if(data.success){ 
             	for(index=0;index<toolbar.length;index++){ 
             		if(toolbar[index].rightKey != undefined && data.data.indexOf(toolbar[index].rightKey) == -1  )
             			{ 
             				toolbar[index].disabled=true;  
             			} 
             		else
             			toolbar[index].disabled=false; 
             	}

      		 }else{
             	for(index=0;index<toolbar.length;index++)
             		toolbar[index].disabled=true;
      		 }    
        }
    }); 
}
function checkButton(ObjectName){  
	if(arguments.length <=1)
		return; 
	var rqUrl=getRootPath()+"/Admin/Security/getOperation" ;  

	var btns=arguments;
	
	$.ajax({
        type: "POST",         
        async:false,
        url: rqUrl , 
        data:{ObjectName:ObjectName},
        dataType: "json", 
        error: function(data) {  
          for(index=1;index<btns.length;index++)
        	  btns[index].disabled=true; 
        },
        success: function(data) {  
           if(data.success){ 
               for(index=1;index<btns.length;index++){   
                 if(btns[index].attributes["rightkey"].value != undefined && data.data.indexOf(btns[index].attributes["rightkey"].value) == -1  )
                   {   
                	 btns[index].disabled="true";    
                	 btns[index].setAttribute("class","btndisabled");  
  
                	 btns[index].setAttribute('onclick', "void(0);");  
                   } 
             		else
             			btns[index].disabled="false"; 
             	}

      		 }else{
             	for(index=1;index<btns.length;index++)
             		btns[index].disabled=true; 
      		 }    
        }
    }); 	 
}


/**新版机构树*/

var currSelectedId;

var currMultiple;
var currParentId;
var currOurNnit;
var currViewName;
var currLazy;
var currSearch;

var currSetValue="";
var currGetValue="";

function  showSelectBox(id, url, multiple, ptitle, pwidth, pheight, parentId, ourNnit,viewName, lazy, search, setValue, getValue){
		

	var popupBox = document.getElementById("popupBox");
	if(!popupBox) {
		var popupBox = document.createElement("div");
		popupBox.setAttribute("style", "display:none;overflow:hidden"); 
		popupBox.setAttribute("id", "popupBox");
		document.body.appendChild(popupBox);
	}
	currSelectedId = id;	
	currMultiple = multiple;
	currParentId = parentId;
	currOurNnit = ourNnit;
	currViewName = viewName;
	currLazy = lazy;
	currSearch = search;
	
	currSetValue = setValue;
	currGetValue = getValue;
	
	showOrgWin(url,ptitle,pwidth,pheight,"auto");
	
}


function setValue(value, showValue){
	
	setValueById(currSelectedId, value, showValue);

}

function setValueById(id, value, showValue){
	
	if(showValue == "" || showValue == null ) {
		$.ajax({
	        type: "POST",         
	        async:false,
	        url: getRootPath()+"/sysOrg/queryDwmcByDwdm.json"  , 
	        data:{dwdm:value},
	        dataType: "json", 
	        error: function(data) {  
	         
	        },
	        success: function(data) {  
	           if(data.success){ 
	        	   showValue=data.dwmc;
	       		//document.getElementById(id + "_show").setAttribute("title", data.dwmc);
	        }
	        }
	    }); 	
	}

	if(currSetValue != "") {
		eval(currSetValue + "('" + id + "', '" +value +"', '" + showValue + "')");
	} else {
		document.getElementById(id + "_show").value=showValue;
		document.getElementById(id + "_show").setAttribute("title", showValue);
		document.getElementById(id).value=value;
		if(document.getElementById(id)) {
			document.getElementById(id).parentNode.setAttribute("class", "textbox easyui-fluid combo");
			document.getElementById(id+ "_show").setAttribute("class", "");
			document.getElementById(id).setAttribute("class", "");
		}
	}
	
	
	//}

}

function getMultiple() {
	return currMultiple;
}

function getParentId() {
	if(currParentId == null || currParentId =="" || currParentId.length <= 0) {
		return null;
	}
	return currParentId;
}

function getOurNnit() {
	return currOurNnit;
}

function getViewName() {
	return currViewName;
}


function getValue() {
	
	return getValueById(currSelectedId);
}

function getValueById(id) {
	
	if(currGetValue != "") {
		return eval(currGetValue + "('" + id + "')");
	} else {
		return document.getElementById(id).value;
	}
}

function getLazy() {
	return currLazy;
}

function getSearch() {
	return currSearch;
}



/**选择框  DIV层弹出
 * 
 * */
function showOrgWin(url, title, width, height,scrolling) { 

	url=encodeURI(url);
 
	var strWidth;
	var strHeight;
	if (width == "" || !width)
	strWidth = 300;
	else
	strWidth = width;
	if (height == "" || !height)
	strHeight = 400;
	else
	strHeight = height;
	if (url != null) {
	
	var divId='popupBox';
	document.getElementById(divId).style.display="";
	if(scrolling){
	}else{
		scrolling='no';
	}
	var content = '<iframe name=\"popupSelected\" scrolling=\"'+scrolling+'\" frameborder=\"0\" src=\"' + url + '\" style=\"width:100%\;height:100%\;\"></iframe>';
	$('#'+divId).window({ 
		modal:true,
		shadow:false,
		draggable: false,
		title: title,
		animate: false,
	    collapsible: false,        //折叠
	    minimizable: false,        //最小化
	    maximizable: false,        //最大化
	    resizable: false,        //改变窗口大小
	    content: content,  
		width: strWidth,
		height: strHeight
		});
	}
}

/**
 * 关闭选择框弹出窗口 不刷新
 * */
function closeOrgWinOff() { 
	document.getElementById("popupBox").style.display="none";
	$('#popupBox').window('close', true);  
}
