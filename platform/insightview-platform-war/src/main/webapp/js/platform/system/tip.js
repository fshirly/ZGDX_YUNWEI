//添加文本框事件
$(document).ready(function(){
	var txtTipDiv = document.createElement("div");//创建tip div对象
	
	txtTipDiv.id = "txtTipDiv";
	if (null == document.getElementsByTagName("body").item(0))
		return;
	document.getElementsByTagName("body").item(0).appendChild(txtTipDiv);//div加入body 
	
	var texts = $("input[type=text]");//获取所有文本框
	
	for(var i=0;i<texts.length;i++)//加入事件
	{
		var id = texts[i].id;
		
		if(id==''||id==null||typeof(id)=='undefined')//id为空
			continue;
		if (window.addEventListener) //firefox
		{ 
			texts[i].addEventListener("mouseover",new Function("textMouseOver('"+"#"+id+"')"), false); 
			texts[i].addEventListener("mouseout",new Function("textMouseOut()"), false); 
		}else//ie
		{
			texts[i].attachEvent("onmouseover",new Function("textMouseOver('"+"#"+id+"')"));
			texts[i].attachEvent("onmouseout",new Function("textMouseOut()"));
		}
	}
	
	var textareas = $("textarea");//获取所有文本框
	
	for(var i=0;i<textareas.length;i++)//加入事件
	{
		var id = textareas[i].id;
		
		if(id==''||id==null||typeof(id)=='undefined')//id为空
			continue;
		if (window.addEventListener) //firefox
		{ 
			textareas[i].addEventListener("mouseover",new Function("textMouseOver('"+"#"+id+"')"), false); 
			textareas[i].addEventListener("mouseout",new Function("textMouseOut()"), false); 
		}else//ie
		{
			textareas[i].attachEvent("onmouseover",new Function("textMouseOver('"+"#"+id+"')"));
			textareas[i].attachEvent("onmouseout",new Function("textMouseOut()"));
		}
	}
});

//添加文本框鼠标移入事件
function textMouseOver(id)
{
	if(navigator.userAgent.indexOf("Firefox") > 0) {
		var $E = function() {
			var c = this.caller; 
			while(c.caller) c = c.caller; 
			return c.arguments[0];
			};
		__defineGetter__("event", $E);
	}
	var minLength = 10;//每多少截断
	var cutLength = 20;//每多少截断
	
	var val = $(id).val()==''?'':$(id).val();//文本框值
	if (val.length < minLength) {
		return;
	}
	
	var length = val.length%cutLength==0?Math.floor(val.length/cutLength):Math.floor(val.length/cutLength)+1;//截成几段
	
	var newval = "";//新值
	
	for(var i=0;i<length;i++)
	{
		newval+=val.substr(i*cutLength,val.length-i*cutLength>cutLength?cutLength:val.length-i*cutLength)+"<br>";//如果当前开始位置到结束多于每截断值则每截断值否则实际值
	}
	//获取当前鼠标位置的X坐标
    var x = document.body.scrollLeft + event.clientX;
    //获取当前鼠标位置的Y坐标
    var y = document.body.scrollTop + event.clientY;

	var tipDiv = document.getElementById('txtTipDiv');
	tipDiv.style.backgroundColor = "#FFFFE1";
	tipDiv.style.border = "solid 1px #000000";
	tipDiv.style.zIndex = 100000;
	tipDiv.style.paddingTop = 2;
	tipDiv.style.paddingBottom = 2;
	tipDiv.style.paddingLeft = 2;
	tipDiv.style.paddingRight = 2;
	tipDiv.style.posTop = y + 3;
	tipDiv.style.posLeft = x + 3;
	tipDiv.innerHTML = newval;
	tipDiv.style.position="absolute";
	tipDiv.style.display='';
}

//添加文本框鼠标移出事件
function textMouseOut()
{
	var tipDiv = document.getElementById('txtTipDiv');
	if (null == tipDiv)
		return;
	tipDiv.style.display='none';
}

// 添加easyUIdatagrid列TIP提示
function showColTip(value,len) {
	var strLength=10;
	if(len){
		strLength=len;
	}
	var strShow = value;
	if (null != strShow && strShow.length > strLength) {
		strShow = strShow.substr(0, strLength) + "...";
	}else if (strShow==null||strShow=="null"||strShow==undefined){
		strShow="";
	}	
	return "<label title=\""+value+"\">"+strShow+"</label>";
}