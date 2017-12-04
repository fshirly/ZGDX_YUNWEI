$(document).ready(function() {
	initCommunityDetail();
});

/*
 * 初始化信息
 */
function initCommunityDetail() {
	var id = $('#id').val();
	var path = getRootName();
	var uri = path + "/monitor/roomCommunity/initCommunityDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "lbl");
			$("#lbl_ipAddress").text(data.ipAddress);
		}
	};
	ajax_(ajax_param);
}