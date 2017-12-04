/**
 * 编辑通知公告
 */
$(document).ready(function() {
	$('#ipt_createTime').datetimebox({
		required : false,
		showSeconds : true,
		editable : false,
		disabled : true,
		formatter : formatDateText
	});
	if ($('#ipt_createTime').val() == '') {
        $('#ipt_createTime').datetimebox('setValue',formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
    }
	$('#ipt_deadLine').datetimebox({
		required : false,
		showSeconds : true,
		editable : false,
		formatter : formatDateText
	});
	$('#ipt_typeId').combobox({
		panelHeight : '120',
		url : getRootName() + '/dict/readItems?id=3066',
		valueField : 'key',
		textField : 'val',
		editable : false
	});
});

function doAnnouncementUpdate() {
	if (checkInfo('#tblAnnouncementInfo')) {
		var path = getRootName();
		var uri = path + "/announcement/updateItsmAnn";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : $("#ipt_id").val(),
				"title" : $("#ipt_title").val(),
				"typeId" : $("#ipt_typeId").combobox("getValue"),
				"creater" : $("#creater").val(),
				"createTime" : $("#ipt_createTime").datetimebox('getValue'),
				"deadLine" : $("#ipt_deadLine").datetimebox('getValue'),
				"summary" : $("#ipt_summary").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#popWin').window('close');
					if (window.frames['ifrAnnouList']) {
						window.frames['ifrAnnouList'].reloadTable();
					} else if (window.frames['component_2']) {
						window.frames['component_2'].reloadTable();
					}
				} else {
					$.messager.alert("提示", "信息修改失败！", "error");
				}
			}
		}
		ajax_(ajax_param);
	}
}