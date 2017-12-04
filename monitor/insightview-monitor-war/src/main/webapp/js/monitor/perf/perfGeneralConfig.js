$(document).ready(function() {
	initAlarmRuleVal();
	initCollectPeriodTable();
});

/**
 * 获得告警设置的初始值
 */
function initAlarmRuleVal(){
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/initAlarmRuleVal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
		}
	};
	ajax_(ajax_param);
}

/**
 * 设置告警条件的默认值 
 */
function doSetAlarmRule(){
	var result = checkInfo("#tblAlarmSetting");
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/doSetAlarmRule";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"repeatNum" : $("#ipt_repeatNum").val(),
			"upgradeNum" : $("#ipt_upgradeNum").val(),
			"timeWindow" : $("#ipt_timeWindow").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == true) {
				$.messager.alert("提示", "设置告警条件默认值成功！", "info");
			} else {
				$.messager.alert("错误", "设置告警条件默认值失败！", "error");
			}
		}
	};
	if(result == true){
		ajax_(ajax_param);
	}
}

/**
 * 选择对象类型
 */
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/addDevice/initTree";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"trmnlBrandNm" : "",
				"qyType" : "brandName",
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择对象类型", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].classId;
					var _nameTemp = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_mobjectClassID','"
							+ _id + "','"+ className + "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}

/**
 * 选择隐藏树
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId,className, showVal) {
	//判断是否为叶子节点
	var path = getRootName();
	var uri=path+"/monitor/addDevice/isLeaf";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"classID" : showId,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data){
				if (true == data || "true" == data) {
					$("#" + controlId).val(showVal);
					$("#" + controlId).attr("alt", showId);
					$("#" + divId).dialog('close');
					$("#className").val(className);
					initCollectPeriodVal(className);
				}
			}
		};
		ajax_(ajax_param);
	
}

/**
 * 获得对象的初始默认采集周期
 */
function initCollectPeriodVal(className){
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/initCollectPeriodVal?className="+className;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if(data!= -1){
				$("#ipt_collectPeriod").val(data);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 设置采集周期的默认值 
 */
function doSetCollectPeriod(){
	var result = checkInfo("#tblPerfSetting");
	var className = $("#className").val();
	var collectPeriod = $("#ipt_collectPeriod").val();
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/doSetCollectPeriod?className="+className+"&collectPeriod="+collectPeriod;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == true) {
				$.messager.alert("提示", "设置采集周期默认值成功！", "info");
			} else {
				$.messager.alert("错误", "设置采集周期默认值失败！", "error");
			}
		}
	};
	if(result == true){
		ajax_(ajax_param);
	}
}

/*
 * 页面加载初始化表格 
 */
function initCollectPeriodTable() {
	var path = getRootName();
	$('#tblCollectPeriodLst')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/perfGeneralConfig/listCollectPeriod',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
							doOpenAdd();
							}
						}],
						columns : [ [
								{
									field : 'classLable',
									title : '对象类型',
									width : 120,
								},
								{
									field : 'collectPeriodVal',
									title : '采集周期(min)',
									width : 180,
									align : 'center',
								},
								{
									field : 'ids',
									title : '操作',
									width : 180,
									align : 'center',
									formatter : function(value, row, index) {
										var to = "&quot;"
											+ row.className
											+ "&quot;,&quot;"
											+ row.classLable
											+ "&quot;,&quot;"
											+ row.collectPeriodVal
											+ "&quot;";
										return '<a style="cursor: pointer;" onclick="javascript:doOpenModify('
											+ to
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a>';
									}
								} ] ]
					});
}

/*
 * 更新表格
 */
function reloadTable(){
	reloadTableCommon('tblCollectPeriodLst');
}

/**
 * 打开新增界面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'新增采集周期',
        width:600,
        height : 440,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/perfGeneralConfig/toShowCollectPeriodAdd',
    });
}

var _className = "";
var _classLable = "";
var _collectPeriod = -1;
/**
 * 打开编辑界面
 */
function doOpenModify(className,classLable,collectPeriod){
	_className = className;
	_classLable = classLable;
	_collectPeriod = collectPeriod;
	parent.$('#popWin').window({
    	title:'编辑采集周期',
        width:600,
        height : 440,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/perfGeneralConfig/toShowCollectPeriodModify',
    });
}


