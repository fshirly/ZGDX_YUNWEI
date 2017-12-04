var path = getRootName();
var manuData = [];
var resCateData;
var manuMap = getMap();
var resCateMap;
var usedManuCateLst = [];
var manuId="";
var cateId="";
$(document).ready(function() {
	if (!Array.prototype.indexOf)
	{
	  Array.prototype.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	$("#deviceConfigRangeDiv").hide();
	$("#othersConfigRangeDiv").hide();
	toGetAllManu();
});

/*
 * 选择对象类型
 */
var treeLst=[];
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/sysMo/initTree";
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
					dataTreeOrg.add(_relationID, _newParentID, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_moClassID','"
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

function toAddOthersRange(){
	var mid = $("#ipt_mid").val();
	var moClassId = $('#ipt_moClassID').attr("alt");
	var uri=path+"/monitor/sysMo/addMoClassRelation";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"mid":mid,
				"moClassId":moClassId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data==true){
			$.messager.alert("提示","监测器适用范围配置成功！","info");
			$('#popWin').window('close');
			window.frames['component_2'].reloadTable();
		}else{
			
		}
	}
	};
	ajax_(ajax_param);
}

function doInitTable(mid){
	var path = getRootName();
	$('#tblSysMoManufactureList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 780,
						height : 250,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : path + '/monitor/sysMo/listSysManufactureByMid?mid='+mid,
						remoteSort : false,
						idField : 'id',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						rownumbers : true,// 行号
						toolbar : [ 
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
						        	field : 'id',
						        	checkbox : true
						        },
								{
									field : 'resManufacturerName',
									title : '厂商',
									width : 200,
									align : 'center',
								},
								{
									field : 'resCategoryName',
									title : '型号',
									width : 280,
									align : 'center'
								},
								{
									field : 'mids', 
									title : '操作',
									width : 300,
									align : 'center',
									formatter : function(value, row, index) {
										var to = "&quot;" + row.id
										+ "&quot;,&quot;" + row.resManufacturerId
										+ "&quot;,&quot;" + row.resCategoryId
										+ "&quot;"
										return '<a style="cursor: pointer;" onclick="javascript:doDel('
										+ to
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
						}
								} ] ]
					});
//    $(window).resize(function() {
//        $('#tblSysMoManufactureList').resizeDataGrid(0, 0, 0, 0);
//    });

}


/**
 * 加载所有厂商
 */



/**
 * 加载所有
 */


//配置适用范围
function toConfigRange(){

	var result = checkInfo('#tblAddSysMoInfo');
	if (result == true) {
		var moName = $("#ipt_moName").val();
		var moClass = $("#ipt_moClassPre").val()+$("#ipt_moClass").val();
		var monitorTypeID = $("#ipt_monitorTypeName").combobox('getValue');
		var monitorProperty = $("#ipt_moProperty").combobox('getValue');
		var doIntervals = $("#ipt_doIntervals").val();
		var unitValue = $("#unit option:selected").val();
		if (doIntervals == "") {
			doIntervals = "20";
		}
		var moDescr = $("#ipt_moDescr").val();
		
//		if (moClass.indexOf("Job") != 0 || moClass.charCodeAt(0) != "J".charCodeAt(0)) {
//			$.messager.alert("提示","监测器调度类名格式错误!以Job开头，例如:JobSnmpCpu","info");
//		} else {

			var uri=path+"/monitor/sysMo/checkMoClass";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"moClass":moClass,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data > 0){
					$.messager.alert("提示","监测器调度类名重复！","info");
				}else{
					var uri=path+"/monitor/sysMo/addMoInfo";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"moName":moName,
								"moClass":moClass,
								"monitorTypeID":monitorTypeID,
								"monitorProperty":monitorProperty,
								"doIntervals":doIntervals,
								"moDescr":moDescr,
								"timeUnit":unitValue,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data != -1){
							window.frames['component_2'].reloadTable();
							$("#ipt_mid").val(data);
							var monitorProperty = $("#ipt_moProperty").combobox('getValue');
							if (monitorProperty == 0) {
								$("#deviceConfigRangeDiv").show();
								$("#addSysMoInfoDiv").hide();
//								$('#ipt_moResManufacturerName').autocomplete(manuData).result(function(event, data, formatted) {
//									$("#ipt_moResManufacturerId").val(manuMap.get($('#ipt_moResManufacturerName').val()));
//									$("#ipt_moResCategoryName").val("");
//									initCate();
//								});
								doInitTable($("#ipt_mid").val());
							} else {
								$("#othersConfigRangeDiv").show();
								$("#addSysMoInfoDiv").hide();
							}
						}else{
							$.messager.alert("提示","新增监测器失败！","error");
						}
					}
					};
					ajax_(ajax_param);
				}
			}
			};
			ajax_(ajax_param);
		
//		}
	}

}


//新增
function toAdd(){
	var result = checkInfo('#tblAddSysMoInfo');
	if (result == true) {
		var moName = $("#ipt_moName").val();
		var moClass = $("#ipt_moClassPre").val()+$("#ipt_moClass").val();
		var monitorTypeID = $("#ipt_monitorTypeName").combobox('getValue');
		var monitorProperty = $("#ipt_moProperty").combobox('getValue');
		var doIntervals = $("#ipt_doIntervals").val();
		if (doIntervals == "") {
			doIntervals = 20;
		}
		var unitValue = $("#unit option:selected").val();
		var moDescr = $("#ipt_moDescr").val();
//		if (moClass.indexOf("Job") != 0 || moClass.charCodeAt(0) != "J".charCodeAt(0)) {
//			$.messager.alert("提示","监测器调度类名格式错误!以Job开头，例如:JobSnmpCpu","info");
//		} else {
			if (monitorTypeID == "" || monitorTypeID == null) {
				$.messager.alert("提示","监测器类型不能为空！","info");
			} else {
				var uri=path+"/monitor/sysMo/checkMoClass";
				var ajax_param={
						url:uri,
						type:"post",
						dateType:"json",
						data:{
							"moClass":moClass,
							"t" : Math.random()
				},
				error:function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if(data > 0){
						$.messager.alert("提示","监测器调度类名重复！","info");
					}else{
						var uri=path+"/monitor/sysMo/addMoInfo";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"moName":moName,
									"moClass":moClass,
									"monitorTypeID":monitorTypeID,
									"monitorProperty":monitorProperty,
									"doIntervals":doIntervals,
									"moDescr":moDescr,
									"timeUnit":unitValue,
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data != -1){
								$("#ipt_mid").val(data);
								$.messager.alert("提示","新增监测器成功！","info");
								$('#popWin').window('close');
								window.frames['component_2'].reloadTable();
							}else{
								$.messager.alert("提示","新增监测器失败！","error");
							}
						}
						};
						ajax_(ajax_param);
					}
				}
				};
				ajax_(ajax_param);
			}
			
//		}
	}
}


function toAddManuCate(){
	var mid = $("#ipt_mid").val();
	var resManufacturerId = $("#ipt_moResManufacturerId").val();  
	var resCategoryId =  $("#ipt_moResCategoryId").val();
	var manuName = $("#ipt_moResManufacturerName").val();  
	var cateName = $("#ipt_moResCategoryName").val();
	if(manuName != null && manuName != ""){
		manuId = manuMap.get(manuName);
	}
	if(cateName != null && cateName != ""){
		cateId = resCateMap.get(cateName);
	}else{
		cateId = null;
	}
	var manuCateId = manuId+"#"+cateId;
	if (manuId == null || manuId == "") {
		$.messager.alert("提示","厂商不能为空！","info");
	}else if (usedManuCateLst.indexOf(manuCateId) >= 0) {
		$.messager.alert("提示","厂商型号重复！","info");
	}else {
		var uri=path+"/monitor/sysMo/addMoManuCateRelation";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"mid":mid,
					"resManufacturerId":manuId,
					"resCategoryId":cateId,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data==true){
				usedManuCateLst.push(manuId+"#"+cateId);
				doInitTable(mid);
			}
		}
		};
		ajax_(ajax_param);
	}
}

function doDel(id,resManufacturerId,resCategoryId){
	var uri=path+"/monitor/sysMo/toDelManuCateById";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"id":id,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data==true){
			doInitTable($("#ipt_mid").val());
			usedManuCateLst.remove(resManufacturerId+"#"+resCategoryId);
			$.messager.alert("提示","监测器适用范围删除成功！","info");
		}else{
			$.messager.alert("提示","监测器适用范围删除失败！","info");
		}
	}
	};
	ajax_(ajax_param);
}

function doBatchDel(){
	var rows = $('#tblSysMoManufactureList').datagrid('getSelections');
	var checkedItems = $('[name=id]:checked');
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
		var uri=path+"/monitor/sysMo/toDelManuCateByIds";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"ids":ids,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data==true){
				doInitTable($("#ipt_mid").val());
				for(var i=0; i<rows.length; i++){
					usedManuCateLst.remove(rows[i].resManufacturerId+"#"+rows[i].resCategoryId);
				}
				$.messager.alert("提示","监测器适用范围删除成功！","info");
			}else{
				$.messager.alert("提示","监测器适用范围删除失败！","info");
			}
		}
		};
		ajax_(ajax_param);
	}
	
}

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
			$('#ipt_moResManufacturerName').autocomplete(manuData).result(function(event, data, formatted) {
				$("#ipt_moResManufacturerId").val(manuMap.get($('#ipt_moResManufacturerName').val()));
				$("#ipt_moResCategoryName").val("");
				initCate();
			});
		}
	}
	};
	ajax_(ajax_param);

}


//根据厂商获取型号
function toGetResCateByManuId(){
	var resManufacturerID = $("#ipt_moResManufacturerId").val();
	var manuName = $("#ipt_moResManufacturerName").val();  
	if(manuName != null && manuName != ""){
		manuId = manuMap.get(manuName);
	}else{
		manuId = 0;
	}
//	if(resManufacturerID == "" || resManufacturerID == null){
//		resManufacturerID = 0;
//	}
	var uri=getRootName()+"/monitor/sysMo/listResCateInfos";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"resManufacturerID" : manuId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		resCateData = [];
		resCateMap = getMap();
		$("#ipt_moResCategoryName").unautocomplete();
		if(data.length > 0){
			for ( var int = 0; int < data.length; int++) {
				resCateData.push(data[int].split(";")[1]);
				resCateMap.put(data[int].split(";")[1]+"",data[int].split(";")[0]);
			}
			$('#ipt_moResCategoryName').autocomplete(resCateData,{cacheLength:1}).result(function(event, data, formatted) {
				$("#ipt_moResCategoryId").val(resCateMap.get($('#ipt_moResCategoryName').val()));
			});
		}
	}
	};
	ajax_(ajax_param);

}

function initCate(){
	toGetResCateByManuId();
//	$('#ipt_moResCategoryName').autocomplete(resCateData).result(function(event, data, formatted) {
//		console.log("resCateData 222 === "+resCateData);
//		$("#ipt_moResCategoryId").val(resCateMap.get($('#ipt_moResCategoryName').val()));
//	});
}

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