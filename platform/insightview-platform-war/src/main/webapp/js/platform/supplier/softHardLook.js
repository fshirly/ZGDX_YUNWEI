var path = getRootName();
$(document).ready(function() {
	initRoomResDetail();
});

/*
 * 初始化信息
 */
function initRoomResDetail(){
	$("#lbl_proxyName").html(objSH.proxyName);
	if(objSH.productType == 2) {
		$("#lbl_productType").html("硬件");
	} else if(objSH.productType == 1) {
		$("#lbl_productType").html("软件");
	}
	$("#lbl_proxyFirmName").html(objSH.proxyFirmName);
	$("#lbl_proxyBeginTime").html(formatDateText2(new Date(objSH.proxyBeginTime)));
	$("#lbl_proxyEndTime").html(formatDateText2(new Date(objSH.proxyEndTime)));
	$("#lbl_description").html(objSH.description);
	$("#lbl_certificateUrl").html(initDownLinkTag("lbl_certificateUrl",objSH.certificateUrl));
}
