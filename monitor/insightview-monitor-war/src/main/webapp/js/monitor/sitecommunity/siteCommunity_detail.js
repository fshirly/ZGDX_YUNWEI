$(document).ready(function() {
	initSiteCommunityInfo();
});


/**
 * 初始化站点凭证
 */
function initSiteCommunityInfo(){
	var id = $("#id").val();
	var path = getRootName();
	var uri = path + "/monitor/siteCommunity/initSiteCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" :id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data,"lbl");
			var siteType = data.siteType;
			if(siteType == 1){
				$("#tblFtpDetail").show();
				$("#tblHttpDetail").hide();
				$("#lbl_siteType1").text("FTP");
				$("#lbl_ftpIPAddress").text(data.ipAddress);
			}else if(siteType == 2){
				$("#tblFtpDetail").hide();
				$("#tblHttpDetail").show();
				$("#lbl_siteType2").text("HTTP");
				$("#lbl_httpIPAddress").text(data.ipAddress);
				var flag = data.requestMethod;
				if(flag ===1){
					$("#lbl_requestMethod").text("GET");
				}else if(flag ===2){
					$("#lbl_requestMethod").text("POST");
				}else if(flag ===3){
					$("#lbl_requestMethod").text("HEAD");
				}
			}
		}
	};
	ajax_(ajax_param);
}
