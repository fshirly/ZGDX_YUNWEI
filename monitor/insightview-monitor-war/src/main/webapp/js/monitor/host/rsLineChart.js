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
	$("#timeVal").val(time);
	var path = getRootPatch();
	var u = $("#proUrl").val();
	var uri = path + u ;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moID" : $("#moid").val(),
			"moClass" : $("#moClass").val(),
			"seltItem" : $("#seltItem").val(),
			"time" :time,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			// 得到json数据源
			var datas = eval('(' + data.oracleChartData + ')');
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
			
			showChartLine('rs_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}



/**
 * 打开历史详情页面
 * @return
 */
function toInterfaceDeatil(){
	var timeVal=$("#timeVal").val();
	var perfKind = $("#perfKind").val();
	var moid = $("#moid").val();
	var proUrl = $("#preUrl").val();
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid+"&time="+timeVal;
	window.open(url,"_blank");
}
