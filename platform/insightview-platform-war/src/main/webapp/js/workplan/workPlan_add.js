var workplan = workplan || {};

workplan.add = {
		basePath: getRootName(),
		confirm: function() {
			var that = this;
			//根据id是否为空来判断是添加还是修改操作
			var path = ($('#workPlan_id').val()=='')?that.basePath+'/workPlan/addWorkPlan':that.basePath+'/workPlan/editWorkPlan';
			if(checkInfo('#workPlanInfo')) {
				//判断起始时间是否大于结束时间
				var planStart = $('#plan_start').datebox('getValue')+':00';
				var planEnd = $('#plan_end').datebox('getValue')+':00';
				if(new Date(planStart) > new Date(planEnd)) {
					$.messager.alert('提示', '计划日期的开始时间不能大于结束时间！', 'info');
					return;
				}
				var id = $('#workPlan_id').val();
				var title = $('input[name="title"]').val();
				var planType = $('input[name="planType"]').val();
				var relationPersons = $('#plan_relationPersons').val();
				var planContent = $('#plan_content').val();
				var file = $('input[name="file"]').val();
				$.ajax({
					url: path,
					data: {
						id: id,
						title: title,
						planType: planType,
						relationPersons: relationPersons,
						planStart: planStart,
						planEnd: planEnd,
						planContent: planContent,
						file: file
					},
					success: function(data) {
						if(data == 'success') {
							$('#workPlan_popWin').window('close');
							$('#tblWorkPlanList').datagrid('reload');
							$('#tblWorkPlanList').datagrid('uncheckAll');
							$('#tblWorkPlanList').datagrid('unselectAll');
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
	//初始化计划类型
	$('#plan_type1').combobox({
		url: getRootName() + '/dict/readItems?id=3085',
		valueField : 'key',
		textField : 'val',
		editable : false,
		panelWidth: 180,
		loadFilter: function(data) {
			var initObj = {"key": "", "val": "请选择"};
			var arr = [];
			arr.push(initObj);
			return arr.concat(data);
		}
	});
	//初始化计划日期
	$('#plan_start, #plan_end').datetimebox({
		editable: false,
		showSeconds: false
	});
	//文件上传操作
	$('#plan_file').f_fileupload({
		filePreview : true
	});
	
});