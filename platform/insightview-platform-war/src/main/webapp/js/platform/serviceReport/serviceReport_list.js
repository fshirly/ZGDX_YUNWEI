f.namespace('platform.paoject');
/**
 * 服务报告列表页API
 */
platform.paoject.serviceReport = (function(){
	/**
	 * 暴露给页面的API
	 */
	var serviceReport = {
			toUpdate: function(id){
				toUpdate(id);
			},
			toView: function(id){
				toView(id);
			},
			doDel: function(id){
				doDel(id);
			},
			reloadTable: function(){
				reloadTable();
			},
			toReview: function(id){
				toReview(id);
			},
			toApply: function(id){
				toApply(id);
			},
	};
	
	/**
	 * 给页面元素绑定事件
	 */
	f('#conditions_query').click(reloadTable);
	f('#conditions_reset').click(resetQueryValue);
	
	var path = getRootName();

	$(document).ready(function() {
		
		doInitTable();
		
		$('#startTime').datetimebox({
			formatter : formatDate,
			editable : false
		});
		$('#endTime').datetimebox({
			formatter : formatDate,
			editable : false
		});
		
		
		//报告人
		$('#reporter').createSelect2({
			uri : '/permissionSysUser/querySysUserInfo',
		    name : 'userName',
		    id : 'userID'
	    });
		
	});


	/**
	 * 重新加载查询出的服务报告信息
	 */
	function reloadTable() {
		var name = $("#name").val();
		var reporter = $("#reporter").select2("val");
		var startTime = $("#startTime").datetimebox("getValue");
		var endTime = $("#endTime").datetimebox("getValue");
		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
		if (end < start) {
			$.messager.alert('提示', '查询结束时间不得早于开始时间', 'error');
		}else{
			$('#tblServiceReport').datagrid('options').queryParams = {
				"name" : name,
				"reporter" : reporter,
				"startTime" : startTime,
				"endTime" : endTime
			};
		}
		reloadTableCommon('tblServiceReport');
	}

	/**
	 * 重置
	 * @param divName
	 */
	function resetQueryValue() {
		resetForm("divFilter");
		$("#reporter").select2("val", -1);
	}

	/**
	 * 页面初始化 显示服务报告列表信息
	 */
	function doInitTable() {
		$('#tblServiceReport').datagrid(
						{
							iconCls : 'icon-edit',// 图标
							width : 'auto',
							height : 'auto',
							nowrap : false,
							striped : true,
							border : true,
							collapsible : false,// 是否可折叠的
							fit : true,// 自动大小
							fitColumns : true,
							url : path + '/serviceReport/listServiceReport',
							remoteSort : false,
							idField : 'serviceReportID',
							singleSelect : true,// 是否单选
							checkOnSelect : false,
							selectOnCheck : false,
							pagination : true,// 分页控件
							rownumbers : true,// 行号
							toolbar : [ {
								text : '新建',
								iconCls : 'icon-add',
								handler : function() {
									toAdd();
								}
							}, '-', {
								text : '删除',
								iconCls : 'icon-cancel',
								handler : function() {
									doBatchDel();
								}
							}],
							columns : [ [
									{
										field : 'serviceReportID',
										title : '全选',
										checkbox : 'true',
										width : 80,
										align : 'center'
									},
									{
										field : 'name',
										title : '服务报告名称',
										width : 200,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a  style="cursor: pointer;color:#000000" title="'+value+'" class="easyui-tooltip">'+value+'</a>'
										}
									},
									{
										field : 'reportTime',
										title : '报告时间',
										width : 150,
										align : 'center',
										sortable:true,
										sorter:function(a,b){
											return (a>b?1:-1);
										},
										formatter : function(value, row, index) {
											if(value!=null && value!="" ){
												return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
											}else{
												return "";
											}	
										}
									},
									{
										field : 'reporterName',
										title : '报告人',
										width : 150,
										align : 'center'
									},
									{
										field : '0',
										title : '操作',
										width : 220,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a  style="cursor: pointer;color:#0064b1" title="查看" class="easyui-tooltip" onclick="javascript:platform.paoject.serviceReport.toView('
													+ row.serviceReportID
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a  style="cursor: pointer;color:#0064b1" title="修改" class="easyui-tooltip" onclick="javascript:platform.paoject.serviceReport.toUpdate('
													+ +row.serviceReportID
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_modify.png" alt="修改" />&nbsp;&nbsp;&nbsp;&nbsp;<a  style="cursor: pointer;color:#0064b1" title="删除" class="easyui-tooltip" onclick="javascript:platform.paoject.serviceReport.doDel('
													+ row.serviceReportID
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
										}
									} ] ]
						});
		$(window).resize(function() {
	        $('#tblServiceReport').resizeDataGrid(0, 0, 0, 0);
	    });
	}


	/**
	 * 打开新增服务报告信息
	 */
	function toAdd() {
		 parent.$('#popWin').window({
				 title : '服务报告新增',
		         width : 800,
		         height : 375,
		         minimizable : false,
				 maximizable : false,
				 collapsible : false,
				 modal : true,
				 href : getRootName() + '/serviceReport/toServiceReportAdd'
			});
	}
	

	/**
	 * 打开修改服务报告信息
	 */
	function toUpdate(id) {
		 parent.$('#popWin').window({
				 title : '服务报告修改',
		         width : 800,
		         height : 375,
		         minimizable : false,
				 maximizable : false,
				 collapsible : false,
				 modal : true,
				 href : getRootName() + '/serviceReport/toServiceReportEdit?id='+id
			});
	}

	/**
	 * 打开服务报告查看页面
	 * @param id
	 */
	function toView(id){
		parent.$('#popWin').window({
			 title : '服务报告详情',
	         width : 900,
	         height : 375,
	         minimizable : false,
			 maximizable : false,
			 collapsible : false,
			 modal : true,
			 href : getRootName() + '/serviceReport/toServiceReportView?id='+id
		});
	}
	
	/**
	 * 删除
	 * @param id
	 */
	function doDel(id){
		$.messager.confirm("提示", "确定删除?", function(r) {
			if (r == true) {
				var uri = path + "/serviceReport/delServiceReport?id="+id;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"id" : id,
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							reloadTable();
						} else {
							$.messager.alert("提示", "删除失败！", "error");
						}
					}
				};
				ajax_(ajax_param);
			}
		});
	}

	/**
	 * 批量删除
	 */
	function doBatchDel(){
		var checkedItems = $('#tblServiceReport').datagrid('getChecked');
		if(checkedItems.length == 0){
			$.messager.alert("提示", "未选中任何项！", "info");
		}else{
			$.messager.confirm("提示","确定删除所选中项？",function(r){
				if (r == true) {
					var ids = null;
					$.each(checkedItems, function(index, item) {
						if (null == ids) {
							ids = item.serviceReportID;
						} else {
							ids += ',' + item.serviceReportID;
						}
					});
					if (null != ids) {
						var uri = getRootName() + '/serviceReport/batchDelServiceReport?ids='+ids;
						var ajax_param = {
								url : uri,
								type : "post",
								datdType : "json",
								data : {
									"t" : Math.random()
								},
								success : function(data) {
									reloadTable();
									$("#tblServiceReport").datagrid("uncheckAll");
								}
						};
						ajax_(ajax_param);
					}
				}
			});
			
		}
	}
	
	return serviceReport;
	
})();
