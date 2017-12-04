/**
 * 选择部门树
 */
function choseDeptTree() {
	initDeptTree();
	$('#divChoseDept').dialog('open');
}
/**
 * 部门树
 */
var _initDeptCount = 0;
function initDeptTree() {
	if (0 == _initDeptCount) {
		++_initDeptCount;
		var path = getRootPatch();
		var uri = path + "/permissionDepartment/findDeptLst";
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
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择部门", "");

				// 得到树的json数据源
				var datas = eval('(' + data.deptLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].deptId;
					var _nameTemp = gtmdlToolList[i].deptName;
					var _parent = gtmdlToolList[i].parentDeptID;

					dataTreeOrg.add(_id, _parent, _nameTemp,
							"javascript:hiddenDeptDTreeSetValEasyUi('divChoseDept','ipt_deptId','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataDeptTreeDiv').empty();
				$('#dataDeptTreeDiv').append(dataTreeOrg + "");
			}
		}
		ajax_(ajax_param);
	}
}

function hiddenDeptDTreeSetValEasyUi(divId,controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	cancelRedBox(controlId);
}

/**
 * 分配到部门
 * @return
 */
function toAssign(){
	var result = checkInfo("#tblSubnetInfo");
	var deptId = $("#ipt_deptId").attr("alt");
	var subNetIdStr = $("#subNetId").val();
	var subNetId =parseInt(subNetIdStr);
	if(deptId == null || deptId == ""){
		$.messager.alert("提示", "请选择部门！", "info");
	}else{
		var ids = $("#ids").val();
		var path = getRootName();
		var uri = path + "/platform/subnetViewManager/doFreeAssignDept?ids="+ids;
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deptId" : deptId,
				"subNetId" :subNetId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (data == true) {
					$.messager.alert("提示", "分配到部门成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].frames['component_2'].reloadTable();
					window.frames['component_2'].initTree();
				} else {
					$.messager.alert("提示", "分配到部门失败！", "error");
				}
			}
		};
		if(result == true){
			ajax_(ajax_param);
		}
	}
}