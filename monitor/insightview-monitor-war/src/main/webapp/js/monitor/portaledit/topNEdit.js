/*$(document).ready(function() {
	changeTime();
});*/
   function checkTip(){
	   $("#tipDiv").html("<font color='red'>友情提醒：排名和标题务必保持一致！</font>");
	  setTimeout(function(){ $("#tipDiv").html(''); }, 3000);
   }

/**
 * 提交
 */
function doCommit() {
	if (checkNum() && checkInfo("#tblEdit")) {
			var widgetTitle = $("#widgetTitle").val();
			var widgetName = $("#widgetName").val();
			var widgetId = $("#widgetId").val();
			var timeBegin = $("#timeBegin").val();
			var timeEnd = $("#timeEnd").val();
			var topOrder= $("#topOrder").val();
			var num = $("#num").val();
			var url = $("#url").val();
			if(url.indexOf("topOrder") > 0 )
			{
				url=url.substring(0,url.indexOf("topOrder")-1);
			}
			//var url = $("#url").val().replace(/(topOrder=.*?;)|(timeBegin=.*?;)|(timeEnd=.*?;)/g, '');
			url=url+";topOrder="+topOrder+";timeBegin="+timeBegin+";timeEnd="+timeEnd;
			url =url.replace(/(num=)[^;]+/, '$1' + num);
			var index = window.$('#tabsIndex').val();
			var eidtPortalName = window.$('#eidtPortalName').val();
			if(index != undefined){
				var ifr = window.document.getElementById('ifr'+eidtPortalName); 
				var win = ifr.window || ifr.contentWindow; 
				win.doReLoadData("24H",widgetTitle,url,widgetName,widgetId);
			}else{
				window.doReLoadData("24H",widgetTitle,url,widgetName,widgetId);
			}
			$('#popView').window('close');
	}
}

function changeTime(){
	var newStart;
	var newEnd;
	var selVal = $("#timeTemplate").val();
		if(selVal==1){
			//最近半小时
			var nd = addNDays(0.5*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==2){
			//最近1小时
			var nd = addNDays(1*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==3){
			//最近2小时
			var nd = addNDays(2*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==4){
			//最近4小时
			var nd = addNDays(4*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==5){
			//最近6小时
			var nd = addNDays(6*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==6){
			//最近12小时
			var nd = addNDays(12*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==7){
			//最近1天
			var nd = addNDays(24*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==8){
			//最近2天
			var nd = addNDays(48*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==9){
			//今天
			var date = new Date();
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==10){
			//昨天
			var date = new Date();
			date.setDate(date.getDate()-1);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
			var end = new Date();//结束时间
			end.setDate(end.getDate()-1);
			end.setHours(23);
			end.setMinutes(59);
			end.setSeconds(59);
			newEnd = new Date(end.getTime()).format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==11){
			//本周
			var date = new Date();
			var days = 0;
			if(date.getDay() == 0){
				days = date.getDay()+6;
			}else{
				days = date.getDay()-1;
			}
			var nd = addNDays(days*24*60*60*1000);
			nd.setHours(0);
			nd.setMinutes(0);
			nd.setSeconds(0);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==12){
			//最近一周
			var nd = addNDays(7*24*60*60*1000);
			newStart = nd.format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==13){
			//本月
			var date = new Date();
			date.setDate(1);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
		}else if(selVal==14){
			//上月
			var date = new Date();
			date.setMonth(date.getMonth()-1);
			date.setDate(1);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
			var end = new Date();
			end.setMonth(end.getMonth()-1);
			var days = getDaysOfMonth(end);
			end.setDate(days);
			end.setHours(23);
			end.setMinutes(59);
			end.setSeconds(59);
			newEnd = new Date(end.getTime()).format('yyyy-MM-dd hh:mm:ss');
		}
		$("#timeBegin").val(newStart);//开始时间		
		if(selVal == 1 || selVal == 2 || selVal == 3 || selVal == 4 || 
				selVal == 5 || selVal == 6 || selVal == 7 || selVal == 8 || 
				selVal == 9 || selVal == 11 || selVal == 12 || selVal == 13 ){
			var end = new Date();//结束时间
			newEnd = end.format('yyyy-MM-dd hh:mm:ss');
		}
		
		$("#timeEnd").val(newEnd);
	
	//$("#timeTemplate option").eq(selVal).attr('selected', 'true');
	$("#timeTemplate").find("option[value="+selVal+"]").attr('selected', 'true');
}

Date.prototype.format =function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)){ 
        format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(format)){
            format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+o[k]).length));
        }
    }
    return format;
}
var addNDays=function(n){
	var date = new Date();
    var time=date.getTime();
    var newTime=time-n;
    return new Date(newTime);
};

//获取一个月多少天
function getDaysOfMonth(date){   
    var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);   
    var strYear = date.getFullYear();     
    var strDay = date.getMonth()+1;    
    var strMonth = date.getMonth()+1;   
    var strTime = date.getTime();
    if(strYear%4 == 0 && strYear%100 != 0){   
       daysInMonth[2] = 29;   
    }   
    var days = daysInMonth[strDay];
    return days;   
 }

function getLastMonthYestdy(date,n){   
    var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);   
    var strYear = date.getFullYear();     
    var strDay = date.getDate();     
    var strMonth = date.getMonth()+1;   
    var strTime = date.getTime();
    if(strYear%4 == 0 && strYear%100 != 0){   
       daysInMonth[2] = 29;   
    }   
    if(n == 1){
   	 if(strMonth - 1 == 0)   
	     {   
	        strYear -= 1;   
	        strMonth = 12;   
	     }   
	     else  
	     {   
	        strMonth -= 1;   
	     } 
    }else if(n == 3){
   	 if(strMonth - 3 == 0)   
	     {   
	        strYear -= 1;   
	        strMonth = 12;   
	     }   
	     else  
	     {   
	        strMonth -= 3;   
	     } 
    }else if(n == 12){
   	 strYear -= 1; 
    }
      
    strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];   
    if(strMonth<10)     
    {     
       strMonth="0"+strMonth;     
    }   
    if(strDay<10)     
    {     
       strDay="0"+strDay;     
    }   
    datastr = strYear+"-"+strMonth+"-"+strDay;   
    return datastr;   
 } 

function getDays(strDateStart,strDateEnd){
	   var strSeparator = "-"; //日期分隔符
	   var oDate1;
	   var oDate2;
	   var iDays;
	   oDate1= strDateStart.split(strSeparator);
	   oDate2= strDateEnd.split(strSeparator);
	   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
	   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
	   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 
	   return iDays ;
}
function checkNum() {
	var num = $("#num").val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(num))) {
		$.messager.alert("提示", "排名只能输入正整数！", "info", function(e) {
			$("#num").focus();
		});
		return false;
	}
	return true;
}