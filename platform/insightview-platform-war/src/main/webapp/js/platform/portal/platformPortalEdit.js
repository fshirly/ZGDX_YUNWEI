$(function() {
	edit();
	initTree();
});

// 树数据
var _treeData = "";
var map = getMap();
var nameMap = getMap();
var chNameMap = getMap();
var _currentNodeId = -1;
var _currentNodeName = "";

var treeLst = [];
function initTree() {
	var path = getRootPatch();
	var uri = path + "/platform/platformPortalEdit/initPortalTree";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "视图模板",
					"javascript:treeClickAction(0,'视图模板');");
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for ( var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].portalId;
				var _name = gtmdlToolList[i].portalName;
				var _parent = 0;
				var _ChName = gtmdlToolList[i].portalDesc;
				map.put(_id, _name);
				nameMap.put(_name, _id);
				if (_ChName != "" && _ChName != null) {
					chNameMap.put(_ChName, _id);
					dataTree.add(_id, _parent, _ChName,
							"javascript:treeClickAction('" + _id + "','"
									+ _ChName + "');");
				}

			}
			// dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv')
					.append(
							"<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv')
					.append(
							"<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv')
					.append(
							"<input type='button' value='新增' onclick='javascript:toAdd();'/>");
			$('#dataTreeDiv')
					.append(
							"<input type='button' value='编辑' onclick='javascript:toUpdate();'/>");
			$('#dataTreeDiv')
					.append(
							"<input type='button' value='删除' onclick='javascript:doDel();'/>");
			$('#dataTreeDiv')
					.append(
							"<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");

			// 操作tree对象
			// dataTree.openAll();
			// 物理主机是7，_relationID=6

			doInitPortal();
		}
	}
	ajax_(ajax_param);
}

// 新增视图节点
function toAdd() {
	$(".input").val("");
	$("#btnSave2").unbind("click");
	$("#btnSave2").bind("click", function() {
		doAdd();
	});
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('#divDataPortal').dialog('close');
	});

	$('#divDataPortal').dialog('open');
}

function doAdd() {
	var result = checkInfo('#tblEditPortal');
	if (result == true) {
		var enName = nameMap.get($("#ipt_portalName").val());
		var chName = chNameMap.get($("#ipt_portalDesc").val());
		if (enName != null) {
			$.messager.alert("提示", "该节点英文名已经存在！", "info");
		} else {
			if (chName != null) {
				$.messager.alert("提示", "该节点中文名已经存在！", "info");
			} else {
				var portalContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?><portal name=\"homepage\" title=\""+$("#ipt_portalDesc").val()+"\"><grid name=\"homegrid\" horizontal=\"5\" vertival=\"5\" basewidth=\"20\" baseheight=\"20\">               </grid></portal>";
				var path = getRootName();
				var uri = path + "/platform/platformPortalEdit/addPortalTree";
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"portalName" : $("#ipt_portalName").val(),
						"portalDesc" : $("#ipt_portalDesc").val(),
						"portalContent" : portalContent,
						"portalType" : "2",
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data) {
							$('#divDataPortal').dialog('close');
							initTree();
						} else {
							$.messager.alert("提示", "视图节点新增失败！", "error");
						}

					}
				}
				ajax_(ajax_param);
			}
		}

	}

}

// 删除节点
function doDel() {
	$.messager.confirm("提示", "确定删除此节点？", function(r) {
		if (r == true) {
			var path = getRootName();
			var uri = path + "/platform/platformPortalEdit/deletePortalTree";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"portalId" : _currentNodeId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data == true) {
						initTree();
					} else {
						$.messager.alert("提示", "该节点删除失败！", "error");
					}
				}
			}
			ajax_(ajax_param);
		}
	});

}

// 编辑树节点
function toUpdate() {
	$(".input").val("");
	doInitTreeInfo(_currentNodeId);
	$("#btnSave2").unbind("click");
	$("#btnSave2").bind("click", function() {
		doUpdate(_currentNodeId);
	});
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('#divDataPortal').dialog('close');
	});

	$('#divDataPortal').dialog('open');
}

// 初始化节点数据
function doInitTreeInfo(portalId) {
	var path = getRootName();
	var portalName = map.get(portalId);
	var uri = path + "/platform/platformPortalEdit/showPortalTreeInfo";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : portalName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$('#ipt_portalName').val(data.portalName);
			$("#ipt_portalDesc").val(data.portalDesc);
		}
	}
	ajax_(ajax_param);

}

function doUpdate(portalId) {
	var path = getRootName();
	var uri = path + "/platform/platformPortalEdit/modifyPortalTree";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalId" : portalId,
			"portalName" : $("#ipt_portalName").val(),
			"portalDesc" : $("#ipt_portalDesc").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data) {
				$('#divDataPortal').dialog('close');
				initTree();
			} else {
				$.messager.alert("提示", "视图节点更新失败！", "error");
			}

		}
	}
	ajax_(ajax_param);

}

/**
 * 保存视图模板
 * 
 * @return
 */
function save() {
	var portalName = $('#ipt_portalName').val();
	if (portalName == "") {
		var newPortalName = $('#portalName').val();
		var path = getRootName();
		var uri = path + "/monitor/gridsterEdit/addPortalInfo";
		var ajax_param = {
			url : uri,
			type : "post",
			dateType : "json",
			data : {
				"portalContent" : $("#ipt_portalContent").val(),
				"portalName" : newPortalName,
				"portalType" : 2,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (data == true) {
					$.messager.alert("提示", "视图配置成功！", "info");
				} else {
					$.messager.alert("提示", "视图配置失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	} else {
		var path = getRootName();
		var uri = path + "/monitor/gridsterEdit/modifyPortalInfo";
		var ajax_param = {
			url : uri,
			type : "post",
			dateType : "json",
			data : {
				"portalName" : portalName,
				"portalContent" : $("#ipt_portalContent").val(),
				"portalDesc" : $('#portalDesc').val(),
				"portalType" : 2,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (data == true) {
					$.messager.alert("提示", "视图配置成功！", "info");
				} else {
					$.messager.alert("提示", "视图配置失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	}

}

function doBack() {
	var portalName = $('#ipt_portalName').val();
	if (portalName == '') {
		$("#ipt_portalContent").val("<?xml version='1.0' encoding='UTF-8'?>");
	} else {
		var path = getRootPatch();
		var uri = path + "/monitor/gridsterEdit/showOnePortal";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"portalName" : portalName,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (data == "") {
					$('#ipt_portalName').val("");
					$("#ipt_portalContent").val(
							"<?xml version='1.0' encoding='UTF-8'?>");
				} else {
					$('#ipt_portalName').val(portalName);
					$("#ipt_portalContent").val(data.portalContent);
				}

			}
		}
		ajax_(ajax_param);
	}

}

function toShowWidget() {
	var path = getRootName();
	var uri = path + "/platform/platformPortalEdit/toShowWidget?portalId="+_currentNodeId;
	var iWidth = 1300; // 弹出窗口的宽度;
	var iHeight = 700; // 弹出窗口的高度;
	var iTop = (window.screen.availHeight - 60 - iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 20 - iWidth) / 2; // 获得窗口的水平位置;
	window.open(uri,"","height="+ iHeight+ ",width="+ iWidth+ ",left="+ iLeft+ ",top="+ iTop+ ",resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

function edit() {
	var radioChecked = $(':radio:checked');
	if (radioChecked.val() === '1') {
		$('#divPortalInfo').show();
		$('#divUploadPortal').hide();
	}
	if (radioChecked.val() === '2') {
		$('#divPortalInfo').hide();
		$('#divUploadPortal').show();
	}
}

/**
 * 文件上传
 * 
 */
 function doupload11() {
	 var path=getRootName();
    // var form = $("#fm").serializeObject();
	 var portalName = $('#ipt_portalName').val();
     $.ajaxFileUpload({
			fileElementId : 'file',
			type:"post",
			url : path+"/monitor/gridsterEdit/uploadPortalInfo",
			dataType : 'json',
			data:{portalName:portalName},
			//contentType : "application/json",
			secureuri : false,
			fileFilter:"jsp",
			beforeSend : function() {
				if(!portalName){
					$.messager.alert("提示", "请选择用户");
					return false;
				}
				if(!$("#file").val()){
					$.messager.alert("提示", "请选择文件");
					return false;
				}
				return true;
			},
			success : function(data, status) {
				$.messager.alert("提示","上传成功！");
			},
			error : function(data, status, e) {
				$.messager.alert("提示","上传失败！");
			}
		});
 }
/*function doUploadFile() {
	var portalName = $('#ipt_portalName').val();
	var filePath = $('#ipt_fileupload').val();
	var path = getRootName();
	var uri = path + "/monitor/gridsterEdit/uploadPortalInfo";
	var ajax_param = {
		url : uri,
		type : "post",
		dateType : "json",
		data : {
			"filePath" : filePath,
			"portalName" : portalName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == true) {
				$.messager.alert("提示", "文件上传成功！", "info");
			} else {
				$.messager.alert("提示", "文件上传失败！", "error");
			}
		}
	};
	ajax_(ajax_param);

}*/

/**
 * 上传前验证
 * 
 * @param imgFile
 * @return
 */
function initFile(file) {
	var passfix = file.value.substring(file.value.lastIndexOf(".") + 1,
			file.value.length);
	if (passfix != "xml") {
		$.messager.alert("提示", "对不起，系统仅支持XML" + "的文件，请您调整格式后重新上传，谢谢 !", "info");
	}
}

/*
 * 上传过后的回调
 * 
 * @param imgFile
 */
function uploadCallBack(fileObj) {
	$("#" + fileObj.uploadTagId).val(fileObj.filePath);
}

function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	reloadTable();
}

/*
 * 更新界面
 */
function reloadTable() {
	doInitPortal();
}

// 菜单定位
function toSerach(treedata) {
	var treeName = $("#treeName").val();
	if (treeName == "") {
		treeClickAction('2', '设备');
	} else {
		var path = getRootName();
		var uri = path + "/platform/platformPortalEdit/searchTreeNodes";
		var ajax_param = {
			url : uri,
			type : "post",
			dateType : "json",
			data : {
				"portalDesc" : treeName,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (data != null && data != "") {
					if(data.portalIds.indexOf(",") >= 0){
						var treeIds=data.portalIds.split(",");
						for ( var int = 1; int < treeIds.length; int++) {
							var treeId=treeIds[int];
							for ( var int2 = 0; int2 < treeLst.length; int2++) {
								if(treeId==treeLst[int2]){
									treedata.openTo(treeId,true);
									_currentNodeId = treeId;
									break;
								}
							}
							
						}
					}else{
						var treeIds=data.portalIds;
						treedata.openTo(treeIds,true);
						_currentNodeId = treeIds;
					}
					
					reloadTable();
				}
			}
		};
		ajax_(ajax_param);
	}

}

// 加载某一个portal模板
function doInitPortal() {
	var portalName = map.get(_currentNodeId);
	
	/*
	 * if(portalName == 'Interface'){ var portalParentName =
	 * map.get(_currentParnetId); portalName = portalParentName+"/"+portalName; }
	 * $('#portalName').val(portalName); $("#ipt_ifName").val("");
	 */
	 $('#portalName').val(portalName);
	var path = getRootPatch();
	var uri = path + "/monitor/gridsterEdit/showOnePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : portalName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == "") {
				$('#ipt_portalName').val(portalName);
				$("#ipt_portalContent").val(
						"<?xml version='1.0' encoding='UTF-8'?>");
			} else {
				$('#portalDesc').val(data.portalDesc);
				$('#ipt_portalName').val(portalName);
				$("#ipt_portalContent").val(data.portalContent);
			}

		}
	}
	ajax_(ajax_param);

}

// 定义简单Map
function getMap() {// 初始化map_,给map_对象增加方法，使map_像Map
	var map_ = new Object();
	map_.put = function(key, value) {
		map_[key + '_'] = value;
	};
	map_.get = function(key) {
		return map_[key + '_'];
	};
	map_.remove = function(key) {
		delete map_[key + '_'];
	};
	map_.keyset = function() {
		var ret = "";
		for ( var p in map_) {
			if (typeof p == 'string' && p.substring(p.length - 1) == "_") {
				ret += ",";
				ret += p.substring(0, p.length - 1);
			}
		}
		if (ret == "") {
			return ret.split(",");
		} else {
			return ret.substring(1).split(",");
		}
	};
	return map_;
}


//预览视图
function previewPortal(){
	var portalName = $('#ipt_portalName').val();
	var path=getRootName();
	var uri=path+"/platform/platformPortal/initPortalContent";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"portalContent":$("#ipt_portalContent").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");  
	},
	success:function(data){
		if(data==true){
			var urlParams = $('#urlParams').val();
			if(urlParams != ''){
				urlParams = "&"+urlParams;
			}
			var uri=path+"/platform/platformPortal/showPortalView?moType=platform"+urlParams;
			
			var iWidth = window.screen.availWidth -210; //弹出窗口的宽度190
			var y = window.screen.availHeight-102;
			var y2 = y-29;
			var iHeight = window.screen.availHeight-265; //弹出窗口的高度;
			var iTop = 165;
//				(window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = 188;
//				(window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
		}else{
			$.messager.alert("提示","视图格式不对！","error");
		}
	}
	};
		ajax_(ajax_param);
		
	
}