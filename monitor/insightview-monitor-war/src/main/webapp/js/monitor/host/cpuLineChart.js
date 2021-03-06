$(document).ready(function() {
	getCpuChartJsonData();//获取Cpu数据
});

var dataArr = new Array();

/**
 * 获取Cpu数据
 * @return
 */
function getCpuChartJsonData(){
	var path = getRootPatch();
	var uri = path + "/monitor/historyManage/initCpuChartData";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#MOID").val(),
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
			var arr2=xAxisData.split(",");
			dataArr.push(arr2);//x轴数据放入数组
			
			var str = datas.seriesData;
			var arr3=eval(str);
			dataArr.push(arr3);//y轴数据放入数据
			
			showChartLine('cpu_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}
