/**
 * 首页我的关注详情页命名空间
 */
f.namespace('platform.dashboard');
/**
 * 我的关注详情页API
 */
platform.dashboard.myConcernDetail = (function(){
	
	/**
	 * 暴露给页面的API
	 */
	var myConcernDetail = {
			checkResCiChangeInfo: function(changeId){
				checkResCiChangeInfo(changeId);
			},
			showResCiAttrChanges: function(changeId , changeItem){
				showResCiAttrChanges(changeId , changeItem);
			}
	};
	
	/**
	 * 给页面元素绑定事件
	 */
	f('#myConcernDetail_cancle').click(doCancel);
	f('#myConcernCiChangeInfo_cancle').click(doInfoCancle);
	
	$(document).ready(function() {
		loadCiChangeTable();
	});

	/**
	 * 查看变更明细打开窗口
	 */
	function showResCiAttrChanges(changeId , changeItem) {
		// 先加载配置项列表
		doInitChangeDetailTable(changeId , changeItem);
		$('#divResCiChangeDetails').dialog('open');
	}

	/**
	 * 查看变更明细初始化数据
	 */
	function doInitChangeDetailTable(changeId , changeItem) {
		//根据变更类型，加载不同的datagrid
		$('#tblResCiChangeList').datagrid({
			queryParams : {
				changeId : changeId,
			},
			iconCls : 'icon-edit',// 图标
			width : 660,
			height : 'auto',
			nowrap : false,
			striped : true,
			border : true,
			collapsible : false,// 是否可折叠的
			fit : true,// 自动大小
			remoteSort : false,
			idField : 'ciID',
			singleSelect : true,// 是否单选
			//pagination : true,// 分页控件
			rownumbers : true,// 行号
		});
		if(changeItem === 4){
			//关系变更
			$('#tblResCiChangeList').datagrid({
				url : getRootName() + '/resCiChange/queryCiRelaHistory',
				columns : [ [ {
					field : 'changeId',
					title : '编号',
					width : 100,
					align : 'center'
				}, {
					field : 'sourceCiName',
					title : '主配置项',
					width : 150,
					align : 'center',
				}, {
					field : 'targetCiName',
					title : '目标配置项',
					width : 150,
					align : 'center',
				},{
					field : 'relationTypeName',
					title : '关系类型',
					width : 100,
					align : 'center',
				},{
					field : 'operateType',
					title : '操作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						if (value === 1) {
							return "添加";
						} else {
							return "删除";
						}
					}
				}] ]
			});
		}else{
			//关键属性、状态变更
			$('#tblResCiChangeList').datagrid({
				url : getRootName() + '/resCiChange/queryCiAttrHistory',
				columns : [ [ {
					field : 'changeId',
					title : '编号',
					width : 120,
					align : 'center'
				}, {
					field : 'attributeName',
					title : '变更项目/属性',
					width : 160,
					align : 'center',
					formatter : function(value, row, index) {
						if (value == 'status') {
							return "状态";
						} else {
							return value;
						}
					}
				}, {
					field : 'preValue',
					title : '原值',
					width : 160,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.attributeName == 'status') {
							value = parseInt(value);
							if (value === 0) {
								return '库存';
							} else if (value === 1) {
								return "正常使用";
							} else if (value === 2) {
								return "故障";
							} else if (value === 3) {
								return "移除/清除";
							}
						} else {
							return value;
						}
					}
				}, {
					field : 'currentValue',
					title : '目标值',
					width : 160,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.attributeName == 'status') {
							value = parseInt(value);
							if (value === 0) {
								return '库存';
							} else if (value === 1) {
								return "正常使用";
							} else if (value === 2) {
								return "故障";
							} else if (value === 3) {
								return "移除/清除";
							}
						} else {
							return value;
						}
					}
				} ] ]
			});
		}
	}

	/**
	 * 查看配置项变更信息
	 */
	function checkResCiChangeInfo(changeId) {
		var operate = 'view';
		$('#divResCiChangeInfo').window(
				{
					title : '查看配置项变更',
					width : 750,
					height : 400,
					collapsible : false,
					minimizable : false,
					maximizable : false,
					onBeforeOpen : function() {
						if ($('.divResCiChangeInfo').size() > 1) {
							$('.divResCiChangeInfo:first').parent().remove();
						}
					},
					modal : true,
					href : getRootName() + '/resCiChange/checkResCiChangeInfo?changeId='
							+ changeId + '&operate=' + operate
				});
	}

	function doCancel() {
		$('#divResCiChangeDetails').dialog('close');
	}

	function doInfoCancle(){
		parent.$('#popWin').window('close');
		window.frames['component_2'].$('#dashboard-widget-myConcern .sDashboard-icon.sDashboard-refresh-icon').click();
		window.frames['component_2'].platform.dashboard.myConcernList.reloadTable();
	}

	/**
	 * 加载配置项变更信息
	 */
	function loadCiChangeTable() {
		$("#tblCiChangeInfo").datagrid(
						{
							queryParams : {
								ciID : $('#ciID').val(),
							},
							iconCls : 'icon-edit',// 图标
							width : 830,
							height : '400',
							nowrap : false,
							striped : true,
							border : true,
							collapsible : false,// 是否可折叠的
							fit : false,// 自动大小
							remoteSort : false,
							idField : 'id',
							singleSelect : true,// 是否单选
							//pagination : true,// 分页控件
							url : getRootName() + '/resCiChange/loadLatestCiChanges',
							columns : [ [
									{
										field : 'changeId',
										title : '编号',
										width : 75,
										align : 'center'
									},
									{
										field : 'changeItem',
										title : '变更类别',
										width : 200,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == 1) {
												return '<a style="cursor: pointer;color:#0064b1" onclick="javascript:platform.dashboard.myConcernDetail.checkResCiChangeInfo('
														+ row.changeId
														+ ');">关键属性变更</a>';
											} else if (value == 2) {
												return '<a style="cursor: pointer;color:#0064b1" onclick="javascript:platform.dashboard.myConcernDetail.checkResCiChangeInfo('
														+ row.changeId
														+ ');">配置项类型变更</a>';
											} else if (value == 3) {
												return '<a style="cursor: pointer;color:#0064b1" onclick="javascript:platform.dashboard.myConcernDetail.checkResCiChangeInfo('
														+ row.changeId
														+ ');">配置项状态变更</a>';
											} else if (value == 4) {
												return '<a style="cursor: pointer;color:#0064b1" onclick="javascript:platform.dashboard.myConcernDetail.checkResCiChangeInfo('
														+ row.changeId
														+ ');">变更关系/组件</a>';
											} else {
												return '';
											}
										}
									},
									{
										field : 'changeManName',
										title : '变更人',
										width : 140,
										align : 'center'
									},
									{
										field : 'changeTime',
										title : '变更时间',
										width : 160,
										align : 'center',
										formatter : function(value, row, index) {
											if (value == '') {
												return '';
											} else {
												return formatDate(new Date(
														row.changeTime),
														"yyyy-MM-dd hh:mm:ss");
											}
										}
									},
									{
										field : '0',
										title : '变更明细',
										width : 180,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a style="cursor: pointer;color:#0064b1" title="查看明细" class="easyui-tooltip" onclick="javascript:platform.dashboard.myConcernDetail.showResCiAttrChanges('
													+ row.changeId + ',' + row.changeItem
													+ ');"><img src="'
													+ getRootName()
													+ '/style/images/icon/icon_view.png" alt="查看明细" /></a>';
										}
									} ] ],
						});
	}

	return myConcernDetail;
})();


