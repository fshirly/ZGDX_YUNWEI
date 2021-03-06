$(document).ready(function() {
	changeTime();
});
/**
 * 提交
 */
function doEditMiddleDB() {
	if (checkInfo("#tblEdit")) {
			var widgetTitle = $("#widgetTitle").val();
			var widgetName = $("#widgetName").val();
			var widgetId = $("#widgetId").val();
			var moid = $("#ipt_deviceId").val();
			var timeBegin = $("#timeBegin").val();
			var timeEnd = $("#timeEnd").val();
			var widgetId = $("#widgetId").val();
			var url = $("#url").val();
			url = url.replace(/(moID=)[^;]+/, '$1' + moid);
			var index = window.$('#tabsIndex').val();
//			alert(index);
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

/**
 * 加载设备信息列表页面
 * @return
 */
function loadDeviceInfo(){
	var path=getRootName();
	var moClassId = $("#ipt_moClassId").attr("alt");
	if(moClassId==null ||moClassId==""){
		$.messager.alert("提示", "请先选择设备类型！", "info");
	}else if(moClassId==15 ||moClassId==16 ||moClassId==54){
		var uri=path+"/monitor/perfTask/toDBMSServerList?flag=choose&moClassId="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(moClassId==19 ||moClassId==20 ||moClassId==53){
		var uri=path+"/monitor/perfTask/toMiddleWareList?flag=choose&jmxType="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else if(moClassId==44){
		var uri=path+"/monitor/perfTask/toZoneManageList?flag=choose&moClassId="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}else{
		var uri=path+"/monitor/discover/toDiscoverDeviceList?flag=choose&moClassId="+moClassId;
		window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	}
	
}

/*
 * 选择对象类型
 */
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/addDevice/initTree";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"trmnlBrandNm" : "",
				"qyType" : "brandName",
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择设备类型", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].classId;
					var _nameTemp = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_moClassId','"
							+ _id + "','"+ className +  "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#popView2').dialog({
						       title:"选择对象类型",
							   width: 400,
							   height: 450,
							   top :150
				});
				
			}
		}
		ajax_(ajax_param);
}
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId, className ,showVal) {
	//判断是否为叶子节点
	var path = getRootName();
	var uri=path+"/monitor/addDevice/isLeaf";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"classID" : showId,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data){
				if (true == data || "true" == data) {
					$("#" + controlId).val(showVal);
					$("#" + controlId).attr("alt", showId);
					$("#popView2").dialog('close');
					$("#ipt_deviceIp").val("");
				}
			}
		};
		ajax_(ajax_param);	
}
/**
 * 获得中间件的信息
 * @return
 */
function findMiddleWareInfo(){
	var moID = $("#ipt_middleWareId").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findMiddleWareInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moId" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_deviceIp").val(data.ip);//+":"+data.port 凭证的端口
			$("#ipt_deviceId").val(data.moId);
		}
	};
	ajax_(ajax_param);
}
/**
 * 获得数据库服务的信息
 * @return
 */
function findDBMSServerInfo(){
	var moID = $("#ipt_dbmsServerId").val();
	var path = getRootName();
	var uri=path+"/monitor/perfTask/findDBMSServerInfo";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"moid" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_deviceIp").val(data.ip);//+":"+data.port 凭证的端口
			$("#ipt_deviceId").val(data.moid);
		}
	};
	ajax_(ajax_param);
}

function changeTime() {
	var newStart;
	var newEnd;
	var selVal = $("#timeTemplate").val();
	/*
	 * if(selVal==0){ $("#timeBegin").val(""); $("#timeEnd").val(""); }else {
	 */
	if (selVal == 1) {
		// 最近半小时
		var nd = addNDays(0.5 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 2) {
		// 最近1小时
		var nd = addNDays(1 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 3) {
		// 最近2小时
		var nd = addNDays(2 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 4) {
		// 最近4小时
		var nd = addNDays(4 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 5) {
		// 最近6小时
		var nd = addNDays(6 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 6) {
		// 最近12小时
		var nd = addNDays(12 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 7) {
		// 最近24小时
		var nd = addNDays(24 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 8) {
		// 今天
		var date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 9) {
		// 昨天
		var date = new Date();
		date.setDate(date.getDate() - 1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
		var end = new Date();// 结束时间
		end.setDate(end.getDate() - 1);
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(59);
		newEnd = new Date(end.getTime()).format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 10) {
		// 本周
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
	} else if (selVal == 11) {
		// 7天
		var nd = addNDays(7 * 24 * 60 * 60 * 1000);
		newStart = nd.format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 12) {
		// 本月
		var date = new Date();
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
	} else if (selVal == 13) {
		// 上月
		var date = new Date();
		date.setMonth(date.getMonth() - 1);
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		newStart = new Date(date.getTime()).format('yyyy-MM-dd hh:mm:ss');
		var end = new Date();
		end.setMonth(end.getMonth() - 1);
		var days = getDaysOfMonth(end);
		end.setDate(days);
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(59);
		newEnd = new Date(end.getTime()).format('yyyy-MM-dd hh:mm:ss');
	}
	$("#timeBegin").val(newStart);// 开始时间
	if (selVal == 1 || selVal == 2 || selVal == 3 || selVal == 4 || selVal == 5
			|| selVal == 6 || selVal == 7 || selVal == 8 || selVal == 10
			|| selVal == 11 || selVal == 12) {
		var end = new Date();// 结束时间
		newEnd = end.format('yyyy-MM-dd hh:mm:ss');
	}

	$("#timeEnd").val(newEnd);
	// }
	$("#timeTemplate option").eq(selVal).attr('selected', 'true');

}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

var addNDays = function(n) {
	var date = new Date();
	var time = date.getTime();
	var newTime = time - n;
	return new Date(newTime);
};

// 获取一个月多少天
function getDaysOfMonth(date) {
	var daysInMonth = new Array([ 0 ], [ 31 ], [ 28 ], [ 31 ], [ 30 ], [ 31 ],
			[ 30 ], [ 31 ], [ 31 ], [ 30 ], [ 31 ], [ 30 ], [ 31 ]);
	var strYear = date.getFullYear();
	var strDay = date.getDate();
	var strMonth = date.getMonth() + 1;
	var strTime = date.getTime();
	if (strYear % 4 == 0 && strYear % 100 != 0) {
		daysInMonth[2] = 29;
	}
	var days = daysInMonth[strDay];
	return days;
}

function getLastMonthYestdy(date, n) {
	var daysInMonth = new Array([ 0 ], [ 31 ], [ 28 ], [ 31 ], [ 30 ], [ 31 ],
			[ 30 ], [ 31 ], [ 31 ], [ 30 ], [ 31 ], [ 30 ], [ 31 ]);
	var strYear = date.getFullYear();
	var strDay = date.getDate();
	var strMonth = date.getMonth() + 1;
	var strTime = date.getTime();
	if (strYear % 4 == 0 && strYear % 100 != 0) {
		daysInMonth[2] = 29;
	}
	if (n == 1) {
		if (strMonth - 1 == 0) {
			strYear -= 1;
			strMonth = 12;
		} else {
			strMonth -= 1;
		}
	} else if (n == 3) {
		if (strMonth - 3 == 0) {
			strYear -= 1;
			strMonth = 12;
		} else {
			strMonth -= 3;
		}
	} else if (n == 12) {
		strYear -= 1;
	}

	strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];
	if (strMonth < 10) {
		strMonth = "0" + strMonth;
	}
	if (strDay < 10) {
		strDay = "0" + strDay;
	}
	datastr = strYear + "-" + strMonth + "-" + strDay;
	return datastr;
}

function getDays(strDateStart, strDateEnd) {
	var strSeparator = "-"; // 日期分隔符
	var oDate1;
	var oDate2;
	var iDays;
	oDate1 = strDateStart.split(strSeparator);
	oDate2 = strDateEnd.split(strSeparator);
	var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
	var strDateE = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
	iDays = parseInt(Math.abs(strDateS - strDateE) / 1000 / 60 / 60 / 24)// 把相差的毫秒数转换为天数
	return iDays;
}
