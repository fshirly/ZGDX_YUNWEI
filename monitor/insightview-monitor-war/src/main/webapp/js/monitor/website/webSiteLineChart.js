$(document).ready(function() {
	getChartJsonData(time);//获取数据
});

var dataArr = new Array();

/**
 * 获取图表数据
 * @return
 */
var time="24H";
function getChartJsonData(time){
	var path = getRootPatch();
	var u = $("#proUrl").val();
	var uri = path + u ;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moID" : $("#moID").val(),
			"time" : time,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			// 得到json数据源
			var datas = eval('(' + data.webSiteChartData + ')');
			dataArr=[];//清空数组
			dataArr.push(datas.text);//text标题放入数组
			
			var leg = datas.legend;
			if(leg.indexOf(",") >= 0){
				var arr1=leg.split(",");
				dataArr.push(arr1);//legend内容放入数组
			}else{
				var arr1=[];
				arr1.push(leg)
				dataArr.push(arr1);//legend内容放入数组
			}
			
			
			var xAxisData = datas.xAxisData;
			if(xAxisData != null ){
				var arr2=xAxisData.split(",");
				dataArr.push(arr2);//x轴数据放入数组
			}else{
				dataArr.push("");
			}			
			
			var str = datas.seriesData;
			var arr3=eval(str);
			dataArr.push(arr3);//y轴数据放入数据
			
			showChartLine('database_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}



function showChartLine(chartId){
	//显示拆线图
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
    	y:'bottom',
        data:dataArr[1]
    },
    calculable : true,
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
   	 	height : '30%'
     }, 
    series : dataArr[3]     
    
    });
}


function toResponseDeatil(){
	var moid = $("#moID").val();
	var siteType = $("#siteType").val();
	var proUrl = "";
	if(siteType == "1"){
		proUrl = "/monitor/webSiteWidget/getFtpResponseTime?moID="+moid;
	}
	if(siteType == "2"){
		proUrl = "/monitor/webSiteWidget/getDnsResponseTime?moID="+moid;
	}
	if(siteType == "3"){
		proUrl = "/monitor/webSiteWidget/getHttpResponseTime?moID="+moid;
	}
	var url = getRootPatch()+"/monitor/webSiteWidget/toHistoryChartsDetail?proUrl="+proUrl;
	var xpwidth=window.screen.width-10;
    var xpheight=window.screen.height-35;
	window.open(url,"_blank",'resizable=yes,directories=no,top=0,left=0,width='+xpwidth+',height='+xpheight);
}