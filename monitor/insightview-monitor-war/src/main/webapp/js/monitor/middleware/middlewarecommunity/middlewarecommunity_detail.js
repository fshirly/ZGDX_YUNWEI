$(document).ready(function() {
	initCommunityDetail();
});

/*
 * 初始化指标信息
 */
function initCommunityDetail(){
	var id=$('#id').val();
	var path=getRootName();
	var uri=path+"/monitor/MiddleWareCommunity/initCommunityDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"id":id,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"lbl");
				$("#lbl_deviceIp").text(data.ipAddress);
				var middleWareName = data.middleWareName;
				if (middleWareName == 1) {
					$("#lbl_middleWareName").text('weblogic');
					$("#isShowUserAndPwd").show();
				}else  if(middleWareName == 2){
					$("#lbl_middleWareName").text('tomcat');
					$("#isShowUserAndPwd").hide();
				}else  if(middleWareName ==3){
					$("#lbl_middleWareName").text('websphere');
					$("#isShowUserAndPwd").hide();
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 选择对象类型
 */
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
					var _id = gtmdlToolList[i].classId;
					var _nameTemp = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
					dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenDTreeSetValEasyUi('divMObject','ipt_classID','"
							+ _id + "','" + _nameTemp + "');");
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}


function toUpdate(){
	var result = checkInfo("#tblAddPerfKPIDef") 
	if(result == true){
		checkBeforeUpdate();
	}
}

/*
 * 验证SNMP验证中该设备是否存在
 */
function checkBeforeUpdate() {
	var kpiID = $("#kpiID").val();
	var path = getRootName();
	var uri = path + "/monitor/perfKPIDef/checkPerKPIName?flag=update";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"kpiID" : kpiID,
			"name" : $("#ipt_name").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该指标名称已存在！", "error");
			} else {
				updatePerfKPIDef();
				return;
			}
		}
	};
	ajax_(ajax_param);
}


/*
 * 新增指标
 */
function updatePerfKPIDef(){
		var kpiID = $("#kpiID").val();
		var path = getRootName();
		var uri = path + "/monitor/perfKPIDef/updatePerfKPIDef";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"kpiID" : kpiID,
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
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "采集指标更新成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
				} else {
					$.messager.alert("提示", "采集指标更新失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
}
