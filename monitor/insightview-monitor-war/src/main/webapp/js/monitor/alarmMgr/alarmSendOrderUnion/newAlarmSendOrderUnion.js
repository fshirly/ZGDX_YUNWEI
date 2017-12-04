/**
 * 
 */
// 是否发送短信默认设置
$(document).ready(function() {
	$.ajax({
		url : getRootName() + "/materialApply/isSendMessage",
		success : function(data) {
			if (data == "false") {
				$("#sendMsg").attr("checked", false);
			} else {
				$("#sendMsg").attr("checked", true);
			}
		},
		error : function() {
			$("#sendMsg").attr("checked", true);
		}
	});
});

$(function() {
	var workOrderList = $('#workOrderSingleTableId'), f1 = $("#formWorkOrder"), f2 = $("#formProcessNext");
	initWorkOrderForm();
	initWorkOrderProcessNext();
	$('#titleId1').val("");
	$('#comment').val("");
	resetForm("formProcessNext");
});

function initWorkOrderForm() {
	$("#workOrderSingleTableId")
			.css("height", "200px")
			.datagrid(
					{
						title : "告警派单列表",
						fitColumns : true,
						scrollbarSize : 0,
						striped : true,
						rownumbers : true,
						wedith : 'auto',
						url : getRootName() + '/monitor/alarmSendOrderUnion/alarmSendOrderUnionConfig?alarmIds=' + $('#alarmIds').val(),
						columns : [ [
								{
									field : "alarmTitle",
									title : "告警标题",
									width : 100,
									align : "center"
								},
								{
									field : "alarmID",
									title : "告警序号",
									width : 100,
									align : "center",
									formatter : function(value, row) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toView('
												+ row.alarmID
												+ ');">'
												+ row.alarmID + '</a>';
									}
								}, {
									field : "sourceMOName",
									title : "告警源名称",
									width : 100,
									align : "center"
								}, {
									field : "sourceMOIPAddress",
									title : "告警源IP",
									width : 100,
									align : "center"
								}, {
									field : "alarmLevelName",
									title : "告警等级",
									width : 100,
									align : "center"
								}, {
									field : "statusName",
									title : "告警状态",
									width : 100,
									align : "center"
								} ] ]
					}).end();
	$(window).resize(function() {
		$("#workOrderSingleTableId").resizeDataGrid(0, 0, 0, 0);
	});
	$("#workOrderForm a:first").click(function() {
		var rows = $("#workOrderSingleTableId").datagrid("getRows");
		if (0 == rows.length) {
			$.messager.alert("警告", "请选择要派发的告警");
			return;
		}
		if (checkInfo('#tableWorkOrder')) {
//			$("#workOrderForm").css("display", "none");
			$("#workOrderProcessNext").window('open');
		}
	});

}

util = {};
util.serializeForm = function(form, enablenull) {
	var o = {};
	$ && $.each($(form).serializeArray(), function(index) {
		if (!this['value']) {
			return;
		}
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

function closeAllWindow(){
	  $('#popWin').window('close');
      $("#workOrderProcessNext").window('close');
}


function initWorkOrderProcessNext() {
	var root = getRootName();
	var submiting = false;
	// 确定
	$("#workOrderProcessNext a:eq(1)").click(function(){
	    if (submiting) {
	        return;
	    }
	    submiting = true;
	    // 两个表单数据
	    var param = $.extend({}, util.serializeForm("#formWorkOrder"), util.serializeForm("#formProcessNext"));
	    if ("" == param.selectPeopleId || null == param.selectPeopleName) {
	        $.messager.alert("警告", "处理人必须选择");
	        return;
	    }
	    var rows = $("#workOrderSingleTableId").datagrid("getRows");
	    var rs = [];
	    var ids = $.map(rows, function(v){
	        var r = {};
	        r.alarmID = v.alarmID;
	        r.sourceMOName = v.sourceMOName;
	        r.sourceMOIpAdress = v.sourceMOIPAddress;
	        r.alarmLevel = v.alarmLevel;
	        r.alarmLevelName = v.alarmLevelName;
	        r.alarmTitle = v.alarmTitle;
	        r.moClassID = v.moclassID
	        r.moClassName = v.moClassName;
	        r.alarmDescription = v.alarmContent;
	        r.sourceMoClassID = v.sourceMOClassID;
	        r.startTime = v.startTime;
	        r.alarmStatus = v.alarmStatus;
	        r.statusName = v.statusName;
	        r.repeatCount = v.repeatCount;
	        // 新增的
	        r.alarmOperateStatus = v.alarmOperateStatus;
	        r.operateStatusName = v.operateStatusName;
	        rs.push(r);
	        
	        return v.alarmID;
	    });
	    param.content = JSON.stringify(rs);
	    param.ids = ids.join();
	    $.ajax({
	        url: getRootName() + "/monitor/AlarmSendSingle/ajaxSendSingleAlarm?isSend="+$("#sendMsg").prop("checked")+"&remark="+$("#comment").val(),
	        type: "post",
	        data: param,
	        dataType: "json",
	        success: function(d){
	        	var msg = '派发成功.';
				if (!d) {
					msg = '派发失败，请稍后再试。';
	        	} 
				$.messager.alert("提示", msg, "info");
				closeAllWindow();
				if(typeof reloadTable === 'function'){
					reloadTable();	
				}else{
					var iframes = parent.frames['component_2'].frames['ifrhomepage'];
					var frameId;
					for (var i = 0; i < iframes.contentWindow.frames.length; i++) {
						frameId = iframes.contentWindow.frames[i].frameElement.id;
						if (frameId.indexOf("AlarmActive") === 0) {
							iframes.contentWindow.refreshIfrView("" + frameId + "");
							return;
						}
					}
				}
	            submiting = false;
	        },
	        error: function(){
	            $.messager.alert("提示", "派发失败，请稍后再试。");
	            submiting = false;
	        }
	    });
	});

	// 选人组件
	$.post(getRootName() + "/eventManage/findStaffInfo2",
	 		{groupId:404},
	 		function(data){
	 			$("#departmentMan4WorkOrder li").remove();
	 			$.each(data,function(k,v){				        				
	 				var listaff=$("<li></li>");
	 				var checkboxName=$("<input type='radio'  name='selectPerson'>");
	 				$.each(v,function(id,value){
	 					if(id!='selected'){
	        					if(id=='val'){
	        						listaff.html(value);			        						
	        					}
	        					checkboxName.attr(id,value);
	 					}
	 					else {
	 						checkboxName.attr('checked','checked');
	 					}
	 				});	
	 				listaff.prepend(checkboxName);
	 				checkboxName.bind("click",userSelect);
	 				listaff.appendTo("#departmentMan4WorkOrder");
	 			});
	 			userSelect();
	 	});

	// 上一步
	$("#workOrderProcessNext a:eq(0)").click(function(){
	    $("#workOrderProcessNext").window('close');
	    $("#summary").val("");
	});
}

// 用户选择事件
function userSelect() {
	var selectMan = "";
	var selectId = "";
	var checked = $('#departmentMan4WorkOrder :radio:checked');
	$.each(checked, function() {
		selectMan += $(this).attr('val') + ",";
		selectId += $(this).attr('key') + ",";
	});
	$("#selectPeopleId4WorkOrder").val(selectId.substr(0, selectId.length - 1));
	$("#selectPeopleName4WorkOrder").val(
			selectMan.substr(0, selectMan.length - 1));
}

/**
 * 打开告警详情页面
 * 
 * @return
 */
function toView(id) {
	parent.parent.$('#popWin').window(
			{
				title : '告警详情',
				width : 800,
				height : 550,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/alarmActive/toAlarmActiveDetail?alarmID='
						+ id
			});
}