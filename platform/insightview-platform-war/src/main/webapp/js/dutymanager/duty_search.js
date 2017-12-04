var dutymanager = dutymanager || {};
dutymanager.duty = {
		basePath : getRootName(),
		search : function () {
			f('#tblDutyDataList').datagrid('load');
		},
		reload : function () {
			f('#tblDutyDataList').datagrid('reload');
		},
		reset : function () {
			f('#duty_leader').select2('val', '-1'),
			f('#duty_watch').select2('val', '-1'),
			f('#search_startTime').datebox('clear'),
			f('#search_endTime').datebox('clear');
		},
		getParams : function () {
			var params ;
			try {
				params = {
						leader : f('#duty_leader').select2('val'),
						watch : f('#duty_watch').select2('val'),
						begin : f('#search_startTime').datebox('getValue'),
						end : f('#search_endTime').datebox('getValue')
				};				
			} catch (e) {}
			return params ? params : {};
		},
		toAdd : function (){
			this.openWin('新建值班信息', 700, 600,'/platform/dutymanager/duty/toAdd');
		},
		toEdit : function (dutyId) {
			this.openWin('编辑值班信息', 700, 600,'/platform/dutymanager/duty/toEdit?dutyId=' + dutyId);
		},
		toView : function (dutyId) {
			this.openWin('查看值班信息', 700, 600,'/platform/dutymanager/duty/toView?dutyId=' + dutyId);
		},
		dodelete : function (dutyId) {
			var that = this;
			$.messager.confirm('提示', '确定删除该值班信息吗？', function(r){
				if (r){
					f.ajax({
						url : that.basePath + '/platform/dutymanager/duty/delete',
						data : {dutyId : dutyId},
						success : function (data) {
							if (data) {
								that.reload();
							} else {
								f.messager.alert('提示', '删除值班记录信息异常,请检查！', 'info');
							}
						}
					});
				}
			});
		},
		openWin : function (title, width, height, url) {/*打开窗口*/
			var that = this;
			parent.f('#popWin').window({
				title : title,
				width : width,
				height : height,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : this.basePath + url
			});
		},
		importData : function () {/*导入值班记录*/
			var that = this;
			try {
				f('#duty_import').dialog('open');
			} catch (e) {
				f('#duty_import').removeAttr('style').dialog({
					title : '导入值班信息',
					width : 500,
					height : 320,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					onOpen : function () {
						f('#duty_file').val('');
						f('#msg_duty').html('');
					}
				}).dialog('open');
				/*初始化上传控件项目附件*/
				f('.import_btn').bind("click", importData);
				
				function importData(event) {
					var path = getRootName(), dutyF = f('#duty_file')[0], fileVal = dutyF.value;
					if (!dutyF.value) {
						f.messager.alert('提示', '请选择导入值班信息文件！', 'info');
						return;
					}
					var fileSize = 0;
		            if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") {
		                var objFSO = new ActiveXObject("Scripting.FileSystemObject");
		                var objFile = objFSO.getFile(fileVal);
		                fileSize = objFile.size;
		            }else{
		                fileSize = dutyF.files[0].size;
		            }
		            var fileType = fileVal.substring(fileVal.lastIndexOf(".") + 1, fileVal.length);
		            if (!('xls' == fileType)) {
		            	f.messager.alert('提示', '导入文件类型只支持xls！', 'info');
						return;
		            }
		            if (fileSize/1024/1024 > 5) {
		            	f.messager.alert('提示', '导入文件不能大于5M', 'info');
						return;
		            }
		            f(event.target).attr('disabled', 'disabled').unbind();
		            f('#msg_duty').html('<div style="text-align: center;"><img title="导入中" src="' + path + '/style/images/handling.png"/></div>');
					f.ajaxFileUpload({
						url : path + '/platform/dutymanager/duty/importData',
						secureuri : false,
						fileElementId : 'duty_file',
						dataType: 'json',
						success : function(data) {
							if (data.title) {
								f('#msg_duty').html(data.title);
							} else {
								f('#msg_duty').html(data.error);
							}
							f(event.target).removeAttr('disabled').bind('click', importData);
							that.search();
						},
						error : function(data, status, e) {
							f.messager.alert("提示","导入值班信息失败！","error");
						}
					});
				}
			}
		},
		template : function () {/*导入模板下载*/
			window.location = this.basePath + '/platform/dutymanager/duty/template';
		},
		generatGridContent : function (datas, field) {
			if (!datas) {/*没有设置值班班次*/
				return;
			}
			var content = [];
			content.push('<table class="orders" style="width:100%;height:100%;">');
			switch (field) {
				case 'Dutyer':	
					for (var i = 0, length = datas.length; i < length; i++) {
						var names = f.map(datas[i].userNames, function (name) {
							return name.UserName;
						});
						content.push('<tr><td>');
						if (names.length >= 3) {
							content.push('<span title="' + names.join(",") + '">');
							content.push(names.slice(0,2).join(',') + '...');
							content.push('</span>');
						} else {
							content.push(names.join(','));
						}
						content.push('</td></tr>');
					}
				break;
				case 'Order':	
					var titles = f.map(datas, function (title) {
						return '<tr><td>' + title.Title + '</td></tr>';
					});
					content.push(titles.join(' '));
				break;
				case 'Begin':	
					var begins = f.map(datas, function (begin) {
						return '<tr><td>' + begin.BeginPoint + '</td></tr>';
					});
					content.push(begins.join(' '));
				break;
				case 'End':	
					var ends = f.map(datas, function (end) {
						return '<tr><td>' + end.EndPoint + '</td></tr>';
					});
					content.push(ends.join(' '));
				break;
			}
			content.push('</table>');
			return content.join(" ");
		}
};

f(function(){
	f('#duty_leader,#duty_watch').createSelect2({
		uri : '/platform/dutymanager/duty/queryDutyers',// 获取数据
		name : 'name',// 显示名称
		id : 'id'
	});	
	setTimeout(function (){initDutyGrid();}, 100);
	function initDutyGrid(){	
		var $dutyList = f('#tblDutyDataList'), path = getRootName();
		$dutyList.datagrid({
			iconCls : 'icon-edit',// 图标
			fit : true,// 自动大小
			fitColumns : true,
			url : path + '/platform/dutymanager/duty/list',
			idField : 'ID',
			singleSelect : true,// 是否单选
			pagination : true,// 分页控件
			rownumbers : true,// 行号
			toolbar : '#duty_toolbar',
			scrollbarSize:0,
			striped : true,
			sortName : 'DutyDate',
			sortOrder : 'desc',
			onBeforeLoad : function () {       	
				return f.extend(arguments[0], dutymanager.duty.getParams());
			},
			onLoadSuccess : function () {
				var orders = f('table.orders');
				orders.find('td:eq(0)').addClass('td_border_no');
				orders.find('td:gt(0)').addClass('td_border_one');
			},
			columns : [ [
			             {
			            	 field : 'DutyDate',
			            	 title : '值班日期',
			            	 align : 'center',
			            	 width : 140,
			            	 sortable : true
			             },
			             {
			            	 field : 'Dutyer',
			            	 title : '值班人',
			            	 align : 'center',
			            	 width : 160,
			            	 formatter : function(value,row,index){
			            		 return dutymanager.duty.generatGridContent(row.order, 'Dutyer');
			            	 }
			             },
			             {
			            	 field : 'Order',
			            	 title : '值班班次',
			            	 align : 'center',
			            	 width : 160,
			            	 formatter : function(value,row,index){
			            		 return dutymanager.duty.generatGridContent(row.order, 'Order');
			            	 }
			             },
			             {
			            	 field : 'Begin',
			            	 title : '开始时间',
			            	 align : 'center',
			            	 width : 120,
			            	 formatter : function(value,row,index){
			            		 return dutymanager.duty.generatGridContent(row.order, 'Begin');
			            	 }
			             },
			             {
			            	 field : 'End',
			            	 title : '结束时间',
			            	 align : 'center',
			            	 width : 120,
			            	 formatter : function(value,row,index){
			            		 return dutymanager.duty.generatGridContent(row.order, 'End');
			            	 }
			             },
			             {
			            	 field : 'Leader',
			            	 title : '带班领导',
			            	 align : 'center',
			            	 width : 120
			             },
			             {
			            	 field : 'Oper',
			            	 title : '操作',
			            	 align : 'center',
			            	 width : 140,
			            	 formatter : function(value,row,index){
			            		 return '<a style="cursor: pointer;");"><img src="'
									+ path	+ '/style/images/icon/icon_view.png" title="查看" onclick="dutymanager.duty.toView(' + row.ID + ');"/></a> &nbsp;'+
									'<a style="cursor: pointer;" title="修改"><img src="' + path
									+ '/style/images/icon/icon_modify.png" title="修改" onclick="dutymanager.duty.toEdit(' + row.ID + ');"/></a> &nbsp;' +
									'<a style="cursor: pointer;" title="删除" "><img src="'
									+ path
									+ '/style/images/icon/icon_delete.png" title="删除" onclick="dutymanager.duty.dodelete(' + row.ID + ');"/></a>';
			            	 }
			             }] ]
		});
	    // 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
		$(window).resize(function() {
			f('#duty_search_panel').panel('resize', {width : f('#duty_toolbar').closest('div.panel-body').css('width')});
			$dutyList.resizeDataGrid(0, 0, 0, 0);
		});
	};
});

/**
 * 数据导出
 */
function doExport() {
	var params = dutymanager.duty.getParams();
	var leader = params.leader;
	var watch = params.watch;
	var begin = params.begin;
	var end = params.end;
	var order = f('#tblDutyDataList').datagrid('options').sortOrder;
	window.location.href = getRootName() + "/platform/dutymanager/duty/exportExcel?leader="+leader+"&watch="+watch+"&begin="+begin+"&end="+end+"&order="+order;
}
