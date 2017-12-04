var workplan = workplan || {};

workplan.list = {
		basePath: getRootName(),
		reload: function() {
			$("#tblWorkPlanList").datagrid("reload");
		},
		search: function() {
			var that = this;
			var params = that.getParams();
			if(params.planStart1 && params.planStart2) {
				if(new Date(params.planStart1) > new Date(params.planStart2)) {
					$.messager.alert('提示', '计划开始日期的开始时间不能大于结束时间！', 'info');
					return;
				}
			}
			if(params.planEnd1 && params.planEnd2) {
				if(new Date(params.planEnd1) > new Date(params.planEnd2)) {
					$.messager.alert('提示', '计划结束日期的开始时间不能大于结束时间！', 'info');
					return;
				}
			}
			$("#tblWorkPlanList").datagrid("load", that.getParams());
		},
		reset: function() {
			$("#plan_title").val("");
			$("#plan_start1").datebox("clear");
			$("#plan_start2").datebox("clear");
			$("#plan_type").combobox('setValue', '-1');
			$("#plan_end1").datebox("clear");
			$("#plan_end2").datebox("clear");
		},
		getParams: function() {
			var params;
			var temp1 = $("#plan_start1").datebox("getValue");
			var temp2 = $("#plan_start2").datebox("getValue");
			var temp3 = $("#plan_end1").datebox("getValue");
			var temp4 = $("#plan_end2").datebox("getValue");
			try {
				params = {
						title: $("#plan_title").val(),
						planStart1: temp1==''?'':temp1+':00',
						planStart2:	temp2==''?'':temp2+':00',
						planType: $("#plan_type").combobox('getValue'),
						planEnd1: temp3==''?'':temp3+':00',
						planEnd2: temp4==''?'':temp4+':00',
				};
			} catch(e) {}
			return params ? params : {};
		},
		toAdd: function() {
			this.openWin("新建工作计划", 700, 350, "/workPlan/toAddWorkPlan");
		},
		toEdit: function(id) {
			this.openWin("修改工作计划", 700, 350, "/workPlan/toEditWorkPlan?id="+id);
		},
		toShow: function(id) {
			this.openWin("查看工作计划", 750, 350, "/workPlan/toShowWorkPlan?id="+id);
		},
		doDel: function(id) {
			var that = this;
			$.messager.confirm("提示", "确定删除该工作计划吗？", function(r) {
				if(r) {
					$.ajax({
						url: that.basePath + "/workPlan/deleteWorkPlan",
						data: "id=" + id,
						success: function(data) {
							if(data == "success") {
								$('#tblWorkPlanList').datagrid('uncheckAll');
								$('#tblWorkPlanList').datagrid('unselectAll');
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
			var checkedItems = $("#tblWorkPlanList").datagrid("getChecked");
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
			$.messager.confirm("提示", "确定删除所选中的工作计划吗？", function(r) {
				if(r) {
					$.ajax({
						url: that.basePath + "/workPlan/deleteWorkPlans",
						data: "ids=" + ids,
						success: function(data) {
							if(data == "success") {
								$('#tblWorkPlanList').datagrid('uncheckAll');
								$('#tblWorkPlanList').datagrid('unselectAll');
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
			$('#workPlan_popWin').window({
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
			});
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
	//初始化计划类型
	$('#plan_type').combobox({
		url: getRootName() + '/dict/readItems?id=3085',
		valueField : 'key',
		textField : 'val',
		editable : false
	});
	doInitTable();
	
	function doInitTable() {
		var path = getRootName();
		$('#tblWorkPlanList').datagrid({
			iconCls: 'icon-edit',// 图标
			fit: true,// 自动大小
			fitColumns: true,
			url: path + '/workPlan/loadWorkPlanList',
			queryParams: workplan.list.getParams(),
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
	        		workplan.list.toAdd();
	        	}
			}, {
				text: "删除",
	        	iconCls: "icon-cancel",
	        	handler: function() {
	        		workplan.list.doBatchDel();
	        	}
			}, {
				text: "日历视图",
	        	iconCls: "icon-add",
	        	handler: function() {
	        		window.open(path+'/workPlan/toCalendar');
	        	}
			}
			],
			scrollbarSize: 0,
			striped: true,
			sortName: 'planStart',
			sortOrder: 'desc',
			columns: [ [
			             {
			            	 title: 'ck',
			            	 checkbox: true,
			             },
			             {
			            	 title: '计划名称',
			            	 field: 'title',
			            	 align: 'center',
			            	 width: 160,
			             },
			             {
			            	 title: '开始时间',
			            	 field: 'planStart',
			            	 align: 'center',
			            	 width: 120,
			            	 sortable: true,
			            	 formatter: function(value,row,index) {
			            		 return workplan.list.formatDate(value);
			            	 }
			             },
			             {
			            	 title: '结束时间',
			            	 field: 'planEnd',
			            	 align: 'center',
			            	 width: 120,
			            	 formatter: function(value,row,index) {
			            		 return workplan.list.formatDate(value);
			            	 }
			             },
			             {
			            	 title: '相关人员',
			            	 field: 'relationPersons',
			            	 align: 'center',
			            	 width: 160,
			             },
			             {
			            	 title: '操作',
			            	 field: 'id',
			            	 align: 'center',
			            	 width: 140,
			            	 formatter : function(value,row,index){
			            		 return "<a style='cursor:pointer' title='查看' onclick=javascript:workplan.list.toShow(" + row.id + ");><img src='" + path + "/style/images/icon/icon_view.png' alt='查看'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
			                     "<a style='cursor:pointer' title='修改' onclick=javascript:workplan.list.toEdit(" + row.id + ");><img src='" + path + "/style/images/icon/icon_modify.png' alt='编辑'/></a>&nbsp;&nbsp;&nbsp;&nbsp;" + 
			                     "<a style='cursor:pointer' title='删除' onclick=javascript:workplan.list.doDel(" + row.id + ");><img src='" + path + "/style/images/icon/icon_delete.png' alt='删除'/></a>";
			            	 }
			             }] ]
		});
	    // 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
		$(window).resize(function() {
			$('#tblWorkPlanList').resizeDataGrid(0, 0, 0, 0);
		});
	}
});