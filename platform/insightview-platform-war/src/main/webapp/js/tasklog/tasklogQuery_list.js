var tasklog = tasklog || {};

tasklog.query = {
	basePath: getRootName(),
	reload: function() {
		$('#tblTaskLogQueryList').datagrid('reload');
	},
	reset: function() {
		$('#task_start1').datebox('clear');
		$('#task_start2').datebox('clear');
		$('#task_status').combobox('setValue', '-1');
	},
	search: function() {
		var params = this.getParams();
		if(params.taskTime1 && params.taskTime2) {
			if(new Date(params.taskTime1) > new Date(params.taskTime2)) {
				$.messager.alert('提示', '任务日期的开始时间不能大于结束时间！', 'info');
				return;
			}
		}
		$("#tblTaskLogQueryList").datagrid("load", params);
	},
	getParams: function() {
		var params = {};
		var task_start1 = $('#task_start1').datebox('getValue');
		var task_start2 = $('#task_start2').datebox('getValue');
		params.taskTime1 = task_start1;
		params.taskTime2 = task_start2;
		params.status = $('#task_status').combobox('getValue');
		return params;
	},
	toShow: function(id) {
		this.openWin('查看任务日志', 730, 330, '/platform/taskLog/toShowTaskLog?id='+id+'&flag=t');
	},
	openWin: function(title, width, height, url) {
		$('#tasklogQuery_popWin').window({
			title: title,
			width: width,
			height: height,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			resizable: false,
			draggable: false,
			href: this.basePath + url,
		}).window('center');
	},
	formatDate: function(val) {
		var date = new Date(val);
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y+"-"+(m<10?("0"+m):m)+"-"+(d<10?('0'+d):d);
	},
	formatDate1: function(val) {
		var date = new Date(val);
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var hh = date.getHours();
		var mm = date.getMinutes();
		return y+"-"+(m<10?("0"+m):m)+"-"+(d<10?('0'+d):d)+' '+(hh<10?('0'+hh):hh)+':'+(mm<10?('0'+mm):mm);
	}
};

$(function() {

	doInitTable();
	function doInitTable() {
		var path = getRootName();
		$('#tblTaskLogQueryList').datagrid({
			iconCls: 'icon-edit',// 图标
			fit: true,// 自动大小
			fitColumns: true,
			url: path + '/platform/taskLog/loadTaskLogQueryList',
			queryParams: tasklog.query.getParams(),
			idField: 'id',
			singleSelect: true,// 是否单选
			checkOnSelect: false,
			selectOnCheck: false,
			pagination: true,// 分页控件
			rownumbers: true,// 行号
			remoteSort: false,
			scrollbarSize: 0,
			striped: true,
			sortName: 'writeTime',
			sortOrder: 'desc',
			onLoadSuccess: function(data) {
			},
			columns: [ [
						 {
							 title: '任务日期',
							 field: 'taskTime',
							 align: 'center',
							 width: 120,
							 formatter: function(value,row,index) {
								 return tasklog.query.formatDate(value);
							 }
						 },
			             {
			            	 title: '日志主题',
			            	 field: 'title',
			            	 align: 'center',
			            	 width: 160,
			             },
			             {
			            	 title: '状态',
			            	 field: 'status',
			            	 align: 'center',
			            	 width: 160,
			            	 formatter: function(value,row,index) {
			            		 if(value == 1) {
			            			 return '<span style="color: red;">未填写</span>';
			            		 }
			            		 return '已填写';
			            	 }
			             },
			             {
			            	 title: '是否星标',
			            	 field: 'starLevel',
			            	 align: 'center',
			            	 width: 100,
			            	 formatter: function(value,row,index) {
			            		 if(value == 2) {
			            			 return '<img src="'+path+'/style/images/u85.png">';
			            		 }
			            	 }
			             },
			             {
			            	 title: '受理人',
			            	 field: 'userName',
			            	 align: 'center',
			            	 width: 100,
			             },
			             {
			            	 title: '填写时间',
			            	 field: 'writeTime',
			            	 align: 'center',
			            	 width: 100,
			            	 sortable: true,
			            	 formatter: function(value,row,index) {
			            		 if(value == null || value == '') {
			            			 return '';
			            		 }
								 return tasklog.query.formatDate1(value);
							 }
			             },
			             {
			            	 title: '操作',
			            	 field: 'id',
			            	 align: 'center',
			            	 width: 140,
			            	 formatter : function(value,row,index){
			            		 return '<a style="cursor: pointer" onclick="javascript:tasklog.query.toShow(' + row.id + ');">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;'
			            	 }
			             }] ]
		});
	    // 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
		$(window).resize(function() {
			$('#tblTaskLogList').resizeDataGrid(0, 0, 0, 0);
		});
	}
});