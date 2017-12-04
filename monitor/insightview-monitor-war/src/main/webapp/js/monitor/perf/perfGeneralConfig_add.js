
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
				}
			}
		};
		ajax_(ajax_param);
	
}

function toSetCollectPeriod(){
	var result = checkInfo("#tblPerfSetting");
	var className = $("#className").val();
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/isExitSetting?className="+className;
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
			if (data == true || "true" == data) {
				doSetCollectPeriod();
			} else {
				$.messager.alert("错误", "该对象类型的采集周期已经配置！", "error");
			}
		}
	};
	if(result == true){
		ajax_(ajax_param);
	}
}

/**
 * 设置采集周期的默认值 
 */
function doSetCollectPeriod(){
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
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("错误", "设置采集周期默认值失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}



