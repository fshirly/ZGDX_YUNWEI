$(function() {
	$("#commitBtn").click(function() {
//		if ('' == $('#ipt_title1').val()) {
//			$("#errorText").html("请输入通知公告标题!");
//			return false;
//		} else if ('' == $("#ipt_creator1").val()) {
//			$("#errorText").html("请输入创建者!");
//			return false;
//		} else if ('' == $("#ipt_deadLine1").datetimebox('getValue')) {
//			$("#errorText").html("请输入有效期!");
//			return false;
//		} else if ('' == $('#description').val()) {
//			$("#errorText").html("请输入描述!");
//			return false;
//		}
   if(checkInfo('#announcementTab')){
		var path = getRootName();
		var uri = path + "/announcement/addItsmAnn";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"title" : $("#ipt_title1").val(),
				"creator" : $("#ipt_creator1").val(),
				"deadLine" : $("#ipt_deadLine1").datetimebox('getValue'),
				"summary" : $("#ipt_summary1").val()
			},
			error : function() {
				$("#errorText").html("创建公告失败!");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$("#errorText").html("已成功创建公告！");
					$('#ipt_title1').val('');
					$('#ipt_summary1').val('');
					$('#ipt_deadLine1').datetimebox('clear');
					reloadTableCommon('tblAnnouncementList');
					try{
						reloadTableCommon('tblAnnouncementList1');
					}catch(e){}
				} else {
					$("#errorText").html("创建公告失败!");
				}
			}
		};
		ajax_(ajax_param);
   }
	});
});