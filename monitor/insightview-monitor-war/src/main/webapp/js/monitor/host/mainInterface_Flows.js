$(document).ready(function() {
	getInterfaceChartJsonData();//获取接口数据
});

var dataArr = new Array();

/**
 * 获取接口数据
 * @return
 */
function getInterfaceChartJsonData(){
	var path = getRootPatch();
	var u = $("#proUrl").val();
	var uri = path + u ;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#interfaceId").val(),
			"perfKind" : $("#perfKind").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			// 得到json数据源
			var datas = eval('(' + data.jsonChartData + ')');
			dataArr=[];//清空数组
			dataArr.push(datas.text);//text标题放入数组
			
			var leg = datas.legend;
			var arr1=leg.split(",");
			dataArr.push(arr1);//legend内容放入数组
			
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
			
			showChartLine('interface_line');//显示拆线图
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
     	width : '80%',
     	height : '40%'
     }, 
    series : dataArr[3]     
    
    });
}
/**
 * 打开历史详情页面
 * @return
 */
function toInterfaceDeatil(){
	var perfKind = $("#perfKind").val();
	var moid = $("#interfaceId").val();
	var proUrl = $("#proUrl").val();
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid;
	window.open(url,"_blank");
}