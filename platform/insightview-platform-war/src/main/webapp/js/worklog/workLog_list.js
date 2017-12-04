var worklog = worklog || {};

worklog.list = {
		basePath: getRootName(),
		reload: function() {
			$("#tblWorkLogList").datagrid("reload");
		},
		search: function() {
			var that = this;
			var params = that.getParams();
			if(params.logStart1 && params.logStart2) {
				if(new Date(params.logStart1) > new Date(params.logStart2)) {
					$.messager.alert('提示', '日志开始日期的开始时间不能大于结束时间！', 'info');
					return;
				}
			}
			if(params.logEnd1 && params.logEnd2) {
				if(new Date(params.logEnd1) > new Date(params.logEnd2)) {
					$.messager.alert('提示', '日志结束日期的开始时间不能大于结束时间！', 'info');
					return;
				}
			}
			$("#tblWorkLogList").datagrid("load", that.getParams());
		},
		reset: function() {
			$("#log_title").val("");
			$("#log_start1").datetimebox("clear");
			$("#log_start2").datetimebox("clear");
			$("#log_starLevel").combobox('setValue', '-1');
			$("#log_end1").datetimebox("clear");
			$("#log_end2").datetimebox("clear");
		},
		getParams: function() {
			var params;
			var temp1 = $("#log_start1").datebox("getValue");
			var temp2 = $("#log_start2").datebox("getValue");
			var temp3 = $("#log_end1").datebox("getValue");
			var temp4 = $("#log_end2").datebox("getValue");
			try {
				params = {
						title: $("#log_title").val(),
						logStart1: temp1==''?'':temp1+':00',
						logStart2:	temp2==''?'':temp2+':00',
						starLevel: $("#log_starLevel").combobox('getValue'),
						logEnd1: temp3==''?'':temp3+':00',
						logEnd2: temp4==''?'':temp4+':00',
				};
			} catch(e) {}
			return params ? params : {};
		},
		toAdd: function() {
			this.openWin("新建工作日志", 750, 400, "/workLog/toAddWorkLog");
		},
		toEdit: function(id) {
			this.openWin("修改工作日志", 750, 400, "/workLog/toEditWorkLog?id="+id);
		},
		toShow: function(id) {
			this.openWin("查看工作日志", 750, 400, "/workLog/toShowWorkLog?id="+id);
		},
		doDel: function(id) {
			var that = this;
			$.messager.confirm("提示", "确定删除该工作日志吗？", function(r) {
				if(r) {
					$.ajax({
						url: that.basePath + "/workLog/deleteWorkLog",
						data: "id=" + id,
						success: function(data) {
							if(data == "success") {
								$('#tblWorkLogList').datagrid('uncheckAll');
								$('#tblWorkLogList').datagrid('unselectAll');
								that.reload();
							}
						},
						error: function() {
							$.messager.alert("错误", "ajax_error", "error");
						}
					});
				}
			});
		},
		doBatchDel: function() {
			var that = this, ids = null;
			var checkedItems = $("#tblWorkLogList").datagrid("getChecked");
			if(checkedItems.length == 0) {
				$.messager.alert("提示","没有选择任何删除项！","info");
				return;
			}
			$.each(checkedItems, function(index, item) {
				if(ids == null) {
					ids = item.id;
				} else {
					ids += "," + item.id;
				}
			});
			$.messager.confirm("提示", "确定删除所选中的工作日志吗？", function(r) {
				if(r) {
					$.ajax({
						url: that.basePath + "/workLog/deleteWorkLogs",
						data: "ids=" + ids,
						success: function(data) {
							if(data == "success") {
								$('#tblWorkLogList').datagrid('uncheckAll');
								$('#tblWorkLogList').datagrid('unselectAll');
								that.reload();
							}
						},
						error: function() {
							$.messager.alert("错误", "ajax_error", "error");
						}
					});
				}
			});
		},
		openWin: function(title, width, height, url) {
			var that = this;
			$('#workLog_popWin').window({
				title: title,
				width: width,
				height: height,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				resizable: false,
				draggable: false,
				href: that.basePath + url,
			}).window('center');
		},
		formatDate: function(val) {
			var date = new Date(val);
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			var hh = date.getHours();
			var mm = date.getMinutes();
			return y+"-"+(m<10?("0"+m):m)+"-"+(d<10?('0'+d):d)+' '+(hh<10?('0'+hh):hh)+':'+(mm<10?('0'+mm):mm);
		}
		
}

$(function() {

	doInitTable();
	function doInitTable() {
		var path = getRootName();
		$('#tblWorkLogList').datagrid({
			iconCls: 'icon-edit',// 图标
			fit: true,// 自动大小
			fitColumns: true,
			url: path + '/workLog/loadWorkLogList',
			queryParams: worklog.list.getParams(),
			idField: 'id',
			singleSelect: true,// 是否单选
			checkOnSelect: false,
			selectOnCheck: false,
			pagination: true,// 分页控件
			rownumbers: true,// 行号
			remoteSort: false,
			toolbar: [{
				text: "新增",
	        	iconCls: "icon-add",
	        	handler: function() {
	        		worklog.list.toAdd();
	        	}
			}, {
				text: "删除",
	        	iconCls: "icon-cancel",
	        	handler: function() {
	        		worklog.list.doBatchDel();
	        	}
			}, {
				text: "日历视图",
	        	iconCls: "icon-add",
	        	handler: function() {
	        		window.open(getRootName()+'/workLog/toCalendar');
	        	}
			}
			],
			scrollbarSize: 0,
			striped: true,
			sortName: 'startTime',
			sortOrder: 'desc',
			columns: [ [
			             {
			            	 title: 'ck',
			            	 checkbox: true,
			             },
			             {
			            	 title: '日志主题',
			            	 field: 'title',
			            	 align: 'center',
			            	 width: 160,
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
			            	 title: '日志开始时间',
			            	 field: 'startTime',
			            	 align: 'center',
			            	 width: 120,
			            	 sortable: true,
			            	 formatter: function(value,row,index) {
			            		 return worklog.list.formatDate(value);
			            	 }
			             },
			             {
			            	 title: '日志结束时间',
			            	 field: 'endTime',
			            	 align: 'center',
			            	 width: 120,
			            	 formatter: function(value,row,index) {
			            		 return worklog.list.formatDate(value);
			            	 }
			             },
			             {
			            	 title: '操作',
			            	 field: 'id',
			            	 align: 'center',
			            	 width: 140,
			            	 formatter : function(value,row,index){
			            		 return "<a style='cursor:pointer' title='查看' onclick=javascript:worklog.list.toShow(" + row.id + ");><img src='" + path + "/style/images/icon/icon_view.png' alt='查看'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
			                     "<a style='cursor:pointer' title='修改' onclick=javascript:worklog.list.toEdit(" + row.id + ");><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" + 
			                     "<a style='cursor:pointer' title='删除' onclick=javascript:worklog.list.doDel(" + row.id + ");><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
			            	 }
			             }] ]
		});
	    // 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
		$(window).resize(function() {
			$('#tblWorkLogList').resizeDataGrid(0, 0, 0, 0);
		});
	}
});