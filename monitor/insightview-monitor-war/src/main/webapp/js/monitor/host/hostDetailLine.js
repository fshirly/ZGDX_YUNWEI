$(document).ready(function() {
	  doHostCPUInitTables();// 主机CPU
	  doHostMemoryInitTables();// 主机内存
	  doHostDiskInitTables();// 主机硬盘
	  doFlowsInitTables();// 接口流量

});

// 主机CPU
function doHostCPUInitTables() {
					var path = getRootName();
					$('#tblCPU')
							.datagrid(
									{
										iconCls : 'icon-edit',// 图标
										width : 700,
										height : 'auto',
										nowrap : false,
										striped : true,
										border : true,
										singleSelect : false,// 是否单选
										checkOnSelect : false,
										selectOnCheck : true,
										collapsible : false,// 是否可折叠的
										fit : true,// 自动大小
										url : path + '/monitor/hostManage/findDetailCPUInfo',
									 								
										idField : 'fldId',
										columns : [ [
												
												{
													field : 'deviceip',
													title : '设备IP',
													width : 120,
													align : 'center'
												},
												{
													field : 'instance',
													title : 'CPU编号',
													width : 150,
													align : 'center',
											        }
												,
												{
													field : 'perfvalue',
													title : 'CPU使用率',
													width : 150,
													align : 'center',
											        }
												,
												{
													field : 'collecttime',
													title : '采集时间',
													width : 150,
													align : 'center',
											        }
											        ] ]
											     });
											 }

// 主机内存
function doHostMemoryInitTables() {
	var path = getRootName();
	$('#tblHostTopMemory')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						singleSelect: true, // 是否单行选择
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						url : path + '/monitor/hostManage/topPerfMemoryInfo?tableName=PerfSnapshotHost',
					 								
						idField : 'fldId',
						columns : [ [
								{
									field : 'moname',
									title : '设备名称',
									width : 120,
									align : 'center',
								},
								{
									field : 'deviceip',
									title : '设备IP',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
								          return "<a href='/fable/monitor/hostManage/toShowVMDetail?deviceIP="+value+"' target='_blank'>"+value+"</a>";
								         }
								},
								{
									field : 'perfvalue',
									title : '使用率',
									width : 150,
									align : 'center',
									 formatter : function(value, row, index) {
							          var color = value < 90 ? '#00ff00' : '#ff0000';
							          return '<div class="easyui-progressbar" color="' + color + '" value="' + value + '" style="width:100px;height:15px;"></div>';
							         }
							        }
							        ] ],
							        onLoadSuccess:function(){
							 			$('.easyui-progressbar').progressbar().progressbar('setColor');
							        }
							     });
							 }


// TOP10虚拟内存使用率
function doVirtualMemoryInitTables() {
	var path = getRootName();
	$('#tblVirtualMemory')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						singleSelect: true, // 是否单行选择
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						url : path + '/monitor/hostManage/topPerfServVirtualMemoryInfo',
					 								
						idField : 'fldId',
						columns : [ [
								{
									field : 'moname',
									title : '设备名称',
									width : 120,
									align : 'center',
								},
								{
									field : 'deviceip',
									title : '设备IP',
									width : 120,
									align : 'center',
								},
								{
									field : 'virmemoryusage',
									title : '使用率',
									width : 150,
									align : 'center',
									 formatter : function(value, row, index) {
							          var color = value < 90 ? '#00ff00' : '#ff0000';
							          return '<div class="easyui-progressbar" color="' + color + '" value="' + value + '" style="width:100px;height:15px;"></div>';
							         }
							        }
							        ] ],
							        onLoadSuccess:function(){
							 			$('.easyui-progressbar').progressbar().progressbar('setColor');
							        }
							     });
							 }
// TOP10主机 硬盘使用率
function doHostDiskInitTables() {
	 var path = getRootName();
	 $('#tblHostTopDisk')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 700,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect: true, // 是否单行选择
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      url : path + '/monitor/hostManage/topPerfDiskInfo?tableName=PerfSnapshotHost',
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'moname',
	         title : '设备名称',
	         width : 120,
	         align : 'center',
	        },
	        {
	         field : 'deviceip',
	         title : '设备IP',
	         width : 110,
	         align : 'center',
	     	formatter : function(value, row, index) {
		          return "<a href='/fable/monitor/hostManage/toShowDeviceDetail?deviceIP="+value+"' target='_blank'>"+value+"</a>";
		         }
	        },
	        {
	         field : 'rawdescr',
	         title : '硬盘分区',
	         width : 70,
	         align : 'center',
	        },
	        {
	         field : 'perfvalue',
	         title : '使用率',
	         width : 150,
	         align : 'center',
	         formatter : function(value, row, index) {
	          var color = value < 90 ? '#00ff00' : '#ff0000';
	          return '<div class="easyui-progressbar" color="' + color + '" value="' + value + '" style="width:100px;height:15px;"></div>';
	         }
	        }
	        ] ],
	        onLoadSuccess:function(){
	 			$('.easyui-progressbar').progressbar().progressbar('setColor');
	        }
	     });
	 }


// 接口流量
function doFlowsInitTables() {
	 var path = getRootName();
	 $('#tblTopFlows')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 700,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect: true, // 是否单行选择
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      url : path + '/monitor/hostManage/topPerfFlowsInfo',
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'moname',
	         title : '设备名称',
	         width : 120,
	         align : 'center',
	        },
	        {
	         field : 'deviceip',
	         title : '设备IP',
	         width : 110,
	         align : 'center',
	        },
	        {
	         field : 'ifname',
	         title : '接口名称',
	         width : 70,
	         align : 'center',
	         formatter : function(value, row, index) {
		          return "<a href='/fable/monitor/hostManage/toShowIfDetail?deviceIP="+row.deviceip+"&IfName="+row.ifname+"' target='_blank'>"+value+"</a>";
		         }
	        },
	        {
		         field : 'inflows',
		         title : '流入量(Mb)',
		         width : 70,
		         align : 'center',
		        },
		        {
			         field : 'outflows',
			         title : '流出量(Mb)',
			         width : 70,
			         align : 'center',
			        }
	       
	        ] ]
	       
	     });
	 }

// 接口带宽流量
function doIfUsageInitTables() {
	 var path = getRootName();
	 $('#tblTopIfUsage')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 700,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect: true, // 是否单行选择
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      url : path + '/monitor/hostManage/topPerfIfUsageInfo',
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'moname',
	         title : '设备名称',
	         width : 120,
	         align : 'center',
	        },
	        {
	         field : 'deviceip',
	         title : '设备IP',
	         width : 110,
	         align : 'center',
	         formatter : function(value, row, index) {
		          return "<a href='/fable/monitor/hostManage/toShowPhysicsDetail?deviceIP="+value+"' target='_blank'>"+value+"</a>";
		         }
	        },
	        {
	         field : 'ifname',
	         title : '接口名称',
	         width : 70,
	         align : 'center'
	        },
	        {
		         field : 'inusage',
		         title : '流入使用率',
		         width : 150,
		         align : 'center',
		         formatter : function(value, row, index) {
			          var color = value < 90 ? '#00ff00' : '#ff0000';
			          return '<div class="easyui-progressbar" color="' + color + '" value="' + value + '" style="width:100px;height:15px;"></div>';
			         }
		        },
		        {
			         field : 'outusage',
			         title : '流出使用率',
			         width : 150,
			         align : 'center',
			         formatter : function(value, row, index) {
			          var color = value < 90 ? '#00ff00' : '#ff0000';
			          return '<div class="easyui-progressbar" color="' + color + '" value="' + value + '" style="width:100px;height:15px;"></div>';
			         }
			        }
			        ] ],
			        onLoadSuccess:function(){
			 			$('.easyui-progressbar').progressbar().progressbar('setColor');
			        }
			     });
			 }
// 最新告警
function doAlarmInitTables() {
	 var path = getRootName();
	 $('#tblAlarmActive')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 700,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect: true, // 是否单行选择
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      url : path + '/monitor/hostManage/topAlarmActiveInfo',
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'levelicon',
	         title : '告警级别',
	         width : 80,
	         align : 'center',
	         formatter : function(value, row, index) {
		          return '<img src="'
					+ path
					+ '/style/images/levelIcon/'
					+ value
					+ '"/>';
		         }
	         
	        },
	        {
	         field : 'sourcemoipaddress',
	         title : '告警IP',
	         width : 110,
	         align : 'center',
	        },
	        {
	         field : 'alarmcontent',
	         title : '告警信息',
	         width : 170,
	         align : 'left',
	        },
	        {
		         field : 'starttime',
		         title : '发生时间',
		         width : 180,
		         align : 'center'
		        }
	       
	        ] ]
	       
	     });
	 }
$.fn.progressbar.methods.setColor = function(jq, color){
	return jq.each(function(){
		var opts = $.data(this, 'progressbar').options;
		var color = color || opts.color || $(this).attr('color');
		$(this).css('border-color', color).find('div.progressbar-value .progressbar-text').css('background-color', color);
	});
}
