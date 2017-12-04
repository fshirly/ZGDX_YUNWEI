var path = getRootName();
$(document).ready(function() {
	initRoomResDetail();
});

/*
 * 初始化信息
 */
function initRoomResDetail(){
	$("#lbl_serviceName").html(objService.serviceName);
	$("#lbl_serviceFirmName").html(objService.serviceFirmName);
	$("#lbl_serviceBeginTime").html(formatDateText2(new Date(objService.serviceBeginTime)));
	$("#lbl_serviceEndTime").html(formatDateText2(new Date(objService.serviceEndTime)));
	$("#lbl_description").html(objService.description);
	$("#lbl_certificateUrl").html(initDownLinkTag("lbl_certificateUrl",objService.certificateUrl));
}
