$(function () {  
            $('#serviceLevelTabs').tabs({  
                onSelect: function (title) {  
            		if (title == "内存") {  
            			getMemoryChartJsonData();//获取内存数据
            		}else if(title == "数据存储"){
            			getStorageChartJsonData();//获取数据存储数据
            		}else if(title == "磁盘"){
            			getHardChartJsonData();//获取硬盘数据
            		}else if(title == "接口"){
            			getInterfaceChartJsonData();//获取内存数据
            		}
                }  
            });  
}); 

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
	var uri = path + "/monitor/historyManage/hostCpuChartData";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#MOID").val(),
			"perfKind" : $("#cpu").val(),
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

/**
 * 获取硬盘数据
 * @return
 */
function getHardChartJsonData(){
	var path = getRootPatch();
	var uri = path + "/monitor/historyManage/hostHardDiskChartData";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#MOID").val(),
			"perfKind" : $("#hardDisk").val(),
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
			
			showChartLine('hardDisk_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}

/**
 * 获取接口数据
 * @return
 */
function getInterfaceChartJsonData(){
	var path = getRootPatch();
	var uri = path + "/monitor/historyManage/hostInterfaceChartData";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#MOID").val(),
			"perfKind" : $("#interface").val(),
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
			
			showChartLine('interface_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}

/**
 * 获取内存数据
 * @return
 */
function getMemoryChartJsonData(){
	var path = getRootPatch();
	var uri = path + "/monitor/historyManage/hostMemoryChartData";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#MOID").val(),
			"perfKind" : $("#memory").val(),
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
			
			showChartLine('memory_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}

/**
 * 获取数据存储数据
 * @return
 */
function getStorageChartJsonData(){
	var path = getRootPatch();
	var uri = path + "/monitor/historyManage/hostStoreChartData";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"MOID" : $("#MOID").val(),
			"perfKind" : $("#storage").val(),
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
			
			showChartLine('storage_line');//显示拆线图
		}
	}
	ajax_(ajax_param);
}

/**
 * 打开查询详情页面
 * @return
 */
function toCpuDeatil(){
	var perfKind = $("#cpu").val();
	var proUrl = "/monitor/historyManage/hostCpuChartData";
	var moid = $("#MOID").val();
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid;
	window.open(url,"_blank");
}
function toMemoryDeatil(){
	var perfKind = $("#memory").val();
	var moid = $("#MOID").val();
	var proUrl = "/monitor/historyManage/hostMemoryChartData";
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid;
	window.open(url,"_blank");
}
function toStorageDeatil(){
	var perfKind = $("#storage").val();
	var moid = $("#MOID").val();
	var proUrl = "/monitor/historyManage/hostStoreChartData";
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid;
	window.open(url,"_blank");
}
function toHardDiskDeatil(){
	var perfKind = $("#hardDisk").val();
	var moid = $("#MOID").val();
	var proUrl = "/monitor/historyManage/hostHardDiskChartData";
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid;
	window.open(url,"_blank");
}
function toInterfaceDeatil(){
	var perfKind = $("#interface").val();
	var moid = $("#MOID").val();
	var proUrl = "/monitor/historyManage/hostInterfaceChartData";
	var url = getRootPatch()+"/monitor/interfaceChart/toHistoryChartsDetail?proUrl="+proUrl+"&perfKind="+perfKind+"&MOID="+moid;
	window.open(url,"_blank");
}