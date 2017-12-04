/*
 * 选择对象类型
 */
var treeLst=[];
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/perfKPIDef/initTree";
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
					var _classId = gtmdlToolList[i].classId;
					var _classLable = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var _newParentID = gtmdlToolList[i].newParentID;
					var _relationID = gtmdlToolList[i].relationID;
					var _relationPath = gtmdlToolList[i].relationPath;
					var className = gtmdlToolList[i].className;
					treeLst.push(_relationID);
//					console.log("_id==="+_id+",_nameTemp==="+_nameTemp+",_parent==="+_parent);
					dataTreeOrg.add(_relationID, _newParentID, _classLable, "javascript:hiddenDTreeSetValEasyUi('divMObject','ipt_classID','"
							+ _classId + "','" + _classLable + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}


function toAdd(){
	var result = checkInfo("#tblAddPerfKPIDef") 
	if(result == true){
		checkBeforeAdd();
	}
}

/*
 * 验证SNMP验证中该设备是否存在
 */
function checkBeforeAdd() {
	var path = getRootName();
	var uri = path + "/monitor/perfKPIDef/checkPerKPIName?flag=add";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"name" : $("#ipt_name").val(),
			"enName" : $("#ipt_enName").val(),
			"kpiCategory" : $("#ipt_kpiCategory").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data.checkResult || "true" == data.checkResult) {
				addPerfKPIDef();
				return;
			} else if (0 == data.isNameOrEName || "0" == data.isNameOrEName) {
				$.messager.alert("提示", "该指标名称已存在！", "error");
			}else if (1 == data.isNameOrEName || "1" == data.isNameOrEName) {
				$.messager.alert("提示", "该指标英文名已存在！", "error");
			}else if (2 == data.isNameOrEName || "2" == data.isNameOrEName) {
				$.messager.alert("提示", "该指标名称和英文名已存在！", "error");
			}
		}
	};
	ajax_(ajax_param);
}


/*
 * 新增指标
 */
function addPerfKPIDef(){
		var path = getRootName();
		var uri = path + "/monitor/perfKPIDef/addPerfKPIDef";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"kpiCategory" : $("#ipt_kpiCategory").val(),
				"classID" :$("#ipt_classID").attr("alt"),
				"name" : $("#ipt_name").val(),
				"enName" : $("#ipt_enName").val(),
				"quantifier" : $("#ipt_quantifier").val(),
				"valueType" : $("#ipt_valueType").val(),
				"amountType" : $("#ipt_amountType").val(),
				"processFlag" : 1,
				"valueRange" : $("#ipt_valueRange").val(),
				"note" : $("#ipt_note").val(),
//				"isSupport":$("#ipt_isSupport").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "采集指标添加成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
					window.frames['component_2'].getAllCategory();
				} else {
					$.messager.alert("提示", "采集指标添加失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
}
