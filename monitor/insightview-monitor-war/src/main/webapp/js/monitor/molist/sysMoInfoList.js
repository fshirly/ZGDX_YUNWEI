var manuData = [];
var resCateData = [];
var manuMap = getMap();
var resCateMap = getMap();
$(document).ready(function() {
	doInitTable();
	$('#tblSysMoInfoList').datagrid('hideColumn','timeUnit');
//	console.log($("#moClassTd"));
	$("#moClassTd").hide();
	$("#resManuTd").hide();
	$("#resCateTd").hide();
	$('#txtMoProperty').combobox({   
         //相当于html >> select >> onChange事件   
         onSelect:function(rec){
			if(rec.value == 0){
				$("#resManuTd").show();
				$("#resCateTd").show();
				$("#moClassTd").hide();
			}else{
				$("#moClassTd").show();
				$("#resManuTd").hide();
				$("#resCateTd").hide();
			}
	}
    })  
	toGetAllManu();
	initCate();
});

function initCate(){
	toGetResCateByManuId();
}
/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysMoInfoList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/sysMo/listSysMoInfo',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'mid',
						singleSelect : false,// 是否单选
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
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						}
						],
						
						columns : [ [
						        {
						        	field : 'mid',
						        	checkbox : true
						        },
								{
									field : 'moName',
									title : '监测器名称',
									width : 80,
									align : 'center',
								},
								{
									field : 'moClass',
									title : '监测器调度类名',
									width : 130,
									align : 'center'
								},
								{
									field : 'monitorTypeName',
									title : '监测器类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'doIntervals',
									title : '默认采集周期',
									width : 60,
									formatter : function(value, row, index) {
										var timeUnitVal = "分";
										if(value == null || value == "null"){
											return "";
										}else{
											if(row.timeUnit == 1){
												timeUnitVal = "分";
											}else if(row.timeUnit == 2){
												timeUnitVal = "时";
											}else if(row.timeUnit == 3){
												timeUnitVal = "天";
											}
											return value+"("+timeUnitVal+")"
										}
										
									},
									align : 'center'
								},
								{
									field : 'timeUnit',
									title : '时间单位',
									width : 80,
									align : 'center'
								},
								{
									field : 'resManufacturerName',
									title : '厂商',
									width : 80,
									align : 'center'
								},
								{
									field : 'resCategoryName',
									title : '型号',
									width : 80,
									align : 'center'
								},
								{
									field : 'moClassLable',
									title : '监测对象类型',
									width : 60,
									align : 'center'
								},
								{
									field : 'mids', 
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
									return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
									+ row.mid
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
									+ row.mid
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
									+ row.mid
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
						}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysMoInfoList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
//	console.log($("#txtMoProperty").combobox('getValue'));
	var moProperty = $("#txtMoProperty").combobox('getValue');
	var resManufacturerName = $("#txtMoResManufacturerName").val();
	var resCategoryName =  $("#txtMoResCategoryName").val();
	var moClassLable = $("#txtMoClassName").val();
	if (moProperty == 0) {
		moClassLable = null;
	} else {
		resManufacturerName = null;
		resCategoryName = null;
	}
	$('#tblSysMoInfoList').datagrid('options').queryParams = {
		"monitorTypeName" : $("#txtMoType").combobox('getText'),
		"moName" : $("#txtMoName").val(),
		"monitorProperty" : moProperty,
		"resManufacturerName" : resManufacturerName,
		"resCategoryName" : resCategoryName,
		"moClassLable" : moClassLable
	};
	reloadTableCommon_1('tblSysMoInfoList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}


/**
 * 打开编辑页面
 * @return
 */
function doOpenAdd(){
//	 查看配置项页面
	parent.$('#popWin').window({
    	title:'新增监测器信息',
        width:800,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMo/toShowAddMoInfoView'
    });
	
//	var options = {};
//	options.url = getRootName() + '/monitor/sysMo/toShowAddMoInfoView';
//    var opts = $.extend({
//        title:'新增监测器信息',
//        width : 800,
//        height : 450,
//        modal : true,
//        minimizable:false,
//        maximizable:false,
//        collapsible:false,
//        onClose : function() {
//            $(this).dialog('destroy');
//        }
//    }, options);
//    opts.modal = true;
//    if (options.url) {
//        opts.content = '<iframe id="" src="' + options.url + '" allowTransparency="true" scrolling="auto" width="90%" height="80%" frameBorder="0" name=""></iframe>';
//    }
//    parent.$('<div/>').dialog(opts).dialog("center");	
	
}


function doOpenModify(mid){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'修改监测器信息',
        width:700,
        height:420,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMo/toShowModifyMoInfoView?mid='+mid
    });
}

function doOpenDetail(mid){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'查看监测器信息',
        width:700,
        height:420,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMo/toShowMoInfoDetail?mid='+mid
    });
}

//获取所有厂商
function toGetAllManu(){
	var uri=getRootName()+"/monitor/sysMo/listSysManufactureInfos";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data != null){
			for ( var int = 0; int < data.length; int++) {
				manuData.push(data[int].split(";")[1]);
				manuMap.put(data[int].split(";")[1]+"",data[int].split(";")[0]);
			}
			$('#txtMoResManufacturerName').autocomplete(manuData).result(function(event, data, formatted) {
				$("#txtMoResManufacturerID").val(manuMap.get($('#txtMoResManufacturerName').val()));
				initCate();
		    });
		}
	}
	};
	ajax_(ajax_param);

}

//根据厂商获取型号
function toGetResCateByManuId(){
	var resManufacturerID = $("#txtMoResManufacturerID").val();
	if(resManufacturerID == "" || resManufacturerID == null){
		resManufacturerID = 0;
	}
	var uri=getRootName()+"/monitor/sysMo/listResCateInfos";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"resManufacturerID" : resManufacturerID,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data != null){
			for ( var int = 0; int < data.length; int++) {
				resCateData.push(data[int].split(";")[1]);
				resCateMap.put(data[int].split(";")[1]+"",data[int].split(";")[0]);
			}
			$('#txtMoResCategoryName').autocomplete(resCateData).result(function(event, data, formatted) {
				$("#txtMoResCategoryID").val(resCateMap.get($('#txtMoResCategoryName').val()));
			});
		}
	}
	};
	ajax_(ajax_param);

}


//定义简单Map
function getMap() {// 初始化map_,给map_对象增加方法，使map_像Map
         var map_ = new Object();     
         map_.put = function(key, value) {     
             map_[key+'_'] = value;     
         };     
         map_.get = function(key) {     
             return map_[key+'_'];     
         };     
         map_.remove = function(key) {     
             delete map_[key+'_'];     
         };     
         map_.keyset = function() {     
             var ret = "";     
             for(var p in map_) {     
                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {     
                     ret += ",";     
                     ret += p.substring(0,p.length-1);     
                 }     
             }     
             if(ret == "") {     
                 return ret.split(",");     
             } else {     
                 return ret.substring(1).split(",");     
             }     
         };     
         return map_;     
}  


/*
 * 选择对象类型
 */
var treeLst=[];
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/alarmmgr/moKPIThreshold/initTree";
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
					var _newParentID = gtmdlToolList[i].newParentID;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
					var _relationID = gtmdlToolList[i].relationID;
					var _relationPath = gtmdlToolList[i].relationPath;
					treeLst.push(_relationID);
//					console.log("_id==="+_id+",_nameTemp==="+_nameTemp+",_parent==="+_parent);
					dataTreeOrg.add(_relationID, _newParentID, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','txtMoClassName','"
							+ _id + "','" + _parent +"','" + _relationPath + "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}

/*
 * 选择隐藏树
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId,parentId,relationPath, showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
}

function doDel(mid){
	$.messager.confirm("提示","确定删除此监测器？",function(r){
		if (r == true) {
			var uri=getRootName()+"/monitor/sysMo/existMoOfPerfTask";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"mids":mid,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示", "请先删除监测器关联的监测任务！", "info");
				}else{
					var uri=getRootName()+"/monitor/sysMo/toDelMoInfoById";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"mids":mid,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data == true){
							$.messager.alert("提示", "监测器删除成功！", "info");
							reloadTable();
						}else{
							$.messager.alert("提示", "监测器删除失败！", "info");
						}
					}
					};
					ajax_(ajax_param);
				}
			}
			};
			ajax_(ajax_param);
		}
	});
}


function doBatchDel(){
	var checkedItems = $('[name=mid]:checked');
	var ids = null;
	if (checkedItems.length == 0) {
		$.messager.alert('提示', '没有任何选中项!', 'info');
	} else {
		$.each(checkedItems, function(index, item) {
			if (null == ids) {
				ids = $(item).val();
			} else {
				ids += ',' + $(item).val();
			}
		});
		if(null != ids){
			var uri=getRootName()+"/monitor/sysMo/existMoOfPerfTask";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"mids":ids,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示", "请先删除监测器关联的监测任务！", "info");
				}else{
					var uri=getRootName()+"/monitor/sysMo/toDelMoInfoById";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"mids":ids,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data == true){
							$.messager.alert("提示", "监测器删除成功！", "info");
							reloadTable();
						}else{
							$.messager.alert("提示", "监测器删除失败！", "info");
						}
					}
					};
					ajax_(ajax_param);
				}
			}
			};
			ajax_(ajax_param);
		}
	}

}