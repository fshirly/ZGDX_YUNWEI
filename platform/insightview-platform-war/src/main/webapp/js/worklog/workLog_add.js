var worklog = worklog || {};

worklog.add = {
		basePath: getRootName(),
		confirm: function() {
			var that = this;
			//根据id是否为空来判断是添加还是修改操作
			var path = ($('#workLog_id').val()=='')?that.basePath+'/workLog/addWorkLog':that.basePath+'/workLog/editWorkLog';
			if(checkInfo('#workLogInfo')) {
				//判断起始时间是否大于结束时间
				var logStart = $('#log_start').datebox('getValue')+':00';
				var logEnd = $('#log_end').datebox('getValue')+':00';
				if(new Date(logStart) > new Date(logEnd)) {
					$.messager.alert('提示', '任务日期的开始时间不能大于结束时间！', 'info');
					return;
				}
				var id = $('#workLog_id').val();
				var title = $('input[name="title"]').val();
				var starLevel = $('input[name="starLevel"]').prop('checked')?2:1;
				var logContent = $('#log_content').val();
				var createTime = $('#log_createTime').val();
				var taskFile = $('input[name="taskFile"]').val();
				var resultFile = $('input[name="resultFile"]').val();
				$.ajax({
					url: path,
					data: {
						id: id,
						title: title,
						starLevel: starLevel,
						startTime: logStart,
						endTime: logEnd,
						logContent: logContent,
						createTime: createTime,
						taskFile: taskFile,
						resultFile: resultFile
					},
					success: function(data) {
						if(data == 'success') {
							$('#workLog_popWin').window('close');
							$('#tblWorkLogList').datagrid('reload');
							$('#tblWorkLogList').datagrid('uncheckAll');
							$('#tblWorkLogList').datagrid('unselectAll');
						}
					},
					error: function() {
						$.messager.alert("错误", "ajax_error", "error");
					}
				});
			}
		}
}

$(function() {
	//初始化计划日期
	$('#log_start, #log_end').datetimebox({
		editable: false,
		showSeconds: false,
	});
	//文件上传操作
	$('#log_taskFile').f_fileupload({
		filePreview: true,
		upLoadBtnId: 'upload_a',
		downloadFile: 'download_a',
		inputFileId: 'aaa'
	});
	$('#log_resultFile').f_fileupload({
		filePreview : true,
		upLoadBtnId: 'upload_b',
		downloadFile: 'download_b',
		inputFileId: 'bbb'
	});
});