f.namespace('platform.paoject');

/**
 * 服务报告新增页API
 */
platform.paoject.serviceReportView = (function(){

	/**
	 * 给页面元素绑定事件
	 */
	f('#ServiceReportInfo_cancle').click(serviceReportInfoCancle);
	
	
	$(document).ready(function() {
		initDownLinkTag("downloadContractFileLook", $("#previousContractFileNameLook").val());
	});
	
	/**
	 * 新增取消按钮
	 */
	function serviceReportInfoCancle(){
		$('#popWin').window('close');
	}

	
})();



