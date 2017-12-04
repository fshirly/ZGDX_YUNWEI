$(function() {
	var path = ($("#id").val()=="")?"/dutyOrder/addDutyOrder":"/dutyOrder/editDutyOrder";
	
	$("#dutyOrderInfo_save").click(function() {
		if(checkInfo("#dutyOrderInfo")) {
			var id = $("#id").val();
			var title = $("#title").val();
			var begin = $("#beginPoint").val();
			var end = $("#endPoint").val();
			var intervalDays = $('#intervalDays').val();

			if(isInterval(id) && intervalDays > 0) {
				$.messager.alert("提示", "已存在隔天的班次(最多只能存在一个隔天的班次)!", "info");
				return;
			}
			if(isTitleRepeat(title, id)) {
				$.messager.alert("提示", "班次名称不能重复！", "info");
				return;
			}
			//判断是否添加隔天的班次
			if(intervalDays > 0) {
				var hour = end.split(':')[0];
				var min = end.split(':')[1];
				end = (parseInt(hour)+24)+':'+min;
			}
			
			if(!isTimeRight(begin, end)) {
				$.messager.alert("提示", "结束时间不得早于或等于开始时间！", "info");
				return;
			}
			if(isTimeCrossed(begin, end, id)) {
				$.messager.alert("提示", "值班班次时间段不允许交叉或者包含！", "info");
				return;
			}
			//交班校验
			var exchangeStart = $("#exchangeStart").val();
			var exchangeEnd = $("#exchangeEnd").val();
			var forceTime = $("#forceTime").val();
			if(!isTimeRight(exchangeStart, exchangeEnd)) {
				$.messager.alert("提示", "交班结束时间不得早于或等于交班开始时间！", "info");
				return;
			}
			if(!isTimeRight(exchangeEnd, forceTime)) {
				$.messager.alert("提示", "强制交班时间不得早于或等于交班结束时间！", "info");
				return;
			}
			$.ajax({
				url: getRootName() + path,
				data: {
					id: $("#id").val(),
					title: $("#title").val(),
					beginPoint: $("#beginPoint").val(),
					endPoint: $("#endPoint").val(),
					intervalDays: $('#intervalDays').val(),
					exchangeStart: exchangeStart,
					exchangeEnd: exchangeEnd,
					forceTime: forceTime
				},
				success: function(data) {
					if(data == "success") {
						$("#popWin").window("close");
						window.frames['component_2'].$("#tblDutyOrder").datagrid("load");
					}
				},
				error: function () {
					$.messager.alert("错误", "ajax_error", "error");
				}
			});
		}
	});
	
	$("#beginPoint").timespinner({width:182});
	$("#endPoint").timespinner({width:182});
	$("#exchangeStart").timespinner({width:182});
	$("#exchangeEnd").timespinner({width:182});
	$("#forceTime").timespinner({width:182});
});

function isTimeRight(begin, end) {
	var flag = false;
	var begin_hour = begin.split(":")[0];
	var begin_minute = begin.split(":")[1];
	var end_hour = end.split(":")[0];
	var end_minute = end.split(":")[1];
	if((begin_hour < end_hour) || (begin_hour == end_hour && begin_minute < end_minute)) {
		flag = true;
	}
	return flag;
}

function getMinutes(time) {
	var hour = time.split(":")[0];
	var minute = time.split(":")[1];
	return parseInt(hour)*60 + parseInt(minute);
}

/**
 * 判断是否有隔天的班次
 */
function isInterval(id) {
	var rows = window.frames['component_2'].$("#tblDutyOrder").datagrid("getRows");
	//根据id判断是新增还是编辑
	for(var j=0; j<rows.length; j++) {
		if(rows[j].id == id) {
			continue;
		}
		if(rows[j].intervalDays > 0) {
			return true;
		}
	}
	return false;
}

/**
 * 判断时间是否与已有的时间段交叉或包含
 */
function isTimeCrossed(begin, end, id) {
	var rows = window.frames['component_2'].$("#tblDutyOrder").datagrid("getRows");
	var push_date = [];
	var cur_time_start = getMinutes(begin);
	var cur_time_end = getMinutes(end);
	
	//push_date容器添加元素（根据id判断是新增还是编辑）
	for(var i=0; i<rows.length; i++) {
		var row = rows[i];
		if(row.id == id) {
			continue;
		} else {
			var date = {};
			date.time_start = getMinutes(row.beginPoint);
			//判断是否隔天
			if(row.intervalDays > 0) {
				var end = row.endPoint;
				var hour = end.split(':')[0];
				var min = end.split(':')[1];
				end = (parseInt(hour)+24)+':'+min;
				date.time_end = getMinutes(end);
			} else {
				date.time_end = getMinutes(row.endPoint);
			}
			push_date.push(date);
		}
	}
	
	if(rows.length == 0 || push_date.length == 0) {
		return false;
	}
	
	//判断是否交叉
	for(var j=0; j<push_date.length; j++) {
		var temp_time_start = push_date[j].time_start;
		var temp_time_end = push_date[j].time_end;
		//判断是否交叉
		if((temp_time_start <= cur_time_start && temp_time_end > cur_time_start) || (temp_time_start < cur_time_end && temp_time_end >= cur_time_end)) {
			return true;
		}
	}
	
	//判断是否包含
	var time_start_arr = [];
	var time_end_arr = [];
	var between = cur_time_end - cur_time_start;
	$.each(push_date, function(index, item) {
		time_start_arr.push(item.time_start);
		time_end_arr.push(item.time_end);
	});
	var max_time_end = Math.max.apply(null, time_end_arr);
	if(cur_time_start >= max_time_end) {
		return false;
	}
	
	var temp_arr_1 = [];
	$.each(time_start_arr, function(index, item) {
		temp_arr_1.push(item-cur_time_start);
	});
	
	var temp_arr_2 = [];
	$.each(temp_arr_1, function(index, item) {
		if(item > 0) {
			temp_arr_2.push(item);
		}
	});
	var min_num = Math.min.apply(null, temp_arr_2);
	if(min_num >= between) {
		return false;
	} else {
		return true;
	}
	
}

/**
 * 检查班次名称是否重复
 */
function isTitleRepeat(title, id) {
	var rows = window.frames['component_2'].$("#tblDutyOrder").datagrid("getRows");
	//修改班次操作
	if(id != "") {
		for(var j=0; j<rows.length; j++) {
			if(rows[j].id == id && rows[j].title == title) {
				return false;
			}
		}
	}
	for(var i=0; i<rows.length; i++) {
		if(title == rows[i].title) {
			return true;
		}
	}
	return false;
}

