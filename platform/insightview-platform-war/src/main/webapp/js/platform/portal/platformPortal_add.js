$(function(){
	initPortalInfos();
});
var mapPortalName = getMap();
var mapPortalCnName = getMap();

//保存视图信息
function doSave(){
	var portalName = $("#portalName").val();
	var portalDesc = $("#portalDesc").val();
	var tabsIndex = $("#tabsIndex").val();
	var portalContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?><portal name=\"homepage\" title=\""+portalDesc+"\"><grid name=\"homegrid\" horizontal=\"5\" vertival=\"5\" basewidth=\"20\" baseheight=\"20\">            </grid></portal>";
	var path = getRootPatch();
	var result = checkInfo('#tblAddPortal');
	if (result == true) {
		var nameIsExist = mapPortalName.get(portalName);
		var cnNameIsExist = mapPortalCnName.get(portalDesc);
		if (nameIsExist != null) {
			$.messager.alert("提示","视图英文名已经存在！","info");
		} else if (cnNameIsExist != null) {
			$.messager.alert("提示","视图中文名已经存在！","info");
		} else {
			var uri = path + "/monitor/gridsterEdit/addPortalInfo";
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"portalName" : portalName,
						"portalDesc" : portalDesc,
						"portalContent" : portalContent,
						"portalType" : 2,
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error"); 
					},
					success : function(data) {
						if(data==true){
							$.messager.alert("提示","视图新增成功！","info");
							$("#popWin").window('close');
							var url = "/platform/platformPortal/showHomePagePortal?portalName="+portalName+"&flag=device";
							window.frames['component_2'].toShowTabsInfo(url,portalDesc,portalName);
						}else{
							$.messager.alert("提示","视图新增失败！","error");
						}
					}
				}
				ajax_(ajax_param);
		}
	}
}

//加载所有视图名称信息
function initPortalInfos() {
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
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			for ( var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].portalId;
				var _name = gtmdlToolList[i].portalName;
				var _parent = 0;
				var _ChName = gtmdlToolList[i].portalDesc;
				if (_ChName != "" && _ChName != null) {
					mapPortalName.put(_name,_id);
					mapPortalCnName.put(_ChName,_id);
				}
			}
		}
	}
	ajax_(ajax_param);
}


//定义简单Map
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
