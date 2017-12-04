$(document).ready(function() {
	getChartJsonData();//获取数据
});

var dataArr = new Array();

/**
 * 获取接口数据
 * @return
 */
function getChartJsonData(){
	var path = getRootPatch();
	var moID = $("#moId").val();
	var ajax_param = {
	    url : path + '/monitor/myManage/findMyChartAvailability?moID='+moID,
		type : "post",
		datdType : "json",
		data : {
			"seltDate" : $("#seltDate").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			// 得到json数据源
			var datas =data.jsonChartData;
			showChartLine(datas);
			
		}
	}
	ajax_(ajax_param);
}



function showChartLine(datas){
	//显示仪表盘
	 var myChart = echarts.init(document.getElementById('database_Chart'));
	  myChart.setOption({
	    tooltip : {
	        formatter: "{a} <br/>{b} : {c}%"
	    },
	    series : [
	        {
	            name:'性能指标',
	            type:'gauge',
	            detail : {formatter:'{value}%'},
	            data:[{value: datas, name: '可用率'}],
	            axisLine:{show: true, lineStyle: {color: [[0.2, '#2ec7c9'],[0.8, '#5ab1ef'],[1, '#d87a80']],width: 10}},
	            axisTick:{splitNumber: 10,length :15,lineStyle: {color: 'auto'}},
	            axisLabel:{textStyle: {color: 'auto'}},
	            splitLine:{length :22,lineStyle: {color: 'auto'}},
	            pointer:{width : 5,color : 'auto'}
	        }
	    ]
	});

}