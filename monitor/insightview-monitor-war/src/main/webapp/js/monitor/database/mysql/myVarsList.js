$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doInnoDBInitTables(moID);
	doMyIsamInitTables(moID);
	doCharsetInitTables(moID);
	doPerfParamInitTables(moID);
	doInstallInitTables(moID);
	doDbSwitchInitTables(moID);
	doDbConfigInitTables(moID);
	doAllInitTables(moID,liInfo);
});
/**
 * 刷新表格数据
 */
function reloadTable(type){
	if (type == 'InnoDB') {
		$("#selName").val($('#selNameInnoDB').val());
		$("#opera").val($('#operaInnoDB').val());
		$("#txtValue").val($('#txtValueInnoDB').val());
	} else if (type == 'MyIsam') {
		$("#selName").val($('#selNameMyIsam').val());
		$("#opera").val($('#operaMyIsam').val());
		$("#txtValue").val($('#txtValueMyIsam').val());
	} else if (type == 'Charset') {
		$("#selName").val($('#selNameCharset').val());
		$("#opera").val($('#operaCharset').val());
		$("#txtValue").val($('#txtValueCharset').val());
	} else if (type == 'PerfParam') {
		$("#selName").val($('#selNamePerfParam').val());
		$("#opera").val($('#operaPerfParam').val());
		$("#txtValue").val($('#txtValuePerfParam').val());
	} else if (type == 'Install') {
		$("#selName").val($('#selNameInstall').val());
		$("#opera").val($('#operaInstall').val());
		$("#txtValue").val($('#txtValueInstall').val());
	} else if (type == 'Switch') {
		$("#selName").val($('#selNameSwitch').val());
		$("#opera").val($('#operaSwitch').val());
		$("#txtValue").val($('#txtValueSwitch').val());
	} else if (type == 'DbConfig') {
		$("#selName").val($('#selNameDbConfig').val());
		$("#opera").val($('#operaDbConfig').val());
		$("#txtValue").val($('#txtValueDbConfig').val());
	} else {
		$("#selName").val($('#selNameAll').val());
		$("#opera").val($('#operaAll').val());
		$("#txtValue").val($('#txtValueAll').val());
	}
	var varName = $("#selName").val();
	var opera = $("#opera").val();
	var txtValue= $("#txtValue").val();
	if (type == 'InnoDB') {
		$('#tablInnoDBVars').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablInnoDBVars');
	} else if (type == 'MyIsam') {
		$('#tablMyISAMConfig').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablMyISAMConfig');
	} else if (type == 'Charset') {
		$('#tablCharsetConfig').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablCharsetConfig');
	} else if (type == 'PerfParam') {
		$('#tablPerfParameter').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablPerfParameter');
	} else if (type == 'Install') {
		$('#tablInstallInfo').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablInstallInfo');
	} else if (type == 'Switch') {
		$('#tablDbSwitch').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablDbSwitch');
	} else if (type == 'DbConfig') {
		$('#tablDbConfig').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablDbConfig');
	} else {
		$('#tablAll').datagrid('options').queryParams = {
			"varName" : varName,
			"opera" : opera,
			"txtValue" : txtValue
		};
		reloadTableCommon_1('tablAll');
	}
	
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

//InnoDB启动选项
function doInnoDBInitTables(moID) {
	 var path = getRootName();
	 $('#tablInnoDBVars')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=InnoDBStartupOptions',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablInnoDBVars').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//MyISAM配置
function doMyIsamInitTables(moID) {
	 var path = getRootName();
	 $('#tablMyISAMConfig')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect: true, // 是否单行选择
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=MyISAMConfig',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablMyISAMConfig').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//字符集设置
function doCharsetInitTables(moID) {
	 var path = getRootName();
	 $('#tablCharsetConfig')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=CharsetConfig',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablCharsetConfig').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//性能参数
function doPerfParamInitTables(moID) {
	 var path = getRootName();
	 $('#tablPerfParameter')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=PerfParameter',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablPerfParameter').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//数据库安装信息
function doInstallInitTables(moID) {
	 var path = getRootName();
	 $('#tablInstallInfo')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=InstallInfo',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablInstallInfo').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//数据库开关
function doDbSwitchInitTables(moID) {
	 var path = getRootName();
	 $('#tablDbSwitch')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=DBSwitch',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablDbSwitch').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//数据库配置
function doDbConfigInitTables(moID) {
	 var path = getRootName();
	 $('#tablDbConfig')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	    singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID+'&varClass=DBConfig',
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tablDbConfig').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//所有
function doAllInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tablAll')
	   .datagrid(
	     {
	    iconCls : 'icon-edit',// 图标
	  	width: 'auto',
		height : 'auto',
		nowrap : false,
	    rownumbers:true,
	    striped : true,
	    border : true,
	   singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
	    collapsible : false,// 是否可折叠的
	    fit : true,// 自动大小
	    fitColumns:true,
		url : path + '/monitor/myManage/findMySQLVarsList?moID='+moID,
		idField : 'fldId',
		pagination : true,// 分页控件
	      columns : [ [
			{
			    field : 'varName',
			    title : '变量名称',
			    width : 200,
			    align : 'left'
			
			}
			,
	        {
		         field : 'varValue',
		         title : '变量值',
		         width : 240,
		         align : 'left'	 
	        }
	        ,
	        {
		         field : 'varChnName',
		         title : '变量中文名称',
		         width : 300,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dynamicVarType',
		         title : '动态系统变量类型',
		         width : 300,
		         align : 'left'
	        }
	       
	        ] ],
			onLoadSuccess: function(){   
		 		//自适应部件大小
		 		window.parent.resizeWidgetByParams(liInfo);
		 	} 
	       
	     });
	 $(window).resize(function() {
		    $('#tablAll').resizeDataGrid(0, 0, 0, 0);
		});
	 }

