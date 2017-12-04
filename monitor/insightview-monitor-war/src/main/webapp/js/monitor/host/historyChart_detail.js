$(document).ready(function() {
	$("#timeTemplate option").eq(7).attr('selected', 'true');//默认取最近1天
	changeTime();
	selectViewPic(1);//图表切换显示		 
});

var dataArr = new Array();

/**
 * 获取json数据
 * @return
 */
function getChartJsonData(){
	
	var path = getRootPatch();
	var u = $("#proUrl").val();
	var uri = path + u ;
	$.ajax({
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"timeBegin" : $('#timeBegin').datetimebox('getValue'),
			"timeEnd" : $('#timeEnd').datetimebox('getValue'),
			"perfKind" : $("#perfKind").val(),
			"MOID" : $("#MOID").val(),
			"markPoint" : 1,//表示曲线使用最大值,最小值
			"t" : Math.random()
		},
		beforeSend: function () {
			$("#loading").show();
	    },
		success : function(data) {
			// 得到json数据源
	    	
			var datas = eval('(' + data.jsonChartData + ')');
			if(datas.xAxisData !=null)
			{
				if(datas.xAxisData.length==0){
					$("#loading").hide();
				}
			}
			dataArr=[];//清空数组
			dataArr.push(datas.text);//text标题放入数组
			
			var leg = datas.legend;
			var arr1=leg.split(",");
			dataArr.push(arr1);//legend内容放入数组
			
			var xAxisData = datas.xAxisData;
			if(xAxisData !=null)
			{
				var arr2=xAxisData.split(",");
				dataArr.push(arr2);//x轴数据放入数组
			}
			
			var str = datas.seriesData;
			var arr3=eval(str);
			dataArr.push(arr3);//y轴数据放入数据			
			
			showChartLine('type_line');//显示拆线图
			
			//列表数据展示
			
			doInitTable(arr1,arr2,str);
			
			if(str !=null)
			{
				//区域图数据转换
				var str2 = str.replace(/"line"/g,"'line',smooth:true,itemStyle:{normal:{areaStyle:{type:'default'}}}");
				var arr4=eval(str2);
				dataArr.push(arr4);//y轴数据放入数据	
			}
			
			showChartArea('type_area');//显示区域图
		},
		 complete: function () {
	        $("#loading").hide();
	    },
	    error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		}

	});
	
}


/**
 * 列表数据展示
 * @param columnLst
 * @param xDataLst
 * @param yDataLst
 * @return
 */
var cols = new Array();//colums
function doInitTable(columnLst,xDataLst,yDataLst) {
	var path = getRootName();
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 250,
						nowrap : false,
						striped : true,
						border : true,
//						fitColumns:true, // fitColumns（自适应列宽）,设置为true将自动使列适应表格宽度以防止出现水平滚动。
						collapsible : false,// 是否可折叠的
						idField : '',
						pageSize:10,
						remoteSort: false,
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : false,// 分页控件
						rownumbers : true,// 行号	
						sortName:'time',	
						sortOrder:'desc'	
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
	var cols = [];//初始化
	//添加固定column
	cols.push({field:'time',title:'时间点',width:150,align:'left',sortable:true});
	//动态添加column
	var columnLen = columnLst.length
	for(var i=0;i<columnLen;i++){
		cols.push({field:'colName'+i,title:columnLst[i],width:150,align:'center'});
	}
	$('#tblDataList').datagrid({
		columns:[cols]
	});
	if(xDataLst !=undefined)
	{
	
	//x轴
	var xlen = xDataLst.length;
	//y轴数据
	var arrYData = eval(yDataLst);
	//二维数组
	var sndArr = new Array();
	sndArr=[];
	if(arrYData!=undefined)
	{
		for(i=0;i<arrYData.length;i++){
			sndArr[i]=new Array();
			var serData = arrYData[i].data;
			var serDataArr=serData.toString().split(",");
			for(var j=0;j<xlen;j++){
				sndArr[i][j]=serDataArr[j];
			}	
		}
	}
	var xAxisSring = '{"total":'+xlen+',"rows":[';
	for(i=0;i<xlen;i++){
		//xAxisSring +='{"time":"'+xDataLst[i]+'","colName0":'+i+'},';
		xAxisSring +='{"time":"'+xDataLst[i]+'"';
		for(j=0;j<sndArr.length;j++){
				xAxisSring +=',"colName'+j+'":"'+sndArr[j][i]+'"';
		}
		xAxisSring +='},';
	}
	xAxisSring = xAxisSring.substring(0,xAxisSring.length-1);
	xAxisSring +=']}';
	var data = $.parseJSON(xAxisSring); 
	$('#tblDataList').datagrid('loadData', data); //将数据绑定到datagrid	
	
	}
}

//查询
function reloadShow(){
	var timeBegin=$('#timeBegin').datetimebox('getValue');
	var timeEnd=$('#timeEnd').datetimebox('getValue');
	var start = new Date(timeBegin.replace("-", "/").replace("-", "/"));
	var end = new Date(timeEnd.replace("-", "/").replace("-", "/"));
	if (end < start) {
		$.messager.alert('提示', '开始有效时间不得早于结束时间', 'error');
	}else
	{
		$('#tblDataList').datagrid({columns:[]});
		$('#tblDataList').datagrid('loadData', { total: 0, rows: [] });
		selectViewPic(1);
	}
}

/**
 * 选择显示方式
 * @return
 */
function selectViewPic(viewPic){
	if(viewPic==1){
		$("#area").hide();	
		$("#line").show();
	}else{
		$("#line").hide();		
		$("#area").show();
	}
	
	getChartJsonData();//获取json数据
}

function showChartLine(chartId){
	
	//显示拆线图
	var browser_width = $("#type_line").width(); //动态获取图形的宽度
	var Xwith = parseInt(browser_width)- 130;//下面图例放（legend）的位置是通过此处计算出来的。
    var myChart1 = echarts.init(document.getElementById(chartId));
    myChart1.setOption({
    	title : {
    	x:'center',
        text: dataArr[0],
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
    	orient: 'vertical', 
    	x:Xwith,
    	y:'center',
        data:dataArr[1]
    },
    calculable : true,
    dataZoom : {
        show : true,
        realtime : true,
        start : 20,
        end : 80
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : dataArr[2]
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value}'
            }
        }
    ],
    grid: {
    	x : '10%',
     	width : '78%',
     	height : '65%'
     },
    series : dataArr[3]     
    
    });
}

function showChartArea(chartId){
	//显示区域图
    var myChart1 = echarts.init(document.getElementById(chartId));
    myChart1.setOption({
    	title : {
    	x:'center',
        text: dataArr[0],
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
    	orient: 'vertical', 
    	x:'right',
    	y:'center',
        data:dataArr[1]
    },
    calculable : true,
    dataZoom : {
        show : true,
        realtime : true,
        start : 20,
        end : 80
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : dataArr[2]
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value}'
            }
        }
    ],
    grid: {
    	x : '10%',
     	width : '78%',
     	height : '65%'
     },
    series : dataArr[4]     
    
    });
}


function changeTime(){
	var time = $("#time").val();
	var newStart;
	var newEnd;
	if(time != null && time != "" && time != "null"){
		if(time == '24H'){
			$("#timeTemplate").val(7);
		}else if(time == 'Today'){
			$("#timeTemplate").val(9);
		}else if(time == 'Yes'){
			$("#timeTemplate").val(10);
		}else if(time == 'Week'){
			$("#timeTemplate").val(11);
		}else if(time == '7D'){
			$("#timeTemplate").val(12);
		}else if(time == 'Month'){
			$("#timeTemplate").val(13);
		}else if(time == 'LastMonth'){
			$("#timeTemplate").val(14);
		}
		 $("#time").val("");
	}
	var selVal = $("#timeTemplate").val();
	if(selVal==0){
		$("#timeBegin").val("");
		$("#timeEnd").val("");
	}else {
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
		$("#timeBegin").datebox("setValue",newStart);//开始时间		
		if(selVal == 1 || selVal == 2 || selVal == 3 || selVal == 4 || 
				selVal == 5 || selVal == 6 || selVal == 7 || selVal == 8 || 
				selVal == 9 || selVal == 11 || selVal == 12 || selVal == 13 ){
			var end = new Date();//结束时间
			newEnd = end.format('yyyy-MM-dd hh:mm:ss');
		}
		
		$("#timeEnd").datebox("setValue",newEnd);
	}
	$("#timeTemplate option").eq(selVal).attr('selected', 'true');
		
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

	    


