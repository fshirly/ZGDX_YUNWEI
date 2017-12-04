var dutymanager = dutymanager || {};
dutymanager.addduty = {
		basePath : getRootName(),		
		add : function () {
			var that = this;
			if (checkInfo ('#add_duty_form') && this.validateDuty()) {
				var orders = this.getExistOrder(true);
				if (orders.length === 0) {
					f.messager.alert('提示', '值班计划必须包含值班班次,请检查！' , 'info');
					return;
				}
				f.ajax({
					url : that.basePath + '/platform/dutymanager/duty/add',
					data : f('#add_duty_form').serialize() + '&' + f.param({'orders' : orders}) +'&level='+$('#cc').combobox('getValue'),
					success : function (data) {
						if (data) {
							window.frames['component_2'].dutymanager.duty.search();
							f('#popWin').window('close');
						} else {
							f.messager.alert('提示', '新建值班信息失败，请检查！' , 'info');
						}
					}
				});
			}
		},
		confige : function (type) {/*选择值班人员*/
			var that = this;
			var readers = f('[name="readys"]').val() + ';' + f('#readys').val();
			var dutyers = f('[name="dutyers"]').val() + ';' + f('#dutyers').val();
			var params = (readers === ';' ? '' : ('&readys=' + readers)) + (dutyers === ';' ? '' : ('&dutyers=' + dutyers));
			f('<div id="configer"/>').window({
				title : '选择人员',
				width : 640,
				height : 540,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				onClose : function () {
					f(this).window('destroy');
				},
				href : that.basePath + '/platform/dutymanager/duty/toSelect?type=' + type + params
			});
		},
		addOrder : function () {/*跳转到添加值班班次页面*/
			var that = this, orders = that.getIdleOrder(that.getExistOrder().join(','));
			if (orders.length === 0) {
				f.messager.alert('提示', '没有可选择的值班班次！', 'info');
				return ;
			}
			try {
				f('#add_order_dialog').dialog('open');
			} catch (e) {
				f('#add_order_dialog').removeAttr('style').dialog({
					title : '添加班次及值班人',
					width : 400,
					height : 250,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					onBeforeOpen : function () {
						$("#dutyers").keyup();
						if ($('.add_order_dialog').size() > 1){
							$('.add_order_dialog:first').parent().remove();
						}
					}
				});
				f('#duty_orders').combobox({/*班次信息*/				   
				    valueField : 'ID',
				    textField : 'Title',
				    editable : false,
				    panelWidth : 182,
				    loadFilter : function (data) {
				    	f.each(data, function (ind, option) {
				    		option.Time = option.BeginPoint + '~' + option.EndPoint;
				    		if (ind === 0) {
				    			option.selected = true;
				    			f('#duty_orders_time').val(option.Time);
								f('#order_intervals').val(option.IntervalDays);
				    		}
				    	});
				    	if (data.length === 0) {/*没有可选择的班次信息*/
				    		f('#duty_orders_time').val('');
				    		f(this).combobox('clear');
				    	}
				    	return data;
				    },
				    onSelect : function (record) {
				    	f('#duty_orders_time').val(record.Time);
						f('#order_intervals').val(record.IntervalDays);
				    }
				});
			}
			f('#duty_orders').combobox('loadData', orders);/*重新加载班次信息*/
			f('#dutyers').val('').removeAttr('title').prev().val('');
		},
		confirm : function () {/*添加值班班次信息*/
			if (checkInfo('#add_order_dialog') ) {
				this.setGridData();
				f('#add_order_dialog').dialog('close');
			}
		},
		getExistOrder : function (datas) {/*获取已添加的值班班次信息*/
			var data = f('#orders').datagrid('getData').rows;
			if (datas) {
				return data;
			}
			return f.map(data, function (row) {return row.ID;});
		},
		getIdleOrder : function (existIds) {/*查询值班班次信息*/
			var idleOrder , that = this;
			f.ajax({
				url : that.basePath + '/platform/dutymanager/duty/queryOrders',
				data : {ids : existIds},
				async : false,
				success : function (data) {
					idleOrder = data;
				}
			});
			return idleOrder;
		},
		setGridData : function () {/*添加值班班次信息*/
			var $order = f('#duty_orders'),	data = {ID : $order.combobox('getValue'), Title : $order.combobox('getText')};
			var times = f('#duty_orders_time').val().split('~');
			var userName = f('#dutyers').val();
			var userIds = f('[name="dutyers"]').val();
			var unIndex = userName.lastIndexOf(',');
			var uiIndex = userIds.lastIndexOf(',');
			var $orderGrid = f('#orders');
			var rows = $orderGrid.datagrid('getData').rows;
			var newTime = times[0].substr(0, times[0].indexOf(":"));
			var oldTime ;
			data.BeginPoint = times[0];
			data.EndPoint = times[1];
			data.IntervalDays = f('#order_intervals').val();
			if (unIndex >= 0) {
				data.Principal = userName.substr(unIndex + 1);			
				data.PrincipalId = userIds.substr(uiIndex + 1);				
				data.Dutyers = userName;
				data.UserIds = userIds.substring(0, uiIndex);
			} else {
				data.Principal = userName;			
				data.PrincipalId = userIds;
				data.Dutyers = userName;
			}
			for (var i = 0, size = rows.length; i < size; i++) {
				oldTime = rows[i].BeginPoint.substr(0, rows[i].BeginPoint.indexOf(":"));
				if (parseInt(oldTime) < parseInt(newTime)) {
					if (i === size - 1) {
						$orderGrid.datagrid('insertRow', {index : size, row : data});
					}
					continue;
				} else {
					$orderGrid.datagrid('insertRow', {index : i, row : data});
					break;
				}
			}
			if (0 === size) {
				$orderGrid.datagrid('insertRow', {index : 0, row : data});
			}
		},
		delGridData : function (index) {/*删除值班班次*/
			f.messager.confirm('提示', '确定删除该班次信息?', function(r){
				if (r) {
					f('#orders').datagrid('deleteRow', index);
				}
			});
		},
		validateDuty : function () {/*验证值班日期*/
			var that = this, isExist, dutyDate = f('#dutyDate').datebox('getValue');
			f.ajax({
				url : that.basePath + '/platform/dutymanager/duty/validateDutyDate',
				data : {dutyDate : dutyDate},
				async : false,
				success : function (data) {
					isExist = data;
				}
			});
			if (!isExist) {
				f.messager.alert('提示', '日期[' + dutyDate + ']的值班计划已安排,请检查！', 'info');
			}
			return isExist;
		}
};

f(function(){
	f('#leaderId').createSelect2({
		uri : '/platform/dutymanager/duty/queryDutyers',// 获取数据
		name : 'name',// 显示名称
		id : 'id'
	});
	$("#cc").combobox({
		url : getRootName() + '/dict/readItems?id=3687',
		valueField : 'key',
		textField : 'val',
		editable : false,
		panelWidth : 182
	});
	$("#cc").combobox('select', 4);
	(function(){
		f('#orders').datagrid({
			iconCls : 'icon-edit',// 图标
			fit : true,// 自动大小
			fitColumns : true,
			idField : 'ID',
			rownumbers : true,// 行号
			scrollbarSize:0,
			striped : true,
			toolbar : '#add_duty_toolbar',
			columns : [ [
			             {
			            	 field : 'Title',
			            	 title : '班次名称',
			            	 align : 'center',
			            	 width : 120
			             },
			             {
			            	 field : 'BeginPoint',
			            	 title : '开始时间',
			            	 align : 'center',
			            	 width : 80
			             },
			             {
			            	 field : 'EndPoint',
			            	 title : '结束时间',
			            	 align : 'center',
			            	 width : 80
			             },
			             {
			            	 field : 'Dutyers',
			            	 title : '值班人',
			            	 align : 'center',
			            	 width : 120,
			            	 formatter : function (value,row,index) {
			            		 if (value) {
			            			 var names = value.split(',');
			            			 if (names.length > 2) {
			            				 return '<span title="' + value + '">' + names.slice(0,2).join(',') + '...' + '</span>';
			            			 } 
			            			 return value;
			            		 }
			            	 }
			             },
			             {
			            	 field : 'Principal',
			            	 title : '值班负责人',
			            	 align : 'center',
			            	 width : 100
			             },
			             {
			            	 field : 'Oper',
			            	 title : '操作',
			            	 align : 'center',
			            	 width : 80,
			            	 formatter : function(value,row,index){
			            		 return '<a href="javascript:dutymanager.addduty.delGridData(' + index + ')">删除</a>';
			            	 }
			             }] ]
		});
	})();
});