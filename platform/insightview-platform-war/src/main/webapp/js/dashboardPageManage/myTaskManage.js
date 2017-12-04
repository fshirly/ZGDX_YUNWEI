$(document).ready(
function() {
	$.extend($.fn.datagrid.methods, {
		getChecked : function(jq) {
			var rr = [];
			var rows = jq.datagrid('getRows');
			jq.datagrid('getPanel').find(
					'div.datagrid-cell-check input:checked').each(
					function() {
						var index = $(this).parents('tr:first').attr(
								'datagrid-row-index');
						rr.push(rows[index]);
					});
			return rr;
		}
	});
	        		
	$('#type').combobox({
		panelHeight : '120',
		valueField : 'key',
		textField : 'val',
		data: [{
			key: '1',
			val: '会议'
		},{
			key: '2',
			val: '巡检'
		},{
			key: '3',
			val: '备份'
		},{
			key: '4',
			val: '其他'
		}],
		editable : false
	});
	
	$('#directorName').createSelect2({
   		uri : '/permissionSysUser/querySysUserInfo',//获取数据
   		name : 'userName',//显示名称
   		id : 'userID', //对应值值
   		initVal :{directorName:$("#directorName").attr("value")}
	});
	
	$('#validTimeEnd').datetimebox({
		required : false,
		showSeconds : true,
		formatter : formatDateText,
		editable : false
	});
	
	$("#resetBtn").click(function(){
		$("#errorText").html("");
		$('#title').val('');
        $('#type').combobox('setValue',''),
        $('#directorName').select2("val","-1");
        $("#validTimeEnd").datetimebox('setValue',"");
        $('#director').val('');
        $('#content').val('');
	});
	
	$("#commitBtn").click(function(){
		if (!checkInfo('#taskForm')){
           return false;
        }
		
		var path = getRootName();
		var uri = path + "/taskManage/saveTask2";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"title" : $("#title").val(),
				"type" : $("#type").combobox('getValue'),
				"validTimeEnd" : $("#validTimeEnd").datetimebox('getValue'),
				"director" : $("#directorName").select2("val"),
				"content" : $("#content").val()
			},
			error : function() {
				$("#errorText").html("提交失败!");
			},
			success : function(data) {
				if ("OK" == data) {
					$("#errorText").html("提交成功!");
					$('#title').val('');
                    $('#type').combobox('setValue',''),
                    $('#directorName').select2("val","-1");
                    $("#validTimeEnd").datetimebox('setValue',"");
                    $('#director').val('');
                    $('#content').val('');
                    reloadTableCommon('tblMyTaskList');
                    
				} else {
					$("#errorText").html("提交失败!");
				}
			}
		};
		ajax_(ajax_param); 
	});
	
	$('#type').combobox('setValue','1');
	
	// 页面初始化加载表格
	doInitTable();

});

function reloadTable(){
	reloadTableCommon('tblMyTaskList');
	reloadTableCommon('tblMyTaskList2');
}

function doUpdate(id) {
	 parent.$('#popWin').window({
	        title:'编辑任务',
	        width:800,
	        height:480,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/taskManage/replyhistory?id=' + id
	    });
}


function doInitTable() {
	var path = getRootName();
	
	$('#tblMyTaskList').datagrid({
			iconCls : 'icon-edit',// 图标
			width : 'auto',
			height : '570',
			fitColumns:true,
			nowrap : false,
			striped : true,
			border : true,
			collapsible : false,// 是否可折叠的
			fitColumns:true,
			url : '../dashboardPageManage/queryAllMyTaskList?status=0',
			remoteSort : false,
			idField : 'fldId',
			singleSelect : true,// 是否单选
			pageSize:5,
			rownumbers : true,
			pagination : true,// 分页控件
			columns : [ [
					{
						field : 'title',
						title : '任务标题',
						align : 'center',
						width : 120
					},
					{
						field : 'createTime',
						title : '创建时间',
						align : 'center',
						width : 90,
						formatter : function(value, row, index) {
                            return formatDate(
                                    new Date(row.createTime),
                                    "yyyy-MM-dd hh:mm:ss");
                        }
					},
					{
						field : 'createrName',
						title : '发布人',
						align : 'center',
						width : 90
					},
					{
						field : 'status',
						title : '状态',
						align : 'center',
						width : 120,
						formatter : function(value, row, index) {
							if (row.status == 1) {
								return "待发布";
							} else if (row.status == 2) {
								return "执行中";
							} else {
								return "已结束";
							}
						}
					},
					{
						field : 'id',
						title : '操作',
						width : 60,
						align : 'center',
						formatter : function(value, row, index) {
							return '<a  style=\'cursor:pointer\' onclick="javascript:doUpdate('
									+ row.id + ');"><img src="' + path + '/style/images/icon/icon_modify.png" alt="修改" /></a>';
						}
					} ] ]
		});
	
		$('#tblMyTaskList2').datagrid({
			iconCls : 'icon-edit',// 图标
			width : 'auto',
			height : 'auto',
			fitColumns:true,
			nowrap : false,
			striped : true,
			border : true,
			collapsible : false,// 是否可折叠的
			fitColumns:true,
			url : '../dashboardPageManage/queryMyTaskList?status=1',
			remoteSort : false,
			idField : 'fldId',
			singleSelect : true,// 是否单选
			pageSize:5,
			rownumbers : true,
			columns : [ [
					{
						field : 'title',
						title : '任务标题',
						align : 'center',
						width : 120
					},
					{
						field : 'createTime',
						title : '创建时间',
						align : 'center',
						width : 90,
						formatter : function(value, row, index) {
	                        return formatDate(
	                                new Date(row.createTime),
	                                "yyyy-MM-dd hh:mm:ss");
	                    }
					},
					{
						field : 'createrName',
						title : '发布人',
						align : 'center',
						width : 90
					},
					{
						field : 'status',
						title : '状态',
						align : 'center',
						width : 120,
						formatter : function(value, row, index) {
							if (row.status == 1) {
								return "待发布";
							} else if (row.status == 2) {
								return "执行中";
							} else {
								return "已结束";
							}
						}
					},
					{
						field : 'id',
						title : '操作',
						width : 60,
						align : 'center',
						formatter : function(value, row, index) {
							return '<a  style=\'cursor:pointer\' onclick="javascript:doUpdate('
									+ row.id + ');"><img src="' + path + '/style/images/icon/icon_view.png" alt="查看" /></a>';
						}
					} ] ]
		});
}