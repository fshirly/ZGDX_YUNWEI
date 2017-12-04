f.namespace('platform.paoject');

/**
 * 服务报告新增页API
 */
platform.paoject.serviceReportAdd = (function(){

	/**
	 * 给页面元素绑定事件
	 */
	f('#ServiceReportInfo_submit').click(serviceReportInfoSubmit);
	f('#ServiceReportInfo_cancle').click(serviceReportInfoCancle);
	
	var path = getRootName();
	
	$(document).ready(function() {

		$('#ipt_reportTime').datetimebox({
//			formatter : formatDateText,
			editable : false,
			onChange : function(){
				$('#ipt_reportTime').next(".combo").find('input').trigger('change');
			}
		});
		
		
		//报告人
		$('#ipt_reporter').createSelect2({
			uri : '/permissionSysUser/querySysUserInfo',
		    name : 'userName',
		    id : 'userID',
		    initVal :{ipt_reporter:$("#ipt_reporter").attr("value")}
	    });
		
		//附件
		$("#ipt_reportFile").f_fileupload(
				{
					whetherPreview : false,
//					fileFormat : "['doc', 'docx']",
					filesize : 5*1024,
					imgWidth : "default",
					imgHeight :"default",
					filePreview : true
				});
		
	});
	
	/**
	 * 新增确定按钮
	 */
	function serviceReportInfoSubmit(){
		if (checkInfo('#divServiceReportInfo')) {
			var ajax_param = {
					url : path + '/serviceReport/submitServiceReportInfo',
					type : "post",
					datdType : "json",
					data : {
						"serviceReportID":$('#ipt_serviceReportID').val(),
						"name":$('#ipt_name').val(),
						"reportTime":$('#ipt_reportTime').datetimebox('getValue'),
						"reporter":$("#ipt_reporter").select2("val"),
						"summary" : $('#ipt_Summary').val(),
						"reportFile":$('#iconFileName').val()==null?$('#ipt_reportFile').val():$('#iconFileName').val(),
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "服务报告新增失败！", "error");
					},
					success : function(data) {
						$('#popWin').window('close');
						window.frames['component_2'].platform.paoject.serviceReport.reloadTable();
					}
			};
			ajax_(ajax_param);
		}
		
	}

	
	/**
	 * 新增取消按钮
	 */
	function serviceReportInfoCancle(){
		$('#popWin').window('close');
	}

})();



