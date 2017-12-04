$(document).ready(function() {
	var serverid = $("#serverid").val();
	toConfigDomain(serverid)
});

/**
 * 配置管理域
 * 
 */
var _openMenuCount = 0;
function toConfigDomain(serverid) {
		var path = getRootPatch();
		var uri = path + "/monitor/harvesterManage/getDomainTreeVal";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"serverid" : serverid,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				var dataTreeMenu = new dTree("dataTreeMenu", getRootPatch()+ "/plugin/dTree/img/");
//				dataTreeMenu.add(0, -1, "选择管理域", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				//获得已经配置的管理域
				var domainIds = data.domainIds;
//				console.log("domainIds=="+domainIds);
				_treeData = datas;
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].domainId;
					var _nameTemp = gtmdlToolList[i].domainName;
					var _parent = gtmdlToolList[i].parentId;
					
					var domainIdLst = new Array();
					domainIdLst = domainIds.split(",");
					var managedDomainId = gtmdlToolList[i].domainId;
//					console.log("managedDomainId===="+managedDomainId+", 类型："+typeof(managedDomainId+""));
					var isIn = $.inArray(managedDomainId+"", domainIdLst);
//					console.log("isIn==="+isIn);
					if (isIn >= 0) {
//						console.log("isIn=="+isIn);
						var _chkBox = "<input id='"
							+ _id
							+ "' alt='"
							+ _parent
							+ "' type='checkbox' onclick='doEditChkbox("+_id+");' checked=true/>";
					} else {
						var _chkBox = "<input id='"
							+ _id
							+ "' alt='"
							+ _parent
							+ "' type='checkbox' onclick='doEditChkbox("+_id+");' />";
					}
					dataTreeMenu.add(_id, _parent,  _chkBox +_nameTemp,
							"javascript:hiddenDTreeSetVal('selFilterParentId','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataDomainTreeDiv').empty();
				$('#dataDomainTreeDiv').append(dataTreeMenu + "");
				dataTreeMenu.openAll();
			}
		}
		ajax_(ajax_param);
}


/**
 * 修改复选框状态
 * @return
 */
function doEditChkbox(id){
//	console.log("点击的id="+id);
	var obj = document.getElementById(id);
//	console.log("checked="+obj.checked);
	var ids="#"+id;
	 if (obj.checked)
     {
		 $(ids).attr("checked",true);
     }else{
    	 obj.checked=false;
    	 $(ids).attr("checked",false);
     }
}

/**
 * 配置管理域
 * @return
 */
function doConfig(){
	var selDomainId = "";
	$("#dataDomainTreeDiv").find("input").each(function(i, val) {
//		console.log("id="+$(val).attr("id")+"  ，是否被选中："+$(val).attr("checked"));
		if ($(val).attr("checked")) {
			var selId = $(val).attr("id");
			selDomainId +=  selId+"," ;
		}
	});
//	console.log("selDomainId=="+selDomainId)
	var serverId = $("#serverid").val();
	var path = getRootName();
	var uri = path + "/monitor/harvesterManage/addDomain?selDomainId="+selDomainId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"serverId" : serverId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "配置所辖管理域成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "配置所辖管理域失败！！", "error");
				window.frames['component_2'].reloadTable();
			}
		}
	}
	ajax_(ajax_param);
}

/**
 * 关闭页面
 * @return
 */
function closeConfig(){
	$('#popWin').window('close');
	window.frames['component_2'].reloadTable();
}