var tasklog = tasklog || {};

tasklog.add = {
		basePath: getRootName(),
		confirm: function() {
			var id = $('#tasklog_id').val();
			var title = $('input[name="title"]').val();
			var starLevel = $('input[name="starLevel"]').prop('checked')?2:1;
			var writeTime = $('#write_time').val()+':00';
			var logContent = $('#log_content').val();
			var file = $('input[name="file"]').val();
			$.ajax({
				url: this.basePath+'/platform/taskLog/editTaskLog',
				data: {
					id: id,
					title: title,
					starLevel: starLevel,
					writeTime: writeTime,
					logContent: logContent,
					file: file,
					status: 2
				},
				success: function(data) {
					if(data == 'success') {
						$('#tasklog_popWin').window('close');
						$('#tblTaskLogList').datagrid('reload');
						$('#tblTaskLogList').datagrid('uncheckAll');
						$('#tblTaskLogList').datagrid('unselectAll');
					}
				},
				error: function() {
					$.messager.alert("错误", "ajax_error", "error");
				}
			});
		},
};

$(function() {
	//上传文件操作
	$('#task_file').f_fileupload({
		filePreview : true
	});
});